package nju.zjl.cvs.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class GUI extends Application{
    public void start(Stage primaryStage) throws Exception{
        primaryStage.show();
        Platform.setImplicitExit(false);
        gs.newGame(primaryStage);
    }


    void f(){

    }

    private GameScene gs = new GameScene(this::f);

}
