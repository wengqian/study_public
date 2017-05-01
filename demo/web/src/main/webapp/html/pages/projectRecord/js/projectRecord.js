/**
 * Created by huixie on 2017/4/30.
 */

//特殊类型数据项
var special_btn={
    0:{
        btn:["风险点管控方案","销售策略","销售节点","销售要点"],
        bg:"#c94080"
    },
    1:{
        btn:["风险点管控方案","框架方案","业务功能思维导图","业务需求列表","原型方案","招标文件","投标文件"],
        bg:"#3b55d4"
    },
    2:{
        btn:["风险点管控方案","数据对接","网络环境","硬件环境","业务推进方案","功能列表","分包说明","实施进度","风险点处理方案","项目文档","验收文档"],
        bg:"#329969"
    },
    3:{
        btn:["风险点管控方案","人文成本","差旅费用","市场费用","硬件费用","外包费用","其他费用"],
        bg:"#998132"
    }
}


$(function(){
    init();
})

function init(){
    //获取销售商务的特殊类型按钮
    getSpecialTypeBtns('0');
    //点击tab 子项
    $(".tab-item ul li a").unbind('click').click(function(){
        $(".tab-item ul li").removeClass('active');
        $(this).parent().addClass("active");
        var type=$(this).attr('type');
        getSpecialTypeBtns(type);
    })

    //添加人员弹窗点击tr
    $("#userList").on('click','tr',function(){
        if($(this).hasClass('info')){
            $(this).removeClass('info');
        }else{
            $(this).addClass('info');
        }
    })
}

//获取特殊类型按钮
function getSpecialTypeBtns(type){
    var btns=special_btn[type];
    var $btn_list=$("#special-type").empty();
    for(var i=0;i<btns.btn.length;i++){
        var html='<button class="btn" style="background:'+btns.bg+'" type="button" onclick="onAddTypeContentHandle(1,\''+btns.btn[i]+'\')">'+btns.btn[i]+'</button>'
        $btn_list.append(html);
    }
}

//点击添加类型内容
function onAddTypeContentHandle(key,type){
    $('#addTypeContentModal').modal()
    $("#type-content-label").attr("key",key);
    $("#type-content-data").text(type);
}