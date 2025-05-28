package dungeon.gui;

import dungeon.engine.GameEngine;
import dungeon.textgame.GameSave;
import dungeon.textgame.Player;
import dungeon.textgame.GameBoard;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class Controller {
    @FXML private GridPane gridPane;
    @FXML private Button upButton, downButton, leftButton, rightButton, saveButton, loadButton, quitButton;
    @FXML private Label healthLabel, scoreLabel, stepsLabel, levelLabel;
    @FXML private TextArea gameTextArea;

    private GameBoard board;
    private Player player;
    private int steps = 100;

    @FXML
    public void initialize() {
        board = new GameBoard(2, 3, 1, 1, 2, 1, 3);
        player = new Player("Hero", 10, 0, 1, 1);

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

    private void movePlayer(String direction) {
        String movementResult = player.playerMovement(direction, board); // Movement feedback
        String attackMessage = player.checkNearbyRangedMutants(board); // Mutant attack feedback

        steps--;
        updateGUI();

        // Show both movement result + attack alerts in the GUI text box
        gameTextArea.setText(movementResult + attackMessage);
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

}

