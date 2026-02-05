import greenfoot.*;

/**
 * LoginWorld – clean, clickable login/registration screen.
 */
public class LoginWorld extends World
{
    private String username = "";
    private String password = "";
    private boolean usernameSelected = true; // which field is active
    private boolean registering = false;     // toggle login/register
    private String feedback = "";

    private final int boxWidth = 300;
    private final int boxHeight = 40;
    private final int startY = 200;

    public LoginWorld()
    {
        super(900, 500, 1);
        drawScreen();
    }

    public void act()
    {
        handleMouseClicks();
        handleTyping();
        handleModeToggle();
        handleSubmit();
        drawScreen();
    }

    /** Draw UI elements */
    private void drawScreen()
    {
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        bg.setColor(Color.WHITE);
        bg.fill();
        setBackground(bg);

        // Draw labels
        drawText("Login System", getWidth()/2, 100, 40, Color.BLACK);

        // Draw username box
        drawBox(getWidth()/2, startY, boxWidth, boxHeight, usernameSelected ? Color.LIGHT_GRAY : Color.GRAY);
        drawText("Username: " + username, getWidth()/2, startY, 20, Color.BLACK);

        // Draw password box
        drawBox(getWidth()/2, startY + 60, boxWidth, boxHeight, !usernameSelected ? Color.LIGHT_GRAY : Color.GRAY);
        drawText("Password: " + "*".repeat(password.length()), getWidth()/2, startY + 60, 20, Color.BLACK);

        // Draw buttons
        drawBox(getWidth()/2 - 100, startY + 140, 100, 40, Color.GREEN);
        drawText("Login", getWidth()/2 - 100, startY + 140, 20, Color.BLACK);

        drawBox(getWidth()/2 + 100, startY + 140, 100, 40, Color.CYAN);
        drawText("Register", getWidth()/2 + 100, startY + 140, 20, Color.BLACK);

        // Feedback message
        drawText(feedback, getWidth()/2, startY + 200, 20, Color.RED);

        // Mode indicator
        drawText(registering ? "Register Mode" : "Login Mode", getWidth()/2, startY - 40, 20, Color.BLUE);
    }

    /** Draw a box */
    private void drawBox(int x, int y, int w, int h, Color color)
    {
        GreenfootImage img = getBackground();
        img.setColor(color);
        img.fillRect(x - w/2, y - h/2, w, h);
    }

    /** Draw text centered */
    private void drawText(String text, int x, int y, int size, Color color)
    {
        GreenfootImage img = getBackground();
        GreenfootImage textImg = new GreenfootImage(text, size, color, new Color(0,0,0,0));
        img.drawImage(textImg, x - textImg.getWidth()/2, y - textImg.getHeight()/2);
    }

    /** Handle clicking on fields or buttons */
    private void handleMouseClicks()
    {
        if (Greenfoot.mouseClicked(null))
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            int mx = mouse.getX();
            int my = mouse.getY();

            // Username box
            if (mx >= getWidth()/2 - boxWidth/2 && mx <= getWidth()/2 + boxWidth/2 &&
                my >= startY - boxHeight/2 && my <= startY + boxHeight/2)
            {
                usernameSelected = true;
            }

            // Password box
            if (mx >= getWidth()/2 - boxWidth/2 && mx <= getWidth()/2 + boxWidth/2 &&
                my >= startY + 60 - boxHeight/2 && my <= startY + 60 + boxHeight/2)
            {
                usernameSelected = false;
            }

            // Login button
            if (mx >= getWidth()/2 - 150 && mx <= getWidth()/2 - 50 &&
                my >= startY + 120 && my <= startY + 160)
            {
                registering = false;
                attemptSubmit();
            }

            // Register button
            if (mx >= getWidth()/2 + 50 && mx <= getWidth()/2 + 150 &&
                my >= startY + 120 && my <= startY + 160)
            {
                registering = true;
                attemptSubmit();
            }
        }
    }

    /** Handle typing letters, numbers, backspace */
    private void handleTyping()
    {
        for (char c = 'A'; c <= 'Z'; c++) addChar(c);
        for (char c = '0'; c <= '9'; c++) addChar(c);
        addChar('_');

        if (Greenfoot.isKeyDown("backspace"))
        {
            if (usernameSelected && username.length() > 0) username = username.substring(0, username.length()-1);
            else if (!usernameSelected && password.length() > 0) password = password.substring(0, password.length()-1);
            Greenfoot.delay(5);
        }
    }

    /** Add character */
    private void addChar(char c)
    {
        if (Greenfoot.isKeyDown(String.valueOf(c)))
        {
            if (usernameSelected) username += c;
            else password += c;
            Greenfoot.delay(5);
        }
    }

    /** Toggle mode with keys L/R */
    private void handleModeToggle()
    {
        if (Greenfoot.isKeyDown("l")) { registering = false; Greenfoot.delay(10); }
        if (Greenfoot.isKeyDown("r")) { registering = true; Greenfoot.delay(10); }
    }

    /** Submit using ENTER key */
    private void handleSubmit()
    {
        if (Greenfoot.isKeyDown("enter")) attemptSubmit();
    }

    /** Attempt login or registration */
    // Inside attemptSubmit() in LoginWorld

    private void attemptSubmit()
    {
        if (username.isEmpty() || password.isEmpty())
        {
            feedback = "Username and password cannot be empty";
            return;
        }

        PlayerSession session = null;
        if (!registering)
        {
            // LOGIN: always start fresh resources
            session = UserManager.login(username, password);
            if (session != null)
            {
                feedback = "";
                Greenfoot.setWorld(new Stage1World(session));
                return;
            }
            feedback = "Login failed. Check username/password.";
        }
        else
        {
            // REGISTER: also fresh session
            if (UserManager.register(username, password))
            {
                session = new PlayerSession(username, 0);
                feedback = "";
                Greenfoot.setWorld(new Stage1World(session));
                return;
            }
            feedback = "Registration failed. Username exists.";
        }

        // Reset fields for next attempt
        username = "";
        password = "";
        usernameSelected = true;
    }
    
}

