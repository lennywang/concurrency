package ch03_Juc.ch03_01_ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 **/
public class ch03_01_01_ReentrantLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static int i=0;

    public void run() {
        for (int j = 0; j < 10000000; j++) {
            lock.lock();
            try {
                i++;
            }finally{
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ch03_01_01_ReentrantLock reentrantLock = new ch03_01_01_ReentrantLock();
        Thread thread1 = new Thread(reentrantLock);
        Thread thread2 = new Thread(reentrantLock);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(i);
    }
}
