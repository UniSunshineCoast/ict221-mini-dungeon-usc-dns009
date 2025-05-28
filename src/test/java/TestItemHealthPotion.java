package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestItemHealthPotion {

    @Test
    public void testItemInteraction() {
        Player player = new Player("Test", 5, 0, 0, 1); // Player starts with 5 health
        ItemHealthPotion potion = new ItemHealthPotion(4); // Potion heals for 4

        potion.itemInteraction(player);

        assertEquals(9, player.getHealth(), "Player's health should increase by 4");

        // Test health cap at 10
        potion.itemInteraction(player);
        assertEquals(10, player.getHealth(), "Player's health should cap at 10");
    }
}

