package unit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by williaz on 10/8/16.
 * should not be
 */
public class Convention {
    @Test
    public void assertionWithMessage() {
        final List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        assertTrue("The list should not be empty", !numbers.isEmpty());
    }
}
