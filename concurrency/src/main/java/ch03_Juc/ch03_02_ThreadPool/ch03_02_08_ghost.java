package ch03_Juc.ch03_02_ThreadPool;

import java.util.concurrent.*;

/**
 *
 **/
public class ch03_02_08_ghost {
    public static class DivTask implements Runnable{
        int a,b;

        public DivTask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public void run() {
            double re =a/b;
            System.out.println(re);
        }
    }

    public static void main(String[] args)  {
        ThreadPoolExecutor pools = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        for (int i = 0; i < 5; i++) {
            pools.submit(new DivTask(100,i));
        }
    }
}
