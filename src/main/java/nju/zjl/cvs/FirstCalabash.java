package nju.zjl.cvs;

import static com.google.common.base.Preconditions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class FirstCalabash{
    void moveTo(int dest){
        if(moveCD > 0){
            return;
        }
        if( movePath == null || //has not yet compute path 
            movePath.peekLast() != dest || //dest has changed
            MainController.getCreatureByPos(movePath.peekFirst()) != null //map has changed, next hop was peroccupied 
            ){
            if(computePath(dest)){
                return;
            }
        }
        int next = movePath.pollFirst();
        MainController.moveCreature(pos, next);
        pos = next;
        moveCD = Constants.CREATUREMOVECD;
        if(movePath.isEmpty()){ //reach the dest, remove the instruction
            inst = Instruction.newNullInst();
        }
    }

    boolean computePath(int dest){
        int[] path = Algorithms.findPath(MainController.getIntCreatureMap(), Constants.COLUMNS, pos, dest);
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

    void autoAttack(){
        
    }

    void generateAffector(int target){
        if(atkCD > 0){
            return;
        }
        //generate affector
    }

    void attack(int target){
        AbstractCreature ct = MainController.getCreatureById(target);
        if(ct == null){ //target was already dead, drop instruction
            inst = Instruction.newNullInst();
            return;
        }
        int tPos = ct.getPos();
        int dis = Math.max(Math.abs(pos - tPos) / Constants.COLUMNS, Math.abs(pos - tPos) % Constants.COLUMNS);
        if(dis > atkRange){ //out of attack range, first try to move towards it
            moveTo(tPos);
            return;
        }
        generateAffector(target);
    }


    void update(){
        moveCD -= 1;
        atkCD -= 1;
        switch(inst.action){
            case NULL:
                autoAttack();
                break;
            case MOVE:
                moveTo(inst.pos);
                break;
            case ATTACK:
                int target = inst.target;
                break;
            default:
                break;
        }
    }

    int id;
    int pos;
    int hp;
    int atk;
    int atkRange;
    int moveCD;
    int atkCD;
    int camp;
    Instruction inst;
    LinkedList<Integer> movePath;
}
