package nju.zjl.cvs.client;

import javafx.scene.canvas.Canvas;
import nju.zjl.cvs.game.ItemManager;

public class InteractionController {
    public InteractionController(ItemManager items){
        this.items = items;
    }
    
    protected ItemManager items;
    protected Canvas canvas;
}
