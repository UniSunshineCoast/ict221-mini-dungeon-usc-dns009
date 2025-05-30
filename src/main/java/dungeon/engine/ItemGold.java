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
     * Handles player interaction when collecting the gold item.
     * This method both prints a message to the console (for text-based gameplay)
     * and returns the same message as a String for the GUI display.
     * @param player The player collecting the gold.
     * @return A message indicating the gold collection and score gain.
     */
    @Override
    public String itemInteraction(Player player) {
        // Adjust player's score.
        player.adjustScore(goldScoreWorth);

        // Construct the interaction message.
        String message = "You obtained gold! +" + goldScoreWorth + " score.";

        // Return the message for the GUI to display.
        return message;
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
