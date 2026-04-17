package rts;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArraySwap arraySwap = new ArraySwap();
        ArrayListConverter arrayListConverter = new ArrayListConverter();
        UniqueWordsProcessor uniqueWordsProcessor = new UniqueWordsProcessor();

        Integer[] arrInt = {5, 2, 3, 4, 1};
        String[] arrString = {"5", "4", "2", "3", "1" };
        String[] words = {"java", "python", "java", "kotlin", "python",
                "javascript", "java", "c++", "python", "java",
                "kotlin", "javascript", "java", "python", "c++",
                "java", "kotlin", "python", "javascript", "java"
        };

        System.out.println("TASK_1");
        System.out.println(Arrays.toString(arraySwap.swap(arrInt, 0, 4)));
        System.out.println(Arrays.toString(arraySwap.swap(arrString, 2, 3)));

        System.out.println("\nTASK_2");
        System.out.println(arrayListConverter.toArrayList(arrInt));

        System.out.println("\nTASK_3");
        System.out.println(uniqueWordsProcessor.printUniqueWords(words));
        System.out.println(uniqueWordsProcessor.countWordFrequencies(words));
    }
}