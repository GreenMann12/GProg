package gprog;

public abstract class Creature {

	private int xCoord;
	private int yCoord;
	private int xVel;
	private int yVel;
	private int health;
	private boolean canJump=true;

	private int yGravity;



	public void update() {


		xCoord = xCoord + xVel;
		yCoord = yCoord + yVel + yGravity;

		//xVel=0;
		//yVel=0;
	}





	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public void setxVel(int xVel) {
		this.xVel = xVel;
	}

	public void setyVel(int yVel) {
		this.yVel = yVel;
	}

	public int getxVel() {
		return xVel;
	}

	public int getyVel() {
		return yVel;
	}

	public int getHealth(){
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public boolean getCanJump() {
		return canJump;
	}
	public void setCanJump(boolean b){
		canJump=b;
	}

	public int getyGravity() {
		return yGravity;
	}

	public void setyGravity(int yGravity) {
		this.yGravity = yGravity;
	}
	
}
