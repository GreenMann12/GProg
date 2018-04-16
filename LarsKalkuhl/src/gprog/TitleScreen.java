package gprog;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TitleScreen extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//Layout
		BorderPane borderPane = new BorderPane();
		borderPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		
		Label l_gameName = new Label("NameEinesGames");
		l_gameName.setStyle("-fx-font: 36 arial;");
		borderPane.setTop(l_gameName);
		borderPane.setAlignment(l_gameName, Pos.CENTER);
		borderPane.setMargin(l_gameName, new Insets(50,10,10,0));
		
		VBox vbox = new VBox();
		Button b_startGame = new Button("Neues Spiel");
		Button b_loadGame = new Button("Spiel Laden");
		Button b_options = new Button("Optionen");
		Button b_exit = new Button("Spiel Verlassen");
		b_startGame.setBackground(null);
		b_loadGame.setBackground(null);
		b_options.setBackground(null);
		b_exit.setBackground(null);
		b_startGame.setStyle("-fx-font: 24 arial;");
		b_loadGame.setStyle("-fx-font: 24 arial;");
		b_options.setStyle("-fx-font: 24 arial;");
		b_exit.setStyle("-fx-font: 24 arial;");
		vbox.getChildren().addAll(b_startGame, b_loadGame, b_options, b_exit);
		vbox.setAlignment(Pos.CENTER);
		vbox.setMargin(b_startGame, new Insets(0,10,10,10));
		vbox.setMargin(b_loadGame, new Insets(0,10,10,10));
		vbox.setMargin(b_options, new Insets(0,10,10,10));
		vbox.setMargin(b_exit, new Insets(0,10,10,0));
		borderPane.setCenter(vbox);
		
		//EventHandler
		b_startGame.setOnAction(e -> new Control(stage));
		//b_loadGame.setOnAction(e ->);
		//b_options.setOnAction(e ->);
		b_exit.setOnAction(e -> Platform.exit());
		
		Scene scene = new Scene(borderPane, 600, 600);
		
		stage.setMinHeight(500);
		stage.setMinWidth(500);
	    stage.setTitle("Name des Spiels");
	    stage.setScene(scene);
		stage.show();
	}

}
