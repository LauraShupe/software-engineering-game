import greenfoot.*;
import java.util.*;

/**
 * Stage1World – First stage of Fortune & Fury.
 * Pac-Man style resource-gathering maze stage.
 */
public class Stage1World extends World
{
    private final int cellSize = 40;
    private PlayerSession session;

    public Stage1World(PlayerSession session)
    {
        super(600, 440, 1);
        this.session = session;
        
        session.setResources(0);
        
        // Set Background
        GreenfootImage bg = new GreenfootImage("Stage1BG.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);


        
        // Start Stage 1 music
        MusicManager.playMusic("Stage1Music.wav", 70);

        prepareMaze();
        addPlayer();
        addEnemies();
        addResources(10);

        // Show player info
        showText("Player: " + session.getUsername(), 80, 40);
        showText("Resources: " + session.getResources(), 80, 20);
    }

    private void prepareMaze()
    {
        int[][] maze = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,1,0,0,0,0,0,1,0,0,0,1},
            {1,0,1,0,1,0,1,1,1,0,1,0,1,0,1},
            {1,0,1,0,0,0,0,0,1,0,0,0,1,0,1},
            {1,0,1,1,1,0,1,0,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,1,0,0,0,0,0,1,0,1},
            {1,1,1,1,1,0,1,1,1,1,1,0,1,0,1},
            {1,0,0,0,1,0,0,0,0,0,1,0,0,0,1},
            {1,0,1,0,1,1,1,1,1,0,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };

        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[y].length; x++) {
                if (maze[y][x] == 1) {
                    addObject(new Wall(), x * cellSize + cellSize/2, y * cellSize + cellSize/2);
                }
            }
        }
    }

    private void addPlayer()
    {
        addObject(new Player(), 1 * cellSize + cellSize/2, 1 * cellSize + cellSize/2);
    }

    private void addEnemies()
    {
        addObject(new Stage1Enemy(), 13 * cellSize + cellSize/2, 1 * cellSize + cellSize/2);
        addObject(new Stage1Enemy(), 13 * cellSize + cellSize/2, 9 * cellSize + cellSize/2);
    }

    private void addResources(int count)
    {
        Random rand = new Random();
        int placed = 0;

        int[][] maze = getMazeArray(); // helper to get maze layout

        while (placed < count) {
            int x = rand.nextInt(maze[0].length);
            int y = rand.nextInt(maze.length);

            boolean isWall = maze[y][x] == 1;
            boolean isOccupied = !getObjectsAt(x * cellSize + cellSize/2, y * cellSize + cellSize/2, Actor.class).isEmpty();

            if (!isWall && !isOccupied) {
                addObject(new Resource(), x * cellSize + cellSize/2, y * cellSize + cellSize/2);
                placed++;
            }
        }
    }

    private int[][] getMazeArray()
    {
        // Reuse the maze layout
        return new int[][] {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,1,0,0,0,0,0,1,0,0,0,1},
            {1,0,1,0,1,0,1,1,1,0,1,0,1,0,1},
            {1,0,1,0,0,0,0,0,1,0,0,0,1,0,1},
            {1,0,1,1,1,0,1,0,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,1,0,0,0,0,0,1,0,1},
            {1,1,1,1,1,0,1,1,1,1,1,0,1,0,1},
            {1,0,0,0,1,0,0,0,0,0,1,0,0,0,1},
            {1,0,1,0,1,1,1,1,1,0,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
    }

    /**
     * Called by Player when a resource is collected.
     */
    public void collectResource()
    {
        session.addResources(1);
        showText("Resources: " + session.getResources(), 80, 20);

        // Play resource collection sound
        MusicManager.playSFX("collect.wav", 80);
        
        // If all resources collected
        if (session.getResources() >= 10) 
        {
            MusicManager.stopMusic();
            MusicManager.playSFX("victory.wav", 80);
            
            // Reward Player with 3 more coins for collecting all the resources
            session.addResources(3);
            
            // Show a victory message
            showText("Congradulations, you collected all the coins. You get +3 bonus coins as wellStage 2 Incoming!", getWidth()/2, getHeight()/2);
            
            // Show a transition message
            showText("Stage 2 Incoming!", getWidth()/2 + 40, getHeight()/2 + 40);
            Greenfoot.delay(100); // Adjust delay length
            
            // Transition to Stage2World
            Greenfoot.setWorld(new Stage2World(session));
        }
    }

    /**
     * Called by Player when hit by an enemy.
     */
    public void playerHit()
    {
        
        // Play player hit SFX
        MusicManager.playSFX("playerhit.mp3", 80);

        Greenfoot.delay(3);
        // End stage and go to Stage2 (preserve session)
        MusicManager.stopMusic();
        
        // Check if Player hasn't collect any resources -> if so then they can't transition to stage 2
        // Check if the player has collected at least 1 resource
        if(session.getResources() < 1)
        {
            showText("You must collect at least 1 coin to advance!", getWidth() / 2, getHeight() / 2);
            Greenfoot.delay(120); // show message for a few seconds
            showText("", getWidth() / 2, getHeight() / 2); // clear message
            
            // Player failed stage — go to GameOverWorld
            Greenfoot.setWorld(new GameOverWorld(session, 0, 0)); // 0 waves, 0 resources
            return;
        }
        
        // Show a transition message
        showText("Stage 2 Incoming!", getWidth()/2, getHeight()/2);
        Greenfoot.delay(100); // Adjust delay length
        
        // Proceed to Stage 2
        Greenfoot.setWorld(new Stage2World(session));
    }
}
