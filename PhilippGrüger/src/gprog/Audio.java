package gprog;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.time.Duration;


/**
 * Created by PG-Project on 12.05.2018.
 */
public class Audio {

    private static Clip clip;
    static Slider slider2 = new Slider(0,1,0);

    public static void music(String track){
        String trackname = track;

       //if(!clip.isActive()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {

                            try {
                                clip = AudioSystem.getClip();
                                AudioInputStream inputstream = AudioSystem.getAudioInputStream(new File(trackname));
                                clip.open(inputstream);
                                clip.start();
                                System.out.println(track);


                                clip.loop(0);
                                Thread.sleep(clip.getMicrosecondLength());


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                    }
                }
            }).start();
        }
   // }
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

    final double fontSize = 30.0;

    public Audio(BorderPane _oldBorder, Stage _stage, Scene _title) {
        BorderPane border = new BorderPane();


        border.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        Label l_gameName = new Label("NameEinesGames");

        l_gameName.setStyle("-fx-font: 36 arial;");
        border.setTop(l_gameName);
        border.setAlignment(l_gameName, Pos.CENTER);
        border.setMargin(l_gameName, new Insets(50,10,10,0));
        _stage.setResizable(true);


        VBox vbox = new VBox();
        Label music = new Label("Music");
        music.setFont(new Font(fontSize));
        RadioButton soundOn = new RadioButton("On");
        RadioButton soundOff = new RadioButton("Off");
        Label volume = new Label("Volume");
        Button back = new Button("<- Back");
        soundOn.setBackground(null);
        soundOff.setBackground(null);
        final ToggleGroup toggle = new ToggleGroup();


        soundOn.setToggleGroup(toggle);
        soundOff.setToggleGroup(toggle);


        back.setBackground(null);
        soundOn.setStyle("-fx-font: 24 arial;");
        soundOff.setStyle("-fx-font: 24 arial;");


        back.setStyle("-fx-font: 24 arial;");
        vbox.getChildren().addAll(music, soundOn,soundOff,volume,slider2, back);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMargin(soundOn, new Insets(0,10,10,10));
        vbox.setMargin(soundOff, new Insets(0,10,10,10));
        vbox.setMargin(back, new Insets(0,10,10,0));
        vbox.setMargin(volume, new Insets(0,10,10,10));
        vbox.setMargin(slider2, new Insets(0,200,10,200));


        border.setCenter(vbox);

        _title.setRoot(border);

        toggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(soundOn.isSelected())
                {
                    Audio.musicLoop("src/Audio/titleScreen.wav");
                }
                else if(soundOff.isSelected())
                {
                    Audio.stopPlay();
                }
            }
        });

        back.setOnAction(ev -> _title.setRoot(_oldBorder));
    }
}


