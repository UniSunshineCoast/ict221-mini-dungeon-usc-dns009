package dungeon.engine;

/**
 * Represents a tile on the game board.
 * This can be either walkable or non-walkable, depending on its type.
 */
public abstract class Tile {
    protected boolean walkable;

    /**
     * Constructs a tile with a specified walkability.
     * @param walkable True if the tile can be walked on, false otherwise.
     */
    public Tile(boolean walkable) {
        this.walkable = walkable;
    }

    /**
     * Checks if this tile can be walked on.
     * @return True if walkable, false otherwise.
     */
    public boolean isWalkable() {
        return walkable;
    }

    /**
     * Returns the symbol representing this tile.
     * @return A character symbol associated with the tile.
     */
    public abstract char getSymbol();

    /**
     * Handles player interaction with the tile.
     * By default, tiles have no interaction.
     * @param player The player interacting with this tile.
     */
    public void interactWithPlayer(Player player) {
        // Default: No interaction
    }
}

