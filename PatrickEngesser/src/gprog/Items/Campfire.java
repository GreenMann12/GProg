package gprog.Items;

import gprog.ItemStack;

import java.util.ArrayList;

/**
 * Created by Patrick on 30.05.2018.
 */
public class Campfire extends Construct {

    public Campfire () {
        this.setID(24);
        this.setMiningLevel(1);
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(new Wood(),3));
        this.setItems(items);
    }

}
