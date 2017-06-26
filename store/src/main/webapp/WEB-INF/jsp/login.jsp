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
    <title>store-login</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="工业云平台,imes,scada">
	<meta http-equiv="description" content="工业云平台">
	<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/microsoft/Default2052.css">
	<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/charisma-app.css">
	<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/jquery.noty.css">
	<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/noty_theme_default.css">
    <link href="<%=apath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
	<script src ="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
	<script src ="<%=apath%>/resources/js/jquery.noty.js"></script>
	<script src ="<%=apath%>/resources/js/charisma.js"></script>
	<script  type="text/javascript" src="<%=apath%>/resources/js/bootstrap.js"></script>
  </head>

<body class="cb">
<div >
  <form name="loginForm" id="loginForm" method="post">
      <div style="width:90%;height:90%;max-width: 600px; max-height: 600px;    margin: 0 auto;">
        <section class="section no-margin-bottom" style="margin:10px;width:90%;height:90%;min-height:540px;min-width:565px;background-image: url(<%=apath%>/resources/images/store_login_screen.png);background-position:center center;background-size:100% 100%;background-repeat: no-repeat;"> 
        
          <div class="form-group col-md-18 col-md-offset-1 col-xs-18 col-xs-offset-1" style="padding-top:55%;">
            <div class="placeholderContainer input-group input-group-lg">
              <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
              <input type="email" name="email" id="email"  maxlength="113" class="form-control ltr_override" aria-label="电子邮箱" placeholder="电子邮箱" > 
            </div>
            <br>
            <div class="placeholderContainer input-group input-group-lg">
              <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
              <input name="password" type="password" id="password" autocomplete="off" class="form-control" aria-describedby="passwordError passwordDesc" placeholder="密码" aria-label="密码" maxlength="18"> 
            </div>
            <br>
            <div id="idTd_PWD_KMSI_Cb" class="form-group checkbox text-block-body no-margin-top col-xs-6"> 
            <label id="idLbl_PWD_KMSI_Cb" style="display:inline-block;"> 
            <input name="KMSI" id="idChkBx_PWD_KMSI0Pwd" type="checkbox" style="display:inline-block;margin:0px;left: 13px;"> 
            <span>使我保持登录状态</span> </label> 
            </div>
            <div class="col-xs-6"> 
            <input type="button" id="submit" class="btn btn-block btn-primary" value="登录"> </div>
            
            </div> 
           
            <div class="row"> 
            <div class="section col-md-18 col-md-offset-1 col-xs-18 col-xs-offset-1">没有帐户?
            <a href="<%=apath%>/register/toRegister" id="signup" class="display-inline-block">创建一个!</a>
            </div> </div>
        
        
        </section>
       </div>
    </form> 
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
	$('input').bind('keyup', function () {
		this.value=this.value.replace(/[<|>]/g,'');
		});
});
var testNamePassword = /^[\da-zA-Z]{6,20}$/;
/* $("#email").change(function() { 
	if(!$.trim($("#email").val()) || $.trim($("#email").val()) == ""){
		optTip2("请输入电子邮箱!");
		$("#email").focus();
		return;
	}
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!filter.test($("#email").val())){
		optTip2("电子邮箱格式不正确！");
		$("#email").focus();
		return false;
	}
});
$("#password").change(function() { 
	if(!testNamePassword.test($("#password").val())){
		optTip2("请输入6~20位的密码!");
		$("#password").focus();
		return false;
	}
	if ($("#password").val() == ""){
		optTip2("请输入密码！");
		$("#password").focus();
		return false;
	} 
});*/
function check(){
	if(!$.trim($("#email").val()) || $.trim($("#email").val()) == ""){
		optTip2("请输入电子邮箱!");
		$("#email").focus();
		return;
	}
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!filter.test($("#email").val())){
		optTip2("电子邮箱格式不正确！");
		$("#email").focus();
		return false;
	}
	if(!testNamePassword.test($("#password").val())){
		optTip2("请输入6~20位的密码!");
		$("#password").focus();
		return false;
	}
	if ($("#password").val() == ""){
		optTip2("请输入密码！");
		$("#password").focus();
		return false;
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
			if(data.code=="0"){
				//$("#errorInfo").css("display","block");
				//$("#errorInfo").text(data.msg);
				optTip2(data.message);
			}else{
				//判断request.returnUrl是否为空
				if (!returnUrl || typeof(returnUrl)=="undefined" || returnUrl==''|| returnUrl=="null" || returnUrl==null){
					window.location.href = "<%=apath%>/goodsList/toHomePage";
				}else{
					window.location.href = returnUrl;
				}
			}
		}
	});
}
</script>
</html>

<!-- <script type="text/javascript">
 $(function() {
	 $("#signup").click(function(){
		$("#bell").popover({html:true});
	}); 
}); 
</script> -->