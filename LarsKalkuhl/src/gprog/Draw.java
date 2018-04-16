package gprog;

import javafx.scene.image.Image;

public class Draw {
	
	private Image block[] = new Image[11];
	
	public Draw(){
		for (int i = 0; i < block.length; i++) {
			try {
					block[i] = new Image(Draw.class.getResourceAsStream("../image/block"+i+".png"), 20, 20, false, false);
			} catch (Exception e) {
				block[i] = null;
			}
		}
	}
	
	public Image loadBlockTexture(int id){
        return block[id];
    }
	
}
