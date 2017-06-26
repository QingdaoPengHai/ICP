<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心</title>
<link href="<%=apath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/css/style.css" rel="stylesheet" type="text/css"/>
<!-- 弹出框  -->
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/charisma-app.css">
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/jquery.noty.css">
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/noty_theme_default.css">
<!-- 弹出框  -->
<script  type="text/javascript" src="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/bootstrap.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/main.js"></script>
<script type="text/javascript" src="<%=apath%>/resources/js/superslide.2.1.js"></script>
<script src ="<%=apath%>/resources/js/jquery.noty.js"></script>
<script src ="<%=apath%>/resources/js/charisma.js"></script>
</head>
<body>
<div id="mainContent" class="mainContent">
<!-- 头导航栏 -->
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<!-- end 头导航栏 -->
	<div class="user-content" style="min-width: 730px;">	
		<div class="user-con" style="width:90%;max-width: 1200px;">
			<div class="user-left-menu" style="width:20%;max-width:120px;">			
				<dl class="side-menutree">
					<dt><a href="<%=apath%>/login/toUserOrderList">我的订单</a></dt>
					<dt><a href="<%=apath%>/login/toUserInfo">我的信息</a></dt>
					<dt><a href="<%=apath%>/login/toUserChangePassword" class="active" style="color: #e4393c;font-weight: bold;">修改密码</a></dt>
				</dl>									    	
			</div>
			<div class="user-right" style="width:80%;max-width: 1050px;">
				<div class="user-content-info">
					 	
					 	
<div id="mainContainer" style="min-height:500px;">

<form id="changePasswordForm" ><div id="Credentials">
	  <div id="errorInfo" class="alert alert-danger" style="display: none;"></div>
            <div id="CredentialsInputPane" class="container">
             <!--Password-->
                <div class="row">
                    <div class="form-group col-xs-4 col-xs-offset-2">
                        <label class="text-base" for="oldPassword" style="padding:10px;">请输入原密码(*必填)</label>
                        <input type="password" id="oldPassword" class="form-control" name="oldPassword" placeholder="请输入原密码" maxlength="20" >
                        <!-- <div class="text-caption">8-16 个字符，区分大小写</div> -->
                    </div>
                </div>
                <!--Password-->
                <div class="row">
                    <div class="form-group col-xs-4 col-xs-offset-2">
                        <label class="text-base" for="password" style="padding:10px;">新密码(*必填)</label>
                        <input type="password" id="password" class="form-control" name="password" placeholder="请输入6~20位由数字和字母组成的密码" maxlength="20">
                        <!-- <div class="text-caption">8-16 个字符，区分大小写</div> -->
                    </div>
                </div>
                <!--Retype Password-->
                <div class="row">
                    <div class="form-group col-xs-4 col-xs-offset-2">
                        <label class="text-base" for="retypePassword" style="padding:10px;">确认密码(*必填)</label>
                        <input type="password" id="retypePassword" class="form-control" name="retypePassword" placeholder="请再次输入新密码" maxlength="20">
                    </div>
                </div>
				<div class="row">
                    <div class="col-xs-4 col-xs-offset-2" style="padding:10px;">
                        <input type="button" id="submitChangePassword" class="btn btn-primary btn-block" value="提交">
                    </div>
                </div>
            </div>

        </div></form>

			</div><!--user-right结束	-->	
			<div class="clear"></div>
		</div><!--user-con结束-->	
	</div><!--user-content结束-->		
	<div class="clear"></div>	


</div>	
</div></div>
<!-- foot.jsp -->
<%@ include file="/WEB-INF/jsp/foot.jsp"%>
<!-- end foot.jsp -->

</body>
<script type="text/javascript">
$(document).ready(function(){
	var h1= $(window).height(); 
	h1=h1-160;
	$("div.user-content").css({		minHeight: h1	});
	window.onresize = function () {
		$("div.user-content").css({ minHeight:$(window).height()-160 });
	}
});
var testNamePassword = /^[\da-zA-Z]{6,20}$/;
$("#oldPassword").change(function() { 
	if ($("#oldPassword").val() == ""){
		optTip2("请输入原密码！");
		$("#oldPassword").focus();
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
		optTip2("请输入新密码！");
		$("#password").focus();
		return false;
	}
});
$("#retypePassword").change(function() { 
	if ($("#retypePassword").val() == ""){
		optTip2("请输入确认密码！");
		$("#retypePassword").focus();
		return false;
	}
	if(!testNamePassword.test($("#retypePassword").val())){
		optTip2("请输入6~20位的确认密码!");
		$("#retypePassword").focus();
		return false;
	}
	if ($("#password").val() != $("#retypePassword").val()){
		optTip2("两次输入的密码不相同！");
		$("#retypePassword").focus();
		return false;
	}
});
$("#submitChangePassword").click(function() {
	$.ajax({
		url: "<%=apath%>/login/userChangePassword?format=json",
		async: false,
		data: {"oldPassword":$("#oldPassword").val(),
			"password":$("#password").val()},
		type: "POST",
		success: function(data) {
			//更改成功
			if(data.state=="1"){
				optTip(true);
			}else{
				optTip2(data.message);
			}
		},error: function(){
			consles.log("registerError");
		}
	});
});
</script>
</html>