<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品分类管理</title>
<link href="<%=apath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/css/style.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/treeTable/themes/vsStyle/treeTable.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/charisma-app.css">
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/jquery.noty.css">
<link href="<%=apath%>/resources/css/noty_theme_default.css" rel="stylesheet" type="text/css">

<script  type="text/javascript" src="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/bootstrap.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/main.js"></script>
<%-- <script type="text/javascript" src="<%=apath%>/resources/js/jquery.js"></script> --%>
<script type="text/javascript" src="<%=apath%>/resources/js/superslide.2.1.js"></script>
<script src ="<%=apath%>/resources/js/jquery.noty.js"></script>
<script src ="<%=apath%>/resources/js/charisma.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/treeTable/jquery.treeTable.js"></script>
</head>
<body>
<!-- 头导航栏 -->
	<div class="head">
		<div class="topnavbar" style="width:95%;max-width:1180px;">
		    <div class="toplogo">
			     <a class="headlogo" href="<%=apath%>/goodsList/toHomePage"><img src="<%=apath%>/resources/images/penghaiLogo.png" title=""></a>
		    </div>
	    	<div class="narmenu" style="width: 150px;">
	    		<%-- <div class="searchgroup">	
					<input type="text" placeholder="请在此处搜索" class="form-control searchinpt" id="searchinpt">
					<button  id="search" name="search" aria-label="搜索" class="search-glyph" title="搜索" onclick="searchGoodsListByGoodName()"><img src="<%=apath%>/resources/images/searchicon.png"></button>
		    	</div> --%>
<%-- 		        <a class="shopping-car" href="<%=apath%>/shoppingCart/toShoppingCart">
	                <span class="s-car-img"></span>
	                <span class="shopping-cart-amount">0</span>
           		</a> --%>
           		<c:set var="nickname" value="${adminNickname}"/>
           		<c:set var="email" value="${adminEmail}"/>
           		<c:set var="userId" value="${adminUserId}"/>
           		<c:choose>  
				   <c:when test="${not empty adminNickname and adminNickname!='null'}">
				   	 <a class="login" style="float:left;padding-left: 18px;">
		                        <em>${adminNickname}</em>
	                    </a>
	                    <a class="login"  onclick="logout()" style="padding-left: 18px;cursor:pointer;">
		                        <em>注销</em>
	                    </a>
				   </c:when>  
				   <c:when test="${not empty adminEmail and adminEmail!='null'}"> 
				   	 <a class="login" style="float:left;padding-left: 18px;">
		                        <em>${adminNickname}</em>
	                    </a>
	                    <a class="login"  onclick="logout()" style="padding-left: 18px;cursor:pointer;">
		                        <em>注销</em>
	                    </a>
				   </c:when>      
				   <c:otherwise>  
					 <a href="<%=apath%>/login/toLogin" class="login">登录</a>
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
	<div class="content">	
		<div class="conCatagory" style="width:90%;max-width:1200px;">
			<div class="left-nav" style="width:20%;max-width: 260px;">
				<div class="left-title">
					<h2>全部分类</h2>
				</div> 
				<!-- 开始侧边栏 -->				
				<ul class="sidebar-menu subnav" id="subnav" style="width:100%;">
	    			<li class="treeview">
							<a style="cursor:pointer;">
							  <i class="fa"></i>
							  <span>订单管理</span>
							</a>
							<ul class="treeview-menu" style="display: block;cursor:pointer;">
							  	<li><a href="<%=apath%>/admin/toOrderManagement" >
							  	<i class="fa fa-circle-o"></i>订单管理</a></li>
							</ul>
						</li>
				</ul>
				<ul class="sidebar-menu subnav" id="subnav" style="width:100%;">
	    			<li class="treeview">
							<a style="cursor:pointer;">
							  <i class="fa"></i>
							  <span>商品管理</span>
							</a>
							<ul class="treeview-menu" style="display: block;cursor:pointer;">
							  	<li><a href="<%=apath%>/admin/toCommodityCategory">
							  	<i class="fa fa-circle-o"></i>商品分类管理</a></li>
							</ul>
							<ul class="treeview-menu" style="display: block;cursor:pointer;">
							  	<li><a href="<%=apath%>/admin/toCommodityManagement">
							  	<i class="fa fa-circle-o"></i>商品管理</a></li>
							</ul>
						</li>
				</ul>											    	
			</div><!-- end 侧边栏 -->
			<!-- 内容页面 -->
			<div id="content" class="right-prolist" style="width:75%;max-width: 900px;">

				<!-- content starts -->
				<jsp:include page="${cmsContent}"/>
				<!-- content ends -->

			</div><!--right-prolist结束	-->	
		</div><!--con结束-->	
	</div><!--content结束-->
<%-- <div style="max-width: 1200px;    margin: 0 auto;    position: relative;    overflow: hidden;">
		<div class="row">

			<div id="content" class="col-lg-10 col-sm-10">

				<!-- content starts -->
				<jsp:include page="${cmsContent}"/>
				<!-- content ends -->

			</div>
		</div>
		<hr>
</div> --%>

	<div class="footer">
		<div class="f-info">
	    <h6>Copyright © 2017-2020 鹏海软件 鲁ICP备10205373号 </h6>
	   </div>
	</div><!--footer结束-->

<script type="text/javascript">
$(document).ready(function(){
	var h1= $(window).height(); 
	h1=h1-160;
	$("div.content").css({		minHeight: h1	});
	window.onresize = function () {
		$("div.content").css({ minHeight:$(window).height()-160 });
	}
});
$(function(){
	$('input').bind('keyup', function () {
		this.value=this.value.replace(/[<|>]/g,'');
		});
});
function logout(){
	$.ajax({
		url : "<%=apath%>/admin/logout",
		type:"POST",
		dataType:"json",
		async : false,
		success : function(json){
			window.location.href = "<%=apath%>/admin/login";
		}
	});
}   
</script>
</body>
</html>
