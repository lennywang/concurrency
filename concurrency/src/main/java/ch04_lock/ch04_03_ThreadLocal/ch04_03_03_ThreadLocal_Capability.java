package ch04_lock.ch04_03_ThreadLocal;

import java.util.Random;
import java.util.concurrent.*;

/**
 *
 **/
public class ch04_03_03_ThreadLocal_Capability {
    public static final int GEN_COUNT=10000000;
    public static final int THREAD_COUNT=4;
    static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    public static Random random = new Random(123);
    public static ThreadLocal<Random> tRandom = new ThreadLocal<Random>(){
        @Override
        protected Random initialValue() {
            return new Random(123);
        }
    };

    public static class RndTask implements Callable<Long>{
        private int mode =0;

        public RndTask(int mode) {
            this.mode = mode;
        }

        public Random getRandom(){
            if (mode==0){
                return random;
            }else if(mode==1){
                return tRandom.get();
            }else{
                return null;
            }
        }

        @Override
        public Long call() throws Exception {
            long l = System.currentTimeMillis();
            for (int i = 0; i < GEN_COUNT; i++) {
                getRandom().nextInt();
            }
            long l2 = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+" spend "+(l2-l)+"ms");
            return l2-l;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future[] futures = new Future[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
           futures[i]=  executorService.submit(new RndTask(0));
        }
        long totalTime=0;
        for (int i = 0; i < THREAD_COUNT; i++) {
         totalTime+=  (Long) futures[i].get();
        }
        System.out.println("多线程访问同一个Random实例："+totalTime+"ms");

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i]= executorService.submit(new RndTask(1));
        }
        totalTime=0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            totalTime+=(Long) futures[i].get();
        }
        System.out.println("使用ThreadLocal包装Random实例："+totalTime+"ms");
        executorService.shutdown();
    }
}
