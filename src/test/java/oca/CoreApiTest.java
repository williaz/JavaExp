package oca;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by williaz on 10/23/16 - 10/27 : 5d
 *
 * tricky -> careful, compile, method usage right!
 *
 * # check "The code does not compile." first!!
 *
 * # Placing one String before the other String and combining them together
 *   is called string concatenation
 *
 * # Immutable objects can be garbage collected just like mutable objects.!!!
 *
 * # answer "The result is undefined." for Arrays.binarySearch() on unsorted array
 *
 * # Arrays.asList(arr) !!
 *
 * # LocalData vs LocalTime, be careful the usage of respective methods!!!
 */
public class CoreApiTest {
    /**
     * 1. If both operands are numeric, + means numeric addition.
     * 2. If either operand is a String, + means concatenation.
     * 3. The expression is evaluated left to right.
     */
    @Test
    public void Test_StringConcatenation(){
        assertEquals("3c", (1 + 2 + "c"));

        String str1 = "1";
        str1 += 2;

        assertEquals(str1, "12");
    }

    /**
     *
     * immutable classes in Java are final, and subclasses can’t add mutable behavior.
     */
    @Test
    public void Test_StringImmutable(){
        String str1 = "w";
        String str2 = str1.concat("i");
        str2.concat("ll");

        assertEquals("wi", str2);

    }

    /**
     * The string pool contains literal values that appear in your program.
     * toString() is a string but not a literal, so it does not go into the string pool.
     * Strings not in the string pool are garbage collected just like any other object.
     */
    @Test
    public void Test_StringLiteral(){
        String str1 = "will";
        String str2 = new String("will");
        StringBuilder sb = new StringBuilder("will");

        assertEquals(str1, str2);
        assertFalse(str1 == str2);
        assertFalse(str1 == sb.toString());
    }

    /**
     * For all these methods, you need to remember that a string is a sequence of characters
     * and Java counts from 0 when indexed.
     * length(), charAt(), indexOf(), substring(),
     * toUpperCase(), toLowerCase(), equals(), equalsIgnoreCase(),
     * startWith(), endWith(), contains(), replace(),
     * trim()
     */
    @Test
    public void Test_String13Methods(){
        String str = "animal";
        assertEquals(6,str.length());
        assertEquals('a', str.charAt(4));
        assertEquals(4, str.indexOf('a', 4)); // include the from index
        assertEquals(-1, str.indexOf('n', 4));  // no found -1
        assertEquals("im", str.substring(2,4)); // endIndex - exclude
        assertEquals("animal", str.substring(0, 6));
        assertEquals("ANIMAL", str.toUpperCase());

        String name = "Will";
        assertEquals("will", name.toLowerCase());
        assertTrue("Will".equals("Will"));
        assertTrue("Will".equalsIgnoreCase("will"));

        assertFalse("abcd".startsWith("Ab"));
        assertTrue("abcd".endsWith("d"));

        assertTrue("Austin".contains("in"));
        assertFalse("Edison".contains("op"));

        assertEquals("Will".replace('W', 'B'), "Bill");  // CharSequence!!!
        assertEquals("Harrison".replace("Harr", "Ed"), "Edison");

        /*
        whitespace consists of spaces along with the \t (tab) and \n (newline)
        and \r (carriage return) are included in what gets trimmed.
         */
        assertEquals("\t \ra b cde \n".trim(), "a b cde");

        // Method Chaining
        assertEquals("AnimAl", "aniMaL is food".substring(0, 6).toLowerCase().replace('a', 'A'));
        //immutable, all create a nw String
        String str1 = "aniMaL is food";
        String str2 = str1.substring(0, 6);
        String str3 = str2.toLowerCase();
        String str4 = str3.replace('a', 'A');
        assertFalse(str1.equals(str2));
        assertFalse(str2.equals(str3));
        assertFalse(str3.equals(str4));

        String sss = "1234";
        assertEquals('2', sss.charAt(1));

    }

    /**
     * When use string directly in concatenation, many objects are instantiated,
     * but most of which are immediately eligible for garbage collection.
     */
    @Test
    public void Test_StringBuilderMutable(){
        StringBuilder sb1 = new StringBuilder("abc");
        StringBuilder sb2 = sb1.append("de"); //returns a reference to the current StringBuilder.
        sb2.append("f").append("ghi");

        assertEquals("abcdefghi", sb1.toString());
        assertEquals("abcdefghi", sb2.toString());
        assertTrue(sb1 == sb2); // point to the same object !!!

        //VS

        String str1 = "abc";
        String str2 = str1.concat("de");
        str2.concat("f").concat("ghi");

        assertEquals("abc", str1);
        assertEquals("abcde", str2);
        assertFalse(str1 == str2);

        // substring
        String string = "will best of best";
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.substring(4, 9);  // return new String  !!!
        assertNotEquals("best", stringBuilder);

        //
        assertEquals("will is trying", stringBuilder.substring(0, 4).concat(" is trying") );

    }

