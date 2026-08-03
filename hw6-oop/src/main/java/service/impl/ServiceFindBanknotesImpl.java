package service.impl;

import service.ServiceFindBanknotes;

import java.util.Map;
import java.util.TreeMap;

public class ServiceFindBanknotesImpl implements ServiceFindBanknotes {
    @Override
    public boolean findBanknotes(Map<Integer, Integer> banknotes, int amount, Map<Integer, Integer> result) {
        if (amount == 0) {
            return true;
        }

        if (banknotes.isEmpty()) {
            return false;
        }

        int nominal = banknotes.keySet().iterator().next();
        int nominalAmount = banknotes.get(nominal);
        int max = Math.min(amount / nominal, nominalAmount);

        for (int count = max; count >= 0; count--) {

            if (count > 0) {
                result.put(nominal, count);
            }

            Map<Integer, Integer> remaining = new TreeMap<>(banknotes);
            remaining.remove(nominal);

            if (findBanknotes(remaining, amount - nominal * count, result)) {
                return true;
            }

            result.remove(nominal);
        }
        return false;
    }
}