<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>购物车</title>
<%-- <link href="<%=apath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/css/style.css" rel="stylesheet" type="text/css"/> --%>
<link href="<%=apath%>/resources/css/shoppingCart.css" rel="stylesheet" type="text/css"/>

<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/charisma-app.css">
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/jquery.noty.css">
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/noty_theme_default.css">

<script src ="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script src ="<%=apath%>/resources/js/jquery.noty.js"></script>
<script src ="<%=apath%>/resources/js/charisma.js"></script>
</head>
<body id="mainContent" class="mainContent">
<!-- 头导航栏 -->
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<!-- end 头导航栏 -->
<div id="mainContainer">
<div><div class="cartContainer clearfix" ><div>
<div class="main-wrapper" >
<div class="title clearfix" ><div class="topic">
<header><h1><span >购物车内有</span><span class="h-l" >${shoppingCart.count}</span><span>件商品</span></h1></header>
</div></div>
<!-- 购物车列表 -->
<div class="cartList">
	<header class="clearfix mobileCheckout" ><p class="col-1" >商品图片</p><p class="col-2" >名称和规格</p><p class="col-4">价格</p><p class="col-3">数量</p>
	</header>
<!-- 商品显示 -->
<c:forEach items="${shoppingCart.shoppingCartList}" var="shoppingCartOne" varStatus="vs">
	<div class="cart-item">
		<div class="clearfix cart-item-tray">
			<div class="col-1">
				<div class="img">
					<a href="<%=apath%>/goodDetail/toGoodDetail?goodsId=${shoppingCartOne.goodsId}">
						<img class="product-img" src="${shoppingCartOne.pictureURL}">
					</a>
				</div>
				<p class="del">
					<a href="#" onclick="deleteShoppingCart(${shoppingCartOne.goodsId})">删除商品</a>
				</p>
			</div>
			<div class="col-right">
				<div class="col-2 info">
					<h2>
						<a href="<%=apath%>/goodDetail/toGoodDetail?goodsId=${shoppingCartOne.goodsId}">${shoppingCartOne.goodsName}</a>
					</h2>
					<p>${shoppingCartOne.description}</p>
				</div>
				<div class="col-4 price">
					<p>¥ ${shoppingCartOne.goodsPrice}</p>
				</div>
				<div class="col-3 quantity clearfix">
					<div>
						<span class="vector sub" name="sub">
							<svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%" viewBox="0 0 250 25" version="1.1">
							    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
							        <rect id="Rectangle-1-Copy-2" fill="#666666" x="0" y="0" width="250" height="25"></rect>
							    </g>
							</svg>
						</span>
						<input type="tel" value="${shoppingCartOne.goodsNumber}" style="vertical-align:top;" readonly="readonly">
						<span class="vector plus" name="plus">
							<svg xmlns="http://www.w3.org/2000/svg"width="100%" height="100%" viewBox="0 0 250 251" version="1.1">
							    <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
							        <g id="Rectangle-1-+-Rectangle-1-Copy" fill="#666666">
							            <rect id="Rectangle-1" x="0" y="113" width="250" height="25"></rect>
							            <rect id="Rectangle-1-Copy" transform="translate(125.000000, 125.500000) rotate(-90.000000) translate(-125.000000, -125.500000) " x="0" y="113" width="250" height="25"></rect>
							        </g>
							    </g>
							</svg>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:forEach>

</div><!-- end 购物车列表 -->

</div>
<div class="sider-wrapper" style="">
<div class="checkoutBox fixed" style="top:50%; z-index:0;background-color:#f7f7f7;padding:10px;">
<div class="clearfix allWrap" style="background-color:#f7f7f7;"><p class="f-l">总计</p>
<p class="price-num f-r">¥ ${shoppingCart.totalPrice}</p></div>
<div class="clearfix" style="background-color:#f7f7f7;">
<a href="javascript:;" onclick="submitOrder()" class="widgetButton checkOut f-r">结算</a>
</div>
<!-- <p style="">支付方式：支付宝/微信支付/银联/网银支付/货到付款</p> -->
</div></div></div>
</div></div></div>

<!-- foot.jsp -->
<%@ include file="/WEB-INF/jsp/foot.jsp"%>
<!-- end foot.jsp -->

</body>

<script type="text/javascript">
$(document).ready(function(){
	var h1= $(window).height(); 
	h1=h1-160;
	$("#mainContainer").css({		minHeight: h1	});
	window.onresize = function () {
		$("#mainContainer").css({ minHeight:$(window).height()-160 });
		mainContainer
	}
});
	     function deleteShoppingCart(goodsId){
	    	 $.ajax({
		 			url : "<%=apath%>/shoppingCart/deleteShoppingCart",
		 			data : {"goodsId":goodsId},
		 			type:"POST",
		 			dataType:"json",
		 			async : false,
		 			success : function(json){
		 				if(json.result=='1'){
		 					alert("删除成功！");
		 					optTip2("删除成功！");
		 					
		 					reload();
		 					//setTimeout(alert(111),5000);
		 					//setTimeout(reload(),5000);
		 				}else{
		 					optTip2("删除失败，请刷新页面后重试！");
		 				}
		 			},error: function(a){
		 				optTip2("删除失败，请刷新页面后重试！");
		 			}
		 		});
	     }
	     function submitOrder(){
//	    	 alert('${nickname}');
	    	 if('${nickname}' != null && '${nickname}' != 'null'&& '${nickname}' != ''){
	    		 $.ajax({
			 			url : "<%=apath%>/shoppingCart/submitOrder",
			 			data : {},
			 			type:"POST",
			 			dataType:"json",
			 			async : false,
			 			success : function(json){
			 				if(json.result=='1'){
			 					optTip2("结算成功！");

			 					window.location.href="<%=apath%>/shoppingCart/orderSubmitSuccess";
			 				}else if(json.result=='2'){
			 					optTip2("购物车为空，无法提交！");
			 				}else{
			 					optTip2("提交失败，请刷新页面后重试！");
			 				}
			 			},error: function(a){
			 				optTip2("提交失败，请刷新页面后重试！");
			 			}
			 		});
	    	 }else{
	    		 window.location.href="<%=apath%>/login/toLogin";
	    	 }
	     }
	     function reload(){
	    	<%--  window.location.href="<%=apath%>/shoppingCart/toShoppingCart"; --%>
	    	setTimeout(window.location.href="<%=apath%>/shoppingCart/toShoppingCart",3000);
	     }
     </script>
</html>