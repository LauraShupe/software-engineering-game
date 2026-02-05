import greenfoot.*;

/**
 * WaveManager – manages enemy waves in Stage2World.
 */
public class WaveManager
{
    private Stage2World world;
    private int waveNumber = 1;
    private int enemiesPerWave = 5;
    private int enemiesSpawned = 0;
    private int spawnInterval = 100; // ticks between spawns
    private int spawnTimer = 0;
    private boolean spawning = true;
    private int maxWaves = 5;


    public WaveManager(Stage2World world)
    {
        this.world = world;
    }

    /** Called every act() in Stage2World */
    public void update()
    {
        if (!spawning) return;

        spawnTimer++;

        // Spawn enemy if interval reached and still remaining for this wave
        if (spawnTimer >= spawnInterval && enemiesSpawned < enemiesPerWave)
        {
            spawnTimer = 0;
            int lane = Greenfoot.getRandomNumber(world.getLaneCount()); // pick random lane
            world.spawnEnemy(lane, waveNumber);
            enemiesSpawned++;
        }

        // If wave is complete (all enemies spawned and defeated), start next wave
        if (enemiesSpawned >= enemiesPerWave && world.getObjects(Stage2Enemy.class).isEmpty())
        {
            startNextWave();
        }
    }

    /** Start next wave */
    private void startNextWave()
    {
      if (waveNumber >= maxWaves)
       {
        spawning = false;
        world.victory();   // You will add this to Stage2World
        return;
       }

      waveNumber++;
      enemiesPerWave += 2;
      enemiesSpawned = 0;
      spawnTimer = 0;
    }

    /** Stop spawning waves (e.g., base destroyed) */
    public void stopWaves()
    {
        spawning = false;
    }

    public int getWaveNumber()
    {
        return waveNumber;
    }
}
