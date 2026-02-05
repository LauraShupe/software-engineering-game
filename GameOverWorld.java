import greenfoot.*;
import java.util.*;

/**
 * GameOverWorld – displays when the game ends (Stage1 failure or Stage2 completion).
 */
public class GameOverWorld extends World
{
    public GameOverWorld(Stage2World stage2)
    {
        super(900, 500, 1);

        int finalResources = stage2.getResources();
        int finalWave = stage2.getCurrentWaveNumber();
        PlayerSession session = stage2.getSession();

        setupWorld(session, finalResources, finalWave);
    }

    public GameOverWorld(PlayerSession session, int waveNumber, int resources)
    {
        super(900, 500, 1);

        setupWorld(session, resources, waveNumber);
    }

    // Common method to set up world and leaderboard
    private void setupWorld(PlayerSession session, int resources, int waveNumber)
    {
        showText("GAME OVER", getWidth() / 2, 150);
        showText("Final Resources: " + resources, getWidth() / 2, 250);
        showText("Wave Reached: " + waveNumber, getWidth() / 2, 300);
        showText("Press R to restart or Q to quit", getWidth() / 2, 180);

        // Save current player's score if they collected anything
        if(resources > 0 || waveNumber > 0) {
            LeaderboardManager.addEntry(new LeaderboardEntry(session.getUsername(), resources, waveNumber));
        }

        // Display top 5 leaderboard entries
        List<LeaderboardEntry> top5 = LeaderboardManager.getTop(5);
        int y = 350;
        int rank = 1;
        for(LeaderboardEntry entry : top5) {
            showText(rank + ". " + entry.getUsername() + " - Resources: " + entry.getResources() 
                     + ", Wave: " + entry.getWave(), getWidth()/2, y);
            y += 30;
            rank++;
        }
    }

    public void act()
    {
        if (Greenfoot.isKeyDown("r"))
        {
            // Reset to login or Stage1 with fresh PlayerSession
            Greenfoot.setWorld(new LoginWorld());
        }
        else if (Greenfoot.isKeyDown("q"))
        {
            Greenfoot.stop();
        }
    }
}
