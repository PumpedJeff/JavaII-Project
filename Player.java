import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import java.util.*;

public class Player extends DrawableObject
{
    // player size
    private final int radius = 14;
    private final int diameter = radius * 2;

    // players forces
    private double forceX = 0;
    private double forceY = 0;

    // make keyReleased boolean for WASD
    private boolean W_released = true;
    private boolean A_released = true;
    private boolean S_released = true;
    private boolean D_released = true;

    //takes in its position
    public Player(float x, float y)
    {
        super(x,y);
    }

    //draws itself at the passed in x and y.
    public void drawMe(float x, float y, GraphicsContext gc)
    {
        gc.setFill(Color.LIME);
        gc.fillOval(x - radius,y - radius,diameter,diameter);
        gc.setFill(Color.GRAY);
        gc.fillOval(x - radius + 5,y - radius + 5,diameter - 10,diameter - 10);
    }

    public void act()
    {
        // add force to player
        this.setX(this.getX() + (float)this.getForceX());
        this.setY(this.getY() + (float)this.getForceY());

        if(W_released)
        {
            // reduce up velocity
            if(forceY < 0)
            {
                forceY = forceY + 0.025;
                if(forceY > 0)
                {
                    forceY = 0;
                }
            }
        }
        else
        {
            // move player is W is pressed
            this.moveUp();
        }
        if(S_released)
        {
            // reduce down velocity
            if(forceY > 0)
            {
                forceY = forceY - 0.025;
                // once forceY has gone below 0, make forceY 0
                if(forceY < 0)
                {
                    forceY = 0;
                }
            }
        }
        else
        {
            // move player is S is pressed
            this.moveDown();
        }
        if(A_released)
        {
            // reduce left velocity
            if(forceX < 0)
            {
                forceX = forceX + 0.025;
                if(forceX > 0)
                {
                    forceX = 0;
                }
            }
        }
        else
        {
            // move player is A is pressed
            this.moveLeft();
        }
        if(D_released) {
            // reduce right velocity
            if (forceX > 0) {
                forceX = forceX - 0.025;
                // once forceY has gone below 0, make forceY 0
                if (forceX < 0) {
                    forceX = 0;
                }
            }
        }
        else
        {
            // move player is D is pressed
            this.moveRight();
        }
    }

    // player movement
    public void moveDown()
    {
        forceY = Math.round((forceY + 0.1) * 10);
        forceY = forceY / 10;
        if(forceY > 5.0)
        {
            forceY -= 0.1;
        }
    }
    public void moveUp()
    {
        forceY = Math.round((forceY - 0.1) * 10);
        forceY = forceY / 10;
        if(forceY < -5.0)
        {
            forceY += 0.1;
        }
    }
    public void moveLeft()
    {
        forceX = Math.round((forceX - 0.1) * 10);
        forceX = forceX / 10;
        if(forceX < -5.0)
        {
            forceX += 0.1;
        }
    }
    public void moveRight()
    {
        forceX = Math.round((forceX + 0.1) * 10);
        forceX = forceX / 10;
        if(forceX > 5.0)
        {
            forceX -= 0.1;
        }
    }

    // collision detectors
    public boolean checkCollision(Mine m)
    {
        int collisionDistance = m.getRadius() + this.getRadius();
        // if the distance between the player and the mine are less than the collisionDistance, collide
        if(this.distance(m) <= collisionDistance)
        {
            // end game screen
            return true;
        }
        return false;
    }
    public boolean didCollide(ArrayList<Mine> m)
    {
        // iterate over the whole arraylist and check all distances
        for(int i = 0; i < m.size(); i++)
        {
            // if any have collided with the player(this) return true
            // uses checkCollision from Mine class
            if(m.get(i).checkCollision(this))
            {
                return true;
            }
        }
        // else always return false to continue gameplay loop
        return false;
    }

    // accessors
    public int getRadius(){return this.radius;}
    public int getDiameter(){return this.diameter;}
    public double getForceX(){return this.forceX;}
    public double getForceY(){return this.forceY;}
    public boolean getAReleased(){return this.A_released;}
    public boolean getWReleased(){return this.W_released;}
    public boolean getDReleased(){return this.D_released;}
    public boolean getSReleased(){return this.S_released;}

    // set force
    public void setForceX(double forceX){this.forceX = forceX;}
    public void setForceY(double forceY){this.forceY = forceY;}

    // key released methods
    public void flipKey(String key)
    {
        //if key true, make false, vice versa
        switch(key)
        {
            case "W":
                this.W_released = !this.W_released;
                break;
            case "A":
                this.A_released = !this.A_released;
                break;
            case "S":
                this.S_released = !this.S_released;
                break;
            case "D":
                this.D_released = !this.D_released;
                break;
        }
    }
    public void keyFalse(String key)
    {
        // make released key false
        switch(key)
        {
            case "W":
                this.W_released = false;
                break;
            case "A":
                this.A_released = false;
                break;
            case "S":
                this.S_released = false;
                break;
            case "D":
                this.D_released = false;
                break;
        }
    }
}