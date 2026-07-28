package rts.logger;

public class Logging implements LoggingInterface {

    @Override
    public void calculation(int a) {
    }

    @Override
    public int calculation(int a, int b) {
        return a + b;
    }

    @Override
    public int calculation(int a, int b, int c) {
        return a + b + c;
    }
}