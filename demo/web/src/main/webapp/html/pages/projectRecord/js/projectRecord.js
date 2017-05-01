/**
 * Created by huixie on 2017/4/30.
 */

//特殊类型数据项
var special_btn={
    0:{
        btn:["0","1","2","3"],
        type1:{"0":"风险点管控方案","1":"风险点管控方案","2":"风险点管控方案","3":"风险点管控方案"},
        bg:"#c94080"
    },
    1:{
        btn:["0","1","2","3","4","5","6"],
        type1:{"0":"风险点管控方案","1":"框架方案","2":"业务功能思维导图","3":"业务需求列表","4":"原型方案","5":"招标文件","6":"投标文件"},
        bg:"#3b55d4"
    },
    2:{
        btn:["0","1","2","3","4","5","6","7","8","9","10"],
        type1:{"0":"风险点管控方案","1":"数据对接","2":"网络环境","3":"硬件环境","4":"业务推进方案","5":"功能列表","6":"分包说明","7":"实施进度","8":"风险点处理方案","9":"项目文档","10":"验收文档"},
        bg:"#329969"
    },
    3:{
        btn:["0","1","2","3","4","5","6"],
        type1:{"0":"风险点管控方案","1":"人文成本","2":"差旅费用","3":"市场费用","4":"硬件费用","5":"外包费用","6":"其他费用"},
        bg:"#998132"
    }
}


$(function(){
    init();
    initData();
})
function initData() {
    risk_control_search_list();
    getShareUserList();
    project_board_search_list();
    multiple_message_board_search_list();
}
//获取列
var publci_type='0';
function init(){
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
}

//获取特殊类型按钮
function getSpecialTypeBtns(type){
    var btns=special_btn[type];
    var $btn_list=$("#special-type").empty();
    for(var i=0;i<btns.btn.length;i++){
        var html='<button class="btn" style="background:'+btns.bg+'" type="button">'+btns.type1[btns.btn[i]]+'</button>'
        $btn_list.append(html);
    }
}