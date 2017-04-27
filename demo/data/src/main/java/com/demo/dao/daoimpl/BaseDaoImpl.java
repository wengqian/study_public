package com.demo.dao.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.demo.dao.BaseDao;
import com.demo.utils.DataSourceUtil;
import com.demo.utils.TableFieldUtil;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wq on 2017/2/4.
 * @author wq
 */
@Component("baseDaoImpl")
public class BaseDaoImpl implements BaseDao{
    /**
     * baseJdbcTemplate 基础数据源
     * **/
//    @Resource(name ="baseJdbcTemplate")
//    public JdbcTemplate baseJdbcTemplate;

    @Override
    public List<JSONObject> select(String sql) {
        return select(sql,DataSourceUtil.baseDataSource);
    }

    @Override
    public List<JSONObject> select(String sql, String jdbcTemplateName) {
        List list = DataSourceUtil.getJdbcTemplate(jdbcTemplateName).queryForList(sql);
        List<JSONObject> result = new ArrayList<JSONObject>();
        for(int i=0;i<list.size();i++){
            result.add(new JSONObject((Map) list.get(i)));
        }
        return result;
    }

    @Override
    public JSONObject selectOne(String sql) {
        return selectOne(sql,DataSourceUtil.baseDataSource);
    }

    @Override
    public JSONObject selectOne(String sql, String jdbcTemplateName) {
        List list = DataSourceUtil.getJdbcTemplate(jdbcTemplateName).queryForList(sql);
        JSONObject result = new JSONObject();
        for(int i=0;i<list.size() && i< 1;i++){
            result = new JSONObject((Map) list.get(i));
        }
        return result;
    }

    @Override
    public long count(String sql) {
        return count(sql,DataSourceUtil.baseDataSource);
    }

    @Override
    public long count(String sql, String jdbcTemplateName) {
        return DataSourceUtil.getJdbcTemplate(jdbcTemplateName).queryForObject(sql,Long.class);
    }

    @Override
    public void add(JSONObject valueObj,String tableName) {
        add(valueObj,tableName,DataSourceUtil.baseDataSource);
    }

    @Override
    public void add(JSONObject valueObj,String tableName, String jdbcTemplateName) {
        if(valueObj ==null ){
            return;
        }

        valueObj = getValueObj(valueObj,tableName,jdbcTemplateName);

        if(valueObj==null){
            return;
        }
        String sql ="insert into "+tableName+" (";
        Object[] obj = new Object[valueObj.size()];
        int j =0;
        for(String key:valueObj.keySet()){
            sql = sql+key +",";
            obj[j] = valueObj.get(key);
            j++;
        }
        sql = sql.substring(0,sql.length()-1);
        sql =sql +") values (";
        for(int i=0;i<valueObj.size();i++){
            sql =sql+"?,";
        }
        sql = sql.substring(0,sql.length()-3);

        DataSourceUtil.getJdbcTemplate(jdbcTemplateName).update(sql,obj);
    }

    @Override
    public void update(JSONObject valueObj,String tableName, JSONObject keyObj) {
        update(valueObj,tableName,keyObj,DataSourceUtil.baseDataSource);
    }

    @Override
    public void update(JSONObject valueObj,String tableName, JSONObject keyObj, String jdbcTemplateName) {
        if(valueObj==null || keyObj==null){
            return;
        }

        valueObj = getValueObj(valueObj,tableName,jdbcTemplateName);

        if(valueObj==null){
            return;
        }

        String sql ="update "+tableName+"set ";
        Object[] obj = new Object[valueObj.size()+keyObj.size()];
        int j =0;
        for(String key:valueObj.keySet()){
            sql = sql+key +"=?,";
            obj[j] = valueObj.get(key);
            j++;
        }
        sql = sql.substring(0,sql.length()-1);
        sql =sql +" where ";
        for(String key:keyObj.keySet()){
            sql =sql +" "+key+"=? and";
            obj[j] = keyObj.get(key);
            j++;
        }
        sql = sql.substring(0,sql.length()-3);

        DataSourceUtil.getJdbcTemplate(jdbcTemplateName).update(sql,obj);
    }

    @Override
    public void executeSQL(String sql) {
        executeSQL(sql,DataSourceUtil.baseDataSource);
    }

    @Override
    public void executeSQL(String sql, String jdbcTemplateName) {
         DataSourceUtil.getJdbcTemplate(jdbcTemplateName).update(sql);
    }



    public String[] getTableField(String tableName,String jdbcTemplateName){
//        tablename = tablename.toLowerCase();
        if(TableFieldUtil.tableFieldMap.get("tablename") == null){
            String sql ="select * from "+tableName;
            String [] strArray;
            try {
                PreparedStatement pst = DataSourceUtil.getJdbcTemplate(jdbcTemplateName).getDataSource().getConnection().prepareStatement(sql);
                ResultSetMetaData rsmd = pst.executeQuery().getMetaData();
                int cc = rsmd.getColumnCount();
                strArray = new String[cc];
                for(int i=0;i<cc;i++){
                    String name = rsmd.getColumnName(i+1);
//                String name = rsmd.getColumnName(i+1).toLowerCase();
                    strArray[i] = name;
                }

                TableFieldUtil.tableFieldMap.put(tableName,strArray);
            }catch (Exception e){
                TableFieldUtil.tableFieldMap.remove(tableName);
//            e.printStackTrace();
            }finally {

            }
        }
        return TableFieldUtil.tableFieldMap.get(tableName);
    }

    /**
     * 该方法用于处理存储与数据库中的数据对象
     * */
    public JSONObject getValueObj(JSONObject valueObj,String tableName,String jdbcTemplateName){
        String[] tableFieldArr = getTableField(tableName,jdbcTemplateName);
        if(tableFieldArr== null){
            return null;
        }
        JSONObject obj = new JSONObject();
        String tablefield;
        for(int i=0;i<tableFieldArr.length;i++){
            tablefield = tableFieldArr[i];
            for(String key:valueObj.keySet()){
                if(tablefield.equals(key)){
                    obj.put(key,valueObj.get(key));
                    break;
                }
            }
        }
        return  obj;
    }

}
