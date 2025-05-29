package dungeon.engine;

public abstract class Tile {
    protected boolean walkable;

    public Tile(boolean walkable) {
        this.walkable = walkable;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public abstract char getSymbol();

    public void interactWithPlayer(Player player) {
        // Default: No interaction
    }
}
