package gprog;

import java.io.Serializable;

public class Save implements Serializable{

	private String gameName;
	private int[][] map;
	private int x;
	private int y;
	private int[][] inventory;
	
	public Save(){
		
	}
	
	public void saveData(String name, int[][] map, int x, int y, int[][] inventory){
		this.gameName = name;
		this.map = map;
		this.x = x;
		this.y = y;
		this.inventory = inventory;
	}
	
	public String getgameName(){
		return gameName;
	}
	
	public int[][] getMap(){
		return map;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int[][] getInventory(){
		return inventory;
	}
}
