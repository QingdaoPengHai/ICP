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
<script  type="text/javascript" src="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/bootstrap.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/main.js"></script>
<%-- <script type="text/javascript" src="<%=apath%>/resources/js/jquery.js"></script> --%>
<script type="text/javascript" src="<%=apath%>/resources/js/superslide.2.1.js"></script>
<link href="<%=apath%>/resources/css/orderList.css" rel="stylesheet" type="text/css"/>
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
					<dt><a href="<%=apath%>/login/toUserInfo" class="active" style="color: #e4393c;font-weight: bold;">我的信息</a></dt>
					<dt><a href="<%=apath%>/login/toUserChangePassword">修改密码</a></dt>
				</dl>									    	
			</div>
			<div class="user-right" style="width:80%;max-width: 1050px;">
				<div class="user-content-info">
					 	
					 	
<div id="mainContainer" style="min-height:500px;">

<form id="registerForm" ><div id="Credentials">
      <!-- 注册提示 -->
	  <div id="errorInfo" class="alert alert-danger" style="display: none;"></div>
            <div id="CredentialsInputPane" class="container">
                <div class="row">
                    
                    <div class="form-group col-xs-6 col-xs-offset-1">
                        <label class="text-base" for="nickname">昵称</label>
                        <input id="nickname" class="form-control" name="nickname" readonly="readonly" value="${user.nickname}">
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-xs-6 col-xs-offset-1">
                        <label class="text-base" for="email">电子邮箱</label>
                        <input id="email" class="form-control" name="email" readonly="readonly" value="${user.email}">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                    <label class="text-base" for="enterpriseName">企业全称</label>
                    <input id="enterpriseName" class="form-control" name="enterpriseName" readonly="readonly" value="${user.enterpriseName}">
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                    <label class="text-base" for="organizationCode">组织机构代码号</label>
                    <input id="organizationCode" class="form-control" name="organizationCode" readonly="readonly" value="${user.organizationCode}">
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                    <label class="text-base" for="companyAddress">地址</label>
                    <input id="companyAddress" class="form-control" name="companyAddress" readonly="readonly" value="${user.companyAddress}">
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                    <label class="text-base" for="contacts">联系人</label>
                    <input id="contacts" class="form-control" name="contacts" readonly="readonly" value="${user.contacts}">
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 form-group col-xs-offset-1">
                        <label class="text-base" for="companyTel">联系电话</label>
                        <input id="companyTel" class="form-control" name="companyTel" readonly="readonly" value="${user.contacts}">
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
</div>
<!-- foot.jsp -->
<%@ include file="/WEB-INF/jsp/foot.jsp"%>
<!-- end foot.jsp -->
<script type="text/javascript">
$(document).ready(function(){
	var h1= $(window).height(); 
	h1=h1-160;
	$("div.user-content").css({		minHeight: h1	});
	window.onresize = function () {
		$("div.user-content").css({ minHeight:$(window).height()-160 });
		if($(window).width()<600){
			window.resizeTo(600,$(window).height());
		}
	}
});
</script>
</body>
</html>