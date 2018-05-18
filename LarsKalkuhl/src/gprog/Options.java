package gprog;

import javafx.beans.value.ObservableValue;

import java.awt.BorderLayout;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Options{

	private Button visuals;
	private Button audio;
	private Button controls;
	private Button backToStart;



	public Options(BorderPane oldBorder,Stage stage, Scene title){
		//Layout
		
		BorderPane border = new BorderPane();
		
		border.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

		Label l_gameName = new Label("NameEinesGames");
		l_gameName.setStyle("-fx-font: 36 arial;");
		border.setTop(l_gameName);
		border.setAlignment(l_gameName, Pos.CENTER);
		border.setMargin(l_gameName, new Insets(50,10,10,0));

		VBox vbox = new VBox();
		visuals = new Button("Visuals");
		audio = new Button("Audio");
		controls = new Button("Controls");
		backToStart = new Button("<- Back");
		visuals.setBackground(null);
		audio.setBackground(null);
		controls.setBackground(null);
		backToStart.setBackground(null);
		visuals.setStyle("-fx-font: 24 arial;");
		audio.setStyle("-fx-font: 24 arial;");
		controls.setStyle("-fx-font: 24 arial;");
		backToStart.setStyle("-fx-font: 24 arial;");
		vbox.getChildren().addAll(visuals, audio, controls, backToStart);
		vbox.setAlignment(Pos.CENTER);
		vbox.setMargin(visuals, new Insets(0,10,10,10));
		vbox.setMargin(audio, new Insets(0,10,10,10));
		vbox.setMargin(controls, new Insets(0,10,10,10));
		vbox.setMargin(backToStart, new Insets(0,10,10,0));
		border.setCenter(vbox);
		
		title.setRoot(border);

		backToStart.setOnAction(ev -> title.setRoot(oldBorder));
		visuals.setOnAction(ev -> new Visuals(border,stage, title));

	}
	
}

class Visuals {

	public Visuals(BorderPane oldBorder, Stage stage, Scene title)
	{
		BorderPane border = new BorderPane();
		
		border.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

		Label l_gameName = new Label("NameEinesGames");
		l_gameName.setStyle("-fx-font: 36 arial;");
		border.setTop(l_gameName);
		border.setAlignment(l_gameName, Pos.CENTER);
		border.setMargin(l_gameName, new Insets(50,10,10,0));

		VBox vbox = new VBox();
		CheckBox fullscreen = new CheckBox("Fullscreen");
		Button back = new Button("<- Back");
		fullscreen.setBackground(null);
		final ToggleGroup toggle = new ToggleGroup();




		back.setBackground(null);
		fullscreen.setStyle("-fx-font: 24 arial;");
		back.setStyle("-fx-font: 24 arial;");
		vbox.getChildren().addAll(fullscreen, back);
		vbox.setAlignment(Pos.CENTER);
		vbox.setMargin(fullscreen, new Insets(0,10,10,10));
		vbox.setMargin(back, new Insets(0,10,10,0));
		border.setCenter(vbox);

		title.setRoot(border);
		
		fullscreen.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (fullscreen.isSelected()) {
					stage.setFullScreen(true);

				}else if (!fullscreen.isSelected()){
					stage.setFullScreen(false);
				}
			}
		});
		back.setOnAction(ev -> title.setRoot(oldBorder));
	}
}
