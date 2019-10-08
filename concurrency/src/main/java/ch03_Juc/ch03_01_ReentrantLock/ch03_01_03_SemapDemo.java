package ch03_Juc.ch03_01_ReentrantLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 **/
public class ch03_01_03_SemapDemo implements Runnable {
    final Semaphore semaphore = new Semaphore(5);
    public void run() {
        try {
         semaphore.acquire();
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName()+":done!");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final   ch03_01_03_SemapDemo semapDemo = new ch03_01_03_SemapDemo();
        for (int i = 0; i < 20; i++) {
            executorService.submit(semapDemo);
        }
    }
}
