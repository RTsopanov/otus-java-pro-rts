package rts.observer;


import java.lang.reflect.Method;

public class Listener {
    private final Object subscriber;
    private final Method method;

    public Listener(Object subscriber, Method method) {
        this.subscriber = subscriber;
        this.method = method;
    }

    public void invoke(Object event) {
        try {
            method.setAccessible(true);
            method.invoke(subscriber, event);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(
                    "Не удалось вызвать listener " + method.getName(),
                    e
            );
        }
    }
}