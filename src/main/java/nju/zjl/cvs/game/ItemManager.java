package nju.zjl.cvs.game;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

public class ItemManager {
    public Creature getCreatureByPos(int pos){
        return ctPosMap.get(pos);
    }

    public Creature getCreatureById(int id){
        return ctIdMap.get(id);    
    }

    public int[] getIntCreatureMap(){
        return IntStream.range(0, Constants.ROWS * Constants.COLUMNS).
                map(i -> ctPosMap.containsKey(i) ? 1 : 0).
                toArray();
    }

    public void addCreature(Creature ct){
        int pos = ct.getPos();
        checkArgument(!ctPosMap.containsKey(pos), "pos %s already has a creature but tried to add another to here", pos);
        ctIdMap.put(ct.getId(), ct);
        ctPosMap.put(pos, ct);
    }

    public void moveCreature(int src, int dest){
        checkArgument(ctPosMap.containsKey(src), "pos %s has no creature but tried to move from it", src);
        checkArgument(!ctPosMap.containsKey(dest), "dest %s already has a creature but tried to move to it", dest);
        ctPosMap.put(dest, ctPosMap.remove(src));
    }

    public void removeCreature(int id){
        checkArgument(ctIdMap.containsKey(id), "creature %s is not existed but tried to remove it", id);
        ctPosMap.remove(ctIdMap.get(id).getPos());
        ctIdMap.remove(id);
    }

    public void addAffector(Affector at){
        atQueue.add(at);
    }

    public void removeAffector(Affector at){
        atQueue.remove(at);
    }

    public Creature[] getCreatures(){
        return ctPosMap.values().toArray(new Creature[0]);
    }

    public Affector[] getAffectors(){
        return atQueue.toArray(new Affector[0]);
    }

    public void initDefaultCreatures(){
        addCreature(CreatureFactory.generateCalabash(3, 4, "red"));
        addCreature(CreatureFactory.generateCalabash(2, 2, "orange"));
        addCreature(CreatureFactory.generateCalabash(2, 3, "yellow"));
        addCreature(CreatureFactory.generateCalabash(2, 5, "green"));
        addCreature(CreatureFactory.generateCalabash(2, 6, "blue"));
        addCreature(CreatureFactory.generateCalabash(1, 2, "cyan"));
        addCreature(CreatureFactory.generateCalabash(1, 3, "purple"));
        addCreature(CreatureFactory.generateGranpa(1, 4));
        addCreature(CreatureFactory.generateScorpion(3, 6));
        addCreature(CreatureFactory.generateSnake(3, 8));
    }

    protected Map<Integer, Creature> ctPosMap = new ConcurrentHashMap<>();
    protected Map<Integer, Creature> ctIdMap = new ConcurrentHashMap<>();

    protected Queue<Affector> atQueue = new ConcurrentLinkedQueue<>();
}
