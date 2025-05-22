import java.io.*;
import java.util.*;
import java.lang.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

/* 
   Jeffrie McGehee
   Java II Project
   4.20.25
*/

public class Main extends Application
{
   // javaFX objects
   StackPane sp;
   Canvas theCanvas = new Canvas(600,600);
   AnimationTimer ta;
   GraphicsContext gc;

   // player and player objects & variables
   Player thePlayer = new Player(300,300);
   Label statsLabel = new Label("Stats");
   private int highScoreVar = 0;

   // test mine & objects
   Random rand = new Random();
   ArrayList<Mine> mines = new ArrayList<>();

   public void start(Stage stage)
   {
      // draw the first 10 mines
      for(int i = 0; i < 10; i++)
      {
         mines.add(new Mine(rand.nextInt(600), rand.nextInt(600)));
      }

      // create the flowpane and add the canvas
      sp = new StackPane();
      gc = theCanvas.getGraphicsContext2D();    
      sp.getChildren().addAll(theCanvas, statsLabel);

      statsLabel.setTextFill(Color.WHITE);
      StackPane.setAlignment(statsLabel, Pos.TOP_LEFT);
      
      drawBackground(thePlayer.getX(),thePlayer.getY(),gc);
      
      thePlayer.drawMe(300, 300, gc);
      
      Scene scene = new Scene(sp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project :)");
      stage.show();
      
      sp.setOnKeyReleased(new KeyListenerUp());
      sp.setOnKeyPressed(new KeyListenerDown());
      sp.requestFocus();
      
      ta = new AnimationHandler();
      ta.start();
   }
   
   Image background = new Image("stars.png");
   Image overlay = new Image("starsoverlay.png");
   Random backgroundRand = new Random();
   
   //this piece of code doesn't need to be modified
   public void drawBackground(float playerx, float playery, GraphicsContext gc)
   {
	  //re-scale player position to make the background move slower. 
      playerx *= .1;
      playery *= .1;
   
	   //figuring out the tile's position.
      float x = (playerx) / 400;
      float y = (playery) / 400;
      
      int xi = (int) x;
      int yi = (int) y;
      
	  //draw a certain amount of the tiled images
      for(int i = xi - 3; i < xi + 3; i++)
      {
         for(int j = yi - 3; j < yi + 3; j++)
         {
            gc.drawImage(background,-playerx+i*400,-playery+j*400);
         }
      }
      
	  //below repeats with an overlay image
      playerx *= 2f;
      playery *= 2f;
   
      x = (playerx) / 400;
      y = (playery) / 400;
      
      xi = (int) x;
      yi = (int) y;
      
      for(int i = xi - 3; i < xi + 3; i++)
      {
         for(int j = yi - 3; j < yi + 3; j++)
         {
            gc.drawImage(overlay,-playerx+i*400,-playery+j*400);
         }
      }
   }
   // key listeners
   public class KeyListenerUp implements EventHandler<KeyEvent>  
   {  
      public void handle(KeyEvent event) 
      {
         // once the key is up, flip to true
         if(event.getCode() == KeyCode.W) 
         { 
            thePlayer.flipKey("W");
         }
         if(event.getCode() == KeyCode.S)
         {
            thePlayer.flipKey("S");
         }
         if(event.getCode() == KeyCode.A) 
         {
            thePlayer.flipKey("A");
         }
         if(event.getCode() == KeyCode.D) 
         {
            thePlayer.flipKey("D");
         }
      }
   }
   public class KeyListenerDown implements EventHandler<KeyEvent>  
   {  
      public void handle(KeyEvent event) 
      {
          // make key released false while player is pressing key
          if(event.getCode() == KeyCode.W) 
          {
             thePlayer.keyFalse("W");
          }
          if(event.getCode() == KeyCode.S) 
          {
             thePlayer.keyFalse("S");
          }
          if(event.getCode() == KeyCode.A) 
          {
             thePlayer.keyFalse("A");
          }
          if(event.getCode() == KeyCode.D) 
          {
             thePlayer.keyFalse("D");
          }
      }
   }

