package nju.zjl.cvs.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Bullet implements Affector, Drawable {
    public Bullet(int x, int y, int target, int damage, String imgName){
        this.x = x;
        this.y = y;
        this.target = target;
        this.damage = damage;
        this.imgName = imgName;
        this.angle = 0.;
    }
    
    @Override
    public void update(ItemManager items){
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
            items.removeAffector(this);
        }
        computeAngle(dest[0], dest[1]);
    }

    @Override
    public void draw(GraphicsContext gc){
        gc.save();

        Rotate r = new Rotate(angle, x, y);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        Image img = Constants.getImage(imgName);
        gc.drawImage(img, x - img.getWidth(), y - img.getHeight() / 2);

        gc.restore();
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    protected void moveTo(int destX, int destY){
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
    
    protected void computeAngle(int destX, int destY){
        int dx = destX - x;
        double dis = Math.sqrt(dx * dx + (destY - y) * (destY - y));
        double r = Math.acos(dx / dis) / Math.PI * 180;
        angle = y > destY ? 360 - r : r;
    }

    protected int x;
    protected int y;
    protected double angle;

    protected int target;
    protected int damage;

    protected String imgName;
}
