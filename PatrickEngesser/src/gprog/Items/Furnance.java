package gprog.Items;

import gprog.ItemStack;

import java.util.ArrayList;

/**
 * Created by Patrick on 30.05.2018.
 */
public class Furnance extends Construct {

    public Furnance () {
        this.setID(25);
        this.setMiningLevel(1);
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(new Wood(),3));
        items.add(new ItemStack(new Stone(),2));
        for (int i=0; i<3 ; i++) {
            items.get(1).addItemToStack(new Stone());
        }
        this.setItems(items);
    }

}
