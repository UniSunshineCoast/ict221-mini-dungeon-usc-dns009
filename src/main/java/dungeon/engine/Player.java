package dungeon.engine;

/**
 * Represents a player in the game with health, score, and movement capabilities.
 */
public class Player {
    private String name;
    private int health;
    private int score;
    private int x;
    private int y;

    /**
     * Constructs a player with initial attributes.
     * @param name   The player's name.
     * @param health The player's health points.
     * @param score  The player's score.
     * @param x      The player's initial X-coordinate.
     * @param y      The player's initial Y-coordinate.
     */
    public Player(String name, int health, int score, int x, int y) {
        this.name = name;
        this.health = health;
        this.score = score;
        this.x = x;
        this.y = y;
    }

    // Getters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getScore() { return score; }
    public int getX() { return x; }
    public int getY() { return y; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setHealth(int health) { this.health = health; }
    public void setScore(int score) { this.score = score; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    /**
     * Checks for nearby ranged mutants that may attack the player.
     * @param board The game board containing mutant locations.
     * @return A message indicating whether the player was attacked or missed.
     */
    public String checkNearbyRangedMutants(GameBoard board) {
        StringBuilder message = new StringBuilder();
        boolean alreadyAttacked = false; // Prevent multiple attacks

        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                int checkX = x + dx;
                int checkY = y + dy;

                // Ensure checks are only horizontal or vertical
                if ((dx == 0 || dy == 0) && (dx != dy)) {
                    if (checkX >= 0 && checkX < board.getSize() && checkY >= 0 && checkY < board.getSize()) {
                        Tile tile = board.getTile(checkX, checkY);

                        // If the tile has a ranged mutant, trigger its attack ONCE
                        if (tile instanceof TileOpen && ((TileOpen) tile).hasItem() && ((TileOpen) tile).getItem() instanceof ItemRangedMutant) {
                            if (!alreadyAttacked) {
                                boolean hit = ((ItemRangedMutant) ((TileOpen) tile).getItem()).attemptAttack(this);
                                alreadyAttacked = true;

                                message.append(hit ? "\nA Ranged Mutant shot at you! -2 HP." : "\nA Ranged Mutant missed its shot!");
                            }
                        }
                    }
                }
            }
        }
        return message.toString(); // Ensure only one attack message appears
    }

    /**
     * Moves the player in the specified direction and interacts with the tile.
     * @param direction The direction of movement (u, d, l, r).
     * @param board     The game board containing tiles.
     * @return A message confirming movement or interaction effects.
     */
    public String playerMovement(String direction, GameBoard board) {
        StringBuilder message = new StringBuilder("Moved ");
        message.append(switch (direction) {
            case "u" -> "up";
            case "d" -> "down";
            case "l" -> "left";
            case "r" -> "right";
            default -> direction.toUpperCase(); // Unexpected inputs
        });

        // Determine new position
        int newX = x, newY = y;
        if ("u".equals(direction)) newX--;
        if ("d".equals(direction)) newX++;
        if ("l".equals(direction)) newY--;
        if ("r".equals(direction)) newY++;

        // Validate movement
        Tile newTile = board.getTile(newX, newY);
        if (newTile instanceof TileClosed) {
            return "You can't move there, there's a wall!";
        }

        // Update player position
        x = newX;
        y = newY;

        // Handle item interactions
        if (newTile instanceof TileOpen) {
            TileOpen openTile = (TileOpen) newTile;
            if (openTile.hasItem()) {
                Item item = openTile.getItem();
                item.itemInteraction(this);

                // Remove item after interaction (except traps)
                if (!(item instanceof ItemTrap)) {
                    openTile.setItem(null);
                }
            }
        }

        // Append attack message if near a ranged mutant
        message.append(checkNearbyRangedMutants(board));
        return message.toString();
    }

    /**
     * Adjusts player's health, ensuring it stays within valid bounds.
     * @param healthDelta The amount to modify health by.
     */
    public void adjustHealth(int healthDelta) {
        health = Math.max(0, Math.min(health + healthDelta, 10));
    }

    /**
     * Adjusts player's score.
     * @param scoreDelta The amount to modify score by.
     */
    public void adjustScore(int scoreDelta) {
        score += scoreDelta;
    }
}
