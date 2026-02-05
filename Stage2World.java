import greenfoot.*;

/**
 * Stage2World – tower defense stage.
 * Handles towers, enemies, base, resources, and waves.
 */
public class Stage2World extends World
{
    //private int resources;
    private PlayerSession session;

    
    private DefenseTile[][] tiles;
    private int lanes = 5;
    private int columns = 8;
    private int tileSize = 80;

    private WaveManager waveManager;
    private Base base;
    
    private int selectedDefense = 1; 
    // 1 = Tower
    // 2 = Fortification
    // 3 = GoldMine
    

    public Stage2World(PlayerSession session)
    {
        super(900, 500, 1); 

        //this.resources = session.getResources(); // start with collected resources (OLD)
        this.session = session;
        
        GreenfootImage bg = new GreenfootImage("Stage2BG.jpg");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);

        
        // Start Stage 2 background music
        MusicManager.playMusic("Stage2Music.mp3", 70);

        prepareGrid();
        spawnBase();
        waveManager = new WaveManager(this);

        showHUD();
    }

    public void act()
    {
        // --- Update selected defense first ---
        if (Greenfoot.isKeyDown("1")) 
            selectedDefense = 1; // Tower
        if (Greenfoot.isKeyDown("2"))
            selectedDefense = 2; // Fortification
        if (Greenfoot.isKeyDown("3")) 
            selectedDefense = 3; // GoldMine
        
        // Update waves
        waveManager.update();

        // Update HUD
        showHUD();
    }

    /** Prepare the tower placement grid */
    private void prepareGrid()
    {
        tiles = new DefenseTile[lanes][columns];
        int startX = 100;
        int startY = 60;

        for (int row = 0; row < lanes; row++)
        {
            for (int col = 0; col < columns; col++)
            {
                DefenseTile t = new DefenseTile();
                addObject(t, startX + col * tileSize, startY + row * tileSize);
                tiles[row][col] = t;
            }
        }
    }

    /** Spawn the player base on the left */
    private void spawnBase()
    {
        base = new Base();
        addObject(base, 50, getHeight() / 2);
    }

    /** Place a tower on a tile */
    public void placeDefense(DefenseTile tile)
    {
            // Do nothing if the tile already has a defense
        if (tile.hasDefense()) return;

        Actor defense = null;
        int cost = 0;

        // Determine which defense to place
        switch (selectedDefense) {
            case 1: // Tower
                defense = new Tower();
                cost = 2;
                break;
            case 2: // Fortification (like Walnut)
                defense = new Fortification();
                cost = 1;
                break;
            case 3: // GoldMine (like Sunflower)
                defense = new GoldMine();
                cost = 3;
                break;
                
            default:
                return; // invalid selection, do nothing
        }

        // Check if player has enough resources in PlayerSession
        if (defense != null && session.getResources() >= cost)
        {
            // Place defense on the tile
            addObject(defense, tile.getX(), tile.getY());
            tile.setDefense(defense);
    
            // Deduct resources from the session
            session.spendResources(cost);
        }

    }


    /** Add resources (from killing enemies) */
    public void addResources(int r)
    {
        session.addResources(r);
    }

    /** Get the base for enemies to move toward */
    public Base getBase()
    {
        return base;
    }

    /** Called by Base when destroyed */
    public void baseDestroyed()
    {
        waveManager.stopWaves();  // stop spawning
        
        // Stop background music
        MusicManager.stopMusic();

        // Play Game Over sound once
        MusicManager.playSFX("gameover.wav", 80);
        
        Greenfoot.setWorld(new GameOverWorld(this));
    }

    /** Spawn a new enemy in a lane */
    public void spawnEnemy(int lane, int wave)
    {
        Actor enemy;

        if (wave < 2)
            enemy = new BasicEnemy();
        else if (wave < 4)
            enemy = Greenfoot.getRandomNumber(2) == 0 ? 
                    new FastEnemy() : new BasicEnemy();
        else
            enemy = Greenfoot.getRandomNumber(2) == 0 ? 
                    new TankEnemy() : new BruteEnemy();

        addObject(enemy, getWidth() - 20, 60 + lane * tileSize);
    }


    /** Show HUD (resources, wave number, base health) */
    public void showHUD()
    {
        //showText("Resources: " + resources, 780, 30);
        showText("Resources: " + session.getResources(), 780, 30);
        showText("Wave: " + waveManager.getWaveNumber(), 780, 60);

        if (base != null)
            showText("Base HP: " + base.getHealth(), 780, 90);
            
        // Show selected defense
        String selectedText = "";
        switch(selectedDefense)
        {
            case 1: selectedText = "Tower (2 coins)"; break;
            case 2: selectedText = "Fortification (1 coin)"; break;
            case 3: selectedText = "GoldMine (3 coins)"; break;
        }
        showText("Selected: " + selectedText, 780, 120);
        
        // Show instructions
        showText("Press 1=Tower, 2=Fortification, 3=GoldMine, then click to place", 450, 480);
    }

    //public int getResources() { return resources; }
    public int getResources() 
    { 
        return session.getResources();
    }

    
    public int getCurrentWaveNumber() {
        return waveManager.getWaveNumber();
    }
    
    public PlayerSession getSession()
    {
        return session;
    }
    
    public int getLaneCount()
    {
        return 5;
    }

    public void victory()
    {
        //waveManager.stopWaves(); // could mess it up

        // Stop background music
        MusicManager.stopMusic();

        // Play Victory sound once
        MusicManager.playSFX("victory.wav", 80);

        // add victory audio
        Greenfoot.setWorld(new GameOverWorld(this));
    }
    
    //public int calculateFinalScore() // formula: total score = resources - missingHP * 3, make sure not 0

}


