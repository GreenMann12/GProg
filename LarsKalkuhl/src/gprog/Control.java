package gprog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Control {

	World world = null;
	Inventory inventory = new Inventory();
	Save savefile = new Save();
	String worldName = "";
	
	public Control(Stage stage, String name, String seed){
		worldName = name;
		world = new World(stage, this, null);
	}
	
	public Control(Stage stage, Save save){
		savefile = save;
		worldName = savefile.getgameName();
		world = new World(stage, this, savefile);
	}
	
	public int[][] getInventory(){
		
		//verknüpfung mit inventar klasse (Patrick2)
		
		return inventory.getInventory();
	}
	
	public void setInventory(int[][] inventory){
		this.inventory.setInventory(inventory);
	}
	
	public boolean inventoryIsFull(){
		
		//nur zu test zwecken
		if (true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void inventoryAddItem(int id){
		inventory.addItem(id);
	}
	
	public void inventoryRemoveItem(int pos){
		inventory.removeItem(pos);
	}
	
	
	/////schauen ob abbauen möglich ist////////////////////////////////////////////////////
	public boolean minePossible(int map, int invPos){
		if (map == 1 || map == 11) {
			return true;
		}
		else if (map > 1 && invPos == 12) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkMinePos(int XBlockPos, int YBlockPos, int XCharPos, int YCharPos){
		if (XBlockPos > XCharPos - 6 && XBlockPos < XCharPos + 5 && YBlockPos > YCharPos - 5 && YBlockPos < YCharPos + 7) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	//////Monster erstellen//////////////////////////////////////////////////////////////////
	public Monster[] createMonster(){
		Monster[] monster = new Monster[3];
		for (int i = 0; i < monster.length; i++) {
			monster[i] = new Monster();
		}
		return monster;
	}
	
	///////Inventar Position//////////////////////////////////////////////////////////////
	public boolean inventarPos(int x, int y){
		if (x < 250 && y < 125) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/////Speichern///////////////////////////////////////////////////////////////////////////
	public void saveGame(int[][] map, int x, int y){
		savefile.saveData(worldName, map, x, y, inventory.getInventory());
		
		try {
			File f = new File("src/saves/"+worldName+".save");
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(savefile);
			oos.close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, "Fehler beim speichern!", ButtonType.OK, ButtonType.CANCEL);
			alert.showAndWait();
		}
	}
}
