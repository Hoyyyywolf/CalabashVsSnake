package nju.zjl.cvs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.*;

public class AlgorithmsTest {
    @Test
    public void testFindPath(){
        boolean[] map = {
            false, false, false, false, false, false,
            false, false, false, false, false, false,
            true , true , true , false, false, false,
            false, false, true , false, false, false,
            false, false, true , false, false, false,
            false, false, false, false, false, false
        };
        int[] ret = Algorithms.findPath(map, 6, 19, 1);
        assertEquals("start point wrong", 19, ret[0]);
        assertEquals("end point wrong", 1, ret[ret.length - 1]);
        for(int i = 1; i < ret.length - 1; i++){
            int x1 = ret[i] / 6;
            int y1 = ret[i] % 6;
            int x2 = ret[i + 1] / 6;
            int y2 = ret[i + 1] % 6;
            assertTrue("path is not adjacent", (x1 == x2 && Math.abs(y1 - y2) == 1) || (y1 == y2 && Math.abs(x1 - x2) == 1));
        }
        System.out.println(Arrays.toString(ret));
    }
}
