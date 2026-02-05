import java.io.*;
import java.util.*;

public class LeaderboardManager {
    private static final String FILE = "leaderboard.txt";

    // Save a new entry
    public static void addEntry(LeaderboardEntry entry) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {
            bw.write(entry.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get top N entries, including duplicates usernames
    public static List<LeaderboardEntry> getTop(int n) {
        List<LeaderboardEntry> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                LeaderboardEntry e = LeaderboardEntry.fromString(line);
                if(e != null) list.add(e);
            }
        } catch (IOException e) {
            // If file does not exist yet, just return empty list
        }

        Collections.sort(list); // uses compareTo in LeaderboardEntry
        if(list.size() < n) {
            // Fill with dummy entries
            int missing = n - list.size();
            for(int i=0;i<missing;i++) list.add(new LeaderboardEntry("-", 0, 0));
        }

        return list.subList(0, n);
    }
}

