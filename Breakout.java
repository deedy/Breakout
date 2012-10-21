 /** TIME SPENT= 20 hours 
 * 
 * EXTENSIONS (from the suggested ones):
  * 1. User control over bounces improved. If the left or right 1/4th of the paddle is hit, both vx and vy are reflected.
  * 2. The player can play as many games as they want to, by clicking at the end of a game.
  * 3. Appropriate sounds were added - Bounce was added for the paddle bounce, two seperate plate shatter noises were added 
  * for brick breaking, either one which plays randomly. The SuperMario lose life sound is used when a life is lost. The
  * SuperMario mushroom collect noise is used when a power up is collected. A fiery sound is used when fireball mode is 
  * activated.
  * 4. Instead of a kicker, the ball in my game has a slow progression in speed with gameplay. Everytime the paddle is hit,
  * the pause in milliseconds per frame goes down by 0.5, until it reaches a pause minimum, or a maximum speed, beyond which
  * it can only go if the appropriate powerup is activated.
  * 5. Score is kept in a cool new way. The upper blocks are worth more than the lower ones. When multiple bricks are broken
  * with one hit, you get n*(original points of the block) where 'n' is the consecutive blocks you've hit upto that point.
  * This means that players who "Break out" get more points.
  * 
  * ADDITIONAL EXTENSIONS (of my own):
  * 1. There is a dropbar of customizable length at the bottom of the playing window with certain special features.
  * 2. There is a label which keeps track of the number of bricks left.
  * 3. There is a label which keeps track of the lives the player has left. Initial = 3.
  * 4. There is a label to keep the score of the player.
  * 5. The bricks extend 3DRect instead of Rect for a better look and feel of the game.
  * 6. A 3D pause button was added, which changes prompt when pressed to "Resume" and pauses the game. When pressed, the 
  * 3DRect has a distinct pressed look to make it appear like an authentic button.
  * 7. A 3D mute button was added, which changes prompt when pressed to "Turn mute off" and mutes all the game sounds. 
  * When pressed, the 3DRect has a distinct pressed look to make it appear like an authentic button.
  * 8. A 3D Funk button was added, which changes prompt when pressed to "Turn funk off". 
  * When pressed, the 3DRect has a distinct pressed look to make it appear like an authentic button. This idea, admittedly,
  * is based off a submission from last year shown on the first day of class. When funk is turned on, the color of the ball
  * and paddle rapidly and randomly switch between the rowColors vector of Colors, giving a glowing, mesmerizing, funky
  * feel to the game.
  * 9. At the start of every new ball, or life, a prompt is displayed, and the life is started after a click instead of 
  * a pause, making for better gameplay.
  * 10**. POWER UPS - Every 1 in 2 blocks might contain power ups, which can only be released if a power up is not active 
  * already. When a block containing a power up is broken, the power up appears in the shape of the ball (for convenience)
  * and drops down at a pace INDEPENDENT of the speed of the ball, as a glowing ball (similar to our ball in funk mode).
  * If the paddle manages to 'catch' the power up, it disappears, and one of 6 power ups is activated, each given equal 
  * probability, for simplicity:
  * a. PADDLE SIZE INCREASE - Paddle size doubles for 5 paddle hits
  * b. PADDLE SIZE DECREASE - Paddle size halves for 5 paddle hits
  * c. BALL SPEED UP - Ball speeds up by a certain amount for 5 paddle hits
  * d. BALL SLOW DOWN - Ball slows down by a certain amount for 5 paddle hits
  * e. EXTRA LIFE - The user gets an extra life
  * f. FIREBALL MODE (my favorite) - The Ball acts like a fireball, and is no more reflected by bricks for 5 turns. It 
  * burns straight through all the bricks like a fireball. The appropriate fireball noise is made, and the ball appears red.
  * 11. Improved Ball movement. When the ball hits the side of the brick, it is reflected along the horizontal axis instead 
  * of the vertical.
  * 12. When a game is finished, prompt messages are displayed for winning, losing along with the score. 
  * 
  * 
  * IMPROVEMENTS TO ASSIGNMENT:
  * 1. One of my major complaints about an assignment of this order was the grading scheme. I feel like an innovative student is 
  * really bogged down by the grading to let his mind loose. I mean, I've implemented so many cool procedures but I still feel 
  * like I might not get the best grade because maybe I missed a class spec or missed writing the time I spent. The emphasis
  * on fine tuning somehow domninates that on innovation.
  * 2. And also, as a student, Im discouraged for doing more than necessary knowing that someone who's implemented it simply
  * and spent less time on it will be rewarded as much as I am.
  * 3. I think as a solution to the above, there should be 20 marks allotted to invention. This will encourage the grade-seeker
  * to innovate and know he'll get results. Maybe extra credit perhaps.
  * 4. Adding images instead of GObjects would be pretty cool
  * 5. Again, more stress on doing things our own way is key. Instead of writing out how the implementation should happen, 
  * I think we should clearly be given the option to try our own way. Suppose I wanted to launch the ball from the paddle
  * instead of the center, perhaps. 
  * 6. This might be a personal opinion, but I think assignment should have a higher bearing than 7%.
  * 
  * Otherwise, I really liked this assignment. This is by far the coolest assignment we've done and it was incredibly fun to 
  * code and show off to our other non-CS friends. As high school kids we've always dreamed of coding our own games and this
  * was incredible and should be continued in the years to come. It was long only because I wanted to work on it. It makes you 
  * feel like a programmer. It set me up and inspired me to design even more games. Absolutely amazing.
  */


