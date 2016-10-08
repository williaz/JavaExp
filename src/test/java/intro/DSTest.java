package intro;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by williaz on 9/26/16.
 */
public class DSTest {
    @Test
    public void GetValueFromTree(){
        Map<String, Integer> tree= new LinkedHashMap<String, Integer>();
        int[] nums={12,21,26,42};
        tree.put("Bo",12);
        tree.put("Will",21);
        tree.put("William",26);
        tree.put("Austin",42);


        Iterator<String> keys=tree.keySet().iterator();//set!

        int i=0;

        while(keys.hasNext()){
            assertEquals(Integer.valueOf(nums[i]), tree.get(keys.next()));
            i++;
        }

        //assertEquals(Integer.valueOf(26), tree.get("William"));



    }
}
