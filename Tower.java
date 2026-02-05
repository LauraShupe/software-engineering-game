import greenfoot.*;

/**
 * Tower – placed on TowerTile, shoots projectiles at enemies in the SAME lane only.
 */
public class Tower extends Actor
{
    private int fireCooldown = 60;
    private int cooldownTimer = 0;
    //private int range = 250;
    private int laneTolerance = 10; // pixels for same-row check

    public Tower()
    {
        GreenfootImage img = new GreenfootImage("tower.png");
        img.scale(50, 50);
        setImage(img);
    }

    public void act()
    {
        cooldownTimer++;

        if (cooldownTimer >= fireCooldown)
        {
            Stage2Enemy target = findTargetInSameLane();
            if (target != null)
            {
                shoot();
                cooldownTimer = 0;
            }
        }
    }

    /**
     * Find the closest enemy in the SAME lane only.
     */
    private Stage2Enemy findTargetInSameLane()
    {
        Stage2Enemy closest = null;
        int closestX = Integer.MAX_VALUE;

        for (Object obj : getWorld().getObjects(Stage2Enemy.class))
        {
            Stage2Enemy enemy = (Stage2Enemy)obj;
    
            // Same lane only
            if (Math.abs(enemy.getY() - getY()) <= laneTolerance)
            {
                int dx = enemy.getX() - getX();

                // Enemy must be IN FRONT of the tower
                if (dx > 0 && enemy.getX() < closestX)
                {
                    closest = enemy;
                    closestX = enemy.getX();
                }
            }
        }
        return closest;
    }

    /**
     * Shoot straight forward.
     */
    private void shoot()
    {
        Projectile p = new Projectile();
        getWorld().addObject(p, getX() + 20, getY());
        
        // Play shooting sound
        Greenfoot.playSound("shoot.wav");
    }
}

