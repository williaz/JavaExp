package ocp.creational;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by williaz on 11/24/16.
 */
public final class Immutable { //5. no overridden, no subclass
    private final List<String> food; // 2. fields are private final
    private final String name;
    private final int age;

    public Immutable(String name, int age, List<String> food) { // 1. constructor to set all properties
        this.name = name;
        this.age = age;
        if (food == null) {
            throw new IllegalArgumentException("food is must!");
        }
        this.food = new ArrayList<>(food); // 4. no modified
    }

    //copy constructor
    public Immutable(Immutable other) {
        this.name = other.getName();
        this.age = other.getAge();
        food = new ArrayList<>();
        // copy content
        food.addAll(other.getFood().stream().map(fd -> fd + "_c").collect(Collectors.toList()));
    }

    // 3. no setters
    // 4. mutable ref type field cannot be modified
    public List<String> getFood() {
        return Collections.unmodifiableList(food);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

}
