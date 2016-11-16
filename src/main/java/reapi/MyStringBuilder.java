package reapi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by williaz on 11/15/16.
 * I won't override equals() as the Java API authors did.
 */
public final class MyStringBuilder implements CharSequence{
    private final List<Character> value;
    public MyStringBuilder() {
        value = new ArrayList<>();
    }

    public MyStringBuilder(int initCapacity) {
        value = new ArrayList<>(initCapacity); // linkedlist better, but I want to practise ArrayList
    }

    public MyStringBuilder(String str) {
        char[] chars = str.toCharArray();
        value = new ArrayList<>(chars.length * 3/2 +1);
        for (Character c: chars) {
            value.add(c);
        }
    }

    public MyStringBuilder(CharSequence sequence) {
        this(sequence.toString());
    }

    public MyStringBuilder(List<Character> value) {
        this.value = value;
    }

    @Override
    public int length() {
        return value.size();
    }

    @Override
    public char charAt(int index) {
        return value.get(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return substring(start, end);
    }

    @Override
    public String toString() {
        char[] arr = new char[value.size()];
        int i = 0;
        for (char c : value) {
            arr[i] = c;
            i++;
        }
        return new String(arr);
    }

    public String substring(int start, int end) {
        return new MyStringBuilder(value.subList(start, end)).toString();
    }

    public MyStringBuilder append(MyStringBuilder msb) {
        this.value.addAll(msb.value);
        return this;
    }

    public MyStringBuilder append(String str) {
        MyStringBuilder msb = new MyStringBuilder(str);
        return this.append(msb);
    }

    public MyStringBuilder reverse() {
        int i = 0, j = value.size() - 1;
        while (i < j) {
            Character temp = value.get(i);
            value.set(i, value.get(j));
            value.set(j, temp);
            i++;
            j--;
        }
        return this;
    }


}
