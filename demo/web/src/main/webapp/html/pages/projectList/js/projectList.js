/**
 * Created by huixie on 2017/4/29.
 */

$(function(){
    init();
    getProjectList();
})

function init(){

}

//获取项目列表
function getProjectList(){
    var $list=$("#projectList").empty();
    $list.append('<div class="item-box">' +
                '   <div class="item-box-border"> ' +
                '       <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> ' +
                '   </div> ' +
                '</div>')
    for(var i=0;i<4;i++){
        var html=createProjectListHTML();
        $list.append(html);
    }
}

//创建项目列表HTML
function createProjectListHTML(obj){
    var html="";
    html+='<div class="item-box">';
    html+='    <div class="item-box-border">';
    html+='        <p><span class="item-label">项目名称：</span><span class="item-data">G20实战应用平台</span></p>';
    html+='        <p><span class="item-label">创建时间：</span><span class="item-data">2016-05-03 12:59:59</span></p>';
    html+='        <p><span class="item-label">更新时间：</span><span class="item-data">2016-05-03 12:59:59</span></p>';
    html+='        <p><button type="button"  class="btn btn-default" onclick="onEnterProjectRecordHandle()">进入</button></p>';
    html+='    </div>';
    html+='</div>';
    return html;
}

function onEnterProjectRecordHandle(){
    $("#mainIframe",parent.document).attr("src",'pages/projectRecord/projectRecord.html')
}