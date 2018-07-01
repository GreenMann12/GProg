package gprog;

import gprog.Items.Item;

public class World {
    public static int width = 1000;
    public static int heigth = 300;

    public Item[][] map;  //--------------------------------------------------------------------------------------------- Größenzuweisung entfernen

    public void generateWorld() {
        String seed = "2130242223";

        GenerateWorld generateWorld = new GenerateWorld();
        map = generateWorld.createWorld(seed, heigth);
    }   //------------------------------------------------------------------------------ alte generationWorld ersetzen
}