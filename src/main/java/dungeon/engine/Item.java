package dungeon.engine;

/**
 * Represents an item within the dungeon maze.
 * Items can interact with the player and have a symbolic representation.
 */
public interface Item {

    /**
     * Handles interaction between the player and the item.
     * @param player The player interacting with the item.
     */
    void itemInteraction(Player player);

    /**
     * Returns the symbol representing this item.
     * @return A character symbol associated with the item.
     */
    char getSymbol();
}
