package com.demo.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by wq on 2017/2/4.
 * @author wq
 */
public interface BaseDao {
    /**
     * 通过sql查询多条数据的返回
     * @param sql 执行sql
     * */
     List<JSONObject> select(String sql);
    /**
     * 通过sql查询多条数据的返回
     * @param sql 执行sql
     * @param jdbcTemplateName  数据源名称
     * */
    List<JSONObject> select(String sql,String jdbcTemplateName);
    /**
     * 通过sql查询一条数据的返回
     * @param sql  执行sql
     * */
    JSONObject selectOne(String sql);
    /**
     * 通过sql查询一条数据的返回
     * @param sql  执行sql
     * @param jdbcTemplateName 数据源名称
     * */
     JSONObject selectOne(String sql,String jdbcTemplateName);
    /**
     * 只执行count的统计语句
     * @param sql  执行sql
     * */
     long count(String sql);
    /**
     * 只执行count的统计语句
     * @param sql  执行sql
     * @param jdbcTemplateName 数据源名称
     * */
     long count(String sql,String jdbcTemplateName);

    /**
     * 新增
     * @param valueObj 更新的对象
     * @param tableName 表名
     * */
    void add(JSONObject valueObj,String tableName);
    /**
     * 新增
     * @param valueObj 更新的对象
     * @param tableName 表名
     * @param jdbcTemplateName 数据源名称
     * */
    void add(JSONObject valueObj,String tableName,String jdbcTemplateName);

    /**
     * 对象更新
     * @param valueObj 更新的对象
     * @param tableName 表名
     * @param keyObj 更新对象的限定
     * */
    void update(JSONObject valueObj,String tableName,JSONObject keyObj);
    /**
     * 对象更新
     * @param valueObj 更新的对象
     * @param tableName 表名
     * @param keyObj 更新对象的限定
     * @param jdbcTemplateName 数据源名称
     * */
    void update(JSONObject valueObj,String tableName,JSONObject keyObj,String jdbcTemplateName);

    /**
     * 执行SQL
     * @param sql 执行的sql
     * */
    void executeSQL(String sql);
    /**
     * 执行SQL
     * @param sql 执行的sql
     * @param jdbcTemplateName 数据源名称
     * */
    void executeSQL(String sql,String jdbcTemplateName);
}
