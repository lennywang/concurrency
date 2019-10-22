package ch04_lock.ch04_04_cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 *
 **/
public class ch04_04_07_AtomicIntegerFieldUpdater {

    public static class Candidate{
        int id;
        volatile  int score;
    }

    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater = AtomicIntegerFieldUpdater.newUpdater(Candidate.class,"score");
    public static AtomicInteger allScore = new AtomicInteger(0);

    public static void main(String[] args) {
        final Candidate stu = new Candidate();
        Thread[] ts = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            ts[i] = new Thread(){
                @Override
                public void run() {
                    if (Math.random()>0.4)
                    {
                        scoreUpdater.incrementAndGet(stu);
                        allScore.incrementAndGet();
                    }
                }

            };
            ts[i].start();
        }

        for (int i = 0; i < 10000; i++) {
            try {
                ts[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("score="+stu.score);
        System.out.println("allScore="+allScore);
    }


}
