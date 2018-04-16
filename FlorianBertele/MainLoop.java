/**
 * Created by Florian on 11.04.2018.
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainLoop extends Application {
    Image dreck = new Image("Dreck.png");
    Character character = new Character(0,0,dreck);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group group = new Group();
        Canvas canvas = new Canvas(500,500);
        group.getChildren().add(canvas);
        Scene scene = new Scene(group);

        primaryStage.setScene(scene);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().toString().equals("W")){
                    character.setyVel(-10);
                }
                if(event.getCode().toString().equals("A")){
                    character.setxVel(-10);
                }
                if(event.getCode().toString().equals("S")){
                    character.setyVel(10);
                }
                if(event.getCode().toString().equals("D")){
                    character.setxVel(10);
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                character.stop();
            }
        });

        MyAnimTimer myAnimTimer = new MyAnimTimer(60) {
            @Override
            public void handle() {
                gc.clearRect(0,0,500,500);

                character.update();
                character.draw(gc);

            }
        };
        myAnimTimer.start();
        primaryStage.show();

    }
}
