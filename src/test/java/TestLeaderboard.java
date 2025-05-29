package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.Leaderboard;
import dungeon.engine.LeaderboardEntry;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestLeaderboard {

    @Test
    public void testAddEntry() {
        Leaderboard leaderboard = new Leaderboard();

        // Add five entries to simulate a full leaderboard
        leaderboard.addEntry("Alice", 100);
        leaderboard.addEntry("Bob", 80);
        leaderboard.addEntry("Charlie", 60);
        leaderboard.addEntry("Dave", 40);
        leaderboard.addEntry("Eve", 20);

        // Add a new high score
        leaderboard.addEntry("Frank", 90);

        List<LeaderboardEntry> entries = leaderboard.getEntries(); // Direct access for testing purposes

        // Ensure the highest score is first
        assertEquals("Alice", entries.get(0).getName(), "Highest score should remain at the top.");

        // Ensure Frank's 90 score is placed correctly
        assertEquals("Frank", entries.get(1).getName(), "Frank's score should be second place.");

        // Ensure only five entries exist
        assertEquals(5, entries.size(), "Leaderboard should only store the top 5 scores.");
    }
}
