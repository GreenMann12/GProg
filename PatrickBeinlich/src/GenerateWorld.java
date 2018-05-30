import java.util.ArrayList;
import java.util.Random;

public class GenerateWorld {
    int[][] map;
    ArrayList<Biom> bioms = new ArrayList<>();
    int heigth;
    int width;

    Random random = new Random();

    int waterlevel = 100;
    int acth = 100;
    int ground = 5;

    int numberiron = 50;
    int numbercopper = 30;
    int numbersilver = 20;
    int numbergold = 10;
    int numberdiamond = 10;


    public int[][] createWorld(String seed, int pheight) {
        heigth = pheight;

        width = splitseed(seed);
        map = new int[width][heigth];
        World.width = width;

        createLandscape();

        calculateResourceNumbers();

        // place iron ore
        placeOre(numberiron, 4);

        // place copper ore
        placeOre(numbercopper, 5);

        // place silver ore
        placeOre(numbersilver, 6);

        // place gold ore
        placeOre(numbergold, 7);

        placeOre(numberdiamond, 8);

        // place Trees
        placeRandomlyTrees();

        return map;
    }

    private int splitseed(String seed) {
        ArrayList<Integer> digits = new ArrayList<>();
        int size = 0;

        if (seed.length() < 2){
            seed = randomString();
        }

        char[] cseed = seed.toCharArray();

        for (char c : cseed) {
            int i = parseCharToInt(c);

            while (i >= 10) {
                digits.add(i % 10);
                i /= 10;
            }

            digits.add(i);
        }

        if (!checkworldsize(digits)) {
            bioms.add(new Biom(1, 200));
            bioms.add(new Biom(2, 200));
            bioms.add(new Biom(3, 200));
            bioms.add(new Biom(4, 200));

            size = 800;
        } else {
            for (int a = 1; a < digits.size(); a = a + 2) {
                int bsize = digits.get(a - 1) * 100;

                bioms.add(new Biom(digits.get(a), bsize));
                size += bsize;
            }
        }
        return size;
    }

    private void createLandscape() {
        int start = 0;
        int end = 0;
        int border = 0;
        int prevbiom = 0;
        int curbiom = 0;

        for (int i = 0; i < bioms.size(); i++) {
            curbiom = bioms.get(i).biomID;

            if (i > 0) {
                prevbiom = bioms.get(i - 1).biomID;
            }

            end = start + bioms.get(i).size;

            if (curbiom == 0) {
                border = transition(start, waterlevel + 20);
                createOcean(border, end);
            } else if (curbiom == 1) {
                border = transition(start, waterlevel - 15);
                createFlatland(border, end);
            } else if (curbiom == 2) {
                border = transition(start, waterlevel - 30);
                createHills(border, end);
            } else if (curbiom == 3) {
                border = transitionMountains(start, prevbiom);
                createMountains(border, end);
            } else if (curbiom == 4) {
                border = transitionDesert(start, prevbiom);
                createDesert(border, end);
            } else {
                border = transition(start, waterlevel - 20);
                createStandardLandscape(border, end);
            }

            start = end;
        }
    }

    private void calculateResourceNumbers() {
        numberiron = width / 15;
        numbercopper = width / 25;
        numbersilver = width / 40;
        numbergold = width / 70;
        numberdiamond = width / 100;
    }



    private void createOcean(int start, int end) {
        acth = waterlevel + 20;

        for (int x = start; x < end; x++) {
            fillStandard(x);
        }
    }

    private void createFlatland(int start, int end) {
        int groundlevel = waterlevel - 15;

        for (int x = start; x < end; x++) {
            int upordown = random.nextInt(12);

            if (upordown == 0 && acth < (groundlevel + 7)) {
                acth++;
            } else if (upordown == 11 && acth > (groundlevel - 7)) {
                acth--;
            }

            fillStandard(x);
        }
    }

