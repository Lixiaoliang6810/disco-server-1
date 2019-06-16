package com.miner.disco.mybatis.support;

import org.apache.ibatis.annotations.*;

/**
 * @author Created by lubycoder@163.com 2018/11/15
 */
public interface BasicMapper<T> {

    @InsertProvider(method = "insert", type = BasicSqlProvider.class)
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void insert(T t);

    @SelectProvider(method = "queryByPrimaryKey", type = BasicSqlProvider.class)
    T queryByPrimaryKey(@Param("id") Long id);

    @UpdateProvider(method = "updateByPrimaryKey", type = BasicSqlProvider.class)
    void updateByPrimaryKey(T t);

}
