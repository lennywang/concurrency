package ch03_Juc.ch03_01_ReentrantLock;

import jdk.nashorn.internal.ir.TryNode;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 **/
public class ch03_01_01_03_TryLock implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public ch03_01_01_03_TryLock(int lock) {
        this.lock = lock;
    }

    public void run() {
        if (lock==1){
            while (true){
                if (lock1.tryLock()){
                    try {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (lock2.tryLock()){
                            try {
                                System.out.println(Thread.currentThread().getName()+": My Job Done");
                                return;
                            }finally {
                                lock2.unlock();
                            }
                        }
                    }finally {
                        lock1.unlock();
                    }
                }
            }
        }else{
            while (true){
                if (lock2.tryLock()){
                    try {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (lock1.tryLock()){
                            try{
                                System.out.println(Thread.currentThread().getName()+": My Job Done");
                                return;
                            }finally {
                                lock1.unlock();
                            }
                        }
                    }finally{
                        lock2.unlock();
                    }
                }
            }


        }
    }

    public static void main(String[] args) {
        ch03_01_01_03_TryLock tryLock1 = new ch03_01_01_03_TryLock(1);
        ch03_01_01_03_TryLock tryLock2 = new ch03_01_01_03_TryLock(2);

        Thread thread1 = new Thread(tryLock1,"Thread1");
        Thread thread2 = new Thread(tryLock2,"Thread2");

        thread1.start();
        thread2.start();
    }
}
