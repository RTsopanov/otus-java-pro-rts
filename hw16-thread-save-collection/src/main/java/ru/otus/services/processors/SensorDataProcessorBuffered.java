package ru.otus.services.processors;

import lombok.extern.slf4j.Slf4j;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

// Этот класс нужно реализовать
@SuppressWarnings({"java:S1068", "java:S125"})
@Slf4j
public class SensorDataProcessorBuffered implements SensorDataProcessor {

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final Queue<SensorData> dataBuffer = new ConcurrentLinkedQueue<>();
    private final AtomicInteger bufferedCount = new AtomicInteger(0);
    private final AtomicBoolean flushInProgress = new AtomicBoolean(false);

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public void process(SensorData data) {
        dataBuffer.add(data);

        if (bufferedCount.incrementAndGet() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        if (!flushInProgress.compareAndSet(false, true)) {
            return;
        }

        try {
            if (dataBuffer.isEmpty()) {
                return;
            }

            List<SensorData> bufferedData = drainBuffer();
            if (bufferedData.isEmpty()) {
                return;
            }

            bufferedData.sort(Comparator.comparing(SensorData::getMeasurementTime));
            writer.writeBufferedData(bufferedData);
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        } finally {
            flushInProgress.set(false);
        }
    }

    private List<SensorData> drainBuffer() {
        var result = new ArrayList<SensorData>();
        SensorData item;
        while ((item = dataBuffer.poll()) != null) {
            bufferedCount.decrementAndGet();
            result.add(item);
        }
        return result;
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}