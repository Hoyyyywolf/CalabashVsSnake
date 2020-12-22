package nju.zjl.cvs;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class DrawController {
    public DrawController(ItemManager items, Canvas canvas){
        this.items = items;
        this.canvas = canvas;

        creatureImageMap.put("red.png", getImage("red.png"));

        colorMap.put("black", Color.BLACK);
    }

    public void update(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Stream.of(items.getCreatures()).forEach(c -> drawCreature(c, gc));
        Stream.of(items.getAffectors()).forEach(a -> drawAffector(a, gc));
    }

    protected void drawCreature(Creature ct, GraphicsContext gc){
        int ltx = (ct.getPos() % Constants.COLUMNS) * Constants.GRIDWIDTH;
        int lty = (ct.getPos() / Constants.COLUMNS) * Constants.GRIDHEIGHT;
        gc.drawImage(creatureImageMap.get(ct.getImgName()), ltx + 10, lty + 15);
        gc.setFill(Color.RED);
        gc.fillRect(ltx + 5, lty + 5, (Constants.GRIDWIDTH - 10) * ct.getHp() / 500, 10);
        gc.strokeRect(ltx + 5, lty + 5, Constants.GRIDWIDTH - 10, 10);
    }

    protected void drawAffector(Affector at, GraphicsContext gc){
        if(at instanceof Bullet){
            gc.setFill(colorMap.get(((Bullet)at).getColor()));
            gc.fillOval(((Bullet)at).getX() - 4, ((Bullet)at).getY() - 4, 8, 8);
        }
    }

    private Image getImage(String imgName){
        URL url = DrawController.class.getClassLoader().getResource(imgName);
        return new Image(url.toString(), Constants.GRIDWIDTH - 20, Constants.GRIDHEIGHT - 30, true, true);
    }

    protected ItemManager items;
    protected Canvas canvas;
    protected Map<String, Image> creatureImageMap = new HashMap<>();
    protected Map<String, Color> colorMap = new HashMap<>();
}
