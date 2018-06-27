package gprog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;

import javax.xml.ws.Response;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
		BorderPane.setAlignment(l_gameName, Pos.CENTER);
		BorderPane.setMargin(l_gameName, new Insets(50,10,10,0));
		
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
		VBox.setMargin(b_startGame, new Insets(0,10,10,10));
		VBox.setMargin(b_loadGame, new Insets(0,10,10,10));
		VBox.setMargin(b_options, new Insets(0,10,10,10));
		VBox.setMargin(b_exit, new Insets(0,10,10,0));
		borderPane.setCenter(vbox);
		
		Audio.musicLoop("src/Audio/titleScreen.wav");
		
		Scene scene = new Scene(borderPane, 600, 600);
		
		//EventHandler
		b_startGame.setOnAction(e -> newGame(stage, scene));
		b_loadGame.setOnAction(e -> loadGame(stage, scene, borderPane));
		b_options.setOnAction(e -> new Options(borderPane ,stage, scene));
		b_exit.setOnAction(e -> System.exit(0));
		
		stage.setOnCloseRequest(e -> System.exit(0));
		
		stage.setHeight(628);
		stage.setWidth(806);
		stage.setTitle("Name des Spiels");
	    stage.setScene(scene);
		stage.show();
	}
	
	private void newGame(Stage stage, Scene scene){
		//Audio.music stoppen
		BorderPane borderPane = new BorderPane();
		borderPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		GridPane gpNewGame = new GridPane();
		TextField tf_Name = new TextField();
		TextField tf_Seed = new TextField("2349120492");
		Button b_start = new Button("Start");
		
		gpNewGame.add(new Text("Name: "), 0, 0);
		gpNewGame.add(tf_Name, 1, 0);
		gpNewGame.setMargin(tf_Name, new Insets(0,10,10,10));
		gpNewGame.add(new Text("Seed: "), 0, 1);
		gpNewGame.add(tf_Seed, 1, 1);
		gpNewGame.setMargin(tf_Seed, new Insets(10,10,10,10));
		gpNewGame.add(b_start, 1, 2);
		gpNewGame.setMargin(b_start, new Insets(10,10,10,10));
		
		gpNewGame.setAlignment(Pos.CENTER);
		
		borderPane.setCenter(gpNewGame);
		
		scene.setRoot(borderPane);
		
		b_start.setOnAction(e -> startNewGame(stage, scene, tf_Name.getText(), tf_Seed.getText()));
	}
	
	private void startNewGame(Stage stage, Scene scene, String name, String seed){
		Audio.stopPlay();
		new Control(stage, name, seed);
	}
	
	private void loadGame(Stage stage, Scene scene, BorderPane bpOld){
		//Audio.music stoppen
		
		BorderPane borderPane = new BorderPane();
		borderPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		GridPane gridPane = new GridPane();
		
		//Tabelle
		String[] s_savefile = getSavefiles();
		ObservableList<String> savefile = FXCollections.observableArrayList(s_savefile);
		
		ListView<String> listview = new ListView<String>();
		listview.setPrefSize(200, 400);
		listview.setItems(savefile);
		//
		
		//Button
		Button b_zurueck = new Button("Zur�ck");
		//
		
		//EventHandler
		b_zurueck.setOnAction(e -> scene.setRoot(bpOld));
		listview.getSelectionModel().selectedItemProperty().addListener(
		        (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
		        	final Save save = loadSave(new_val);
		        	if (save != null) {
		        		Audio.stopPlay();
		        		ButtonType bt_del = new ButtonType("L�schen", ButtonBar.ButtonData.OK_DONE);
		        		ButtonType bt_load = new ButtonType("Laden", ButtonBar.ButtonData.OK_DONE);
		        		Alert alert = new Alert(AlertType.NONE, "Laden oder L�schen?", bt_load, bt_del,ButtonType.CANCEL);
		        		alert.showAndWait().ifPresent(response -> {
		        			if (response == bt_load) {
		        				new Control(stage, save);
							}
		        			else if(response == bt_del){
		        				Alert lalert = new Alert(AlertType.WARNING, "Wirklich l�schen?", ButtonType.OK, ButtonType.CANCEL);
		        				lalert.showAndWait();
		        				if (lalert.getResult() == ButtonType.OK) {
		        					try {
			        					File f = new File("src/saves/"+new_val+".save");
			        					if (f.exists()) {
			        						f.delete();
			        					}
			        					else{
			        						Alert walert = new Alert(AlertType.ERROR, "Fehler beim l�schen!", ButtonType.OK, ButtonType.CANCEL);
			        						walert.showAndWait();
			        					}
			        				} catch (Exception e) {
			        					Alert walert = new Alert(AlertType.ERROR, "Fehler beim l�schen!", ButtonType.OK, ButtonType.CANCEL);
			        					walert.showAndWait();
			        					loadGame(stage, scene, bpOld);
			        				}
		        				}
		        				loadGame(stage, scene, bpOld);
		        			}
		        			else if(response == ButtonType.CANCEL){
		        				loadGame(stage, scene, bpOld);
		        			}
		        		});
					}
		        	else {
		        		Alert alert = new Alert(AlertType.ERROR, "Fehler beim laden test!", ButtonType.OK, ButtonType.CANCEL);
		    			alert.showAndWait();
					}
		    });
		
		gridPane.add(listview, 0, 0);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setMargin(listview, new Insets(10,10,10,10));
		
		borderPane.setCenter(gridPane);
		borderPane.setMargin(gridPane, new Insets(10,10,10,10));
		borderPane.setBottom(b_zurueck);
		borderPane.setAlignment(b_zurueck, Pos.CENTER);
		borderPane.setPadding(new Insets(10,10,10,10));
		
		scene.setRoot(borderPane);
	}
	
	public String[] getSavefiles(){
		String[] savefile = null;
		try {
			File f = new File("src/saves/");
			File[] files = f.listFiles(new FilenameFilter(){
				public boolean accept(File f, String name){
					return name.endsWith("save");
				}
			});
			savefile = new String[files.length];
			for (int i = 0; i < files.length; i++) {
				savefile[i] = String.valueOf(files[i].getName().substring(0,files[i].getName().lastIndexOf(46)));
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, "Fehler beim laden!", ButtonType.OK, ButtonType.CANCEL);
			alert.showAndWait();
		}
		
		return savefile;
	}
	
	public Save loadSave(String name){
		try {
			Save save = null;
			File f = new File("src/saves/"+name+".save");
			File file = f.getAbsoluteFile();
			
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			save = (Save) ois.readObject();
			ois.close();
			return save;
		} catch (Exception e) {
			return null;
		}
	}
}
