/**
 * Created by Patrick on 16.04.2018.
 */
public class Pickaxe extends Item {

    private int ID;
    private int attackPower;

    Pickaxe (int ID){
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public int getAttackPower() {
        return attackPower;
    }

    @Override
    public void pickUp(ItemInventory itemInventory) {
        itemInventory.add(this);
    }

    @Override
    public void attack() {

    }
}