    private void createHills(int start, int end) {
        int groundlevel = waterlevel - 30;

        for (int x = start; x < end; x++) {
            int upordown = random.nextInt(5);

            if (upordown == 0 && acth < (groundlevel + 20)) {
                acth += 2;
            } else if (upordown == 1 && acth < groundlevel + 20) {
                acth++;
            } else if (upordown == 3 && acth < groundlevel - 20) {
                acth--;
            } else if (upordown == 4 && acth > (groundlevel - 20)) {
                acth -= 2;
            }

            fillStandard(x);
        }
    }

    private void createMountains(int start, int end) {
        int groundlevel = waterlevel - 50;

        for (int x = start; x < end; x++) {
            int upordown = random.nextInt(21);

            if (upordown >= 0 && upordown <= 2 && acth < (groundlevel + 40)) {
                acth = acth + 3;
            } else if (upordown >= 3 && upordown <= 6 && acth < (groundlevel + 40)) {
                acth = acth + 2;
            } else if (upordown >= 7 && upordown <= 9 && acth < (groundlevel + 40)) {
                acth = acth + 1;
            } else if (upordown >= 11 && upordown <= 13 && acth > (groundlevel - 40)) {
                acth = acth - 1;
            } else if (upordown >= 14 && upordown <= 17 && acth > (groundlevel - 40)) {
                acth = acth - 2;
            } else if (upordown >= 18 && upordown <= 20 && acth > (groundlevel - 40)) {
                acth = acth - 3;
            }

            fillStandard(x);
        }
    }

    private void createDesert(int start, int end) {
        int groundlevel = waterlevel - 20;
        int sandheigth = 10;

        for (int x = start; x < end; x++) {
            int upordown = random.nextInt(5);

            if (upordown == 0 && acth < (groundlevel + 15)) {
                acth++;
            } else if (upordown == 4 && acth > (groundlevel - 15)) {
                acth--;
            }

            fillDesert(x, sandheigth);
        }
    }

    private void createStandardLandscape(int start, int end) {
        int groundlevel = waterlevel - 20;

        acth = groundlevel;

        for (int x = start; x < end; x++) {
            fillStandard(x);
        }
    }



    private int transition(int start, int targeth ) {
        int starth = acth;

        if (start == 0) {
            acth = targeth;
        } else {
            if (acth > targeth) {
                while (acth > targeth) {
                    if (Math.abs(starth - acth)<=2){
                        if (start % 2 == 0) {
                            acth--;
                        }
                    } else if (Math.abs(acth - targeth)<=2){
                        if (start % 2 == 0){
                            acth--;
                        }
                    } else if (Math.abs(starth - acth)<=5){
                        acth--;
                    } else if (Math.abs(acth - targeth) <=5){
                        acth--;
                    } else {
                        acth -= 2;
                    }

                    fillStandard(start);
                    start++;
                }
            } else if (acth < targeth) {
                while (acth < targeth) {
                    if (Math.abs(starth - acth)<=2){
                        if (start % 2 == 0) {
                            acth++;
                        }
                    } else if (Math.abs(acth - targeth)<=2){
                        if (start % 2 == 0){
                            acth++;
                        }
                    } else if (Math.abs(starth - acth)<=5){
                        acth++;
                    } else if (Math.abs(acth - targeth) <=5){
                        acth++;
                    } else {
                        acth += 2;
                    }

                    fillStandard(start);
                    start++;
                }
            }
        }

        return start;
    }

    private int transitionMountains(int start, int id) {
        int targeth = waterlevel - 50;
        if (start == 0) {                                                                                                // If the biom is the first
            acth = targeth;
        } else if (id != 3) {                                                                                            // if the biom before wasn't a mountain biom
            while (acth > targeth) {
                acth -= 2;

                fillStandard(start);
                start++;
            }
        }

        return start;
    }

