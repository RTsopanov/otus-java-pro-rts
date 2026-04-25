package rts.myTest;

import rts.anntotations.After;
import rts.anntotations.Before;
import rts.anntotations.Test;

public class TestOne {

    @Before
    void setUp() {
        System.out.println("START TEST");
    }

    @After
    void after() {
        System.out.println("\nFINISH\n");
    }

    @Test
    void testMethodFirst() {
        for (int i = 0; i < 5; i++) {
            System.out.print(i + " ");
        }
    }

    @Test
    void testMethodSecond() {
        throw new IllegalArgumentException();
    }

    @Test
    void testMethodThird() {
        for (int i = 0; i < 50; i += 10) {
            System.out.print(i + " ");
        }
    }
}