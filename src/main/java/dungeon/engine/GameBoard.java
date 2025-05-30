package dungeon.engine;

import java.util.Random;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
     *
     * @param x      The X-coordinate.
     * @param y      The Y-coordinate.
     * @param player The player object.
     * @return A StackPane containing the tile's display.
     */
    public StackPane getCellDisplay(int x, int y, Player player) {
        Tile tile = getTile(x, y);
        StackPane cellPane = new StackPane();

        // Create the background rectangle for the cell.
        Rectangle rect = new Rectangle(40, 40);
        rect.setStroke(Color.BLACK);

        // Set the background color based on tile type.
        if (tile instanceof TileClosed) {
            rect.setFill(Color.DARKGRAY);
        } else {
            rect.setFill(Color.LIGHTGRAY);
        }

        // Define a Node for the content that will be displayed on top of the rectangle.
        Node content;

        // If the tile is open and contains an item, then decide which image to display.
        if (tile instanceof TileOpen && ((TileOpen) tile).hasItem()) {
            Item item = ((TileOpen) tile).getItem();
            if (item instanceof ItemTrap) {
                Image trapImage = new Image(getClass().getResourceAsStream("/trap_icon.png"));
                ImageView trapView = new ImageView(trapImage);
                trapView.setFitWidth(40);
                trapView.setFitHeight(40);
                content = trapView;
            } else if (item instanceof ItemMeleeMutant) {
                Image meleeMutantImage = new Image(getClass().getResourceAsStream("/melee mutant image.jpg"));
                ImageView meleeMutantView = new ImageView(meleeMutantImage);
                meleeMutantView.setFitWidth(40);
                meleeMutantView.setFitHeight(40);
                content = meleeMutantView;
            } else if (item instanceof ItemRangedMutant) {
                Image rangedMutantImage = new Image(getClass().getResourceAsStream("/ranged mutant image.jpg"));
                ImageView rangedMutantView = new ImageView(rangedMutantImage);
                rangedMutantView.setFitWidth(40);
                rangedMutantView.setFitHeight(40);
                content = rangedMutantView;
            } else if (item instanceof ItemHealthPotion) {
                Image healthPotionImage = new Image(getClass().getResourceAsStream("/health potion.jpg"));
                ImageView healthPotionView = new ImageView(healthPotionImage);
                healthPotionView.setFitWidth(40);
                healthPotionView.setFitHeight(40);
                content = healthPotionView;
            } else if (item instanceof ItemLadder) {
                Image ladderImage = new Image(getClass().getResourceAsStream("/ladder.jpg"));
                ImageView ladderView = new ImageView(ladderImage);
                ladderView.setFitWidth(40);
                ladderView.setFitHeight(40);
                content = ladderView;
            } else if (item instanceof ItemGold) {
                Image goldImage = new Image(getClass().getResourceAsStream("/gold.png"));
                ImageView goldView = new ImageView(goldImage);
                goldView.setFitWidth(40);
                goldView.setFitHeight(40);
                content = goldView;
            } else {
                // Fallback: show the tile's symbol as text.
                content = new Text(String.valueOf(tile.getSymbol()));
            }
        }
        // If the tile is closed (a wall), use the wall image.
        else if (tile instanceof TileClosed) {
            Image wallImage = new Image(getClass().getResourceAsStream("/wall.jpg"));
            ImageView wallView = new ImageView(wallImage);
            wallView.setFitWidth(40);
            wallView.setFitHeight(40);
            content = wallView;
        }
        // Otherwise, show the tile's symbol as text.
        else {
            content = new Text(String.valueOf(tile.getSymbol()));
        }

        // Add the background and the content.
        cellPane.getChildren().addAll(rect, content);

        // If the player is at this tile, overlay the hero image.
        if (x == player.getX() && y == player.getY()) {
            Image heroImage = new Image(getClass().getResourceAsStream("/hero.png"));
            ImageView heroView = new ImageView(heroImage);
            heroView.setFitWidth(40);
            heroView.setFitHeight(40);
            cellPane.getChildren().add(heroView);
        }

        return cellPane;
    }
}
