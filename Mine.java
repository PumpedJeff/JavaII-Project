import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public class Mine extends DrawableObject
{

   //takes in its position
   public Mine(float x, float y)
   {
      super(x, y);
   }

   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc) {
      gc.setFill(Color.RED);
      gc.fillOval(x - 14, y - 14, 27, 27);
      gc.setFill(Color.WHITE);
      gc.fillOval(x - 13, y - 13, 25, 25);
   }

   public void act() {
      System.out.println();
   }
}