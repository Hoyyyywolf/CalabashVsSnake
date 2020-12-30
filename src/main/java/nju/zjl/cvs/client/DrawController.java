package nju.zjl.cvs.client;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import nju.zjl.cvs.game.Affector;
import nju.zjl.cvs.game.Bullet;
import nju.zjl.cvs.game.Constants;
import nju.zjl.cvs.game.Creature;
import nju.zjl.cvs.game.ItemManager;

public class DrawController implements Runnable{
    public DrawController(ItemManager items, Canvas canvas){
        this.items = items;
        this.canvas = canvas;
        String[] images = {"red", "green", "blue", "yellow", "orange", "purple", "cyan", "snake", "scorpion"};
        for(String img : images){
            InputStream in = DrawController.class.getClassLoader().getResourceAsStream("image/" + img +".png");
            creatureImageMap.put(img, new Image(in, 50, 50, true, false));
        }
        colorMap.put("black", Color.BLACK);
    }

    @Override
    public void run(){
        while(!gameOver)try{
            Platform.runLater(() -> {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                Stream.of(items.getCreatures()).forEach(c -> drawCreature(c, gc));
                Stream.of(items.getAffectors()).forEach(a -> drawAffector(a, gc));
            });
            TimeUnit.MILLISECONDS.sleep(1000 / Constants.FPS);
        }catch(InterruptedException exception){
            exception.printStackTrace();
        }
    }

    protected void drawCreature(Creature ct, GraphicsContext gc){
        int ltx = (ct.getPos() % Constants.COLUMNS) * Constants.GRIDWIDTH;
        int lty = (ct.getPos() / Constants.COLUMNS) * Constants.GRIDHEIGHT;
        gc.drawImage(creatureImageMap.get(ct.getImgName()), ltx + 10, lty + 15);
        gc.setFill(Color.RED);
        gc.fillRect(ltx + 5, lty + 5, (Constants.GRIDWIDTH - 10) * ct.getHp() / ct.getMaxHp(), 10);
        gc.strokeRect(ltx + 5, lty + 5, Constants.GRIDWIDTH - 10, 10);
    }

    protected void drawAffector(Affector at, GraphicsContext gc){
        if(at instanceof Bullet){
            gc.setFill(colorMap.get(((Bullet)at).getColor()));
            gc.fillOval(((Bullet)at).getX() - 4, ((Bullet)at).getY() - 4, 8, 8);
        }
    }

    public void terminate(){
        gameOver = true;
    }

    protected boolean gameOver = false;
    protected ItemManager items;
    protected Canvas canvas;
    protected Map<String, Image> creatureImageMap = new HashMap<>();
    protected Map<String, Color> colorMap = new HashMap<>();
}