   public void addMines(ArrayList<Mine> m, Player p, int distance)
   {
      // get player position
      int x = (int)p.getX();
      int y = (int)p.getY();

      // player grid position
      int pGridx = x / 100;
      int pGridy = y / 100;

      // spawning mines close to player based on the direction
      int spawnRate = rand.nextInt(20);
      if(spawnRate == 0)
      {
         if(p.getForceX() > 0)
            m.add(new Mine(rand.nextInt(x + 320, x + 350), rand.nextInt(y - 300, y + 300)));

         if(p.getForceX() < 0)
            m.add(new Mine(rand.nextInt(x - 350, x - 320), rand.nextInt(y - 300, y + 300)));

         if(p.getForceY() > 0)
            m.add(new Mine(rand.nextInt(x - 300, x + 300), rand.nextInt(y + 320, y + 350)));

         if(p.getForceY() < 0)
            m.add(new Mine(rand.nextInt(x - 300, x + 300), rand.nextInt(y - 350, y - 320)));
      }
   }

   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
         // GETTING THE HIGH SCORE
         try
         {
            File highScore = new File("high_score.txt");
            Scanner scan = new Scanner(highScore);

            String highScoreStr = scan.nextLine();
            highScoreVar = Integer.parseInt(highScoreStr);
         }
         catch(FileNotFoundException e)
         {
            System.out.println("file not found");
         }

         // initialize variables outside the loop
         double xSquared = Math.pow(thePlayer.getX() - 300, 2);
         double ySquared = Math.pow(thePlayer.getY() - 300, 2);
         int distance = (int)Math.sqrt(xSquared + ySquared);
         // run gameplay loop as long as the player has not collided with a mine
         if(!thePlayer.didCollide(mines))
         {
            thePlayer.act();
            // calculate player distance
            xSquared = Math.pow(thePlayer.getX() - 300, 2);
            ySquared = Math.pow(thePlayer.getY() - 300, 2);
            distance = (int)Math.sqrt(xSquared + ySquared);

            statsLabel.setText("Score: " + distance + "\nHigh Score: " + highScoreVar);
            // Accessing WASD command inputs
            // System.out.println("\nW: " + thePlayer.getWReleased() + "\nA: " + thePlayer.getAReleased() + "\nS: " + thePlayer.getSReleased() + "\nD: " + thePlayer.getDReleased());

            // Accessing ForceX + Y and XY positions
            // System.out.println("\nForceX: " + thePlayer.getForceX() + "\nForceY: " + thePlayer.getForceY() + "\nX: " + thePlayer.getX() + "\nY: " + thePlayer.getY());

            // accessing mines and grid quadrants
            // "\nMines: " + mines.size() + "\ngrid x: " + (int)thePlayer.getX() / 100 + "\ngrid y: " + (int)thePlayer.getY() / 100

            gc.clearRect(0,0,600,600);

            //USE THIS CALL ONCE YOU HAVE A PLAYER
            drawBackground(thePlayer.getX(),thePlayer.getY(),gc);

             //example calls of draw - this should be the player's call for draw
            thePlayer.draw(300,300,gc,true); //all other objects will use false in the parameter.

            //example call of a draw where m is a non-player object. Note that you are passing the player's position in and not m's position.
            addMines(mines, thePlayer, distance);

            for(int i = 0; i < mines.size(); i++)
            {
               // remove mines that are out of bounds of player and draw the new mines
               mines.get(i).checkOutOfBounds(mines, thePlayer);
               mines.get(i).draw(thePlayer.getX(),thePlayer.getY(),gc,false);
            }
         }
         else
         {
            // write the high score
            try
            {
               FileWriter fw = new FileWriter("high_score.txt");
               if(distance > highScoreVar) {
                  fw.write(distance + "");
               }
               else
               {
                  fw.write(highScoreVar + "");
               }
               fw.close();
            }
            catch(Exception e)
            {
               System.out.println("Cannot write to high_score file");
            }
            thePlayer.setX(0);
            thePlayer.setY(0);
            thePlayer.setForceX(0);
            thePlayer.setForceY(0);
         }
      }
   }
   public static void main(String[] args)
   {
      launch(args);
   }
}