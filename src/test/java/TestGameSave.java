package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.GameBoard;
import dungeon.engine.GameSave;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

import java.io.*;

public class TestGameSave {

    @Test
    public void testSaveGameCreatesFile() {
        Player player = new Player("Test", 10, 5, 3, 3);
        GameBoard board = new GameBoard(2, 3, 1, 1, 2, 1, 3);
        int steps = 50;

        // Delete existing save file before testing
        File saveFile = new File("savegame.txt");
        if (saveFile.exists()) {
            saveFile.delete();
        }

        // Save the game (without user confirmation)
        GameSave.saveGame(player, board, steps);

        // Verify the file is created successfully
        assertTrue(saveFile.exists(), "Save file should be created.");
    }


    @Test
    public void testLoadGameRestoresPlayerState() {
        Player player = new Player("Test", 10, 5, 3, 3);
        GameBoard board = new GameBoard(2, 3, 1, 1, 2, 1, 3);
        int steps = 50;

        GameSave.saveGame(player, board, steps);

        // Modify player to simulate fresh game start
        player.setHealth(5);
        player.setScore(0);
        player.setx(0);
        player.sety(0);

        // Load saved game
        int loadedSteps = GameSave.loadGame(board, player);

        // Validate player state restored
        assertEquals("Test", player.getName());
        assertEquals(10, player.getHealth());
        assertEquals(5, player.getScore());
        assertEquals(3, player.getx());
        assertEquals(3, player.gety());
        assertEquals(50, loadedSteps, "Steps should be restored.");
    }

    @Test
    public void testLoadGameHandlesMissingFile() {
        Player player = new Player("Test", 10, 5, 3, 3);
        GameBoard board = new GameBoard(2, 3, 1, 1, 2, 1, 3);

        // Ensure file is deleted before testing
        File saveFile = new File("savegame.txt");
        saveFile.delete();

        // Attempt to load a nonexistent game
        int result = GameSave.loadGame(board, player);

        // Verify proper handling
        assertEquals(-1, result, "Load should return -1 when no save file exists.");
    }
}
