package rts;

public class ArraySwap {

    public <T> T[] swap(T[] array, int i, int j) {
        if (array == null || i < 0 || j < 0 || i >= array.length || j >= array.length) {
            throw new IllegalArgumentException("Некорректные индексы");
        }
        T sim = array[i];
        array[i] = array[j];
        array[j] = sim;
       return array;
    }
}