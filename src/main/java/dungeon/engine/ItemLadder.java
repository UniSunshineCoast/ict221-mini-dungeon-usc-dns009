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
     * This method both returns a descriptive message (for the GUI)
     * and UI. It advances the game level and includes the new level number in the returned message.
     * @param player The player interacting with the ladder.
     * @return A message indicating that the ladder was found and showing the new level.
     */
    @Override
    public String itemInteraction(Player player) {
        // Begin with the initial message.
        String message = "You found a ladder!";

        // Advance the game level using the board's logic.
        board.incrementGameLevel(player);

        // Retrieve the new game level.
        int newLevel = board.getGameBoardLevel();

        // Append the new level information to the message.
        message += " Advanced to level " + newLevel + "!";

        // Return the complete message for the GUI (and for the text game to print).
        return message;
    }

    /**
     * Returns the symbol representing this ladder.
     * @return 'L' as the ladder symbol.
     */
    @Override
    public char getSymbol() {
        return 'L';
    }
}
