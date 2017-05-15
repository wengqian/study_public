/**
 * Created by huixie on 2017/4/29.
 */
var visit_url=targetOrigin;
/*
*
* 创建项目：   0
 * 修改        1
 * 删除        2
 * 单查询      3
 * 列查询      4
* */
var ProjectController ={
    opeartion_project_data:visit_url+'/projectController/opeartion_project_data.do',//项目
    opeartion_project_vist_power:visit_url+'/projectController/opeartion_project_vist_power.do',//访问人员
    opeartion_project_ac_board:visit_url+'/projectController/opeartion_project_ac_board.do',//留言板
    multiple_message_board_exportWord:visit_url+'/projectController/multiple_message_board_exportWord.do',//导出,id
    opeartion_risk_control:visit_url+'/projectController/opeartion_risk_control.do',//发送短信
    opeartion_project_multiple_message_board:visit_url+'/projectController/opeartion_project_multiple_message_board.do',//多项留言板
    opeartion_sys_user:visit_url+'/projectController/opeartion_sys_user.do'//操作用户

}

