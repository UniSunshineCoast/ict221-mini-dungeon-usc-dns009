package dungeon.textgame;

import dungeon.engine.GameEngine;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the GameEngine class.
 */
public class TestGameEngine {

    /**
     * Tests whether GameEngine initializes correctly.
     */
    @Test
    void testGameEngineInitialization() {
        GameEngine gameEngine = new GameEngine(); // No arguments now

        // Ensure GameEngine object is successfully created
        assertNotNull(gameEngine, "GameEngine should be initialized without errors.");
    }
}
