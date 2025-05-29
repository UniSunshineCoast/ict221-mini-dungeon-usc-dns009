package dungeon.engine;

import java.io.*;

/**
 * Manages saving and loading game data.
 */
public class GameSave {
    private static final String SAVE_FILE = "savegame.txt";

    /**
     * Saves the current game state to a file.
     * @param player The player whose data is saved.
     * @param board  The game board state.
     * @param steps  The remaining steps in the game.
     */
    public static void saveGame(Player player, GameBoard board, int steps) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write(player.getName() + "," + player.getHealth() + "," + player.getScore() + "," +
                    player.getX() + "," + player.getY() + "," + board.getGameBoardLevel() + "," +
                    board.getDifficulty() + "," + steps);
            writer.newLine();

            // Save the board state (items on each tile)
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    Tile tile = board.getTile(i, j);
                    if (tile instanceof TileOpen && ((TileOpen) tile).hasItem()) {
                        Item item = ((TileOpen) tile).getItem();
                        writer.write(i + "," + j + "," + item.getClass().getSimpleName());
                        writer.newLine();
                    }
                }
            }

            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Loads game data from a file.
     * @param board  The game board where state is restored.
     * @param player The player whose data is restored.
     * @return The number of remaining steps from the saved state.
     */
    public static int loadGame(GameBoard board, Player player) {
        File saveFile = new File(SAVE_FILE);

        if (!saveFile.exists()) {
            System.out.println("No saved game found.");
            return -1; // Indicate that no save was found
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            String[] data = reader.readLine().split(",");
            String name = data[0];
            int health = Integer.parseInt(data[1]);
            int score = Integer.parseInt(data[2]);
            int x = Integer.parseInt(data[3]);
            int y = Integer.parseInt(data[4]);
            int gameLevel = Integer.parseInt(data[5]);
            int difficulty = Integer.parseInt(data[6]);
            int steps = Integer.parseInt(data[7]);

            // Clear and regenerate board
            board.clearBoard();
            board.setGameBoardLevel(gameLevel);
            board.setDifficulty(difficulty);
            board.generateBoard();

            // Restore player state
            player.setName(name);
            player.setHealth(health);
            player.setScore(score);
            player.setX(x);
            player.setY(y);

            // Restore items on the board
            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemData = line.split(",");
                int itemX = Integer.parseInt(itemData[0]);
                int itemY = Integer.parseInt(itemData[1]);
                String itemType = itemData[2];

                Item item = switch (itemType) {
                    case "ItemGold" -> new ItemGold(2);
                    case "ItemHealthPotion" -> new ItemHealthPotion(4);
                    case "ItemMeleeMutant" -> new ItemMeleeMutant(2, 2);
                    case "ItemRangedMutant" -> new ItemRangedMutant(2, 2);
                    case "ItemTrap" -> new ItemTrap(2);
                    case "ItemLadder" -> new ItemLadder(board);
                    default -> null;
                };

                if (item != null) {
                    ((TileOpen) board.getTile(itemX, itemY)).setItem(item);
                }
            }

            System.out.println("Game loaded successfully!");
            return steps;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return -1;
        }
    }
}
