package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.ItemTrap;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ItemTrap class.
 */
public class TestItemTrap {

    /**
     * Tests whether interacting with ItemTrap correctly decreases the player's health.
     */
    @Test
    public void testItemInteraction() {
        Player player = new Player("Test", 10, 0, 0, 1); // Player starts with full health
        ItemTrap trap = new ItemTrap(2); // Trap deals 2 damage

        // Player interacts with the trap
        trap.itemInteraction(player);

        // Validate health decrease
        assertEquals(8, player.getHealth(), "Player's health should decrease by 2 due to the trap.");
    }
}
