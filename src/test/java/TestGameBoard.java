package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestGameBoard {

    @Test
    public void testIncrementGameLevel() {
        GameBoard board = new GameBoard(5, 5, 2, 3, 3, 1, 3);
        Player player = new Player("Test", 10, 0, 0, 1);

        board.incrementGameLevel(player);

        assertEquals(2, board.getGameBoardLevel(), "GameBoard level should increase by 1");
        assertEquals(5, board.getDifficulty(), "Difficulty should increase by 2");
        assertEquals(5, board.getDifficulty(), "Ranged mutants should match updated difficulty");

    }

    @Test
    public void testGetTile() {
        GameBoard board = new GameBoard(5, 5, 2, 3, 3, 1, 3);

        Tile tile = board.getTile(0, 0);
        assertTrue(tile instanceof TileClosed, "Edge tiles should be walls");

        tile = board.getTile(5, 5);
        assertTrue(tile instanceof TileOpen, "Center tiles should be open");
    }
}
