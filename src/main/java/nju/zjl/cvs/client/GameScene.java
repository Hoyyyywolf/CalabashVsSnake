package nju.zjl.cvs.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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

        StackPane stackPane = new StackPane();
        bgCanvas = new Canvas(Constants.COLUMNS * Constants.GRIDWIDTH, Constants.ROWS * Constants.GRIDHEIGHT);
        gameCanvas = new Canvas(Constants.COLUMNS * Constants.GRIDWIDTH, Constants.ROWS * Constants.GRIDHEIGHT);
        uiCanvas = new Canvas(Constants.COLUMNS * Constants.GRIDWIDTH, Constants.ROWS * Constants.GRIDHEIGHT);
        stackPane.getChildren().addAll(bgCanvas, gameCanvas, uiCanvas);

        info = new TextArea();
        info.setEditable(false);
        info.setPrefWidth(150);
        info.setWrapText(true);

        HBox root = new HBox();
        root.getChildren().addAll(stackPane, info);
        root.setSpacing(20);
        scene = new Scene(root);

        initBackground();
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

        info.appendText(String.format("Connecting to server.%n"));
        new Thread(() -> {
            operator.connect("127.0.0.1", 23456, this::connectSuccess, this::connectOver);
        }).start();
    }

    protected void connectSuccess(Boolean success){
        Platform.runLater(() -> {
            if(Boolean.TRUE.equals(success)){
                info.appendText(String.format("Connection established, matching player.%n"));
            }
            else{
                info.appendText(String.format("Cannot establish connection.%n"));
            }
        });
    }

    protected void connectOver(Camp c){
        Platform.runLater(() -> {
            info.appendText(String.format("Match successfully, your camp is %s.%n", c.name()));
            camp = c;
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(operator);
            exec.execute(game);
            exec.execute(drawer);
            exec.shutdown();
        });
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

    protected void initBackground(){
        GraphicsContext gc = bgCanvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        for(int i = 0; i < Constants.ROWS; i++){
            for(int j = 0; j < Constants.COLUMNS; j++){
                if(i % 2 != j % 2){
                    gc.fillRect(j * Constants.GRIDWIDTH, i * Constants.GRIDHEIGHT, Constants.GRIDWIDTH, Constants.GRIDHEIGHT);
                }
            }
        }
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
        if(s == null){
            select = -1;
            return;
        }
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
    protected Canvas bgCanvas;
    protected Canvas gameCanvas;
    protected Canvas uiCanvas;
    protected TextArea info;

    protected int select;
    protected Camp camp;
    protected ItemManager items;
    protected GameOperator operator;
    protected GameController game;
    protected DrawController drawer;
}
