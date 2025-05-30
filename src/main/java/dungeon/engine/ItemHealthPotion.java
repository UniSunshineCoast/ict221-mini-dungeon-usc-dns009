package dungeon.engine;

/**
 * Represents a health potion that restores the player's health upon interaction.
 */
public class ItemHealthPotion implements Item {
    private int potionHealthAmount;

    /**
     * Constructs an ItemHealthPotion with a specified health restoration amount.
     *
     * @param potionHealthAmount The amount of health restored when used.
     */
    public ItemHealthPotion(int potionHealthAmount) {
        this.potionHealthAmount = potionHealthAmount;
    }

    /**
     * Gets the amount of health restored by this potion.
     *
     * @return The health restoration value.
     */
    public int getPotionHealthAmount() {
        return potionHealthAmount;
    }

    /**
     * Sets the amount of health restored by this potion.
     *
     * @param potionHealthAmount The new health restoration value.
     */
    public void setPotionHealthAmount(int potionHealthAmount) {
        this.potionHealthAmount = potionHealthAmount;
    }

    /**
     * Handles player interaction when using the health potion.
     * Restores the player's health and returns a descriptive message.
     * This method both prints the message for text-based gameplay and returns
     * it for the GUI to display.
     *
     * @param player The player using the potion.
     * @return A message indicating the health restoration.
     */
    @Override
    public String itemInteraction(Player player) {
        // Adjust player's health.
        player.adjustHealth(potionHealthAmount);

        // Construct the interaction message.
        String message = "The health potion restores +" + potionHealthAmount + " HP!";

        // Return the message so the GUI can display it.
        return message;
    }

    /**
     * Returns the symbol representing this item.
     *
     * @return 'H' as the health potion symbol.
     */
    @Override
    public char getSymbol() {
        return 'H';
    }
}
