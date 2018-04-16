import javafx.scene.image.Image;

/**
 * Created by Florian on 13.04.2018.
 */
public class Monster extends Creature {

    public Monster(double _xC, double _yC, Image _im){
        xCoord=_xC;
        yCoord=_yC;
        xVel=0;
        yVel=0;
        image=_im;
    }
}
