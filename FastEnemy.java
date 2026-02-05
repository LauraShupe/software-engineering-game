import greenfoot.*;

public class FastEnemy extends Stage2Enemy
{
    public FastEnemy()
    {
        super(4, 3);

        GreenfootImage img = new GreenfootImage("fast_enemy.png");
        img.scale(40, 40);
        setImage(img);
    }
}

