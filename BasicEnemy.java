import greenfoot.*;

public class BasicEnemy extends Stage2Enemy
{
    public BasicEnemy()
    {
        super(2, 5);

        GreenfootImage img = new GreenfootImage("enemy.png");
        img.scale(40, 40);
        setImage(img);
    }
}

