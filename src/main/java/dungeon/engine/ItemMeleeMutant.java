package dungeon.engine;

public class ItemMeleeMutant implements Item {
    // Data
    private int meleeMutantDamage;
    private int meleeMutantScoreReward;

    // Constructor
    public ItemMeleeMutant(int meleeMutantDamage, int meleeMutantScoreReward) {
        this.meleeMutantDamage = meleeMutantDamage;
        this.meleeMutantScoreReward = meleeMutantScoreReward;
    }

    // Getters
    public int getMeleeMutantDamage() {
        return meleeMutantDamage;
    }

    public int getMeleeMutantScoreReward() {
        return meleeMutantScoreReward;
    }

    // Setters

    public void setMeleeMutantDamage(int meleeMutantDamage) {
        this.meleeMutantDamage = meleeMutantDamage;
    }

    public void setMeleeMutantScoreReward(int meleeMutantScoreReward) {
        this.meleeMutantScoreReward = meleeMutantScoreReward;
    }

    // Methods
    @Override
    public void itemInteraction (Player player) {
        player.adjustHealth(-meleeMutantDamage);
        player.adjustScore(meleeMutantScoreReward);
        System.out.println("You attacked a melee mutant and win. -2 HP, +2 Score");
    }

    @Override
    public char getSymbol() {
        return 'M';
    }
}
