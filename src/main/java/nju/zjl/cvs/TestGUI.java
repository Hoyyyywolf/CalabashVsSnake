package nju.zjl.cvs;

import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestGUI extends Application{    
    public void start(Stage primaryStage){
        try{
            URL dir = TestGUI.class.getClassLoader().getResource("image/red.png");
            System.out.println(dir);
            Image img = new Image(dir.toString(), 100, 100, true, false);
            primaryStage.setTitle("Title");
        Group root = new Group();
        Scene scene = new Scene(root, 600, 330, Color.WHITE);

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        
        final ImageView imv = new ImageView();
        imv.setImage(img);

        final HBox pictureRegion = new HBox();
        
        pictureRegion.getChildren().add(imv);
        gridpane.add(pictureRegion, 1, 1);
        
        
        root.getChildren().add(gridpane);        
        primaryStage.setScene(scene);
        primaryStage.show();
        }catch(Exception exception){
            throw new RuntimeException(exception);
        }
    }
}
