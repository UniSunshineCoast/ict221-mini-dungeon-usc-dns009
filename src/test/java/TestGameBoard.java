package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the GameBoard class.
 */
public class TestGameBoard {

    /**
     * Tests whether incrementing the game level correctly updates difficulty and game state.
     */
    @Test
    public void testIncrementGameLevel() {
        GameBoard board = new GameBoard(5, 5, 2, 3, 3, 1, 3);
        Player player = new Player("Test", 10, 0, 0, 1);

        board.incrementGameLevel(player);

        assertEquals(2, board.getGameBoardLevel(), "GameBoard level should increase by 1");
        assertEquals(5, board.getDifficulty(), "Difficulty should increase by 2");
        assertEquals(5, board.getDifficulty(), "Ranged mutants should match updated difficulty");
    }

    /**
     * Tests whether the getTile method correctly returns expected tile types.
     */
    @Test
    public void testGetTile() {
        GameBoard board = new GameBoard(5, 5, 2, 3, 3, 1, 3);

        // Edge tiles should be walls
        Tile tile = board.getTile(0, 0);
        assertTrue(tile instanceof TileClosed, "Edge tiles should be walls");

        // Center tiles should be open
        tile = board.getTile(5, 5);
        assertTrue(tile instanceof TileOpen, "Center tiles should be open");
    }
}
