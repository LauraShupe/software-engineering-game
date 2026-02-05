import greenfoot.*;

public class BruteEnemy extends Stage2Enemy
{
    public BruteEnemy()
    {
        super(2, 10);
        damage = 3;

        GreenfootImage img = new GreenfootImage("brute_enemy.png");
        img.scale(45, 45);
        setImage(img);
    }
}

