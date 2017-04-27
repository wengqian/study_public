package com.demo.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wq on 2017/2/6.
 * 数据源工具
 */
//@Component("dataPool")
public class DataSourceUtil {
    //基础数据源
    public static String baseDataSource  = "baseJdbcTemplate";
    //数据源
    public static Map<String,DruidDataSource> dataPool;

    public static JdbcTemplate baseJdbcTemplate  ;

    public static  JdbcTemplate getJdbcTemplate(String dataSorcename)  {
        if(dataPool==null){
            DataSourceUtil.initDataPool();
        }
        if(baseDataSource.equals(dataSorcename)){//基础数据库
            if(baseJdbcTemplate == null){
                baseJdbcTemplate = (JdbcTemplate)SpringBeanUtil.getBean(baseDataSource);
            }
            return baseJdbcTemplate;
        }
        //配置的数据库
        return new JdbcTemplate(dataPool.get(dataSorcename));
    }

    /**
     * 获取数据源
     * */
    public static  DruidDataSource getDataSource1(String dataSorcename)  {
        if(dataPool==null){
            DataSourceUtil.initDataPool();
        }
        return dataPool.get(dataSorcename);
    }
    /**
     * 初始化数据池
     * */
    public static void initDataPool()  {
        dataPool = new HashMap<String, DruidDataSource>();
//        dataPool.put("baseJdbcTemplate", (JdbcTemplate)SpringBeanUtil.getBean("baseJdbcTemplate"));
    }
    /**
     * 创建新的数据源连接,并且让如到数据池中
     * */
    public static DruidDataSource createDruidDataSource(JSONObject dataSorucejson){
        try {
            DruidDataSource druidDataSource = new DruidDataSource();
            //启动包
            if(dataSorucejson.getString("driver_class_name") !=null && !dataSorucejson.getString("driver_class_name").isEmpty()){
                druidDataSource.setDriverClassName(dataSorucejson.getString("driver_class_name"));
            }
            //数据库用户
            if(dataSorucejson.getString("username") !=null && !dataSorucejson.getString("username").isEmpty()){
                druidDataSource.setUsername(dataSorucejson.getString("username"));
            }
            //数据库密码
            if(dataSorucejson.getString("password") !=null && !dataSorucejson.getString("password").isEmpty()){
                druidDataSource.setPassword(dataSorucejson.getString("password"));
            }
            //数据库对应url写法
            if(dataSorucejson.getString("jdbcurl") !=null && !dataSorucejson.getString("jdbcurl").isEmpty()){
                druidDataSource.setUrl(dataSorucejson.getString("jdbcurl"));
            }
            //初始化时建立物理连接的个数
            if(dataSorucejson.getString("initial_size") !=null && !dataSorucejson.getString("initial_size").isEmpty()){
                druidDataSource.setInitialSize(dataSorucejson.getInteger("initial_size"));
            }
            //最小连接数
            if(dataSorucejson.getString("min_idle") !=null && !dataSorucejson.getString("min_idle").isEmpty()){
                druidDataSource.setMinIdle(dataSorucejson.getInteger("min_idle"));
            }
            //最大连接数
            if(dataSorucejson.getString("max_active") !=null && !dataSorucejson.getString("max_active").isEmpty()){
                druidDataSource.setMaxActive(dataSorucejson.getInteger("max_active"));
            }
            // 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
            if(dataSorucejson.getString("filters") !=null && !dataSorucejson.getString("filters").isEmpty()){
                druidDataSource.setFilters(dataSorucejson.getString("filters"));
            }
            //是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
            if(dataSorucejson.getString("pool_prepared_statements") !=null && !dataSorucejson.getString("pool_prepared_statements").isEmpty()){
                druidDataSource.setPoolPreparedStatements(dataSorucejson.getBoolean("pool_prepared_statements"));
            }
            return druidDataSource;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args){
//        try{
//            PropUtil.setloadPropertiesFile("jdbc/jdbc.properties");
//            System.out.println( PropUtil.getString("datasorce_jdbc_url"));
//            //需要指定是那种数据源c
//
//            DruidDataSource druidDataSource = new DruidDataSource();
//            druidDataSource.setDriverClassName("org.postgresql.Driver");
//            druidDataSource.setUsername("test");
//            druidDataSource.setPassword("test");
//            druidDataSource.setUrl("jdbc:postgresql://localhost:5432/test?searchpath=test");
//            druidDataSource.setInitialSize(5);
//            druidDataSource.setMinIdle(1);
//            druidDataSource.setMaxActive(10); // 启用监控统计功能
//            druidDataSource.setFilters("stat");// for mysql
//            druidDataSource.setPoolPreparedStatements(false);
//
//            JdbcTemplate jdbcTemplate = new JdbcTemplate(druidDataSource);
//            String sql ="select * from data_source ";
//            List list = jdbcTemplate.queryForList(sql);
//            JSONObject result = new JSONObject();
//            for(int i=0;i<list.size() && i< 1;i++){
//                result = new JSONObject((Map) list.get(i));
//            }
//            System.out.print(result.toJSONString());
//
//            JdbcTemplate jdbcTemplate1 = new JdbcTemplate(DataSourceUtil.createDruidDataSource(result));
//            dataPool.put(result.getString("datasource_name"),jdbcTemplate1);
//
//            sql ="select * from data_test ";
//            list = jdbcTemplate1.queryForList(sql);
//            for(int i=0;i<list.size() && i< 1;i++){
//                result = new JSONObject((Map) list.get(i));
//            }
//            System.out.print(result.toJSONString());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

}
//
//    配置	缺省值	说明
//        name	 	配置这个属性的意义在于，如果存在多个数据源，监控的时候可以通过名字来区分开来。
//        如果没有配置，将会生成一个名字，格式是："DataSource-" + System.identityHashCode(this)
//        jdbcUrl	 	连接数据库的url，不同数据库不一样。例如：
//        mysql : jdbc:mysql://10.20.153.104:3306/druid2
//        oracle : jdbc:oracle:thin:@10.20.149.85:1521:ocnauto
//        username	 	连接数据库的用户名
//        password	 	连接数据库的密码。如果你不希望密码直接写在配置文件中，可以使用ConfigFilter。详细看这里：https://github.com/alibaba/druid/wiki/%E4%BD%BF%E7%94%A8ConfigFilter
//        driverClassName	根据url自动识别	这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName(建议配置下)
//        initialSize	0	初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
//        maxActive	8	最大连接池数量
//        maxIdle	8	已经不再使用，配置了也没效果
//        minIdle	 	最小连接池数量
//        maxWait	 	获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
//        poolPreparedStatements	false	是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
//        maxOpenPreparedStatements	-1	要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
//        validationQuery	 	用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
//        testOnBorrow	true	申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
//        testOnReturn	false	归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
//        testWhileIdle	false	建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
//        timeBetweenEvictionRunsMillis	 	有两个含义：
//        1) Destroy线程会检测连接的间隔时间2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
//        numTestsPerEvictionRun	 	不再使用，一个DruidDataSource只支持一个EvictionRun
//        minEvictableIdleTimeMillis
//        connectionInitSqls	 	物理连接初始化的时候执行的sql
//        exceptionSorter	根据dbType自动识别	当数据库抛出一些不可恢复的异常时，抛弃连接
//        filters	 	属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
//        监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
//        proxyFilters
//        类型是List<com.alibaba.druid.filter.Filter>，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系