package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.Leaderboard;
import dungeon.engine.LeaderboardEntry;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Unit tests for the Leaderboard class.
 */
public class TestLeaderboard {

    /**
     * Tests whether adding entries correctly maintains the leaderboard order,
     * enforces a maximum of five entries, and clears the leaderboard before and after the test.
     */
    @Test
    public void testAddEntry() {
        Leaderboard leaderboard = new Leaderboard();

        // Clear leaderboard before starting the test to ensure a fresh start
        leaderboard.clearEntries();

        // Add entries with the requested scores
        leaderboard.addEntry("Alice", 15);
        leaderboard.addEntry("Frank", 12);
        leaderboard.addEntry("Bob", 10);
        leaderboard.addEntry("Charlie", 8);
        leaderboard.addEntry("Dave", 6);
        leaderboard.addEntry("Eve", 20); // New entry that should be placed accordingly

        List<LeaderboardEntry> entries = leaderboard.getEntries(); // Direct access for testing purposes

        // Ensure Eve's highest score is first
        assertEquals("Eve", entries.get(0).getName(), "Highest score should remain at the top.");

        // Ensure Alice's score is correctly placed in second position
        assertEquals("Alice", entries.get(1).getName(), "Alice's score should be second place.");

        // Ensure the leaderboard only stores the top five scores
        assertEquals(5, entries.size(), "Leaderboard should maintain a maximum of five entries.");

        // **Clear leaderboard after the test to ensure clean data for gameplay**
        leaderboard.clearEntries();
        assertTrue(leaderboard.getEntries().isEmpty(), "Leaderboard should be empty after test execution.");
    }
}
