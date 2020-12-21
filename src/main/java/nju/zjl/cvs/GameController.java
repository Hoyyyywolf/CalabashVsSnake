package nju.zjl.cvs;

import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class GameController implements Callable<Camp> {
    public GameController(ItemManager items, Operator operator){
        this.items = items;
        this.operator = operator;
    }

    @Override
    public Camp call() {
        while(!gameOver()){
            times++;
            update();
            try{
                Thread.sleep(1000 / Constants.FPS);
            }catch(InterruptedException exception){
                exception.printStackTrace();
            }
        }
        return items.getCreatures()[0].getCamp();
    }

    void update(){
        while(times > 0){
            if(logicTimer <= 0){
                Operation[] ops = operator.getLogicFrames(logicFrame);
                if(ops == null){
                    return;
                }
                for(Operation op : ops){
                    items.getCreatureById(op.executor).setInst(op.inst);
                }
                logicTimer = Constants.FPS / 10;
            }
            Stream.of(items.getCreatures()).forEach(c -> c.update(items));
            Stream.of(items.getAffectors()).forEach(a -> a.update(items));
            times--;
            logicTimer--;
        }
    }

    boolean gameOver(){
        Creature[] creatures = items.getCreatures();
        Camp camp = creatures[0].getCamp();
        return Stream.of(creatures).allMatch(c -> c.getCamp() == camp); 
    }

    private int logicTimer = Constants.FPS / 10;
    private int logicFrame = 0;
    private int times = 0;
    private ItemManager items;
    private Operator operator;
}
