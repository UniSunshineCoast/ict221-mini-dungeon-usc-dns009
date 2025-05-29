package dungeon.engine;

/**
 * Represents an entry in the leaderboard, containing a player's name, score, and date.
 */
public class LeaderboardEntry {
    private final String name;
    private final int score;
    private final String date;

    /**
     * Constructs a leaderboard entry with player details.
     * @param name  The player's name.
     * @param score The player's score.
     * @param date  The date of the entry.
     */
    public LeaderboardEntry(String name, int score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    /**
     * Gets the player's name.
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's score.
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the date of the entry.
     * @return The date as a string.
     */
    public String getDate() {
        return date;
    }
}
