package gprog;


public class Monster extends Creature{
	
	   public Monster(int _xC, int _yC){
	        setxCoord(_xC);
	        setyCoord(_yC);
	        setxVel(0);
	        setyVel(0);
	        setHealth(100);
	    }
}
