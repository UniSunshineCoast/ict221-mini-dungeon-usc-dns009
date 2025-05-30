package dungeon.engine;

import java.util.Scanner;

/**
 * Represents the game engine, which manages the game board and gameplay logic.
 */
public class GameEngine {

    /**
     * Plays the text-based Dungeon Crawler game.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String retry = "yes";
        Leaderboard leaderboard = new Leaderboard();

        while (retry.equals("yes")) {
            // Game introduction
            int steps = 100;
            System.out.println("Welcome to the Dungeon Crawler! What is your name?");
            String playerName = scanner.nextLine();
            System.out.println("How to play: Move with u (up), d (down), l (left), r (right), save (to save the game), load (to load a saved game), or q to quit.");

            // Ask for difficulty level
            System.out.println("Choose difficulty level (provide a number between 1 and 5):");
            int difficulty = scanner.nextInt();
            scanner.nextLine();

            // Validate difficulty input
            if (difficulty < 1 || difficulty > 5) {
                System.out.println("Invalid difficulty. Setting default difficulty level: 3");
                difficulty = 3;
            }

            // Initialize player and board
            Player player = new Player(playerName, 10, 0, 8, 1);
            GameBoard board = new GameBoard(5, 5, 2, 3, difficulty, 1, difficulty);

            // Print initial board state
            board.printBoard(player.getX(), player.getY());

            // Game loop
            while (steps > 0 && board.getGameBoardLevel() < 3 && player.getHealth() > 0) {
                System.out.println("Make your move. Health: " + player.getHealth() +
                        ", Score: " + player.getScore() +
                        ". Steps remaining: " + steps +
                        ". Type 'help' for instructions.");

                String move = scanner.nextLine().toLowerCase();

                // Handle quit command.
                if (move.equals("q")) {
                    break;
                }
                // Handle help command.
                else if (move.equals("help")) {
                    System.out.println("Instructions: Use 'u' for up, 'd' for down, 'l' for left, 'r' for right. "
                            + "Type 'save' to save, 'load' to load a saved game, or 'q' to quit.");
                    continue; // Skip step decrement since this is not a movement command.
                }
                // Handle save command.
                else if (move.equals("save")) {
                    GameSave.saveGame(player, board, steps);
                }
                // Handle load command.
                else if (move.equals("load")) {
                    int loadedSteps = GameSave.loadGame(board, player);
                    if (loadedSteps != -1) {
                        steps = loadedSteps;
                    }
                }
                // Handle valid movement commands.
                else if (move.equals("u") || move.equals("d") || move.equals("l") || move.equals("r")) {
                    String moveMessage = player.playerMovement(move, board);
                    System.out.println(moveMessage);
                }
                // Handle any other invalid command.
                else {
                    System.out.println("Invalid command. Type 'help' for instructions.");
                    continue; // Skip decrementing steps for an invalid command.
                }

                // Print the board (only if a valid move was processed)
                board.printBoard(player.getX(), player.getY());
                steps--;
            }


            // Determine game outcome
            if (steps == 0) {
                System.out.println("You ran out of steps! Game over!");
            } else if (player.getHealth() == 0) {
                System.out.println("Your health reached zero! Game over!");
            } else if (board.getGameBoardLevel() >= 3) {
                System.out.println("You exited the dungeon! You win!");

                // Add player to leaderboard and display it
                leaderboard.addEntry(player.getName(), player.getScore());
            }

            // Display leaderboard
            leaderboard.displayLeaderboard();

            System.out.println("Do you wish to retry? (yes/no):");
            retry = scanner.nextLine().toLowerCase();
        }

        System.out.println("Game Over!");
        scanner.close();
    }
}
