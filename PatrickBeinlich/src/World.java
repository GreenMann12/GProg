import java.util.Random;

public class World {
    public static int width = 1000;
    public static int heigth = 255;

    public int[][] world = new int[width][heigth];

    public void generateWorld(){
        int hoverground = 150;
        int numberTrees = 100;
        int numberiron = 20;
        int numbercopper = 10;
        int numbersilver = 10;
        int nummbergold = 5;

        Random random = new Random();

        for(int x = 0; x < width; x++){
            for(int y = 0; y < heigth; y++){
                if (y < hoverground){
                    world[x][y] = 0;
                } else if (y < hoverground + 10) {
                    world[x][y] = 1;
                } else {
                    world[x][y] = 2;
                }
            }
        }

        //<editor-fold desc="Trees">
        for (int count = 0; count < numberTrees; count++){
            int x = random.nextInt(width-10)+5;
            int y = 0;

            while (y < heigth){
                if(world[x][y] != 1) {
                    y++;
                } else {
                    // Tree trunk
                    world[x][y-1] = 3;
                    world[x][y-2] = 3;
                    world[x][y-3] = 3;
                    world[x][y-4] = 3;
                    world[x][y-5] = 3;

                    // Leaves

                    world[x-1][y-5] = 9;
                    world[x+1][y-5] = 9;

                    world[x][y-6] = 9;
                    world[x-1][y-6] = 9;
                    world[x+1][y-6] = 9;
                    world[x-2][y-6] = 9;
                    world[x+2][y-6] = 9;

                    world[x][y-7] = 9;
                    world[x-1][y-7] = 9;
                    world[x+1][y-7] = 9;

                    world[x][y-8] = 9;

                    break;
                }
            }
        }
        //</editor-fold>


        

        //<editor-fold desc=" ">

        //</editor-fold>




    }
}
