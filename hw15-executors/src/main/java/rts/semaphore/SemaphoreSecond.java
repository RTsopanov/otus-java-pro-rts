package rts.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreSecond {

    public void printSemaphore2(int index, Semaphore semaphore1, Semaphore semaphore2) {
        int count = 1;
        while (count <= index) {
            try {
                semaphore2.acquire();
                log.info("Поток 2: {}", count);
                count++;
                semaphore1.release();
            } catch (InterruptedException e) {
                semaphore2.release();
                throw new RuntimeException(e);
            }
        }
        index--;
        while (index >= 1) {
            try {
                semaphore2.acquire();
                log.info("Поток 2: {}", index);
                index--;
                semaphore1.release();
            } catch (InterruptedException e) {
                semaphore2.release();
                throw new RuntimeException(e);
            }
        }
    }
}