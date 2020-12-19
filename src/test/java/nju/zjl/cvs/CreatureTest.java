package nju.zjl.cvs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.*;


public class CreatureTest {
    @Test
    public void creatureTest1(){
        c1.inst = Instruction.newMoveInst(35);
        for(int i = 0; i < 3000; i++){
            c1.update(items);
        }
        assertEquals(35, c1.getPos());
        assertEquals(Instruction.Action.NULL, c1.inst.action);
        c1.inst = Instruction.newMoveInst(78);
        for(int i = 0; i < 3000; i++){
            c1.update(items);
        }
        assertEquals(78, c1.getPos());
        assertEquals(Instruction.Action.NULL, c1.inst.action);
    }

    @Test
    public void creatureTest2(){
        c1.update(items);
        assertNotEquals(0, items.atQueue.size());
        assertEquals(c2.getId(), ((Bullet)items.atQueue.poll()).target);
        for(int i = 0; i < 7 * Constants.CREATUREATTACKCD; i++){
            c1.update(items);
        }
        assertEquals(7, items.atQueue.size());
    }

    @Test
    public void creatureTest3(){
        c1.inst = Instruction.newAttackInst(c3.getId());
        c1.update(items);
        assertEquals(0, items.atQueue.size());
        while(items.atQueue.isEmpty()){
            c1.update(items);
        }
        assertEquals(c3.getId(), ((Bullet)items.atQueue.poll()).target);
    }

    public CreatureTest(){
        items = new ItemManager();
        c1 = CreatureFactory.generatePlainCreature(2, 2, Camp.SNAKE);
        c2 = CreatureFactory.generatePlainCreature(5, 4, Camp.CALABASH);
        c3 = CreatureFactory.generatePlainCreature(6, 8, Camp.CALABASH);
        items.addCreature(c1);
        items.addCreature(c2);
        items.addCreature(c3);
    }

    ItemManager items;
    Creature c1;
    Creature c2;
    Creature c3;
}
