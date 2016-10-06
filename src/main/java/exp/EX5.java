package exp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by williaz on 10/3/16.
 * Mean What??Calculate the average of the ‘Result’ calculated above from all the data points
 */
public class EX5 {
    private final static double pi=3.14;
    private final static double psi=6.39485;
    private final static double zeta=3.2;


    public static List<Character> getCharArr(String ss){
        char[] cc=ss.toCharArray();
        List<Character> re=new ArrayList<>();
        for(char c: cc){
            if(c!=' '){
                re.add(c);
            }
        }

        return re;

    }

    public static double printResult(int num, List<Character> elements){
        if(num==2){
            int i=Character.getNumericValue(elements.get(0));
            int j=Character.getNumericValue(elements.get(1));
            double avg=(i+j)/2.0;
            System.out.printf("AVG = %.3f\n",avg);
            double result=pi*i + psi*(1.34+ Math.pow(j,3));

            System.out.printf("FORM = %.3f\n",result);


            return result;


        }
        else if(num==3){
            int i=Character.getNumericValue(elements.get(0));
            int j=Character.getNumericValue(elements.get(1));
            int n=3;
            char k=elements.get(2);

            if(k=='b'){
                n=4;
            }else if(k=='c'){
                n=6;
            }


            double avg=(i+j+n)/3.0;
            System.out.printf("AVG = %.3f\n",avg);
            double result=pi*i + psi*(1.34+ Math.pow(j,3)) +Math.pow(zeta,n)/(double)j;

            System.out.printf("FORM = %.3f\n",result);


            return result;

        }

        return 0;

    }

    public static void main(String []args) {
        String [] files={"/Users/williaz/IdeaProjects/JavaExp/Ex5Text"};
        if(files.length == 0) {
            System.err.println("pass the name of the file(s) as argument");
            System.exit(-1);
        }


        for(String file : files) {



            try (BufferedReader inputFile = new BufferedReader(new FileReader(new File(file)))) {

                int numOfSample=0;

                String ch = null;
                //first line
                if ( (ch = inputFile.readLine()) != null ){
                   numOfSample = Integer.parseInt(ch);
                }
                else{
                    return;//no first, out
                }

                int numRecord=0;
                double AvgResult=0;

                for(int i=0; i<numOfSample; i++){
                    List<Character> meta=null;
                    if ( (ch = inputFile.readLine()) != null ){
                        meta=getCharArr(ch);

                    }else{
                        return;
                    }

                    System.out.println("Sample++++++++");

                    if(meta.get(0).equals('*')){
                        List<Character> elements=null;
                        int first=Character.getNumericValue(meta.get(1));
                        //System.out.println("fst:"+first);//
                        for(int j=0; j< first; j++){
                            ////do
                            if ( (ch = inputFile.readLine()) != null ){
                                elements=getCharArr(ch);
                                int second=Character.getNumericValue(meta.get(2));
                                numRecord++;
                                System.out.println("Total record read: "+numRecord);
                                AvgResult+=printResult(second,elements);

                                System.out.printf("the average of the ‘Result’ " +
                                        "calculated above from all the data point = %.3f\n\n",AvgResult/numRecord);

                            }else{
                                return;
                            }
                        }


                    }else{
                        System.err.println("No *");
                        return;
                    }

                }





            } catch (FileNotFoundException fnfe) {


                System.err.println("Cannot open the given file"+file+" Reason: "+fnfe.toString());
            } catch (IOException ioe) {

                System.err.printf("Error when processing file %s... skipping it", file);
            }

        }
    }
}
