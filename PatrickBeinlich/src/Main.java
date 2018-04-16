public class Main {
    public static void main(String[] args) {
        World world = new World();
        world.generateWorld();

        for (int y = 0; y < World.heigth; y++){
            for (int x = 0; x < World.width; x++){
                System.out.print(world.world[x][y] + " ");
            }
            System.out.print("\n");
        }
    }
}
