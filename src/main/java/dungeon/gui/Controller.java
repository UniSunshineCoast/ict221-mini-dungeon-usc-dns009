package dungeon.gui;

import dungeon.engine.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * Manages the game's GUI and player interactions.
 */
public class Controller {
    @FXML private GridPane gridPane;
    @FXML private Button upButton, downButton, leftButton, rightButton, saveButton, loadButton, quitButton;
    @FXML private Label healthLabel, scoreLabel, stepsLabel, levelLabel;
    @FXML private TextArea gameTextArea;
    @FXML private TextField playerNameField, difficultyField;
    @FXML private Button startGameButton;

    private GameBoard board;
    private Player player;
    private int steps = 100;
    private final Leaderboard leaderboard = new Leaderboard();

    /**
     * Initializes the game and sets up the GUI components.
     */
    @FXML
    public void initialize() {
        board = new GameBoard(5, 5, 2, 3, 2, 1, 3);
        player = new Player("Hero", 10, 0, 8, 1);

        startGameButton.setOnAction(event -> startGame());

        updateGUI();
        setupButtonActions();
    }

    /**
     * Updates the graphical user interface based on the game state.
     * Refreshes the board display, updates labels, and checks for victory or game over conditions.
     */
    private void updateGUI() {
        gridPane.getChildren().clear();

        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                gridPane.add(board.getCellDisplay(i, j, player), j, i);
            }
        }

        healthLabel.setText("Health: " + player.getHealth());
        scoreLabel.setText("Score: " + player.getScore());
        stepsLabel.setText("Steps: " + steps);
        levelLabel.setText("Dungeon Level: " + board.getGameBoardLevel());

        if (board.getGameBoardLevel() >= 3) {
            leaderboard.addEntry(player.getName(), player.getScore());
            victory();
        }

        if (player.getHealth() <= 0 || steps <= 0) {
            gameOver();
        }
    }

    /**
     * Sets up button actions for movement, saving, loading, and quitting the game.
     */
    private void setupButtonActions() {
        upButton.setOnAction(event -> movePlayer("u"));
        downButton.setOnAction(event -> movePlayer("d"));
        leftButton.setOnAction(event -> movePlayer("l"));
        rightButton.setOnAction(event -> movePlayer("r"));

        saveButton.setOnAction(event -> saveGame());
        loadButton.setOnAction(event -> loadGame());
        quitButton.setOnAction(event -> System.exit(0));
    }

    /**
     * Starts a new game, creating a player and setting up the board based on user input.
     */
    private void startGame() {
        String playerName = playerNameField.getText().trim();
        int difficulty = parseDifficulty(difficultyField.getText().trim());

        player = new Player(playerName.isEmpty() ? "Hero" : playerName, 10, 0, 1, 1);
        board = new GameBoard(5, 5, 2, 3, difficulty, 1, difficulty);

        playerNameField.clear();
        difficultyField.clear();
        updateGUI();
        gameTextArea.setText("Welcome, " + player.getName() + "! Difficulty set to " + difficulty + ".");
    }

    /**
     * Parses the difficulty input and ensures it falls within valid bounds.
     * @param input The difficulty input as a string.
     * @return A valid difficulty level between 1 and 5, defaulting to 3 if invalid.
     */
    private int parseDifficulty(String input) {
        try {
            int difficulty = Integer.parseInt(input);
            return (difficulty >= 1 && difficulty <= 5) ? difficulty : 3;
        } catch (NumberFormatException e) {
            return 3;
        }
    }

    /**
     * Moves the player in the specified direction and updates the game state.
     * @param direction The movement direction (u, d, l, r).
     */
    private void movePlayer(String direction) {
        gameTextArea.setText(player.playerMovement(direction, board));
        steps--;
        updateGUI();
    }

    /**
     * Saves the current game state.
     */
    private void saveGame() {
        GameSave.saveGame(player, board, steps);
        gameTextArea.setText("Game saved successfully!");
    }

    /**
     * Loads a previously saved game state.
     */
    private void loadGame() {
        int loadedSteps = GameSave.loadGame(board, player);
        gameTextArea.setText((loadedSteps != -1) ? "Game loaded successfully!" : "No saved game found.");
        if (loadedSteps != -1) steps = loadedSteps;
        updateGUI();
    }

    /**
     * Displays a game-over message and prompts the user to retry or exit.
     */
    private void gameOver() {
        String message = (player.getHealth() <= 0) ? "You ran out of health! Game Over." : "You ran out of steps! Game Over.";
        gameTextArea.setText(message);
        showEndGameDialog("Game Over", "You have lost the game!", "Would you like to retry?");
    }

    /**
     * Displays a victory message and prompts the user to retry or exit.
     */
    private void victory() {
        gameTextArea.setText("Congratulations! You escaped the dungeon and won!\n" + getLeaderboardText());
        leaderboard.addEntry(player.getName(), player.getScore());
        showEndGameDialog("Victory!", "You escaped the dungeon!", "Would you like to retry?");
    }

    /**
     * Displays a dialog box for retry or exit options after game completion.
     * @param title   The title of the dialog.
     * @param header  The header message.
     * @param content The body text of the dialog.
     */
    private void showEndGameDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.getButtonTypes().setAll(new ButtonType("Retry"), new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE));

        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("Retry")) {
                restartGame();
            } else {
                System.exit(0);
            }
        });
    }

    /**
     * Retrieves the formatted leaderboard as a string.
     * @return A leaderboard summary with names, scores, and dates.
     */
    private String getLeaderboardText() {
        StringBuilder leaderboardText = new StringBuilder("\n--- Leaderboard ---\n");
        leaderboard.getEntries().forEach(entry ->
                leaderboardText.append("# ").append(entry.getName())
                        .append(" - Score: ").append(entry.getScore())
                        .append(" - Date: ").append(entry.getDate()).append("\n"));
        return leaderboardText.append("-------------------\n").toString();
    }

    /**
     * Restarts the game, resetting the player and board.
     */
    private void restartGame() {
        board = new GameBoard(5, 5, 2, 3, 2, 1, 3);
        player = new Player("Hero", 10, 0, 8, 1);
        steps = 100;
        updateGUI();
        gameTextArea.setText("Game restarted! Try again.");
    }
}
