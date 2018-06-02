package gprog;

public class Main {
    public static void main(String[] args) {
        World world = new World();
        world.generateWorld();
        /*
        for (int y = 0; y < World.heigth; y++){
            for (int x = 0; x < World.width; x++){
                System.out.print(world.map[x][y]);
                if (world.map[x][y] < 10){
                    System.out.print("  ");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
*/
        //System.out.println(World.width);
    }
}
