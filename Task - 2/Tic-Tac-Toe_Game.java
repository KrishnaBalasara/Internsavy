import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Optional;

public class App extends Application {

    private static final int BOARD_SIZE = 3;
    private static final int CELL_SIZE = 100;

    private Button[][] buttons = new Button[BOARD_SIZE][BOARD_SIZE];
    private String currentPlayer = "X";
    private int player1Score = 0;
    private int player2Score = 0;

    private Label currentPlayerLabel;
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;

    private boolean gameOver = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic Tac Toe");

        GridPane gridPane = createGameBoard();
        gridPane.setBackground(new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox scoreBox = createScoreBox();

        Scene scene = new Scene(new VBox(gridPane, scoreBox), (BOARD_SIZE * CELL_SIZE) + 100, BOARD_SIZE * CELL_SIZE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createGameBoard() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button button = createCellButton();
                buttons[row][col] = button;
                gridPane.add(button, col, row);
            }
        }
        return gridPane;
    }

    private Button createCellButton() {
        Button button = new Button();
        button.setPrefSize(CELL_SIZE, CELL_SIZE);
        button.setFont(Font.font("Arial", 36));
        button.setOnAction(e -> onCellClicked(button));
        return button;
    }

    private void onCellClicked(Button button) {
        if (gameOver || !button.getText().isEmpty()) {
            return; // Ignore clicks if the game is over or cell already occupied
        }
        button.setText(currentPlayer);
        button.setDisable(true);

        if (checkForWin()) {
            showGameOver();
        } else if (checkForDraw()) {
            showGameOver("It's a Draw!");
        } else {
            currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
            currentPlayerLabel.setText("Current Player: " + currentPlayer);
        }
    }

    private boolean checkForWin() {
        String[][] boardState = new String[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                boardState[row][col] = buttons[row][col].getText();
            }
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            // Check rows
            if (boardState[i][0].equals(boardState[i][1]) && boardState[i][0].equals(boardState[i][2])
                    && !boardState[i][0].isEmpty()) {
                highlightWinningCells(buttons[i][0], buttons[i][1], buttons[i][2]);
                updateScores(boardState[i][0]);
                return true;
            }

            // Check columns
            if (boardState[0][i].equals(boardState[1][i]) && boardState[0][i].equals(boardState[2][i])
                    && !boardState[0][i].isEmpty()) {
                highlightWinningCells(buttons[0][i], buttons[1][i], buttons[2][i]);
                updateScores(boardState[0][i]);
                return true;
            }
        }

        // Check diagonals
        if (boardState[0][0].equals(boardState[1][1]) && boardState[0][0].equals(boardState[2][2])
                && !boardState[0][0].isEmpty()) {
            highlightWinningCells(buttons[0][0], buttons[1][1], buttons[2][2]);
            updateScores(boardState[0][0]);
            return true;
        }
        if (boardState[0][2].equals(boardState[1][1]) && boardState[0][2].equals(boardState[2][0])
                && !boardState[0][2].isEmpty()) {
            highlightWinningCells(buttons[0][2], buttons[1][1], buttons[2][0]);
            updateScores(boardState[0][2]);
            return true;
        }

        return false;
    }

    private boolean checkForDraw() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false; // At least one empty cell found
                }
            }
        }
        return true; // All cells are filled
    }

    private void highlightWinningCells(Button... winningCells) {
        for (Button button : winningCells) {
            button.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    private void updateScores(String winner) {
        if (winner.equals("X")) {
            player1Score++;
        } else if (winner.equals("O")) {
            player2Score++;
        }
        player1ScoreLabel.setText("Player 1: " + player1Score);
        player2ScoreLabel.setText("Player 2: " + player2Score);
    }

    private HBox createScoreBox() {
        currentPlayerLabel = new Label("Current Player: " + currentPlayer);
        currentPlayerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        player1ScoreLabel = new Label("Player 1: 0");
        player1ScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        player1ScoreLabel.setTextFill(Color.BLUE);

        player2ScoreLabel = new Label("Player 2: 0");
        player2ScoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        player2ScoreLabel.setTextFill(Color.GREEN);

        HBox scoreBox = new HBox(20, currentPlayerLabel, player1ScoreLabel, player2ScoreLabel);
        scoreBox.setAlignment(Pos.CENTER);
        return scoreBox;
    }

    private void showGameOver() {
        String winner = (currentPlayer.equals("X")) ? "Player 1" : "Player 2";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(winner + " wins!");
        alert.setContentText("Do you want to continue playing?");
        alert.setGraphic(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            newGame();
        } else {
            gameOver = true;
            currentPlayerLabel.setText("Game Over!");
        }
    }

    private void showGameOver(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(message);
        alert.setContentText("Do you want to continue playing?");
        alert.setGraphic(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            newGame();
        } else {
            gameOver = true;
            currentPlayerLabel.setText("Game Over!");
        }
    }

    private void newGame() {
        currentPlayer = "X";
        currentPlayerLabel.setText("Current Player: " + currentPlayer);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setDisable(false);
                buttons[row][col].setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        gameOver = false;
    }
}
