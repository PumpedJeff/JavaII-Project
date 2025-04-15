import java.io.*;
import java.util.*;
import java.text.*;
import java.io.*;
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
import javafx.scene.text.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;

/* 
   Jeffrie McGehee
   Java II Project
   4.20.25
*/


public class Main extends Application
{
   FlowPane fp;
   
   Canvas theCanvas = new Canvas(600,600);
   
   TestObject thePlayer = new TestObject(300,300);
   
   AnimationTimer ta;
   
   GraphicsContext gc;

   public void start(Stage stage)
   {
      // create the flowpane and add the canvas
      fp = new FlowPane(); 
      gc = theCanvas.getGraphicsContext2D();    
      fp.getChildren().add(theCanvas);
      
      drawBackground(thePlayer.getX(),thePlayer.getY(),gc);
      
      thePlayer.drawMe(300, 300, gc);
      
      Scene scene = new Scene(fp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project :)");
      stage.show();
      
      fp.setOnKeyReleased(new KeyListenerUp());
      fp.setOnKeyPressed(new KeyListenerDown());
      fp.requestFocus();
      
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

   public class KeyListenerUp implements EventHandler<KeyEvent>  
   {  
      public void handle(KeyEvent event) 
      {
         thePlayer.flipKey();
         /*
         if(event.getCode() == KeyCode.W) 
         { 
            thePlayer.slowDirection("down");
         }
         if(event.getCode() == KeyCode.S)
         {
            thePlayer.slowDirection("up");
         }
         if(event.getCode() == KeyCode.A) 
         {
            thePlayer.slowDirection("left");
         }
         if(event.getCode() == KeyCode.D) 
         {
            thePlayer.slowDirection("right");
         }
         // once the player releases key, flip to true
         thePlayer.flipKey();
         */
      }
   }

   
   public class KeyListenerDown implements EventHandler<KeyEvent>  
   {  
      public void handle(KeyEvent event) 
      {        
          KeyCode savedKey = event.getCode();
          
          if(event.getCode() == KeyCode.W) 
          {
            thePlayer.moveUp();
          }
          if(event.getCode() == KeyCode.S) 
          {
            thePlayer.moveDown();
          }
          if(event.getCode() == KeyCode.A) 
          {
            thePlayer.moveLeft();
          }
          if(event.getCode() == KeyCode.D) 
          {
            thePlayer.moveRight();
          }
          // make key released false while player is pressing key
          thePlayer.keyFalse();
      }
   }
   
   

   
   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
         thePlayer.act();
         //System.out.println("FoceY: " + thePlayer.getForceY());
      
         gc.clearRect(0,0,600,600);
         
         //USE THIS CALL ONCE YOU HAVE A PLAYER
         drawBackground(thePlayer.getX(),thePlayer.getY(),gc); 

	      //example calls of draw - this should be the player's call for draw
         thePlayer.draw(300,300,gc,true); //all other objects will use false in the parameter.
         
         //example call of a draw where m is a non-player object. Note that you are passing the player's position in and not m's position.
         //m.draw(thePlayer.getX(),thePlayer.getY(),gc,false);
         
      }
   }

   public static void main(String[] args)
   {
      launch(args);
   }
}

