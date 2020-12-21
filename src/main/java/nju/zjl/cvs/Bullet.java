package nju.zjl.cvs;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends Affector{
    @Override
    void update(ItemManager items){
        Creature ct = items.getCreatureById(target);
        if(ct == null){
            items.removeAffector(this);
            return;
        }
        int[] dest = Constants.creaturePos2BulletPos(ct.getPos());
        moveTo(dest[0], dest[1]);
        if(ct.getPos() == Constants.bulletPos2CreaturePos(x, y)){
            boolean dead = ct.hurt(damage);
            if(dead){
                items.removeCreature(ct.getId());
            }
            else if(buff != null){
                ct.addBuff(buff);
            }
            items.removeAffector(this);
        }
    }

    @Override
    public void draw(GraphicsContext gc){
        gc.setFill(color);
        gc.fillOval(x - 4, y - 4, 8, 8);
    }

    void moveTo(int destX, int destY){
        int deltaX = destX - x;
        int deltaY = destY - y;
        double dis = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if(dis <= Constants.BULLETSPEED){
            x = destX;
            y = destY;
        }
        else{
            double s = Constants.BULLETSPEED / dis;
            x += deltaX * s;
            y += deltaY * s;
        }
    }

    public Bullet(int x, int y, int target, int damage, Color color,Buff buff){
        this.x = x;
        this.y = y;
        this.target = target;
        this.damage = damage;
        this.buff = buff;
        this.color = color;
    }
    
    protected int x;
    protected int y;

    protected int target;
    protected int damage;

    protected Color color;
    protected Buff buff;
}
