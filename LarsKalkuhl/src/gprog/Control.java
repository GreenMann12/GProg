package gprog;

import javafx.stage.Stage;

public class Control {

	World world = null;
	Inventory inventory = new Inventory();
	
	public Control(Stage stage){
		world = new World(stage, this);
	}
	
	public int[][] getInventory(){
		
		//verknüpfung mit inventar klasse (Patrick2)
		
		return inventory.getInventory();
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
	
}
