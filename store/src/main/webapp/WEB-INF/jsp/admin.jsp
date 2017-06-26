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
    <title>管理员系统登录-工业云平台</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/microsoft/Default2052.css">
	<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/charisma-app.css">
	<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/jquery.noty.css">
	<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/noty_theme_default.css">

	<script src ="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
	<script src ="<%=apath%>/resources/js/jquery.noty.js"></script>
	<script src ="<%=apath%>/resources/js/charisma.js"></script>
  </head>

<body class="cb">
<div>
  <form name="loginForm" id="loginForm" method="post">
      <div id="maincontent">
        <section class="section no-margin-bottom"> 
          <div class="section-body container"> 
             <div class="container" id="CredentialsInputPane">
            <div class="row text-body">
            <img class="img-centipede" src="<%=apath%>/resources/images/banner1.jpg" alt="ICP-store">
            <div class="row text-subheader" role="heading">登录</div> 
            <!-- 登录提示 -->
			<div id="errorInfo" class="alert alert-danger" style="display: none;"></div>
            <div class="form-group col-md-24">
            <div class="placeholderContainer">
            <input type="email" name="email" id="email"  maxlength="113" class="form-control ltr_override" aria-label="电子邮箱" placeholder="电子邮箱" > 
            <br>
            <div class="placeholderContainer">
              <input name="password" type="password" id="password" autocomplete="off" class="form-control" aria-describedby="passwordError passwordDesc" placeholder="密码" aria-label="密码" maxlength="18"> 
            </div>
            <br><div id="idTd_PWD_KMSI_Cb" class="form-group checkbox text-block-body no-margin-top col-xs-12"> 
            <label id="idLbl_PWD_KMSI_Cb" style="display:inline-block;"> 
            <input name="KMSI" id="idChkBx_PWD_KMSI0Pwd" type="checkbox" style="display:inline-block;"> 
            <span>使我保持登录状态</span> </label> 
            </div>
            <div class="col-xs-12"> 
            <input type="button" id="submit" class="btn btn-block btn-primary" value="登录"> </div>
            
            </div></div> 
           
            <%-- <div class="row"> 
            <div class="section col-md-24">没有帐户?
            <a href="<%=apath%>/register/toRegister" id="signup" class="display-inline-block" aria-label="创建 Microsoft 帐户">创建一个!</a>
            </div> </div> --%>
           </div> 
            </div>
            </div> </section>
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
		url : "<%=apath%>/admin/checkLogin",
		data : $("#loginForm").serialize(),
		type:"POST",
		dataType:"json",
		async : false,
		success : function(json){
			var data = eval(json); 
			if(data.code=="0"){
				optTip2(data.message);
			}else{
				window.location.href = "<%=apath%>/admin/toOrderManagement";
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