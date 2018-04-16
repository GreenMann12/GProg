/**
 * Created by Patrick on 16.04.2018.
 */
public class Dirt extends Resource {

    private int ID;
    private int miningLevel;

    Dirt (int ID) {
        this.ID = ID;
        this.miningLevel = 1;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    @Override
    public Resource mine(Item item) {
        return null;
    }
}
