package dungeon.gui;

import dungeon.engine.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Manages the game's GUI and player interactions.
 */
public class Controller {
    @FXML private GridPane gridPane;
    @FXML private Button upButton, downButton, leftButton, rightButton, saveButton, loadButton, quitButton;
    @FXML private Label healthLabel, scoreLabel, stepsLabel, levelLabel;
    @FXML private TextArea gameTextArea;

    private GameBoard board;
    private Player player;
    private int steps = 100;
    private final Leaderboard leaderboard = new Leaderboard();
    private int difficultySetting = 3; // default difficulty

    /**
     * Initializes the game and sets up the GUI components.
     */
    @FXML
    public void initialize() {
        // Create a temporary placeholder player so that showSetupDialog can update the name.
        player = new Player("", 10, 0, 1, 1);

        // Disable movement buttons until setup is complete.
        upButton.setDisable(true);
        downButton.setDisable(true);
        leftButton.setDisable(true);
        rightButton.setDisable(true);

        // Show the modal setup dialog (blocks until player enters data).
        showSetupDialog();

        // Now that the modal has updated the player’s name and the difficultySetting field,
        // initialize the game state with startGame().
        startGame();

        // Re-enable movement controls now that the game state is ready.
        upButton.setDisable(false);
        downButton.setDisable(false);
        leftButton.setDisable(false);
        rightButton.setDisable(false);

        updateGUI();
        setupButtonActions();
    }


    /**
     * Displays a modal dialog that forces the player to enter their name
     * and choose a difficulty (1–5) before they can begin playing.
     */
    private void showSetupDialog() {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Game Setup");

        // Create UI controls.
        Label nameLabel = new Label("Enter your name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        Label diffLabel = new Label("Select Difficulty (1-5):");
        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("1", "2", "3", "4", "5");
        difficultyComboBox.setPromptText("Choose Difficulty");

        Button setupStartButton = new Button("Start Game");

        // Handle button action.
        setupStartButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            String chosenDifficulty = difficultyComboBox.getValue();

            if (name.isEmpty() || chosenDifficulty == null || chosenDifficulty.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing Information");
                alert.setHeaderText(null);
                alert.setContentText("Please enter your name and select a difficulty.");
                alert.showAndWait();
            } else {
                // Store the new player name and difficulty.
                player.setName(name);
                difficultySetting = Integer.parseInt(chosenDifficulty);
                System.out.println("Player name: " + name);
                System.out.println("Difficulty: " + difficultySetting);
                dialogStage.close();
            }
        });

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));
        dialogVBox.getChildren().addAll(nameLabel, nameField, diffLabel, difficultyComboBox, setupStartButton);

        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
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
        // Use the name that was set by the modal dialog.
        String playerName = player.getName();
        // Fallback to "Hero" if, for some reason, the name is still empty.
        if (playerName.isEmpty()) {
            playerName = "Hero";
        }
        // Use the difficulty setting that was updated by the modal dialog.
        int difficulty = difficultySetting;

        // Create a new Player and GameBoard using the modally obtained values.
        player = new Player(playerName, 10, 0, 8, 1);
        board = new GameBoard(5, 5, 2, 3, difficulty, 1, difficulty);

        updateGUI();
        gameTextArea.setText("Welcome, " + player.getName() + "! Difficulty set to " + difficulty + ".");
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
        gameTextArea.setText("Congratulations! You escaped the dungeon and won!");
        showEndGameDialog("Victory!", "You escaped the dungeon!", "Would you like to retry?");
    }

    /**
    * Displays a dialog box for retry or exit options after game completion,
    * including the leaderboard text above the retry prompt.
    * @param title   The title of the dialog.
    * @param header  The header message.
    * @param content The body text (prompt) of the dialog.
    */
    private void showEndGameDialog(String title, String header, String content) {
        String overallContent = getLeaderboardText() + "\n\n" + content;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(overallContent);

        alert.getButtonTypes().setAll(new ButtonType("Retry"),
                new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE));

        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("Retry")) {
                resetGame();  // Use resetGame() so that the modal setup dialog appears.
            } else {
                System.exit(0);
            }
        });
    }

    /**
     * Resets the game by forcing the player to re-enter setup information
     * (name and difficulty), and then reinitializes the game state.
     */
    private void resetGame() {
        // Reset any global game state variables if needed, for example:
        steps = 100;
        // Display the setup dialog to prompt the player to enter their name and difficulty.
        showSetupDialog();
        // Start the game with the new configuration.
        startGame();
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
