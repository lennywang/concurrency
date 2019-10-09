package ch03_Juc.ch03_01_ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 **/
public class ch03_01_01_02_IntLock implements Runnable {

    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public ch03_01_01_02_IntLock(int lock) {
        this.lock = lock;
    }

    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace(System.out);
                }
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        } finally {
            if (lock1.isHeldByCurrentThread())
                lock1.unlock();
            if (lock2.isHeldByCurrentThread())
                lock2.unlock();
            System.out.println(Thread.currentThread().getName()+":綫程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ch03_01_01_02_IntLock intLock1 = new ch03_01_01_02_IntLock(1);
        ch03_01_01_02_IntLock intLock2 = new ch03_01_01_02_IntLock(2);
        Thread thread1 = new Thread(intLock1,"Thread-1");
        Thread thread2 = new Thread(intLock2,"Thread-2");
        thread1.start();
        thread2.start();
        TimeUnit.SECONDS.sleep(2);
        thread2.interrupt();

        //java.lang.InterruptedException
        //Thread-2:綫程退出
        //Thread-1:綫程退出
    }
}
