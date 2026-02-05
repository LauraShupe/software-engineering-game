import greenfoot.*;

/**
 * Base – the player’s base that enemies try to destroy.
 */
public class Base extends Actor
{
    private int health = 10;  // total health

    public Base()
    {
        GreenfootImage img = new GreenfootImage("base.png");
        img.scale(60, 60);
        setImage(img);
    }

    /**
     * Called when an enemy reaches the base.
     */
    public void takeDamage(int amount)
    {
        health -= amount;
        if (health <= 0)
        {
            health = 0;
            // Notify Stage2World that base is destroyed
            Stage2World world = (Stage2World)getWorld();
            if (world != null)
            {
                world.baseDestroyed();
            }
        }
    }

    public int getHealth()
    {
        return health;
    }
}
