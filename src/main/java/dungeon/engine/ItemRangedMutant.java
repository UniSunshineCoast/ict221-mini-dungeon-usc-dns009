package dungeon.engine;

import java.util.Random;

/**
 * Represents a ranged mutant enemy that can attack the player from a distance.
 */
public class ItemRangedMutant implements Item {
    private int rangedMutantDamage;
    private int rangedMutantScoreReward;

    /**
     * Constructs an ItemRangedMutant with specified attack damage and score reward.
     * @param rangedMutantDamage    The amount of damage the mutant deals.
     * @param rangedMutantScoreReward The score reward when defeated.
     */
    public ItemRangedMutant(int rangedMutantDamage, int rangedMutantScoreReward) {
        this.rangedMutantDamage = rangedMutantDamage;
        this.rangedMutantScoreReward = rangedMutantScoreReward;
    }

    /**
     * Gets the mutant's attack damage.
     * @return The amount of damage dealt.
     */
    public int getRangedMutantDamage() {
        return rangedMutantDamage;
    }

    /**
     * Gets the score reward for defeating this mutant.
     * @return The amount of score gained.
     */
    public int getRangedMutantScoreReward() {
        return rangedMutantScoreReward;
    }

    /**
     * Sets the mutant's attack damage.
     * @param rangedMutantDamage The new attack damage value.
     */
    public void setRangedMutantDamage(int rangedMutantDamage) {
        this.rangedMutantDamage = rangedMutantDamage;
    }

    /**
     * Sets the score reward for defeating this mutant.
     * @param rangedMutantScoreReward The new score reward value.
     */
    public void setRangedMutantScoreReward(int rangedMutantScoreReward) {
        this.rangedMutantScoreReward = rangedMutantScoreReward;
    }

    /**
     * Handles player interaction when encountering this mutant.
     * Defeating the mutant increases the player's score.
     * @param player The player interacting with the mutant.
     */
    @Override
    public void itemInteraction(Player player) {
        player.adjustScore(rangedMutantScoreReward);
        System.out.println("You defeated the ranged mutant, +" + rangedMutantScoreReward + "!");
    }

    /**
     * Returns the symbol representing this mutant.
     * @return 'R' as the ranged mutant symbol.
     */
    @Override
    public char getSymbol() {
        return 'R';
    }

    /**
     * Attempts an attack on the player with a 50% chance of success.
     * @param player The player being targeted.
     * @return True if the attack hits, false if missed.
     */
    public boolean attemptAttack(Player player) {
        boolean hit = new Random().nextBoolean(); // 50% chance of hitting

        if (hit) {
            player.adjustHealth(-rangedMutantDamage);
            System.out.println("A ranged mutant shoots you from afar! -" + rangedMutantDamage + " HP.");
        } else {
            System.out.println("A ranged mutant takes a shot but misses!");
        }

        return hit;
    }
}
