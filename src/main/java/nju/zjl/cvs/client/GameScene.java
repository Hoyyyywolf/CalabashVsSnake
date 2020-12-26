package nju.zjl.cvs.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import nju.zjl.cvs.game.Constants;
import nju.zjl.cvs.game.Constants.Camp;
import nju.zjl.cvs.game.Creature;
import nju.zjl.cvs.game.GameController;
import nju.zjl.cvs.game.Instruction;
import nju.zjl.cvs.game.ItemManager;
import nju.zjl.cvs.game.Operation;

public class GameScene {
    public GameScene(Runnable backToMenu){
        this.backToMenu = backToMenu;
        HBox root = new HBox();
        StackPane stackPane = new StackPane();
        gameCanvas = new Canvas(Constants.COLUMNS * Constants.GRIDWIDTH, Constants.ROWS * Constants.GRIDHEIGHT);
        uiCanvas = new Canvas(Constants.COLUMNS * Constants.GRIDWIDTH, Constants.ROWS * Constants.GRIDHEIGHT);
        stackPane.getChildren().addAll(gameCanvas, uiCanvas);
        grid = new GridPane();
        root.getChildren().addAll(stackPane, grid);
        scene = new Scene(root);
        
        initEventHandler();
    }
    
    public void newGame(Stage currentStage){
        select = -1;
        items = new ItemManager();
        items.initDefaultCreatures();
        operator = new GameOperator();
        game = new GameController(items, operator, this::gameOver);
        drawer = new DrawController(items, gameCanvas);
        currentStage.setOnCloseRequest(e -> {
            game.terminate();
            drawer.terminate();
            operator.terminate();
            Platform.exit();
        });
        currentStage.setScene(scene);
        camp = operator.connect("127.0.0.1", 2345);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(operator);
        try{
            TimeUnit.MILLISECONDS.sleep(100);
        }catch(Exception e){
            e.printStackTrace();
        }
        exec.execute(game);
        exec.execute(drawer);
        exec.shutdown();
    }

    protected void initEventHandler(){
        uiCanvas.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                leftMouseClickEvent(Constants.bulletPos2CreaturePos((int)e.getX(), (int)e.getY()));
                e.consume();
            }
            else if(e.getButton() == MouseButton.SECONDARY){
                rightMouseClickEvent(Constants.bulletPos2CreaturePos((int)e.getX(), (int)e.getY()));
                e.consume();
            }
        });
    }

    protected void leftMouseClickEvent(int pos){
        Creature c = items.getCreatureByPos(pos);
        if(c == null){
            select = -1;
            return;
        }
        select = c.getId();
    }

    protected void rightMouseClickEvent(int pos){
        if(select == -1){
            return;
        }
        Creature s = items.getCreatureById(select);
        if(s.getCamp() != camp){
            return;
        }
        Creature t = items.getCreatureByPos(pos);
        if(t == null){
            operator.addOperation(new Operation(select, Instruction.newMoveInst(pos)));
        }
        else if(t.getCamp() != camp){
            operator.addOperation(new Operation(select, Instruction.newAttackInst(t.getId())));
        }
    }

    protected void gameOver(Camp winner){
        drawer.terminate();
        operator.terminate();
        Platform.exit();
    }

    protected Runnable backToMenu;
    protected Scene scene;
    protected GridPane grid;
    protected Canvas gameCanvas;
    protected Canvas uiCanvas;

    protected int select;
    protected Camp camp;
    protected ItemManager items;
    protected GameOperator operator;
    protected GameController game;
    protected DrawController drawer;
}
