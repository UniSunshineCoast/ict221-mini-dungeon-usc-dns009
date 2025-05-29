package dungeon.engine;

/**
 * Represents a closed tile that cannot be traversed.
 */
public class TileClosed extends Tile {

    /**
     * Creates a closed tile that is not walkable.
     */
    public TileClosed() {
        super(false);
    }

    /**
     * Returns the symbol representing this tile.
     * @return '#' indicating a wall or obstacle.
     */
    @Override
    public char getSymbol() {
        return '#';
    }
}
