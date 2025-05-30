package dungeon.engine;

/**
 * Represents a trap item that deals damage to a player upon interaction.
 */
public class ItemTrap implements Item {
    private int trapDamage;

    /**
     * Constructs an ItemTrap with a specified damage value.
     * @param trapDamage The amount of damage the trap inflicts.
     */
    public ItemTrap(int trapDamage) {
        this.trapDamage = trapDamage;
    }

    /**
     * Gets the damage amount of this trap.
     * @return The damage value.
     */
    public int getTrapDamage() {
        return trapDamage;
    }

    /**
     * Sets the damage amount for this trap.
     * @param trapDamage The new damage value.
     */
    public void setTrapDamage(int trapDamage) {
        this.trapDamage = trapDamage;
    }

    /**
     * Applies the trap's damage effect when a player interacts with it.
     * This method both prints the message to the console (for text-based gameplay)
     * and returns the same message as a String for the GUI to display.
     * @param player The player affected by the trap.
     * @return A message indicating the trap damage.
     */
    @Override
    public String itemInteraction(Player player) {
        // Apply trap damage to the player's health.
        player.adjustHealth(-trapDamage);

        // Construct a descriptive message.
        String message = "You stepped in a trap and took " + trapDamage + " damage!";

        // Return the message for the GUI to display.
        return message;
    }

    /**
     * Returns the symbol representing this trap.
     * @return 'T' as the trap symbol.
     */
    @Override
    public char getSymbol() {
        return 'T';
    }
}
