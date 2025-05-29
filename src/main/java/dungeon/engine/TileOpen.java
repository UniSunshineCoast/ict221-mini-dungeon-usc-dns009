package dungeon.engine;

public class TileOpen extends Tile {
    private Item item;

    // Constructor for no item tile
    public TileOpen() {
        super(true);
        this.item = null;
    }

    // Constructor for item tile
    public TileOpen(Item item) {
        super(true);
        this.item = item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public boolean hasItem() {
        return item != null;
    }

    @Override
    public char getSymbol() {
        if (hasItem()) {
            return item.getSymbol();
        } else {
            return '.';
        }
    }

    @Override
    public void interactWithPlayer(Player player) {
        if (hasItem()) {
            item.itemInteraction(player);

            // Remove item after being stepped on; only if it isn't a trap
            if (!(item instanceof ItemTrap)) {
                item = null; // Remove item after collection
            }
        }
    }
}
