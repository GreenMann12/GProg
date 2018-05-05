package gprog;

import java.util.Random;

import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class World{

	private Draw draw = new Draw();
	
	public static int heigth = 255;
	public static int width = 1000;
	int x = 0;
	int y = 40;
	
	public int[][] map = new int[width][heigth];
	public Character hero = new Character();
	public Monster[] monster;
	
	public World(Stage stage){
		loading(stage);
		generateWorld();
		showWorld(stage);
	}
	
	public void loading(Stage stage){
		VBox vb = new VBox();
		ImageView iview = new ImageView();
		iview.setImage(draw.loadLoading(800, 600));
		vb.getChildren().add(iview);
		Scene scene = new Scene(vb, 800, 600);
		stage.setScene(scene);
	}
	
	public void showWorld(Stage stage){
		
		Group group = new Group();
		Canvas canvas = new Canvas((800),(600));
		group.getChildren().add(canvas);
		
		Scene scene = new Scene(group, 800, 600);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		MyAnimTimer animTimer = new MyAnimTimer(20, x, y) {
			
			@Override
			public void handle() {
				int koordX = 0;
				int koordY = 0;
				int xChar = 0;
				int yChar = 0;
				boolean charIsDraw = false;
				for (int w = x; w < width; w++) {
					koordX = 0;
					for (int h = y; h < heigth; h++) {
						gc.drawImage(draw.loadBlockTexture(map[w][h]), koordY, koordX);
						if (charIsDraw == false && map[w][h] == 1) {
							xChar = koordX-50;
							yChar = koordY;
							charIsDraw = true;
						}
						koordX += 15;
					}
					koordY += 15;
				}
				gc.drawImage(draw.loadCharTexture(0), yChar, xChar);
			}
		};
		
		animTimer.start();
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
            public void handle(KeyEvent event) {
                if(event.getCode().toString().equals("W")){
                }
                if(event.getCode().toString().equals("A")){
                	if (x > 0) {
                		x = x-1;
					}
                	animTimer.setXandY(x, y);
                }
                if(event.getCode().toString().equals("S")){
                	if (x > 0) {
                		y = y+1;
					}
                	animTimer.setXandY(x, y);
                }
                if(event.getCode().toString().equals("D")){
                	if (x < width) {
                		x = x+1;
					}
                	animTimer.setXandY(x, y);
                }
            }
		});
		
		stage.setScene(scene);
	}
	
	
	
	
	
	
	
	
	public void generateWorld() {
        int air = 70;
        int ground = 5;
        int acth = 70;
        int numberTrees = 100;
        int numberiron = 50;
        int numbercopper = 30;
        int numbersilver = 20;
        int numbergold = 10;
        int numberwater = 5;

        Random random = new Random();

        for (int x = 0; x < width; x++) {
            int upordown = random.nextInt(5);

            if (upordown == 0 && acth < (air +  40) ){
                acth++;
            } else if (upordown == 4 && acth > (air - 40)){
                acth--;
            }

            for (int y = 0; y < heigth; y++) {
                if (y < acth){
                    map[x][y] = 0;
                } else if (y < acth+ground) {
                    map[x][y] = 1;
                } else {
                    map[x][y] = 2;
                }
            }
        }

        //<editor-fold desc="Water">
        while (numberwater != 0){
            int x = random.nextInt(width-10)+5;
            int y = 0;

            while (y < heigth && map[x][y] != 1) {
                y++;
            }

            if (y != width && y < air){
                numberwater--;

                map[x][y]=10;










            }
        }
        //</editor-fold>

        //<editor-fold desc="Trees">
        while (numberTrees != 0){
            int x = random.nextInt(width - 10) + 5;
            int y = 0;

            while (y < heigth) {
                if (map[x][y] != 1) {
                    y++;
                } else {
                    // Tree trunk
                    map[x][y - 1] = 3;
                    map[x][y - 2] = 3;
                    map[x][y - 3] = 3;
                    map[x][y - 4] = 3;
                    map[x][y - 5] = 3;

                    // Leaves

                    map[x - 1][y - 5] = 9;
                    map[x + 1][y - 5] = 9;

                    map[x][y - 6] = 9;
                    map[x - 1][y - 6] = 9;
                    map[x + 1][y - 6] = 9;
                    map[x - 2][y - 6] = 9;
                    map[x + 2][y - 6] = 9;

                    map[x][y - 7] = 9;
                    map[x - 1][y - 7] = 9;
                    map[x + 1][y - 7] = 9;

                    map[x][y - 8] = 9;

                    numberTrees--;
                    break;
                }
            }
        }
        //</editor-fold>

        // place iron ore
        placeOre(numberiron, 4);

        // place copper ore
        placeOre(numbercopper, 5);

        // place silver ore
        placeOre(numbersilver, 6);

        // place gold ore
        placeOre(numbergold, 7);
    }

    private void placeOre(int number, int oreid){
        Random random = new Random();

        for (int count = 0; count < number; count++) {
            int x = random.nextInt(width - 10) + 5;
            int y = 0;

            while (y < heigth && map[x][y] != 2) {
                y++;
            }


            if (y != heigth) {
                y = random.nextInt(heigth - (y + 10)) + y;

                map[x][y] = oreid;

                int exp = 5;


                int r = random.nextInt(exp);
                for (int c = 0; c < r; c++) {
                    map[x + c][y] = oreid;
                }

                r = random.nextInt(exp);
                for (int c = 0; c < r; c++) {
                    map[x - c][y] = oreid;
                }

                r = random.nextInt(exp);
                for (int c = 0; c < r; c++) {
                    map[x][y + c] = oreid;
                }

                r = random.nextInt(exp);
                for (int c = 0; c < r; c++) {
                    map[x][y - c] = oreid;
                }


                // check if the diagonales should be ore too
                //SE
                if (map[x + 2][y] == oreid && map[x][y + 2] == oreid) {
                    map[x + 1][y + 1] = oreid;
                }
                //SW
                if (map[x - 2][y] == oreid && map[x][y + 2] == oreid) {
                    map[x - 1][y + 1] = oreid;
                }
                //NW
                if (map[x - 2][y] == oreid && map[x][y - 2] == oreid) {
                    map[x - 1][y - 1] = oreid;
                }
                //NE
                if (map[x + 2][y] == oreid && map[x][y - 2] == oreid) {
                    map[x + 1][y - 1] = oreid;
                }
            }
        }
    }
	
}
