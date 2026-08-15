package ru.otus.services.processors;

import lombok.extern.slf4j.Slf4j;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;

@Slf4j
public class SensorDataProcessorRoom implements SensorDataProcessor {

    private final String roomName;

    public SensorDataProcessorRoom(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public void process(SensorData data) {
        if (data.getValue() == null || data.getValue().isNaN()) {
            return;
        }
        log.info("Обработка данных по заданной комнате ({}): {}", roomName, data);
    }
}