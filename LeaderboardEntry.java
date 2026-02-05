public class LeaderboardEntry implements Comparable<LeaderboardEntry> {
    private String username;
    private int resources;
    private int wave;

    public LeaderboardEntry(String username, int resources, int wave) {
        this.username = username;
        this.resources = resources;
        this.wave = wave;
    }

    public String getUsername() { return username; }
    public int getResources() { return resources; }
    public int getWave() { return wave; }

    // Compare entries by combined score (resources + wave), descending
    public int compareTo(LeaderboardEntry other) {
        int score = this.resources + this.wave;
        int otherScore = other.resources + other.wave;
        return Integer.compare(otherScore, score); // descending
    }

    @Override
    public String toString() {
        return username + "," + resources + "," + wave;
    }

    // Parse from CSV line
    public static LeaderboardEntry fromString(String line) {
        String[] parts = line.split(",");
        if(parts.length != 3) return null;
        return new LeaderboardEntry(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }
}
