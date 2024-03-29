package ch03_Juc.ch03_02_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 **/
public class ch03_02_06_ExtThreadPool {
    public static class MyTask implements Runnable{
        public String name;
        public MyTask(String name){
            this.name=name;
        }

        public void run() {
            System.out.println("正在执行 Thread ID："+Thread.currentThread().getId()+"，Task Name = "+ name);

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Integer integer= Runtime.getRuntime().availableProcessors();
        System.out.println(integer);
        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行："+((MyTask)r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成："+((MyTask)r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };

        for (int i = 0; i < 5; i++) {
            MyTask task = new MyTask("TASK-GEYM-" + i);
            //executorService.submit(task);
            executorService.execute(task);
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
