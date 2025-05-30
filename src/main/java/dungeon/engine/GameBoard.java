package dungeon.engine;

import java.util.Random;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Represents the game board, managing tile placement, entities, and levels.
 */
public class GameBoard {
    private static final int SIZE = 10; // Fixed board size
    private Tile[][] tiles;
    private int traps, gold, potions, meleeMutants, rangedMutants;
    private int gameBoardLevel;
    private int difficulty;

    /**
     * Constructs a game board with specified entity counts and difficulty level.
     * @param traps        The number of traps.
     * @param gold         The amount of gold.
     * @param potions      The number of health potions.
     * @param meleeMutants The number of melee mutants.
     * @param rangedMutants The number of ranged mutants.
     * @param gameBoardLevel The initial game level.
     * @param difficulty   The difficulty level.
     */
    public GameBoard(int traps, int gold, int potions, int meleeMutants, int rangedMutants, int gameBoardLevel, int difficulty) {
        this.traps = traps;
        this.gold = gold;
        this.potions = potions;
        this.meleeMutants = meleeMutants;
        this.gameBoardLevel = gameBoardLevel;
        this.difficulty = difficulty;
        this.rangedMutants = this.difficulty;

        tiles = new Tile[SIZE][SIZE];
        generateBoard();
        populateEntities(); // Place items dynamically
    }

    /**
     * Generates the dungeon layout with walls and open tiles.
     */
    public void generateBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tiles[i][j] = (i == 0 || i == SIZE - 1 || j == 0 || j == SIZE - 1) ? new TileClosed() : new TileOpen();
            }
        }
    }

    /**
     * Populates the dungeon with items and enemies.
     */
    private void populateEntities() {
        Random rand = new Random();
        placeItem(new ItemTrap(2), this.traps, rand);
        placeItem(new ItemGold(2), gold, rand);
        placeItem(new ItemHealthPotion(4), potions, rand);
        placeItem(new ItemMeleeMutant(2, 2), meleeMutants, rand);
        placeItem(new ItemRangedMutant(2, 2), this.rangedMutants, rand);
        placeItem(new ItemLadder(this), 1, rand);
    }

    /**
     * Places items at random positions on the board.
     * @param item  The item to be placed.
     * @param count The number of instances to place.
     * @param rand  The random generator for placement.
     */
    private void placeItem(Item item, int count, Random rand) {
        int placed = 0;

        while (placed < count) {
            int x = rand.nextInt(SIZE);
            int y = rand.nextInt(SIZE);

            if (tiles[x][y] instanceof TileOpen && !((TileOpen) tiles[x][y]).hasItem()) {
                ((TileOpen) tiles[x][y]).setItem(item);
                placed++;
            }
        }
    }

    /**
     * Gets a tile at the specified coordinates.
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     * @return The tile at the given position.
     */
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    /**
     * Prints the dungeon board to the console, marking the player's position.
     * @param playerX The player's X-coordinate.
     * @param playerY The player's Y-coordinate.
     */
    public void printBoard(int playerX, int playerY) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print((i == playerX && j == playerY) ? 'P' : tiles[i][j].getSymbol());
            }
            System.out.println();
        }
    }

    /**
     * Advances the game level, regenerating the board while preserving the player's position.
     * @param player The player advancing to the next level.
     */
    public void incrementGameLevel(Player player) {
        gameBoardLevel++;
        difficulty += 2;
        rangedMutants = difficulty;

        // Comment out or remove this print statement to avoid duplicate messages in the text game:
        // System.out.println("You made it to level " + gameBoardLevel + " of the dungeon!");

        int ladderX = player.getX();
        int ladderY = player.getY();

        tiles = new Tile[SIZE][SIZE];
        generateBoard();
        populateEntities();

        player.setX(ladderX);
        player.setY(ladderY);
    }

    /**
     * Clears the game board, resetting it to an empty state.
     */
    public void clearBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tiles[i][j] = new TileOpen();
            }
        }
    }

    /**
     * Gets the size of the board.
     * @return The board size.
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Gets the current game level.
     * @return The game level.
     */
    public int getGameBoardLevel() {
        return gameBoardLevel;
    }

    /**
     * Sets the current game level.
     * @param gameBoardLevel The new game level.
     */
    public void setGameBoardLevel(int gameBoardLevel) {
        this.gameBoardLevel = gameBoardLevel;
    }

    /**
     * Gets the current difficulty level.
     * @return The difficulty level.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the current difficulty level.
     * @param difficulty The new difficulty level.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the graphical representation of a tile for the game UI.
     * @param x      The X-coordinate.
     * @param y      The Y-coordinate.
     * @param player The player object.
     * @return A StackPane containing the tile's display.
     */
    public StackPane getCellDisplay(int x, int y, Player player) {
        Tile tile = getTile(x, y);
        StackPane cellPane = new StackPane();

        Rectangle rect = new Rectangle(40, 40);
        rect.setFill(tile instanceof TileClosed ? Color.DARKGRAY : Color.LIGHTGRAY);
        rect.setStroke(Color.BLACK);

        Text cellText = new Text(String.valueOf(tile.getSymbol()));

        if (x == player.getX() && y == player.getY()) {
            rect.setFill(Color.BLUE);
        }

        cellPane.getChildren().addAll(rect, cellText);
        return cellPane;
    }
}
