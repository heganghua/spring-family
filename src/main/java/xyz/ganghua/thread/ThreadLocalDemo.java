package xyz.ganghua.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemo {

    public static void main(String[] args) {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            newFixedThreadPool.execute(() -> {
                System.out.println(DataUtils.parse("2022-07-24 16:34:30"));
            });
        }
        newFixedThreadPool.shutdown();

    }
}
