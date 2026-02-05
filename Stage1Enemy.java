import greenfoot.*;
import java.util.*;

/**
 * Stage1Enemy – moves randomly in the maze and damages player on contact.
 */
public class Stage1Enemy extends Actor
{
    private int dx = 0; // horizontal movement per act
    private int dy = 0; // vertical movement per act
    private int speed = 2; // enemy speed

    public Stage1Enemy() {
        // Set enemy image
        GreenfootImage img = new GreenfootImage("enemy.png");
        img.scale(30, 30);
        setImage(img);

        pickNewDirection();
    }

    public void act() {
        move(dx, dy);

        // Bounce off walls
        if (isTouching(Wall.class)) {
            move(-dx, -dy);
            pickNewDirection();
        }

        // Check collision with Player
        Player player = (Player) getOneIntersectingObject(Player.class);
        if (player != null) {
            Stage1World world = (Stage1World) getWorld();
            if (world != null) {
                world.playerHit(); // Player loses or stage ends
            }
        }
    }

    private void move(int xAmount, int yAmount) {
        setLocation(getX() + xAmount, getY() + yAmount);
    }

    private void pickNewDirection() {
        List<int[]> directions = Arrays.asList(
            new int[]{1, 0},   // right
            new int[]{-1, 0},  // left
            new int[]{0, 1},   // down
            new int[]{0, -1}   // up
        );
        int[] dir = directions.get(Greenfoot.getRandomNumber(directions.size()));
        dx = dir[0] * speed;
        dy = dir[1] * speed;
    }
}