import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/** An instance is the game breakout. Start it by executing
    Breakout.main(null);
    */
public class Breakout extends GraphicsProgram implements MouseListener{
    
    /** Width of the game display (all coordinates are in pixels) */
    private static final int GAME_WIDTH= 480;
    /** Height of the game display */
    private static final int GAME_HEIGHT= 620;
    
    /** Width of the paddle */
    private static final int PADDLE_WIDTH= 100; 
    /** Height of the paddle */
    private static final int PADDLE_HEIGHT= 11;
    /**Distance of the (bottom of the) paddle up from the bottom */
    private static final int PADDLE_OFFSET= 30;
    /**The Paddle */
    private static GRect PADDLE;
    
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
    /** Number of bricks left */
    public static int BRICK_NUMBER_LEFT= BRICKS_IN_ROW * BRICK_ROWS;
    /** Diameter of the ball in pixels */
    private static final int BALL_DIAMETER= 18;
    
    /** Number of lives */
    private static int NTURNS= 3;
    
    /** Initial slowness of the ball */
    private static final int INIT_SLOWNESS= 15;
    /** Final slowness of the ball after perpetual gameplay */
    private static final int FINAL_SLOWNESS= 5;
    /** Current slowness of the ball */
    private static double SLOWNESS= INIT_SLOWNESS;
    
    /** The Ball */
    private static GOval BALL;
    
    /** The collectible falling Power Up */
    private static GOval POWER_UP;
    
    /** The velocity of the ball in the x and y direction */
    private double vx, vy;
    
    /** The Player's score */
    private static int SCORE;
    
    /** The Label which displays the number of lives the player has left */
    private static GLabel LIVESDISP;
    /** The Label which displays the number of bricks left */
    private static GLabel BRICK_COUNTER;
    /** The Label which displays the player's score */
    private static GLabel SCORE_LABEL;
    
    /** 3D Funk Button */
    private static G3DRect FUNK_BUTTON;
    /** Funk button label */
    private static GLabel FUNK_LABEL;
    /** Current condition of the Funk */
    private static boolean FUNK_STATUS=false;
    
    /** 3D Mute Button */
    private static G3DRect MUTE_BUTTON;
    /** Mute button label */
    private static GLabel MUTE_LABEL;
    /** Current condition of the Mute */
    private static boolean MUTE_STATUS=false;
    
