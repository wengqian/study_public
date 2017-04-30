package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.dao.BaseDao;
import com.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/projectController")
public class ProjectController {
    @Autowired
    private BaseDao baseDao;
    /**
     *          opeartion_type
     * 创建项目：   0
     * 修改        1
     * 删除        2
     * 单查询      3
     * 列查询      4
     *
     * */
    @RequestMapping(value = "/opeartion_project_data",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String opeartion_project_data(String params){
        JSONObject param = dealPage(params);
        JSONObject obj = new JSONObject();
        String opeartion_type = param.getString("opeartion_type");
        String opeartion_usercode = param.getString("opeartion_usercode");

        if("0".equals(opeartion_type)){//新增
            obj.put("project_id", getUUUID());
            obj.put("project_name",param.getString("project_name"));
            obj.put("create_usercode",opeartion_usercode);
            obj.put("create_time",new Date());
            obj.put("is_delete","0");
            baseDao.add(obj,"project_data");
            JSONObject result = new JSONObject();
            result.put("project_obj",obj);
            return new Result(1,"新增成功",result).toString();
        }else if("1".equals(opeartion_type)){//修改
            if(param.getString("project_id")==null || "".equals(param.getString("project_id"))){
                return new Result(0,"project_id不能为空").toString();
            }
            obj.put("update_usercode",opeartion_usercode);
            obj.put("update_time",new Date());
            if(param.getString("project_name")!=null){
                obj.put("project_name",param.getString("project_name")) ;
            }
            if(param.getString("is_delete")!=null){
                obj.put("is_delete",param.getString("is_delete")) ;
            }
            JSONObject keyObj = new JSONObject();
            keyObj.put("project_id",param.getString("project_id"));

            baseDao.update(obj,"project_data",keyObj);
            return new Result(1,"修改成功",null).toString();
        }else if ("2".equals(opeartion_type)){//删除
            if(param.getString("project_id")==null || "".equals(param.getString("project_id"))){
                return new Result(0,"project_id不能为空").toString();
            }
            obj.put("delete_usercode",opeartion_usercode);
            obj.put("delete_time",new Date());
            obj.put("is_delete","1");
            if(param.getString("project_name")!=null){
                obj.put("project_name",param.getString("project_name")) ;
            }
            JSONObject keyObj = new JSONObject();
            keyObj.put("project_id",param.getString("project_id"));

            baseDao.update(obj,"project_data",keyObj);
            return new Result(1,"删除成功",null).toString();
        }else if ("3".equals(opeartion_type)){//获取单个对象
            String project_id = param.getString("project_id");
            String sql ="select a.*,b.username as create_username from project_data a ,sys_user b where a.project_id ='"+project_id+"' " +
                    "and a.is_delete ='0' and a.create_usercode = b.usercode ";
            obj = baseDao.selectOne(sql);
            JSONObject result = new JSONObject();
            result.put("obj",obj);
            return new Result(1,"删除成功",result).toString();
        }else if ("4".equals(opeartion_type)){//获取数组
            String list_sql ="select a.*,b.username as create_username  ";
            String count_sql ="select count(*)  ";
            String public_sql =" from project_data a left Join sys_user b on b.usercode = a.create_usercode " +
                    " where a.is_delete ='0'  and (a.create_usercode ='"+opeartion_usercode+"' " +
                    " or EXISTS(select 1 from project_vist_power c where a.project_id = c.project_id and c.usercode ='"+opeartion_usercode+"') ) ";
            if(param.getString("project_name")!=null && !"".equals(param.getString("project_name"))){
                public_sql +=" and a.project_name like '%"+param.getString("project_name")+"%' ";
            }
            if(param.getString("create_usercode")!=null && !"".equals(param.getString("create_usercode"))){
                public_sql +=" and a.create_usercode = '"+param.getString("create_usercode")+"' ";
            }

            list_sql +=public_sql+" order by create_time desc ";
            count_sql+=public_sql;
            if(param.getString("paging_sql")!=null && !"".equals(param.getString("paging_sql"))){
                list_sql+=param.getString("paging_sql");
            }
            long count = baseDao.count(count_sql);
            List<JSONObject> list;
            if(count ==0){
                list = new ArrayList<JSONObject>();
            }else{
                list = baseDao.select(list_sql);
            }

            JSONObject result = new JSONObject();
            result.put("count",count);
            result.put("list",list);

            return new Result(1,"查询列成功",result).toString();
        }

        return new Result(0,"无该项操作",null).toString();
    }

    /**
     * 共享人员的设置
     * */
    @RequestMapping(value = "/opeartion_project_vist_power",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String opeartion_project_vist_power(String params){
        JSONObject param = dealPage(params);
        JSONObject obj = new JSONObject();
        String opeartion_type = param.getString("opeartion_type");

        if("0".equals(opeartion_type)){//新增,修改,删除贡献人员
            if(param.getString("usercode_arr")==null || "".equals(param.getString("usercode_arr"))){
                return new Result(0,"共享人员名单不能为空",null).toString();
            }
            String[] usercode_arr = param.getString("usercode_arr").split(",");
            String project_id = param.getString("project_id");
            try {
                String delete_sql ="delete from project_vist_power where project_id ='"+project_id+"' ";
                baseDao.executeSQL(delete_sql);
                for(int i=0;i<usercode_arr.length;i++){
                    obj = new JSONObject();
                    obj.put("project_id",project_id);
                    obj.put("usercode",usercode_arr[i]);
                    baseDao.add(obj,"project_vist_power");
                }
            }catch (Exception e){

            }
            //查询新的共享人员
            param.put("opeartion_type","1");
            return opeartion_project_vist_power(param.toJSONString());

        }else if("1".equals(opeartion_type)){//查询共享人员
            String project_id = param.getString("project_id");
            String sql ="select b.*,a.username from sys_user a,project_vist_power b where a.userocde =b.usercode and project_id ='"+project_id+"' ";
            List<JSONObject> share_user_list = baseDao.select(sql);
            JSONObject result = new JSONObject();
            result.put("list",share_user_list);
            return new Result(1,"操作成功",result).toString();

        }
        return new Result(0,"无该项操作",null).toString();
    }

    /**
     * 项目交流版
     * 新增       0
     * 修改       1
     * 删除       2
     * 查询列     4
     * */
    @RequestMapping(value = "/opeartion_project_ac_board",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String opeartion_project_ac_board(String params){
        JSONObject param = dealPage(params);
        JSONObject obj = new JSONObject();
        String opeartion_type = param.getString("opeartion_type");
        String opeartion_usercode = param.getString("opeartion_usercode");

        if("0".equals(opeartion_type)){//新增
            obj.put("id",getUUUID());
            obj.put("project_id",param.getString("project_id"));
            obj.put("send_content",param.getString("send_content"));
            obj.put("send_time",new Date());
            obj.put("send_usercode",opeartion_usercode);
            obj.put("is_delete","0");
            baseDao.add(obj,"project_ac_board");
            return new Result(1,"操作成功",obj).toString();
        }else if("1".equals(opeartion_type)){//修改
            obj.put("send_content",param.getString("send_content"));
            JSONObject keyObj = new JSONObject();
            keyObj.put("id",param.getString("id"));
            baseDao.update(obj,"project_ac_board",keyObj);
            return new Result(1,"操作成功",obj).toString();
        }else if("2".equals(opeartion_type)){//删除
            obj.put("is_delete","1");
            obj.put("delete_time",new Date());
            obj.put("delete_usercode",opeartion_usercode);
            JSONObject keyObj = new JSONObject();
            keyObj.put("id",param.getString("id"));
            baseDao.update(obj,"project_ac_board",keyObj);
            return new Result(1,"操作成功",obj).toString();
        }else if("3".equals(opeartion_type)){//查询单个


        }else if("4".equals(opeartion_type)){//查询列
            String project_id = param.getString("project_id");

            String list_sql ="select a.*,b.username as send_username  ";
            String count_sql ="select count(*)  ";
            String public_sql =" from project_ac_board a left Join sys_user b on b.usercode = a.send_usercode " +
                    " where a.is_delete ='0'  and a.project_id='"+project_id+"' " ;
            if(param.getString("send_content")!=null &&!"".equals(param.getString("send_content"))){
                public_sql +=" and a.send_content like '%"+param.getString("send_content")+"%' ";
            }
            if(param.getString("send_usercode")!=null && !"".equals(param.getString("send_usercode"))){
                public_sql +=" and a.send_usercode = '"+param.getString("send_usercode")+"' ";
            }

            list_sql +=public_sql+" order by send_time desc ";
            count_sql+=public_sql;
            if(param.getString("paging_sql")!=null && !"".equals(param.getString("paging_sql"))){
                list_sql+=param.getString("paging_sql");
            }
            long count = baseDao.count(count_sql);
            List<JSONObject> list;
            if(count ==0){
                list = new ArrayList<JSONObject>();
            }else{
                list = baseDao.select(list_sql);
            }

            JSONObject result = new JSONObject();
            result.put("count",count);
            result.put("list",list);

            return new Result(1,"查询列成功",result).toString();

        }
        return new Result(0,"无该项操作",null).toString();
    }
    /**
     * 风险点管控
     * 发送       0
     * 删除       2
     * 查询列     4
     *
     * */
    @RequestMapping(value = "/opeartion_risk_control",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String opeartion_risk_control(String params){
        JSONObject param = dealPage(params);
        JSONObject obj = new JSONObject();
        String opeartion_type = param.getString("opeartion_type");
        String opeartion_usercode = param.getString("opeartion_usercode");
        if("0".equals(opeartion_type)){//发送(新增)
            obj.put("id",getUUUID());
            obj.put("project_id",param.getString("project_id"));
            obj.put("send_content",param.getString("send_content"));
            obj.put("send_time",new Date());
            obj.put("send_usercode",opeartion_usercode);
            obj.put("is_delete","0");
            obj.put("receive_user_json",param.getString("receive_user_json"));//{11:{usercode:'11',username:'wengqian'},12:{usercode:'12',username:'wengqian2'}}
            baseDao.add(obj,"risk_control");
            //需要发送 start

            //需要发送end
            return new Result(1,"操作成功",obj).toString();
        }else if("1".equals(opeartion_type)){//修改

        }else if("2".equals(opeartion_type)){//删除
            obj.put("is_delete","1");
            obj.put("delete_time",new Date());
            obj.put("delete_usercode",opeartion_usercode);
            JSONObject keyObj = new JSONObject();
            keyObj.put("id",param.getString("id"));
            baseDao.update(obj,"risk_control",keyObj);
            return new Result(1,"操作成功",obj).toString();
        }else if("3".equals(opeartion_type)){//查询单个


        }else if("4".equals(opeartion_type)){//查询列

            String project_id = param.getString("project_id");

            String list_sql ="select a.*,b.username as send_username  ";
            String count_sql ="select count(*)  ";
            String public_sql =" from risk_control a left Join sys_user b on b.usercode = a.send_usercode " +
                    " where a.is_delete ='0'  and a.project_id='"+project_id+"' " ;
            if(param.getString("send_content")!=null && !"".equals(param.getString("send_content"))){
                public_sql +=" and a.send_content like '%"+param.getString("send_content")+"%' ";
            }
            if(param.getString("send_usercode")!=null && !"".equals(param.getString("send_usercode"))){//发送人员
                public_sql +=" and a.send_usercode = '"+param.getString("send_usercode")+"' ";
            }

            list_sql +=public_sql+" order by send_time desc ";
            count_sql+=public_sql;
            if(param.getString("paging_sql")!=null &&!"".equals(param.getString("paging_sql"))){
                list_sql+=param.getString("paging_sql");
            }
            long count = baseDao.count(count_sql);
            List<JSONObject> list;
            if(count ==0){
                list = new ArrayList<JSONObject>();
            }else{
                list = baseDao.select(list_sql);
            }

            JSONObject result = new JSONObject();
            result.put("count",count);
            result.put("list",list);

            return new Result(1,"查询列成功",result).toString();

        }
        return new Result(0,"无该项操作",null).toString();
    }

    /**
     * 各种类型的留言板
     *  新增
     *  修改
     *  删除
     *  查询单
     *  查询列
     * */
    @RequestMapping(value = "/opeartion_project_multiple_message_board",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String opeartion_project_multiple_message_board(String params){

        JSONObject param = dealPage(params);
        JSONObject obj = new JSONObject();
        String opeartion_type = param.getString("opeartion_type");
        String opeartion_usercode = param.getString("opeartion_usercode");
        if("0".equals(opeartion_type)){//新增
            obj.put("id",getUUUID());
            obj.put("project_id",param.getString("project_id"));
            obj.put("send_content",param.getString("send_content"));
            obj.put("send_time",new Date());
            obj.put("send_usercode",opeartion_usercode);
            obj.put("type",param.getString("type"));//类型
            obj.put("type1",param.getString("type_name"));//类型名称
            obj.put("type",param.getString("type1"));//类型1
            obj.put("type1",param.getString("type1_name"));//类型1
            obj.put("is_delete","0");
            baseDao.add(obj,"project_multiple_message_board");
            return new Result(1,"操作成功",obj).toString();
        }else if("1".equals(opeartion_type)){//修改
            obj.put("send_content",param.getString("send_content"));
            JSONObject keyObj = new JSONObject();
            keyObj.put("id",param.getString("id"));
            baseDao.update(obj,"project_multiple_message_board",keyObj);
        }else if("2".equals(opeartion_type)){//删除
            obj.put("is_delete","1");
            obj.put("delete_time",new Date());
            obj.put("delete_usercode",opeartion_usercode);
            JSONObject keyObj = new JSONObject();
            keyObj.put("id",param.getString("id"));
            baseDao.update(obj,"project_multiple_message_board",keyObj);
            return new Result(1,"操作成功",obj).toString();
        }else if("3".equals(opeartion_type)){//查询单个
            String id = param.getString("id");
            String sql ="select a.*,b.username as send_username  from project_multiple_message_board a " +
                    "left Join sys_user b on b.usercode = a.send_usercode  where a.is_delete ='0' and a.id ='"+id+"'";
            obj = baseDao.selectOne(sql);
            JSONObject result = new JSONObject();
            result.put("obj",obj);
        }else if("4".equals(opeartion_type)){//查询列
            String project_id = param.getString("project_id");

            String list_sql ="select a.*,b.username as send_username  ";
            String count_sql ="select count(*)  ";
            String public_sql =" from project_multiple_message_board a left Join sys_user b on b.usercode = a.send_usercode " +
                    " where a.is_delete ='0'  and a.project_id='"+project_id+"' " ;

            if(param.getString("type")!=null &&!"".equals(param.getString("type"))){//类型
                public_sql +=" and a.type = '"+param.getString("type")+"' ";
            }
            if(param.getString("type1")!=null &&!"".equals(param.getString("type1"))){//类型1
                public_sql +=" and a.type1 = '"+param.getString("type1")+"' ";
            }
            if(param.getString("send_content")!=null &&!"".equals(param.getString("send_content"))){
                public_sql +=" and a.send_content like '%"+param.getString("send_content")+"%' ";
            }
            if(param.getString("send_usercode")!=null &&!"".equals(param.getString("send_usercode"))){//发送人员
                public_sql +=" and a.send_usercode = '"+param.getString("send_usercode")+"' ";
            }

            list_sql +=public_sql+" order by send_time desc ";
            count_sql+=public_sql;
            if(param.getString("paging_sql")!=null && !"".equals(param.getString("paging_sql"))){
                list_sql+=param.getString("paging_sql");
            }
            long count = baseDao.count(count_sql);
            List<JSONObject> list;
            if(count ==0){
                list = new ArrayList<JSONObject>();
            }else{
                list = baseDao.select(list_sql);
            }

            JSONObject result = new JSONObject();
            result.put("count",count);
            result.put("list",list);

            return new Result(1,"查询列成功",result).toString();

        }

        return new Result(0,"无该项操作",null).toString();
    }

    /**
     * 操作用户
     * 新增        0
     * 修改        1
     * 删除        2
     * 查询单      3
     * 查询列      4
     * */
    @RequestMapping(value = "/opeartion_sys_user",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String opeartion_sys_user(String params){

        JSONObject param = dealPage(params);
        JSONObject obj = new JSONObject();
        String opeartion_type = param.getString("opeartion_type");
        String opeartion_usercode = param.getString("opeartion_usercode");
        if("0".equals(opeartion_type)){//新增
            String sql ="select count(*) from sys_user where usercode ='"+param.getString("usercode")+"' ";
            long count  = baseDao.count(sql);
            if(count>0){
                return new Result(0,"该账户以及存在",obj).toString();
            }
            obj.put("usercode",param.getString("usercode"));
            obj.put("username",param.getString("username"));
            obj.put("create_time",new Date());
            obj.put("is_delete","0");
            baseDao.add(obj,"sys_user");
            return new Result(1,"操作成功",obj).toString();
        }else if("1".equals(opeartion_type)){//修改
            obj.put("username",param.getString("username"));
            JSONObject keyObj = new JSONObject();
            keyObj.put("usercode",param.getString("usercode"));
            baseDao.update(obj,"sys_user",keyObj);
        }else if("2".equals(opeartion_type)){//删除
            obj.put("is_delete","1");
            JSONObject keyObj = new JSONObject();
            keyObj.put("usercode",param.getString("usercode"));
            baseDao.update(obj,"sys_user",keyObj);
            return new Result(1,"操作成功",obj).toString();
        }else if("3".equals(opeartion_type)){//查询单个
            String usercode = param.getString("usercode");
            String sql ="select a.* from sys_user a where a.is_delete='0' and a.usercode ='"+usercode+"' " ;
            obj = baseDao.selectOne(sql);
            if(obj==null){
                return new Result(0,"不存在该账户或已删除").toString();
            }
            JSONObject result = new JSONObject();
            result.put("obj",obj);
            return new Result(1,"查询单个成功",result).toString();
        }else if("4".equals(opeartion_type)){//查询列
            String list_sql ="select a.* ";
            String count_sql ="select count(*)  ";
            String public_sql =" from sys_user a where a.is_delete ='0'  " ;

            if(param.getString("username")!=null && !"".equals(param.getString("username"))){
                public_sql +=" and a.username like '%"+param.getString("username")+"%' ";
            }

            list_sql +=public_sql+" order by create_time desc ";
            count_sql+=public_sql;
            if(param.getString("paging_sql")!=null &&!"".equals(param.getString("paging_sql"))){
                list_sql+=param.getString("paging_sql");
            }
            long count = baseDao.count(count_sql);
            List<JSONObject> list;
            if(count ==0){
                list = new ArrayList<JSONObject>();
            }else{
                list = baseDao.select(list_sql);
            }

            JSONObject result = new JSONObject();
            result.put("count",count);
            result.put("list",list);

            return new Result(1,"查询列成功",result).toString();

        }else if("5".equals(opeartion_type)){//login
            String usercode = param.getString("usercode");
            String password = param.getString("password");
            String sql ="select a.* from sys_user a where a.is_delete='0' and a.usercode ='"+usercode+"' and a.password ='"+password+"'" ;
            obj = baseDao.selectOne(sql);
            if(obj==null){
                return new Result(0,"账户或者密码输入错误").toString();
            }
            JSONObject result = new JSONObject();
            result.put("user_obj",obj);
            return new Result(1,"查询单个成功",result).toString();
        }

        return new Result(0,"无该项操作",null).toString();
    }
    public String getUUUID(){
       return UUID.randomUUID()+"";
    }
    /**
     * 处理分页
     * */
    public JSONObject dealPage(String param){
        JSONObject param_obj = new JSONObject();
        if(param!=null && !"".equals(param)){
            param_obj = JSONObject.parseObject(param);
        }
        String page = param_obj.getString("page");
        String pagesize = param_obj.getString("pagesize");
        if(page!=null && !"".equals(page)
                && pagesize!=null && !"".equals(pagesize)){
            Integer page_int = Integer.parseInt(page);
            Integer pagesieze_int = Integer.parseInt(pagesize);
            int page_start = (page_int-1)*pagesieze_int;
            int page_end = pagesieze_int;
            String paging_sql = " limit "+page_end+" OFFSET "+page_start+"  ";
            param_obj.put("paging_sql",paging_sql);
        }
        return param_obj;
    }
}