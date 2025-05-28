package dungeon.textgame;

public class ItemLadder implements Item {
    private GameBoard board;


    ItemLadder(GameBoard board) {
        this.board = board;
    }

    // Methods
    @Override
    public void itemInteraction(Player player) {
        System.out.println("You found a ladder!");

        // Increment game level and redraw map
        board.incrementGameLevel(player);
    }

    @Override
    public char getSymbol() {
        return 'L';
    }
}
