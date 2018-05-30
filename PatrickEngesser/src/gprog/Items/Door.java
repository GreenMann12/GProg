package gprog.Items;

import gprog.ItemStack;

import java.util.ArrayList;

/**
 * Created by Patrick on 30.05.2018.
 */
public class Door extends Construct {

    public Door () {
        this.setID(23);
        this.setMiningLevel(1);
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(new Wood(),3));
        items.get(0).addItemToStack(new Wood());
        this.setItems(items);
    }

}
