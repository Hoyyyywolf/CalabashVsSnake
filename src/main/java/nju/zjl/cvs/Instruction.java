package nju.zjl.cvs;

public class Instruction {
    public final Action action;
    public final int pos;
    public final int target;

    enum Action{
        NULL, MOVE, CAST, ATTACK    
    }
    
    public static Instruction newNullInst(){
        return new Instruction(Action.NULL, -1, -1);
    }

    public static Instruction newMoveInst(int pos){
        return new Instruction(Action.MOVE, pos, -1);
    }

    public static Instruction newAttackInst(int target){
        return new Instruction(Action.ATTACK, -1, target);
    }

    private Instruction(Action action, int pos, int target){
        this.action = action;
        this.pos = pos;
        this.target = target;
    }


}
