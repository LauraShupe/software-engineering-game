import greenfoot.*;

/**
 * DefenseTile – a tile in Stage2World where any defense can be placed.
 */
public class DefenseTile extends Actor
{
    private Actor defense; // can be Tower, Fortification, GoldMine, etc.

    public DefenseTile()
    {
        GreenfootImage img = new GreenfootImage(70, 70);
        img.setColor(new Color(200, 200, 200, 100));
        img.fillRect(0, 0, 70, 70);
        setImage(img);
    }

    /** Set a defense on this tile */
    public void setDefense(Actor d)
    {
        defense = d;
    }

    /** Check if any defense exists on this tile */
    public boolean hasDefense()
    {
        return defense != null;
    }

    public Actor getDefense()
    {
        return defense;
    }

    public void act()
    {
        // ✅ AUTO-CLEAR DESTROYED DEFENSE
        if (defense != null && defense.getWorld() == null)
        {   
            defense = null;
        }
    
        // Highlight on hover
        if (Greenfoot.mouseMoved(this))
        {
            getImage().setColor(new Color(150, 150, 255, 150));
            getImage().fillRect(0, 0, 70, 70);
        }
        else if (Greenfoot.mouseMoved(null))
        {
            getImage().setColor(new Color(200, 200, 200, 100));
            getImage().fillRect(0, 0, 70, 70);
        }

        // Place defense on click
        if (Greenfoot.mouseClicked(this) && !hasDefense())
        {
            Stage2World world = (Stage2World)getWorld();
            if (world != null)
            {
                world.placeDefense(this);
            }
        }
    }
}
