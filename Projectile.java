import greenfoot.*;

/**
 * Projectile – straight-line PvZ-style projectile.
 */
public class Projectile extends Actor
{
    private int speed = 7;
    private int damage = 1;

    public Projectile()
    {
        /*
        GreenfootImage img = new GreenfootImage(10, 10);
        img.setColor(Color.YELLOW);
        img.fillOval(0, 0, 10, 10);
        */
        
        // Load arrow image instead of yellow ball
        GreenfootImage img = new GreenfootImage("arrow.png"); // place arrow.png in sounds/images folder
        img.scale(40, 10);  // scale as needed
        setImage(img);
        
        setImage(img);
    }

    public void act()
    {
        // ✅ MOVE STRAIGHT RIGHT ONLY
        setLocation(getX() + speed, getY());

        // ✅ Check collision with enemies
        Stage2Enemy hit = (Stage2Enemy)getOneIntersectingObject(Stage2Enemy.class);
        if (hit != null)
        {
            hit.takeDamage(damage);
            getWorld().removeObject(this);
            return;
        }

        // ✅ Remove if off-screen
        if (getX() > getWorld().getWidth())
        {
            getWorld().removeObject(this);
        }
    }
}