    /**
     * Size is the number of characters currently in the sequence,
     * and capacity is the number of characters the sequence can currently hold.
     *
     * Often StringBuilder is used internally for performance purposes
     * but the end result needs to be a String.
     *
     * Be careful! StringBuilder b = "rumble"; XXXXX
     * append(), capacity(), length(), indexOf(),
     * toString(), reverse()
     */
    @Test
    public void Test_StringBuilderMethods(){
        StringBuilder sb = new StringBuilder();
        assertEquals(16, sb.capacity());

        sb = new StringBuilder(42);
        assertEquals(42, sb.capacity());

        sb = new StringBuilder("will");
        assertEquals(20, sb.capacity());// "will".length()+16

        assertEquals(4, sb.length());
        assertEquals(2, sb.indexOf("l")); // first one
        assertEquals('i', sb.charAt(1));
        assertEquals("il", sb.substring(1,3));
        assertFalse("il" == sb.substring(1,3));

        char[] cc= {'a', 'b'};
        StringBuilder sb1 = new StringBuilder()
                .append(true).append(' ').append(cc)
                .append(" is good ").append(23).append(1.42)
                .append(9000L).append(sb);
        assertEquals("true ab is good 231.429000will", sb1.toString());

        sb.insert(2,242);
        assertEquals("wi242ll", sb.toString());

        sb.deleteCharAt(2); // to delete only one
        assertEquals("wi42ll", sb.toString());

        sb.delete(2, 4);
        assertEquals("will", sb.toString());

        sb.reverse();
        assertEquals("lliw", sb.toString());


    }

    /**
     * Remember that Strings are immutable and literals are pooled.
     * The JVM created only one literal in memory.
     *
     * the authors of StringBuilder did not implement equals().
     */
    @Test
    public void Test_Equality(){

        String str1 = "will is best";
        String str2 = "will is best";

        assertTrue(str1 == str2);

        String str3 = str2.replace('w', 'W');

        assertTrue(str1 == str2);
        assertNotEquals("Will is best", str2);

        /*
        The lesson is to never use == to compare String objects.
        The only time you should have to deal with == for Strings is on the exam.
         */

        String str4 = new String("will is best");
        assertFalse(str1 == str4);

        StringBuilder sb1 = new StringBuilder("will");
        StringBuilder sb2 = new StringBuilder("will");

        assertFalse(sb1.equals(sb2));
        assertNotEquals(sb1, sb2);

    }

    /**
     * you can type the [] before or after the name, and adding a space is optional
     *
     * Although it is legal to leave out the size for later dimensions of a multidimensional array,
     * the first one is required. !
     */
    @Test
    public void Test_Array(){
        int num[] = new int[5];
        int num3 [] = new int[9];
        int[] num1 = new int[]{3, 5, 1, 3};
        int [] num2 = {12, 34, 15, 41};
        assertEquals("[3, 5, 1, 3]", Arrays.toString(num1));

        String[] strings = {"will best", "Who?", "Where?","123", "655"};

        /*
        alphabetic order: Numbers sort before letters
        and uppercase sorts before lowercase,
         */
        Arrays.sort(num2);
        assertArrayEquals(new int[]{12, 15, 34, 41}, num2);
        Arrays.sort(strings);
        assertArrayEquals(new String[]{"123", "655", "Where?", "Who?", "will best"}, strings);

        /*
        No Found -- Negative value showing one smaller than the negative of index,
        where a match needs to be inserted to preserve sorted order

        As soon as you see the array isn’t sorted,
        look for an answer choice about unpredictable output.
         */
        assertEquals(2, Arrays.binarySearch(num2, 34));
        assertEquals(-4, Arrays.binarySearch(num2, 35));

        Object[] objects = strings;
        objects[0] = new String(); // type safe
       // objects[2] = new StringBuilder(); //ArrayStoreException
       // objects[0] = new Object(); // mistype, ArrayStoreException
        assertEquals(5, num.length);  // final length
        assertEquals(num[3], 0);




        //Varargs
        assertEquals(12, getSum(num1));

        int a =3;
        int b =5;

        assertEquals(getSum(a, b), 8);

        int[][] arr = new int[3][];
        arr[0] = new int[4];
        arr[1] = new int[2];
        arr[2] = new int[6];


    }

