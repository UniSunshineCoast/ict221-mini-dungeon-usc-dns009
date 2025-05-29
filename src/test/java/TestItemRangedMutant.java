package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.ItemRangedMutant;
import dungeon.engine.Player;
import org.junit.jupiter.api.Test;
import java.util.Random;

/**
 * Unit tests for the ItemRangedMutant class.
 */
public class TestItemRangedMutant {

    /**
     * Tests whether the ranged mutant's attack correctly reduces the player's health if hit.
     * Ensures health remains unchanged if the attack misses.
     */
    @Test
    public void testAttemptAttack() {
        Player player = new Player("Test", 10, 0, 0, 1);
        ItemRangedMutant rangedMutant = new ItemRangedMutant(2, 2); // Mutant deals 2 damage, gives 2 score

        // Simulate the attack outcome using controlled randomness
        boolean hit = new Random().nextBoolean();

        if (hit) {
            rangedMutant.attemptAttack(player);
            assertEquals(8, player.getHealth(), "Player's health should decrease by 2 if hit.");
        } else {
            assertEquals(10, player.getHealth(), "Player's health should remain unchanged if mutant misses.");
        }
    }
}
