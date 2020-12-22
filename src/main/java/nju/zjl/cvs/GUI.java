package nju.zjl.cvs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class GUI extends Application{    
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(e -> {
            if(drawer != null){
                drawer.gameOver();
            }
        });
        primaryStage.show();
        newGame();
    }

    void gameOver(Camp winner){
        drawer.gameOver();
        //Platform.exit();
    }
    
    void newGame(){
        primaryStage.setScene(gameScene);
        ItemManager items = new ItemManager();
        GameController game = new GameController(items, i -> new Operation[0], this::gameOver);
        drawer = new DrawController(items, gameCanvas);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(game);
        exec.execute(drawer);
        exec.shutdown();
    }

    public GUI(){
        Platform.setImplicitExit(false);

        gameCanvas = new Canvas(Constants.GRIDWIDTH * Constants.COLUMNS, Constants.GRIDHEIGHT * Constants.ROWS);
        Group root = new Group();
        gameScene = new Scene(root);
        root.getChildren().add(gameCanvas);
    }

    protected Stage primaryStage;

    protected Scene gameScene;
    protected Canvas gameCanvas;
    protected DrawController drawer;

    private Scene recordScene;
    private Scene menuScene;
}
