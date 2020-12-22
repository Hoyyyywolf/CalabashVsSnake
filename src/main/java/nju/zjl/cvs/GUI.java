package nju.zjl.cvs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class GUI extends Application{    
    public void start(Stage primaryStage){
        Platform.setImplicitExit(false);

        Canvas canvas = new Canvas(Constants.GRIDWIDTH * Constants.COLUMNS, Constants.GRIDHEIGHT * Constants.ROWS);
        Group root = new Group();
        Scene scene = new Scene(root);
        root.getChildren().add(canvas);

        //scene.setFill(Color.GRAY);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
