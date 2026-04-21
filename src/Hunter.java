import java.awt.*;

public class Hunter {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public int fish;
    public boolean isAlive;//a boolean to denote if the hero is alive or dead.
    public Rectangle hitbox;

    //This is the constructor that declares the variables
    public Hunter (int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx = 10;
        dy = 4;
        width = 100;
        height = 100;
        isAlive = true;
        hitbox = new Rectangle(xpos, ypos, width, height);
    }

    //The move method. Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {

        xpos = xpos + dx;
        ypos = ypos + dy;
        if (xpos >= 1000 - width) {//bounce of right wall
            dx = -dx;
        }
        if (ypos >= 700 - height) {//bounce of bottom wall
            dy = -dy;
        }
        if (xpos < 0) {// Bounce of Left wall
            dx = -dx;
        }
        if (ypos < 0) {//Bounce of top wall
            dy = -dy;
        }

        hitbox = new Rectangle(xpos, ypos, width, height);

    }

    // Method that increases the fish when it hits the seaweed
    public void increase() {

        if (width > 2 && height > 2) {
            width += 4;
            height += 4;
            System.out.println("Fish gets Bigger!");
        }
    }
}
