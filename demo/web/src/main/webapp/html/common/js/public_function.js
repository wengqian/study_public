/**
 * Created by admin on 2017/5/8.
 */

//传递消息到Iframe
function postMessageToIframe(iframeId,sendData){
    var origin=targetOrigin+"";
    $('#'+iframeId).prop("contentWindow").postMessage(sendData,origin);
}

//存对象session
function setObjSession(str,obj) {
    window.sessionStorage.setItem(str,JSON.stringify(obj));
}
//取对象session
function getObjSession(str) {
    return JSON.parse(window.sessionStorage.getItem(str));
}
//取对象session 字符串
function getObjSessionStr(str) {
    var str = window.sessionStorage.getItem(str);
    return str.substr(1,str.length-2);
}

//获取时间长字符串
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
