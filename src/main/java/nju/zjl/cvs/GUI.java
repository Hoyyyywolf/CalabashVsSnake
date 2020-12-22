package nju.zjl.cvs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class GUI extends Application{    
    public void start(Stage primaryStage) throws Exception{
        Platform.setImplicitExit(false);

        Canvas canvas = new Canvas(Constants.GRIDWIDTH * Constants.COLUMNS, Constants.GRIDHEIGHT * Constants.ROWS);
        Group root = new Group();
        Scene scene = new Scene(root);
        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.show();
        canvas.getGraphicsContext2D().fillOval(7, 8, 15, 40);
        ItemManager items = new ItemManager();

        GameController game = new GameController(items, i -> new Operation[0]);
        DrawController draw = new DrawController(items, canvas);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(draw);
        Future<Camp> ret = exec.submit(game);
        //Camp winner = ret.get();
        //draw.gameOver();
        exec.shutdown();
        //System.out.println(winner);
        //Platform.exit();
    }

    void gameOver(Camp winner){

    }
    
    void newGame(){

    }

    public GUI(){

    }

    private Scene gameScene;
    private Scene recordScene;
    private Scene menuScene;
}
