/**
 * Created by huixie on 2017/4/30.
 */

//获取列
var publci_type='';
var share_set_Json={};
var project_id=sessionStorage.getItem('cur_project_id');
var projectBoardSendContent=null;
var messageBoardAddSendContent=null;
//特殊类型数据项
//{
// a:核心目的
// b:人物逻辑
// c:会议纪要
// d:自定义
// }
//
var special_btn={
    gd:{
        a:"核心目的",
        b:"人物逻辑",
        c:"会议纪要",
        d:"自定义"
    },
    0:{
        btn:["0","1","2","3"],
        type1:{"0":"风险点管控方案","1":"销售策略","2":"销售节点","3":"销售要点"},
        bg:"#327299"
    },
    1:{
        btn:["0","1","2","3","4","5","6"],
        type1:{"0":"风险点管控方案","1":"框架方案","2":"业务功能思维导图","3":"业务需求列表","4":"原型方案","5":"招标文件","6":"投标文件"},
        bg:"#329978"
    },
    2:{
        btn:["0","1","2","3","4","5","6","7","8","9","10"],
        type1:{"0":"风险点管控方案","1":"数据对接","2":"网络环境","3":"硬件环境","4":"业务推进方案","5":"功能列表","6":"分包说明","7":"实施进度","8":"风险点处理方案","9":"项目文档","10":"验收文档"},
        bg:"#998132"
    },
    3:{
        btn:["0","1","2","3","4","5","6"],
        type1:{"0":"风险点管控方案","1":"人文成本","2":"差旅费用","3":"市场费用","4":"硬件费用","5":"外包费用","6":"其他费用"},
        bg:"#994b32"
    }
}

$(function(){
    init();
    initData();
})


function init(){
    //初始化编辑器
    projectBoardSendContent=$('#project_board_add_send_content').Editor();
    messageBoardAddSendContent=$('#multiple_message_board_add_send_content').Editor();

    //获取销售商务的特殊类型按钮
    getSpecialTypeBtns('0');
    //点击tab 子项
    $(".tab-item ul li a").unbind('click').click(function(){
        $(".tab-item ul li").removeClass('active');
        $(this).parent().addClass("active");
        var type=$(this).attr('type');
        publci_type = type
        getSpecialTypeBtns(publci_type);
        //初始化数据
        multiple_message_board_search_list();
    })

    //添加人员弹窗点击tr
    $("#userList").on('click','tr',function(){
        if($(this).hasClass('info')){
            $(this).removeClass('info');
            delete share_set_Json[($(this)[0].cells[1].innerText)];
        }else{
            $(this).addClass('info');
            share_set_Json[$(this)[0].cells[1].innerText] = $(this)[0].cells[2].innerText;
        }
    })

    //添加项目成员
    $("#addProjectMember").unbind('click').click(function(){
        share_set_Json={};
        sure_user_type=1;
        opeartion_sys_user_search_list();
        $("#userListModal").modal();
    });

    //风险点管控
    $("#riskControl").unbind('click').click(function(){
        $("#risk_control_sure_users").empty();
        $("#risk_control_content").val("");
        $("#riskModal").modal();
    })

    //风险点管控弹窗——添加成员
    $("#add_userMember").unbind('click').click(function(){
        share_set_Json={};
        sure_user_type=0;
        opeartion_sys_user_search_list();
        $("#userListModal").modal();
    })
}

//初始化数据
function initData() {
    getCurProjectData();//获取当前项目内容
    risk_control_search_list();//获取风险点黑板内容
    getShareUserList();
    project_board_search_list();
    multiple_message_board_search_list();
}

//获取当前项目数据
function getCurProjectData(){
    var data ={
        project_id:project_id,
        opeartion_type:'3'
    };
    sendPost(ProjectController.opeartion_project_data,data,getCurProjectData_success);
}
//成功获取当前项目数据
function getCurProjectData_success(reply){
    $("#projectName").text(reply.obj.project_name);
}

