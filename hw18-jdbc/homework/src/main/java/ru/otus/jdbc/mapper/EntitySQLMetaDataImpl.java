package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData1) {
        entityClassMetaData = entityClassMetaData1;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("SELECT %s, %s FROM %s",
                entityClassMetaData.getIdField().getName().toLowerCase(),
                entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).map(this::cutStringField).sorted().collect(Collectors.joining(", ")),
                entityClassMetaData.getName().toLowerCase());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("SELECT %s, %s FROM %s WHERE %s=?",
                entityClassMetaData.getIdField().getName().toLowerCase(),
                entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).map(this::cutStringField).sorted().collect(Collectors.joining(", ")),
                entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getIdField().getName().toLowerCase());
    }

    @Override
    public String getInsertSql() {
        return String.format("INSERT INTO %s (%s) values (%s)",
                entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).map(this::cutStringField).sorted().collect(Collectors.joining(", ")),
                Collections.nCopies(entityClassMetaData.getFieldsWithoutId().size(), "?").stream().collect(Collectors.joining(", ")));
    }

    @Override
    public String getUpdateSql() {
        return String.format("UPDATE %s SET %s WHERE %s= ?",
                entityClassMetaData.getName().toLowerCase(),
                entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).map(this::cutStringField).sorted().map(s -> s + "= ?").collect(Collectors.joining(", ")),
                entityClassMetaData.getIdField().getName().toLowerCase());
    }

    private String cutStringField(String str) {
        return str.substring(str.lastIndexOf('.') + 1);
    }
}
