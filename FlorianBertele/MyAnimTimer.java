import javafx.animation.AnimationTimer;

/**
 * Created by Florian on 13.04.2018.
 */
public abstract class MyAnimTimer extends AnimationTimer {
    private long sleepNs=0;
    long prevTime =0;
    public MyAnimTimer(long sleepMs){
        this.sleepNs=sleepMs*1_000_000;
    }

    @Override
    public void handle(long now) {
        if((now-prevTime)<sleepNs){
            return;
        }
        prevTime=now;
        handle();
    }
    public abstract void handle();
}
