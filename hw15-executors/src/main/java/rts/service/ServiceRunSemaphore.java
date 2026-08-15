package rts.service;

import rts.executors.ThreadFirst;
import rts.executors.ThreadSecond;
import rts.semaphore.SemaphoreFirst;
import rts.semaphore.SemaphoreSecond;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public class ServiceRunSemaphore {
    ThreadFirst t1 = new ThreadFirst();
    ThreadSecond t2 = new ThreadSecond();
    Semaphore s1 = new Semaphore(1);
    Semaphore s2 = new Semaphore(0);
    SemaphoreFirst semaphoreFirst = new SemaphoreFirst();
    SemaphoreSecond semaphoreSecond = new SemaphoreSecond();

    public void myRun(int index) {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> semaphoreFirst.printSemaphore1(index, s1, s2), t1.execute());
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> semaphoreSecond.printSemaphore2(index, s1, s2), t2.execute());


    }
}