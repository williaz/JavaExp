package dataStructure;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by williaz on 9/26/16.
 */
public class DSTest {
    static int[] arr1=new int[15];
    static int[] arr2={4,5, 6,1,2};
    static int[] arr3=new int[]{1,2,3};
    //Write a program to determine whether the SUM of the Digits of the Number
    // to the Power of the Length of the number equals the original number.
    public static List<Integer> getTheNumber(int min, int max){
        //10-999
        List<Integer> nums= new ArrayList<>();

        for(int i=min; i<=max; i++){
            List<Integer> list=divNumber(i);
            int sum=0;
            for(int j=1; j<=list.get(0);j++){
                sum+=Math.pow(list.get(j),list.get(0));
            }
            if(i==sum) {
                nums.add(i);
            }

        }
        return nums;
    }

    public static List<Integer> divNumber(int num){
        int ten=10;
        List<Integer> nums= new ArrayList<>();
        nums.add(1);
        int number=0;

        while(num>0){

            nums.add(num%10);
            num/=10;
            number++;

        }

        nums.set(0,number);
        return nums;

    }

//    public static void arr2(int[] arr){
//        System.out.println(arr);
//        arr[0]*=2;
//    }


    public static void main(String[] args){

//        int[] a={2,1};
//        arr2(a);
//        System.out.println("main:"+a);


        //System.out.println(divNumber(123));
        //System.out.println(getTheNumber(10,999));

        /*
        System.out.println(Arrays.toString(arr1));
        System.arraycopy(arr2,0,arr1,0,4);
        System.out.println(Arrays.toString(arr1));

        String s1 = new String ("hello");
        String s2 = new String ("hello");

        if (s1.equals(s2)) System.out.println("equal");
        else System.out.println("not equal");

        System.out.println("+++++++++++++++++");

        StringBuffer sb1 = new StringBuffer ("hello");
        StringBuffer sb2 = new StringBuffer ("hello");

        if (sb1.equals(sb2)) System.out.println("equal");
        else System.out.println("not equal");
        */

    }






}




