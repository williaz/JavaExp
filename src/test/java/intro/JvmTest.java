package intro;

import jvm.ValueContainer;
import jvm.WeakReferenceStack;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by williaz on 10/9/16.
 */
public class JvmTest {
    @Test
    public void Test_WeakReferenceStack_ForGC_NullPushedValue_ExpectedNullInStack() {
        final WeakReferenceStack<ValueContainer> stack = new WeakReferenceStack<>();
        final ValueContainer expected = new ValueContainer("Value for the stack");
        stack.push(new ValueContainer("Value for the stack"));
        ValueContainer peekedValue = stack.peek();
        assertEquals(expected, peekedValue);
        assertEquals(expected, stack.peek());
        peekedValue = null;
        System.gc();//must
        assertNull(stack.peek());
    }

    @Test
    public void addShudownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println(
                        "Shutting down JVM at time: " + new Date());
            }
        });
    }



}
