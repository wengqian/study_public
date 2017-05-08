/**
 * Created by huixie on 2017/4/29.
 */

//初始化
$(function(){
    init();
})
function init(){
    setObjSession("user_info",null);
    $("#login").unbind('click').click(function(){
        login();
    });
}
/**
 *登录访问账户
 */
function login(){
    var usercode =$("#userName_test").val();
    var password =$("#userPwd_test").val();
    if(usercode=='' || usercode==undefined){
        alert('请输入用户名！');
        return;
    }
    if(password=='' || password==undefined){
        alert('请输入密码！');
        return;
    }
    var data ={
        usercode:usercode,
        password:password,
        opeartion_type:'5'
    }
     sendPost(ProjectController.opeartion_sys_user,data,login_success)
}
/**
 * 登录成功之后操作
 * */
function login_success(data){
    if(data["user_obj"]=='' ||data["user_obj"]==undefined){
        alert('无此用户！');
        return ;
    }
    // console.log(data)
    setObjSession("user_info",data["user_obj"]);
    // console.log(getSession("user_info"));
    window.location.href='index.html';
}