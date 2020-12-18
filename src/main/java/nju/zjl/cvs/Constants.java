package nju.zjl.cvs;

public class Constants {
    //map size
    public static final int ROWS = 10;
    public static final int COLUMNS = 15;

    //game FPS
    public static final int FPS = 30;

    //creature move speed, 15 frames(0.5s) to move 1 grid
    public static final int CREATUREMOVECD = 15;
    public static final int CREATUREATTACKCD = 45;

    public static final int BULLETSPEED = 50;

    public static final int GRIDWIDTH = 50;
    public static final int GRIDHEIGHT = 50;


    public static int bulletPos2CreaturePos(int x, int y){
        return x / GRIDWIDTH + y / GRIDHEIGHT * COLUMNS;
    }

    public static int[] creaturePos2BulletPos(int pos){
        int[] ret = new int[2];
        ret[0] = pos % COLUMNS * GRIDWIDTH + GRIDWIDTH / 2;
        ret[1] = pos / COLUMNS * GRIDHEIGHT + GRIDHEIGHT / 2;
        return ret;
    }
}
