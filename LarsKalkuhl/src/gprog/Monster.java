package gprog;


public class Monster extends Creature{
	
		MonsterThread mthread;
		boolean left=true;
		boolean right=true;
		int direction=0;
		World world;
	
	   public Monster(int _xC, int _yC, World world){
	        setxCoord(_xC);
	        setyCoord(_yC);
	        setxVel(0);
	        setyVel(0);
	        setHealth(100);
	        this.world = world;
	        mthread = new MonsterThread(this);
	        mthread.start();
	    }
	   
	   public class MonsterThread extends Thread{
		   
		   Monster m;
		   
		   public MonsterThread(Monster m) {
			   this.m = m;
		   }
		   
		   @Override
			public void run(){
					//System.out.println("Thread.start");
					while (true) {
						if (world.hero.getxCoord() < m.getxCoord()) {
							m.direction = 1;
							if (m.getxCoord() > 100) {
								if (world.checkColl(m.getxCoord() - 2, m.getyCoord()) && world.checkColl(m.getxCoord() - 2, m.getyCoord() + 1) && world.checkColl(m.getxCoord() - 2, m.getyCoord() + 2)) {
									if (left)
										//System.out.println("LINKS");
										m.setxVel(-1);


								} else {
									if (m.getCanJump())
										m.setyVel(-2);
									m.setxVel(-1);
									m.setCanJump(false);


								}
							}
						} else if (world.hero.getxCoord() > m.getxCoord()) {
							m.direction = 2;
							if (m.getxCoord() < world.width - (int) (world.getSceneXwidth() / 30)) {
								if (world.checkColl(m.getxCoord() + 1, m.getyCoord()) && world.checkColl(m.getxCoord() + 1, m.getyCoord() + 1) && world.checkColl(m.getxCoord() + 1, m.getyCoord() + 2)) {
									if (right)
										//System.out.println("RECHTS");
										m.setxVel(1);


								} else {
									if (m.getCanJump())
										m.setyVel(-2);
									m.setxVel(1);
									m.setCanJump(false);


								}
							}
						}

						if (world.checkColl(m.getxCoord(), m.getyCoord() + 3) && world.checkColl(m.getxCoord() - 1, m.getyCoord() + 3)) {
							//System.out.println("GRAV");
							m.setyGravity(1);
						} else {
							m.setyGravity(0);
						}

						if (m.getxCoord() == world.hero.getxCoord() + 1 && m.getyCoord() == world.hero.getyCoord()) {
							world.monsterRobot(65);
							world.setPushed(true);
							world.hero.setHealth(world.hero.getHealth() - 2);
						} else if (m.getxCoord() + 1 == world.hero.getxCoord() && m.getyCoord() == world.hero.getyCoord()) {
							world.monsterRobot(68);
							world.setPushed(true);
							world.hero.setHealth(world.hero.getHealth() - 2);
						}
						if (world.hero.getHealth() <= 0) {
							try {
								Thread.sleep(500);

							} catch (Exception e) {
								e.printStackTrace();
							}
							//world.hero = null;
							//world.hero = new GameCharacter(world.width / 2, world.playerSpawn(world.width / 2));
							world.hero.setxCoord(world.width / 2);
							world.hero.setyCoord(world.playerSpawn(world.width / 2));
							world.hero.setHealth(100);


						}


						world.updateMonster(m, m.direction);
						try {
							Thread.sleep(200);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (!m.getCanJump()) {
							if (m.jumpCount < 10) {
								m.jumpCount = m.jumpCount + 1;
							} else {
								m.setCanJump(true);
								m.jumpCount = 0;
							}
						}
						m.left = true;
						m.right = true;
						m.setyVel(0);

					}
			}
	   }
	   
	   public void die(){
		   if (mthread != null) {
			try {
				   mthread.stop();
			} catch (Exception e) {
				
			}
			   
		   }
	   }
}
