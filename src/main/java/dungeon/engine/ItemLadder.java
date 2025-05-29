package dungeon.engine;

/**
 * Represents a ladder that allows the player to advance to the next game level.
 */
public class ItemLadder implements Item {
    private final GameBoard board;

    /**
     * Constructs an ItemLadder associated with a game board.
     * @param board The game board in which the ladder exists.
     */
    public ItemLadder(GameBoard board) {
        this.board = board;
    }

    /**
     * Handles player interaction when stepping on the ladder.
     * Advances the player to the next game level.
     * @param player The player interacting with the ladder.
     */
    @Override
    public void itemInteraction(Player player) {
        System.out.println("You found a ladder!");

        // Increment game level and redraw map
        board.incrementGameLevel(player);
    }

    /**
     * Returns the symbol representing this item.
     * @return 'L' as the ladder symbol.
     */
    @Override
    public char getSymbol() {
        return 'L';
    }
}
