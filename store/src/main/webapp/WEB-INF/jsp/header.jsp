<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
String hpath = request.getContextPath();
/* String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/"; */
%>
<link href="<%=hpath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="<%=hpath%>/resources/css/style.css" rel="stylesheet" type="text/css"/>
<script  type="text/javascript" src="<%=hpath%>/resources/js/main.js"></script>
<script  type="text/javascript" src="<%=hpath%>/resources/js/bootstrap.js"></script>
  <div class="head" style="z-index:10;">
		<div class="topnavbar" style="width:95%;max-width:1180px;min-width:720px;">
		    <div class="toplogo">
			    <a class="headlogo" href="<%=hpath%>/goodsList/toHomePage"><img src="<%=hpath%>/resources/images/penghaiLogo.png" title=""></a>
		    </div>
	    	<div class="narmenu">
	    		<div class="searchgroup">	
					<input type="text" placeholder="请在此处搜索" class="form-control searchinpt" id="searchinpt">
					<button  id="search" name="search" aria-label="搜索" class="search-glyph" title="搜索" onclick="searchGoodsListByGoodName()"><img src="<%=hpath%>/resources/images/searchicon.png"></button>
		    	</div>
		        <a class="shopping-car" href="<%=hpath%>/shoppingCart/toShoppingCart">
	                <span class="s-car-img"></span>
	                <span id="shoppingCartCount" class="shopping-cart-amount"></span>
           		</a>
           		<c:set var="nickname" value="${nickname}"/>
           		<c:set var="email" value="${email}"/>
           		<c:set var="userId" value="${userId}"/>
           		<c:choose>  
				   <c:when test="${not empty nickname and nickname!='null'}">
                      <a class="login" href="<%=hpath%>/login/toUserOrderList" style="float:left;padding-left: 18px;">
		                        <em>${nickname}</em>
	                    </a>
	                    <a class="login"  onclick="logout()" style="padding-left: 18px;cursor:pointer;">
		                        <em>注销</em>
	                    </a>
				   </c:when>  
				   <c:when test="${not empty email and email!='null'}"> 
				   	<%--  <a href="<%=hpath%>/login/toPersonalCenter" class="login">${email}</a> --%>
				   	 <a class="login" href="<%=hpath%>/login/toUserOrderList" style="float:left;padding-left: 18px;">
		                        <em>${email}</em>
	                    </a>
	                    <a class="login" onclick="logout()" style="padding-left: 18px;cursor:pointer;">
		                        <em>注销</em>
	                    </a>
				   </c:when>      
				   <c:otherwise>  
					 <a href="<%=hpath%>/login/toLogin" class="login" style="float:left;padding-left: 18px;">登录</a>
					 <a class="login" href="<%=hpath%>/register/toRegister" style="padding-left: 18px;cursor:pointer;">
		                        <em>注册</em>
	                    </a>
				   </c:otherwise>  
				</c:choose>  
           		
   		    </div>
   		   
	    </div>  
	    <div class="menutitle" style="min-width:720px;">
	    	<div class="classifmodal" style="width:90%;max-width:1180px;">
	    		<div  class="menu-gud">
	    			<a href="###" id="btnallclassif" class="btn-classification">全部分类</a>
	    		</div>
	    		<!--//下拉列表-->
	    		<div id="navtreeview" class="navtreeview">	
	    		<ul class="sidebar-menu">
	    		<c:forEach items="${doubleLevel}" var="firstLevel">
	    			<li class="treeview">
							<a href="#">
							  <i class="fa"></i>
							  <span>${firstLevel.parentName}</span>
							</a>
							<ul class="treeview-menu" style="display: none;">
							  <c:forEach items="${firstLevel.children}" var="secondLevel">
							  	<li><a href="<%=hpath%>/category/toProducts?categoryId=${secondLevel.childrenId}&categoryName=${secondLevel.childrenName}"><i class="fa fa-circle-o"></i>${secondLevel.childrenName}</a></li>
							  </c:forEach>  
							</ul>
						</li>
				</c:forEach>    				
					</ul>					
				</div>	
	    	</div>	    				        
	    </div>
    </div><!--head结束-->
<script type="text/javascript">
	$(function(){
		getShoppingCartCount();
	});
     function getShoppingCartCount(){
 		$.ajax({
 			url : "<%=hpath%>/shoppingCart/getShoppingCartCount",
 			data : {},
 			type:"POST",
 			dataType:"json",
 			async : false,
 			success : function(json){
 				$('#shoppingCartCount').text(json.result);
 			},error: function(a){
 				
 			}
 		});
 	}
    //点击搜索按钮调用根据商品名称搜索商品方法
    function searchGoodsListByGoodName(){
 		var goodName = $('#searchinpt').val();
 		if(""==goodName){
			$('#searchinpt').attr("placeholder","请输入商品名称！");
		}else{
			var nameUTF8 = encodeURI(encodeURI(goodName));
			window.location.href="<%=hpath%>/category/searchGoodsByGoodName?goodName="+nameUTF8;
		}
    }
    
function logout(){
	$.ajax({
		url : "<%=hpath%>/login/logout",
		type:"POST",
		dataType:"json",
		async : false,
		success : function(json){
			window.location.href = "<%=hpath%>/goodsList/toHomePage";
		}
	});
}    
    
    
     </script>