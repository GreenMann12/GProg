package gprog.Items;

import gprog.ItemStack;

import java.util.ArrayList;

/**
 * Created by Patrick on 30.05.2018.
 */
public class Chair extends Construct {

    public Chair () {
        this.setID(21);
        this.setMiningLevel(1);
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(new Wood(),3));
        items.get(0).addItemToStack(new Wood());
        this.setItems(items);
    }

    @Override
    public Construct clone () {
        return new Chair();
    }

}
