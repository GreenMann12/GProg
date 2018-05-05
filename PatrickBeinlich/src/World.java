import java.util.Random;

public class World {
    public static int width = 1000;
    public static int heigth = 255;

    public int[][] map = new int[width][heigth];

    public void generateWorld() {
        int air = 70;
        int ground = 5;
        int acth = 70;
        int numberTrees = 100;
        int numberiron = 50;
        int numbercopper = 30;
        int numbersilver = 20;
        int numbergold = 10;
        int numberwater = 20;

        Random random = new Random();

        for (int x = 0; x < width; x++) {
            int upordown = random.nextInt(5);

            if (upordown == 0 && acth < (air +  40) ){
                acth++;
            } else if (upordown == 4 && acth > (air - 40)){
                acth--;
            }

            for (int y = 0; y < heigth; y++) {
                if (y < acth){
                    map[x][y] = 0;
                } else if (y < acth+ground) {
                    map[x][y] = 1;
                } else {
                    map[x][y] = 2;
                }
            }
        }

        //<editor-fold desc="Water">
        while (numberwater != 0){
            int x = random.nextInt(width-10)+5;
            int y = 0;

            while (y < heigth && map[x][y] == 0) {
                y++;
            }

            map[x][y] = 10;

            numberwater--;
        }
        //</editor-fold>

        //<editor-fold desc="Trees">
        while (numberTrees != 0){
            int x = random.nextInt(width - 10) + 5;
            int y = 0;

            while (y < heigth) {
                if (map[x][y] != 1) {
                    y++;
                } else {
                    // Tree trunk
                    map[x][y - 1] = 3;
                    map[x][y - 2] = 3;
                    map[x][y - 3] = 3;
                    map[x][y - 4] = 3;
                    map[x][y - 5] = 3;

                    // Leaves

                    map[x - 1][y - 5] = 9;
                    map[x + 1][y - 5] = 9;

                    map[x][y - 6] = 9;
                    map[x - 1][y - 6] = 9;
                    map[x + 1][y - 6] = 9;
                    map[x - 2][y - 6] = 9;
                    map[x + 2][y - 6] = 9;

                    map[x][y - 7] = 9;
                    map[x - 1][y - 7] = 9;
                    map[x + 1][y - 7] = 9;

                    map[x][y - 8] = 9;

                    numberTrees--;
                    break;
                }
            }
        }
        //</editor-fold>

        // place iron ore
        placeOre(numberiron, 4);

        // place copper ore
        placeOre(numbercopper, 5);

        // place silver ore
        placeOre(numbersilver, 6);

        // place gold ore
        placeOre(numbergold, 7);
    }

    private void placeOre(int number, int oreid){
        Random random = new Random();

        for (int count = 0; count < number; count++) {
            int x = random.nextInt(width - 10) + 5;
            int y = 0;

            while (y < heigth && map[x][y] != 2) {
                y++;
            }


            if (y != heigth) {
                y = random.nextInt(heigth - (y + 10)) + y;

                map[x][y] = oreid;

                int exp = 5;


                int r = random.nextInt(exp);
                for (int c = 0; c < r; c++) {
                    map[x + c][y] = oreid;
                }

                r = random.nextInt(exp);
                for (int c = 0; c < r; c++) {
                    map[x - c][y] = oreid;
                }

                r = random.nextInt(exp);
                for (int c = 0; c < r; c++) {
                    map[x][y + c] = oreid;
                }

                r = random.nextInt(exp);
                for (int c = 0; c < r; c++) {
                    map[x][y - c] = oreid;
                }


                // check if the diagonales should be ore too
                //SE
                if (map[x + 2][y] == oreid && map[x][y + 2] == oreid) {
                    map[x + 1][y + 1] = oreid;
                }
                //SW
                if (map[x - 2][y] == oreid && map[x][y + 2] == oreid) {
                    map[x - 1][y + 1] = oreid;
                }
                //NW
                if (map[x - 2][y] == oreid && map[x][y - 2] == oreid) {
                    map[x - 1][y - 1] = oreid;
                }
                //NE
                if (map[x + 2][y] == oreid && map[x][y - 2] == oreid) {
                    map[x + 1][y - 1] = oreid;
                }
            }
        }
    }
}