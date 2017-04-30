/**
 * Created by huixie on 2017/4/29.
 */

//初始化
$(function(){
    init();
})
function init(){
    $("#login").unbind('click').click(function(){
        login();
    });
}
/**
 *登录访问账户
 */
function login(){
    var data ={
        usercode:'1',
        password:'1',
        opeartion_type:'5'
    }
    sendPost(ProjectController.opeartion_sys_user,data,login_success)
}
/**
 * 登录成功之后操作
 * */
function login_success(data){
    // console.log(data)
    setSession("user_info",data["user_obj"]);
    // console.log(getSession("user_info"));
    window.location.href='index.html';
}