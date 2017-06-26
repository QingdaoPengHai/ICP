<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;">
		<!-- css -->
		<link href="<%=apath%>/resources/css/microsoft/signupv2_zhs_p2EQslf7FAJqr-1ZACkjMw2.css" rel="stylesheet"/>
		<link href="<%=apath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/charisma-app.css">
		<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/jquery.noty.css">
		<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/noty_theme_default.css">
		
		<script src ="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
		<script src ="<%=apath%>/resources/js/jquery.noty.js"></script>
		<script src ="<%=apath%>/resources/js/charisma.js"></script>
		<title>store-register</title>
	</head>

<body class="ltr  Chrome _Win _M57 _D0 Full Win81 RE_WebKit light animate" lang="zh-CN">
    <div class="App" id="iPageElt">
    <div id="c_base" class="c_base">
        <div id="c_content">
    <div id="maincontent">
        <div class="container">
            <div class="row">
            	<div class="head text-subheader col-xs-6 col-xs-offset-1"><img alt="ICP-store" src="<%=apath%>/resources/images/penghaiLogo.png" style="width:25%;"></div>
            </div>
            <div class="row">
            	<div class="head text-subheader col-xs-6 col-xs-offset-1" role="heading">创建帐户</div>
            </div>
            <div class="row">
                <div class="col-xs-6 form-group col-xs-offset-1">
                请使用电子邮箱作为您的 ICP-store&nbsp;帐户的用户名。
                </div>
            </div>
        </div>

    <div id="pageControlHost">
    <div style="opacity: 1;">
    <form id="registerForm" ><div id="Credentials">
      <!-- 注册提示 -->
	  <div id="errorInfo" class="alert alert-danger" style="display: none;"></div>
            <div id="CredentialsInputPane" class="container">
                <div class="row">
                    <!-- <div class="form-group col-xs-9">
                        <label class="text-base" for="LastName">姓名</label>
                        <input type="text" id="LastName" class="form-control" name="LastName">
                        <div class="alert alert-error" data-bind="errorMessage: lastName, visible: showError(lastName)" role="alert" aria-live="assertive" aria-relevant="text" aria-atomic="true" style="display: none;">此信息为必填项。</div>
                    </div> -->
                    <div class="form-group col-xs-6 col-xs-offset-1">
                        <label class="text-base" for="nickname">昵称</label>
                        <input id="nickname" class="form-control" name="nickname" placeholder="昵称" maxlength="20" <c:if test="${not empty nickname and nickname!='null'}">value="<%=request.getParameter("nickname")%>"</c:if>
                        onkeyup="this.value=this.value.replace(/[<|>|,|;|:|\']/g,'')" >
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-xs-6 col-xs-offset-1">
                        <label class="text-base" for="email">电子邮箱(<font style="color:red;">*</font>)</label>
                        <%-- <script type="text/javascript">alert("${email}"+"<%=request.getParameter("email")%>");</script> --%>
                        <input id="email" class="form-control" name="email" placeholder="电子邮件" maxlength="30" <c:if test="${not empty email and email!='null'}">value="<%=request.getParameter("email")%>"</c:if>
                        onkeyup="this.value=this.value.replace(/[<|>]/g,'')">
                    </div>
                </div>
                <!--Password-->
                <div class="row">
                    <div class="form-group col-xs-6 col-xs-offset-1">
                        <label class="text-base" for="password">密码(<font style="color:red;">*</font>)</label>
                        <input type="password" id="password" class="form-control" name="password" placeholder="请输入6~20位由数字和字母组成的密码" maxlength="20" <c:if test="${not empty password}">value="<%=request.getParameter("password")%>"</c:if>
                        onkeyup="this.value=this.value.replace(/[\W]/g,'')">
                        <!-- <div class="text-caption">8-16 个字符，区分大小写</div> -->
                    </div>
                </div>
                <!--Retype Password-->
                <div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                        <label class="text-base" for="retypePassword">重新输入密码(<font style="color:red;">*</font>)</label>
                        <input type="password" id="retypePassword" class="form-control" name="retypePassword" placeholder="请再次输入密码" maxlength="20" <c:if test="${not empty retypePassword}">value="<%=request.getParameter("retypePassword")%>"</c:if>
                        onkeyup="this.value=this.value.replace(/[\W]/g,'')">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                    <label class="text-base" for="enterpriseName">企业全称</label>
                    <input id="enterpriseName" class="form-control" name="enterpriseName" maxlength="30" <c:if test="${not empty enterpriseName}">value="<%=request.getParameter("enterpriseName")%>"</c:if>
                    onkeyup="this.value=this.value.replace(/[<|>]/g,'')">
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                    <label class="text-base" for="organizationCode">组织机构代码号</label>
                    <input id="organizationCode" class="form-control" name="organizationCode" maxlength="30" <c:if test="${not empty organizationCode}">value="<%=request.getParameter("organizationCode")%>"</c:if>
                    onkeyup="this.value=this.value.replace(/[<|>]/g,'')">
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                    <label class="text-base" for="companyAddress">地址</label>
                    <input id="companyAddress" class="form-control" name="companyAddress" maxlength="30" <c:if test="${not empty companyAddress}">value="<%=request.getParameter("companyAddress")%>"</c:if>
                    onkeyup="this.value=this.value.replace(/[<|>]/g,'')">
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                    <label class="text-base" for="contacts">联系人</label>
                    <input id="contacts" class="form-control" name="contacts" maxlength="10" <c:if test="${not empty contacts}">value="<%=request.getParameter("contacts")%>"</c:if>
                    onkeyup="this.value=this.value.replace(/[<|>]/g,'')">
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                        <label class="text-base" for="companyTel">联系电话</label>
                        <input id="companyTel" class="form-control" name="companyTel" maxlength="16" <c:if test="${not empty companyTel}">value="<%=request.getParameter("companyTel")%>"</c:if>
                        onkeyup="this.value=this.value.replace(/[^\d|-]/g,'')" >
                    </div>
                </div>

                <!-- <div class="row">
                    <div class="col-xs-18">单击“创建帐户”<strong></strong>即表示你同意 <a target="_blank" rel="noreferrer noopener" href="https://www.microsoft.com/zh-cn/servicesagreement/default.aspx"> ICP-store 服务协议</a>以及<a target="_blank" rel="noreferrer noopener" href="https://privacy.microsoft.com/zh-cn/privacystatement">隐私和 Cookie 声明</a>。</div>
                </div> -->
				<div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                    <label class="text-base">验证码</label>
                    <input id="code" name="code" class="form-control" style="width:250px;display: inline-block;" maxlength="6"/> 
                    <img id="imgCode" src="<%=apath%>/register/validateCode" />
        			<a href='#' onclick="javascript:changeImg()" style="display: inline-block;"><label style="display: inline-block;">看不清？</label></a>
        			 
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 col-xs-offset-1">
                        <input type="button" id="CredentialsAction" class="btn btn-primary btn-block" title="创建帐户" value="创建帐户">
                    </div>
                </div>
            </div>

        </div></form></div></div>

   </div> </div></div> </div>
    
