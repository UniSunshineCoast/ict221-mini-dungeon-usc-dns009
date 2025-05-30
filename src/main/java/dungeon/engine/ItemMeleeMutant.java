package dungeon.engine;

/**
 * Represents a melee mutant enemy that can attack the player.
 */
public class ItemMeleeMutant implements Item {
    private int meleeMutantDamage;
    private int meleeMutantScoreReward;

    /**
     * Constructs an ItemMeleeMutant with specified attack damage and score reward.
     * @param meleeMutantDamage      The amount of damage the mutant deals.
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
     * Handles player interaction when encountering this melee mutant.
     * This method both prints a message to the console (for text-based gameplay)
     * and returns the same message as a String for the GUI to display.
     * It decreases the player’s health by the mutant’s damage value and
     * rewards the player with the mutant's score reward.
     * @param player The player interacting with the mutant.
     * @return A message indicating the outcome of the encounter.
     */
    @Override
    public String itemInteraction(Player player) {
        // Adjust player's health and score.
        player.adjustHealth(-meleeMutantDamage);
        player.adjustScore(meleeMutantScoreReward);

        // Construct the interaction message.
        String message = "You attacked a melee mutant and won! -"
                + meleeMutantDamage + " HP, +"
                + meleeMutantScoreReward + " Score.";

        // Return the message for the GUI to display.
        return message;
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
