package nju.zjl.cvs.game;

public class AffectorFactory {
    public static Affector generatePlainBullet(int x, int y, int target, int damage){
        return new GuidedBullet(x, y, target, damage, "yellowBullet");
    }

    public static Affector generateRedBullet(int x, int y, int target, int damage){
        return new GuidedBullet(x, y, target, damage, "greenBullet");
    }

    public static Affector generateGreenBullet(int x, int y, int target, int damage){
        return new GuidedBullet(x, y, target, damage, "greenBullet");
    }

    public static Affector generateBlueBullet(int x, int y, int target, int damage){
        return new GuidedBullet(x, y, target, damage, "blueBullet");
    }

    public static Affector generateOrangeBullet(int x, int y, int target, int damage){
        return new GuidedBullet(x, y, target, damage, "orangeBullet");
    }

    public static Affector generateCyanBullet(int x, int y, int target, int damage){
        return new GuidedBullet(x, y, target, damage, "cyanBullet");
    }

    public static Affector generateYellowBullet(int x, int y, int target, int damage){
        return new GuidedBullet(x, y, target, damage, "yellowBullet");
    }

    public static Affector generatePurpleBullet(int x, int y, int target, int damage){
        return new GuidedBullet(x, y, target, damage, "purpleBullet");
    }

    public static Affector generatePenetrableBullet(int x, int y, int target, int damage){
        return new PenetrableBullet(x, y, target, damage, "straightBullet");
    }

    public static Affector generateBouncingBullet(int x, int y, int target, int damage){
        return new BouncingBullet(x, y, target, damage, "redBullet");
    }
}
