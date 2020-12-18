package nju.zjl.cvs;

public class CreatureFactory {
    public static Creature generatePlainCreature(int x, int y){
        return new Creature(Camp.CALABASH, x * Constants.COLUMNS + y, 100, 10, 3, AffectorFactory::generatePlainBullet);
    }
}
