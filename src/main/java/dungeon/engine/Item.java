package dungeon.engine;

/**
 * Represents an item within the dungeon maze.
 * Items can interact with the player and have a symbolic representation.
 */
public interface Item {

    /**
     * Handles interaction between the player and the item.
     * Returns a message describing the interaction.
     * @param player The player interacting with the item.
     * @return A message about what happened.
     */
    String itemInteraction(Player player);

    /**
     * Returns the symbol representing this item.
     * @return A character symbol associated with the item.
     */
    char getSymbol();
}
