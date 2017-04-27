package com.demo.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.demo.dao.BaseDao;
import com.demo.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wq on 2017/1/18.
 */
@Component("baseService")
public class BaseServiceImpl implements BaseService{
    @Autowired
    public BaseDao baseDao_test;
    @Override
    public String service() {
        String sql ="select * from data_test ";
        JSONObject obj =  baseDao_test.selectOne(sql);

        return obj.toString();
    }
}
