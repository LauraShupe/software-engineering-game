import greenfoot.*;

/**
 * GoldMine – generates resources over time (Sunflower-style).
 */
public class GoldMine extends Actor
{
    private int generationCooldown = 180; // frames between resource gains (~3 seconds)
    private int timer = 0;
    private int generationAmount = 1;     // how much it gives per cycle

    public GoldMine()
    {
        GreenfootImage img = new GreenfootImage("goldmine.png"); 
        img.scale(50, 50);
        setImage(img);
    }

    public void act()
    {
        timer++;

        if (timer >= generationCooldown)
        {
            generateResources();
            timer = 0;
        }
    }

    private void generateResources()
    {
        Stage2World world = (Stage2World)getWorld();
        if (world != null)
        {
            world.addResources(generationAmount);
        }
    }
}

