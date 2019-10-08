package ch03_Juc.referencemeterial;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 **/
public class ch03_Juc_RM_01_ScheduledThreadPoolExecutorTest {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        BusinessTask task = new BusinessTask();
        executorService.scheduleWithFixedDelay(task,1000,2000, TimeUnit.MILLISECONDS);
    }

    private static class BusinessTask implements Runnable{

        public void run() {

            try {
                System.out.println("任务开始...");
                doBusiness();
                System.out.println("任务结束...");
            } catch (Exception e) {
                //e.printStackTrace(System.out);
                System.out.println("出现异常");
            }
        }

        private void doBusiness(){
            System.out.println("执行结果："+1/0);
        }
    }
}
