package nju.zjl.cvs;

import javafx.scene.paint.Color;

public class AffectorFactory {
    public static Affector generatePlainBullet(int x, int y, int target, int damage){
        return new Bullet(x, y, target, damage, Color.BLACK, null);
    }
}
