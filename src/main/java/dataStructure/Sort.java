package dataStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by williaz on 10/8/16.
 */
public class Sort {


    public static void main(String[] args) {
        //int[] numbers={6,4,9,5};
        //bubbleSort(numbers);
        //System.out.println(numbers.length);
        //Arrays.stream(numbers).forEach(System.out::println);


        List<Integer> list=Arrays.asList(6,4,9,5,3,11,55,34);
        List<Integer> listSorted=mergeSort(list).stream().collect(Collectors.toList());
        listSorted.forEach(System.out::println);
        System.out.println("At "+getIndex(listSorted,5));







    }

    public static int getIndex(List<Integer> list, int target){
        if(list == null || list.isEmpty()){
            return -1;
        }

        int left=0;
        int right=list.size();
        int mid;

        while(left+1<right){
            mid=left+(right-left)/2;

            //first
            if(target>=list.get(mid)){
                left=mid;
            }else{
                right=mid;
            }

        }

        if(list.get(left)==target){
            return left;
        }
        if(list.get(right)==target){
            return right;
        }

        return -1;



    }

    public static List<Integer> mergeSort(List<Integer> list){
        int size=list.size();

        if(size<2){
            return list;
        }


        List<Integer> left=list.subList(0,size/2);
        List<Integer> right=list.subList(size/2,size);

        return merge(mergeSort(left),mergeSort(right));




    }

    public static List<Integer> merge(List<Integer> left, List<Integer> right){
        List<Integer> merged=new ArrayList<>();
        int l=0;
        int r=0;

        while(l<left.size() && r<right.size()){
            if(left.get(l)<right.get(r)){
                merged.add(left.get(l));
                l++;
            }else if(left.get(l)>right.get(r)){
                merged.add(right.get(r));
                r++;
            }else{
                merged.add(right.get(r));
                r++;
                l++;
            }
        }

        if(l<left.size()){
            merged.addAll(left);
        }

        if(r<right.size()){
            merged.addAll(right.subList(r,right.size()));
        }

        return merged;



    }


    public static List<Integer> quickSort(List<Integer> list){
        //exit
        if(list.size()<2){
            return list;
        }

        int pivot=list.get(0);
        List<Integer> left=new ArrayList<>();
        List<Integer> right=new ArrayList<>();

        for(int i=0; i<list.size(); i++){
            if(list.get(i)<pivot){
                left.add(list.get(i));
            }else{
                right.add(list.get(i));
            }
        }

        List<Integer> sorted=quickSort(left);

        //sorted.add(pivot);

        sorted.addAll(right);

        return sorted;



    }



    public static List<Integer> insertSort(List<Integer> list){
        List<Integer> sorted=new LinkedList<>();
        for(int i: list){
            if(sorted.isEmpty()){
                sorted.add(i);

            }else{
                boolean flag=false;
                for(int j=0; j<sorted.size(); j++){
                    if(i<=sorted.get(j)){
                        sorted.add(j,i); //add front
                        flag=true;
                        break;
                    }
                }
                if(!flag){
                    sorted.add(i);
                }
            }


        }//end for

        return sorted;

    }


    //ascending
    public static void bubbleSort(int[] arr){

        boolean flag;
        do {
            flag=false;
            for(int i=0 ; i<arr.length-1; i++){
                if(arr[i]>arr[i+1]){
                    int intern=arr[i+1];
                    arr[i+1]=arr[i];
                    arr[i]=intern;
                    flag=true;
                }
            }//end for
        } while (flag);
    }

}

