package rts.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreFirst  {

    public void printSemaphore1(int index, Semaphore semaphore1, Semaphore semaphore2) {
        int count = 1;
        while (count <= index) {
            try {
                semaphore1.acquire();
                log.info("Поток 1: {}", count);
                count++;
                semaphore2.release();
            } catch (InterruptedException e) {
                semaphore1.release();
                throw new RuntimeException(e);
            }
        }
        index--;

        while (index >= 1) {
            try {
                semaphore1.acquire();
                log.info("Поток 1: {}", index);
                index--;
                semaphore2.release();
            } catch (InterruptedException e) {
                semaphore1.release();
                throw new RuntimeException(e);
            }
        }
    }

}