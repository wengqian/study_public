/**
 * Created by huixie on 2017/4/29.
 */

$(function(){
    // console.log(window.sessionStorage.getItem("user_info"));
    init();
    getProjectList();
})

function init(){
    // getSession("user_info")["usecode"];
    // console.log(getSession("user_info"));
    $("#projectList").on('click','#project_add',function(){
        $("#addProjectModal").modal();
    })
}

function getProjectList() {
    var data ={
        opeartion_usercode:getSession("user_info")["usercode"],
        opeartion_type:'4'
    }
    sendPost(ProjectController.opeartion_project_data,data,getProjectList_success)
}
//获取项目列表
function getProjectList_success(data){
    var long  = data.long;
    var $list=$("#projectList").empty();
    $list.append('<div class="item-box" id ="project_add">' +
                '   <div class="item-box-border"> ' +
                '       <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> ' +
                '   </div> ' +
                '</div>')
    var list = data.list;
    for(var i=0;i<list.length;i++){
        var html=createProjectListHTML(list[i]);
        $list.append(html);
    };
    //新增
    // $("#project_add").unbind('click').click(function(){
    //     project_add();
    //     //test
    //     // project_modify();
    //     // setTimeout(function(){
    //     //     project_delete();
    //     // },3000)
    // });
}
/***
 * 新增
 * */
function project_add(){
    var data ={
        opeartion_usercode:getSession("user_info")["usercode"],
        project_name:$("#projectName").val(),
        opeartion_type:'0'
    }
    sendPost(ProjectController.opeartion_project_data,data,project_add_success)
}
/***
 * 修改
 * */
function project_add_success(data){
    getProjectList();
    // console.log(data.project_obj)；
    // var obj =data.project_obj;
    // obj["project_name"]="wengqian";
    // var html = createProjectListHTML(obj);
    // $("#project_add").append(html);
}
function project_modify(){
    var data ={
        project_id:'031cf57c-26bc-496b-87ea-f837d31ac5e4',
        project_name:'项目_modify',
        is_delete:'0',
        opeartion_usercode:getSession("user_info")["usercode"],
        opeartion_type:'1'
    }
    sendPost(ProjectController.opeartion_project_data,data,project_modify_success)
}
/***
 * 删除
 * */
function project_modify_success(){
    getProjectList();
}
function project_delete(){
    var data ={
        project_id:'031cf57c-26bc-496b-87ea-f837d31ac5e4',
        project_name:'项目',
        opeartion_usercode:getSession("user_info")["usercode"],
        opeartion_type:'2'
    }
    sendPost(ProjectController.opeartion_project_data,data,project_delete_success)
}
function project_delete_success(data){
    getProjectList();
}
//创建项目列表HTML
function createProjectListHTML(obj){
    var project_id = obj["project_id"];
    var html="";
    html+='<div class="item-box">';
    html+='    <div class="item-box-border">';
    html+='        <p><span class="item-label">项目名称：</span><span class="item-data">'+obj["project_name"]+'</span></p>';
    html+='        <p><span class="item-label">创建时间：</span><span class="item-data">'+longToDate_str(obj["create_time"])+'</span></p>';
    html+='        <p><span class="item-label">更新时间：</span><span class="item-data">'+longToDate_str(obj["update_time"])+'</span></p>';
    html+='        <p><button type="button"  class="btn btn-default" onclick="onEnterProjectRecordHandle(\''+project_id+'\');" >进入</button></p>';
    html+='    </div>';
    html+='</div>';
    return html;
}
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

function onEnterProjectRecordHandle(project_id){
    // project_id = project_id.substr(1,project_id.length-1);
    // project_id = project_id.trim();
    setSession("opeartion_project_id",project_id);//当前操作的项目id
    $("#mainIframe",parent.document).attr("src",'pages/projectRecord/projectRecord.html')
}