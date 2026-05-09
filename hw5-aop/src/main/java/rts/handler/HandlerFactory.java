package rts.handler;

import java.lang.reflect.Proxy;

public class HandlerFactory {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new TestHandler(target)
        );
    }
}