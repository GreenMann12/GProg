package gprog;

import java.lang.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.javafx.image.impl.ByteIndexed.Getter;

import gprog.Items.Air;
import gprog.Items.Item;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class World{

	private Draw draw = new Draw();
	private Control control;
	
	public static int heigth = 300;
	public static int width = 1000;
	
	private boolean mousePressed = true;
	private boolean showInventory = false;
	private boolean menuOpen  = false;
	private int inventoryPos = 0;
	private int motion = 10;
	Timer timer = new Timer();
	private int dayTime = 0;
	boolean holdItem = false;
	ItemStack holdItemId = null;
	int holdItemIdCount = 0;
	
	int direction = 0;
	boolean left=true;
	boolean right=true;
	
	//Guter Alter Sounds//////
	int inventorySound = 0;
	//////////////////////////
	
	public Item[][] map;
	public GameCharacter hero;
	public Monster[] monster;
	private ItemStack inventory[] = new ItemStack[32];
	
	public World(Stage stage, Control control, Save savefile){
		this.control = control;
		inventory = control.getInventory();
		loading(stage);
		if (savefile == null) {
			GenerateWorld generateWorld = new GenerateWorld();
			map = generateWorld.createWorld("2349120492", heigth);
			hero = new GameCharacter(width/2, playerSpawn(width/2));
		}
		else {
			this.map = savefile.getMap();
			hero = new GameCharacter(savefile.getX(), savefile.getY());
			inventory = savefile.getInventory();
			control.setInventory(inventory);
		}
		System.out.println(width);
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
		
		///////Anzeige Thread/////////////////////////////////////////////////////
		
		MyAnimTimer animTimer = new MyAnimTimer(32) {
			
			@Override
			public void handle() {
				int koordX = 0;
				int koordY = 0;
				int xChar = hero.getxCoord();
				int yChar = hero.getyCoord();
				
				canvas.setHeight(scene.getHeight());
				canvas.setWidth(scene.getWidth());
				int x = (int) (xChar-(scene.getWidth()/30)); //vorher -400
				int y = (int) (yChar-(scene.getHeight()/30)); //vorher -300
				
				//System.out.println(hero.getX());
				//System.out.println("x: " + x + " y: " + y);
				
				for (int w = x; w < width; w++) {
					koordY = 0;
					for (int h = y; h < heigth; h++) {
						if (map[w][h].getID() == 0 && dayTime == 0) {
							gc.drawImage(draw.loadBlockTexture(0), koordX, koordY);
						}
						else if (map[w][h].getID() == 0 && dayTime == 1) {
							gc.drawImage(draw.loadNightTexture(), koordX, koordY);
						}
						else{
							gc.drawImage(draw.loadBlockTexture(map[w][h].getID()), koordX, koordY);
						}
						koordY += 15;
					}
					koordX += 15;
				}	
				
				

				if (checkColl(hero.getxCoord(), hero.getyCoord() + 3) && checkColl(hero.getxCoord() - 1, hero.getyCoord() + 3)) {

					hero.setyGravity(1);
				} else {
					hero.setCanJump(true);
					hero.setyGravity(0);
				}
				
				updateHero(direction);
				right = true;
				left = true;
				
				
				//charakter animation
				if (motion < 2) {
					gc.drawImage(draw.loadCharTexture(1), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15);
					motion++;
				}
				else if (motion > 1 && motion < 5) {
					gc.drawImage(draw.loadCharTexture(2), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15);
					motion++;
				}
				else if(motion == 5){
					gc.drawImage(draw.loadCharTexture(2), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15);
					motion = 0;
				}
				else {
					gc.drawImage(draw.loadCharTexture(1), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15);
				}
				
				if (inventory[inventoryPos].getID() != 0) {
					gc.drawImage(draw.loadItemTexture(inventory[inventoryPos].getID()), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15 +10);
				}
				//gc.drawImage(draw.loadCharTexture(0), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15);
				//gc.drawImage(draw.loadCharTexture(0), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15);  //(400,300)
				
				//Inventar anzeigen
				if (showInventory) {
					gc.drawImage(draw.loadInventoryTexture(1), 0, 0);
					gc.setFill(Color.RED);
					gc.fillRect(31*inventoryPos, 0, 32, 32);
					
					int ixPos = 1;
					int iyPos = 1;
					
					for (int i = 0; i < 32; i++) {
						gc.drawImage(draw.loadItemInventoryTexture(inventory[i].getID()), ixPos, iyPos);
						if (inventory[i].getSize() > 0) {
							gc.setFill(Color.BLACK);
							gc.setFont(new Font(15));
							if (inventory[i].getSize() < 10) {
								gc.fillText(String.valueOf(inventory[i].getSize()), ixPos + 20, iyPos + 25);
							}
							else {
								gc.fillText(String.valueOf(inventory[i].getSize()), ixPos + 10, iyPos + 25);
							}
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
					gc.fillRect(31*inventoryPos, 0, 32, 32);
					
					int ixPos = 1;
					int iyPos = 1;
					
					for (int i = 0; i < 8; i++) {
						gc.drawImage(draw.loadItemInventoryTexture(inventory[i].getID()), ixPos, iyPos);
						if (inventory[i].getSize() > 0) {
							gc.setFill(Color.BLACK);
							gc.setFont(new Font(15));
							if (inventory[i].getSize() < 10) {
								gc.fillText(String.valueOf(inventory[i].getSize()), ixPos + 20, iyPos + 25);
							}
							else {
								gc.fillText(String.valueOf(inventory[i].getSize()), ixPos + 10, iyPos + 25);
							}
						}
						ixPos += 31;
					}
				}
				
				//Monster
				if (monster != null) {
					for (int i = 0; i < monster.length; i++) {
						//System.out.println("TADAAA I'M A MONSTER!");
					}
				}
			}
		};
		
		animTimer.start();
		
		//////////Tag/Nacht Zyklus///////////////////////////////////////////////////////
		
		class DayTimer extends Thread{
			@Override
			public void run(){
				while(true){
					try {
						Thread.sleep(60*1000);
					} catch (InterruptedException e) {
					}
					if (dayTime == 0) {
						dayTime = 1;
						monster = control.createMonster();
					}
					else {
						dayTime = 0;
						monster = null;
					}
				}
			}
		}
		
		DayTimer daytimer = new DayTimer();
		daytimer.start();
		
		//////Tastatur Eingaben////////////////////////////////////////////////////////////////
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
            public void handle(KeyEvent event) {
				int test = 0;
                if(event.getCode().toString().equals("SPACE") && menuOpen == false){
                	if (hero.getyCoord() > 20) {
						if (checkColl(hero.getxCoord(), hero.getyCoord() - 1) && checkColl(hero.getxCoord() - 1, hero.getyCoord() - 1)) {
							test = -1;
							if (checkColl(hero.getxCoord(), hero.getyCoord() - 2) && checkColl(hero.getxCoord() - 1, hero.getyCoord() - 2)) {
								test = -2;
							}

							if (hero.getCanJump()) {
								hero.setyGravity(0);
								hero.setyVel(test);
								TimerTask timerTask = new TimerTask() {
									@Override
									public void run() {
										try {
											Thread.sleep(200);
										} catch (Exception e) {
											e.printStackTrace();
										}
										hero.setyVel(0);
										hero.setCanJump(false);

									}
								};
								timer.schedule(timerTask, 10);

								//hero.setyCoord(hero.getyCoord() - 1);
							} else {

							}
						}

					}
                }
                if(event.getCode().toString().equals("A") && menuOpen == false){
                	direction = 1;
                	if (hero.getxCoord() > 100) {
                		if (checkColl(hero.getxCoord() - 2, hero.getyCoord()) && checkColl(hero.getxCoord() - 2, hero.getyCoord() + 1) && checkColl(hero.getxCoord() - 2, hero.getyCoord() + 2)) {
                			if(left)
                			hero.setxVel(-1);
                			Random ran = new Random();
                			int i = ran.nextInt(3);
							if(map[hero.getxCoord()][hero.getyCoord()+3].getClass() == gprog.Items.Sand.class)
								Audio.music("src/Audio/sand"+i+".wav");
							else if(map[hero.getxCoord()][hero.getyCoord()+3].getClass() == gprog.Items.Stone.class)
								Audio.music("src/Audio/stone"+i+".wav");
							else if (map[hero.getxCoord()][hero.getyCoord()+3].getClass() == gprog.Items.Dirt.class)
								Audio.music("src/Audio/grass"+i+".wav");
							else if (map[hero.getxCoord()][hero.getyCoord()+3].getClass() == gprog.Items.Water.class)
								Audio.music("src/Audio/water.wav");

                			motion = 0;
                		}
					}
                }
                /*
                if(event.getCode().toString().equals("S") && menuOpen == false){
                	if(hero.getyCoord() < 600){
                		hero.setyCoord(hero.getyCoord() + 1);
                	}
                }
                */
                if(event.getCode().toString().equals("D") && menuOpen == false){
                	direction = 2;
                	if (hero.getxCoord() < width - (int) (scene.getWidth()/30)) {
                		if (checkColl(hero.getxCoord() + 1, hero.getyCoord()) && checkColl(hero.getxCoord() + 1, hero.getyCoord() + 1) && checkColl(hero.getxCoord() + 1, hero.getyCoord() + 2)) {
							if(right)
                			hero.setxVel(1);
							Random ran = new Random();
							int i = ran.nextInt(3);
							if(map[hero.getxCoord()][hero.getyCoord()+3].getClass() == gprog.Items.Sand.class)
								Audio.music("src/Audio/sand"+i+".wav");
							else if(map[hero.getxCoord()][hero.getyCoord()+3].getClass() == gprog.Items.Stone.class)
								Audio.music("src/Audio/stone"+i+".wav");
							else if (map[hero.getxCoord()][hero.getyCoord()+3].getClass() == gprog.Items.Dirt.class)
								Audio.music("src/Audio/grass"+i+".wav");
							else if (map[hero.getxCoord()][hero.getyCoord()+3].getClass() == gprog.Items.Water.class)
								Audio.music("src/Audio/water.wav");


							motion = 0;
                		}
					}
                }
                if(event.getCode().toString().equals("E") && menuOpen == false){
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
                		inventoryPos = Integer.valueOf(event.getCode().getName()) - 1;
                	}
                }
                if(event.getCode()== KeyCode.ESCAPE)
				{
                	animTimer.stop();
                	menuOpen = true;
                	
                	VBox vbox = new VBox();
            		Button b_continueGame = new Button("Fortsetzen");
            		Button b_saveGame = new Button("Spiel Speichern");
            		Button b_options = new Button("Optionen");
            		Button b_exit = new Button("Spiel Verlassen");
            		b_continueGame.setBackground(null);
            		b_saveGame.setBackground(null);
            		b_options.setBackground(null);
            		b_exit.setBackground(null);
            		b_continueGame.setStyle("-fx-font: 24 arial;");
            		b_saveGame.setStyle("-fx-font: 24 arial;");
            		b_options.setStyle("-fx-font: 24 arial;");
            		b_exit.setStyle("-fx-font: 24 arial;");
            		vbox.getChildren().addAll(b_continueGame, b_saveGame, b_options, b_exit);
            		vbox.setAlignment(Pos.CENTER);
            		VBox.setMargin(b_continueGame, new Insets(0,10,10,10));
            		VBox.setMargin(b_saveGame, new Insets(0,10,10,10));
            		VBox.setMargin(b_options, new Insets(0,10,10,10));
            		VBox.setMargin(b_exit, new Insets(0,10,10,0));
            		BorderPane borderPane = new BorderPane();
            		borderPane.setCenter(vbox);
            		
            		scene.setRoot(borderPane);
            		
            		//EventHandler
            		b_continueGame.setOnAction(new EventHandler<ActionEvent>(){

						@Override
						public void handle(ActionEvent event) {
							scene.setRoot(group);
							menuOpen = false;
							animTimer.start();
						}
            			
            		});
            		b_saveGame.setOnAction(new EventHandler<ActionEvent>(){

						@Override
						public void handle(ActionEvent event) {
							class SaveThread extends Thread{
								@Override
								public void run(){
									control.saveGame(map, hero.getxCoord(), hero.getyCoord());
								}
							}
							
							SaveThread saveThread = new SaveThread();
							saveThread.start();
							scene.setRoot(group);
							menuOpen = false;
							animTimer.start();
						}
            			
            		});
            		b_options.setOnAction(e -> new Options(borderPane ,stage, scene));
            		b_exit.setOnAction(e -> System.exit(0));
				}
            }
		});
		
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().toString().equals("SPACE")) {
					hero.setyVel(0);

				}
				if (event.getCode().toString().equals("A")) {
					hero.setxVel(0);
					motion = 10;
				}
				if (event.getCode().toString().equals("S")) {
					hero.setyVel(0);
				}
				if (event.getCode().toString().equals("D")) {
					hero.setxVel(0);
					motion = 10;
				}
			}
		});
		
		/////////Maus Eingaben///////////////////////////////////////////////////////////////////////////////////////////////////
		
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				//abbauen der Bl�cke
				if (event.getButton() == MouseButton.PRIMARY && menuOpen == false) {
					mousePressed = true;
					
					TimerTask timerTask = new TimerTask() {
						
						@Override
						public void run() {
							try {
								Thread.sleep(1000);
							
							if (mousePressed) {
								int x = (int) (hero.getxCoord() - (scene.getWidth()/30)) + (int)(event.getSceneX()/15);
								int y = (int) (hero.getyCoord() - (scene.getHeight()/30)) + (int)(event.getSceneY()/15);
								
								if (map[x][y].getID() != 0 && map[x][y].getID() != 10 && control.minePossible(map[x][y], inventory[inventoryPos].getItem()) && control.checkMinePos(x, y, hero.getxCoord(), hero.getyCoord())) {
									if (control.inventoryIsFull() != false) {
										control.inventoryAddItem(map[x][y]);
										map[x][y] = new Air();
										inventory = control.getInventory();
										System.out.println((int) (event.getSceneX()) + "  und  " + (int) (event.getSceneY()));
										System.out.println(x+ "  und  " + y);
										System.out.println(hero.getxCoord()+ "  und  " + hero.getyCoord());
										System.out.println(scene.getHeight());
										System.out.println("------------------------------------");
									}
								}
							}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					
					int ix = (int) (event.getSceneX()/31)  + 8 * ((int) (event.getSceneY()/31));
					
					if (showInventory == true && control.inventarPos((int) event.getSceneX(), (int) event.getSceneY()) && inventory[ix].getID() != 0) {
						scene.setCursor(new ImageCursor(draw.loadItemInventoryTexture(inventory[ix].getID())));
						holdItem = true;
						holdItemId = inventory[ix];
						holdItemIdCount = inventory[ix].getSize();
					}
					else {
						timer.schedule(timerTask, 500);
					}
					
					scene.setOnMouseReleased(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							if (event.getButton() == MouseButton.PRIMARY) {
								if (holdItem) {
									if (showInventory == true && control.inventarPos((int) event.getSceneX(), (int) event.getSceneY()) && inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))].getID() == 0) {
										inventory[ix] = new ItemStack(null,0);
										inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))] = holdItemId;
										//inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))][1] = holdItemIdCount;
									}
									else if (showInventory == true && control.inventarPos((int) event.getSceneX(), (int) event.getSceneY()) && inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))].getID() != 0) {
										inventory[ix] = inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))];
										//inventory[ix][1] = inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))][1];
										inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))] = holdItemId;
										//inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))][1] = holdItemIdCount;
									}
									holdItem = false;
									holdItemId = null;
									scene.setCursor(Cursor.DEFAULT);
								}
								mousePressed = false;
								timerTask.cancel();
							}
						}
						
					});
				}
				
				//platzieren der Bl�cke
				if (event.getButton() == MouseButton.SECONDARY && menuOpen == false) {
					int x = (int) (hero.getxCoord() - (scene.getWidth()/30)) + (int)(event.getSceneX()/15);
					int y = (int) (hero.getyCoord() - (scene.getHeight()/30)) + (int)(event.getSceneY()/15);
					inventory = control.getInventory();
					if (inventory[inventoryPos].getID() != 0 && map[x][y].getID() == 0 && inventory[inventoryPos].getID() < draw.getBlock() && control.checkMinePos(x, y, hero.getxCoord(), hero.getyCoord())) {
						map[x][y] = control.inventoryRemoveItem(inventoryPos);
					}
				}
			}
		});
		
		stage.setScene(scene);
	}
	
	
	public void updateHero(int r) {
		if (r == 1) {
			if (!checkColl(hero.getxCoord() - 2, hero.getyCoord()) || !checkColl(hero.getxCoord() - 2, hero.getyCoord() + 1) || !checkColl(hero.getxCoord() - 2, hero.getyCoord() + 2))
			{
				hero.setxVel(0);
				left= false;
			}
		}
		else if (r==2) {
			if (!checkColl(hero.getxCoord() + 1, hero.getyCoord()) || !checkColl(hero.getxCoord() + 1, hero.getyCoord() + 1) || !checkColl(hero.getxCoord() + 1, hero.getyCoord() + 2))
			{
				hero.setxVel(0);
				right= false;
			}
		}

						hero.setxCoord(hero.getxCoord() + hero.getxVel());
						hero.setyCoord(hero.getyCoord() + hero.getyVel() + hero.getyGravity());
					}
	
	private boolean checkColl(int x, int y) {
		if (map[x][y].getID() == 0 || map[x][y].getID() == 3 || map[x][y].getID() == 10 || map[x][y].getID() == 9) {

			return true;
		} else {

			return false;
		}

	}
	
	///////Spawnpunkt des Spielers zu Begin eines neuen Spiels//////////////////////////////////////////////
	
	private int playerSpawn(int x){
		int y = 0;
		for (int i = 0; i < heigth; i++) {
			if (map[x][i].getID() == 1) {
				y = i-3;
				i = heigth;
			}
		}
		return y;
	}
	
}
