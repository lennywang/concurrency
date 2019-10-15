package ch03_Juc.ch03_02_ThreadPool;

import java.util.concurrent.*;

/**
 *
 **/
public class ch03_02_08_02_TraceThreadPoolExecutor extends ThreadPoolExecutor {
    public static class DivTask implements Runnable{
        int a,b;

        public DivTask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public void run() {
            double re =a/b;
            System.out.println(re);
        }
    }

    public ch03_02_08_02_TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command,clientTrace(),Thread.currentThread().getName()));
    }

    @Override
    public Future<?> submit(Runnable task) {
       return super.submit(wrap(task,clientTrace(),Thread.currentThread().getName()));
    }

    private Exception clientTrace(){
        return new Exception("Client stack trace");
    }

    private Runnable wrap(final Runnable task,final Exception clientStack,String clientThreadName){
        return new Runnable() {
            public void run() {
                try {
                    task.run();
                }catch (Exception e){
                    clientStack.printStackTrace();
                    try {
                        throw e;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };
    }

    public static void main(String[] args) {
        ThreadPoolExecutor pools = new ch03_02_08_02_TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());
        for (int i = 0; i < 5; i++) {
            pools.execute(new DivTask(100,i));
        }
    }
}
