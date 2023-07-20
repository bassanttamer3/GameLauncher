package gamelauncher;

import flappybird.res.Resourses;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class FlappyBird extends GameLauncher {

	private final double width = 1000, height = 400; // تعيين حجم الشاشة، العرض 1000 والارتفاع 400
	Resourses res = new Resourses(); // إنشاء كائن من الصنف Resourses للوصول إلى الموارد
	Pane root; // لوحة لعرض المحتوى على الشاشة
	boolean gameOver = false; // متغير يحدد ما إذا كانت اللعبة انتهت أم لا
	private boolean incrementOnce = true; // متغير يتحقق من تزايد النتيجة مرة واحدة فقط
	int score = 0; // متغير يخزن النتيجة الحالية
	int highScore = 0; // متغير يخزن أعلى نتيجة تم تحقيقها
	double FPS_30 = 30; // عدد إطارات الفيديو في الثانية (إطارات في الثانية)
	int counter_30FPS = 0, counter_3FPS = 0; // عدادات لتتبع الوقت وإدارة الحركة بمعدلات مختلفة
	Bird bird; // كائن يمثل الطائر في اللعبة
	TranslateTransition jump; // حركة الترجمة للقفز
	TranslateTransition fall; // حركة الترجمة للسقوط
	RotateTransition rotator; // حركة الدوران للطائر
	ArrayList<TwoTubes> listOfTubes = new ArrayList<>(); // قائمة تحتوي على أنابيب اللعبة
	ArrayList<Cloud> listOfClouds = new ArrayList<>(); // قائمة تحتوي على السحب في اللعبة
	ScoreLabel scoreLabel = new ScoreLabel(width, 0); // علامة تظهر النتيجة على الشاشة
	Timeline gameLoop; // الحلقة الزمنية التي تدير اللعبة
        AudioClip flyEffect;

	
	public Scene game(Button quitButton, Button x) {
		root = new Pane(); // إنشاء كائن Pane لعرض المحتوى
		root.setStyle("-fx-background-color: #4EC0CA"); // تعيين لون الخلفية للـ Pane
		StackPane Pane = new StackPane();
		root.getChildren().add(Pane);
	    Scene scene = new Scene(root, width, height); // إنشاء كائن Scene بواسطة الـ Pane وحجم الشاشة
        Image start = new Image("gamelauncher/greenSignri.png");	
    	ImageView Start = new ImageView(start);
    	Start.setFitHeight(30);
    	Start.setFitWidth(50);
    	Button startButton = new Button();
    	startButton.setGraphic(Start);
    	startButton.prefHeight(100);
        startButton.setPrefWidth(150);		 
        startButton.setStyle("-fx-background-radius: 30px; " +
                "-fx-background-color: white; ");
        startButton.setOnAction(e -> {
    	    initGame(x); // استدعاء الدالة initGame لبدء اللعبة
        });		    	
		
	    
	    HBox ButtonBox = new HBox(80);
	    ButtonBox.setAlignment(Pos.BOTTOM_CENTER);
	    HBox.setMargin(startButton,new Insets(40,40,80,40));
	    HBox.setMargin(quitButton,new Insets(40,40,80,40));
	    ButtonBox.getChildren().addAll(startButton, quitButton);
	    Image back = new Image("gamelauncher/flappybird.jpg");
	    ImageView background = new ImageView(back);
	    background.setOpacity(.8);		        
	    Pane.getChildren().addAll(background,ButtonBox);
	    Pane.getStyleClass().add("start-menu");
	    background.fitWidthProperty().bind(scene.widthProperty());
	    background.fitHeightProperty().bind(scene.heightProperty());
            
                        
            
            
	    root.setPrefSize(width, height); // تعيين الحجم المفضل للـ Pane
            flyEffect = new AudioClip(getClass().getResource("flap.mp3").toString());
	    root.setOnMouseClicked(e -> {
	        if (!gameOver) {
	            jumpflappy();   // إذا لم تنتهِ اللعبة، قم بالقفز
	            flyEffect.play();
	        } else {
	            initializeGame(); // إعادة تهيئة اللعبة إذا انتهت
	        }
	    });
	    
	    scene.setOnKeyPressed(e -> {
	        if (!gameOver) {
	            jumpflappy(); // إذا لم تنتهِ اللعبة، قم بالقفز
	            flyEffect.play();
	        } else {
	            initializeGame(); // إعادة تهيئة اللعبة إذا انتهت
	        }
	    });
	    root.requestFocus();
            
            
            return scene ;
	}

	

	/*
	 * 
	 * تقوم هذه الوظيفة بالتحقق مما إذا كان العداد counter_30FPS مضاعفًا للعدد 4 (أي يكون قسمته على 4 بدون باقي). إذا كان الشرط متحققًا، فإنه يتم استدعاء bird.refreshBird() لتحديث شكل الطائر أو أداء أي عملية أخرى ذات الصلة. بعد ذلك، يتم تعيين قيمة 1 للعداد counter_30FPS لبدء العد من جديد. وفي كل حالة، يتم زيادة قيمة counter_30FPS بواحد.

تعتمد وظيفة updateCounters() على العداد counter_30FPS والذي يتم تحديثه في كل إطار من إطارات اللعبة (بتكرار معدله 30 مرة في الثانية). يمكن استخدام هذه الوظيفة لتنفيذ أي إجراءات تحتاج إلى تحديث متكرر بناءً على الوقت أو الإطارات في اللعبة.
	 */
	
	private void updateCounters() {
	    if (counter_30FPS % 4 == 0) {
	        bird.refreshBird();
	        counter_30FPS = 1;
	    }
	    counter_30FPS++;
	}

	public void jumpflappy() {
	    rotator.setDuration(Duration.millis(100));
	    rotator.setToAngle(-40);
	    rotator.stop();
	    rotator.play(); // تدور الطائر لأعلى بزاوية -40 درجة
	    jump.setByY(-50);
	    jump.setCycleCount(1); // تقوم بحركة القفز لأعلى بقيمة 50
	    bird.jumping = true;
	    fall.stop();
	    jump.stop();
	    jump.play(); // تشغيل حركة القفز
	    jump.setOnFinished((finishedEvent) -> {
	        rotator.setDuration(Duration.millis(500));
	        rotator.setToAngle(40);
	        rotator.stop();
	        rotator.play(); // تدور الطائر لأسفل بزاوية 40 درجة
	        bird.jumping = false;
	        fall.play(); // تشغيل حركة السقوط
	    });
	}
	private void checkCollisions(Button x) {
        AudioClip pointEffect = new AudioClip(getClass().getResource("point.mp3").toString());
        AudioClip dieEffect = new AudioClip(getClass().getResource("die.mp3").toString());
        AudioClip hitEffect = new AudioClip(getClass().getResource("hit.mp3").toString());
        AudioClip swoshEffect = new AudioClip(getClass().getResource("flap.mp3").toString());
	    TwoTubes tube = listOfTubes.get(0); // احصل على أول عنصر من قائمة الأنابيب
	    if (tube.getTranslateX() < 35 && incrementOnce) { // إذا وصلت الأنبوبة إلى الموضع المحدد ولم يتم زيادة النقاط بعد
	    	pointEffect.play();
	    	score++; // زيادة النقاط	        
	        scoreLabel.setText("Score: " + score); // تحديث عرض النقاط
	        incrementOnce = false; // تعيين قيمة الزيادة مرة واحدة إلى صحيحة
	    }
	    Path p1 = (Path) Shape.intersect(bird.getBounds(), tube.topBody); // احصل على تداخل الشكل الهندسي للطائر مع أجزاء الأنبوبة
	    Path p2 = (Path) Shape.intersect(bird.getBounds(), tube.topHead);
	    Path p3 = (Path) Shape.intersect(bird.getBounds(), tube.lowerBody);
	    Path p4 = (Path) Shape.intersect(bird.getBounds(), tube.lowerHead);
	    boolean intersection = !(p1.getElements().isEmpty() // تحقق مما إذا كان هناك تداخل بين الطائر والأنبوبة
	            && p2.getElements().isEmpty()
	            && p3.getElements().isEmpty()
	            && p4.getElements().isEmpty());
	    if (bird.getBounds().getCenterY() + bird.getBounds().getRadiusY() > root.getHeight() // إذا واحدة من حدود الطائر تتجاوز حدود الشاشة
	            || bird.getBounds().getCenterY() - bird.getBounds().getRadiusY() < 0) {
	        intersection = true;  // يتم تعيين التداخل إلى صحيحة
	    }
	    if (intersection) { 
        	hitEffect.play();// إذا حدث التداخل
	    	dieEffect.play();
	    	GameOverLabel gameOverLabel = new GameOverLabel(width / 2, height / 2); // إنشاء عنصر GameOverLabel
	    	highScore = highScore < score ? score : highScore; // تحديث أعلى درجة
	        gameOverLabel.setText("Tap to retry. Score: " + score + "\n\tHighScore: " + highScore); // تعيين نص GameOverLabel
	    	swoshEffect.play(); 
	        saveHighScore(); // حفظ أعلى درجة
	        root.getChildren().addAll(gameOverLabel, x); // إضافة عنصر GameOverLabel إلى الـ Pane
	        root.getChildren().get(1).setOpacity(0); // تعيين تعتيم العنصر الثاني في الـ Pane
	        gameOver = true; // تعيين القيمة المنتهية للعبة إلى صحيحة
	        gameLoop.stop(); // توقف الحلقة الرئيسية للعبة
	    }
	}

	void initializeGame() {
        
	    listOfTubes.clear(); // مسح قائمة الأنابيب
	    listOfClouds.clear(); // مسح قائمة الغيوم
	    root.getChildren().clear(); // مسح جميع العناصر في الـ Pane
	    bird.getGraphics().setTranslateX(100); // تعيين موضع الطائر على المحور الأفقي
	    bird.getGraphics().setTranslateY(200); // تعيين موضع الطائر على المحور الرأسي
	    scoreLabel.setOpacity(0.8); // تعيين شفافية عنصر ScoreLabel
	    scoreLabel.setText("Score: 0"); // تعيين عرض النقاط إلى القيمة الأولية
	    root.getChildren().addAll(bird.getGraphics(), scoreLabel); // إضافة عنصري الطائر و ScoreLabel إلى الـ Pane
	    for (int i = 0; i < 5; i++) { // حلقة لإنشاء خمس غيوم
	        Cloud cloud = new Cloud(); // إنشاء عنصر Cloud جديد
	        cloud.setX(Math.random() * width); // تعيين موضع الغيمة على المحور الأفقي بشكل عشوائي
	        cloud.setY(Math.random() * height * 0.5 + 0.1); // تعيين موضع الغيمة على المحور الرأسي بشكل عشوائي
	        listOfClouds.add(cloud); // إضافة الغيمة إلى قائمة الغيوم
	        root.getChildren().add(cloud); // إضافة الغيمة إلى الـ Pane
	    }
	    for (int i = 0; i < 5; i++) { // حلقة لإنشاء خمسة أنابيب
	        SimpleDoubleProperty y = new SimpleDoubleProperty(0); // إنشاء خاصية بسيطة للإرتفاع
	        y.set(root.getHeight() * Math.random() / 2.0); // تعيين الإرتفاع بشكل عشوائي
	        TwoTubes tube = new TwoTubes(y, root, false, false); // إنشاء عنصر TwoTubes جديد
	        tube.setTranslateX(i * (width / 4 + 10) + 800); // تعيين موضع الأنبوبة على المحور الأفقي
	        listOfTubes.add(tube); // إضافة الأنبوبة إلى قائمة الأنابيب
	        root.getChildren().add(tube); // إضافة الأنبوبة إلى الـ Pane
	    }
	    score = 0; // تعيين النقاط إلى القيمة الأولية
	    incrementOnce = true; // تعيين زيادة الزيادة مرة واحدة إلى القيمة الأولية
	    gameOver = false; // تعيين حالة اللعبة إلى غير منتهية
	    bird.jumping = false; // تعيين حالة القفز للطائر إلى غير قائم بالقفز
	    fall.stop(); // إيقاف صوت السقوط إذا كان قيد التشغيل
	    fall.play(); // تشغيل صوت السقوط
	    gameLoop.play(); // تشغيل حلقة اللعبة
    }

	void initGame(Button x) {
	    bird = new Bird(res.birdImgs); // إنشاء كائن الطائر وتعيين الصور المتعلقة به
	    rotator = new RotateTransition(Duration.millis(500), bird.getGraphics()); // إنشاء حركة دوران للطائر
	    jump = new TranslateTransition(Duration.millis(450), bird.getGraphics()); // إنشاء حركة ترجمة للطائر (القفز)
	    fall = new TranslateTransition(Duration.millis(5 * height), bird.getGraphics()); // إنشاء حركة ترجمة للطائر (السقوط)
	    jump.setInterpolator(Interpolator.LINEAR); // تعيين نوع التفاعل لحركة القفز
	    fall.setByY(height + 10); // تحديد المسافة التي يتحركها الطائر أثناء السقوط
	    rotator.setCycleCount(1); // تعيين عدد مرات تكرار حركة الدوران (مرة واحدة)
	    bird.getGraphics().setRotationAxis(Rotate.Z_AXIS); // تحديد محور الدوران للطائر
	    gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / FPS_30), new EventHandler<ActionEvent>() {
	        // إنشاء حلقة اللعبة التي تتكرر بمعدل 30 إطارًا في الثانية

	        public void handle(ActionEvent e) {
	            updateCounters(); // تحديث العدادات
	            checkCollisions(x); // فحص التصادمات
	            if (listOfTubes.get(0).getTranslateX() <= -width / 12.3) {
	                // إذا وصلت أنبوبة معينة إلى الحافة اليسرى للشاشة

	                listOfTubes.remove(0); // قم بإزالة الأنبوبة من القائمة
	                SimpleDoubleProperty y = new SimpleDoubleProperty(0);
	                y.set(root.getHeight() * Math.random() / 2.0);
	                TwoTubes tube;
	                if (Math.random() < 0.4) {
	                    tube = new TwoTubes(y, root, true, false); // إنشاء زوج من الأنابيب (أنابيب عادية)
	                } else if (Math.random() > 0.85) {
	                    tube = new TwoTubes(y, root, true, true); // إنشاء زوج من الأنابيب (أنابيب تحتوي على العقبات)
	                } else {
	                    tube = new TwoTubes(y, root, false,false); // إنشاء زوج من الأنابيب (أنابيب عادية)
	                }
	                tube.setTranslateX(listOfTubes.get(listOfTubes.size() - 1).getTranslateX() + (width / 4 + 10));
	                listOfTubes.add(tube); // إضافة الزوج الجديد من الأنابيب إلى القائمة
	                incrementOnce = true;
	                root.getChildren().remove(7);
	                root.getChildren().add(tube); // إضافة الزوج الجديد من الأنابيب إلى الشاشة
	            }
	            for (int i = 0; i < listOfTubes.size(); i++) {
	                if (listOfClouds.get(i).getX() < -listOfClouds.get(i).getImage().getWidth() * listOfClouds.get(i).getScaleX()) {
	                    // إذا خرجت السحابة من الحافة اليسرى للشاشة

	                    listOfClouds.get(i).setX(listOfClouds.get(i).getX() + width + listOfClouds.get(i).getImage().getWidth() * listOfClouds.get(i).getScaleX());
	                    // إعادة تعيين موضع السحابة لتظهر من الحافة اليمنى للشاشة
	                }
	                listOfClouds.get(i).setX(listOfClouds.get(i).getX() - 1); // تحريك السحابة لليسار
	                listOfTubes.get(i).setTranslateX(listOfTubes.get(i).getTranslateX() - 2); // تحريك الأنابيب لليسار
	            }
	        }
	    }));
	    gameLoop.setCycleCount(Timeline.INDEFINITE); // تعيين عدد مرات تكرار حلقة اللعبة (-1 يعني تكرار لانهائي)
	    initializeGame(); // تهيئة اللعبة للبدء
	    loadHighScore(); // تحميل أعلى نقاط اللعبة المحفوظة
	}

	void loadHighScore() {
	    try {
	        highScore = new DataInputStream(new FileInputStream("highScore.score")).readInt();
	        // قراءة أعلى نقاط اللعبة من ملف "highScore.score" وتعيينها في المتغير highScore
	    } catch (Exception e) {
	        // إدارة أي استثناء يمكن أن يحدث أثناء قراءة النقاط العالية
	    }
	}

	void saveHighScore() {
	    try (DataOutputStream out = new DataOutputStream(new FileOutputStream("highScore.score"))) {
	        out.writeInt(score); // كتابة النقاط الحالية في الملف "highScore.score"
	        out.flush();
	    } catch (Exception e) {
	        // إدارة أي استثناء يمكن أن يحدث أثناء حفظ النقاط العالية
	    }
	}
}