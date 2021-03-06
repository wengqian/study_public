/**
 * Created by wengqian on 2017/4/29.
 */


//默认参数
var ajax_json ={
    async:'ture',
    datatype:'json',
    type_post:'POST',
    type_get:'GET'
}
//默认的datatype为json
// var datatype_arr =[
//     xml,//：返回XML文档，可用JQuery处理。
//     html,//：返回纯文本HTML信息；包含的script标签会在插入DOM时执行。
//     script,//：返回纯文本JavaScript代码。不会自动缓存结果。除非设置了cache参数。注意在远程请求时（不在同一个域下），所有post请求都将转为get请求。
//     json,//：返回JSON数据。
//     jsonp,//：JSONP格式。使用SONP形式调用函数时，例如myurl?callback=?，JQuery将自动替换后一个“?”为正确的函数名，以执行回调函数。
//     text,//：返回纯文本字符串。
// ];
/**
 * post 方式
 * */
function sendPost(url,data,callback){
    ajaxj_send(this.ajax_json.type_post,url,data,this.ajax_json.datatype,callback,this.ajax_json.async);
}
/**
 * post get
 * */
function sendGet(url,data,callback){
    ajaxj_send(this.ajax_json.type_get,url,data,this.ajax_json.datatype,callback,this.ajax_json.async);
}
/**
 * ajax的访问
 *
 */
function ajaxj_send(type,url,data,datatype,callback,async){
    $.ajax({
        type: type,
        url: url,
        data: "params="+JSON.stringify(data),
        dataType: datatype,
        async:async,
        callback:callback,
        beforeSend: function(XMLHttpRequest){
            this;   //调用本次ajax请求时传递的options参数
        },
        //请求完成调用
        complete:function(XMLHttpRequest, textStatus){
            this;    //调用本次ajax请求时传递的options参数
        },
        success: function(data,textStatus){
            //data可能是xmlDoc、jsonObj、html、text等等
            //this;  //调用本次ajax请求时传递的options参数
            //var data1 =  JSON.parse(data);
            if(data.code =='1'){
                send_success_callback(callback,data);
            }else if(data.code =='0'){
                send_fail_callback(callback,data);
            }

        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            //通常情况下textStatus和errorThrown只有其中一个包含信息
            this;   //调用本次ajax请求时传递的options参数
        }

    });

}
/**
 * 成功之后调用的方法
 */
function send_success_callback(callback,data){
    if(callback!='undefined' || callback!=null || callback!=''){
        callback(data.data);
    }else{
        return data.data;
    }

}
/**
 * 失败之后调用的方法
 */
function send_fail_callback(callback,data){
    alert(data.msg);
}
