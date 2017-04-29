/**
 * Created by huixie on 2017/4/29.
 */

$(function(){
    $("#homePage").unbind('click').click(function(){
        $("#mainIframe").attr("src",'pages/projectList/projectList.html')
    })
})