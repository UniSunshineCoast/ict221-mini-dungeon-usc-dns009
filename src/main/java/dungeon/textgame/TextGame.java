package dungeon.textgame;

import java.util.Scanner;

public class TextGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String retry = "yes";
        Leaderboard leaderboard = new Leaderboard();

        while (retry.equals("yes")) {
            // Game introduction
            int steps = 100;
            System.out.println("Welcome to the Dungeon Crawler! What is your name? ");
            String playerName = scanner.nextLine();
            System.out.println("How to play: Move with u (up), d (down), l (left), r (right), save (to save the game), load (to load a saved game), or q to quit.");

            // Ask for difficulty level
            System.out.println("Choose difficulty level: (provide a number between 1 and 5)");
            int difficulty = scanner.nextInt();
            scanner.nextLine();

            // Check if difficulty is in bounds, if not set to default of 3
            if (difficulty < 1 || difficulty > 5) {
                System.out.println("Invalid difficulty. Difficulty will be set to default: 3");
                difficulty = 3;
            }

            // Initialize player and board
            Player player = new Player(playerName, 10, 0, 1, 1);
            GameBoard board = new GameBoard(5, 5, 2, 3, difficulty, 1, difficulty);


            // Print initial board state
            board.printBoard(player.getx(), player.gety());

            // Game loop
            while (steps > 0 && board.getGameBoardLevel() < 3 && player.getHealth() != 0) {
                System.out.println("Make your move. Health " + player.getHealth() + ", Score: " + player.getScore() + ". Steps remaining: " + steps + ". Type help for instructions.");
                String move = scanner.nextLine().toLowerCase();

                // Handle a quit command
                if (move.equals("q")) {
                    break;
                }

                // Handle a save or load command
                if (move.equals("save")) {
                    GameSave.saveGame(player, board, steps); // Include steps!
                } else if (move.equals("load")) {
                    int loadedSteps = GameSave.loadGame(board, player);
                    if (loadedSteps != -1) {
                        steps = loadedSteps; // Restore steps count!
                    }
                }


                // Move player
                player.playerMovement(move, board);
                board.printBoard(player.getx(), player.gety());
                steps--;
            }

            if (steps == 0) {
                System.out.println("You ran out of steps! Game over!");
            } else if (player.getHealth() == 0) {
                System.out.println("You're health reached zero! Game over!");
            } else if (board.getGameBoardLevel() >=3) {
                System.out.println("You exited the dungeon! you win!");

                // Add player to leaderboard and display it
                leaderboard.addEntry(player.getName(), player.getScore());
            }

        // Display leaderboard
        leaderboard.displayLeaderboard();

        System.out.println("Do you wish to retry? (yes/no) ");
        retry = scanner.nextLine().toLowerCase();

        }

        System.out.println("Game Over!");
        scanner.close();
    }





}

