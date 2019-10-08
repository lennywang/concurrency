package ch03_Juc.ch03_01_ReentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 **/
public class ch03_01_02_ReentrantLockCondition implements Runnable {
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public static Condition condition=reentrantLock.newCondition();
    public void run() {
        try {
            reentrantLock.lock();
            condition.await();
            System.out.println("Thread is goning on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ch03_01_02_ReentrantLockCondition reentrantLockCondition = new ch03_01_02_ReentrantLockCondition();
        Thread thread = new Thread(reentrantLockCondition);
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        reentrantLock.lock();
        condition.signal();
        reentrantLock.unlock();
    }
}
