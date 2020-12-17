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
        if(movePath == null && !computePath(dest)){
            return;
        }
        int next = movePath.peek();
        if(MainController.getCreatureByPos(next) != null){
            if(!computePath(dest)){
                return;
            }
            next = movePath.peek();
        }
        MainController.moveCreature(pos, next);
        pos = next;
        moveCD = Constants.CMS;
        movePath.poll();
        if(movePath.isEmpty()){
            inst = Instruction.newNullInst();
        }
    }

    boolean computePath(int dest){
        int[] path = Algorithms.findPath(MainController.getIntCreatureMap(), Constants.COLUMNS, pos, dest);
        if(path.length == 0){
            movePath = null;
            inst = Instruction.newNullInst();
            return false;
        }
        movePath = new LinkedList<>();
        IntStream.of(path).
            boxed().
            forEach(movePath::add);
        movePath.poll();
        return true;
    }

    void autoAttack(){
        return;
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
    Instruction inst;
    LinkedList<Integer> movePath;
}
