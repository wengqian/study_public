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