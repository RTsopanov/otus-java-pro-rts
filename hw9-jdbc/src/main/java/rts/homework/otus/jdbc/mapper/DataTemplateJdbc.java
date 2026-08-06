package rts.homework.otus.jdbc.mapper;

import rts.otus.core.repository.DataTemplate;
import rts.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
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
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                this::readOne
        );
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectAllSql(),
                List.of(),
                this::readAll
        ).orElseGet(List::of);
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Object> params = new ArrayList<>();
        for (Field field : entityClassMetaData.getFieldsWithoutId()) {
            params.add(getValue(field, client));
        }
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
    }

    @Override
    public void update(Connection connection, T client) {
        List<Object> params = new ArrayList<>();
        for (Field field : entityClassMetaData.getFieldsWithoutId()) {
            params.add(getValue(field, client));
        }
        params.add(getValue(entityClassMetaData.getIdField(), client));
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
    }

    private T readOne(ResultSet rs) {
        try {
            return rs.next() ? createInstance(rs) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<T> readAll(ResultSet rs) {
        List<T> result = new ArrayList<>();
        try {
            while (rs.next()) {
                result.add(createInstance(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private T createInstance(ResultSet rs) {
        try {
            T instance = entityClassMetaData.getConstructor().newInstance();
            for (Field field : entityClassMetaData.getAllFields()) {
                field.setAccessible(true);
                field.set(instance, rs.getObject(field.getName()));
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create instance of " + entityClassMetaData.getName(), e);
        }
    }

    private Object getValue(Field field, T objectData) {
        try {
            field.setAccessible(true);
            return field.get(objectData);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}