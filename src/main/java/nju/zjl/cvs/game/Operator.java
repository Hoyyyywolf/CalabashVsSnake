package nju.zjl.cvs.game;

public interface Operator {
    void addOperation(Operation op);
    Operation[] getLogicFrames(int logicFrame);
}
