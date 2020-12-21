package nju.zjl.cvs;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class TestGUI extends Application{    
    public void start(Stage primaryStage){
        Creature ct = CreatureFactory.generatePlainCreature(0, 0, Camp.CALABASH);
        Affector at = AffectorFactory.generatePlainBullet(200, 300, 0, 10);
        Canvas canvas = new Canvas(Constants.GRIDWIDTH * Constants.COLUMNS, Constants.GRIDHEIGHT * Constants.ROWS);
        Group root = new Group();
        Scene scene = new Scene(root);
        root.getChildren().add(canvas);
        ct.draw(canvas.getGraphicsContext2D());
        at.draw(canvas.getGraphicsContext2D());

        //scene.setFill(Color.GRAY);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
