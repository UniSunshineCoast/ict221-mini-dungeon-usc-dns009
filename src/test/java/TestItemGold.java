package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.ItemGold;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ItemGold class.
 */
public class TestItemGold {

    /**
     * Tests whether interacting with ItemGold correctly increases the player's score.
     */
    @Test
    public void testItemInteraction() {
        Player player = new Player("Test", 10, 0, 0, 1);
        ItemGold gold = new ItemGold(2); // Gold worth 2 points

        gold.itemInteraction(player);

        assertEquals(2, player.getScore(), "Player's score should increase by 2 after collecting gold.");
    }
}