    public int getSum(int... args){
        int sum =0;

        for(int i: args){
            sum += i;
        }

        return sum;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void Test_ArrayOutOfBound(){
        int num[] = new int[5];
        //num[5] = 4; //1
        for (int i =0; i<= num.length; i++){
            num[i]++;
        } //2


    }

    @Test
    public void Test_ArrayList(){
        int[] a ={1, 3, 4};
        int[] b ={1, 3, 4};
        assertNotEquals(a, b); // array no override equals()

        List<Integer> aI = Arrays.asList(1, 4, 5);
        List<Integer> bI = Arrays.asList(1, 4, 5);
        List<Integer> cI = Arrays.asList(1, 5, 4);
        assertEquals(aI, bI);  // List override equals()
        assertEquals(aI, cI);  // List override equals() keep ordering


        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(5);
        list.add(0, 3);// insert, not replace
        list.add(12);
        assertEquals(list.toString(), "[3, 2, 5, 12]");

        ArrayList<Integer> list1 = new ArrayList<>(list);
        assertEquals(list, list1);
        assertFalse(list == list1);

        assertEquals(new Integer(5), list1.remove(2));  // index
        //assertEquals(5, list1.remove(2));  // index, no auto unboxing,
        assertTrue(list1.remove(new Integer(2)));  // E object

        int gotReplaced = Integer.valueOf( list.set(0, new Integer(12)) );
        assertEquals(3, gotReplaced);

        assertFalse(list.isEmpty());
        assertEquals(list.size(), 4);
        assertEquals(list1.size(), 2);

        list1.clear();
        assertTrue(list1.isEmpty());

        assertTrue(list.contains(new Integer(5)));

        assertFalse(list.equals(list1));

    }

    /**
     * String --->
     * The parse methods, such as parseInt(), return a primitive,  !!!
     * and the valueOf() method returns a wrapper class  !!
     *
     * the Character class doesn’t participate in the parse/ valueOf methods.
     */

    @Test
    public void Test_WrapperClass(){
        int primitives = Integer.parseInt("234");
        Integer wrapper = Integer.valueOf("234");

        assertEquals(234, primitives);

        assertEquals(new Integer(primitives), wrapper);

        Boolean bWrapper = Boolean.valueOf("TRUe"); //equalsIgnoreCase()

        assertEquals(bWrapper, true);

        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(4);
        nums.add(7);
        nums.remove(1); // it is the index, no the object stored
        assertEquals(nums.get(0), new Integer(1));


    }

    /**
     * If you like, you can suggest a larger array to be used instead.
     */

    @Test(expected = IndexOutOfBoundsException.class)
    public void Test_toArray_NoLinked(){
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(4);
        nums.add(7);
        nums.add(2);

        Integer[] ints = nums.toArray(new Integer[10]);
        ints[4] = 13;

        assertEquals( nums.get(4), new Integer(13));


    }
    @Test
    public void Test_toArray_NoLinked1() {
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(4);
        nums.add(7);
        nums.add(2);
        Integer[] ints = nums.toArray(new Integer[10]);
        nums.set(2, 44);
        assertFalse( nums.get(2).equals(ints[2]) );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void Test_asList_LinkedButFixed(){
        String[] strs ={"Will", "is", "Best", "of", "All", "!"};
        List<String> list = Arrays.asList(strs);
        list.add("True");
    }

    @Test
    public void Test_asList_Linked(){
        String[] strs ={"Will", "is", "Best", "of", "All", "!"};
        List<String> list = Arrays.asList(strs); //It is a fixed-size, backed version of a List.
        String toReplace ="the Best";
        list.set(2, toReplace);
        assertEquals(strs[2], toReplace);

        strs[4] = "the world";
        assertEquals(list.get(4), strs[4]);

        //asList() takes varargs, which let you pass in an array or just type out the String values.
        List<Double> doubles = Arrays.asList(23.12, 3.4, 44.4, 15.1); //handy when testing

    }

    @Test
    public void Test_CollectionsSort(){
        List<Double> doubles = Arrays.asList(23.12, 3.4, 44.4, 15.1); //handy when testing
        Collections.sort(doubles);
        Double[] doubles1 ={3.4, 15.1, 23.12, 44.4};
        assertArrayEquals(doubles.toArray(new Double[0]), doubles1);

    }

    static public class reverseOrder implements Comparator<Integer>{

        @Override
        public int compare(Integer o1, Integer o2) {
            return (o2 - o1);
        }
    }


    @Test
    public void Test_ReverseOrdering(){
        final List<Integer> numbers = Arrays.asList(4, 7, 1, 6, 3, 5, 4);
        final List<Integer> expected = Arrays.asList(7, 6, 5, 4, 4, 3, 1);

        Collections.sort(numbers, new reverseOrder());
        assertEquals(numbers, expected);
    }


    /**
     * LocalDate Contains just a date—no time and no time zone.
     * LocalTime Contains just a time—no date and no time zone.
     * LocalDateTime Contains both a date and time but no time zone.
     * If you do need to communicate across time zones, ZonedDateTime handles them.
     *
     * In the United States, the month is written before the date.
     * Java tends to use a 24-hour clock even though the United States uses a 12-hour clock with a.m./p.m.
     *
     * For months in the new date and time methods, Java counts starting from 1 like we human beings do.
     *
     * # The date and time classes have private constructors to force you to use the static methods.
     *
     * #The date and time classes are immutable, just like String was.
     */
    @Test
    public void Test_TimeApi(){
        LocalDate date1 = LocalDate.of(2016, Month.OCTOBER, 26);
        LocalDate date2 = LocalDate.of(2016, 10, 26);

        assertEquals(date1, date2);


        LocalTime time1 = LocalTime.of(14, 59); // hour, min
        LocalTime time2 = LocalTime.of(14, 59, 23); // hour, min, sec
        LocalTime time3 = LocalTime.of(14, 59, 23, 1234); // hour, min, nano

        assertTrue(time1.isBefore(time2));
        assertTrue(time3.isAfter(time2));

        LocalDateTime dateTime1 = LocalDateTime.of(2016, 10, 26, 14, 59);
        LocalDateTime dateTime2 = LocalDateTime.of(date1, time1);
        assertEquals(dateTime1, dateTime2);

        LocalDateTime rightNow = LocalDateTime.now();
        LocalDate today = LocalDate.now();

        assertTrue(today.getMonth().equals(rightNow.getMonth()));

        //immutable
        LocalTime time4 = time1.plusHours(2);
        assertTrue(time1.isBefore(time2));
        assertTrue(time4.isAfter(time2));

        date2 = date2.minusMonths(5);
        assertTrue(date2.isBefore(date1));

        //Java is smart enough to hide the seconds and nanoseconds when we aren’t using them
        System.out.println(dateTime1);
        dateTime1 = dateTime1.minusDays(5).minusHours(2).minusNanos(34);
        System.out.println(dateTime1);

        /*
        LocalDate and LocalDateTime have a method to convert them into long equivalents in relation to 1970.
        What’s special about 1970? That’s what UNIX started using for date standards, so Java reused it.

        Greenwich is in England and GMT does not participate in daylight savings time.
        This makes it a good reference point.

         */

        Period annuly = Period.ofYears(1);
        Period quartly = Period.ofMonths(3);
        Period everyOther23Day = Period.ofDays(23);
        Period weekly = Period.ofWeeks(1);
        Period someDays = Period.of(1, 6, 23); //year, month, day

        //You cannot chain methods when creating a Period.
        Period wrong = annuly.ofMonths(6).ofDays(23);  //!!!!
        //Only the last method is used because the Period.ofXXX methods are static methods.
        assertNotEquals(wrong, someDays);
        assertEquals(wrong, everyOther23Day);

        //For Duration, you can specify the number of days, hours, minutes, seconds, or nanoseconds.
        LocalDate date3 = LocalDate.of(2016, 10, 1);
        LocalDate date4 = LocalDate.of(2016, 10, 24);
        date3 = date3.plus(everyOther23Day); //TemporalType
        assertEquals(date3, date4);

        LocalDateTime dateTime3 = LocalDateTime.of(2016, 10, 1, 15, 12);
        assertEquals(dateTime3.getMonth(), Month.OCTOBER);  //only month return enum
        assertEquals(dateTime3.getDayOfMonth(), 1);  //int
        assertEquals(dateTime3.getMonthValue(), 10);
        assertEquals(dateTime3.getYear(), 2016);

        //DateTimeFormatter can be used to format any type of date and/or time object.
        System.out.println(dateTime3.format(DateTimeFormatter.ISO_DATE_TIME));

        DateTimeFormatter shortDT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        DateTimeFormatter medianDT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        DateTimeFormatter longDT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);  //involve time zones
        DateTimeFormatter fullDT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);  //involve time zones

        System.out.println("==========");
        DateTimeFormatter[] formatters ={ shortDT, medianDT};
        for(DateTimeFormatter f : formatters){
            System.out.println(f.format(dateTime3));
        }

        //either would be OK!
        assertEquals(dateTime3.format(shortDT), shortDT.format(dateTime3));

        //
        System.out.println("======skip the part omitted");
        DateTimeFormatter shortD = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        DateTimeFormatter shortT = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        System.out.println(shortD.format(dateTime3));
        System.out.println(shortT.format(dateTime3));

        /*
        yyyy: y represents the year. yy outputs a two-digit year and yyyy outputs a four-digit year.
        MMMM: M outputs 1, MM outputs 01, MMM outputs Jan, and MMMM outputs January.
        dd: d represents the date in the month. dd means to include the leading zero for a single-digit month.
        hh: h represents the hour. local; HH: 24
        mm: m represents the minute.
        , Use , if you want to output a comma
        : Use : if you want to output a colon.

         */
        System.out.println("======customize");
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy MMM, d H:m");
        System.out.println(myFormat.format(dateTime3));


        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM dd yyyy");
        LocalDate date = LocalDate.parse("01 02 2015", f);
        LocalTime time = LocalTime.parse("11:22");
        System.out.println(date); // 2015-01-02
        System.out.println(time); // 11:22
        //Here we show using both a custom formatter and a default value.
        LocalDateTime ldt = LocalDateTime.parse("2016 Oct, 1 3:12", myFormat);
        System.out.println(ldt);

        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("MM/dd, yyyy");
        LocalDate dateM = LocalDate.parse("01/02, 2015", f1);
        System.out.println(dateM); // 2015-01-02

        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime timeM = LocalTime.parse("11:22", f2);
        System.out.println(timeM);

        /*
        Use DateTimeFormatter.ofPattern("kk mm"); for 12 hour clock
        DateTimeFormatter.ofPattern("HH mm") for 24 hour clock
        If you want to parse time with hh you must combine it wih a where you define AM or PM:
         */


        DateTimeFormatter myFormat1 = DateTimeFormatter.ofPattern("h:m a");
        LocalTime t = LocalTime.parse("3:12 PM", myFormat1);  //only AM or PM
        System.out.println(t);


    }


