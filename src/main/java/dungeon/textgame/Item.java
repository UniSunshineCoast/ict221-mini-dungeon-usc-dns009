package dungeon.textgame;

// Maze item interface
public interface Item {
    void itemInteraction(Player player);
    char getSymbol();
}
