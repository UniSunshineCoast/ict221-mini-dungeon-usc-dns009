package dungeon.engine;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Manages the leaderboard, storing the top player scores.
 */
public class Leaderboard {
    private static final String FILE_PATH = "leaderboard.txt";
    private List<LeaderboardEntry> entries;

    /**
     * Constructs a leaderboard and loads existing entries.
     */
    public Leaderboard() {
        entries = new ArrayList<>();
        loadLeaderboard();
    }

    /**
     * Loads leaderboard entries from a file.
     */
    private void loadLeaderboard() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    String date = parts[2].trim();
                    entries.add(new LeaderboardEntry(name, score, date));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing leaderboard found, creating a new one.");
        }
    }

    /**
     * Adds a new player entry to the leaderboard.
     * @param playerName The player's name.
     * @param score      The player's score.
     */
    public void addEntry(String playerName, int score) {
        String date = LocalDate.now().toString();
        entries.add(new LeaderboardEntry(playerName, score, date));

        // Sort entries by score, highest first
        entries.sort(Comparator.comparingInt(LeaderboardEntry::getScore).reversed());

        // Keep only the top 5 scores
        if (entries.size() > 5) {
            entries = entries.subList(0, 5);
        }

        saveLeaderboard();
    }

    /**
     * Clears all entries from the leaderboard.
     * This method resets the leaderboard.
     * Used after the leaderboard unit test to make sure it doesn't interfere
     * with the game's leaderboard.
     */
    public void clearEntries() {
        entries.clear(); // Removes all stored leaderboard entries
    }

    /**
     * Saves the leaderboard to a file.
     */
    private void saveLeaderboard() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            int limit = Math.min(entries.size(), 5); // Only save up to 5 entries
            for (int i = 0; i < limit; i++) {
                LeaderboardEntry entry = entries.get(i);
                writer.write(entry.getName() + "," + entry.getScore() + "," + entry.getDate());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving leaderboard: " + e.getMessage());
        }
    }

    /**
     * Displays the leaderboard entries in the console.
     */
    public void displayLeaderboard() {
        System.out.println("\n--- Leaderboard ---");
        int rank = 1;
        for (LeaderboardEntry entry : entries) {
            System.out.println("#" + rank + " " + entry.getName() + ", " + entry.getScore() + ", " + entry.getDate());
            rank++;
        }
        System.out.println("-------------------\n");
    }

    /**
     * Retrieves a copy of the leaderboard entries.
     * @return A list of the top leaderboard entries.
     */
    public List<LeaderboardEntry> getEntries() {
        return new ArrayList<>(entries); // Return a copy to prevent unintended modifications
    }
}
