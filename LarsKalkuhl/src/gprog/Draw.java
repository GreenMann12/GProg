package gprog;

import javafx.scene.image.Image;

public class Draw {
	
	private Image block[] = new Image[11];
	private Image item[] = new Image[11];     //nur zu test zwecken
	private Image charakter[] = new Image[3];
	private Image inventory[] = new Image[2];
	
	public Draw(){
		//blöcke
		for (int i = 0; i < block.length; i++) {
			try {
					block[i] = new Image(Draw.class.getResourceAsStream("../image/block"+i+".png"), 15, 15, false, false);
			} catch (Exception e) {
				block[i] = null;
			}
		}
		//charakter
		for (int i = 0; i < charakter.length; i++) {
			try {
				charakter[i] = new Image(Draw.class.getResourceAsStream("../image/char"+i+".png"), 30, 45, false, false);
			} catch (Exception e) {
				charakter[i] = null;
			}
		}
		//nur test für items
		for (int i = 1; i < item.length; i++) {
			try {
					item[i] = new Image(Draw.class.getResourceAsStream("../image/block"+i+".png"), 30, 30, false, false);
			} catch (Exception e) {
				item[i] = null;
			}
		}
		//inventory
		inventory[0] = new Image(Draw.class.getResourceAsStream("../image/inventory1.png"), 249, 32, false, false);
		inventory[1] = new Image(Draw.class.getResourceAsStream("../image/inventory2.png"), 249, 125, false, false);
	}
	
	public Image loadBlockTexture(int id){
        return block[id];
    }
	
	public Image loadItemTexture(int id){
        return item[id];
    }
	
	public Image loadCharTexture(int id){
		return charakter[id];
	}
	
	public Image loadLoading(int x, int y){
		return new Image(Draw.class.getResourceAsStream("../image/loading.png"), x, y, false, false);
	}
	
	public Image loadInventoryTexture(int id){
		return inventory[id];
	}
	
}
