<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<%--     <base href="<%=basePath%>">
 --%>    
    <title>testoficp-login</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/charisma-app.css">
	<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/jquery.noty.css">
	<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/unslider.css">

	<script src ="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
	<script src ="<%=apath%>/resources/js/jquery.noty.js"></script>
	<script src ="<%=apath%>/resources/js/charisma.js"></script>
	<script src ="<%=apath%>/resources/js/unslider.js"></script>
  </head>

<body>
<div style=" margin:0;   width: 100%;    height: 74px;    border-bottom: 1px solid #e3e3e3;    background-color: #fff;    padding: 0px;">
    <div class="header-layout y-row" style="padding:17px 0;overflow:hidden;min-width: 1000px;max-width: 1200px;margin-left: auto;margin-right: auto;zoom: 1;">
        <a href="http://www.aliyun.com" data-spm-anchor-id="0.0.0.0" style="background: url(<%=apath%>/resources/images/banner1.jpg) no-repeat;background-size: 100%;    float: left;    display: inline-block;    height: 40px;    width: 140px;    overflow: hidden;    text-indent: -999em;"> </a>
        <h2 class="logo-title" style="margin-left:10px;    float: left;    display: inline-block;    width: auto;    height: 30px;    line-height: 30px;
    font-size: 20px;    font-weight: normal;    margin-top: 2px;    color: #3c3c3c;">
                             <!-- 登录  -->                    </h2>
    </div>
</div>
<div class="content y-row" data-spm="2" style="    position: relative;    margin-top: 95px;    min-height: 485px;    vertical-align: middle;    min-width: 1000px;    max-width: 1200px;    margin-left: auto;    margin-right: auto;    zoom: 1;">
  <!-- 左边图片轮播 -->
  <div style="margin-left: 100px;float: left;width:450px;height:350px; ">
    <div id="unsiliderdiv" class="unslider" style="width: 450px;    height: 380px;    position: relative;    overflow: auto;    margin: 0;    padding: 0;">
    <div id="bannerdiv">
      <ul>
        <li>
          <a href="https://promotion.aliyun.com/ntms/act/redis256.html?spm=5176.3047821.355504.1.hjoFl2" target="_blank" data-spm-anchor-id="5176.3047821.355504.1">
          <img src="https://img.alicdn.com/tfs/TB1BGngQpXXXXa9aXXXXXXXXXXX-900-700.jpg" style=" width: auto;      height: auto;      max-width: 100%;      max-height: 100%;   "></a>
        </li>
        <li class="">
          <a href="https://promotion.aliyun.com/ntms/act/rds/mysqlenterprise.html?spm=5176.3047821.355504.2.hjoFl2" target="_blank" data-spm-anchor-id="5176.3047821.355504.2">
          <img src="https://img.alicdn.com/tfs/TB1g5lAQpXXXXbJXXXXXXXXXXXX-900-700.jpg" style=" width: auto;      height: auto;      max-width: 100%;      max-height: 100%;   "></a>
        </li>
      </ul>
    </div>
    <!-- <nav class="unslider-nav outer" style="    position: relative;    margin: 8px auto;">
    <ol style="    list-style: none;    text-align: center;">
    <li data-slide="0" class="unslider-active">1</li>
    <li data-slide="1" class="">2</li>
    </ol></nav> -->
    </div>
  </div>
  <!-- 右侧登录框 -->
  <div id="login-module" style="    position: absolute;    border: 1px solid #D4D4D4;    padding-top: 20px;    right: 160px;    margin-bottom: 10px;    background-color: #fff;    min-width: 292px;    min-height: 320px;    background: rgba(255,255,255,0.3);    z-index: 2;">
    <form id="loginForm" name="loginForm" method="post" class="form clr style-type-vertical lang-zh_CN vertical-zh_CN "
          style="position: relative;    font: 400 14px arial;width: 250px;    margin: 10px auto;">
      
      <div class="fm-field-wrap" style="width: 250px; height: 90px;">
      <label for="fm-login-id" style="font-weight: 700;">                    登录名                :</label><div>
        
      </div>
       <input id="email" class="fm-text" style="width:100%;border: 1px solid #ccc;    color: #595959;    vertical-align: middle;    float: left;border-radius: 3px;    line-height: 16px;    padding: 8px 5px;    margin-top: 10px;    font-size: 14px;    background: transparent;" name="email" tabindex="1" placeholder="邮箱" value="" autocorrect="off" autocapitalize="off">
      </div>
      <div>
        
      </div>
      <div class="fm-field-wrap " style=" width: 100%;height: 90px;">
      <label for="fm-login-id" style="font-weight: 700;">                    密码                :</label><div>
        
      </div>
      <input id="password" class="fm-text" style="width:100%;border: 1px solid #ccc;    color: #595959;    vertical-align: middle;    float: left;border-radius: 3px;    line-height: 16px;    padding: 8px 5px;    margin-top: 5px;    font-size: 14px;    background: transparent;" type="password" name="password" tabindex="2" placeholder="登录密码" autocorrect="off" autocapitalize="off">
      </div>
	  <div id="login-submit">
         <input id="submit" value="登录" class="fm-button fm-submit" style="    width: 100%;    margin-top: 20px;display: inline-block;    min-width: 140px;    height: 36px;    padding: 0 30px 1px;    background: #00a2ca;    border: none;    line-height: 32px;    font-size: 14px;    color: #fff;    text-align: center;    outline: none;    cursor: pointer;    border-radius: 4px;"
           tabindex="4" name="submit-btn">
      </div>    
    </form>
    
  </div>
</div>




</body>  
<script type="text/javascript">
var returnUrl = '<%=request.getAttribute("returnUrl")%>' ;
$(function(){
	$(window).keydown(function(event){
		var keyCode =  event.keyCode;
		if(keyCode == 13){
			check();
		}		
	});
	$("#submit").click(function(){
		check();
	});
});
function check(){
	if(!$.trim($("#email").val()) || $.trim($("#email").val()) == ""){
		$("#hing").empty();
		$("#hing").append("请输入邮箱");
		$("#email").focus();
		return;
	}
	if(!$.trim($("#password").val()) || $.trim($("#password").val()) == ""){
		$("#hing").empty();
		$("#hing").append("请输入密码");
		$("#password").focus();
		return;
	}
	/* $("#loginForm").submit(); */
	$.ajax({
		url : "<%=apath%>/login/checkLogin",
		data : $("#loginForm").serialize(),
		type:"POST",
		dataType:"json",
		async : false,
		success : function(json){

			var data = eval(json); 
			if(data!=null && data.result){
				window.location.href = "<%=apath%>/login/toUserOrderList";
			}else{
				optTip2("用户名或密码错误");
				
			}
		},
		error: function(data){
			optTip2(data);
		}
	});
}
//登录页图片轮播
$(document).ready(function(e) {

    $('#bannerdiv').unslider();

});
//登出
function logout(){
	$.ajax({
		url : "<%=apath%>/login/logout",
		type:"POST",
		dataType:"json",
		async : false,
		success : function(json){
			window.location.href = "<%=apath%>/login/toLogin";
		}
	});
} 
</script>
</html>

