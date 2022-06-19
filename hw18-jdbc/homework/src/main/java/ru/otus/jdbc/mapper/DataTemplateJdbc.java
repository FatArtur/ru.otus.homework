package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                T entity = entityClassMetaData.getConstructor().newInstance();
                while (rs.next()) {
                    entityClassMetaData.getAllFields().forEach(field -> {
                        try {
                            field.setAccessible(true);
                            field.set(entity, rs.getObject(cutStringField(field.getName())));
                        } catch (IllegalAccessException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                return entity;
            } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            List<T> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    T entity = entityClassMetaData.getConstructor().newInstance();
                    entityClassMetaData.getAllFields().forEach(field -> {
                        try {
                            field.setAccessible(true);
                            field.set(entity, rs.getObject(cutStringField(field.getName())));
                            result.add(entity);
                        } catch (IllegalAccessException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            return result;
        }).orElseThrow(() -> new RuntimeException("It's nothing here"));
    }

    @Override
    public long insert(Connection connection, T client) {
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getInsertParameters(client));
    }

    @Override
    public void update(Connection connection, T client) {
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), getInsertParameters(client));
    }

    private String cutStringField(String str){
        return str.substring(str.lastIndexOf('.') + 1);
    }

    private List<Object> getInsertParameters(T client) {
        List<Object> parameterList = new ArrayList<>();
        entityClassMetaData.getFieldsWithoutId().forEach(field -> {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(client);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            parameterList.add(fieldValue);
        });
        return parameterList;
    }
}
