package service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import service.ServiceAcceptBanknotes;
import service.ServiceCalculateAmount;
import service.ServiceFindBanknotes;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@RequiredArgsConstructor
public class ServiceClientAccount {

    Map<String, Map<Integer, Integer>> accounts = new HashMap<>();
    private final ServiceAcceptBanknotes serviceAcceptBanknotesImpl;
    private final ServiceCalculateAmount serviceCalculateAmountImpl;
    private final ServiceFindBanknotes serviceFindBanknotesImpl;

    {
        accounts.put("account_1", new TreeMap<>(Comparator.reverseOrder()));
        accounts.put("account_2", new TreeMap<>(Comparator.reverseOrder()));
    }

    public void replenish(String account, List<Integer> amountList) {
        if (!accounts.containsKey(account)) {
            log.warn("Счета нет или указан некорректно: " + account);

            return;
        }
        Map<Integer, Integer> nominalMap = accounts.get(account);
        serviceAcceptBanknotesImpl.accept(nominalMap, amountList);
        int sum = serviceCalculateAmountImpl.calculate(nominalMap);

        log.info("Счет: {}, Сумма: {}, Номинал: {}", account, sum, nominalMap);
    }

    public void give(String account, int amount) {
        if (!accounts.containsKey(account)) {
            log.warn("Счета нет или указан некорректно: " + account);

            return;
        }
        Map<Integer, Integer> nominalMap = accounts.get(account);
        Map<Integer, Integer> result = new TreeMap<>(Comparator.reverseOrder());
        boolean found = serviceFindBanknotesImpl.findBanknotes(nominalMap, amount, result);

        if (!found) {
            log.warn("Нельзя выдать сумму {}", amount);
            return;
        }

        serviceAcceptBanknotesImpl.issue(nominalMap, result);
        int sum = serviceCalculateAmountImpl.calculate(nominalMap);

        log.info("Выдано: {} со счета {}, Номиналом: {}\n Остаток: {}", amount, account, result, sum);
    }

    public void giveAll(String account) {
        if (!accounts.containsKey(account)) {
            log.warn("Счета нет или указан некорректно: " + account);

            return;
        }

        Map<Integer, Integer> nominalMap = accounts.get(account);
        Map<Integer, Integer> result = new TreeMap<>(Comparator.reverseOrder());
        int sum = serviceCalculateAmountImpl.calculate(nominalMap);

        if (sum == 0) {
            log.info("Выдача наличных невозможна. Остаток на счете = {} руб.", sum);
            return;
        }

        serviceAcceptBanknotesImpl.issueAll(nominalMap, result);
        int sum1 = serviceCalculateAmountImpl.calculate(nominalMap);

        log.info("Выдано: {} со счета {}, Номиналом: {}\n Остаток: {}", sum, account, result, sum1);
    }
}