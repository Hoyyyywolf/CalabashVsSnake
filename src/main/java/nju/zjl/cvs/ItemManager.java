package nju.zjl.cvs;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import static com.google.common.base.Preconditions.*;

public class ItemManager {
    public AbstractCreature getCreatureByPos(int pos){
        return ctPosMap.get(pos);
    }

    public AbstractCreature getCreatureById(int id){
        return ctIdMap.get(id);    
    }

    public int[] getIntCreatureMap(){
        return IntStream.range(0, Constants.ROWS * Constants.COLUMNS).
                    map(i -> ctPosMap.containsKey(i) ? 1 : 0).
                    toArray();
    }

    public void moveCreature(int src, int dest){
        checkArgument(ctPosMap.containsKey(src), "pos %s has no creature but try to move from it", src);
        checkArgument(!ctPosMap.containsKey(dest), "dest %s already has a creature but try to move to it", dest);
        ctPosMap.put(dest, ctPosMap.remove(src));
    }

    private Map<Integer, AbstractCreature> ctPosMap = new HashMap<>();
    private Map<Integer, AbstractCreature> ctIdMap = new HashMap<>();
}
