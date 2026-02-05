import greenfoot.*;

/**
 * Player - controlled by arrow keys.
 * Collects resources and avoids walls.
 */
public class Player extends Actor
{
    private int speed = 2;

    public Player() {
        GreenfootImage img = new GreenfootImage("player.png");
        img.scale(30, 30); // scale to fit maze
        setImage(img);
    }

    public void act() {
        handleMovement();
        checkResourceCollision();
        checkEnemyCollision();
    }

    /**
     * Move the player using arrow keys, avoid walls.
     */
    private void handleMovement() {
        int dx = 0;
        int dy = 0;

        if (Greenfoot.isKeyDown("left"))  dx = -speed;
        if (Greenfoot.isKeyDown("right")) dx = speed;
        if (Greenfoot.isKeyDown("up"))    dy = -speed;
        if (Greenfoot.isKeyDown("down"))  dy = speed;

        if (canMove(dx, dy)) {
            setLocation(getX() + dx, getY() + dy);
        }
    }

    /**
     * Check if moving dx, dy would collide with walls.
     */
    private boolean canMove(int dx, int dy) {
        setLocation(getX() + dx, getY() + dy);
        boolean collides = isTouching(Wall.class);
        setLocation(getX() - dx, getY() - dy);
        return !collides;
    }

    /**
     * Check if player is touching a Resource. If so, collect it.
     */
    private void checkResourceCollision() {
        Resource resource = (Resource)getOneIntersectingObject(Resource.class);
        if (resource != null) {
            Stage1World world = (Stage1World)getWorld();
            world.collectResource(); // updates PlayerSession
            world.removeObject(resource);
        }
    }

    /**
     * Check if player is hit by Stage1Enemy.
     */
    private void checkEnemyCollision() {
        Stage1Enemy enemy = (Stage1Enemy)getOneIntersectingObject(Stage1Enemy.class);
        if (enemy != null) {
            Stage1World world = (Stage1World)getWorld();
            world.playerHit();
        }
    }
}

