package intro;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by williaz on 10/6/16.
 */
public class CoreJavaTest {
    @Test(expected = NullPointerException.class)
    public void testNullMethodThrowsException(){
        final String s=null;
        final int len=s.length();
    }
    @Test
    public void testReferenceType(){
        List<Integer> l1=new ArrayList<Integer>();
        l1.add(53);
        Assert.assertTrue(l1.size()==1);
        List<Integer> l2=l1;
        l2.add(41);
        assertEquals(l2.size(),2);

    }

    @Test
    public void testChangePriavteFieldInSameClassObject(){
        PrivateFieldClass pf1=new PrivateFieldClass("will",123);
        PrivateFieldClass pf2=new PrivateFieldClass("bo",21);
        pf2.setOtherName(pf1,"Austin");

        assertEquals(pf1.getName(),"Austin");



    }


    @Test
    public void testEquality(){
        PrivateFieldClass pf1=new PrivateFieldClass("bo",123);
        PrivateFieldClass pf2=new PrivateFieldClass("bo",21);

        assertEquals(pf1, pf2);

    }

    @Test
    public void polymorphicList() {
        List<Rectangle> rectangles = new ArrayList<>(3);
        rectangles.add(new Rectangle(5, 1));
        rectangles.add(new Rectangle(2, 10));
        rectangles.add(new Square(9));
        assertEquals(rectangles.get(0).area(), 5);
        assertEquals(rectangles.get(1).area(), 20);
        assertEquals(rectangles.get(2).area(), 81);
    }

    @Test
    public void testStringLiteral(){
        String str1=new String("Hello!");
        String str2="Hello!";


        Assert.assertEquals(str1,str2);

    }




}
