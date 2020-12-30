package nju.zjl.cvs.game;

import nju.zjl.cvs.game.Constants.Camp;

public class CreatureFactory {
    public static Creature generatePlainCreature(int x, int y, Camp camp){
        return new Creature(camp, x * Constants.COLUMNS + y, 100, 10, 3, AffectorFactory::generatePlainBullet, "red");
    }

    public static Creature generateCalabash(int x, int y, String name){
        switch(name){
            case "red":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 1, AffectorFactory::generateRedBullet, "red");
            case "orange":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generateOrangeBullet, "orange");
            case "yellow":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generateYellowBullet, "yellow");
            case "green":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generateGreenBullet, "green");
            case "cyan":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generateCyanBullet, "cyan");
            case "blue":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generateBlueBullet, "blue");
            case "purple":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generatePurpleBullet, "purple");
            default:
                return null;
        }
    }

    public static Creature generateSnake(int x, int y){
        return new Creature(Camp.SNAKE, x * Constants.COLUMNS + y, 1000, 20, 5, AffectorFactory::generatePlainBullet, "snake");
    }

    public static Creature generateScorpion(int x, int y){
        return new Creature(Camp.SNAKE, x * Constants.COLUMNS + y, 1000, 20, 2, AffectorFactory::generatePlainBullet, "scorpion");
    }
}
