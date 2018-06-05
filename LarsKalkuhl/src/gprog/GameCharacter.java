package gprog;

public class GameCharacter extends Creature{

	public GameCharacter(int _xC, int _yC){
        setxCoord(_xC);
        setyCoord(_yC);
        setxVel(0);
        setyVel(0);
        setHealth(100);

    }
	
	
}
