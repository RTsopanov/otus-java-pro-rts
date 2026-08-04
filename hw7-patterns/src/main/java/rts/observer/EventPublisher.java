package rts.observer;

import rts.annotation.EventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventPublisher {
    private final List<Object> subscribers = new ArrayList<>();

    public void subscribe(Object subscriber) {
        subscribers.add(subscriber);
    }

    public void publish(ResetEvent event) {
        for (Object subscriber : subscribers) {
            for (Method method : subscriber.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(EventListener.class)) {
                    invokeListener(subscriber, method, event);
                }
            }
        }
    }

    private void invokeListener(Object subscriber, Method method, ResetEvent event) {
        try {
            method.setAccessible(true);
            method.invoke(subscriber, event);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Не удалось вызвать listener " + method.getName(), e);
        }
    }
}