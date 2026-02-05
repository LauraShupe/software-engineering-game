import java.io.*;
import java.util.*;

import java.io.*;
import java.util.*;

public class UserManager
{
    private static final String USERS_FILE = "users.txt";      // username,password
    private static final String SCORES_FILE = "scores.txt";    // username,wave,resources

    /** Login a user */
    public static PlayerSession login(String username, String password)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return new PlayerSession(username, 0); // start fresh resources
                }
            }
        } catch (IOException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    /** Register a new user */
    public static boolean register(String username, String password)
    {
        if (userExists(username)) return false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }

        saveScore(username, 0, 0); // initialize score entry
        return true;
    }

    /** Save a player's wave and resources */
    public static void saveScore(String username, int wave, int resources)
    {
        Map<String, int[]> scores = new HashMap<>(); // username -> [wave, resources]

        // Load existing scores
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    scores.put(parts[0], new int[]{Integer.parseInt(parts[1]), Integer.parseInt(parts[2])});
                }
            }
        } catch (IOException e) {}

        // Save only if new wave is higher OR same wave but more resources
        int[] old = scores.getOrDefault(username, new int[]{0, 0});
        if (wave > old[0] || (wave == old[0] && resources > old[1])) {
            scores.put(username, new int[]{wave, resources});
        }

        // Write back
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE))) {
            for (Map.Entry<String,int[]> entry : scores.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue()[0] + "," + entry.getValue()[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving scores: " + e.getMessage());
        }
    }

    /** Get leaderboard sorted by wave descending, then resources descending */
    public static List<String> getLeaderboard()
    {
        List<Map.Entry<String,int[]>> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    list.add(new AbstractMap.SimpleEntry<>(parts[0], new int[]{Integer.parseInt(parts[1]), Integer.parseInt(parts[2])}));
                }
            }
        } catch (IOException e) {}

        // Sort descending by wave, then resources
        list.sort((a,b) -> {
            int cmp = b.getValue()[0] - a.getValue()[0];
            if (cmp == 0) cmp = b.getValue()[1] - a.getValue()[1];
            return cmp;
        });

        List<String> leaderboard = new ArrayList<>();
        for (Map.Entry<String,int[]> entry : list) {
            leaderboard.add(entry.getKey() + " – Wave: " + entry.getValue()[0] + ", Resources: " + entry.getValue()[1]);
        }
        return leaderboard;
    }

    /** Check if a username exists */
    private static boolean userExists(String username)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(username)) return true;
            }
        } catch (IOException e) {}
        return false;
    }
}


