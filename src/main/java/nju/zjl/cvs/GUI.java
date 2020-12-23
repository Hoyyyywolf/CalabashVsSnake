package nju.zjl.cvs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI extends Application{
    protected Stage primaryStage;    
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(e -> {
            if(drawer != null){
                drawer.terminate();
            }
            if(gameC != null){
                gameC.terminate();
            }
            Platform.exit();
        });
        primaryStage.show();
        newGame();
    }


    protected Scene gameScene;
    protected Canvas gameCanvas;
    protected Canvas uiCanvas;
    protected GridPane grid;

    private Scene recordScene;
    private Scene menuScene;

    public GUI(){
        Platform.setImplicitExit(false);

        gameCanvas = new Canvas(Constants.GRIDWIDTH * Constants.COLUMNS, Constants.GRIDHEIGHT * Constants.ROWS);
        uiCanvas = new Canvas(Constants.GRIDWIDTH * Constants.COLUMNS, Constants.GRIDHEIGHT * Constants.ROWS);
        StackPane s = new StackPane();
        s.getChildren().addAll(gameCanvas, uiCanvas);
        grid = new GridPane();
        HBox root = new HBox();
        root.getChildren().addAll(s, grid);
        gameScene = new Scene(root);
    }

    GameController gameC;
    DrawController drawer;

    void newGame(){
        primaryStage.setScene(gameScene);
        ItemManager items = new ItemManager();
        gameC = new GameController(items, i -> new Operation[0], this::gameOver);
        drawer = new DrawController(items, gameCanvas);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(gameC);
        exec.execute(drawer);
        exec.shutdown();
    }

    void gameOver(Camp winner){
        drawer.terminate();
        //Platform.exit();
    }
}
