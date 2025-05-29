package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.*;
import org.junit.jupiter.api.Test;
import java.io.File;

/**
 * Unit tests for the GameSave class.
 */
public class TestGameSave {

    /**
     * Tests whether saving a game correctly creates a save file.
     */
    @Test
    public void testSaveGameCreatesFile() {
        Player player = new Player("Test", 10, 5, 3, 3);
        GameBoard board = new GameBoard(2, 3, 1, 1, 2, 1, 3);
        int steps = 50;

        // Delete existing save file before testing
        File saveFile = new File("savegame.txt");
        if (saveFile.exists()) {
            assertTrue(saveFile.delete(), "Existing save file should be deleted before test.");
        }

        // Save the game
        GameSave.saveGame(player, board, steps);

        // Verify the save file was created successfully
        assertTrue(saveFile.exists(), "Save file should be created after saving the game.");
    }

    /**
     * Tests whether loading a saved game correctly restores the player's state.
     */
    @Test
    public void testLoadGameRestoresPlayerState() {
        Player player = new Player("Test", 10, 5, 3, 3);
        GameBoard board = new GameBoard(2, 3, 1, 1, 2, 1, 3);
        int steps = 50;

        GameSave.saveGame(player, board, steps);

        // Modify player state to simulate fresh game start
        player.setHealth(5);
        player.setScore(0);
        player.setX(0);
        player.setY(0);

        // Load saved game
        int loadedSteps = GameSave.loadGame(board, player);

        // Validate that the player's state has been restored correctly
        assertEquals("Test", player.getName(), "Player name should be restored.");
        assertEquals(10, player.getHealth(), "Player health should be restored.");
        assertEquals(5, player.getScore(), "Player score should be restored.");
        assertEquals(3, player.getX(), "Player X position should be restored.");
        assertEquals(3, player.getY(), "Player Y position should be restored.");
        assertEquals(50, loadedSteps, "Steps should be restored.");
    }

    /**
     * Tests whether loading a game properly handles a missing save file.
     */
    @Test
    public void testLoadGameHandlesMissingFile() {
        Player player = new Player("Test", 10, 5, 3, 3);
        GameBoard board = new GameBoard(2, 3, 1, 1, 2, 1, 3);

        // Ensure file is deleted before testing
        File saveFile = new File("savegame.txt");
        if (saveFile.exists()) {
            assertTrue(saveFile.delete(), "Existing save file should be deleted before test.");
        }

        // Attempt to load a nonexistent game
        int result = GameSave.loadGame(board, player);

        // Verify handling of missing save file
        assertEquals(-1, result, "Load should return -1 when no save file exists.");
    }
}
