package nju.zjl.cvs;

import static org.junit.Assert.*;

import org.junit.*;

public class BulletTest {
    @Test
    public void bulletTest1(){
        Affector bullet = AffectorFactory.generatePlainBullet(targetX, targetY - 200, target.getId(), 10);
        items.addAffector(bullet);
        assertEquals(100, target.getHp());
        while(items.atQueue.contains(bullet)){
            bullet.update(items);
        }
        assertEquals(90, target.getHp());
    }    

    @Test
    public void bulletTest2(){
        Affector bullet = AffectorFactory.generatePlainBullet(targetX + 300, targetY - 200, target.getId(), 110);
        items.addAffector(bullet);
        assertEquals(100, target.getHp());
        while(items.atQueue.contains(bullet)){
            bullet.update(items);
        }
        assertTrue(!items.ctIdMap.containsKey(target.getId()));
    }    


    public BulletTest(){
        target = CreatureFactory.generatePlainCreature(3, 4, Camp.CALABASH);
        int[] ret = Constants.creaturePos2BulletPos(target.getPos());
        targetX = ret[0];
        targetY = ret[1];
        items = new ItemManager();
        items.addCreature(target);
        items.addCreature(CreatureFactory.generatePlainCreature(7, 8, Camp.CALABASH));
        items.addCreature(CreatureFactory.generatePlainCreature(3, 6, Camp.CALABASH));
        items.addCreature(CreatureFactory.generatePlainCreature(2, 5, Camp.CALABASH));
    }

    int targetX;
    int targetY;
    Creature target;
    ItemManager items;
}
