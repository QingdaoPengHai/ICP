<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>
<!-- c标签导入 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>应用商店</title>
<link href="<%=apath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/css/style.css" rel="stylesheet" type="text/css"/>
<script  type="text/javascript" src="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/bootstrap.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/main.js"></script>
<script type="text/javascript" src="<%=apath%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=apath%>/resources/js/superslide.2.1.js"></script>

</head>
	<body>
		<div id="mainContent" class="mainContent">
			<!-- 头导航栏 -->
				<div class="head">
		<div class="topnavbar" style="width:95%;max-width:1180px;">
		    <div class="toplogo">
			     <a class="headlogo" href="<%=apath%>/goodsList/toHomePage"><img src="<%=apath%>/resources/images/penghaiLogo.png" title=""></a>
		    </div>
	    	<div class="narmenu">
	    		<div class="searchgroup">	
					<input type="text" placeholder="请在此处搜索" class="form-control searchinpt" id="searchinpt">
					<button  id="search" name="search" aria-label="搜索" class="search-glyph" title="搜索" onclick="searchGoodsListByGoodName()"><img src="<%=apath%>/resources/images/searchicon.png"></button>
		    	</div>
		        <a class="shopping-car" href="<%=apath%>/shoppingCart/toShoppingCart">
	                <span class="s-car-img"></span>
	                <span class="shopping-cart-amount">0</span>
           		</a>
           		<c:set var="nickname" value="${nickname}"/>
           		<c:set var="email" value="${email}"/>
           		<c:set var="userId" value="${userId}"/>
           		<c:choose>  
				   <c:when test="${not empty nickname and nickname!='null'}">
				   	 <a href="<%=apath%>/login/toPersonalCenter" class="login">${nickname}</a>
				   </c:when>  
				   <c:when test="${not empty email and email!='null'}"> 
				   	 <a href="<%=apath%>/login/toPersonalCenter" class="login">${email}</a>
				   </c:when>      
				   <c:otherwise>  
					 <a href="<%=apath%>/login/toLogin" class="login" style="float:left;padding-left: 18px;">登录</a>
					 <a class="login" href="<%=apath%>/register/toRegister" style="padding-left: 18px;cursor:pointer;">
		                        <em>注册</em>
	                    </a>
				   </c:otherwise> 
				 </c:choose> 
           		<div></div>
   		    </div>
   		   
	    </div>  
	    <div class="menutitle">
	    	<div class="classifmodal" style="width:90%;max-width:1180px;">
	    		<div  class="menu-gud">
	    		<h1>Store</h1>
	    		</div>
	    		<!--//下拉列表-->
	    	</div>	    				        
	    </div>

    </div><!--head结束-->
			<!-- end 头导航栏 -->
			<div class="conCatagory">
			<div style="font-size:40px;text-align:center;padding:10px;">购买成功！
			<div style="font-size:26px;text-align:center;padding:10px;">
				<a href="<%=apath%>/goodsList/toHomePage" class="display-inline-block">继续购买</a>
				<a href="<%=apath%>/login/toUserOrderList?userId=${userId}" class="display-inline-block">查看订单</a>
			</div>
			</div>
			
		</div>
		<!-- foot --> 
			<div class="footer">
				<div class="f-info">
		    		<h6>Copyright © 2017-2020 鹏海软件 鲁ICP备10205373号 </h6>
		   		</div>
			</div><!--footer结束-->
		</div>
<script type="text/javascript">
$(document).ready(function(){
	var h1= $(window).height(); 
	h1=h1-160;
	$("div.conCatagory").css({		minHeight: h1	});
	window.onresize = function () {
		$("div.conCatagory").css({ minHeight:$(window).height()-160 });
	}
});
</script>

	</body>
</html>
