import java.util.Random;

public class World {
    public static int width = 1000;
    public static int heigth = 255;

    public int[][] map = new int[width][heigth];

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
                    map[x][y] = 0;
                } else if (y < hoverground + 10) {
                    map[x][y] = 1;
                } else {
                    map[x][y] = 2;
                }
            }
        }

        //<editor-fold desc="Trees">
        for (int count = 0; count < numberTrees; count++){
            int x = random.nextInt(width-10)+5;
            int y = 0;

            while (y < heigth){
                if(map[x][y] != 1) {
                    y++;
                } else {
                    // Tree trunk
                    map[x][y-1] = 3;
                    map[x][y-2] = 3;
                    map[x][y-3] = 3;
                    map[x][y-4] = 3;
                    map[x][y-5] = 3;

                    // Leaves

                    map[x-1][y-5] = 9;
                    map[x+1][y-5] = 9;

                    map[x][y-6] = 9;
                    map[x-1][y-6] = 9;
                    map[x+1][y-6] = 9;
                    map[x-2][y-6] = 9;
                    map[x+2][y-6] = 9;

                    map[x][y-7] = 9;
                    map[x-1][y-7] = 9;
                    map[x+1][y-7] = 9;

                    map[x][y-8] = 9;

                    break;
                }
            }
        }
        //</editor-fold>

        //<editor-fold desc="Iron Ore">
        for (int count = 0; count < numberiron; count++){
            int x = random.nextInt(width-10)+5;
            int y = 0;

            while (y < heigth && map[x][y] != 2){
                y++;
            }


            if (y != heigth){
                y = random.nextInt(heigth-10)+y;
            }



        }
        //</editor-fold>


        //<editor-fold desc=" ">

        //</editor-fold>




    }
}
