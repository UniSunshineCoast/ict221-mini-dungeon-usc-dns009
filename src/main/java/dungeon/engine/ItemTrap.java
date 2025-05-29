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
     * @param player The player affected by the trap.
     */
    @Override
    public void itemInteraction(Player player) {
        player.adjustHealth(-trapDamage);
        System.out.println("You stepped in a trap and took " + trapDamage + " damage!");
    }

    /**
     * Returns the symbol representing this item.
     * @return 'T' as the trap symbol.
     */
    @Override
    public char getSymbol() {
        return 'T';
    }
}
