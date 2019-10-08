package ch03_Juc.ch03_01_ReentrantLock;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 **/
public class ch03_01_06_CyclicBarrier {

    public static class Soldier implements Runnable{
        private String soldier;
        private CyclicBarrier cyclicBarrier;

        public Soldier(String soldier, CyclicBarrier cyclicBarrier) {
            this.soldier = soldier;
            this.cyclicBarrier = cyclicBarrier;
        }

        public void run() {
            try {
                cyclicBarrier.await();
                doWork();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork(){
            try {
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier+":任务完成");
        }
    }

  public static class BarrierRun implements Runnable{
        boolean flag;
        int N;

      public BarrierRun(boolean flag, int n) {
          this.flag = flag;
          N = n;
      }

      public void run() {
          if (flag){
              System.out.println("司令：【士兵"+N+"个，任务完成！】");
          }else {
              System.out.println("司令：【士兵"+N+"个，集合完毕！】");
              flag = true;
          }
      }
  }

    public static void main(String[] args) {
        final int N = 10;
        Thread[] allSoilder = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N,new BarrierRun(flag,N));
        System.out.println("集合队伍！");
        for (int i = 0; i < N; i++) {
            System.out.println("士兵"+i+"报道！");
            allSoilder[i] = new Thread(new Soldier("士兵"+i,cyclicBarrier));
            allSoilder[i].start();
        }
    }

}
