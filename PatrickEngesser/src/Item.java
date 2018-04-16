/**
 * Created by Patrick on 16.04.2018.
 */
public abstract class Item {

    public abstract int getID ();
    public abstract int getAttackPower ();
    public abstract void pickUp (ItemInventory itemInventory);
    public abstract void attack ();

}
