package nju.zjl.cvs.game;

public class AffectorFactory {
    public static Affector generatePlainBullet(int x, int y, int target, int damage){
        return new Bullet(x, y, target, damage, "redBullet");
    }

    public static Affector generateRedBullet(int x, int y, int target, int damage){
        return new Bullet(x, y, target, damage, "redBullet");
    }

    public static Affector generateGreenBullet(int x, int y, int target, int damage){
        return new Bullet(x, y, target, damage, "greenBullet");
    }

    public static Affector generateBlueBullet(int x, int y, int target, int damage){
        return new Bullet(x, y, target, damage, "blueBullet");
    }
    public static Affector generateOrangeBullet(int x, int y, int target, int damage){
        return new Bullet(x, y, target, damage, "orangeBullet");
    }
    public static Affector generateCyanBullet(int x, int y, int target, int damage){
        return new Bullet(x, y, target, damage, "cyanBullet");
    }
    public static Affector generateYellowBullet(int x, int y, int target, int damage){
        return new Bullet(x, y, target, damage, "yellowBullet");
    }
    public static Affector generatePurpleBullet(int x, int y, int target, int damage){
        return new Bullet(x, y, target, damage, "purpleBullet");
    }

}
