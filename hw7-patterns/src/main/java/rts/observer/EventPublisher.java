package rts.observer;

import rts.annotation.EventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventPublisher {
    private final Map<Class<?>, List<Listener>> listeners = new HashMap<>();

    public void subscribe(Object subscriber) {
        for (Method method : subscriber.getClass().getDeclaredMethods()) {

            if (method.isAnnotationPresent(EventListener.class)) {
                Class<?> eventType = method.getParameterTypes()[0];

                validate(method);

                listeners.computeIfAbsent(eventType, k -> new ArrayList<>())
                        .add(new Listener(subscriber, method));
            }
        }
    }

    public void publish(Object event) {
        List<Listener> handlers = listeners.get(event.getClass());

        if (handlers == null) {
            return;
        }

        handlers.forEach(listener -> listener.invoke(event));
    }

    private void validate(Method method) {
        if (method.getParameterCount() != 1) {
            throw new IllegalArgumentException(
                    "Listener должен принимать один аргумент"
            );
        }

        if (method.getReturnType() != void.class) {
            throw new IllegalArgumentException(
                    "Listener должен возвращать void"
            );
        }
    }
}