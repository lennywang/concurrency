package ch04_lock.ch04_04_cas;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 *
 **/
public class ch04_04_06_AtomicIntegerArray {
    static AtomicIntegerArray array = new AtomicIntegerArray(10);

    public static class AddThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                if ("Thread-0".equals(Thread.currentThread().getName()))
                    System.out.println(Thread.currentThread().getName() + ":" + i + ":" + array.length());
                array.getAndIncrement(i % array.length());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        for (int i = 0; i < 10; i++) {
            ts[i] = new Thread(new AddThread());
        }

        for (int i = 0; i < 10; i++) {
            ts[i].start();
        }

        for (int i = 0; i < 10; i++) {
            ts[i].join();
        }

        System.out.println(array);
    }
}
