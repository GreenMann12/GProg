package gprog.Items;

/**
 * Created by Patrick on 29.05.2018.
 */
public abstract class Tool extends Item {

    private int attackPower;

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

}


