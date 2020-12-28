package nju.zjl.cvs.game;

public class Constants {
    public enum Camp {
        CALABASH, SNAKE
    }

    //map size
    public static final int ROWS = 8;
    public static final int COLUMNS = 10;

    //game FPS
    public static final int FPS = 30;

    //creature move speed, 15 frames(0.5s) to move 1 grid
    public static final int CREATUREMOVECD = 15;
    public static final int CREATUREATTACKCD = 45;

    public static final int BULLETSPEED = 10;

    public static final int GRIDWIDTH = 70;
    public static final int GRIDHEIGHT = 70;

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
