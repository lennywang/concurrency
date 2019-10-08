package ch03_Juc.ch03_01_ReentrantLock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 **/
public class ch03_01_05_CountDownLatch implements Runnable {

    static final CountDownLatch end= new CountDownLatch(10);
    static final ch03_01_05_CountDownLatch count_down_latch= new ch03_01_05_CountDownLatch();

    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10)*100);
            System.out.println("check complete");
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(count_down_latch);
        }
        end.wait();
        System.out.println("Fire!");
        executorService.shutdown();
    }
}
