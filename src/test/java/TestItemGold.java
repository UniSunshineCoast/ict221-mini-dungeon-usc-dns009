package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.ItemGold;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;

public class TestItemGold {

    @Test
    public void testItemInteraction() {
        Player player = new Player("Test", 10, 0, 0, 1);
        ItemGold gold = new ItemGold(5); // Gold worth 5 points

        gold.itemInteraction(player);

        assertEquals(5, player.getScore(), "Player's score should increase by 5");
    }
}
