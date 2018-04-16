import java.util.ArrayList;

/**
 * Created by Patrick on 16.04.2018.
 */
public class ItemInventory extends Inventory {

    private ArrayList<Item> intentory = new ArrayList<>();

    private int space;
    private int nrOfItems;

    ItemInventory(int space){
        this.space = space;
    }

    @Override
    public String getInventory() {
        return intentory.toString();
    }

    @Override
    public int getSpace() {
        return space;
    }

    @Override
    public int getNrOfObjects() {
        return nrOfItems;
    }

    // Add an item to the inventory
    public boolean add (Item item){
        if (nrOfItems < space){
            intentory.add(item);
            nrOfItems++;
            return true;
        }
        else {
            return false;
        }
    }

    // Drop an item from the inventory
    public void drop (Item itemToBeDropped) {
        for (Item item: intentory) {
            if (item.getID() == itemToBeDropped.getID()){
                intentory.remove(item);
                nrOfItems--;
                return;
            }
        }
    }

}
