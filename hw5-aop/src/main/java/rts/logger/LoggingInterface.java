package rts.logger;

import rts.annotations.Log;

public interface LoggingInterface {
    void calculation(int a);

    @Log
    int calculation(int a, int b);

    @Log
    int calculation(int a, int b, int c);
}