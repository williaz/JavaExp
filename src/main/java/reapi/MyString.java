package reapi;

import java.util.Arrays;

/**
 * Created by williaz on 11/15/16.
 * implement 13 method in oca
 * length(), charAt(), indexOf(), substring(),
 * toUpperCase(), toLowerCase(), equals(), equalsIgnoreCase(),
 * startWith(), endWith(), contains(), replace(),
 * trim()
 */
public final class MyString implements CharSequence {
    private final char[] value;

    public MyString() {
        value = new char[0];
    }

    public MyString(char[] arr) {
        value = Arrays.copyOf(arr, arr.length);
    }

    public MyString(String str) {
        value = str.toCharArray();
    }

    public MyString(StringBuilder sb) {
        this(sb.toString());
    }

    @Override
    public String toString() {
        return new String(value);
    }

    @Override
    public int length() {
        return value.length;
    }

    @Override
    public char charAt(int index) {
        if ((index < 0) || (index >= value.length)) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return value[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.substring(start, end);
    }

    public String substring(int start, int end) {
        if (start < 0 || start >= value.length) {
            throw new StringIndexOutOfBoundsException(start);
        }
        int count = end - start;
        if (count < 0) throw new StringIndexOutOfBoundsException(count);
        return (((start == 0) && (end == value.length)) ? (this.toString())
                : (new String(value, start, count)));
    }
    public String substring(int start) {
        return this.substring(start, value.length);
    }

    // TODO: need optimize
    public int indexOf(String target) {
        if (target == null)
            return -1;
        if (target.length() > this.length()) return -1;
        if (target.length() == 0) return 0;
        int j = 0;
        int index = -1;
        for (int i = 0; i < this.length(); i++) {
            if (index == -1) {
                if (target.length() > this.length() - i) {
                    break;
                }
                if (this.charAt(i) == target.charAt(0)) {
                    index = i;
                    j = 1;
                }
            } else {
                if (this.charAt(i) != target.charAt(j)) {
                    i = index;// 1112 112
                    index = -1;
                    // j = 0

                } else if (j == target.length() - 1) {
                    break;
                }
                j++;

            }


        }
        return index;
    }

    public boolean contains(CharSequence s) {
        return this.indexOf(s.toString()) > -1;
    }

    public String concat(String str) {
        if (str == null || str.isEmpty()) return this.toString();
        char[] app = str.toCharArray();
        int len = value.length + str.length();
        char[] big = Arrays.copyOf(value, len);
        for (int i = value.length, j = 0; i < len ; i++, j++) {
            big[i] = app[j];
        }
        return new String(big);
    }

    public String replace(CharSequence target, CharSequence replacement) {
        if (!this.contains(target)) return this.toString();

        int index = this.indexOf(target.toString());
        String left = this.substring(0, index);
        String right = this.substring(index + target.length());

        return left.concat(replacement.toString()).concat(right);
    }

}
