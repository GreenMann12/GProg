package gprog;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Draw {
	private Image night;
	private Image heroLife;
	private Image block[] = new Image[12];
	private Image item[] = new Image[17];     //nur zu test zwecken
	private Image charakter[] = new Image[3];
	private Image inventory[] = new Image[2];
	private Image sword[] = new Image[2];
	
	public Draw(){
		//nacht
		night = new Image(Draw.class.getResourceAsStream("../image/block00.png"), 15, 15, false, false);
		//heroLife
		heroLife = new Image(Draw.class.getResourceAsStream("../image/heart.png"), 20, 20, false, false);
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
				if (i < block.length) {
					item[i] = new Image(Draw.class.getResourceAsStream("../image/block"+i+".png"), 15, 15, false, false);
				}
				else {
					item[i] = new Image(Draw.class.getResourceAsStream("../image/item"+(i-block.length)+".png"), 15, 30, false, false);
				}
			} catch (Exception e) {
				item[i] = null;
			}
		}
		//inventory
		inventory[0] = new Image(Draw.class.getResourceAsStream("../image/inventory1.png"), 249, 32, false, false);
		inventory[1] = new Image(Draw.class.getResourceAsStream("../image/inventory2.png"), 249, 125, false, false);
		//sword
		for (int i = 0; i < sword.length; i++) {
			try {
					sword[i] = new Image(Draw.class.getResourceAsStream("../image/sword"+i+".png"), 30, 15, false, false);
			} catch (Exception e) {
				sword[i] = null;
			}
		}
	}
	
	///////Bekommen der einzelnen Bilder////////////////////////////////////////////// 
	
	public int getBlock(){
		return block.length;
	}
	
	public Image loadNightTexture(){
		return  night;
	}
	
	public Image loadHeroLife(){
		return  heroLife;
	}
	
	public Image loadBlockTexture(int id){
        return block[id];
    }
	
	public Image loadItemTexture(int id){
		return item[id];
    }
	
	public Image loadItemInventoryTexture(int id){
        ImageView iv = new ImageView(item[id]);
		iv.setFitHeight(30);
		iv.setFitWidth(30);
		return iv.snapshot(null, null);
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
	
	public Image loadSwordTexture(int id){
		return sword[id];
	}
}
