import java.awt.*;

public class Coin {
    public int xpos,ypos, width, height;
    public boolean isAlive = true;
    public Rectangle hitbox;

    public Coin(int x, int y){
        xpos=x;
        ypos=y;
        width=40;
        height=40;
        hitbox=new Rectangle(xpos,ypos,width,height);
    }
    public void updateHitbox(){
        hitbox=new Rectangle(xpos,ypos,width,height);
    }
}
