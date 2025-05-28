package dungeon.textgame;

public class TileClosed extends Tile {
    public TileClosed() {
        super(false);
    }

    @Override
    public char getSymbol() {
        return '#';
    }
}