</body>
<script type="text/javascript">
$(function(){
	$(window).keydown(function(event){
		var keyCode =  event.keyCode;
		if(keyCode == 13){
			checkandSubmit();
		}		
	});
	$("#CredentialsAction").click(function(){
		checkandSubmit();
	});
});
var testNamePassword = /^[\da-zA-Z]{6,20}$/;
$("#email").change(function() { 
	if ($("#email").val() == ""){
		optTip2("请输入电子邮箱！");
		$("#email").focus();
		return false;
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
function checkandSubmit(){
	if ($("#email").val() == ""){
		optTip2("请输入电子邮箱！");
		$("#email").focus();
		return false;
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
	$.ajax({
		url: "<%=apath%>/register/userRegister?format=json",
		async: false,
		data: $("#registerForm").serialize(),
		type: "POST",
		success: function(data) {
			//注册成功
			if(data.state=="1"){
				window.location.href = "<%=apath%>/goodsList/toHomePage";
			}else{
				optTip2(data.message);
				/* $("errorInfo").style.display = ""; 
				$("errorInfo").text(data.message); */
			}
		},error: function(){
			consles.log("registerError");
		}
	});
}



function changeImg(){
    var img = document.getElementById("imgCode"); 
    img.src = "<%=apath%>/register/validateCode?date=" + new Date();
    $("#code").focus();
}
</script>
</html>
