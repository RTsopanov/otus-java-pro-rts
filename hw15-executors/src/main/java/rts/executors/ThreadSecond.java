package rts.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadSecond {

    public ExecutorService execute() {
        PriorityBlockingQueue priorityQueue = new PriorityBlockingQueue<>(10);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 60,
                TimeUnit.SECONDS, priorityQueue, new ThreadPoolExecutor.AbortPolicy());
        pool.allowCoreThreadTimeOut(true);

        return pool;
    }
}