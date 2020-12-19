package nju.zjl.cvs;

public class CreatureFactory {
    public static Creature generatePlainCreature(int x, int y, Camp camp){
        return new Creature(camp, x * Constants.COLUMNS + y, 100, 10, 3, AffectorFactory::generatePlainBullet);
    }
}
