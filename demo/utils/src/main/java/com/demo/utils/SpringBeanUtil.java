package com.demo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by wq on 2017/2/6.
 * @author wq
 */
@Component
public class SpringBeanUtil  implements ApplicationContextAware{
    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    public static  ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 手动获取通过spring mvc注入的bean
     * */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException{
        return  (T) applicationContext.getBean(name);
    }
}
