package rts;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class UniqueWordsProcessor {

    public HashSet printUniqueWords(String[] arrayStr) {

        return new HashSet<>(Arrays.asList(arrayStr));
    }

    public HashMap countWordFrequencies(String[] arrayStr) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String str : arrayStr) {
            map.merge(str, 1, Integer::sum);
        }

        return map;
    }

}