    /** 3D Pause Button */
    private static G3DRect PAUSE_BUTTON;
    /** Pause button label */
    private static GLabel PAUSE_LABEL;
    /** Current condition of the Pause Button */
    private static boolean PAUSE_STATUS=false;
    
    /** Height of the Dropbar */
    private static final int DROPBAR_HEIGHT= 100;
    
    /** Current number of bricks broken consecutively */ 
    private static int COMBO_COUNT= 0;
    
    /** Number of times Paddle has been hit since beginning of the game */
    private static int PADDLE_HIT_COUNT=0;
    
    /** Number of the Power presently active, 0 for no power, 1-6 for others */
    private static int POWER_ON= 0;
    
    /** Global counter to count number of hits at power event */
    private static int initHit=0;
    
    /** Label to display activation and deactivation of power */
    private static GLabel POWER_UP_MESSAGE;
    
     /** rowColors[i] is the color of row i of bricks */
    private static final Color[] rowColors= {Color.red, Color.red, Color.orange, Color.orange,
        Color.yellow, Color.yellow, Color.green, Color.green,
        Color.cyan, Color.cyan};
    
    /** random number generator */
    private RandomGenerator rgen= new RandomGenerator();
    
    
    /** Sound to play when the ball hits the paddle. */
    private static AudioClip bouncePaddle = MediaTools.loadAudioClip("bounce.au");
    /** 1st Sound to play when the ball hits a brick. */
    private static AudioClip bounceBrick1 = MediaTools.loadAudioClip("plate1.wav");
    /** 2nd Sound to play when the ball hits a brick */
    private static AudioClip bounceBrick2 = MediaTools.loadAudioClip("plate2.wav");
    /** Sound to play when a life is lost */
    private static AudioClip loselife = MediaTools.loadAudioClip("loselife.wav");
    /** Sound to play when the ball is on Fireball power up mode and hits a brick */
    private static AudioClip fireball = MediaTools.loadAudioClip("fireball.wav");
    /** Sound to play when the paddle receives a power up */
    private static AudioClip powerup = MediaTools.loadAudioClip("PowerUp.wav");
    
    /** Run the program as an application. If args contains 2 elements that are positive
        integers, then use the first for the number of bricks per row and the second for
        the number of rows of bricks.
        A hint on how main works. The main program creates an instance of
        the class, giving the constructor the width and height of the graphics
        panel. The system then calls method run() to start the computation.
      */
    public static void main(String[] args) {
        fixBricks(args);
        String[] sizeArgs= {"width=" + (GAME_WIDTH), "height=" + (GAME_HEIGHT+DROPBAR_HEIGHT)};
        new Breakout().start(sizeArgs);
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
        if (b.length!=2)
            return;
        try {
            int xx= Integer.parseInt (b[0]);
            int yy= Integer.parseInt (b[1]);
        }
        catch (Exception e){
            return;
        }
        BRICKS_IN_ROW= Integer.valueOf (b[0]);
        BRICK_ROWS= Integer.valueOf (b[1]);
        BRICK_WIDTH= GAME_WIDTH / BRICKS_IN_ROW - BRICK_SEP_H;
        BRICK_NUMBER_LEFT= BRICKS_IN_ROW * BRICK_ROWS;
    }
    
    /** Creates the bricks in their appropriate positions with their appropriate colors */
    private void createBricks () {
        for (int i=0;i<BRICKS_IN_ROW;i++) {
            for (int j=0;j<BRICK_ROWS;j++) {
                Brick brick=new Brick ((BRICK_SEP_H/2)+i*(BRICK_WIDTH+BRICK_SEP_H), (BRICK_Y_OFFSET)+j*(BRICK_HEIGHT+BRICK_SEP_V), BRICK_WIDTH,BRICK_HEIGHT);
                brick.setColor (rowColors [j%10]);
                brick.setFilled (true);
                add(brick);
            }
        }
    }
    
