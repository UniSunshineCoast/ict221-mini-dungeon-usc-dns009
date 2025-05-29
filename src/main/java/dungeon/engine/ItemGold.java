package dungeon.engine;

/**
 * Represents a gold item that increases the player's score upon interaction.
 */
public class ItemGold implements Item {
    private int goldScoreWorth;

    /**
     * Constructs an ItemGold with a specified score value.
     * @param goldScoreWorth The score value awarded upon collection.
     */
    public ItemGold(int goldScoreWorth) {
        this.goldScoreWorth = goldScoreWorth;
    }

    /**
     * Gets the score value of this gold item.
     * @return The score value.
     */
    public int getGoldScoreWorth() {
        return goldScoreWorth;
    }

    /**
     * Sets the score value for this gold item.
     * @param goldScoreWorth The new score value.
     */
    public void setGoldScoreWorth(int goldScoreWorth) {
        this.goldScoreWorth = goldScoreWorth;
    }

    /**
     * Handles player interaction when collecting gold.
     * Increases the player's score accordingly.
     * @param player The player collecting the gold.
     */
    @Override
    public void itemInteraction(Player player) {
        player.adjustScore(goldScoreWorth);
        System.out.println("You obtained gold! +" + goldScoreWorth + " score.");
    }

    /**
     * Returns the symbol representing this item.
     * @return 'G' as the gold symbol.
     */
    @Override
    public char getSymbol() {
        return 'G';
    }
}
