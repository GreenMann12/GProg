import javafx.scene.image.Image;

/**
 * Created by Florian on 13.04.2018.
 */
public class Character extends Creature {

    public Character (double _xC, double _yC, Image _im){
        xCoord=_xC;
        yCoord=_yC;
        xVel=0;
        yVel=0;
        this.image=_im;
        health=100;
    }


}
