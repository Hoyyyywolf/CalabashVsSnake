package nju.zjl.cvs;

abstract class AbstractCreature extends AbstractItem implements Interactive{
    @Override
    void move(){
        
    }

    abstract int getPos();
    abstract int getCamp();

    protected int hp = 1;
    protected int atk = 0;
    protected int atkRange = 1;
    protected Instruction inst = null;
    protected int moveCD = 0;
}
