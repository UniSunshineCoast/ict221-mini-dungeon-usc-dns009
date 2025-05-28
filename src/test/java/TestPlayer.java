package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestPlayer {

    @Test
    public void testAdjustHealth() {
        Player player = new Player("Test", 10, 0, 0, 1);

        player.adjustHealth(-5);
        assertEquals(5, player.getHealth(), "Health should decrease by 5");

        player.adjustHealth(6);
        assertEquals(10, player.getHealth(), "Health should cap at 10");

        player.adjustHealth(-15);
        assertEquals(0, player.getHealth(), "Health should not go below 0");
    }

    @Test
    public void testAdjustScore() {
        Player player = new Player("Test", 10, 0, 0, 1);

        player.adjustScore(5);
        assertEquals(5, player.getScore(), "Score should increase by 5");

        player.adjustScore(-3);
        assertEquals(2, player.getScore(), "Score should decrease by 3");
    }
}
