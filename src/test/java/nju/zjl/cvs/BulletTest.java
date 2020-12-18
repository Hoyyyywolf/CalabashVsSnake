package nju.zjl.cvs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.*;

public class BulletTest {
    @Test
    public void BulletTest1(){
        Affector bullet = AffectorFactory.generatePlainBullet(targetX, targetY - 200, target.getId(), 10);
        items.addAffector(bullet);
        assertEquals(100, target.getHp());
        while(items.atQueue.contains(bullet)){
            bullet.update(items);
        }
        assertEquals(90, target.getHp());
    }    

    @Test
    public void BulletTest2(){
        Affector bullet = AffectorFactory.generatePlainBullet(targetX + 300, targetY - 200, target.getId(), 110);
        items.addAffector(bullet);
        assertEquals(100, target.getHp());
        while(items.atQueue.contains(bullet)){
            bullet.update(items);
        }
        assertTrue(!items.ctIdMap.containsKey(target.getId()));
    }    


    public BulletTest(){
        target = CreatureFactory.generatePlainCreature(3, 4);
        int[] ret = Constants.creaturePos2BulletPos(target.getPos());
        targetX = ret[0];
        targetY = ret[1];
        items = new ItemManager();
        items.addCreature(target);
        items.addCreature(CreatureFactory.generatePlainCreature(7, 8));
        items.addCreature(CreatureFactory.generatePlainCreature(3, 6));
        items.addCreature(CreatureFactory.generatePlainCreature(2, 5));
    }

    int targetX;
    int targetY;
    Creature target;
    ItemManager items;
}
