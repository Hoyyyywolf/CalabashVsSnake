package nju.zjl.cvs;

import static com.google.common.base.Preconditions.*;

public class FirstCalabash{
    void moveTo(int dest){
        if(moveCD > 0){
            moveCD -= 1;
            return;
        }
        
    }

    boolean needMove(){
        if(inst == null){
            return false;
        }
        else if(inst.action == Instruction.Action.MOVE){
            return true;
        }
        else if(inst.action == Instruction.Action.ATTACK){

        }
    }

    void autoAttack(){

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
                AbstractCreature 
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
    int lastX;
    int lastY;
}
