package gamelauncher;

import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameLauncher extends Application {

    Button ExitButton1, ExitButton2, ExitButton3;

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

        BorderPane pane = new BorderPane();
        HBox hbox = new HBox();

        Image menu1 = new Image("resources/MainBackground.png");
        ImageView menu = new ImageView(menu1);
        menu.fitHeightProperty().bind(primaryStage.heightProperty());
        pane.getChildren().add(menu);

        BackgroundFill backgroundFill = new BackgroundFill(Color.DARKBLUE, new CornerRadii(100), new Insets(4));
        Background background = new Background(backgroundFill);
        hbox.setBackground(background);

        Label title = new Label("Game Store");
        title.setFont(Font.font("Bauhaus 93", 70));
        title.setTextFill(Color.ALICEBLUE);

        hbox.getChildren().add(title);
        hbox.setAlignment(Pos.CENTER);
        pane.setTop(hbox);
        pane.setPrefSize(200, 200);

        Button btn1 = new Button();
        btn1.setStyle("-fx-background-color:white;");
        Button btn2 = new Button();
        btn2.setStyle("-fx-background-color:white;");
        Button btn3 = new Button();
        btn3.setStyle("-fx-background-color:white;");

        FlowPane GameSelect = new FlowPane();
        GameSelect.setHgap(25);
        GameSelect.setAlignment(Pos.CENTER);
        GameSelect.getChildren().addAll(btn1, btn2, btn3);

        //Snake button
        Image img1 = new Image("resources/snake1.png");
        ImageView view1 = new ImageView(img1);
        view1.setFitHeight(150);
        view1.setPreserveRatio(true);
        //Setting the size of the button
        btn1.setPrefSize(150, 150);
        //Setting a graphic to the button
        btn1.setGraphic(view1);

        //Flappybird button
        Image img2 = new Image("resources/flappybird.png");
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(150);
        view2.setPreserveRatio(true);
        //Setting the size of the button
        btn2.setPrefSize(150, 150);
        //Setting a graphic to the button
        btn2.setGraphic(view2);

        // X/O button
        Image img3 = new Image("resources/xo_mint.png");
        ImageView view3 = new ImageView(img3);
        view3.setFitHeight(150);
        view3.setPreserveRatio(true);
        //Setting the size of the button
        btn3.setPrefSize(150, 150);
        //Setting a graphic to the button
        btn3.setGraphic(view3);

        pane.setCenter(GameSelect);

        //main scene
        Scene scene = new Scene(pane, 889, 500);

        //---------------------------------------------------------------------------------------- 
        //----------------------------------------------------------------------------------------   
        
        //Snake game scene and Exit Button
        
        //return button and Action Event     
        ExitButton1 = new Button();
        ExitButton1.setShape(new Circle(1));

        Image ExitButton1Img = new Image("resources/home1.png");
        ImageView ExitButton1View = new ImageView(ExitButton1Img);
        ExitButton1View.setFitHeight(50);
        ExitButton1View.setPreserveRatio(true);
        //Setting the size of the button
        ExitButton1.setStyle(
                "-fx-background-radius: 5em; "
                + "-fx-min-width: 3px; "
                + "-fx-min-height: 3px; "
                + "-fx-max-width: 3px; "
                + "-fx-max-height: 3px;"
        );
        //Setting a graphic to the button
        ExitButton1.setGraphic(ExitButton1View);
        ExitButton1.setLayoutX(859);
        ExitButton1.setLayoutY(470);
        ExitButton1.setOnAction(e -> primaryStage.setScene(scene));

        //Snake game Scene
        Snake game1 = new Snake();
        Scene scene1 = new Scene(game1.viewscene(ExitButton1), 889, 500);
        scene1.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                    game1.snake.setCurrentDirection(Direction.UP);
                    break;
                case DOWN:
                    game1.snake.setCurrentDirection(Direction.DOWN);
                    break;
                case LEFT:
                    game1.snake.setCurrentDirection(Direction.LEFT);
                    break;
                case RIGHT:
                    game1.snake.setCurrentDirection(Direction.RIGHT);
                    break;
            }
        });
        
        
        
        //---------------------------------------------------------------------------------------- 
        //----------------------------------------------------------------------------------------
        
        //FlappyBird game scene and Exit Button
        
        
        ExitButton2 = new Button();
        ExitButton2.setShape(new Circle(1));

        Image ExitButton2Img = new Image("resources/home1.png");
        ImageView ExitButton2View = new ImageView(ExitButton2Img);
        ExitButton2View.setFitHeight(50);
        ExitButton2View.setPreserveRatio(true);
        //Setting the size of the button
        ExitButton2.setStyle(
                "-fx-background-radius: 5em; "
                + "-fx-min-width: 3px; "
                + "-fx-min-height: 3px; "
                + "-fx-max-width: 3px; "
                + "-fx-max-height: 3px;"
        );
        //Setting a graphic to the button
        ExitButton2.setGraphic(ExitButton2View);
        ExitButton2.setLayoutX(970);
        ExitButton2.setLayoutY(370);
        ExitButton2.setOnAction(e -> primaryStage.setScene(scene));
        
        
        
        Image quit = new Image("gamelauncher/crossbg.png");	
		ImageView Quit = new ImageView(quit);
		Quit.setFitHeight(30);
		Quit.setFitWidth(50);
        
     Button quitButton  = new Button();
	    quitButton.setGraphic(Quit);
	    quitButton.prefHeight(100);
	    quitButton.setPrefWidth(150);        		        
	    quitButton.setStyle("-fx-background-radius: 30px; " +
	            "-fx-background-color: white; ");
	    quitButton.setOnAction(e -> primaryStage.setScene(scene));
        
        FlappyBird game2 = new FlappyBird();
        game2.game(quitButton, ExitButton2);
        
        
        //---------------------------------------------------------------------------------------- 
        //----------------------------------------------------------------------------------------
        

        //XO game scene and Exit Button
        
        
        ExitButton3 = new Button();
        ExitButton3.setShape(new Circle(1));

        Image ExitButton3Img = new Image("resources/home1.png");
        ImageView ExitButton3View = new ImageView(ExitButton3Img);
        ExitButton3View.setFitHeight(50);
        ExitButton3View.setPreserveRatio(true);
        //Setting the size of the button
        ExitButton3.setStyle(
                "-fx-background-radius: 5em; "
                + "-fx-min-width: 3px; "
                + "-fx-min-height: 3px; "
                + "-fx-max-width: 3px; "
                + "-fx-max-height: 3px;"
        );
        //Setting a graphic to the button
        ExitButton3.setGraphic(ExitButton3View);
        ExitButton3.setLayoutX(859);
        ExitButton3.setLayoutY(470);
        ExitButton3.setOnAction(e -> primaryStage.setScene(scene));

        xxo game3 = new xxo();
        Scene scene3 = new Scene(game3.viewscene(ExitButton3), 889, 500);

        
        
                

        //Event to switch to game scene
        btn1.setOnAction(e -> primaryStage.setScene(scene1));
        btn2.setOnAction(e -> primaryStage.setScene(game2.game(quitButton, ExitButton2)));
        btn3.setOnAction(e -> primaryStage.setScene(scene3));
        

        //---------------------------------------------------------------------------------------- 
        //----------------------------------------------------------------------------------------
        
        //Welcome Scene:
        
        
        //Play Video 
        File mediaFile = new File("src/resources/WELCOME_Blue.mp4 ");
        Media media = new Media(mediaFile.toURI().toURL().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);        
        
        //Create Stackpane and WelcomeScene
        StackPane Welcome = new StackPane();
        Scene WelcomeScene = new Scene(Welcome, 889, 500);
        
        mediaView.setFitHeight(WelcomeScene.getHeight());
        
        //Create Welcome button that changes to Game Store Scene
        Button WelcomeBtn = new Button("                                         ");
        WelcomeBtn.setFont(Font.font("Arial Rounded MT Bold", 30));
        WelcomeBtn.setStyle("-fx-background-color: transparent; " + "-fx-background-radius: 5em; ");
        WelcomeBtn.setOnAction(e -> primaryStage.setScene(scene));
        
        //Add Video and button
        Welcome.getChildren().addAll(mediaView, WelcomeBtn);
        WelcomeBtn.setTranslateY(75);
        
        //BackGround Music
        File AudioFile = new File("src/resources/Background_Music.mp3");
        Media Audio = new Media(AudioFile.toURI().toURL().toString());  
        MediaPlayer AudioPlayer = new MediaPlayer(Audio);  
        AudioPlayer.setAutoPlay(true);  
        
        
        //---------------------------------------------------------------------------------------- 
        //----------------------------------------------------------------------------------------
        
        //End Scene:
        
        
        //Play Video 
        File EndFile = new File("src/resources/ENDING.mp4 ");
        Media EndMedia = new Media(EndFile.toURI().toURL().toString());
        MediaPlayer EndPlayer = new MediaPlayer(EndMedia);
        
        MediaView EndView = new MediaView(EndPlayer);
        
        //Create Stackpane and WelcomeScene
        Pane ENDING = new Pane(EndView);
        Scene ENDINGScene = new Scene(ENDING, 889, 500);
        
        EndView.setFitHeight(ENDINGScene.getHeight());
        
        
        
        GameSelect.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Q) {
                AudioPlayer.stop();
                primaryStage.setScene(ENDINGScene);
                EndPlayer.setAutoPlay(true);
                EndPlayer.setOnEndOfMedia(() -> primaryStage.close());

            }
        });
        
        
        
        

        primaryStage.setTitle("");
        primaryStage.setScene(WelcomeScene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    
    
    
    
    public static void main(String[] args) {
        launch(args);
    }

}