    private int transitionDesert(int start, int id) {
        int targeth = waterlevel - 20;
        int sandheight = 0;
        int starth = acth;

        if (start == 0) {
            acth = targeth;
        } else if (id != 4) {
            if (acth > targeth) {
                while (acth > targeth) {
                    if (Math.abs(starth - acth)<=2){
                        sandheight = 2;
                        if (start % 2 == 0) {
                            acth--;
                        }
                    } else if (Math.abs(acth - targeth)<=2){
                        sandheight = 9;
                        if (start % 2 == 0){
                            acth--;
                        }
                    } else if (Math.abs(starth - acth)<=5){
                        sandheight = 4;
                        acth--;
                    } else if (Math.abs(acth - targeth) <=5){
                        sandheight = 8;
                        acth--;
                    } else {
                        sandheight = 6;
                        acth -= 2;
                    }

                    fillDesert(start, sandheight);
                    start++;
                }
            } else if (acth < targeth) {
                while (acth < targeth) {
                    if (Math.abs(starth - acth)<=2){
                        sandheight = 2;
                        if (start % 2 == 0) {
                            acth++;
                        }
                    } else if (Math.abs(acth - targeth)<=2){
                        sandheight = 9;
                        if (start % 2 == 0){
                            acth++;
                        }
                    } else if (Math.abs(starth - acth)<=5){
                        sandheight = 4;
                        acth++;
                    } else if (Math.abs(acth - targeth) <=5){
                        sandheight = 8;
                        acth++;
                    } else {
                        sandheight = 6;
                        acth += 2;
                    }

                    fillDesert(start, sandheight);
                    start++;
                }
            }
        }

        return start;
    }



    private void placeRandomlyTrees() {
        int start = 0;
        int end = 0;
        int number = 0;
        for (Biom b : bioms) {
            if (b.biomID == 1 || b.biomID == 2) {
                end = start + b.size;
                int probability = random.nextInt(5);

                number = 0;

                if (probability == 0) {                                                                                  // a few trees in the biom
                    number = b.size * 25 / 100;
                } else if (probability == 1 || probability == 2) {                                                       // a few trees in the biom
                    number = b.size * 3 / 100;
                }

                placeTrees(start, end, number);
            }
            start += b.size;
        }
    }

    private void placeTrees(int start, int end, int number) {
        start += 5;
        end -= 5;

        while (number != 0) {
            int x = newRandom(start, end);
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

                    number--;
                    break;
                }
            }
        }
    }

    private void placeWater(int start, int end, int number) {
        while (number != 0) {
            int x = newRandom(start, end);
            int y = 0;

            while (y < heigth && map[x][y] == 0) {
                y++;
            }

            map[x][y] = 10;

            number--;
        }
    }

    private void placePlants(int start, int end, int number, int id) {
        while (number != 0) {
            int x = newRandom(start, end);
            int y = 0;

            while (y < heigth && map[x][y] == 0) {
                y++;
            }

            map[x][--y] = id;

            number--;
        }
        //
    }

    private void placeOre(int number, int oreid) {
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



    private void fillStandard(int x) {
        for (int y = 0; y < heigth; y++) {
            if (y > acth + ground || (y > acth && y < 60)) {
                map[x][y] = 2;
            } else if (y > acth && acth < waterlevel) {
                map[x][y] = 1;
            } else if (y > acth && acth >= waterlevel) {
                map[x][y] = 11;
            } else if (y > waterlevel) {
                map[x][y] = 10;
            } else {
                map[x][y] = 0;
            }
        }
    }

    private void fillDesert(int x, int sandheigth) {
        for (int y = 0; y < heigth; y++) {
            if (y > acth + sandheigth){
                map[x][y] = 2;
            } else if (y > acth){
                map[x][y] = 11;
            } else if (y > waterlevel){
                map[x][y] = 10;
            } else {
                map[x][y] = 0;
            }
        }
    }



    private boolean checkworldsize(ArrayList<Integer> seed){
        int size = 0;
        for (int i = 0; i < seed.size(); i += 2){
            size += seed.get(i);
        }

        if (size >= 4){
            return true;
        } else {
            return false;
        }
    }

    private String randomString() {
        final int STRING_LENGTH = 4;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < STRING_LENGTH; i++)
        {
            sb.append((char)((int)(Math.random()*26)+97));
        }
        return sb.toString();
    }

    private int parseCharToInt(char c) {
        try {
            return Integer.parseInt(Character.toString(c));
        } catch (Exception e) {
            return c;
        }
    }

    private int newRandom(int min, int max) {
        int diff = max - min;
        return (int) (Math.random() * diff) + min;
    }
}