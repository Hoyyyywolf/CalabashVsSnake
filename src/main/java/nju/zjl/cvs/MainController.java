package nju.zjl.cvs;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.*;

public class MainController {
    static Map<Integer, AbstractCreature> ctPosMap = new HashMap<>();

    static int[] getIntCreatureMap(){
        return IntStream.range(0, Constants.ROWS * Constants.COLUMNS).
                    map(i -> ctPosMap.containsKey(i) ? 1 : 0).
                    toArray();
    }

    static AbstractCreature getCreatureByPos(int pos){
        return ctPosMap.get(pos);
    }

    static void moveCreature(int src, int dest){
        ctPosMap.put(dest, ctPosMap.remove(src));
    }

    /*
    static AbstractCreature getCreatureById(int id){
        
    }
    */
}
