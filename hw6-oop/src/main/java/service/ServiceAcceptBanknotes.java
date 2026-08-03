package service;

import java.util.List;
import java.util.Map;

public interface ServiceAcceptBanknotes {
    void accept(Map<Integer, Integer> nominalMap, List<Integer> valueList);

    void issue(Map<Integer, Integer> cells, Map<Integer, Integer> banknotes);

    void issueAll(Map<Integer, Integer> cells, Map<Integer, Integer> banknotes);
}