package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.service.BaseService;
import com.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
//@Controller
@RequestMapping("/ViewController")
public class ViewController {
    @Autowired
    public BaseService baseService;
    @RequestMapping(value = "/view",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public String view(){
        System.out.println("你已通过springMVC进入controller方法。。。。");
        String str ="";
        str = baseService.service();
        JSONObject result = new JSONObject();
        result.put("0",str);
        return new Result(1,"查询成功",result).toString();
    }

//    @RequestMapping(value = "/view2",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
//    public String view2(HttpServletRequest request, HttpServletResponse response){
//        System.out.println("你已通过springMVC进入controller方法。。。。");
//        String str ="";
//        str = baseService.service();
//        JSONObject result = new JSONObject();
//        result.put("0",str);
//        return new Result(1,"查询成功",result).toString();
//    }


}