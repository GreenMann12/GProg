package gprog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import gprog.Items.Item;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Control {

	World world = null;
	Inventory inventory = new Inventory();
	ConstructMenu constructMenu = new ConstructMenu();
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
	
	public ItemStack[] getInventory(){
		
		//verknüpfung mit inventar klasse (Patrick2)
		
		return inventory.getInventory();
	}
	
	public void setInventory(ItemStack[] inventory){
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
	
	public void inventoryAddItem(Item item){
		inventory.addItem(item);
	}
	
	public Item inventoryRemoveItem(int pos){
		Item item = inventory.removeItem(pos);
		return item;
	}
	
	
	/////schauen ob abbauen möglich ist////////////////////////////////////////////////////
	public boolean minePossible(Item map, Item invPos){

		try {
			int miningMap = map.getMiningLevel();
			int miningInvPos;
			if (invPos.getID() < 12) {
				miningInvPos = 1;
			} else {
				miningInvPos = invPos.getMiningLevel();
			}

			if (miningMap == miningInvPos) {
				return true;
			} else if (miningMap == 5) {
				if (miningInvPos == 5) {
					return true;
				} else {
					System.out.println("Mining not possible!");
					return false;
				}
			} else if (miningMap == 0) {
				System.out.println("Mining not possible!");
				return false;
			}
			else if (miningMap <  miningInvPos) {
				return true;
			}
			else {
				System.out.println("Mining not possible!");
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	public boolean checkIfConstructable (ItemStack[] inventory, ConstructMenu constructMenu, int constrPos){
		for (ItemStack itemStack : inventory) {
			if (itemStack.getID() == constructMenu.getConstruct(constrPos).getItems().get(0).getID()){
				if (itemStack.getSize() >= constructMenu.getConstruct(constrPos).getItems().size()){
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
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
	public Monster createMonster(int x, int y){
		return new Monster(x,y);
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
	public void saveGame(Item[][] map, int x, int y){
		savefile.saveData(worldName, map, x, y, inventory.getInventory());
		
		try {
			File f = new File("src/saves/"+worldName+".save");
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(savefile);
			oos.close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK, ButtonType.CANCEL);
			alert.showAndWait();
		}
	}
}
