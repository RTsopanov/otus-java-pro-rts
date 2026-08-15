package ru.otus.services.processors;

import lombok.extern.slf4j.Slf4j;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

// Этот класс нужно реализовать
@SuppressWarnings({"java:S1068", "java:S125"})
@Slf4j
public class SensorDataProcessorBuffered implements SensorDataProcessor {

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public void process(SensorData data) {
        /*
            if (dataBuffer.size() >= bufferSize) {
                flush();
            }
        */
    }

    public void flush() {
        try {
            // writer.writeBufferedData(bufferedData);
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}