package dungeon.textgame;

public class LeaderboardEntry {
    private final String name;
    private final int score;
    private final String date;

    public LeaderboardEntry(String name, int score, String date) {
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public String getDate() { return date; }
}