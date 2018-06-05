package gprog;

import gprog.Items.Item;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Patrick on 29.05.2018.
 */
public class ItemStack implements Serializable{

    private int ID;
    private ArrayList<Item> items;
    private int size;

    public ItemStack (Item item, int ID) {
        this.ID = ID;
        items = new ArrayList<>();
        items.add(item);
        size = items.size();
    }

    public int getID() {
        return ID;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getSize () {
        if (this.ID == 0){
            return 0;
        } else {
            return items.size();
        }
    }

    public Item getItem (){
        Item temp = items.get(0);
        return temp;
    }

    public void addItemToStack (Item item) {
        items.add(item);
    }

    public Item removeItemFromStack () {
        Item item;
        int temp = items.size();
        item = items.get(temp-1);
        items.remove(temp-1);
        return item;
    }

}