/*---------------风险点管控start-----------------*/
//查询风险点黑板内容
function risk_control_search_list(){
    var data ={
        project_id:project_id,
        opeartion_type:'4'
    };
    sendPost(ProjectController.opeartion_risk_control,data,risk_control_search_list_success);
}
//成功查询风险点黑板内容
function risk_control_search_list_success(data){
    var list = data.list;
    var html="";
    for(var i=0;i<list.length;i++){
        html+=createHtml_risk_control(list[i]);
    }
    $("#risk-content").html(html);
}
//创建风险点黑板内容Html
function createHtml_risk_control(obj){
    var str = ""+longToDate_str(obj["send_time"])+" "+obj["send_content"]+",已发送给"+obj["receive_user_json"]+"";
    var html ='<p>'+str+'</p>';
    return html;
}

//风险点管控新增确认请求
function risk_control_add_ok(){
    var list =$("#risk_control_sure_users").find('button');
    if(list.length<0){
        alert("请选择用户");
        return ;
    }
    var receive_user_json="";
    for(var i=0;i<list.length;i++){
        receive_user_json+=list[i].innerText+","
    }
    receive_user_json =receive_user_json.substr(0,receive_user_json.length-1);
    var data ={
        project_id:project_id,
        opeartion_usercode:getObjSession("user_info")["usercode"],
        send_content:$("#risk_control_content").val(),
        receive_user_json:receive_user_json,//被接受的人员信息
        opeartion_type:'0'
    };

    sendPost(ProjectController.opeartion_risk_control,data,risk_control_add_success);
}
//风险点管控新增确认请求成功
function risk_control_add_success(data){
    risk_control_search_list();
}

//风险点共享人员
function risk_control_sure_users(){
    var html="";
    for (var key in share_set_Json){
        html+=createHtml_risk_control_sure_user(share_set_Json[key]);
    }
    $("#risk_control_sure_users").html(html);
}
//创建风险点共享人员HTML
function createHtml_risk_control_sure_user(username){
    var html =' <p><span>'+username+'</span><a href="javascript:void(0)">X</span></a></p>';
    return html;
}
/*---------------风险点管控end-----------------*/

//项目人员
var sure_user_type=0;//0代表风险点管控中的添加,1代表项目总的添加
function sure_user(){
    if(sure_user_type==0){
        risk_control_sure_users();
    }else if(sure_user_type==1){
        addShare();
    }
}

//设置共享人员
function addShare(){
    var data ={
        project_id:project_id,
        opeartion_type:'0'
    };
    var usercode_arr='';//共享人员的usercode
    for (var key in share_set_Json){
        usercode_arr+=key+","
    }
    usercode_arr =usercode_arr.substr(0,usercode_arr.length-1);
    data["usercode_arr"]=usercode_arr;
    sendPost(ProjectController.opeartion_project_vist_power,data,addShare_success);
}

function addShare_success(data){
    getShareUserList();
}

/**
 * 获取贡献人员的信息
 * */
function getShareUserList(){
    var data ={
        project_id:project_id,
        opeartion_type:'1'
    };
    sendPost(ProjectController.opeartion_project_vist_power,data,getShareUserList_success);
}
function getShareUserList_success(data){
    var list = data.list;
    var html="";
    $("#risk_control_sure_users").html(html);
    for(var i=0;i<list.length;i++){
        html+=createHtml_risk_control_sure_user(list[i]["username"]);
    }
    $("#share_user").html(html);

}

/*--------各类留言板start----------------*/
//新增
function multiple_message_board_add() {
    console.log($("#multiple_message_board_add_send_content").Editor("getText"))
    if($("#type-content-label").attr("key")=='d'){
        //自定义判断
        if( $("#type-content-data").text()==null ||  $("#type-content-data").text()==undefined || $("#type-content-data").text() =="自定义"){
            alert("请确认自定义类型");
            return ;
        }
    }
    var data ={
        project_id:project_id,
        opeartion_usercode:getObjSession("user_info")["usercode"],
        type:publci_type,
        type1:$("#type-content-label").attr("key"),
        type1_name:$("#type-content-data").text(),
        send_content:$("#multiple_message_board_add_send_content").Editor("getText"),
        opeartion_type:'0'
    };
    sendPost(ProjectController.opeartion_project_multiple_message_board,data,multiple_message_board_add_success);
    $('#addTypeContentModal').modal('hide');
}
function multiple_message_board_add_success(data){
    multiple_message_board_search_list();
}

