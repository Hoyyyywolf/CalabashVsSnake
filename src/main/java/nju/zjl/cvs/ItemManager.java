package nju.zjl.cvs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;
import static com.google.common.base.Preconditions.*;

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

    public void moveCreature(int src, int dest){
        checkArgument(ctPosMap.containsKey(src), "pos %s has no creature but tried to move from it", src);
        checkArgument(!ctPosMap.containsKey(dest), "dest %s already has a creature but tried to move to it", dest);
        ctPosMap.put(dest, ctPosMap.remove(src));
    }

    public void removeCreature(int id){
        checkArgument(ctIdMap.containsKey(id), "creature %s is not existed but tried to remove it", id);
        ctPosMap.remove(ctIdMap.get(id).getPos());
        ctIdMap.remove(id);
        /*
        TODO 
        remove the creature from drwable items, etc
        */
    }

    public void addAffector(Affector at){
        atSet.add(at);
    }

    public void removeAffector(Affector at){
        atSet.remove(at);
        /*
        TODO 
        remove the affector from drwable items if it's drawable, etc
        */
    }

    private Map<Integer, Creature> ctPosMap = new HashMap<>();
    private Map<Integer, Creature> ctIdMap = new HashMap<>();

    private Set<Affector> atSet = new TreeSet<>();
}