package gprog;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.javafx.image.impl.ByteIndexed.Getter;

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
	private int motion = 0;
	Timer timer = new Timer();
	private int dayTime = 0;
	boolean holdItem = false;
	int holdItemId = 0;
	int holdItemIdCount = 0;
	
	//Guter Alter Sounds//////
	int inventorySound = 0;
	//////////////////////////
	
	public int[][] map;
	public GameCharacter hero;
	public Monster[] monster;
	private int inventory[][] = new int[32][2];
	
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
						if (map[w][h] == 0 && dayTime == 0) {
							gc.drawImage(draw.loadBlockTexture(0), koordX, koordY);
						}
						else if (map[w][h] == 0 && dayTime == 1) {
							gc.drawImage(draw.loadNightTexture(), koordX, koordY);
						}
						else{
							gc.drawImage(draw.loadBlockTexture(map[w][h]), koordX, koordY);
						}
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
				if (inventory[inventoryPos][0] != 0) {
					gc.drawImage(draw.loadItemTexture(inventory[inventoryPos][0]), ((int) (scene.getWidth()/30))*15, ((int) (scene.getHeight()/30))*15 +10);
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
						gc.drawImage(draw.loadItemInventoryTexture(inventory[i][0]), ixPos, iyPos);
						if (inventory[i][1] > 0) {
							gc.setFill(Color.BLACK);
							gc.setFont(new Font(15));
							if (inventory[i][1] < 10) {
								gc.fillText(String.valueOf(inventory[i][1]), ixPos + 20, iyPos + 25);
							}
							else {
								gc.fillText(String.valueOf(inventory[i][1]), ixPos + 10, iyPos + 25);
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
						gc.drawImage(draw.loadItemInventoryTexture(inventory[i][0]), ixPos, iyPos);
						if (inventory[i][1] > 0) {
							gc.setFill(Color.BLACK);
							gc.setFont(new Font(15));
							if (inventory[i][1] < 10) {
								gc.fillText(String.valueOf(inventory[i][1]), ixPos + 20, iyPos + 25);
							}
							else {
								gc.fillText(String.valueOf(inventory[i][1]), ixPos + 10, iyPos + 25);
							}
						}
						ixPos += 31;
					}
				}
				
				//Monster
				if (monster != null) {
					for (int i = 0; i < monster.length; i++) {
						System.out.println("TADAAA I'M A MONSTER!");
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
                if(event.getCode().toString().equals("W") && menuOpen == false){
                	if(hero.getY() > 20){
                		hero.setY(hero.getY() - 1);
                	}
                }
                if(event.getCode().toString().equals("A") && menuOpen == false){
                	if (hero.getX() > 100) {
                		hero.setX(hero.getX() - 1);
                		Random ran = new Random();
                		int i = ran.nextInt(3);
                		Audio.music("src/Audio/grass"+i+".wav");
					}
                }
                if(event.getCode().toString().equals("S") && menuOpen == false){
                	if(hero.getY() < 600){
                		hero.setY(hero.getY() + 1);
                	}
                }
                if(event.getCode().toString().equals("D") && menuOpen == false){
                	if (hero.getX() < width - (int) (scene.getWidth()/30)) {
                		hero.setX(hero.getX() + 1);
                		Random ran = new Random();
                		int i = ran.nextInt(3);
                		Audio.music("src/Audio/grass"+i+".wav");
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
							control.saveGame(map, hero.getX(), hero.getY());
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
		
		/////////Maus Eingaben///////////////////////////////////////////////////////////////////////////////////////////////////
		
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				//abbauen der Blöcke
				if (event.getButton() == MouseButton.PRIMARY && menuOpen == false) {
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
								
								if (map[x][y] != 0 && map[x][y] != 10 && control.minePossible(map[x][y], inventory[inventoryPos][0]) && control.checkMinePos(x, y, hero.getX(), hero.getY())) {
									if (control.inventoryIsFull() != false) {
										control.inventoryAddItem(map[x][y]);
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
						}
					};
					
					int ix = (int) (event.getSceneX()/31)  + 8 * ((int) (event.getSceneY()/31));
					
					if (showInventory == true && control.inventarPos((int) event.getSceneX(), (int) event.getSceneY()) && inventory[ix][0] != 0) {
						scene.setCursor(new ImageCursor(draw.loadItemInventoryTexture(inventory[ix][0])));
						holdItem = true;
						holdItemId = inventory[ix][0];
						holdItemIdCount = inventory[ix][1];
					}
					else {
						timer.schedule(timerTask, 10);
					}
					
					scene.setOnMouseReleased(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							if (event.getButton() == MouseButton.PRIMARY) {
								if (holdItem) {
									if (showInventory == true && control.inventarPos((int) event.getSceneX(), (int) event.getSceneY()) && inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))][0] == 0) {
										inventory[ix][0] = 0;
										inventory[ix][1] = 0;
										inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))][0] = holdItemId;
										inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))][1] = holdItemIdCount;
									}
									else if (showInventory == true && control.inventarPos((int) event.getSceneX(), (int) event.getSceneY()) && inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))][0] != 0) {
										inventory[ix][0] = inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))][0];
										inventory[ix][1] = inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))][1];
										inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))][0] = holdItemId;
										inventory[(int) (event.getSceneX()/31) + 8 * ((int) (event.getSceneY()/31))][1] = holdItemIdCount;
									}
									holdItem = false;
									holdItemId = 0;
									scene.setCursor(Cursor.DEFAULT);
								}
								mousePressed = false;
								timerTask.cancel();
							}
						}
						
					});
				}
				
				//platzieren der Blöcke
				if (event.getButton() == MouseButton.SECONDARY && menuOpen == false) {
					int x = (int) (hero.getX() - (scene.getWidth()/30)) + (int)(event.getSceneX()/15);
					int y = (int) (hero.getY() - (scene.getHeight()/30)) + (int)(event.getSceneY()/15);
					inventory = control.getInventory();
					if (inventory[inventoryPos][0] != 0 && map[x][y] == 0 && inventory[inventoryPos][0] < draw.getBlock() && control.checkMinePos(x, y, hero.getX(), hero.getY())) {
						map[x][y] = inventory[inventoryPos][0];
						control.inventoryRemoveItem(inventoryPos);
					}
				}
			}
		});
		
		stage.setScene(scene);
	}
	
	///////Spawnpunkt des Spielers zu Begin eines neuen Spiels//////////////////////////////////////////////
	
	private int playerSpawn(int x){
		int y = 0;
		for (int i = 0; i < heigth; i++) {
			if (map[x][i] == 1) {
				y = i-3;
				i = heigth;
			}
		}
		return y;
	}
	
}
