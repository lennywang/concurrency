package ch03_Juc.ch03_01_ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 **/
public class ch03_01_01_04_FairLock implements Runnable {
    public static ReentrantLock failLock = new ReentrantLock(true);
    public void run() {
        while (true){
            try {
                failLock.lock();
                System.out.println(Thread.currentThread().getName()+"获得锁");
            }finally {
                failLock.unlock();
            }
        }

    }

    public static void main(String[] args) {
        ch03_01_01_04_FairLock fairLock = new ch03_01_01_04_FairLock();
        Thread thread_t1 = new Thread(fairLock, "Thread_t1");
        Thread thread_t2 = new Thread(fairLock, "Thread_t2");
        thread_t1.start();
        thread_t2.start();

        //Thread_t1获得锁
        //Thread_t2获得锁
        //Thread_t1获得锁
        //Thread_t2获得锁
    }
}
