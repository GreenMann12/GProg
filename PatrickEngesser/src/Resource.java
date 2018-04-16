/**
 * Created by Patrick on 16.04.2018.
 */
public abstract class Resource {

    public abstract int getID ();
    public abstract int getMiningLevel ();
    public abstract Resource mine (Item item);

}