//修改
function multiple_message_board_modify() {
    var data ={
        id:'',
        project_id:project_id,
        opeartion_usercode:getObjSession("user_info")["usercode"],
        send_content:'留言板',
        opeartion_type:'1'
    };
    sendPost(ProjectController.opeartion_project_multiple_message_board,data,multiple_message_board_modify_success);
}
function multiple_message_board_modify_success(data){
    multiple_message_board_search_list();
}

//删除
function multiple_message_board_delete() {
    var data ={
        id:'',
        project_id:project_id,
        opeartion_usercode:getObjSession("user_info")["usercode"],
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
        project_id:project_id,
        opeartion_usercode:getObjSession("user_info")["usercode"],
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
        '<span>'+special_btn[obj["type"]][obj["type1"]]+'</span>'+
        '<span>'+obj["send_username"]+'</span>'+
        '</div>'+
        '<div class="list-item-content">'+obj["send_content"]+'</div>'+
        '</div>';
    return html;
}
/*--------各类留言板end----------------*/


//项目交流版
/**
 * 新增
 * */
function project_board_add(){
    var data ={
        project_id:project_id,
        send_content:$("#project_board_add_send_content").Editor("getText"),
        opeartion_usercode:getObjSession("user_info")["usercode"],
        opeartion_type:'0'
    };
    // console.log(data);
    sendPost(ProjectController.opeartion_project_ac_board,data,project_board_add_success);
}
function project_board_add_success(data){
    project_board_search_list();
}
function project_board_modify(){
    var data ={
        project_id:project_id,
        send_content:'评价',
        opeartion_usercode:getObjSession("user_info")["usercode"],
        opeartion_type:'1'
    };
    sendPost(ProjectController.opeartion_project_ac_board,data,project_board_modify_success);
}
function project_board_modify_success(data){

}
//删除
function project_board_delete(){
    var data ={
        project_id:project_id,
        opeartion_usercode:getObjSession("user_info")["usercode"],
        opeartion_type:'2'
    };
    sendPost(ProjectController.opeartion_project_ac_board,data,project_board_delete_success);
}
function project_board_delete_success(data){

}
//查询
function project_board_search_list(){
    var data ={
        project_id:project_id,
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

//查询人员信息
function opeartion_sys_user_search_list(){
    var data ={
        opeartion_type:'4'
    };
    //可选模糊字段
    sendPost(ProjectController.opeartion_sys_user,data,opeartion_sys_user_search_list_success);
}
function opeartion_sys_user_search_list_success(data){
    //展示名称
    // obj={usercode,username}返回的数据
    var list = data.list;
    var html="";
    for(var i=0;i<list.length;i++){
        html+=createHtml_opeartion_sys_user(list[i],i);
    }
    $("#userList").html(html);
}
function createHtml_opeartion_sys_user(obj,index){
    var str = '';
    var html =  '<tr><td>'+(index+1)+'</td><td>'+obj["usercode"]+'</td><td>'+obj["username"]+'</td></tr>';
    return html;
}

//获取特殊类型按钮
function getSpecialTypeBtns(type){
    var btns=special_btn[type];
    var $btn_list=$("#special-type").empty();
    for(var i=0;i<btns.btn.length;i++){
        var html='<button class="btn" style="background:'+btns.bg+'" type="button" onclick="onAddTypeContentHandle(1,\''+btns.type1[btns.btn[i]]+'\')">'+btns.type1[btns.btn[i]]+'</button>'
        $btn_list.append(html);
    }
}

//点击添加类型内容
function onAddTypeContentHandle(key,type){
    $("#multiple_message_board_add_send_content").val("")
    $("#type-content-label").attr("key",key);
    $("#type-content-data").text(type);
    $('#addTypeContentModal').modal()
    publci_type='gd';
}
