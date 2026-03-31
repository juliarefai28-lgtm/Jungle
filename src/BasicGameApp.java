
//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.text.AttributedString;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BasicGameApp implements Runnable {

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;


    // Game Images
    public Image MonkeyPic;
    public Image ParrotPic;
    public Image LionPic;

    //Game objects
    private Monkey monkey;
    private Parrot parrot;
    private Lion lion;


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
        LionPic = Toolkit.getDefaultToolkit().getImage("Lion.png");
      MonkeyPic = Toolkit.getDefaultToolkit().getImage("Monkey.png");
      ParrotPic = Toolkit.getDefaultToolkit().getImage("Parrot.png");


        //Creates objects in starting positions
        shark = new Shark(40, 20);
        fish = new Fish(200, 200);
        plastic = new Plastic(WIDTH / 2, HEIGHT / 2);
        seaweed = new Seaweed(400, 500);


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
        }
    }

    // Method
    // Moves all the game objects
    public void moveThings() {
        Monkey.move();
        Parrot.move();
         Lion.move();
        GameOver();
    }

    // Method that checks for collisions
    public void crashing() {

        // Shark kills fish when they crash
        if (Lion.isAlive && Monkey.isAlive && Lion.hitbox.intersects(monkey.hitbox)) {
            System.out.println("Fish died :(");
            monkey.isAlive = false;
        }
        // Plastic shrinks shark when they crash
        if (plastic.hitbox.intersects(shark.hitbox) && shark.isAlive) {
            shark.shrink();
            if (shark.width <= 20) {// shrinks
                shark.isAlive = false;
                sharkLives--;//removes shark heart when shark dies
            }
        }
        //Seaweed increases fish when they crash
        if (seaweed.hitbox.intersects(fish.hitbox) && shark.isAlive) {
            fish.increase();
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
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        if (gameOver) {
            g.drawImage(endingPic, 0, 0, WIDTH, HEIGHT, null);
            g.dispose();
            bufferStrategy.show();
            return;
        }
        //Draws the background Pic first
        g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);

        //Draws the image of the fish only if it is alive
        if (fish.isAlive == true) {
            g.drawImage(fishPic, fish.xpos, fish.ypos, fish.width, fish.height, null);
        }
        //Draws the image of the shark only if it is alive
        if (shark.isAlive == true) {
            g.drawImage(sharkPic, shark.xpos, shark.ypos, shark.width, shark.height, null);
        }
        //Draws the image of plastic
        g.drawImage(plasticPic, plastic.xpos, plastic.ypos, plastic.width, plastic.height, null);

        //Draws the image of Seaweed
        g.drawImage(seaweedPic, seaweed.xpos, seaweed.ypos, seaweed.width, seaweed.height, null);

        // Draw shark hearts at top left corner
        for (int i = 0; i < sharkLives; i++) {
            g.drawImage(heartPic, 10 + i * 40, 10, 30, 30, null);
        }

        //Draws the fish heart next to the shark heart
        for (int i = 0; i < fishLives; i++) {
            g.drawImage(heartPic, 10 + i * 40, 50, 30, 30, null);
        }

        g.dispose();

        bufferStrategy.show();
    }
    //Method that draws ending pic when both the shark and fish have died
    public void GameOver () {
        if (shark.width <= 20 && fish.isAlive == false) {
            System.out.println("All animals are dead!");
            gameOver = true;
        }
    }
}
