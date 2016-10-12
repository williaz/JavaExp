package thread;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by williaz on 10/11/16.
 * only wait() involve lock releasing
 */
public class ProdConsList {

    public static void main(String[] args) {
        WorkSpace workSpace = new WorkSpace();
        new Thread(new Producer(workSpace)).start();
        new Thread(new Consumer(workSpace)).start();
        new Thread(new Consumer(workSpace)).start();


    }

    private static class WorkSpace{
        private List<Integer> queue = new LinkedList<>();

        synchronized public void produce(Integer i){
            if(queue.isEmpty()){
                queue.add(i);
                notifyAll();
            }else{
                queue.add(i);
            }


        }

        synchronized public Integer consume(){
            while(queue.isEmpty()){
                try{
                    wait();//let the thread executing this one wait for notification.
                }catch(InterruptedException e){

                }
            }
            int i = queue.remove(0);

            return i;

        }
    }

    private static class Producer implements Runnable{
        private WorkSpace workSpace;

        public Producer(WorkSpace workSpace) {
            this.workSpace = workSpace;
        }

        @Override
        public void run() {
            for(int i = 0; i <= 5000; i++){

                try {
                    Thread.sleep((int) (Math.random() * 1000));
                } catch (InterruptedException e) {
                }

                workSpace.produce(i);
                System.out.println("Produce: "+i);

            }

        }
    }

    private static class Consumer implements Runnable{
        private WorkSpace workSpace;

        public Consumer(WorkSpace workSpace) {
            this.workSpace = workSpace;
        }

        @Override
        public void run() {
            int i = 0;

            do{
                try {
                    Thread.sleep((int) (Math.random() * 2000));
                } catch (InterruptedException e) {
                }

                i = workSpace.consume();
                System.out.println("consume: "+i);
            }while(i!=5000);

        }
    }


}
