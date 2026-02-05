public class Leaderboard
{
    public static class ScoreEntry
    {
        public String username;
        public int resources;
        public int wave;

        public ScoreEntry(String username, int resources, int wave)
        {
            this.username = username;
            this.resources = resources;
            this.wave = wave;
        }
    }
}

