/**
 * Created by Patrick on 16.04.2018.
 */
public class CopperOre extends Resource {

    CopperOre (int ID) {
        this.ID = ID;
        this.miningLevel = 3;
    }

    private int ID;
    private int miningLevel;

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
