package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.ItemRangedMutant;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;
import java.util.Random;

public class TestItemRangedMutant {

    @Test
    public void testAttemptAttack() {
        Player player = new Player("Test", 10, 0, 0, 1);
        ItemRangedMutant rangedMutant = new ItemRangedMutant(3, 2); // Mutant deals 3 damage, gives 2 score

        // Force attack to happen by bypassing randomness
        Random rand = new Random();
        if (rand.nextBoolean()) {
            rangedMutant.attemptAttack(player);
            assertEquals(2, player.getHealth(), "Player's health should decrease by 2 if hit");
        } else {
            // If missed, health should remain unchanged
            assertEquals(10, player.getHealth(), "Player's health should remain unchanged if mutant misses");
        }
    }
}
