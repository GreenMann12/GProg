import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class World{

	private Draw draw = new Draw();
	private Control control;
	
	public static int heigth = 255;
	public static int width = 1000;
	
	private boolean mousePressed = true;
	private boolean showInventory = false;
	private int inventarPos = 0;
	private int motion = 0;
	int inventorySound = 0;
	Timer timer = new Timer();
	
	public int[][] map = new int[width][heigth];
	public Character hero;
	public Monster[] monster;
	private int inventory[][] = new int[32][2];
	
	public World(Stage stage, Control control){
		this.control = control;
		loading(stage);
		generateWorld();
		hero = new Character(400, playerSpawn(400));
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
		Canvas canvas = new Canvas((2000),(2000));
		group.getChildren().add(canvas);
		
		Scene scene = new Scene(group, 800, 600);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		MyAnimTimer animTimer = new MyAnimTimer(20) {
			
			@Override
			public void handle() {
				int koordX = 0;
				int koordY = 0;
				int xChar = hero.getX();
				int yChar = hero.getY();
				
				canvas.setHeight(scene.getHeight());
				canvas.setWidth(scene.getWidth());
				int x = (int) (xChar-(scene.getWidth()/30)); //vorher -400
				int y = (int) (yChar-(scene.getHeight()/30)); //vorher -300
				
				//System.out.println(hero.getX());
				//System.out.println("x: " + x + " y: " + y);
				
				for (int w = x; w < width; w++) {
					koordY = 0;
					for (int h = y; h < heigth; h++) {
						gc.drawImage(draw.loadBlockTexture(map[w][h]), koordX, koordY);
						koordY += 15;
					}
					koordX += 15;
				}
				
				//charakter animation
				if (motion < 6) {
					gc.drawImage(draw.loadCharTexture(1), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15);
					motion++;
				}
				if (motion > 5) {
					gc.drawImage(draw.loadCharTexture(2), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15);
					motion++;
				}
				if (motion > 10) {
					motion = 0;
				}
				//gc.drawImage(draw.loadCharTexture(0), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15);
				//gc.drawImage(draw.loadCharTexture(0), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15);  //(400,300)
				
				//Inventar anzeigen
				if (showInventory) {

					gc.drawImage(draw.loadInventoryTexture(1), 0, 0);
					gc.setFill(Color.RED);
					gc.fillRect(31*inventarPos, 0, 32, 32);
					
					int ixPos = 1;
					int iyPos = 1;
					
					for (int i = 0; i < 32; i++) {
						gc.drawImage(draw.loadItemTexture(inventory[i][0]), ixPos, iyPos);
						if (inventory[i][1] > 0) {
							gc.setFill(Color.BLACK);
							gc.setFont(new Font(15));
							gc.fillText(String.valueOf(inventory[i][1]), ixPos + 20, iyPos + 25);
						}
						ixPos += 31;
						if ((i+1) % 8 == 0) {
							ixPos = 1;
							iyPos += 31;
						}
					}
				}
				else {
					gc.drawImage(draw.loadInventoryTexture(0), 0, 0);
					gc.setFill(Color.RED);
					gc.fillRect(31*inventarPos, 0, 32, 32);
					
					int ixPos = 1;
					int iyPos = 1;

					for (int i = 0; i < 8; i++) {
						gc.drawImage(draw.loadItemTexture(inventory[i][0]), ixPos, iyPos);
						if (inventory[i][1] > 0) {

							gc.setFill(Color.BLACK);
							gc.setFont(new Font(15));
							gc.fillText(String.valueOf(inventory[i][1]), ixPos + 20, iyPos + 25);
						}

						ixPos += 31;
					}
				}
			}
		};
		
		animTimer.start();
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
            public void handle(KeyEvent event) {
                if(event.getCode().toString().equals("W")){
                	if(hero.getY() > 20){
                		hero.setY(hero.getY() - 1);
                	}
                }
                if(event.getCode().toString().equals("A")){
                	if (hero.getX() > 100) {
                		hero.setX(hero.getX() - 1);
                		Random ran = new Random();
                		int i = ran.nextInt(3);
                		Audio.music("src/Audio/grass"+i+".wav");
					}
                }
                if(event.getCode().toString().equals("S")){
                	if(hero.getY() < 600){
                		hero.setY(hero.getY() + 1);
                	}
                }
                if(event.getCode().toString().equals("D")){
                	if (hero.getX() < 900) {
                		hero.setX(hero.getX() + 1);
						Random ran = new Random();
						int i = ran.nextInt(3);
						Audio.music("src/Audio/grass"+i+".wav");
					}
                }

                if(event.getCode().toString().equals("E")){
					if(inventorySound == 0) {
						Audio.music("src/Audio/inventory.wav");
						inventorySound = 1;
					}
					else{
						Audio.music("src/Audio/inventory2.wav");
						inventorySound = 0;
					}
                	inventory = control.getInventory();

                	if (showInventory) {
                		showInventory = false;
					}
                	else {
                		showInventory = true;
					}

                }
                if(event.getCode().isDigitKey() && !event.getCode().isKeypadKey()){
                	if(Integer.valueOf(event.getCode().getName()) > 0 && Integer.valueOf(event.getCode().getName()) < 9){
                		inventarPos = Integer.valueOf(event.getCode().getName()) - 1;
                	}
                }
                if(event.getCode()== KeyCode.ESCAPE)
				{
					System.exit(0);
				}
            }
		});
		
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				//abbauen der Bl�cke
				if (event.getButton() == MouseButton.PRIMARY) {
					mousePressed = true;
					
					TimerTask timerTask = new TimerTask() {
						
						@Override
						public void run() {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (mousePressed) {
								int x = (int) (hero.getX() - (scene.getWidth()/30)) + (int)(event.getSceneX()/15);
								int y = (int) (hero.getY() - (scene.getHeight()/30)) + (int)(event.getSceneY()/15);
								
								if (map[x][y] != 0) {
									if (control.inventoryIsFull() != false) {
										control.inventoryAddItem(map[x][y]);
									}
									map[x][y] = 0;
									inventory = control.getInventory();
									System.out.println((int) (event.getSceneX()) + "  und  " + (int) (event.getSceneY()));
									System.out.println(x+ "  und  " + y);
									System.out.println(hero.getX()+ "  und  " + hero.getY());
									System.out.println(scene.getHeight());
									System.out.println("------------------------------------");
								}
							}
						}
					};
					
					timer.schedule(timerTask, 10);
					
					scene.setOnMouseReleased(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							if (event.getButton() == MouseButton.PRIMARY) {
								mousePressed = false;
								timerTask.cancel();
							}
						}
						
					});
				}
				
				//platzieren der Bl�cke
				if (event.getButton() == MouseButton.SECONDARY) {
					int x = (int) (hero.getX() - (scene.getWidth()/30)) + (int)(event.getSceneX()/15);
					int y = (int) (hero.getY() - (scene.getHeight()/30)) + (int)(event.getSceneY()/15);
					inventory = control.getInventory();
					if (inventory[inventarPos][0] != 0 && map[x][y] == 0) {
						map[x][y] = inventory[inventarPos][0];
						control.inventoryRemoveItem(inventarPos);
					}
				}
			}
		});
		
		stage.setScene(scene);
	}
	
	private int playerSpawn(int x){
		int y = 0;
		for (int i = 0; i < heigth; i++) {
			if (map[400][i] == 1) {
				y = i-3;
				i = heigth;
			}
		}
		return y;
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
