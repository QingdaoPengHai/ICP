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
<script type="text/javascript" src="<%=apath%>/resources/js/superslide.2.1.js"></script>
<script src="<%=apath%>/resources/js/api.js"></script>
</head>
<body>
<div id="mainContent" class="mainContent">
	<%@ include file="/WEB-INF/jsp/header.jsp"%><!--head结束-->

	<div class="content">	
		<div class="conCatagory" style="width:90%;max-width:900px;">
			<!-- 商品列表 -->
			<div class="right-prolist" style="width:100%;">
				<div class="right-title">
						<h2>搜索结果</h2>
					</div>
				<div class="products-list">
					<c:forEach items="${searchResult.list}" var="good" varStatus="vs">
						<div class="col">
						<a href="<%=hpath%>/goodDetail/toGoodDetail?goodsId=${good.id}">
							<div class="image">
								<img src="${good.defaultPicture}">
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
	var h1= $(window).height(); 
	h1=h1-160;
	$("div.content").css({		minHeight: h1	});
	window.onresize = function () {
		$("div.content").css({ minHeight:$(window).height()-160 });
	}
});
</script>
</body>
</html>
