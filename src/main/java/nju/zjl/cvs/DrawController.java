package nju.zjl.cvs;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class DrawController implements Runnable{
    public DrawController(ItemManager items, Canvas canvas){
        this.items = items;
        this.canvas = canvas;
        try{
            Path root = Paths.get(DrawController.class.getClassLoader().getResource("").toURI());
            root = root.resolve("image");
            for(File f : root.toFile().listFiles()){
                String name = f.getName();
                creatureImageMap.put(name.substring(0, name.lastIndexOf(".")), new Image(f.toURI().toURL().toString(), Constants.GRIDWIDTH - 20, Constants.GRIDHEIGHT - 30, true, true));
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
        colorMap.put("black", Color.BLACK);
    }

    @Override
    public void run(){
        while(!gameOver)try{
            Platform.runLater(() -> {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setStroke(Color.BLACK);
                IntStream.range(0, Constants.ROWS + 1).forEach(i -> gc.strokeLine(0, i * Constants.GRIDHEIGHT, canvas.getWidth(), i * Constants.GRIDHEIGHT));
                IntStream.range(0, Constants.COLUMNS + 1).forEach(i -> gc.strokeLine(i * Constants.GRIDWIDTH, 0, i * Constants.GRIDWIDTH, canvas.getHeight()));
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
