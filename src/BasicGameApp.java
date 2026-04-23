
//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.text.AttributedString;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BasicGameApp implements Runnable, KeyListener, MouseListener {

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    // Game Images
    public Image BackgroundPic;
    public Image MonkeyPic;
    public Image ParrotPic;
    public Image LionPic;
    public Image HunterPic;
    public Image endingPic;


    //Game objects
    private Monkey monkey;
    private Parrot parrot;
    private Lion lion;
    private Hunter hunter;
    private boolean gameOver = false;


    // Main method
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }

    // Constructor
    public BasicGameApp() {

        setUpGraphics();

        //variable and objects
        //create (construct) the objects needed for the game and load up
        BackgroundPic = Toolkit.getDefaultToolkit().getImage("Background.png");
        LionPic = Toolkit.getDefaultToolkit().getImage("Lion.png");
        MonkeyPic = Toolkit.getDefaultToolkit().getImage("Monkey.png");
        ParrotPic = Toolkit.getDefaultToolkit().getImage("Parrot.png");
        HunterPic = Toolkit.getDefaultToolkit().getImage("Hunter.png");
        endingPic = Toolkit.getDefaultToolkit().getImage("EndingPic.png");

        monkey = new Monkey(100, 100);
        parrot = new Parrot(300, 200);
        lion = new Lion(500, 400);
        hunter = new Hunter(600, 300);


    }// BasicGameApp()


    // Method
    //Repeats while the game is running
    public void run() {

        //for the moment we will loop all methods forever
        while (true) {
            moveThings();
            crashing();
            render();
            pause(20);
            gameOver();
        }
    }

    // Method
    // Moves all the game objects
    public void moveThings() {
        monkey.move();
        parrot.move();
        lion.move();
        hunter.move();
    }

    // Method that checks for collisions
    public void crashing() {


        if (hunter.isAlive && lion.isAlive && hunter.hitbox.intersects(lion.hitbox)) {
            System.out.println("Lion died :(");
            lion.isAlive = false;
        }
        // Parrot shrinks lion when they crash
        if (lion.hitbox.intersects(parrot.hitbox) && parrot.isAlive) {
            if (lion.width >= 20) {// shrinks
                lion.width -= 5;
                lion.height -= 5;
                lion.updateHitbox();
            }
            System.out.println("Checking collision..");
            System.out.println(lion.hitbox);
            System.out.println(hunter.hitbox);
        }
    }


    //Method
    //Pauses the game loop
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    // Method
//Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        canvas.addKeyListener(this);
        canvas.addMouseListener(this);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }


    //Method that draws all game objects on the screen using buffer strategy
    private void render()
    {

        if (bufferStrategy == null) {
            canvas.createBufferStrategy(2);
            bufferStrategy = canvas.getBufferStrategy();
            return;
        }
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //Draws the background Pic first
        g.drawImage(BackgroundPic, 0, 0, WIDTH, HEIGHT, null);

        //Draws the image of the shark only if it is alive
        if (monkey.isAlive == true) {
            g.drawImage(MonkeyPic, monkey.xpos, monkey.ypos, monkey.width, monkey.height, null);
        }
        //Draws the image of plastic
        if (lion.isAlive) {
            g.drawImage(LionPic, lion.xpos, lion.ypos, lion.width, lion.height, null);
        }

        //Draws the image of Seaweed
        if (parrot.isAlive) {
            g.drawImage(ParrotPic, parrot.xpos, parrot.ypos, parrot.width, parrot.height, null);

        }
        //Draws the image of Hunter
        if (hunter.isAlive) {
            g.drawImage(HunterPic, hunter.xpos, hunter.ypos, hunter.width, hunter.height, null);

        }
        if (gameOver == true) {
            g.drawImage(endingPic, 0, 0, WIDTH, HEIGHT, null);
        }
        g.dispose();
        bufferStrategy.show();

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key typed " + e.getKeyCode());

        //UP ARROW is 38
        if (e.getKeyCode() == 38) {
            System.out.println("pressed up arrow");
            lion.ypos = lion.ypos - 20;
            lion.dy = -Math.abs(lion.dy);
        }

        //DOWN ARROW is 40
        System.out.println("key typed " + e.getKeyCode());
        if (e.getKeyCode() == 40) {
            System.out.println("pressed down arrow");
            // astro.ypos = astro.ypos - 20;
            lion.dy = Math.abs(lion.dy);
        }
        //Right ARROW is 39
        System.out.println("key typed " + e.getKeyCode());
        if (e.getKeyCode() == 39) {
            System.out.println("pressed right arrow");
            // astro.ypos = astro.ypos - 20;
            lion.dx = Math.abs(lion.dx);
        }
        //Left ARROW is 37
        System.out.println("key typed " + e.getKeyCode());
        if (e.getKeyCode() == 37) {
            System.out.println("pressed left arrow");
            // astro.ypos = astro.ypos - 20;
            lion.dx = -Math.abs(lion.dx);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key typed" + e.getKeyCode());
        //UP ARROW 38
        if (e.getKeyCode() == 38) {
            System.out.println("pressed up arrow");
            lion.dy = -2;
        }
        //UP ARROW 40
        if (e.getKeyCode() == 40) {
            System.out.println("pressed down arrow");
            lion.dy = 2;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getPoint());
        lion.xpos = e.getX();
        lion.ypos = e.getY();
        lion.updateHitbox();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("entered!!!!!!!");
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //Method that draws ending pic when both the shark and fish have died
    public void gameOver() {
        if (lion.isAlive == false) {
            gameOver = true;
        }
    }
}


