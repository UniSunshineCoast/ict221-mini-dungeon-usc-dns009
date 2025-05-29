package dungeon.engine;

public class ItemGold implements Item {
    // Data
    private int goldScoreWorth;

    // Constructor
    public ItemGold(int goldScoreWorth) {
        this.goldScoreWorth = goldScoreWorth;
    }

    // Getter
    public int getGoldScoreWorth() {
        return goldScoreWorth;
    }

    // Setter
    public void setGoldScoreWorth(int goldScoreWorth) {
        this.goldScoreWorth = goldScoreWorth;
    }

    // Methods
    @Override
    public void itemInteraction(Player player) {
        player.adjustScore(goldScoreWorth);
        System.out.println("You obtained Gold, +" + goldScoreWorth + " score!");
    }

    @Override
    public char getSymbol() {
        return 'G';
    }
}
