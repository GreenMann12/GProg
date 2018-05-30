package gprog;


import gprog.Items.Item;
import gprog.Items.Pickaxe;

/**
 * Created by Patrick on 29.05.2018.
 */
public class Inventory {

	private ItemStack[] inventory = new ItemStack[32];

	public Inventory(){
		for (int i = 0; i < 32; i++) {
			inventory[i] = new ItemStack(null,0);
		}
		inventory[0] = new ItemStack(new Pickaxe(),12);
		//inventory[1] = new ItemStack(new StrongerPickaxe(),13);
		//inventory[2] = new ItemStack(new StrongestPickaxe(),14);
		//inventory[2] = new ItemStack(new Axe(),15);
	}

	public ItemStack[] getInventory() {
		return inventory;
	}

	public void setInventory(ItemStack[] inventory) {
		this.inventory = inventory;
	}

	public void addItem(Item item){
		boolean found = false;
		for (int i = 0; i < 32; i++) {
			if (inventory[i].getID() == item.getID()) {
				if (inventory[i].getSize() < 64) {
					inventory[i].addItemToStack(item);
					found = true;
					i=32;
				}
			}
		}

		if (found == false) {
			for (int i = 0; i < 32; i++) {
				if (inventory[i].getID() == 0) {
					inventory[i] = new ItemStack(item, item.getID());
					i=32;
				}
			}
		}
	}

	public Item removeItem(int pos){
		Item temp = null;
		if (inventory[pos] != null) {
			temp = inventory[pos].removeItemFromStack();
			if (inventory[pos].getSize() == 0) {
				inventory[pos] = new ItemStack(null,0);
			}
		}
		return temp;
	}

	public Item getItem (int pos){
		Item temp = inventory[pos].getItem();
		return temp;
	}


}

