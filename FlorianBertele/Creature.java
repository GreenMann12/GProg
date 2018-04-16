import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by Florian on 13.04.2018.
 */
public abstract class Creature {
    public double xCoord;
    public double yCoord;
    public double xVel;
    public double yVel;
    public int health;
    public Image image;

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    public double getxVel() {
        return xVel;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }

    public void addxVel(double xVel){
        this.xVel+=xVel;
    }

    public void addyVel(double yVel){
        this.yVel+=yVel;
    }

    public double getyVel() {
        return yVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(image,xCoord,yCoord);
    }
    public void update(){
        xCoord=xCoord+(xVel);
        yCoord=yCoord+(yVel);
    }
    public void stop(){
        xVel=0;
        yVel=0;
    }
    public String toString(){
        return "x/y Coord: "+getxCoord()+"/"+getyCoord()+" x/y Vel: "+getxVel()+"/"+getyVel();
    }
}
