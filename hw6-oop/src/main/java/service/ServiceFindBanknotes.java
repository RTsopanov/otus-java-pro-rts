package service;

import java.util.Map;

public interface ServiceFindBanknotes {
    boolean findBanknotes(Map<Integer, Integer> banknotes, int amount, Map<Integer, Integer> result);
}