package rts.handler;

import lombok.extern.slf4j.Slf4j;
import rts.annatations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class TestHandler implements InvocationHandler {
    private final Object target;

    public TestHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log annotation = target.getClass().getMethod(method.getName(), method.getParameterTypes()).getDeclaredAnnotation(Log.class);
        if (annotation == null) {

            return method.invoke(target, args);
        } else {
            log.info("executed method: {}, param: {}", method.getName(), Arrays.toString(args));

            return method.invoke(target, args);
        }
    }
}