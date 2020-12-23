package nju.zjl.cvs.game;

public class Operation {
    public Operation(int executor, Instruction inst){
        this.executor = executor;
        this.inst = inst;
    }
    
    public final int executor;
    public final Instruction inst;
}
