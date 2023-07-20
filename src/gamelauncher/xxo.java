package gamelauncher;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import static javafx.scene.text.FontWeight.BLACK;
import javafx.scene.text.Text;

public class xxo extends GameLauncher {

    private String currentPlayer = "player 1";
    private final Button[][] board = new Button[3][3];
    private static final int WINDOW_WIDTH = 889;
    private static final int WINDOW_HIGHT = 500;
    private GridPane pane;
    private Pane root;
    Text win, tie;

    public Pane viewscene(Button x) {
        root = new Pane();
        root.setStyle("-fx-background-color:#D9FFF7;");
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);

        Game(x);

        root.getChildren().add(pane);
        pane.setLayoutX(210);
        pane.setLayoutY(15);
        return root;

    }

    private void Game(Button x) {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                button.setPrefSize(150, 150);
                button.setStyle("-fx-background-color: #E1C0FA;");
                button.setOnAction(event -> {
                    if (!button.getText().isEmpty()) {
                        return;
                    }
                    button.setText(currentPlayer == "player 1" ? "X" : "O");
                    button.setFont(Font.font("Arial Black", BLACK , 70));
                    button.setTextFill(button.getText() == "X" ? Color.web("#C06DFF") : Color.web("#13A58E"));

                    Result(x);

                });
                board[row][col] = button;
                pane.add(button, col, row);
                

            }
        }
        
        root.getChildren().add(x);

    }

    private Text Win_Text() {

        win = new Text(currentPlayer + "" + " wins");
        win.setFont(Font.font("Arial Black", 60));
        win.setFill(Color.web("#C06DFF"));

        return win;
    }

    private Text Tie_Text() {

        tie = new Text("IT'S A TIE !!");
        tie.setFont(Font.font("Arial Black", 60));
        tie.setFill(Color.web("#C06DFF"));

        return tie;

    }

    private void ResultScreen(Text text, Button x) {

        Button btn = new Button("REPLAY");
        btn.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BLACK , 20));

        VBox vbox = new VBox();
        vbox.getChildren().addAll(text, btn);
        vbox.setAlignment(Pos.CENTER);
        BackgroundFill backgroundFill = new BackgroundFill(Color.web("#E1C0FA"), new CornerRadii(100), new Insets(-15));
        Background background = new Background(backgroundFill);
        vbox.setBackground(background);
        pane.setLayoutX(pane.getWidth() / 2);
        pane.setLayoutY(pane.getHeight() / (2.5));
        pane.getChildren().add(vbox);

        btn.setOnAction(e -> {
            root.getChildren().clear();
            pane.getChildren().clear();
            Game(x);
            pane.setLayoutX(210);
            pane.setLayoutY(15);
            root.getChildren().add(pane);
       });

    }

    private void Result(Button x) {

        if (checkWin()) {
            pane.getChildren().clear();

            ResultScreen(Win_Text(),x);

        } else if (checkTie()) {
            pane.getChildren().clear();

            ResultScreen(Tie_Text(),x);

        } else {
            currentPlayer = currentPlayer == "player 1" ? "player 2" : "player 1";
        }

    }

    private boolean checkWin() {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (!board[row][0].getText().isEmpty() && board[row][0].getText().equals(board[row][1].getText()) && board[row][1].getText().equals(board[row][2].getText())) {
                return true;
            }
        }
        // Check columns
        for (int col = 0; col < 3; col++) {
            if (!board[0][col].getText().isEmpty() && board[0][col].getText().equals(board[1][col].getText()) && board[1][col].getText().equals(board[2][col].getText())) {
                return true;
            }
        }
        // Check diagonals
        if (!board[0][0].getText().isEmpty() && board[0][0].getText().equals(board[1][1].getText()) && board[1][1].getText().equals(board[2][2].getText())) {
            return true;
        }
        if (!board[0][2].getText().isEmpty() && board[0][2].getText().equals(board[1][1].getText()) && board[1][1].getText().equals(board[2][0].getText())) {
            return true;
        }
        return false;

    }

    private boolean checkTie() {
        for (Button[] row : board) {
            for (Button button : row) {
                if (button.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
