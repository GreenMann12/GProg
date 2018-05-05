package gprog;

import javafx.animation.AnimationTimer;

/**
 * Created by Florian on 13.04.2018.
 */
public abstract class MyAnimTimer extends AnimationTimer {
    private long sleepNs=0;
    long prevTime =0;
    int x,y;
    public MyAnimTimer(long sleepMs, int x, int y){
        this.sleepNs=sleepMs*1_000_000;
        this.x = x;
        this.y = y;
    }

    public void setXandY(int x, int y){
    	this.x = x;
    	this.y = y;
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
