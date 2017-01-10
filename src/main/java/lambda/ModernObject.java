package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by williaz on 1/10/17.
 */
public class ModernObject {
    List<String> names = new ArrayList<>();

    public Stream<String> getName() {
        return names.stream();
    }

    public void addName(String name) {
        names.add(name);
    }
}
