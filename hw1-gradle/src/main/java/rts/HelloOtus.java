package rts;

import com.google.common.collect.ImmutableList;

public class HelloOtus {
    public static void main(String[] args) {
        ImmutableList<String> list = ImmutableList.of("Hello", "Otus!");
        list.forEach(System.out::println);
    }
}