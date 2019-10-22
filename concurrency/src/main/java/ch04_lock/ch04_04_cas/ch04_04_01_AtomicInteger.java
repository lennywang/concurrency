package ch04_lock.ch04_04_cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 **/
public class ch04_04_01_AtomicInteger {
    static AtomicInteger i = new AtomicInteger();
    public static class addThread implements Runnable{

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                i.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts=new Thread[10];
        for (int j = 0; j < 10; j++) {
            ts[j] = new Thread(new addThread());
        }
        for (int j = 0; j < 10; j++) {
            ts[j].start();
        }
        for (int j = 0; j < 10; j++) {
            ts[j].join();
        }
        System.out.println(i);
    }
}
