import greenfoot.*;

public class TankEnemy extends Stage2Enemy
{
    public TankEnemy()
    {
        super(1, 20);

        GreenfootImage img = new GreenfootImage("tank_enemy.png");
        img.scale(50, 50); // bigger visual = stronger enemy
        setImage(img);
    }
}