    /** Adds a Paddle of filled black color of PADDLE_WIDTH*PADDLE_HEIGHT dimensions */
    private void addPaddle () {
        PADDLE=new GRect ((GAME_WIDTH-PADDLE_WIDTH)/2, GAME_HEIGHT-(PADDLE_OFFSET+PADDLE_HEIGHT), PADDLE_WIDTH, PADDLE_HEIGHT);
        PADDLE.setColor (Color.BLACK);
        PADDLE.setFillColor (Color.BLACK);
        PADDLE.setFilled (true);
        add (PADDLE);
    }
        
    /** Sets up Ball in the middle of the playing area with a random x velocity between 1 and 3 and a y velocity of 3, downwards
      * as well as a message to release the ball on clicking */
    private void setUpBall() {
        BALL= new GOval (GAME_WIDTH/2 - BALL_DIAMETER/2, GAME_HEIGHT/2 - BALL_DIAMETER/2, BALL_DIAMETER, BALL_DIAMETER);
        BALL.setFillColor (Color.BLACK);
        BALL.setFilled (true);
        add (BALL);
        vx= rgen.nextDouble(1.0, 3.0); 
        if (!rgen.nextBoolean(0.5)) vx= -vx;
        vy=3.0;
        String message= "Click to release ball";
        GLabel prompt= new GLabel (message);
        prompt.setColor(Color.red);
        prompt.setFont(new Font("Arial", Font.PLAIN, 15));
        prompt.move ((GAME_WIDTH-prompt.getWidth())/2, DROPBAR_HEIGHT/2);
        add(prompt);
        waitForClick();
        remove(prompt);
    }
    
    /** Move the ball one frame, according to the current velocity magnitudes, reflecting them in case a boundary is hit
      * and returning true if the user loses (ball goes below playing area) */
    private Boolean moveBall() {
        GPoint ballPoint = BALL.getLocation();
        if (ballPoint.getX()+BALL_DIAMETER > GAME_WIDTH) vx = -vx;
        if (ballPoint.getX() <= 0) vx = -vx;
        if (ballPoint.getY() <= 0) vy = -vy;
        if (ballPoint.getY() > GAME_HEIGHT) return true;//ballVY = -ballVY; // basically lost
        BALL.move(vx, vy);
        if (FUNK_STATUS) {
            BALL.setFillColor(rowColors [rgen.nextInt(0,9)]);
            PADDLE.setFillColor(rowColors [rgen.nextInt(0,9)]);
        }
        return false;
    }
    
    /** Returns the colliding object of the BALL or POWER_UP (as they both have the same dimensions) */
    private GObject getCollidingObject(GObject ballObj) {
        GPoint ballPoint = ballObj.getLocation();
        GObject coll;
        GPoint addingPoints[] = new GPoint[4];
        addingPoints[0]= new GPoint(0,0);
        addingPoints[1]= new GPoint(0,BALL_DIAMETER);
        addingPoints[2]= new GPoint(BALL_DIAMETER,BALL_DIAMETER);
        addingPoints[3]= new GPoint(BALL_DIAMETER,0);
        for (int i= 0; i< 4; i++) {
            coll= getElementAt(ballPoint.getX()+addingPoints[i].getX(), ballPoint.getY()+addingPoints[i].getY());
            if (coll!= null)
                return coll;
        }
        return null;
    }
    
    /** Sets the lives label in the beginning of the game with 3 lives */
    public void setLivesLabel () {
        NTURNS= 3;
        LIVESDISP= new GLabel (("Lives Left: "+ NTURNS), GAME_WIDTH/8, GAME_HEIGHT+(DROPBAR_HEIGHT/8));
        add(LIVESDISP);
    }
    
    /** Updates the lives label when a life is lost */
    public void resetLivesLabel () {
        LIVESDISP.setLabel ("Lives Left: "+ NTURNS);
    }
    
