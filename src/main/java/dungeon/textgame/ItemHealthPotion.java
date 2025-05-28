package dungeon.textgame;

public class ItemHealthPotion implements Item {
    // Data
    private int potionHealthAmount;

    // Constructor
    public ItemHealthPotion(int potionHealthAmount) {
        this.potionHealthAmount = potionHealthAmount;
    }

    // Getter
    public int getPotionHealthAmount() {
        return potionHealthAmount;
    }

    // Setter
    public void setPotionHealthAmount(int potionHealthAmount) {
        this.potionHealthAmount = potionHealthAmount;
    }

    // Methods
    @Override
    public void itemInteraction(Player player) {
        player.adjustHealth(potionHealthAmount);
        System.out.println("The health potion restores +" + potionHealthAmount + " of your health!");
    }

    @Override
    public char getSymbol() {
        return 'H';
    }
}
