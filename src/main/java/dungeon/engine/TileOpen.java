package dungeon.engine;

/**
 * Represents an open tile where items can be placed.
 */
public class TileOpen extends Tile {
    private Item item;

    /**
     * Creates an open tile with no item.
     */
    public TileOpen() {
        super(true);
        this.item = null;
    }

    /**
     * Creates an open tile with an item.
     * @param item The item placed on this tile.
     */
    public TileOpen(Item item) {
        super(true);
        this.item = item;
    }

    /**
     * Sets an item on this tile.
     * @param item The item to be placed.
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Gets the item currently on this tile.
     * @return The item on this tile, or null if empty.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Checks if this tile contains an item.
     * @return True if an item is present, false otherwise.
     */
    public boolean hasItem() {
        return item != null;
    }

    /**
     * Returns the symbol representing this tile.
     * @return The item's symbol if present, otherwise '.'.
     */
    @Override
    public char getSymbol() {
        return hasItem() ? item.getSymbol() : '.';
    }

    /**
     * Allows player interaction with the tile's item.
     * If the item is a trap, it remains. Otherwise, it is removed after interaction.
     * @param player The player interacting with this tile.
     */
    @Override
    public void interactWithPlayer(Player player) {
        if (hasItem()) {
            item.itemInteraction(player);

            // Remove item after being stepped on, unless it's a trap
            if (!(item instanceof ItemTrap)) {
                item = null;
            }
        }
    }
}
