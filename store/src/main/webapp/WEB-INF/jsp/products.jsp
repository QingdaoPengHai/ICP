<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>应用商店-产品列表</title>
<link href="<%=apath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/css/style.css" rel="stylesheet" type="text/css"/>
<script  type="text/javascript" src="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/bootstrap.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/main.js"></script>
<script type="text/javascript" src="<%=apath%>/resources/js/superslide.2.1.js"></script>
<script src="<%=apath%>/resources/js/api.js"></script>
</head>
<body>
<div id="mainContent" class="mainContent">
	<div class="head">
		<div class="topnavbar" style="width:95%;max-width:1180px;min-width:720px;">
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
	    <div class="menutitle" style="min-width:720px;">
	    	<div class="classifmodal" style="width:90%;max-width:1180px;">
	    		<div  class="menu-gud">
	    		<h1>Store</h1>
	    		</div>
	    		<!--//下拉列表-->
	    	</div>	    				        
	    </div>

    </div><!--head结束-->

	<div class="content" style="min-width: 730px;">	
		<div class="conCatagory" style="width:90%;max-width:1200px;">
			<div class="left-nav" style="width:20%;max-width: 260px;">
				<div class="left-title">
					<h2>全部分类</h2>
				</div> 
				<!-- 开始侧边栏 -->				
				<ul class="sidebar-menu subnav" id="subnav" style="width:100%;">
				<c:forEach items="${doubleLevel}" var="firstLevel">
	    			<li class="treeview">
							<a style="cursor:pointer;">
							  <i class="fa"></i>
							  <span>${firstLevel.parentName}</span>
							</a>
							<ul class="treeview-menu" style="display: block;cursor:pointer;">
							  <c:forEach items="${firstLevel.children}" var="secondLevel">
							  	<li><a onclick="refreshGoodsData('${secondLevel.childrenId}','${secondLevel.childrenName}')" >
							  	<i class="fa fa-circle-o"></i>${secondLevel.childrenName}</a></li>
							  </c:forEach>  
							</ul>
						</li>
				</c:forEach>   
					</ul>											    	
			</div><!-- end 侧边栏 -->
			<!-- 商品列表 -->
			<div class="right-prolist" style="width:75%;max-width: 900px;">
				<div class="right-title">
						<h2>${categoryName}</h2>
					</div>
				<div class="products-list">
					<c:forEach items="${categoryGoods.list}" var="good" varStatus="vs">
						<div class="col">
						<a href="<%=apath%>/goodDetail/toGoodDetail?goodsId=${good.id}">
							<div class="image">
								<img src="${good.pictureUrl}">
							</div>
							<div class="pro-tit-inf">
								<h3>${good.goodName}</h3>
								<p id="description${vs.count}"></p>
								<script>
	                               $(function () {
	                                   var descriptionBrief = decodeURI('${good.description}').replace('\n','<br/>').replace(/\n/g,'<br/>');
	                                   if (descriptionBrief.length > 59) {
	                                       var newDescriptionBrief = descriptionBrief.substring(0, 59);
	                                       $("#description${vs.count}").html(newDescriptionBrief + "……");
	                                   } else {
	                                       $("#description${vs.count}").html(descriptionBrief);
	                                   }
	                               });
	                           </script>
							</div>
							<div class="bt-price">
								<p>￥${good.price}</p>
							</div>
						</a>
					  </div>
					</c:forEach> 	
				</div><!--products-list结束-->
			</div><!--right-prolist结束	-->	
			</div><!--con结束-->	
	</div><!--content结束-->		
	 <div class="clear"></div>	

</div>	
<!-- foot.jsp -->
<%@ include file="/WEB-INF/jsp/foot.jsp"%>
<!-- end foot.jsp -->

<script type="text/javascript">
$(document).ready(function(){
//	var clientWidth = document.body.clientWidth;
//	var clientHeight = document.body.clientHeight;
	var h1= $(window).height(); 
//	var w1= $(window).width();   
//	var h2= $(document).height(); 
//	var w2= $(document).width(); 
	console.log("clientWidth:"+document.body.clientWidth);
	console.log("clientHeight:"+document.body.clientHeight);
	console.log("height:"+$(window).height());
	console.log("width:"+$(window).width());
	console.log("height:"+$(document).height());
	console.log("width:"+$(document).width());
	h1=h1-160;
	//$("div.content").height(h1);
	$("div.content").css({		minHeight: h1	});
	window.onresize = function () {
	//	$("div.content").height($(window).height()-190);
		$("div.content").css({ minHeight:$(window).height()-160 });
	}
});

function refreshGoodsData(categoryId,categoryName){

	gapi.load('', function(){
		var url = "<%=apath%>/category/toProducts?categoryId="+categoryId+"&categoryName="+categoryName;
		window.location.href = url;
	    });
	
}
//点击搜索按钮调用根据商品名称搜索商品方法
function searchGoodsListByGoodName(){
		var goodName = $('#searchinpt').val();
		if(""==goodName){
			$('#searchinpt').attr("placeholder","请输入商品名称！");
		}else{
			var nameUTF8 = encodeURI(encodeURI(goodName));
			window.location.href="<%=apath%>/category/searchGoodsByGoodName?goodName="+nameUTF8;
		}
}
</script>
</body>
</html>