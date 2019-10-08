package ch03_Juc.ch03_01_ReentrantLock;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 **/
public class ch03_01_04_ReadWriteLock {
    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = reentrantReadWriteLock.readLock();
    private static Lock writeLock = reentrantReadWriteLock.writeLock();
    private int value;

    public Object handleRead(Lock lock){
        try {
            lock.lock();
            TimeUnit.SECONDS.sleep(1);
            return value;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        }finally {
            lock.unlock();
        }
    }

    public void handleWrite(Lock lock,int index){
        try {
            lock.lock();
            TimeUnit.MILLISECONDS.sleep(500);
            value=index;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ch03_01_04_ReadWriteLock readWriteLock = new ch03_01_04_ReadWriteLock();
        Runnable readRunnable = new Runnable() {
            public void run() {
                try {
                    readWriteLock.handleRead(readLock);
                    //readWriteLock.handleRead(lock);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        Runnable writeRunnable = new Runnable() {
            public void run() {
                try {
                    readWriteLock.handleWrite(writeLock,new Random().nextInt());
                    //readWriteLock.handleWrite(lock,new Random().nextInt());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 18; i++) {
            new Thread(readRunnable).start();
        }

        for (int i = 0; i < 18; i++) {
            new Thread(writeRunnable).start();
        }

    }
}
