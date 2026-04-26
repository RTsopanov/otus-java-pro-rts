package rts.myTest;

import rts.anntotations.After;
import rts.anntotations.Before;
import rts.anntotations.Test;

public class TestAllSuccess {

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
        for (int i = 0; i <= 5; i++) {
            System.out.print(i + " ");
        }
    }

    @Test
    void testMethodSecond() {
        for (int i = -5; i < 0; i++) {
            System.out.print(i + " ");
        }
    }

    @Test
    void testMethodThird() {
        for (int i = 0; i < 50; i += 10) {
            System.out.print(i + " ");
        }
    }
}