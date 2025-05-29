package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.ItemHealthPotion;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ItemHealthPotion class.
 */
public class TestItemHealthPotion {

    /**
     * Tests whether interacting with ItemHealthPotion correctly increases the player's health.
     * Ensures health does not exceed the maximum cap of 10.
     */
    @Test
    public void testItemInteraction() {
        Player player = new Player("Test", 5, 0, 0, 1); // Player starts with 5 health
        ItemHealthPotion potion = new ItemHealthPotion(4); // Potion heals for 4 health points

        // Apply the potion and validate health increase
        potion.itemInteraction(player);
        assertEquals(9, player.getHealth(), "Player's health should increase by 4 after using the potion.");

        // Apply another potion and ensure health caps at 10
        potion.itemInteraction(player);
        assertEquals(10, player.getHealth(), "Player's health should cap at 10.");
    }
}


