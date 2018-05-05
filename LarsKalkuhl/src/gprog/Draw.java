package gprog;

import javafx.scene.image.Image;

public class Draw {
	
	private Image block[] = new Image[11];
	private Image charakter[] = new Image[1];
	
	public Draw(){
		for (int i = 0; i < block.length; i++) {
			try {
					block[i] = new Image(Draw.class.getResourceAsStream("../image/block"+i+".png"), 15, 15, false, false);
			} catch (Exception e) {
				block[i] = null;
			}
		}
		for (int i = 0; i < charakter.length; i++) {
			try {
				charakter[i] = new Image(Draw.class.getResourceAsStream("../image/char"+i+".png"), 30, 50, false, false);
			} catch (Exception e) {
				charakter[i] = null;
			}
		}
	}
	
	public Image loadBlockTexture(int id){
        return block[id];
    }
	
	public Image loadCharTexture(int id){
		return charakter[id];
	}
	
	public Image loadLoading(int x, int y){
		return new Image(Draw.class.getResourceAsStream("../image/loading.png"), x, y, false, false);
	}
	
}