    @Test
    public void Test_CollectionEqualsMethod(){
        Set<Integer> set1 = new HashSet<>();
        set1.add(4);
        set1.add(6);
        set1.add(9);

        Set<Integer> set2 = new HashSet<>();
        set2.add(9);
        set2.add(4);
        set2.add(6);

        assertEquals(set1, set2);

        Map<Integer, String> map1 = new HashMap<>();
        map1.put(1, "will");
        map1.put(3, "bill");
        map1.put(5, "action");

        Map<Integer, String> map2 = new HashMap<>();
        map2.put(1, "will");
        map2.put(3, "bill");
        map2.put(5, "action");

        assertEquals(map1, map2);

        Queue<Integer> queue1 = new LinkedList<>();
        queue1.add(3);
        queue1.add(1);
        queue1.add(2);

        Queue<Integer> queue2 = new LinkedList<>();
        queue2.add(3);
        queue2.add(1);
        queue2.add(2);

        Queue<Integer> queue3 = new LinkedList<>();
        queue3.add(1);
        queue3.add(3);
        queue3.add(2);

        assertEquals(queue1, queue2);
        assertNotEquals(queue1, queue3); // in order


    }

    @Test
    public void test_MapToString() {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Math", 120);
        scores.put("Chinese", 115);
        scores.put("English", 110);
        System.out.println(scores);

    }
    @Test
    public void test_MapUpdateValue() {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Math", 120);
        scores.put("Chinese", 115);
        scores.put("English", 110);
        for (String s : scores.keySet()) {
            if (s.equals("Chinese")) {
                scores.put(s, scores.get(s)+1);
            }
        }
        assertTrue(116 == scores.get("Chinese"));
    }
    @Test
    public void BackwardTraverseUsingListIterator() {
        List<Integer> list = Arrays.asList(3, 5, 12, 34, 76);
        ListIterator<Integer> li = list.listIterator(list.size());
        while (li.hasPrevious()) {
            Integer i = li.previous();
            System.out.println(i);
        }
    }


}
