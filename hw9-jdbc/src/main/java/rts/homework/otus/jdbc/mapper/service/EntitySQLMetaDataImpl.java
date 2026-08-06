package rts.homework.otus.jdbc.mapper.service;

import rts.homework.otus.jdbc.mapper.EntityClassMetaData;
import rts.homework.otus.jdbc.mapper.EntitySQLMetaData;

import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    private String tableName() {
        return entityClassMetaData.getName().toLowerCase();
    }

    @Override
    public String getSelectAllSql() {
        return "select * from " + tableName();
    }

    @Override
    public String getSelectByIdSql() {
        return "select * from " + tableName() + " where "
                + entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        var columns = entityClassMetaData.getFieldsWithoutId().stream()
                .map(java.lang.reflect.Field::getName)
                .collect(Collectors.joining(", "));
        var placeholders = entityClassMetaData.getFieldsWithoutId().stream()
                .map(f -> "?")
                .collect(Collectors.joining(", "));
        return "insert into " + tableName() + " (" + columns + ") values (" + placeholders + ")";
    }

    @Override
    public String getUpdateSql() {
        var setClause = entityClassMetaData.getFieldsWithoutId().stream()
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));
        return "update " + tableName() + " set " + setClause + " where "
                + entityClassMetaData.getIdField().getName() + " = ?";
    }
}