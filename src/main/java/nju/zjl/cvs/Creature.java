package nju.zjl.cvs;

import java.util.LinkedList;
import java.util.stream.IntStream;

abstract class Creature {
    void update(ItemManager items){
        moveCD -= 1;
        atkCD -= 1;
        switch(inst.action){
            case NULL:
                autoAttack(items);
                break;
            case MOVE:
                moveTo(inst.pos, items);
                break;
            case ATTACK:
                attack(inst.target, items);
                break;
            case CAST:
                break;
            default:
                break;
        }
    }

    protected boolean hurt(int damage){
        hp -= damage;
        return hp <= 0;
    }

    protected void moveTo(int dest, ItemManager items){
        if(moveCD > 0){
            return;
        }
        if( movePath == null || //has not yet compute path 
            movePath.peekLast() != dest || //dest has changed
            items.getCreatureByPos(movePath.peekFirst()) != null //map has changed, next hop was peroccupied 
            ){
            if(computePath(dest, items.getIntCreatureMap())){
                return;
            }
        }
        int next = movePath.pollFirst();
        items.moveCreature(pos, next);
        pos = next;
        moveCD = Constants.CREATUREMOVECD;
        if(movePath.isEmpty()){ //reach the dest, remove the instruction
            inst = Instruction.newNullInst();
        }
    }

    protected boolean computePath(int dest, int[] map){
        int[] path = Algorithms.findPath(map, Constants.COLUMNS, pos, dest);
        if(path.length == 0){ //cannot move to dest, instruction was illeagal, drop it
            movePath = null;
            inst = Instruction.newNullInst();
            return false;
        }
        movePath = new LinkedList<>();
        IntStream.of(path).
            boxed().
            forEach(movePath::add);
        movePath.pollFirst();
        return true;
    }

    protected void attack(int target, ItemManager items){
        Creature ct = items.getCreatureById(target);
        if(ct == null){ //target was already dead, drop instruction
            inst = Instruction.newNullInst();
            return;
        }
        int tPos = ct.getPos();
        int dis = Math.max(Math.abs(pos - tPos) / Constants.COLUMNS, Math.abs(pos - tPos) % Constants.COLUMNS);
        if(dis > atkRange){ //out of attack range, first try to move towards it
            moveTo(tPos, items);
            return;
        }
        if(atkCD > 0){
            return;
        }
        generateBullet(target, items);
        atkCD = Constants.CREATUREATTACKCD;
    }

    protected void autoAttack(ItemManager items){
        if(atkCD > 0){
            return;
        }
        int x = pos / Constants.COLUMNS - atkRange;
        int y = pos % Constants.COLUMNS - atkRange;
        for(int i = 0; i < 2 * atkRange + 1; i++)
            for(int j = 0; j < 2 * atkRange + 1; j++){
                if(x + i < 0 || x + i >= Constants.ROWS || y + j < 0 || y + j >= Constants.COLUMNS){
                    continue;
                }
                Creature ct = items.getCreatureByPos((x + i) * Constants.COLUMNS + y + j);
                if(ct == null || ct.getCamp() == camp){
                    continue;
                }
                generateBullet(ct.getId(), items);
                atkCD = Constants.CREATUREATTACKCD;
            }
    }

    protected void addBuff(Buff buff){

    }

    int getId(){
        return id;
    }

    int getPos(){
        return pos;
    }

    Camp getCamp(){
        return camp;
    }

    protected Creature(Camp camp, int pos, int hp, int atk, int atkRange){
        this.id = identifier++;
        this.camp = camp;
        this.pos = pos;
        this.hp = hp;
        this.atk = atk;
        this.atkRange = atkRange;
        this.inst = Instruction.newNullInst();
        this.moveCD = 0;
        this.atkCD = 0;
        this.movePath = null;
    }
    
    abstract void generateBullet(int target, ItemManager items);

    private static int identifier = 0;

    protected int id;
    protected Camp camp;
    protected int pos;

    protected Instruction inst;

    protected int hp;

    protected int moveCD;
    protected LinkedList<Integer> movePath;

    protected int atk;
    protected int atkCD;
    protected int atkRange;
}