    /** Sets the brick counter label in the beginning of the game */
    public void setBrickCounter () {
        BRICK_NUMBER_LEFT= BRICKS_IN_ROW* BRICK_ROWS;
        BRICK_COUNTER= new GLabel ("Bricks Remaining: "+ BRICK_NUMBER_LEFT, GAME_WIDTH/8, GAME_HEIGHT+(DROPBAR_HEIGHT/4));
        add(BRICK_COUNTER);
    }
    
    /** Updates the brick counter label whenever a brick is broken */
    public void updateBrickCounter () {
        BRICK_COUNTER.setLabel ("Bricks Remaining: "+ BRICK_NUMBER_LEFT);
    }
    
    /** Set the score label in the beginning of the game with a score of 0 */
    public void setScoreLabel () {
        SCORE= 0;
        SCORE_LABEL= new GLabel ("SCORE: "+ SCORE, GAME_WIDTH/2, GAME_HEIGHT+(DROPBAR_HEIGHT/8));
        add(SCORE_LABEL);
    }
    
    /** Updates the score label whenever a score changing event occurs */
    public void updateScoreLabel ()  {
        SCORE_LABEL.setLabel ("SCORE: "+ SCORE);
    }
    
    /** Display message when all 3 lives are lost or when no bricks are left */
    public void showEndMessage (boolean win) {
        String message=win?"Congratulations! You have WON with a score of " : "I'm sorry. You've LOST with a score of ";
        message+= SCORE;
        GLabel endmessage= new GLabel (message);
        endmessage.setColor(Color.red);
        endmessage.setFont(new Font("Times New Roman", Font.BOLD, 20));
        endmessage.move ((GAME_WIDTH-endmessage.getWidth())/2, GAME_HEIGHT/2);
        add(endmessage);
    }
    
    /** Set a button to Turn Funk On and Off */
    public void setFunkButton () {
        FUNK_BUTTON=new G3DRect (0,0);
        FUNK_BUTTON.setFillColor (new Color (187,187,187));
        FUNK_BUTTON.setFilled (true);
        FUNK_LABEL=new GLabel ("Turn Funk On", GAME_WIDTH/2, GAME_HEIGHT+(DROPBAR_HEIGHT/2));
        FUNK_LABEL.setFont(new Font("Arial", Font.PLAIN, 15));
        double width= FUNK_LABEL.getWidth()+10;
        double height= FUNK_LABEL.getHeight()+10;
        FUNK_BUTTON.setBounds(GAME_WIDTH/2-5, GAME_HEIGHT+(DROPBAR_HEIGHT/2)-height+5, width, height);
        FUNK_BUTTON.setRaised(true);
        FUNK_LABEL.setColor (Color.black);
        add(FUNK_BUTTON);
        add(FUNK_LABEL);
    }
     
    /** Set a button to Turn Mute On and Off */
    public void setMuteButton () {
        MUTE_BUTTON=new G3DRect (0,0);
        MUTE_BUTTON.setFillColor (new Color (187,187,187));
        MUTE_BUTTON.setFilled (true);
        MUTE_LABEL=new GLabel ("Turn Mute On", 3*GAME_WIDTH/4, GAME_HEIGHT+(DROPBAR_HEIGHT/2));
        MUTE_LABEL.setFont(new Font("Arial", Font.PLAIN, 15));
        double width= MUTE_LABEL.getWidth()+10;
        double height= MUTE_LABEL.getHeight()+10;
        MUTE_BUTTON.setBounds(3*GAME_WIDTH/4-5, GAME_HEIGHT+(DROPBAR_HEIGHT/2)-height+5, width, height);
        MUTE_BUTTON.setRaised(true);
        MUTE_LABEL.setColor (Color.black);
        add(MUTE_BUTTON);
        add(MUTE_LABEL);
    }
    
