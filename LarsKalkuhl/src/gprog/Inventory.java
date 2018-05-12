package gprog;

public class Inventory {

	private int[][] inventory = new int[32][2];
	
	public Inventory(){
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 2; j++) {
				inventory[i][j] = 0;
			}
		}
	}
	
	public int[][] getInventory(){
		return inventory;
	}
	
	public void addItem(int id){
		boolean found = false;
		for (int i = 0; i < 32; i++) {
			if (inventory[i][0] == id) {
				if (inventory[i][1] < 64) {
					inventory[i][1]++;
					found = true;
					i=32;
				}
			}
		}
		
		if (found == false) {
			for (int i = 0; i < 32; i++) {
				if (inventory[i][0] == 0) {
					inventory[i][0] = id;
					inventory[i][1]++;
					i=32;
				}
			}
		}
	}
	
	public void removeItem(int pos){
		if (inventory[pos][0] != 0) {
			inventory[pos][1]--;
			if (inventory[pos][1] == 0) {
				inventory[pos][0] = 0;
			}
		}
	}
	
}
