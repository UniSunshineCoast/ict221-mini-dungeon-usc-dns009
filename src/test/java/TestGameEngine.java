package dungeon.textgame;

import dungeon.engine.GameEngine;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the GameEngine class.
 */
public class TestGameEngine {

    /**
     * Tests whether GameEngine correctly returns the expected board size.
     */
    @Test
    void testGetSize() {
        GameEngine gameEngine = new GameEngine(10);

        assertEquals(10, gameEngine.getSize(), "GameEngine should return the correct board size.");
    }
}