    /** Set a button to Pause and Resume gameplay */
    public void setPauseButton () {
        PAUSE_BUTTON=new G3DRect (0,0);
        PAUSE_BUTTON.setFillColor (new Color (187,187,187));
        PAUSE_BUTTON.setFilled (true);
        PAUSE_LABEL=new GLabel ("Pause   ", GAME_WIDTH/4, GAME_HEIGHT+(DROPBAR_HEIGHT/2));
        PAUSE_LABEL.setFont(new Font("Arial", Font.PLAIN, 15));
        double width= PAUSE_LABEL.getWidth()+10;
        double height= PAUSE_LABEL.getHeight()+10;
        PAUSE_BUTTON.setBounds(GAME_WIDTH/4-5, GAME_HEIGHT+(DROPBAR_HEIGHT/2)-height+5, width, height);
        PAUSE_BUTTON.setRaised(true);
        PAUSE_LABEL.setColor (Color.black);
        add(PAUSE_BUTTON);
        add(PAUSE_LABEL);
    }
    
    /** Set Power Up when certian bricks are broken that drops from the brick */
    public void setPowerUp (GPoint p) {
        POWER_UP= new GOval (p.getX(), p.getY(), BALL_DIAMETER, BALL_DIAMETER);
        POWER_UP.setFillColor (Color.BLACK);
        POWER_UP.setFilled (true);
        add (POWER_UP);
    }
    
    /** Move Power Up downward with a velocity, independent of the changing slowness of quickly variable colors */
    public void movePowerUp() {
        POWER_UP.setFillColor(rowColors [rgen.nextInt(0,9)]);
        POWER_UP.move(0, 3*(SLOWNESS/(double)INIT_SLOWNESS));
    }
            
    /** Sets up all necessary components when the program is run */
    public void setup() {
         createBricks();
         addPaddle();
         setUpBall();
         addMouseListeners();
         setLivesLabel();
         setScoreLabel();
         setBrickCounter();
         setFunkButton();
         setMuteButton();
         setPauseButton();
    }
    
    /** Resets necessary components when a life is lost */
    public void reset() {
        NTURNS--;
        resetLivesLabel();
        if  (MUTE_STATUS==false)
            loselife.play();
        pause (3000);
        remove(PADDLE);
        remove(BALL);
        if (POWER_UP!=null)
            remove(POWER_UP);
        POWER_ON=0;
        initHit=0;
        PADDLE_HIT_COUNT=0;
        SLOWNESS=INIT_SLOWNESS;
        addPaddle();
        if (NTURNS>0)
            setUpBall();
        if (POWER_UP_MESSAGE!=null)
            remove (POWER_UP_MESSAGE);
    }
    
    /** Called when the Ball hits the Paddle. 
      * The noise is played, the slowness is decreased, and the ball is reflected as desired.
      */
    public void PaddleHit () {
        PADDLE_HIT_COUNT++;
        if (SLOWNESS>FINAL_SLOWNESS && POWER_ON!=4 && POWER_ON!=5)
            SLOWNESS-=0.5;
        COMBO_COUNT= 0;
        if (MUTE_STATUS==false)
            bouncePaddle.play();
        GPoint ballPoint = BALL.getLocation();
        GPoint paddlePoint = PADDLE.getLocation();
        if (ballPoint.getY() > GAME_HEIGHT-(PADDLE_OFFSET+PADDLE_HEIGHT+BALL_DIAMETER)) //Makes sure Ball doesnt stick to the paddle 
            BALL.setLocation (ballPoint.getX(),GAME_HEIGHT-(PADDLE_OFFSET+PADDLE_HEIGHT+BALL_DIAMETER));
        vy = -vy;
        if (ballPoint.getX()+BALL_DIAMETER-paddlePoint.getX() <= PADDLE.getWidth()/4 && vx > 0)
            vx= -vx;
        if (ballPoint.getX()-paddlePoint.getX() >= 3*PADDLE.getWidth()/4 && vx < 0)
            vx= -vx;
    }
    
