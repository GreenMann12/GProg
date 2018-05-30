package gprog.Items;

import gprog.ItemStack;

import java.util.ArrayList;

/**
 * Created by Patrick on 30.05.2018.
 */
public class Desk extends Construct {

    public Desk () {
        this.setID(22);
        this.setMiningLevel(1);
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(new Wood(),3));
        for (int i=0; i<2 ; i++) {
            items.get(0).addItemToStack(new Wood());
        }
        this.setItems(items);
    }

}
