package rts.myTest;

import rts.anntotations.After;
import rts.anntotations.Before;
import rts.anntotations.Test;

public class TestAfterThrowException {
    @Before
    void setUp() {
        System.out.println("\nSTART TEST");
    }

    @After
    void after() {
        throw new RuntimeException();
    }

    @Test
    void testMethodFirst() {
        for (int i = 0; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println("\n----------------");
    }

    @Test
    void testMethodSecond() {
        for (int i = -5; i < 0; i++) {
            System.out.print(i + " ");
        }
        System.out.println("\n----------------");
    }

    @Test
    void testMethodThird() {
        for (int i = 0; i < 50; i += 10) {
            System.out.print(i + " ");
        }
        System.out.println("\n----------------");
    }
}