package nju.zjl.cvs;

public class CreatureFactory {
    public static Creature generatePlainCreature(int x, int y, Camp camp){
        return new Creature(camp, x * Constants.COLUMNS + y, 100, 10, 3, AffectorFactory::generatePlainBullet, "red.png");
    }

    public static Creature generateCalabash(int x, int y, String name){
        switch(name){
            case "red":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 1, AffectorFactory::generatePlainBullet, "red.png");
            case "orange":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generatePlainBullet, "orange.png");
            case "yellow":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generatePlainBullet, "yellow.png");
            case "green":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generatePlainBullet, "green.png");
            case "cyan":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generatePlainBullet, "cyan.png");
            case "blue":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generatePlainBullet, "blue.png");
            case "purple":
                return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generatePlainBullet, "purple.png");
            default:
                return null;
        }
    }

    public static Creature generateGranpa(int x, int y){
        return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 2, AffectorFactory::generatePlainBullet, "grandpa.png");
    }

    public static Creature generateSnake(int x, int y){
        return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 1000, 20, 5, AffectorFactory::generatePlainBullet, "snake.png");
    }

    public static Creature generateScorpion(int x, int y){
        return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 1000, 20, 2, AffectorFactory::generatePlainBullet, "scorpion.png");
    }
}
