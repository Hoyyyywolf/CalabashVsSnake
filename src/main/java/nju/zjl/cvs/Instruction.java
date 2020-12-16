package nju.zjl.cvs;

public class Instruction {
    public final Action action;
    public final int pos;
    public final int target;

    enum Action{
        NULL, MOVE, CAST, ATTACK    
    }
    
    public Instruction(Action action, int pos, int target){   
        this.action = action;
        this.pos = pos;
        this.target = target;
    }

    public Instruction(Action action, int tp){   
        this.action = action;
        this.target = tp;
        this.pos = tp;
    }
}
