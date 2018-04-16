import java.util.ArrayList;

/**
 * Created by Patrick on 16.04.2018.
 */
public class ResourceInventory extends Inventory {

    private ArrayList<Resource> intentory = new ArrayList<>();

    private int space;
    private int nrOfResources;

    ResourceInventory(int space){
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
        return nrOfResources;
    }

    // Add a resource to the inventory
    public boolean add (Resource resource){
        if (nrOfResources < space){
            intentory.add(resource);
            nrOfResources++;
            return true;
        }
        else {
            return false;
        }
    }

    // Use a resource from the Inventory
    public void use (Resource resourceToBeUsed) {
        for (Resource resource: intentory) {
            if (resource.getID() == resourceToBeUsed.getID()){
                intentory.remove(resource);
                nrOfResources--;
                return;
            }
        }
    }

}
