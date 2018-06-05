package gprog.Items;

import java.io.Serializable;

/**
 * Created by Patrick on 29.05.2018.
 */
public abstract class Item implements Serializable{

    private int ID;

    private int miningLevel;

    public void setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}

/*
Block-ID        Block-Art		Mining-Level
0               air			    0
1               dirt			1
2               stone			2
3               wood			5
4               iron-ore		2
5               copper-ore		2
6		        silver-ore		2
7               gold-ore		3
8               diamond			4
9		        leaves			1
10		        water			0
11		        sand			1
20              plant           1

Mining-Level:
0 -> nicht abaubar
1 -> auch ohne Tool abbaubar
2 -> Pickaxe
3 -> Stronger Pickaxe
4 -> Strongest Pickaxe
5 -> Axe


Tool-ID         Tool-Art		    Mining-Level    AttackPower
12              Pickaxe			    2               10
13              StrongerPickaxe     3               10
14              StrongestPickaxe	4               10
15              Axe			        5               20

 */
