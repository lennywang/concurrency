package ch03_Juc.ch03_01_ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 **/
public class ch03_01_01_03_TimeLock implements Runnable {
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public void run() {
        try {
            if (reentrantLock.tryLock(5, TimeUnit.SECONDS)){
                TimeUnit.SECONDS.sleep(6);
            }else{
                System.out.println("get lock fail");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            if (reentrantLock.isHeldByCurrentThread())
                reentrantLock.unlock();
        }
    }

    public static void main(String[] args) {
        ch03_01_01_03_TimeLock timeLock = new ch03_01_01_03_TimeLock();
        Thread thread1 = new Thread(timeLock);
        Thread thread2 = new Thread(timeLock);
        thread1.start();
        thread2.start();
    }
}
