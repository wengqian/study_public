/**
 * Created by wengqian on 2017/4/30.
 */

//风险点管控
function risk_control_add(){
    var data ={
        project_id:getSessionStr("opeartion_project_id"),
        opeartion_usercode:getSession("user_info")["usercode"],
        send_content:'短信的内容',
        receive_user_json:[{usercode:'1',username:'1'},{usercode:'1',username:'1'}],//被接受的人员信息
        opeartion_type:'0'
    };

    sendPost(ProjectController.opeartion_risk_control,data,risk_control_add_success);
}

function risk_control_add_success(data){
    risk_control_search_list();
}
//查询列
function risk_control_search_list(){
    var paroject_id =getSessionStr("opeartion_project_id");
    var data ={
        project_id:paroject_id,
        opeartion_type:'4'
    };
    //可选模糊字段
    // data["send_usercode"]  =1;
    // data["send_content"]  =1;
    sendPost(ProjectController.opeartion_risk_control,data,risk_control_search_list_success);
}

function risk_control_search_list_success(data){
    console.log(data);
    var list = data.list;
    var html="";
    for(var i=0;i<list.length;i++){
        html+=createHtml_risk_control(list[i]);
    }
    $("#risk-content").html(html);
}
function createHtml_risk_control(obj){
    var str = ""+longToDate_str(obj["send_time"])+" "+obj["send_content"]+",已发送给xxx,xxx,xxx,xxx";
    var html ='<p>'+str+'</p>';
    return html;
}
//风险点管控结束

//项目人员
//设置共享人员
function setShare(){
    var data ={
        project_id:getSessionStr("opeartion_project_id"),
        opeartion_type:'0'
    };
    var usercode_arr='1,2,1';//共享人员的usercode
    data["usercode_arr"]=usercode_arr;
    sendPost(ProjectController.opeartion_project_vist_power,data,setShare_success);
}

function setShare_success(data){
    getShareUserList();
}
/**
 * 获取贡献人员的信息
 * */
function getShareUserList(){
    var data ={
        project_id:getSessionStr("opeartion_project_id"),
        opeartion_type:'1'
    };
    sendPost(ProjectController.opeartion_project_vist_power,data,getShareUserList_success);
}
function getShareUserList_success(data){
    //展示名称
    // obj={usercode,username}返回的数据
    console.log(data)
    var list = data.list;
    var shar_user_str ="";
    for(var i=0;i<list.length;i++){
        shar_user_str+=list[i]["username"]+","
    }
    shar_user_str =shar_user_str.substr(0,shar_user_str.length-1)
    $("#share_user").text(shar_user_str);

}
//项目人员


//各类留言板

//新增

function multiple_message_board_modify() {
    var data ={
        project_id:getSessionStr("opeartion_project_id"),
        opeartion_usercode:getSession("user_info")["usercode"],
        type:'',
        type1:'',
        send_content:'留言板',
        opeartion_type:'0'
    };
    sendPost(ProjectController.opeartion_project_multiple_message_board,data,multiple_message_board_modify_success);
}
function multiple_message_board_modify_success(data){
    // console.log(data);
}

//修改

function multiple_message_board_modify() {
    var data ={
        id:'',
        project_id:getSessionStr("opeartion_project_id"),
        opeartion_usercode:getSession("user_info")["usercode"],
        send_content:'留言板',
        opeartion_type:'1'
    };
    sendPost(ProjectController.opeartion_project_multiple_message_board,data,multiple_message_board_modify_success);
}
function multiple_message_board_modify_success(data){
    // console.log(data);
}

//删除
function multiple_message_board_delete() {
    var data ={
        id:'',
        project_id:getSessionStr("opeartion_project_id"),
        opeartion_usercode:getSession("user_info")["usercode"],
        opeartion_type:'2'
    };
    sendPost(ProjectController.opeartion_project_multiple_message_board,data,multiple_message_board_delete_success);
}
function multiple_message_board_delete_success(data){
    // console.log(data);
}
//获取单个对象
function multiple_message_board_search_obj() {
    var data ={
        id:'',
        opeartion_type:'3'
    };
    sendPost(ProjectController.opeartion_project_multiple_message_board,data,multiple_message_board_search_obj_success);
}
function multiple_message_board_search_obj_success(data){
    // console.log(data);
}

