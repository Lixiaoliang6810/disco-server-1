package com.miner.disco.mybatis.support;

import com.google.common.base.CaseFormat;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/11/15
 */
public class BasicSqlProvider<T> {

//    @Value("${mybatis.table-prefix}")
    private String tablePrefix = "ds_";

    public String insert(ProviderContext context, T bean) {
        Class<?>[] generics = getMapperGenerics(context.getMapperType());
        Class<?> modelClass = generics[0];
        String tableName = tablePrefix + humpToLine(modelClass.getSimpleName());
        List<Field> fields = getFields(modelClass);
        SQL sql = new SQL();
        sql.INSERT_INTO(tableName);
        fields.forEach((field -> {
            field.setAccessible(true);
            String column = String.format("`%s`",field.getName());
            if (column.equals("`id`")) return;
            sql.VALUES(humpToLine(column), String.format("#{%s}", field.getName()));
        }));
        return sql.toString();
    }

    public String queryByPrimaryKey(ProviderContext context, Serializable id) {
        Class<?>[] generics = getMapperGenerics(context.getMapperType());
        Class<?> modelClass = generics[0];
        String tableName = tablePrefix + humpToLine(modelClass.getSimpleName());
        List<Field> fields = getFields(modelClass);
        SQL sql = new SQL();
        fields.forEach(field -> {
            field.setAccessible(true);
            String column = String.format("`%s`",field.getName());
            sql.SELECT(humpToLine(column));
        });

        sql.FROM(tableName);
        sql.WHERE("id = #{id}");
        return sql.toString();
    }

    public String updateByPrimaryKey(ProviderContext context, T bean) {
        Class clz = bean.getClass();
        String tableName = tablePrefix + humpToLine(clz.getSimpleName());
        List<Field> fields = getFields(clz);
        SQL sql = new SQL();
        sql.UPDATE(tableName);
        fields.forEach((field -> {
            field.setAccessible(true);
            String column = String.format("`%s`",field.getName());
            if (column.equals("`id`")) return;
            try {
                if (field.get(bean) != null) {
                    sql.SET(String.format("%s = #{%s}", humpToLine(column), field.getName()));

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }));
        sql.WHERE("id = #{id}");
        return sql.toString();
    }

    private List<Field> getFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if ("serialVersionUID" .equals(field.getName()))
                continue;
            fieldList.add(field);
        }
        return fieldList;
    }

    public static Class<?>[] getMapperGenerics(Class<?> mapperClass) {
        Type[] types = mapperClass.getGenericInterfaces();
        for (Type type : types) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (BasicMapper.class != parameterizedType.getRawType())
                continue;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            Class<?>[] generics = new Class[typeArguments.length];
            for (int i = 0; i < typeArguments.length; i++)
                generics[i] = (Class<?>) typeArguments[i];
            return generics;
        }
        return null;
    }

    public static Field[] getModelField(Class<?> modelClass) {
        List<Field> fields = new ArrayList<>();
        Field[] declaredFields = modelClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if ("serialVersionUID" .equals(field.getName()))
                continue;
            fields.add(field);
        }
        return fields.toArray(new Field[0]);
    }

    public static String lineToHump(String str) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
    }

    public static String humpToLine(String str) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }

}
