package dungeon.engine;

public class ItemTrap implements Item {
    // Data
    private int trapDamage;

    // Constructor
    public ItemTrap(int trapDamage) {
        this.trapDamage = trapDamage;
    }

    // Getter
    public int getTrapDamage() {
        return trapDamage;
    }

    // Setter
    public void setTrapDamage(int trapDamage) {
        this.trapDamage = trapDamage;
    }

    // Methods
    @Override
    public void itemInteraction(Player player) {
    player.adjustHealth(-trapDamage);
    System.out.println("You stepped in a trap and took " + trapDamage + " damage!");
    }

    @Override
    public char getSymbol() {
        return 'T';
    }
}
