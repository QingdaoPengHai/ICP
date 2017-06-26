<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="<%=apath%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=apath%>/resources/js/superslide.2.1.js"></script>
<link href="<%=apath%>/resources/css/orderList.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="mainContent" class="mainContent">
<!-- 头导航栏 -->
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<!-- end 头导航栏 -->
	<div class="user-content">	
		<div class="user-con">
			<div class="user-left-menu">			
				<%-- <dl class="side-menutree">
					<dt>订单中心</dt>
					<dd>
						<a href="<%=hpath%>/login/toUserOrderList" class="active">我的订单</a>
					</dd>
				</dl> --%>
				<dl class="side-menutree">
					<dt><a href="<%=apath%>/login/toUserOrderList" class="active">我的订单</a></dt>
					<dt><a href="<%=apath%>/login/toUserInfo">我的信息</a></dt>
					<dt><a href="<%=apath%>/login/toUserChangePassword">修改密码</a></dt>
				</dl>										    	
			</div>
			<div class="user-right">
				<div class="user-content-info">
					 	
					 	
<div id="mainContainer" data-react-passed="page/orders/main"><div class="ordersContainer">
<div class="ordersPagination"><div class="filters"></div>

<!-- <a href="javascript:;" class="btn unactive">上一页</a><a href="javascript:;" class="btn unactive">下一页</a> -->
</div></div>					 	
				 	
					 	
					 		 
				</div><!--user-content-info结束-->
			</div><!--user-right结束	-->	
			<div class="clear"></div>
		</div><!--user-con结束-->	
	</div><!--user-content结束-->		
	<div class="clear"></div>	
<!-- 	<div class="footer">
		<div class="f-info">
	    	<h6>Copyright © 2017-2020 鹏海软件 鲁ICP备10205373号 </h6>
	    </div>	
	</div>footer结束 -->
</div>	

<!-- foot.jsp -->
<%@ include file="/WEB-INF/jsp/foot.jsp"%>
<!-- end foot.jsp -->

</body>
</html>