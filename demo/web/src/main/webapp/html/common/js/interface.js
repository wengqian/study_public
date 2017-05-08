/**
 * Created by huixie on 2017/4/29.
 */
var webneturl=window.location.href;
// alert(url);
var visit_url="http://localhost:8080/";
visit_url = webneturl.substring(0,webneturl.lastIndexOf("／html"));
var ProjectController ={
    opeartion_project_data:visit_url+'/projectController/opeartion_project_data.do',
    opeartion_project_vist_power:visit_url+'/projectController/opeartion_project_vist_power.do',
    opeartion_project_ac_board:visit_url+'/projectController/opeartion_project_ac_board.do',
    opeartion_risk_control:visit_url+'/projectController/opeartion_risk_control.do',
    opeartion_project_multiple_message_board:visit_url+'/projectController/opeartion_project_multiple_message_board.do',
    opeartion_sys_user:visit_url+'/projectController/opeartion_sys_user.do'//操作用户

}

function setSession(str,obj) {
    window.sessionStorage.setItem(str,JSON.stringify(obj));

}

function getSession(str) {
  return JSON.parse(window.sessionStorage.getItem(str));
}

function getSessionStr(str) {
    var str = window.sessionStorage.getItem(str);
    return str.substr(1,str.length-2);
}
