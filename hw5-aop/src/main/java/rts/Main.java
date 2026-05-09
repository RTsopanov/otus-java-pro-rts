package rts;

import rts.handler.HandlerFactory;
import rts.logger.Logging;
import rts.logger.LoggingInterface;

public class Main {
    public static void main(String[] args) {
        Logging logging = new Logging();
        LoggingInterface proxy = HandlerFactory.createProxy(logging, LoggingInterface.class);

        proxy.calculation(1);
        proxy.calculation(1, 1);
        proxy.calculation(1, 1, 1);


    }
}