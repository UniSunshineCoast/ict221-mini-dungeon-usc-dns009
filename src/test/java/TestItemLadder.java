package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ItemLadder class.
 */
public class TestItemLadder {

    /**
     * Tests whether interacting with ItemLadder correctly increases the game level and difficulty.
     */
    @Test
    public void testItemInteraction() {
        GameBoard board = new GameBoard(5, 5, 2, 3, 3, 1, 3);
        Player player = new Player("Test", 10, 0, 0, 1);
        ItemLadder ladder = new ItemLadder(board);

        // Player interacts with the ladder
        ladder.itemInteraction(player);

        // Validate game level and difficulty increase
        assertEquals(2, board.getGameBoardLevel(), "GameBoard level should increase by 1 after using the ladder.");
        assertEquals(5, board.getDifficulty(), "Difficulty should increase by 2 after using the ladder.");
    }
}