    /** Called when the Ball hits the a Brick
      * The proper noise is played and the ball is reflected as desired after the brick is removed.
      * The power up status is updated, and the labels - score and bricks are updatd.
      */
    public void BrickHit (GObject collider) {
        if (MUTE_STATUS==false) {
            if (POWER_ON!=6) {
                if (Math.random()>0.5) 
                    bounceBrick1.play(); 
                else
                    bounceBrick2.play();
            }
            else if (POWER_ON==6)
            { fireball.play();
            }
        }
        if (POWER_ON!=6) {
            if (BALL.getX()+BALL.getWidth() <= collider.getX() || BALL.getX() >= (collider.getX()+collider.getWidth()))
                vx = -vx;
            else
                vy = -vy;
        }
        BRICK_NUMBER_LEFT--;
        COMBO_COUNT++;
        SCORE+= COMBO_COUNT*(BRICK_ROWS-(int)(((Brick)collider).getY()-BRICK_Y_OFFSET)/(BRICK_HEIGHT+BRICK_SEP_V))*10;
        updateScoreLabel();
        updateBrickCounter();
        remove(collider);
        if (rgen.nextInt (0,10) <= 5 && POWER_UP==null && POWER_ON==0)
            setPowerUp(BALL.getLocation());
    }
        
        
    /** Runs the major playing component of the game */
    public void play() {
         while(NTURNS>0 && BRICK_NUMBER_LEFT!=0) {
             if (moveBall())
                 reset();
             while (PAUSE_STATUS)
                  pause(1);
             pause (SLOWNESS);
             GObject collider = getCollidingObject(BALL);
             if(collider == PADDLE) {
                 PaddleHit();
             }
             else if(collider instanceof Brick) {
                 BrickHit (collider);
             }
             currentPowerCompute();
         }
         showEndMessage (BRICK_NUMBER_LEFT==0);
         waitForClick();
         removeAll();
         run();
    }
    
    /** Does all the Power Up computations including moving power up, checking whether power up is collecting, 
      * implementing the power up with necessary message and deactivating the power up with necessary message */
    public void currentPowerCompute () {
        if (POWER_UP!=null){                 
                 movePowerUp();
                 GObject collider= getCollidingObject(POWER_UP);
                 if (collider==PADDLE || POWER_UP.getLocation().getY() > GAME_HEIGHT) {
                     if (collider==PADDLE) {
                         initHit=PADDLE_HIT_COUNT;
                         POWER_ON=randomPowerUp();
                     }
                 remove(POWER_UP);
                 POWER_UP=null;
                 }
             }
             if (PADDLE_HIT_COUNT-initHit==4 && POWER_ON>0) {
                 initHit=0;
                 removePowerUp(POWER_ON);
                 POWER_ON= 0;
             }
             if (PADDLE_HIT_COUNT-initHit==1 && POWER_UP_MESSAGE!=null) {
                 remove (POWER_UP_MESSAGE); 
                 POWER_UP_MESSAGE=null;
             }   
    }
    
    /** Generates a random Power Up and show the appropriate message */
    public int randomPowerUp() {
        if (MUTE_STATUS==false)
            powerup.play();
        String message="";
        int i=rgen.nextInt(1,6);
        switch (i) {
            case 1: 
                PADDLE.scale (2,1);
                message="PADDLE SIZE INCREASE";
                break;
            case 2:
                PADDLE.scale (0.5,1);
                message="PADDLE SIZE DECREASE";
                break;
            case 3:
                NTURNS++;
                resetLivesLabel();
                message="EXTRA LIFE";
                break;
            case 4:
                SLOWNESS+=3;
                message="SPEED DECREASE";
                break;
            case 5:
                SLOWNESS-=2;
                message="SPEED INCREASE";
                break;
            case 6:
                BALL.setFillColor(Color.red);
                message="FIREBALL MODE";
                break;
        }
        POWER_UP_MESSAGE= new GLabel (message);
        POWER_UP_MESSAGE.setColor(Color.red);
        POWER_UP_MESSAGE.setFont(new Font("Arial", Font.PLAIN, 15));
        POWER_UP_MESSAGE.move ((GAME_WIDTH-POWER_UP_MESSAGE.getWidth())/2, DROPBAR_HEIGHT/2);
        add(POWER_UP_MESSAGE);
        return i;
    }
   
