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
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/charisma-app.css">
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/jquery.noty.css">
<link href="<%=apath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/noty_theme_default.css">
<script  type="text/javascript" src="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script src ="<%=apath%>/resources/js/jquery.noty.js"></script>
<script src ="<%=apath%>/resources/js/charisma.js"></script>
<body>
<div id="mainContent" class="mainContent">
<!-- 头导航栏 -->
  <div class="head" style="z-index:10;">
		<div class="topnavbar" style="width:95%;max-width:1180px;min-width:720px;">
		    <div class="toplogo">
			    <a class="headlogo" href="<%=apath%>/login/toUserOrderList"><img src="<%=apath%>/resources/images/penghaiLogo.png" title=""></a>
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
			<div class="menutitle" style="min-width:720px;">
		    	<div class="classifmodal" style="width:90%;max-width:1180px;">
		    		<div  class="menu-gud">
		    		<h1 style="margin:0;">存储系统</h1>
		    		</div>
		    		<!--//下拉列表-->
		    	</div>	    				        
		    </div>
    </div><!--head结束-->

<!-- end 头导航栏 -->
	<div class="user-content" style="padding-bottom: 0px; min-width:740px;"">	
		<div class="user-con" style="width:90%;max-width: 1200px;">
			<div class="user-left-menu" style="width:20%;max-width:120px;">			
				<dl class="side-menutree">
					<dt style="font-weight: bold;"><a href="<%=apath%>/login/toUserOrderList" class="active" style="color: #e4393c;font-weight: bold;">我的订单</a></dt>
					<dt style="font-weight: bold;"><a href="<%=apath%>/login/toUserLinker" style="color: #337ab7;">Linker</a></dt>
					<dt style="font-weight: bold;"><a href="<%=apath%>/login/toUserDatabase" style="color: #337ab7;">数据库</a></dt>
				</dl>											    	
			</div>
			<div class="user-right" style="width:80%;max-width: 1050px;">
				<div class="user-content-info">
					 	
					 	
<div id="mainContainer" data-react-passed="page/orders/main"><div class="ordersContainer">
<div class="ordersPagination"><div class="filters"></div>
<div class="pages"><span class="page_index">共${fn:length(ordersAndGoods)}个订单</span>
<script>
    $(function () {
 	   var ordersAndGoods = "${ordersAndGoods}"; 
 	  
    });
</script>
</div></div>					 	
<!-- 订单列表 -->
<div class="ordersList">

<c:forEach items="${ordersAndGoods}" var="order">
<!-- 订单1 -->
<div class="ordersBox"><div class="order"><div class="title"><div class="time"><div class="inner">
<p>${order.buyTime}</p><p>订单编号：${order.orderCode}</p></div></div>
<div class="status"><div class="inner"><p>
<!-- <span>订单状态：</span> -->

</p></div></div>
<div class="payInfo"><div class="inner"><!-- <p class="payMethod"><span>支付方式：</span><span>支付宝</span></p> -->
<p>订单金额：￥${order.totalPrice}</p></div></div>
<div class="link"><div class="inner"><p><!-- <a href="/my-account/order/1661573256">订单详情</a> --></p></div></div></div>
<div class="con">
<div class="paymentBar">
<c:if test="${order.status=='0'} and ${order.isDelete=='0'}">
	<%-- <a class="cancelOrder" style="z-index:3;" href="<%=apath%>/order/cancelOrder?id=${order.id}">取消订单</a> --%>
	<!-- <a class="pay" style="z-index:3;" href="/alipay/requestController/1661573256">付款</a> -->
</c:if>

</div>

<div class="products">
			<c:forEach items="${order.goodList}" var="good">
	    		<div class="product"><div class="mainCon">
	    		<div class="productInfos">
					<div class="img"><a href="<%=apath%>/goodDetail/toGoodDetail?goodsId=${good.goodId}">
					<img style="width:80%" src="${good.goodUrl}">
					 </a></div>
					 <div class="otherInfo">
					   <div class="desc" style="width:47%"><p class="name"><span> </span>
						 <a href="<%=apath%>/goodDetail/toGoodDetail?goodsId=${good.goodId}">${good.goodName} </a></p><!-- <p class="variety"> 电子下载版</p><span>0</span> -->
						 </div>
						 <div class="price"  style="width:33%"><p>价格：￥${good.goodPrice}</p><p>数量：${good.goodNumber}</p>
						</div>
						<div class="desc"  style="width:15%">
						 <c:choose>  
						  <c:when test="${good.status=='0'}">
						    <p class="name"><em>已下单</em></p>
						  </c:when> 
						  <c:when test="${good.status=='1'}">
						    <div>
						    <input id='${good.xmlId}' 
							   	 class="btn btn-block btn-primary" 
							   	 type="button" 
							   	 style="width:100px" 
							   	 onclick="downloadXml('${good.xmlId}')" 
							   	 value="已生成">
							</div>
						  </c:when> 
						  <c:when test="${good.status=='2'}">
						    <p class="name"><em>已下载</em></p>
						  </c:when> 
						  <c:otherwise>  
						    <p class="name"><em>状态异常</em></p>
						  </c:otherwise>  
						</c:choose> 
						</div>
					</div>
					
				</div>
				</div>
				</div>
			</c:forEach>   
			
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
	
</div>

	<div class="footer">
		<div class="f-info">
	    	<h6 style="margin-top:0px;margin-bottom:0px;">Copyright © 2017-2020 鹏海软件 鲁ICP备10205373号 </h6>
	    </div>	
	</div>
  </div>
</body>

<script type="text/javascript">
$(document).ready(function(){
	var h1= $(window).height(); 
	h1=h1-160;
	$("div.user-con").css({		minHeight: h1	});
	window.onresize = function () {
		$("div.user-con").css({ minHeight:$(window).height()-160 });
	}
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

function downloadXml(xmlId){
	$.ajax({
		url : "<%=apath%>/goodsFile/getXmlInfo",
		data: {"xmlId":xmlId},
		type:"POST",
		dataType:"json",
		async : false,
		success : function(json){
			optTip2(json.message);
			if(json.code=="1"){
				window.location.href = "<%=apath%>/login/toUserOrderList";
			}
		}
	});
}
</script>
</html>