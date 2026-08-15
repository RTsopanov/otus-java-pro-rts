package ru.otus.services;

import lombok.extern.slf4j.Slf4j;
import ru.otus.api.SensorsDataChannel;
import ru.otus.api.model.SensorData;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SensorsDataQueueChannel implements SensorsDataChannel {

    private final BlockingQueue<SensorData> sensorsDataQueue;

    public SensorsDataQueueChannel(int sensorsDataQueueCapacity) {
        sensorsDataQueue = new ArrayBlockingQueue<>(sensorsDataQueueCapacity);
    }

    @Override
    public boolean push(SensorData sensorData) {
        var pushResult = sensorsDataQueue.offer(sensorData);
        if (!pushResult) {
            log.warn("Очередь показаний переполнена");
        }
        return pushResult;
    }

    @Override
    public boolean isEmpty() {
        return sensorsDataQueue.isEmpty();
    }

    @Override
    public SensorData take(long timeout, TimeUnit unit) throws InterruptedException {
        return sensorsDataQueue.poll(timeout, unit);
    }
}