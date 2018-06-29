package gprog;

import javafx.beans.value.ObservableValue;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Options{

	private Button visuals;
	private Button sound;
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
		//stage.setResizable(true);

		VBox vbox = new VBox();
		visuals = new Button("Visuals");
		sound = new Button("Sound");
		controls = new Button("Controls");
		backToStart = new Button("<- Back");
		visuals.setBackground(null);
		sound.setBackground(null);
		controls.setBackground(null);
		backToStart.setBackground(null);
		visuals.setStyle("-fx-font: 24 arial;");
		sound.setStyle("-fx-font: 24 arial;");
		controls.setStyle("-fx-font: 24 arial;");
		backToStart.setStyle("-fx-font: 24 arial;");
		vbox.getChildren().addAll(visuals, sound, controls, backToStart);
		vbox.setAlignment(Pos.CENTER);
		vbox.setMargin(visuals, new Insets(0,10,10,10));
		vbox.setMargin(sound, new Insets(0,10,10,10));
		vbox.setMargin(controls, new Insets(0,10,10,10));
		vbox.setMargin(backToStart, new Insets(0,10,10,0));
		border.setCenter(vbox);
		
		title.setRoot(border);

		backToStart.setOnAction(ev -> title.setRoot(oldBorder));
		visuals.setOnAction(ev -> new Visuals(border,stage, title));
		sound.setOnAction(ev -> new Audio(border,stage,title));

	}
}

class Visuals {

	final double fontSize = 30.0;

	public Visuals(BorderPane _oldBorder, Stage _stage, Scene _title) {


		BorderPane border = new BorderPane();

		border.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

		Label l_gameName = new Label("NameEinesGames");

		l_gameName.setStyle("-fx-font: 36 arial;");
		border.setTop(l_gameName);
		border.setAlignment(l_gameName, Pos.CENTER);
		border.setMargin(l_gameName, new Insets(50,10,10,0));
		_stage.setResizable(false);


		VBox vbox = new VBox();
		Label windowsSize = new Label("Select Size");
		windowsSize.setFont(new Font(fontSize));
		RadioButton smallSize = new RadioButton("Small");
		RadioButton mediumSize = new RadioButton("Medium");
		RadioButton bigSize = new RadioButton("Big");
		Button back = new Button("<- Back");
		smallSize.setBackground(null);
		mediumSize.setBackground(null);
		bigSize.setBackground(null);
		final ToggleGroup toggle = new ToggleGroup();


		smallSize.setToggleGroup(toggle);
		mediumSize.setToggleGroup(toggle);
		bigSize.setToggleGroup(toggle);

		back.setBackground(null);
		smallSize.setStyle("-fx-font: 24 arial;");
		mediumSize.setStyle("-fx-font: 24 arial;");
		bigSize.setStyle("-fx-font: 24 arial;");
		back.setStyle("-fx-font: 24 arial;");
		vbox.getChildren().addAll(windowsSize, smallSize,mediumSize,bigSize, back);
		vbox.setAlignment(Pos.CENTER);
		vbox.setMargin(smallSize, new Insets(0,10,10,10));
		vbox.setMargin(mediumSize, new Insets(0,10,10,10));
		vbox.setMargin(bigSize, new Insets(0,10,10,10));
		vbox.setMargin(back, new Insets(0,10,10,0));
		border.setCenter(vbox);


		/*mediumScene = new Scene(border,1006,778);
		bigScene = new Scene(border,1200,810);*/

		if(_stage.getHeight() == 628 && _stage.getWidth() == 806)
			smallSize.setSelected(true);
		else if (_stage.getHeight() == 778 && _stage.getWidth() == 1006)
			mediumSize.setSelected(true);
		else if (_stage.getHeight() == 810 && _stage.getWidth() == 1200)
			bigSize.setSelected(true);

		_title.setRoot(border);

		toggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if (smallSize.isSelected()) {
					_stage.setWidth(800 + (_stage.getWidth()-_title.getWidth()));
					_stage.setHeight(600 + (_stage.getHeight()-_title.getHeight()));
					_stage.setResizable(false);
					//System.out.println("W " +_title.getWidth() + "H " + _title.getHeight());
					//System.out.println("W " +_stage.getWidth() + "H " + _stage.getHeight());

				}
				else if (mediumSize.isSelected()) {
					_stage.setWidth(1000 + (_stage.getWidth()-_title.getWidth()));
					_stage.setHeight(750 + (_stage.getHeight()-_title.getHeight()));
					_stage.setResizable(false);
					//System.out.println("W " +_title.getWidth() + "H " + _title.getHeight());
					//System.out.println("W " +_stage.getWidth() + "H " + _stage.getHeight());

				}
				else if (bigSize.isSelected()) {
					_stage.setWidth(1196 + (_stage.getWidth()-_title.getWidth()));
					_stage.setHeight(900 + (_stage.getHeight()-_title.getHeight()));
					_stage.setResizable(false);
					//System.out.println("W " +_title.getWidth() + "H " + _title.getHeight());
					//System.out.println("W " +_stage.getWidth() + "H " + _stage.getHeight());

				}
			}
		});



		back.setOnAction(ev -> _title.setRoot(_oldBorder));
	}
}

