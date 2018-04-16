/**
 * Created by Patrick on 16.04.2018.
 */

public class Test {
    public static void main(String[] args) {

        // -------------------------------------------------------------------------------------------
        // Item-ItemInventory Test
        // -------------------------------------------------------------------------------------------
        ItemInventory itemInventory = new ItemInventory(10);
        System.out.println("Space: "+ itemInventory.getSpace());
        Pickaxe pickaxe = new Pickaxe(1);
        Shortsword shortsword = new Shortsword(2);
        Hammer hammer = new Hammer(3);
        pickaxe.pickUp(itemInventory);
        shortsword.pickUp(itemInventory);
        hammer.pickUp(itemInventory);
        System.out.println("NrOfItems: "+ itemInventory.getNrOfObjects());
        itemInventory.drop(shortsword);
        System.out.println("NrOfItems: "+ itemInventory.getNrOfObjects());
        System.out.println("ItemInventory: "+ itemInventory.getInventory());
        // -------------------------------------------------------------------------------------------
        // Resource-ItemInventory Test
        // -------------------------------------------------------------------------------------------
        ResourceInventory resourceInventory = new ResourceInventory(50);
        System.out.println("Space: "+ resourceInventory.getSpace());
        Dirt dirt = new Dirt(4);
        dirt.mine(pickaxe);
        Stone stone = new Stone(5);
        stone.mine(pickaxe);
        Wood wood = new Wood(6);
        wood.mine(pickaxe);
        IronOre ironOre = new IronOre(7);
        ironOre.mine(pickaxe);
        CopperOre copperOre = new CopperOre(8);
        copperOre.mine(pickaxe);
        GoldOre goldOre = new GoldOre(9);
        goldOre.mine(pickaxe);
        Diamond diamond = new Diamond(10);
        diamond.mine(pickaxe);
    }
}
