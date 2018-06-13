package gprog;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


/**
 * Created by PG-Project on 12.05.2018.
 */
public class Audio {

	private static Clip clip;

	
     public static void music(String track)
    {
        String trackname = track;


        new Thread(new Runnable() {
            @Override
            public void run() {

                Media sound = new Media(new File(trackname).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                 mediaPlayer.play();


            }
        }).start();
    }
    public static void musicLoop(String track)
    {
    	final String trackname = track;

        new Thread(new Runnable()
        {
        @Override
        public void run()
        {
            while (true)

            { try {
                clip = AudioSystem.getClip();
                AudioInputStream inputstream = AudioSystem.getAudioInputStream(new File(trackname));
                clip.open(inputstream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                Thread.sleep(clip.getMicrosecondLength());
            }catch (Exception e) {
            e.printStackTrace();
            }
        }
        }
        }).start();

    }

    public static void stopPlay()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    if (clip != null) {
                        clip.stop();
                        clip.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }}).start();

    }

}
