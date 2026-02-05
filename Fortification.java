import greenfoot.*;

/**
 * Fortification – blocks enemies and absorbs damage (Walnut-style).
 */
public class Fortification extends Actor
{
    private int health = 25;   // tougher than enemies
    private int maxHealth = 25;

    public Fortification()
    {
        GreenfootImage img = new GreenfootImage("fortification.png"); 
        img.scale(50, 50);
        setImage(img);
    }

    /** Called by enemies when attacking */
    public void takeDamage(int amount)
    {
        health -= amount;

        // Optional: visual damage feedback
        int brightness = Math.max(50, 255 * health / maxHealth);
        getImage().setTransparency(brightness);

        if (health <= 0)
        {
            getWorld().removeObject(this);
        }
    }
}

