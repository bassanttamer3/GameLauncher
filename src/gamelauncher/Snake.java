package gamelauncher;

import static java.lang.Math.random;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class Snake extends GameLauncher {

    private static final int WINDOW_WIDTH = 889;
    private static final int WINDOW_HIGHT = 500;
    private static final int RADIUS = 8;
    private Pane root;
    private Circle food;
    private Random random;
    Snaake snake;
    private Text scoore, gameOVER_TEXT;
    private Timeline time;
    private Button btn;

    private void newFOOD() {
        food = new Circle(random.nextInt(WINDOW_WIDTH), random.nextInt(WINDOW_HIGHT), RADIUS + 2);
        food.setFill(Color.color(random(), random(), 0));
        root.getChildren().add(food);
    }

    private void newSNAKE() {
        snake = new Snaake(WINDOW_WIDTH / 2, WINDOW_HIGHT / 2, RADIUS + 6);
        snake.setFill(Color.BEIGE);
        root.getChildren().add(snake);
        for (int i = 0; i < 5; i++) {
            newFOOD();
            snake.eat(food);
        }

    }

    private boolean hit() {
        return food.intersects(snake.getBoundsInLocal());
    }

    private void gameOVER_TEXT(Button x) {
        gameOVER_TEXT = new Text("GAME OVER");
        gameOVER_TEXT.setFont(Font.font("Cambria", 100));
        gameOVER_TEXT.setFill(Color.RED);
        btn = new Button("REPLAY");
        btn.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BLACK , 20));

        btn.setOnAction(e -> {
            root.getChildren().clear();
            time.stop();
            game(x);
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(gameOVER_TEXT, btn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX(WINDOW_WIDTH / 5);
        vbox.setLayoutY(WINDOW_HIGHT / 3.2);
        root.getChildren().add(vbox);
    }

    private void Scoore() {
        scoore.setText("   SCORE :  " + (snake.getlength() - 6));
        scoore.setFont(Font.font("Cambria", 30));
        scoore.setFill(Color.BLACK);
        root.getChildren().add(scoore);
    }

    private boolean gameOver() {
        return snake.eatSelf();
    }

    private void move(Button x) {
            snake.step();
            adjustLocation();
            if (hit()) {
                snake.eat(food);
                newFOOD();
                Scoore();
            } else if (gameOver()) {
                root.getChildren().clear();
                snake_image();
                gameOVER_TEXT(x);
                root.getChildren().add(x);

            }
        
    }

    private void snake_image() {
        Image snakeimage = new Image("resources/SnakeBackground.jpg");
        ImageView mv = new ImageView(snakeimage);
        mv.setFitWidth(WINDOW_WIDTH);
        mv.setFitHeight(WINDOW_HIGHT);
        mv.setOpacity(1);
        root.getChildren().add(mv);
    }

    private void adjustLocation() {
        if (snake.getCenterX() < 0) {
            snake.setCenterX(WINDOW_WIDTH);
        } else if (snake.getCenterX() > WINDOW_WIDTH) {
            snake.setCenterX(0);
        }
        if (snake.getCenterY() < 0) {
            snake.setCenterY(WINDOW_HIGHT);
        } else if (snake.getCenterY() > WINDOW_HIGHT) {
            snake.setCenterY(0);
        }
    }

    private void game(Button x) {
        time = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            move(x);
        }));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();
        snake_image();
        random = new Random();
        scoore = new Text(0, 32, "0");
        newSNAKE();
        root.getChildren().add(x);
    }

    public Pane viewscene(Button x) {

        root = new Pane();
        root.setPrefSize(WINDOW_WIDTH, WINDOW_HIGHT);

        game(x);
        

        return root;

    }

}
