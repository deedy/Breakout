import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** An instance is the game breakout. Start it by executing
    Breakout.main(null);
    */
public class BreakoutSumit extends GraphicsProgram {
    /** Width of the game display (al coordinates are in pixels) */
    private static final int GAME_WIDTH= 480;
    /** Height of the game display */
    private static final int GAME_HEIGHT= 620;
    
    /** Width of the paddle */
    private static final int PADDLE_WIDTH= 58;
    /** Height of the paddle */
    private static final int PADDLE_HEIGHT= 11;
    /**Distance of the (bottom of the) paddle up from the bottom */
    private static final int PADDLE_OFFSET= 30;
    
    /** Horizontal separation between bricks */
    public static final int BRICK_SEP_H= 5;
    /** Vertical separation between bricks */
    private static final int BRICK_SEP_V= 4;
    /** Height of a brick */
    private static final int BRICK_HEIGHT= 8;
    /** Offset of the top brick row from the top */
    private static final int BRICK_Y_OFFSET= 70;
    
    /** Number of bricks per row */
    public static  int BRICKS_IN_ROW= 10;
    /** Number of rows of bricks, in range 1..10. */
    public static  int BRICK_ROWS= 10;
    /** Width of a brick */
    public static int BRICK_WIDTH= GAME_WIDTH / BRICKS_IN_ROW - BRICK_SEP_H;
    
    /** Diameter of the ball in pixels */
    private static final int ball_DIAMETER= 18;
    
    /** Number of turns */
    private static final int NTURNS= 3;
    
     /** rowColors[i] is the color of row i of bricks */
    private static final Color[] rowColors= {Color.red, Color.red, Color.orange, Color.orange,
        Color.yellow, Color.yellow, Color.green, Color.green,
        Color.cyan, Color.cyan};
    
    /** random number generator */
    private RandomGenerator rgen= new RandomGenerator();
    
    
    private static GRect paddleObject;
    private static GOval ball;
    
    /** Sound to play when the ball hits a brick or the paddle. */
    private static AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
    
    /** Run the program as an application. If args contains 2 elements that are positive
        integers, then use the first for the number of bricks per row and the second for
        the number of rows of bricks.
        A hint on how main works. The main program creates an instance of
        the class, giving the constructor the width and height of the graphics
        panel. The system then calls method run() to start the computation.
      */
    public static void main(String[] args) {
        fixBricks(args);
        String[] sizeArgs= {"width=" + GAME_WIDTH, "height=" + GAME_HEIGHT};
        new BreakoutSumit().start(sizeArgs);
        
    }
    
    /** If b is null, doesn't have exactly two elements, or the elements are not
        positive integers, DON'T DO ANYTHING.
        If b is non-null, has exactly two elements, and they are positive
        integers with no blanks surrounding them, then:
        Store the first int in BRICKS_IN_ROW, store the second int in BRICK_ROWS,
        and recompute BRICK_WIDTH using the formula given in its declaration.
         */
    public static void fixBricks(String[] b) {
        /** Hint. You have to make sure that the two Strings are positive integers.
            The simplest way to do that is to use the calls Integer.valueOf(b[0]) and
            Integer.valueOf(b[1]) within a try-statement in which the catch block is
            empty. Don't store any values in the static fields UNTIL YOU KNOW THAT
            both array elements are positive integers. */
        try {
  if(b!=null) {
   if(b[0]!=null && b[1]!=null) {
    BRICKS_IN_ROW=Integer.valueOf(b[0]);
    BRICK_WIDTH=Integer.valueOf(b[1]);
   }
  }
  }catch(Exception e) {
  }
    }
    
    /** Run the Breakout program. */
    public void run() {
     
    createBricksAndPaddle();
    createBall();
    }

