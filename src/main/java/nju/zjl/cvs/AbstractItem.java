package nju.zjl.cvs;

abstract class AbstractItem implements Drawable{
    abstract void move();
    abstract void act();
    
    protected int x = 0;
    protected int y = 0;
    protected int hSpeed = 0;
    protected int vSpeed = 0;
}
