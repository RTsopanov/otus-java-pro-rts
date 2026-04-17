package rts;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListConverter {

    public  <T> ArrayList<T> toArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }
}