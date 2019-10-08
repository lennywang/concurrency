package ch03_Juc.ch03_01_ReentrantLock;

import java.util.concurrent.locks.LockSupport;

/**
 *
 **/
public class ch03_01_07_02_LockSupportInt {
    public static Object u = new Object();
    static ch03_01_07_LockSupport.ChangeObjectThread t1 = new ch03_01_07_LockSupport.ChangeObjectThread("t1");
    static ch03_01_07_LockSupport.ChangeObjectThread t2 = new ch03_01_07_LockSupport.ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread{

        public ChangeObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.println("in "+getName());
                LockSupport.park();
                if (Thread.interrupted())
                {
                    System.out.println(getName()+" 被中断了");
                }
            }
            System.out.println(getName()+"执行结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.interrupt();
        LockSupport.unpark(t2);
    }
}
