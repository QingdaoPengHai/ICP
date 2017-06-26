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
<link href="<%=apath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<script  type="text/javascript" src="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/bootstrap.js"></script>
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
		    		<h1 style="margin:0;line-height: 1.1">存储系统</h1>
		    		</div>
		    		<!--//下拉列表-->
		    	</div>	    				        
		    </div>
    </div><!--head结束-->

<!-- end 头导航栏 -->
	<div class="user-content" style="padding-bottom: 0px; min-width:740px;">	
		<div class="user-con" style="width:90%;max-width: 1200px;">
			<div class="user-left-menu" style="width:20%;max-width:120px;">			
				<dl class="side-menutree">
					<dt style="font-weight: bold;"><a href="<%=apath%>/login/toUserOrderList" style="color: #337ab7;">我的订单</a></dt>
					<dt style="font-weight: bold;"><a href="<%=apath%>/login/toUserLinker" class="active" style="color: #e4393c;font-weight: bold;">Linker</a></dt>
					<dt style="font-weight: bold;"><a href="<%=apath%>/login/toUserDatabase" style="color: #337ab7;">数据库</a></dt>
				</dl>											    	
			</div>
			<div class="user-right" style="width:80%;max-width: 1050px;">
				<div class="user-content-info">
					 	
					 	
<div id="mainContainer"><div class="ordersContainer">
<div class="ordersPagination"><div class="filters"></div>
<div class="pages"><span class="page_index">共${fn:length(linkers)}个Linker</span>
<script>
    $(function () {
 	   var linkerInfo = "${linkers}"; 
 	  
    });
</script>
</div></div>					 	
<!-- linker列表 -->
<div class="ordersList">

<c:forEach items="${linkers}" var="linker" varStatus="vs">
<!-- linker -->
<div class="ordersBox"><div class="order"><div class="title"><div class="time"><div class="inner">

<p id="registerTime${vs.count}">注册时间：</p><p></p></div></div>
<script type="text/javascript">
  var date = new Date('${linker.registerTime}');
  datelocal = date.toLocaleString();
  $("#registerTime${vs.count}").html("注册时间："+datelocal);
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
				 <input type="button" onclick="getReports('${linker.linkerId}')" style="background-color: #4CAF50;    color: white;    padding: 10px;    font-size: 16px;    border: none;    cursor: pointer;" value="查看错误报告">
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
	
  </div>	
    <div class="footer">
		<div class="f-info">
	    	<h6 style="margin-top:0px;margin-bottom:0px;">Copyright © 2017-2020 鹏海软件 鲁ICP备10205373号 </h6>
	    </div>	
	</div>
</div>

<!-- 错误报告 模态框 -->
<div class="modal fade" id="linkerErrorInfosModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>错误报告</h3>
			</div>
			<div class="modal-body">
				<table id="linkerErrorInfosTable" class="table table-bordered table-striped tablelist">
				  <thead>
					<tr>
						<th class="col-lg-3 col-md-3 col-sx-3">报告类型</th>
						<th class="col-lg-3 col-md-3 col-sx-3">详细信息</th>
						<th class="col-lg-3 col-md-3 col-sx-3">报告时间</th>
					</tr>
				</thead>
				<tbody id="tableBody">
				</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- end错误报告 模态框 -->
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
function getReports(linkerId){
	//showdata
	$.ajax({
		url : "<%=apath%>/login/getLinkerReport",
		data:{"linkerId":linkerId},
		type:"POST",
		dataType:"json",
		async : false,
		success : function(json){
			var data = json.LinkerReports;
			var h = "";
			if (data == null || data == "") {
				h += '<tr><td valign="top" colspan="15" class="dataTables_empty">抱歉， 没有找到</td></tr>';
				$("#tableBody").html(h);
			} else {
				$(data).each(
						function(i, linkeReport) {
							h += '<tr>';
							h += '<td>' + linkeReport.reportErrorType + '</td>';
							h += '<td>' + linkeReport.reportErrorDetail + '</td>';
							//var da = new Date(linkeReport.reportErrorTime);
							//h += '<td>' + da.toLocaleString() + '</td>';
							h += '<td>' + linkeReport.reportErrorStringTime+ '</td>';
							h += '</tr>';
				    });
				$("#tableBody").html(h);
			}
		}
	});
	$("#linkerErrorInfosModal").modal();
}
</script>
</html>

