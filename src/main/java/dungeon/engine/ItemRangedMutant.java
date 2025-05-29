package dungeon.engine;

import java.util.Random;

public class ItemRangedMutant implements Item {
    // Data
    private int rangedMutantDamage;
    private int rangedMutantScoreReward;
    private int rangedMutantX;
    private int rangedMutantY;

    // Constructor
    public ItemRangedMutant(int rangedMutantDamage, int rangedMutantScoreReward) {
        this.rangedMutantDamage = rangedMutantDamage;
        this.rangedMutantScoreReward = rangedMutantScoreReward;
    }

    // Getters
    public int getRangedMutantDamage() {
        return rangedMutantDamage;
    }

    public int getRangedMutantScoreReward() {
        return rangedMutantScoreReward;
    }

    // Setters
    public void setRangedMutantDamage(int rangedMutantDamage) {
        this.rangedMutantDamage = rangedMutantDamage;
    }

    public void setRangedMutantScoreReward(int rangedMutantScoreReward) {
        this.rangedMutantScoreReward = rangedMutantScoreReward;
    }

    // Methods
    @Override
    public void itemInteraction(Player player) {
        player.adjustScore(rangedMutantScoreReward);
        System.out.println("You defeated the ranged mutant, +" + rangedMutantScoreReward + "!");
    }

    @Override
    public char getSymbol() {
        return 'R';
    }

    public boolean attemptAttack(Player player) {
        Random rand = new Random();
        boolean hit = rand.nextBoolean(); // 50% chance of hitting

        if (hit) {
            player.adjustHealth(-rangedMutantDamage);
            System.out.println("A ranged mutant shoots you from afar! -2 HP.");
        } else {
            System.out.println("A ranged mutant takes a shot but misses!");
        }

        return hit; // Now returns true if hit, false if missed
    }

}