    /** Removes the current Power Up and displays the appropriate message up */
    public void removePowerUp(int i) {
        String message="";
        switch (i) {
            case 1: 
                PADDLE.scale (0.5,1);
                message="Paddle size back DOWN to normal";
                break;
            case 2:
                PADDLE.scale (2.0,1);
                message="Paddle size back UP to normal";
                break;
            case 3:
                break;
            case 4:
                SLOWNESS-=3;
                message="Speed back UP to normal";
                break;
            case 5:
                SLOWNESS+=2;
                message="Speed back DOWN to normal";
                break;
            case 6:
                BALL.setFillColor(Color.black);
                message="Fireball OFF";
                break;
        }
        POWER_UP_MESSAGE= new GLabel (message);
        POWER_UP_MESSAGE.setColor(Color.red);
        POWER_UP_MESSAGE.setFont(new Font("Arial", Font.PLAIN, 15));
        POWER_UP_MESSAGE.move ((GAME_WIDTH-POWER_UP_MESSAGE.getWidth())/2, DROPBAR_HEIGHT/2);
        add(POWER_UP_MESSAGE);
        initHit=PADDLE_HIT_COUNT; 
    }
    
    /** Run the Breakout program. */
    public void run() {
        setup();
        play();
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
        double pos= Math.max( Math.min (p.getX()-(PADDLE.getWidth()/2), GAME_WIDTH - PADDLE.getWidth()), 0);
        PADDLE.setLocation((int) pos, GAME_HEIGHT-PADDLE_OFFSET-PADDLE.getHeight());
    }
    
    /** When the mouse is clicked on any of the buttons, the button actions are taken
      * If the Funk button is clicked, the status is set to true and the paddle and ball glow like disco lights
      * If the Mute button is clicked, the sounds are turned off */
    public void mouseClicked(MouseEvent e) {
        GPoint p= new GPoint(e.getPoint());
        GObject coll = getElementAt(e.getX(), e.getY());
        if (coll==FUNK_BUTTON || coll==FUNK_LABEL) {
            if (FUNK_STATUS==false) {
                FUNK_STATUS=true;
                FUNK_LABEL.setLabel("Turn Funk Off");
                FUNK_BUTTON.setRaised(false);
            }
            else if (FUNK_STATUS==true) {
                FUNK_STATUS=false;
                FUNK_LABEL.setLabel("Turn Funk On");
                PADDLE.setFillColor (Color.black);
                BALL.setFillColor (Color.black);
                FUNK_BUTTON.setRaised(true);
            }
        }
        if (coll==MUTE_BUTTON || coll==MUTE_LABEL) {
            if (MUTE_STATUS==false) {
                MUTE_STATUS=true;
                MUTE_LABEL.setLabel("Turn Mute Off");
                MUTE_BUTTON.setRaised(false);
            }
            else if (MUTE_STATUS==true) {
                MUTE_STATUS=false;
                MUTE_LABEL.setLabel("Turn Mute On");
                MUTE_BUTTON.setRaised(true);
            }
        }
        if (coll==PAUSE_BUTTON || coll==PAUSE_LABEL) {
            if (PAUSE_STATUS==false) {
                PAUSE_STATUS=true;
                PAUSE_LABEL.setLabel("Resume");
                PAUSE_BUTTON.setRaised(false);
            }
            else if (PAUSE_STATUS==true) {
                PAUSE_STATUS=false;
                PAUSE_LABEL.setLabel("Pause");
                PAUSE_BUTTON.setRaised(true);
            }
        }
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
           super (w,h);
    }
    /** Constructor: a new brick at (x,y) with width w and height h*/
    public Brick(double x, double y, double w, double h) {
             super (x,y,w,h);
    }
}