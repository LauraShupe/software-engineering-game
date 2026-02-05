import greenfoot.*;
//import java.awt.Color;

/**
 * Wall – maze obstacle for Stage 1.
 */
public class Wall extends Actor
{
    private static final int CELL_SIZE = 40;

    public Wall()
    {
        GreenfootImage img = new GreenfootImage(CELL_SIZE, CELL_SIZE);
        img.setColor(Color.DARK_GRAY);
        img.fillRect(0, 0, CELL_SIZE, CELL_SIZE);
        setImage(img);
    }
}
