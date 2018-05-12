

public class World {
    public static int width = 1000;
    public static int heigth = 300;

    public int[][] map;  //--------------------------------------------------------------------------------------------- Größenzuweisung entfernen

    public void generateWorld() {
        String seed = "1014";

        GenerateWorld generateWorld = new GenerateWorld();
        map = generateWorld.createWorld(seed, heigth);
    }   //------------------------------------------------------------------------------ alte generationWorld ersetzen
}