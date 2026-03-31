import java.awt.*;

public class Lion {

        //VARIABLE DECLARATION SECTION
        //Here's where you state which variables you are going to use.
        public String name;                //holds the name of the hero
        public int xpos;                //the x position
        public int ypos;                //the y position
        public int dx;                    //the speed of the hero in the x direction
        public int dy;                    //the speed of the hero in the y direction
        public int width;
        public int height;
        public int shark;
        public boolean isAlive;//a boolean to denote if the hero is alive or dead.
        public Rectangle hitbox;


        //This is the constructor that declares the variables
        public Lion(int pXpos, int pYpos) {
            xpos = pXpos;
            ypos = pYpos;
            dx = 10;
            dy = 4;
            width = 60;
            height = 60;
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

            if (xpos<0){dx=-dx;}//Bounce of left wall
            if(ypos<0){dy=-dy;}// Bounce of top wall

            updateHitbox();
        }

        public void shrink() {
            //Shrinks the shark when it hits plastic
            if (width > 20 && height > 20) {
                width -= 5;
                height -= 5;
                System.out.println("Shark is Smaller!");
            } else {
                //Shark disappears when it gets too small
                isAlive = false;
                System.out.println("Shark completly dissapears");

            }
            updateHitbox();
        }
        public void updateHitbox() {
            hitbox.setBounds(xpos, ypos, width, height);
        }

}
