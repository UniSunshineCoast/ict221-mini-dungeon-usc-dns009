package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestItemTrap {

    @Test
    public void testItemInteraction() {
        Player player = new Player("Test", 10, 0, 0, 1); // Player starts with full health
        ItemTrap trap = new ItemTrap(3); // Trap deals 3 damage

        trap.itemInteraction(player);

        assertEquals(7, player.getHealth(), "Player's health should decrease by 3 due to the trap");
    }
}
