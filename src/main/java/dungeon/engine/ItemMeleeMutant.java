package dungeon.engine;

/**
 * Represents a melee mutant enemy that can attack the player.
 */
public class ItemMeleeMutant implements Item {
    private int meleeMutantDamage;
    private int meleeMutantScoreReward;

    /**
     * Constructs an ItemMeleeMutant with specified attack damage and score reward.
     * @param meleeMutantDamage    The amount of damage the mutant deals.
     * @param meleeMutantScoreReward The score reward when defeated.
     */
    public ItemMeleeMutant(int meleeMutantDamage, int meleeMutantScoreReward) {
        this.meleeMutantDamage = meleeMutantDamage;
        this.meleeMutantScoreReward = meleeMutantScoreReward;
    }

    /**
     * Gets the mutant's attack damage.
     * @return The amount of damage dealt.
     */
    public int getMeleeMutantDamage() {
        return meleeMutantDamage;
    }

    /**
     * Gets the score reward for defeating this mutant.
     * @return The amount of score gained.
     */
    public int getMeleeMutantScoreReward() {
        return meleeMutantScoreReward;
    }

    /**
     * Sets the mutant's attack damage.
     * @param meleeMutantDamage The new attack damage value.
     */
    public void setMeleeMutantDamage(int meleeMutantDamage) {
        this.meleeMutantDamage = meleeMutantDamage;
    }

    /**
     * Sets the score reward for defeating this mutant.
     * @param meleeMutantScoreReward The new score reward value.
     */
    public void setMeleeMutantScoreReward(int meleeMutantScoreReward) {
        this.meleeMutantScoreReward = meleeMutantScoreReward;
    }

    /**
     * Handles player interaction when encountering this mutant.
     * Attacking the mutant decreases the player's health and increases their score.
     * @param player The player interacting with the mutant.
     */
    @Override
    public void itemInteraction(Player player) {
        player.adjustHealth(-meleeMutantDamage);
        player.adjustScore(meleeMutantScoreReward);
        System.out.println("You attacked a melee mutant and won! -" + meleeMutantDamage + " HP, +" + meleeMutantScoreReward + " Score.");
    }

    /**
     * Returns the symbol representing this mutant.
     * @return 'M' as the melee mutant symbol.
     */
    @Override
    public char getSymbol() {
        return 'M';
    }
}
