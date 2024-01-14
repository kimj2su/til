package com.example.di;

import java.util.Arrays;

public class ContainerService {
    public static <T> T getObject(Class<T> classType) {
        T instance = createInstance(classType);
        Arrays.stream(classType.getDeclaredFields())
                .filter(field -> field.getAnnotation(Inject.class) != null)
                .forEach(field -> {
                    Object fieldInstance = createInstance(field.getType());
                    field.setAccessible(true);
                    try {
                        field.set(instance, fieldInstance);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        return instance;
    }

    private static <T> T createInstance(Class<T> classType) {
        try {
            return classType.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
