package gprog;

import gprog.Items.*;

/**
 * Created by Patrick on 30.05.2018.
 */
public class ConstructMenu {

    private Construct[] constructs = new Construct[5];

    public ConstructMenu () {
        constructs[0] = new Chair();
        constructs[1] = new Desk();
        constructs[2] = new Door();
        constructs[3] = new Campfire();
        constructs[4] = new Furnance();
    }

    public Construct[] getConstructs() {
        return constructs;
    }

    public void setConstructs(Construct[] constructs) {
        this.constructs = constructs;
    }

    public Construct getConstruct(int pos) {
        return constructs[pos];
    }

    public Construct createConstruct (int pos){
        return constructs[pos].clone();
    }


}
