package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.GameBoard;
import dungeon.engine.ItemLadder;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

public class TestItemLadder {

    @Test
    public void testItemInteraction() {
        GameBoard board = new GameBoard(5, 5, 2, 3, 3, 1, 3);
        Player player = new Player("Test", 10, 0, 0, 1);
        ItemLadder ladder = new ItemLadder(board);

        ladder.itemInteraction(player);

        assertEquals(2, board.getGameBoardLevel(), "GameBoard level should increase by 1");
        assertEquals(5, board.getDifficulty(), "Difficulty should increase by 2");
    }
}

