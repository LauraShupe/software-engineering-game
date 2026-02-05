import greenfoot.*;

/**
 * Resource – collectible item in Stage 1.
 */
public class Resource extends Actor
{
    public Resource()
    {
        GreenfootImage img = new GreenfootImage("images/coin.png");
        img.scale(20, 20);
        setImage(img);
    }

    public void act()
    {
        checkCollection();
    }

    /**
     * Check if the player has touched this resource.
     * If so, collect it and remove from world.
     */
    private void checkCollection()
    {
        Player player = (Player) getOneIntersectingObject(Player.class);
        if (player != null)
        {
            // Tell the world to update resources via PlayerSession
            Stage1World world = (Stage1World) getWorld();
            world.collectResource();

            // Remove this resource from the world
            world.removeObject(this);
        }
    }
}