    public void createBricksAndPaddle() {
        paddleObject = new GRect(((GAME_WIDTH/2)-(PADDLE_WIDTH/2)),(GAME_HEIGHT-(PADDLE_OFFSET+PADDLE_HEIGHT)),
                             PADDLE_WIDTH, PADDLE_HEIGHT);
    paddleObject.setFilled(true);
    paddleObject.setFillColor(Color.BLACK);
    paddleObject.setVisible(true);
    add(paddleObject);
    addMouseListeners();
     int x=BRICK_SEP_V+BRICK_SEP_V,y=BRICK_Y_OFFSET;
    for(int i = 0;i<BRICK_ROWS;i++) {
      for(int j = 0;j<BRICKS_IN_ROW;j++) {
        Brick brick = new Brick(x+BRICK_SEP_V,y,BRICK_WIDTH,BRICK_HEIGHT);
        x+=BRICK_WIDTH;
        brick.setFilled(true);
        brick.setFillColor(rowColors[i]);
        add(brick);
      } 
      y+=(BRICK_HEIGHT+BRICK_SEP_H);
      x=BRICK_SEP_V+BRICK_SEP_V;
    }
    }
    
    public void createBall() {
    ball = new GOval(((GAME_WIDTH/2)),(GAME_HEIGHT/2),ball_DIAMETER,ball_DIAMETER);
    ball.setFilled(true);
    ball.setFillColor(Color.BLUE);
    ball.setVisible(true);
    add(ball);
    waitForClick();
    }
    
    /** Move the horizontal middle of the paddle to the x-coordinate of the mouse
        -- but keep the paddle completely on the board.
        Called by the system when the mouse is used. 
      */
    public void mouseMoved(MouseEvent e) {
        GPoint p= new GPoint(e.getPoint());
        // Set x to the left edge of the paddle so that the middle of the paddle
        // is where the mouse is --except that the mouse must stay completely
        // in the pane if the mouse moves past the left or right edge.
        double pos= Math.max( Math.min (p.getX()-(paddleObject.getWidth()/2), GAME_WIDTH - paddleObject.getWidth()), 0);
        paddleObject.setLocation((int) pos, GAME_HEIGHT-PADDLE_OFFSET-paddleObject.getHeight());
    }
     public GObject getCollidingObject() {
   if(getElementAt(ball.getX(),ball.getY())==paddleObject)
   return getElementAt(ball.getX(),ball.getY());
   else if(getElementAt(ball.getX(),ball.getY()+ball_DIAMETER)==paddleObject)
           return getElementAt(ball.getX(),ball.getY()+ball_DIAMETER);
   else if(getElementAt(ball.getX()+ball_DIAMETER,ball.getY())==paddleObject)
     return getElementAt(ball.getX()+ball_DIAMETER,ball.getY());
   else if(getElementAt(ball.getX()+ball_DIAMETER,ball.getY()+ball_DIAMETER)==paddleObject)
     return getElementAt(ball.getX()+ball_DIAMETER,ball.getY()+ball_DIAMETER);
   else
     return null;
  }
    
    /** = representation of array b: its elements separated by ", " and delimited by [].
        if b == null, return null. */
    public static String toString(String[] b) {
        if (b == null) return null;
        
        String res= "[";
        // inv res contains "[" + elements of b[0..k-1] separated by ", "
        for (int k= 0; k < b.length; k= k+1) {
            if (k > 0)
                res= res + ", ";
            res= res + b[k];
        }
        return res + "]";
    }
    
}

/** An instance is a Brick */
/*  Note: This program will not compile until you write the two
    constructors correctly, because GRect does not have a 
    constructor with no parameters.  (You know that if a constructor
    does not begin with a call off another constructor, Java inserts
    
        super();
        
    */
class Brick extends G3DRect {
    
    /** Constructor: a new brick with width w and height h*/
    public Brick(double w, double h) {
           super(w,h);
    }
    
    /** Constructor: a new brick at (x,y) with width w and height h*/
    public Brick(double x, double y, double w, double h) {
             super(x,y,w,h);
    }
}