import javafx.scene.paint.*;
import javafx.scene.canvas.*;

//this is an example object
public class TestObject extends DrawableObject
{
   private double forceX = 0;
   private double forceY = 0;
   boolean keyReleased = true;

	//takes in its position
   public TestObject(float x, float y)
   {
      super(x,y);
   }
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      gc.setFill(Color.RED);
      gc.fillOval(x-14,y-14,27,27);
      gc.setFill(Color.GRAY);
      gc.fillOval(x-13,y-13,25,25);
   }
   
   public void act()
   {
      // add force to player
      this.setX(this.getX() + (float)this.getForceX());
      this.setY(this.getY() + (float)this.getForceY());
      
      //System.out.println(this.getForceY());
      
      if(keyReleased)
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
         // reduce up velocity 
         if(forceY < 0)
         {
            forceY = forceY + 0.025;
            if(forceY > 0)
            {
               forceY = 0;
            }
         }
         
         // reduce right velocity
         if(forceX > 0)
         {
            forceX = forceX - 0.025;
            // once forceY has gone below 0, make forceY 0
            if(forceX < 0)
            {
               forceX = 0;
            }
         }
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
   }
   
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
   /*
   public void slowDirection(String direction)
   {
      switch(direction)
      {
         case "up":
         {
            System.out.println("slowing start");
            /*
            while(forceY > 0)
            {
               forceY = forceY - 0.025;
               System.out.println(forceY);
            }
            // once force gets to 0, reset
            forceY = 0;
            this.flipKey();
            break;
         
            /*
            // continue to reduce the force of up motion by going down (make 0.025 force)
            while(keyReleased)
            {
               // reverse the force by 0.025
               forceY = Math.round((forceY - 0.025) * 100);
               forceY = forceY / 100;
               
               // once force gets to 0, reset
               if(forceY < 0)
               {
                  forceY = 0;
                  this.flipKey();
                  break;
               }
            }
            
         }
         case "down":
         {
            // look at comments for "up" for explanation
            while(keyReleased)
            {
               forceY = Math.round((forceY + 0.025) * 100);
               forceY = forceY / 100;
               
               if(forceY > 0)
               {
                  forceY = 0;
                  this.keyFalse();
                  break;
               }
            }
            break;
         }
         case "left":
         {
            // look at comments for "up" for explanation
            while(keyReleased)
            {
               forceX = Math.round((forceX + 0.025) * 100);
               forceX = forceX / 100;
               
               if(forceX > 0)
               {
                  forceX = 0;
                  this.keyFalse();
                  break;
               }
            }
            break;
         }
         case "right":
         {
            // look at comments for "up" for explanation
            while(keyReleased)
            {
               forceX = Math.round((forceX - 0.025) * 100);
               forceX = forceX / 100;
               
               if(forceX < 0)
               {
                  forceX = 0;
                  this.keyFalse();
                  break;
               }
            }
            break;
         }
      }
   }
   */
   
   // accessors
   public double getForceX(){return this.forceX;}
   public double getForceY(){return this.forceY;}
   public boolean getKeyReleased(){return this.keyReleased;}
   
   public void flipKey()
   {
      if(this.getKeyReleased() == false)
      {
         this.keyReleased = true;
      }
      else
      {
         this.keyReleased = false;
      }
   }
   
   public void keyTrue()
   {
      this.keyReleased = true;
   }
   public void keyFalse()
   {  
      this.keyReleased = false;
   }
}
