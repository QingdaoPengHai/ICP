<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心</title>
<link href="<%=apath%>/resources/css/style.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/css/orderList.css" rel="stylesheet" type="text/css"/>
<script  type="text/javascript" src="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<body>
<div id="mainContent" class="mainContent">
<!-- 头导航栏 -->
  <div class="head" style="z-index:10;">
		<div class="topnavbar">
		    <div class="toplogo">
			    <a class="headlogo" href="<%=apath%>/login/toUserOrderList"><img src="<%=apath%>/resources/images/logo.jpg" title=""></a>
		    </div>
	    	<div class="narmenu" style="width: 150px;">

           		<c:set var="nickname" value="${nickname}"/>
           		<c:set var="email" value="${email}"/>
           		<c:set var="userId" value="${userId}"/>
           		<c:choose>  
				   <c:when test="${not empty nickname and nickname!='null'}">
                    <a class="login" href="<%=apath%>/login/toUserOrderList" style="float:left;padding-left: 18px;">
		                        <em>${nickname}</em>
	                    </a>
	                    <a class="login"  onclick="logout()" style="padding-left: 18px;cursor:pointer;">
		                        <em>注销</em>
	                    </a>
				   </c:when>  
				   <c:when test="${not empty email and email!='null'}"> 
				   	<%--  <a href="<%=hpath%>/login/toPersonalCenter" class="login">${email}</a> --%>
				   	 <a class="login" href="<%=apath%>/login/toUserOrderList" style="float:left;padding-left: 18px;">
		                        <em>${email}</em>
	                    </a>
	                    <a class="login"  onclick="logout()" style="padding-left: 18px;cursor:pointer;">
		                        <em>注销</em>
	                    </a>
				   </c:when>      
				   <c:otherwise>  
					 <a href="<%=apath%>/login/toLogin" class="login">登录</a>
				   </c:otherwise>  
				</c:choose>  
           		
   		    </div>
   		    
	    </div>  
			<div class="menutitle">
		    	<div class="classifmodal">
		    		<div  class="menu-gud">
		    		<h1>存储系统</h1>
		    		</div>
		    		<!--//下拉列表-->
		    	</div>	    				        
		    </div>
    </div><!--head结束-->

<!-- end 头导航栏 -->
	<div class="user-content">	
		<div class="user-con">
			<div class="user-left-menu">			
				<dl class="side-menutree">
					<dt><a href="<%=apath%>/login/toUserOrderList">我的订单</a></dt>
					<dt><a href="<%=apath%>/login/toUserLinker" class="active" style="color: #e4393c;font-weight: bold;">Linker</a></dt>
					<dt><a href="<%=apath%>/login/toUserDatabase">数据库</a></dt>
				</dl>											    	
			</div>
			<div class="user-right">
				<div class="user-content-info">
					 	
					 	
<div id="mainContainer"><div class="ordersContainer">
<div class="ordersPagination"><div class="filters"></div>
<div class="pages"><span class="page_index">共${fn:length(linkerInfo)}个Linker</span>
<script>
    $(function () {
 	   var linkerInfo = "${linkerInfo}"; 
 	  
    });
</script>
</div></div>					 	
<!-- linker列表 -->
<div class="ordersList">

<c:forEach items="${linkerInfo}" var="linker">
<!-- linker -->
<div class="ordersBox"><div class="order"><div class="title"><div class="time"><div class="inner">

<p id="registerTime">注册时间：</p><p></p></div></div>
<script type="text/javascript">
  var date = new Date('${linker.registerTime}');
  datelocal = date.toLocaleString();
  $("#registerTime").html("注册时间："+datelocal);
</script>
<div class="status"><div class="inner">
<p><span> </span></p></div></div>
<div class="payInfo"><div class="inner"><!-- <p class="payMethod"><span>支付方式：</span><span>支付宝</span></p> -->
<p></p></div></div>
<div class="link"><div class="inner"><p><!-- <a href="/my-account/order/1661573256">订单详情</a> --></p></div></div></div>
<div class="con">
<div class="paymentBar">
</div>
<div class="products">
   		<div class="product"><div class="mainCon">
   		<div class="productInfos">
			<div class="img" style="width:0px;"><a href="">
			
			 </a></div>
			 <div class="otherInfo" style="width:100%;">
			   <div class="desc" style="width:47%"><p class="name">
				 linkerId:<br>${linker.linkerId}</p><!-- <p class="variety"> 电子下载版</p><span>0</span> -->
				 </div>
				 <div class="price"  style="width:33%"><%-- <p>价格：￥${good.goodPrice}</p><p>数量：${good.goodNumber}</p> --%>
				</div>
				<div class="desc"  style="width:15%">
				 <p class="name"><em>${linker.linkStatusWord}</em></p>
				</div>
			</div>
			
		</div>
		</div>
		</div>
			
</div>
</div></div></div>
<!-- end 订单1 -->
</c:forEach>  



</div>
<!-- end 订单列表 -->					 	
					 	
					 		 
				</div><!--user-content-info结束-->
			</div><!--user-right结束	-->	
			<div class="clear"></div>
		</div><!--user-con结束-->	
	</div><!--user-content结束-->		
	<div class="clear"></div>	
	<div class="footer">
		<div class="f-info">
	    	<h6>Copyright © 2017-2020 鹏海软件 鲁ICP备10205373号 </h6>
	    </div>	
	</div>
</div>	
</div>

</body>

<script type="text/javascript">
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

