import greenfoot.*;

public class Stage2Enemy extends Actor
{
    protected int speed;
    protected int health;
    protected int damage = 1;
    private int attackCooldown = 0;


    public Stage2Enemy(int speed, int health)
    {
        this.speed = speed;
        this.health = health;
    }

    public void act()
    {
        Stage2World world = (Stage2World)getWorld();
        if (world == null) return;

        // 1. Check for fortification in front of enemy
        Fortification fort = (Fortification)getOneIntersectingObject(Fortification.class);

        if (fort != null)
        {
            // Stop moving and attack fortification
            attackCooldown++;
            if (attackCooldown >= 30) // attack every 30 frames
            {
                fort.takeDamage(1);
                attackCooldown = 0;
            }
            return; // IMPORTANT: do not move while attacking
        }

        // 2. Move ONLY LEFT in lane
        setLocation(getX() - speed, getY());
    
        // 3. If enemy reaches the base, attack it
        Base base = world.getBase();
        if (base != null && getX() <= base.getX() + 20)
        {
            base.takeDamage(1);
            world.removeObject(this);
        }
    }
    

    public void takeDamage(int amount)
    {
        health -= amount;
        if (health <= 0)
        {
            Stage2World world = (Stage2World)getWorld();
            if (world != null)
            {
                world.removeObject(this);
                world.addResources(1);
            }
        }
    }
}

