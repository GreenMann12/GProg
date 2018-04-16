package gprog;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Control {

	World world = null;
	
	public Control(Stage stage){
		world = new World(stage);
	}
	
}
