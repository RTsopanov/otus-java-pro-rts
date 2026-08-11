package rts.appcontainer;

import rts.appcontainer.api.AppComponent;
import rts.appcontainer.api.AppComponentsContainer;
import rts.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        try {
            Object configInstance = configClass.getDeclaredConstructor().newInstance();

            List<Method> componentMethods = new ArrayList<>();
            for (Method method : configClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(AppComponent.class)) {
                    componentMethods.add(method);
                }
            }

            componentMethods.sort(
                    Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order())
            );

            for (Method method : componentMethods) {
                method.setAccessible(true);

                Parameter[] parameters = method.getParameters();
                Object[] args = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    args[i] = getAppComponent(parameters[i].getType());
                }

                Object component = method.invoke(configInstance, args);

                AppComponent annotation = method.getAnnotation(AppComponent.class);
                appComponents.add(component);
                appComponentsByName.put(annotation.name(), component);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to process config " + configClass.getName(), e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        C result = null;
        for (Object component : appComponents) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                if (result != null) {
                    throw new IllegalArgumentException(
                            String.format("More than one component of type %s found", componentClass.getName()));
                }
                result = (C) component;
            }
        }
        if (result == null) {
            throw new IllegalArgumentException(
                    String.format("No component of type %s found", componentClass.getName()));
        }
        return result;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new IllegalArgumentException(String.format("No component with name %s found", componentName));
        }
        return (C) component;
    }
}