function multiple_message_board_search_list() {
    var data ={
        project_id:getSessionStr("opeartion_project_id"),
        opeartion_usercode:getSession("user_info")["usercode"],
        type:publci_type,
        opeartion_type:'4'
    };
    //可选字段
    // data["type1"]='1';
    // data["send_usercode"]  =1;
    // data["send_content"]  =1;
    //支持分页 page 和pagesize
    sendPost(ProjectController.opeartion_project_multiple_message_board,data,multiple_message_board_search_list_success);
}
//获取项目列表
function multiple_message_board_search_list_success(data){
    // console.log(data);
    var list = data.list;
    var html="";
    for(var i=0;i<list.length;i++){
        html+=createHtml_multiple_message_board(list[i]);
    }
    $("#multiple_message_board").html(html);
}
function createHtml_multiple_message_board(obj){
    var str = '';
    var html =  '<div class="message-list-item">'+
                    '<div class="list-item-title">'+
                         '<span>'+longToDate_str(obj["send_time"])+'</span>'+
                         '<span>'+special_btn[publci_type]["type1"][obj["type1"]]+'</span>'+
                         '<span>'+obj["send_username"]+'</span>'+
                         '</div>'+
                    '<div class="list-item-content">'+obj["send_content"]+'</div>'+
                '</div>';
    return html;
}
//各类留言板


//项目交流版
/**
 * 新增
 * */
function project_board_add(){
    var data ={
        project_id:getSessionStr("opeartion_project_id"),
        send_content:'评价',
        opeartion_usercode:getSession("user_info")["usercode"],
        opeartion_type:'0'
    };
    sendPost(ProjectController.opeartion_project_ac_board,data,project_board_add_success);
}
function project_board_add_success(data){

}
function project_board_modify(){
    var data ={
        project_id:getSessionStr("opeartion_project_id"),
        send_content:'评价',
        opeartion_usercode:getSession("user_info")["usercode"],
        opeartion_type:'1'
    };
    sendPost(ProjectController.opeartion_project_ac_board,data,project_board_modify_success);
}
function project_board_modify_success(data){

}
//删除
function project_board_delete(){
    var data ={
        project_id:getSessionStr("opeartion_project_id"),
        opeartion_usercode:getSession("user_info")["usercode"],
        opeartion_type:'2'
    };
    sendPost(ProjectController.opeartion_project_ac_board,data,project_board_delete_success);
}
function project_board_delete_success(data){

}
//查询
function project_board_search_list(){
    var data ={
        project_id:getSessionStr("opeartion_project_id"),
        opeartion_type:'4'
    };
    //可选模糊字段
    // data["send_usercode"]  =1;
    // data["send_content"]  =1;
    sendPost(ProjectController.opeartion_project_ac_board,data,project_board_search_list_success);
}
function project_board_search_list_success(data){
    //展示名称
    // obj={usercode,username}返回的数据
    var list = data.list;
    var html="";
    for(var i=0;i<list.length;i++){
        html+=createHtml_project_board(list[i]);
    }
    $("#project_board").html(html);
}
function createHtml_project_board(obj){
    var str = '';
    var html =  '<div class="box-content-item">'+
                    '<p>'+obj["send_username"]+' '+longToDate_str(obj["send_time"])+'</p>'+
                    '<p>'+obj["send_content"]+'</p>'+
                '</div>';
    return html;
}
//项目交流版
function longToDate_str(date_long) {
    if(date_long==null){
        return "";
    }
    // new Date(parseInt(date_long)).format("yyyy-MM-dd-mm-ss")
    var date = new Date(parseInt(date_long));
    // date.getYear(); //获取当前年份(2位)
    // date.getFullYear(); //获取完整的年份(4位,1970-????)
    // date.getMonth(); //获取当前月份(0-11,0代表1月)
    // date.getDate(); //获取当前日(1-31)
    // date.getDay(); //获取当前星期X(0-6,0代表星期天)
    // date.getTime(); //获取当前时间(从1970.1.1开始的毫秒数)
    // date.getHours(); //获取当前小时数(0-23)
    // date.getMinutes(); //获取当前分钟数(0-59)
    // date.getSeconds(); //获取当前秒数(0-59)
    // date.getMilliseconds(); //获取当前毫秒数(0-999)
    // date.toLocaleDateString(); //获取当前日期
    return date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    // return date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate() + " " + date.getHours();
}

