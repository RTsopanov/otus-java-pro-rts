package rts.logger;

import rts.annatations.Log;

public class Logging implements LoggingInterface {

    @Override
    public void calculation(int a) {

    }

    @Override
    @Log
    public int calculation(int a, int b) {
        return a + b;
    }

    @Override
    @Log
    public int calculation(int a, int b, int c) {
        return a + b + c;
    }
}