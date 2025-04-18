import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import java.util.*;

public class Mine extends DrawableObject
{
   // mine dimensions
   private final int radius = 10;
   private final int diameter = radius * 2;
   private float color = 0;
   Random rand = new Random();

   //takes in its position
   public Mine(float x, float y)
   {
      super(x, y);
   }

   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      float begin = (float)rand.nextDouble(0, 0.05);
      this.color += begin;
      if(color > 1)
      {
         color = 0;
      }
      gc.setFill(new Color(1,color,color,1));
      gc.fillOval(x - radius, y - radius, diameter, diameter);
   }
   public boolean checkCollision(Player p)
   {
      int collisionDistance = p.getRadius() + this.getRadius();
      // if the distance between the player and the mine is less than the collisionDistance, collide
      if(this.distance(p) <= collisionDistance)
      {
         return true;
      }
      return false;
   }
   // accessors
   public int getRadius(){return radius;}
   public int getDiameter(){return diameter;}
}