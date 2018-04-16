import javafx.scene.image.Image;

public class Block {
  public int id;
    public int miningLevel;

    public static Image loadTexture(int id){
        Image image = new Image("source/blocks/default.png");

        if (id == 0){
            image = new Image("source/blocks/block0.png");
        } else if (id == 1){
            image = new Image("source/blocks/block1.png");
        } else if (id == 2){
            image = new Image("source/blocks/block2.png");
        } else if (id == 3){
            image = new Image("source/blocks/block3.png");
        } else if (id == 4){
            image = new Image("source/blocks/block4.png");
        } else if (id == 5){
            image = new Image("source/blocks/block5.png");
        } else if (id == 6){
            image = new Image("source/blocks/block6.png");
        } else if (id == 7){
            image = new Image("source/blocks/block7.png");
        } else if (id == 8){
            image = new Image("source/blocks/block8.png");
        } else if (id == 9){
            image = new Image("source/blocks/block9.png");
        } else if (id == 10){
            image = new Image("source/blocks/block10.png");
        }

        return image;
    }

}
