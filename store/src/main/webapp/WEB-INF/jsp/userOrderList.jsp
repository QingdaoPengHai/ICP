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
<script type="text/javascript" src="<%=apath%>/resources/js/jquery.js"></script>
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
					<dt><a href="<%=apath%>/login/toUserOrderList" class="active" style="color: #e4393c;font-weight: bold;">我的订单</a></dt>
					<dt><a href="<%=apath%>/login/toUserInfo">我的信息</a></dt>
					<dt><a href="<%=apath%>/login/toUserChangePassword">修改密码</a></dt>
				</dl>									    	
			</div>
			<div class="user-right" style="width:80%;max-width: 1050px;">
				<div class="user-content-info">
					 	
					 	
<div id="mainContainer" data-react-passed="page/orders/main"><div class="ordersContainer">
<div class="ordersPagination"><div class="filters"></div>
<div class="pages"><span class="page_index">共${fn:length(ordersAndGoods)}个订单</span>
<!-- <a href="javascript:;" class="btn unactive">上一页</a><a href="javascript:;" class="btn unactive">下一页</a> -->
</div></div>					 	
<!-- 订单列表 -->
<div class="ordersList">

<c:forEach items="${ordersAndGoods}" var="order">
<!-- 订单1 -->
<div class="ordersBox"><div class="order"><div class="title"><div class="time"><div class="inner">
<p>${order.buyTime}</p><p>订单编号：${order.orderCode}</p></div></div>
<div class="status"><div class="inner"><p>
<%-- <span>订单状态：</span> --%>

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
				    <p class="name"><em>已创建</em></p>
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



</div></div>
<!-- end 订单列表 -->					 	
					 	
					 		 
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
<script type="text/javascript">
$(document).ready(function(){
	var h1= $(window).height(); 
	h1=h1-160;
	$("div.user-content").css({		minHeight: h1	});
	window.onresize = function () {
		$("div.user-content").css({ minHeight:$(window).height()-160 });
	}
});
</script>
</body>
</html>