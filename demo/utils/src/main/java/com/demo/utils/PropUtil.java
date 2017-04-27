package com.demo.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wq on 2017/2/6.
 * @author wq
 * 读取properties文件
 */
public class PropUtil {
    //默认一个配置文件
    private static  Properties prop = loadPropertiesFile("");

    /**
     * 设置读取的文件
     * */
    public static void setloadPropertiesFile(String filepath){
        prop = loadPropertiesFile(filepath);
    }
    private static Properties loadPropertiesFile(String filepath) {
        InputStream in;
        try {
            in = PropUtil.class.getClassLoader().getResourceAsStream(filepath);
            Properties p = new Properties();
            p.load(in);
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getString(String key){
        return getString(key,"");
    }

    public static String getString(String key,String defaultVal){
        if(prop != null)
            return prop.getProperty(key);
        return defaultVal;
    }

    public static Integer getInteger(String key){
        return getInteger(key,0);
    }
    public static Integer getInteger(String key,int defaultVal){
        if(prop != null){
            String intStr = prop.getProperty(key);
            if(intStr != null && !"".equals(intStr.trim())){
                return  Integer.parseInt(intStr);
            }
        }
        return defaultVal;
    }
}
