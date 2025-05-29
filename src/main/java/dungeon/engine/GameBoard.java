package dungeon.engine;

import java.util.Random;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameBoard {
    private final int SIZE = 10; // Fixed board size
    private Tile[][] tiles;
    private int traps, gold, potions, meleeMutants, rangedMutants;
    private int gameBoardLevel;
    private int difficulty;

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

    public void generateBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // Place walls on the border
                if (i == 0 || i == SIZE - 1 || j == 0 || j == SIZE - 1) {
                    tiles[i][j] = new TileClosed();
                } else {
                    tiles[i][j] = new TileOpen(); // Open tiles for movement & items
                }
            }
        }
    }

    private void populateEntities() {
        Random rand = new Random();
        placeItem(new ItemTrap(2), this.traps, rand);
        placeItem(new ItemGold(2), gold, rand);
        placeItem(new ItemHealthPotion(4), potions, rand);
        placeItem(new ItemMeleeMutant(2,2), meleeMutants, rand);
        placeItem(new ItemRangedMutant(2,2), this.rangedMutants, rand);
        placeItem(new ItemLadder(this), 1, rand);
    }

    private void placeItem(Item item, int count, Random rand) {
        int placed = 0; // Track successfully placed items

        while (placed < count) {
            int x = rand.nextInt(SIZE);
            int y = rand.nextInt(SIZE);

            // Check if it's an open tile AND doesn't already contain an item
            if (tiles[x][y] instanceof TileOpen && !((TileOpen) tiles[x][y]).hasItem()) {
                ((TileOpen) tiles[x][y]).setItem(item); // Store item within tile
                placed++; // Increase the count of successfully placed items
            }
        }
    }


    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void printBoard(int playerX, int playerY) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == playerX && j == playerY) {
                    System.out.print('P'); // Player icon
                } else {
                    System.out.print(tiles[i][j].getSymbol());
                }
            }
            System.out.println();
        }
    }

    public void incrementGameLevel(Player player) {
        // Increment the game level
        this.gameBoardLevel++;
        this.difficulty += 2;
        this.rangedMutants = this.difficulty;

        System.out.println("You made it to level " + gameBoardLevel + " of the dungeon!");

        // Record the player/ladder's coordinates
        int ladderX = player.getx();
        int ladderY = player.gety();

        // Generate and populate new board
        tiles = new Tile[SIZE][SIZE];
        generateBoard();
        populateEntities();

        // Set player to where the ladder was in the previous level
        player.setx(ladderX);
        player.sety(ladderY);
    }

    // Properly resets the board before loading a save
    public void clearBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tiles[i][j] = new TileOpen(); // Replaces all tiles with empty TileOpen()
            }
        }
    }

    public int getSize() {
        return SIZE;
    }

    public int getGameBoardLevel() {
        return gameBoardLevel;
    }

    public void setGameBoardLevel(int gameBoardLevel) {
        this.gameBoardLevel = gameBoardLevel;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    // GUI
    public StackPane getCellDisplay(int x, int y, Player player) {
        Tile tile = getTile(x, y);
        StackPane cellPane = new StackPane();

        // Define the cell background
        Rectangle rect = new Rectangle(40, 40);
        rect.setFill(tile instanceof TileClosed ? Color.DARKGRAY : Color.LIGHTGRAY);
        rect.setStroke(Color.BLACK);

        // Define the text content inside the cell (player, items, etc.)
        Text cellText = new Text(String.valueOf(tile.getSymbol())); // Converts char to String

        // If it's the player's position, highlight it!
        if (x == player.getx() && y == player.gety()) {
            rect.setFill(Color.BLUE);
        }

        cellPane.getChildren().addAll(rect, cellText);
        return cellPane;
    }
}
