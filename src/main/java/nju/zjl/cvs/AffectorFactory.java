package nju.zjl.cvs;

public class AffectorFactory {
    public Bullet generatePlainBullet(int x, int y, int target, int damage){
        return new Bullet(x, y, target, damage, null);
    }
}
