package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.ItemMeleeMutant;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ItemMeleeMutant class.
 */
public class TestItemMeleeMutant {

    /**
     * Tests whether interacting with ItemMeleeMutant correctly decreases the player's health
     * and increases their score.
     */
    @Test
    public void testItemInteraction() {
        Player player = new Player("Test", 10, 0, 0, 1); // Player starts with full health
        ItemMeleeMutant meleeMutant = new ItemMeleeMutant(2, 2); // Mutant deals 2 damage, gives 2 score

        // Player interacts with the melee mutant
        meleeMutant.itemInteraction(player);

        // Validate health decrease and score increase
        assertEquals(8, player.getHealth(), "Player's health should decrease by 2 after interaction.");
        assertEquals(2, player.getScore(), "Player's score should increase by 2 after interaction.");
    }
}

