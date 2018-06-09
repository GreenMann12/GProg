package gprog;

import java.io.Serializable;

import gprog.Items.Item;

public class Save implements Serializable{

	private String gameName;
	private Item[][] map;
	private int x;
	private int y;
	private ItemStack[] inventory;
	
	public Save(){
		
	}
	
	public void saveData(String name, Item[][] map, int x, int y, ItemStack[] inventory){
		this.gameName = name;
		this.map = map;
		this.x = x;
		this.y = y;
		this.inventory = inventory;
	}
	
	public String getgameName(){
		return gameName;
	}
	
	public Item[][] getMap(){
		return map;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public ItemStack[] getInventory(){
		return inventory;
	}
}
