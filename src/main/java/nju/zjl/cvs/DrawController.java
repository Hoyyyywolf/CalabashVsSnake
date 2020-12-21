package nju.zjl.cvs;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DrawController implements Runnable {
    public DrawController(ItemManager items, Canvas canvas){
        this.gameOver = false;
        this.items = items;
        this.canvas = canvas;
    }

    @Override
    public void run(){
        Semaphore sem = new Semaphore(1);
        while(!gameOver)try{
            sem.acquire();
            Platform.runLater(() -> {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                Stream.of(items.getCreatures()).forEach(c -> c.draw(gc));
                Stream.of(items.getAffectors()).forEach(a -> a.draw(gc));
                sem.release();
            });
            TimeUnit.MILLISECONDS.sleep(1000 / Constants.FPS);
        }catch(InterruptedException exception){
            exception.printStackTrace();
        }
    }

    private boolean gameOver;
    private ItemManager items;
    private Canvas canvas;
}
