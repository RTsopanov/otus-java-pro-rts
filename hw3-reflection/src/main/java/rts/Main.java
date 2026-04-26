package rts;

import static rts.handler.Handler.run;

public class Main {
    public static void main(String[] args) {
        run("rts.myTest.TestAllSuccess");
        run("rts.myTest.TestBeforeThrowException");
        run("rts.myTest.TestAfterThrowException");
    }
}