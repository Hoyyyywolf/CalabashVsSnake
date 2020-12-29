package nju.zjl.cvs.game;

interface BulletSupplier{
    Affector get(int x, int y, int target, int damage);        
}

