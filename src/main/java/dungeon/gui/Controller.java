package dungeon.gui;

import dungeon.engine.Leaderboard;
import dungeon.engine.LeaderboardEntry;
import dungeon.engine.GameSave;
import dungeon.engine.Player;
import dungeon.engine.GameBoard;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class Controller {
    @FXML private GridPane gridPane;
    @FXML private Button upButton, downButton, leftButton, rightButton, saveButton, loadButton, quitButton;
    @FXML private Label healthLabel, scoreLabel, stepsLabel, levelLabel;
    @FXML private TextArea gameTextArea;
    @FXML private TextField playerNameField;
    @FXML private TextField difficultyField;
    @FXML private Button startGameButton;

    private GameBoard board;
    private Player player;
    private int steps = 100;
    private Leaderboard leaderboard = new Leaderboard();

    @FXML
    public void initialize() {
        // Create the GameBoard and default player
        board = new GameBoard(5, 5, 2, 3, 2, 1, 3);
        player = new Player("Hero", 10, 0, 8, 1);

        // Set up the "Start Game" button to trigger startGame()
        startGameButton.setOnAction(event -> startGame());

        // Initialize GUI and buttons
        updateGUI();
        setupButtonActions();
    }


    private void updateGUI() {
        gridPane.getChildren().clear();

        // Loop through board and add cells to GUI
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                StackPane cellDisplay = board.getCellDisplay(i, j, player);
                gridPane.add(cellDisplay, j, i);
            }
        }

        // Update player stats in the GUI
        healthLabel.setText("Health: " + player.getHealth());
        scoreLabel.setText("Score: " + player.getScore());
        stepsLabel.setText("Steps: " + steps);
        levelLabel.setText("Dungeon Level: " + board.getGameBoardLevel());

        // **NEW:** Check for victory condition
        if (board.getGameBoardLevel() >= 3) {
            victory();
        }

        // Check for both game over conditions (health or steps reaching zero)
        if (player.getHealth() <= 0 || steps <= 0) {
            gameOver();
        }
    }

    private void setupButtonActions() {
        upButton.setOnAction(event -> movePlayer("u"));
        downButton.setOnAction(event -> movePlayer("d"));
        leftButton.setOnAction(event -> movePlayer("l"));
        rightButton.setOnAction(event -> movePlayer("r"));

        saveButton.setOnAction(event -> saveGame());
        loadButton.setOnAction(event -> loadGame());
        quitButton.setOnAction(event -> System.exit(0));
    }

    private void startGame() {
        String playerName = playerNameField.getText().trim();
        int difficulty;

        try {
            difficulty = Integer.parseInt(difficultyField.getText().trim());
            if (difficulty < 1 || difficulty > 5) {
                difficulty = 3; // Default if invalid
            }
        } catch (NumberFormatException e) {
            difficulty = 3; // Default if not a valid number
        }

        // Create new player & game board based on input
        player = new Player(playerName.isEmpty() ? "Hero" : playerName, 10, 0, 1, 1);
        board = new GameBoard(5, 5, 2, 3, difficulty, 1, difficulty); // Set difficulty level

        // Clear input fields & update GUI
        playerNameField.clear();
        difficultyField.clear();
        updateGUI();
        gameTextArea.setText("Welcome, " + player.getName() + "! Difficulty set to " + difficulty + ".");
    }

    private void movePlayer(String direction) {
        String movementResult = player.playerMovement(direction, board); // Movement feedback

        steps--;
        updateGUI();

        // Show both movement result + attack alerts in the GUI text box
        gameTextArea.setText(movementResult);
    }

    private void saveGame() {
        GameSave.saveGame(player, board, steps);
        gameTextArea.setText("Game saved successfully!");
    }

    private void loadGame() {
        int loadedSteps = GameSave.loadGame(board, player);
        if (loadedSteps != -1) {
            steps = loadedSteps;
            gameTextArea.setText("Game loaded successfully!");
        } else {
            gameTextArea.setText("No saved game found.");
        }
        updateGUI(); // Refresh board & stats after loading
    }

    private void gameOver() {
        String message = (player.getHealth() <= 0) ? "You ran out of health! Game Over." : "You ran out of steps! Game Over.";
        gameTextArea.setText(message);

        // Create an alert dialog asking to retry
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("You have lost the game!");
        alert.setContentText("Would you like to retry?");

        ButtonType retryButton = new ButtonType("Retry");
        ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(retryButton, exitButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == retryButton) {
                restartGame();
            } else {
                System.exit(0);  // Exit the game
            }
        });
    }

    private void victory() {
        String message = "Congratulations! You escaped the dungeon and won!";
        gameTextArea.setText(message + "\n" + getLeaderboardText());

        // Create an alert dialog asking to retry
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Victory!");
        alert.setHeaderText("You escaped the dungeon!");
        alert.setContentText("Would you like to retry?");

        ButtonType retryButton = new ButtonType("Retry");
        ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(retryButton, exitButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == retryButton) {
                restartGame();
            } else {
                System.exit(0);
            }
        });
    }

    private String getLeaderboardText() {
        StringBuilder leaderboardText = new StringBuilder("\n--- Leaderboard ---\n");

        for (LeaderboardEntry entry : leaderboard.getEntries()) {
            leaderboardText.append("# ").append(entry.getName())
                    .append(" - Score: ").append(entry.getScore())
                    .append(" - Date: ").append(entry.getDate())
                    .append("\n");
        }

        leaderboardText.append("-------------------\n");

        return leaderboardText.toString();
    }

    private void restartGame() {
        board = new GameBoard(5, 5, 2, 3, 2, 1, 3);
        player = new Player("Hero", 10, 0, 8, 1);
        steps = 100;  // Reset steps

        updateGUI();  // Refresh UI
        gameTextArea.setText("Game restarted! Try again.");
    }
}

