package ch04_lock.ch04_03_ThreadLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 **/
public class ch04_03_01_ParseDate_2 {
    static ThreadLocal<SimpleDateFormat> threadLocal= new ThreadLocal<SimpleDateFormat>();
    public static class  ParseDate implements Runnable
    {
        int i =0;
        public ParseDate(int i)
        {
            this.i=i;
        }

        public void run() {

            try {
                if (threadLocal.get()==null){
                    threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date t = threadLocal.get().parse("2015-03-29 19:29:" + i % 60);
                System.out.println(i+":"+t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new ParseDate(i));
        }
    }
}
