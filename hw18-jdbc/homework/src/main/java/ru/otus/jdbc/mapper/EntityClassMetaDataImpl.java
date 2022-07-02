package ru.otus.jdbc.mapper;

import ru.otus.crm.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{

    private final String className;
    private final Constructor<T> constructor;
    private final List<Field> allFields;

    public EntityClassMetaDataImpl(Class<T> tClass) {
        className = tClass.getName().substring(tClass.getName().lastIndexOf('.') + 1);
        constructor = getDefaultConstructor(tClass);
        allFields = Arrays.asList(tClass.getDeclaredFields());
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return allFields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst().orElseThrow(() -> new RuntimeException("Field Id not present!"));
    }

    @Override
    public List<Field> getAllFields() {
        if (allFields.isEmpty()) {
            return Collections.emptyList();
        }
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return allFields.stream()
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }

    private Constructor<T> getDefaultConstructor(Class<T> tClass) {
        try {
            return tClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Default Constructor not found");
        }
    }
}
