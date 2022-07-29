package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        final Object instance = getInstanceClass(configClass);
        List<Method> sortedMethods = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .toList();
        sortedMethods.forEach(method -> {
            String componentName = method.getAnnotation(AppComponent.class).name();
            initComponent(instance, method, componentName);
        });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private Object getInstanceClass(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to initialize class %s.", clazz));
        }
    }

    private void initComponent(Object instance, Method method, String componentName) {
        Object[] parameters = Arrays.stream(method.getParameterTypes())
                .map(this::getAppComponent).toArray();

        try {
            Object component = method.invoke(instance, parameters);
            if (!appComponentsByName.containsKey(componentName)) {
                appComponentsByName.put(componentName, component);
                appComponents.add(component);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to initialize app component %s. ", componentName));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
                .filter(component -> componentClass.isInstance(component))
                .findAny()
                .orElseThrow(() -> new RuntimeException(String.format("Can't find component for this class %s.", componentClass.getName())));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (appComponentsByName.containsKey(componentName)) {
            return (C) appComponentsByName.get(componentName);
        } else {
            throw new RuntimeException(String.format("Can't find component for this class %s. ", componentName));
        }
    }



}
