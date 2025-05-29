package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Player class.
 */
public class TestPlayer {

    /**
     * Tests whether adjusting the player's health correctly decreases, increases,
     * and adheres to health boundaries.
     */
    @Test
    public void testAdjustHealth() {
        Player player = new Player("Test", 10, 0, 0, 1); // Player starts with full health

        // Decrease health by 5
        player.adjustHealth(-5);
        assertEquals(5, player.getHealth(), "Health should decrease by 5.");

        // Increase health beyond max (should cap at 10)
        player.adjustHealth(6);
        assertEquals(10, player.getHealth(), "Health should cap at 10.");

        // Decrease health below 0 (should not go negative)
        player.adjustHealth(-15);
        assertEquals(0, player.getHealth(), "Health should not go below 0.");
    }

    /**
     * Tests whether adjusting the player's score correctly increases and decreases the score.
     */
    @Test
    public void testAdjustScore() {
        Player player = new Player("Test", 10, 0, 0, 1);

        // Increase score by 5
        player.adjustScore(5);
        assertEquals(5, player.getScore(), "Score should increase by 5.");

        // Decrease score by 3
        player.adjustScore(-3);
        assertEquals(2, player.getScore(), "Score should decrease by 3.");
    }
}
