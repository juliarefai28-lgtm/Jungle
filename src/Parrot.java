import java.awt.*;

public class Parrot {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public int plastic;
    public boolean isAlive;//a boolean to denote if the hero is alive or dead.
    public Rectangle hitbox;



    //Constructor that declares variables
    public Parrot(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx = 5;
        dy = 4;
        width = 70;
        height = 70;
        isAlive = true;
        hitbox = new Rectangle(xpos, ypos, width, height);
    }

    //The move method.Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {

        if (xpos >= 1000) {//wraps around screen horizontally off the right edge
            xpos = 0 - width;
        }
        if (ypos <= 0) {//wraps around screen vertically off the top edge
            ypos = 699;
        }
        if (ypos >= 700) {// wraps around screen vertically off the bottom edge
            ypos = 1;
        }

        xpos = xpos + dx;
        ypos = ypos + dy;

        hitbox = new Rectangle(xpos, ypos, width, height);
        hitbox.setBounds(xpos, ypos, width, height);

    }
}
