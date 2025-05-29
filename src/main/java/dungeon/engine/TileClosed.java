package dungeon.engine;

public class TileClosed extends Tile {
    public TileClosed() {
        super(false);
    }

    @Override
    public char getSymbol() {
        return '#';
    }
}
