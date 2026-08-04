package service.impl;

import java.util.Map;

public class ServiceCalculateAmountImpl implements service.ServiceCalculateAmount {
    @Override
    public Integer calculate(Map<Integer, Integer> cells) {
        return cells.entrySet().stream()
                .mapToInt(entry -> entry.getKey() * entry.getValue())
                .sum();
    }
}