package dungeon.engine;

// Maze item interface
public interface Item {
    void itemInteraction(Player player);
    char getSymbol();
}
