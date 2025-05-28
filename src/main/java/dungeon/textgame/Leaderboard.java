package dungeon.textgame;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Leaderboard {
    private static final String FILE_PATH = "leaderboard.txt";
    private List<LeaderboardEntry> entries;

    public Leaderboard() {
        entries = new ArrayList<>();
        loadLeaderboard();
    }

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

    public void addEntry(String playerName, int score) {
        String date = LocalDate.now().toString();
        entries.add(new LeaderboardEntry(playerName, score, date));

        // Sort entries by score, highest first
        entries.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        // Keep only the top 5 scores
        if (entries.size() > 5) {
            entries = entries.subList(0, 5);
        }

        saveLeaderboard();
    }


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


    public void displayLeaderboard() {
        System.out.println("\n--- Leaderboard ---");
        int rank = 1;
        for (LeaderboardEntry entry : entries) {
            System.out.println("#" + rank + " " + entry.getName() + ", " + entry.getScore() + ", " + entry.getDate());
            rank++;
        }
        System.out.println("-------------------\n");
    }

    public List<LeaderboardEntry> getEntries() {
        return new ArrayList<>(entries); // Return a copy to avoid unintended modifications
    }
}

