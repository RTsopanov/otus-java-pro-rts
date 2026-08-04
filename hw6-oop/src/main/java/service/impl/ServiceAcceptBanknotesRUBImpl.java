package service.impl;

import service.ServiceAcceptBanknotes;

import java.util.List;
import java.util.Map;

public class ServiceAcceptBanknotesRUBImpl implements ServiceAcceptBanknotes {
    @Override
    public void accept(Map<Integer, Integer> nominalMap, List<Integer> amountList) {
        for (Integer nominal : amountList) {
            nominalMap.merge(nominal, 1, Integer::sum);
        }
    }

    @Override
    public void issue(Map<Integer, Integer> nominalMap, Map<Integer, Integer> banknotes) {
        banknotes.forEach((nominal, count) ->
                nominalMap.computeIfPresent(nominal,
                        (k, v) -> v - count));
    }

    @Override
    public void issueAll(Map<Integer, Integer> nominalMap, Map<Integer, Integer> banknotes) {
        nominalMap.forEach((nominal, count) -> {
            if (count > 0) {
                banknotes.put(nominal, count);
            }
        });
        nominalMap.clear();
    }
}