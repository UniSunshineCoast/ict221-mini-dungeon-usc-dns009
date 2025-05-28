package dungeon.textgame;


public class Player {

    // Data
    String name;
    int health;
    int score;
    int x;
    int y;

    // Constructor
    public Player(String name, int health, int score, int x, int y) {
        this.name = name;
        this.health = health;
        this.score = score;
        this.x = x;
        this.y = y;
    }

    // Methods

    // Getters

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health; }

    public int getScore() { return score;
    }
    public int getx() {
        return x;
    }
    public int gety() {
        return y;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setx(int x) {
        this.x = x;
    }

    public void sety(int y) {
        this.y = y;
    }

    // Methods

    // Check for nearby ranged mutants
    public String checkNearbyRangedMutants(GameBoard board) {
        StringBuilder message = new StringBuilder();
        boolean alreadyAttacked = false; // Prevent duplicate attacks

        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                int checkX = x + dx;
                int checkY = y + dy;

                // Ensure checking only horizontal OR vertical positions (not diagonals)
                if ((dx == 0 || dy == 0) && (dx != dy)) {
                    if (checkX >= 0 && checkX < board.getSize() && checkY >= 0 && checkY < board.getSize()) {
                        Tile tile = board.getTile(checkX, checkY);

                        // If the tile has a ranged mutant, trigger its attack ONCE
                        if (tile instanceof TileOpen && ((TileOpen) tile).hasItem() && ((TileOpen) tile).getItem() instanceof ItemRangedMutant) {
                            if (!alreadyAttacked) { // Prevent duplicate attacks
                                boolean hit = ((ItemRangedMutant) ((TileOpen) tile).getItem()).attemptAttack(this);
                                alreadyAttacked = true; // Mark as attacked once

                                if (hit) {
                                    message.append("\nA Ranged Mutant shot at you! -2 HP.");
                                } else {
                                    message.append("\nA Ranged Mutant missed its shot!");
                                }
                            }
                        }
                    }
                }
            }
        }

        return message.toString(); // Ensure only ONE attack message appears
    }


    // Player movement
    public String playerMovement(String direction, GameBoard board) {
        StringBuilder message = new StringBuilder("Moved " + direction.toUpperCase());

        // Handle movement logic
        int newX = x, newY = y;
        if (direction.equals("u")) newX--;
        if (direction.equals("d")) newX++;
        if (direction.equals("l")) newY--;
        if (direction.equals("r")) newY++;

        // Check if movement is valid
        Tile newTile = board.getTile(newX, newY);
        if (newTile instanceof TileClosed) {
            return "You can't move there, there's a wall!";
        }

        // Update player position
        x = newX;
        y = newY;

        // Check for item interactions
        if (newTile instanceof TileOpen) {
            TileOpen openTile = (TileOpen) newTile;
            if (openTile.hasItem()) {
                Item item = openTile.getItem();

                // Use item interaction method for effect
                item.itemInteraction(this);

                // Remove item after interaction (except traps)
                if (!(item instanceof ItemTrap)) {
                    openTile.setItem(null);
                }
            }
        }

        // Display attack string if a ranged mutant is nearby
        String attackMessage = checkNearbyRangedMutants(board);
        message.append(attackMessage);

        return message.toString();
    }

    // Adjust health function used for damage and healing
    public void adjustHealth(int healthDelta) {
        health += healthDelta;
        if (health < 0) {
            health = 0;
        } else if (health > 10) {
            health = 10;
        }
    }

    // Adjust score used to increase or decrease score
    public void adjustScore(int scoreDelta) {
        score += scoreDelta;
    }
}
