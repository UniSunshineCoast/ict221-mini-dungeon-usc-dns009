package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestItemMeleeMutant {

    @Test
    public void testItemInteraction() {
        Player player = new Player("Test", 10, 0, 0, 1); // Player starts with full health
        ItemMeleeMutant meleeMutant = new ItemMeleeMutant(3, 2); // Mutant deals 3 damage, gives 2 score

        meleeMutant.itemInteraction(player);

        assertEquals(7, player.getHealth(), "Player's health should decrease by 3");
        assertEquals(2, player.getScore(), "Player's score should increase by 2");
    }
}
