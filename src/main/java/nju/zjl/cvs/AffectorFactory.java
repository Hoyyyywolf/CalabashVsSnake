package nju.zjl.cvs;

public class AffectorFactory {
    public static Bullet generatePlainBullet(int x, int y, int target, int damage){
        return new Bullet(x, y, target, damage, null);
    }
}
