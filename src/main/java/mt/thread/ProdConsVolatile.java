package mt.thread;

/**
 * Created by williaz on 10/13/16.
 */
public class ProdConsVolatile {
    private static volatile long sharedInteger=Long.MAX_VALUE-10000L;

    public static void main(String[] args) {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();

    }


    private static class Producer implements Runnable{
//        private volatile long sharedInteger=0L;
//
//        public Producer(long sharedInteger) {
//            this.sharedInteger = sharedInteger;
//        }

        @Override
        public void run() {
            for(int i = 0; i < 100; i++){

                try{
                    Thread.sleep(400);
                }catch(InterruptedException e){

                }

                sharedInteger++;
            }
        }
    }//class


    private static class Consumer implements  Runnable{
//        private volatile long sharedInteger=0L;
//
//        public Consumer(long sharedInteger) {
//            this.sharedInteger = sharedInteger;
//        }


        @Override
        public void run() {
            for(int i = 0; i < 100; i++){

                try{
                    Thread.sleep(400);
                }catch(InterruptedException e){

                }

                System.out.println(sharedInteger);
            }
        }
    }//class


}
