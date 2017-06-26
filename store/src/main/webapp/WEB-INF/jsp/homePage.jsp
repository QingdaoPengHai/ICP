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
<link href="<%=apath%>/resources/css/animate.css" rel="stylesheet" type="text/css"/>
<script  type="text/javascript" src="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=apath%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=apath%>/resources/js/superslide.2.1.js"></script>
<script type="text/javascript" src="<%=apath%>/resources/js/wow.min.js"></script>
</head>
<body>
<div id="mainContent" class="mainContent">
<!-- 头导航栏 -->
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<!-- end 头导航栏 -->
	<!-- 轮播开始-->
	<div class="fullSlide">
		<div class="bd">
			<ul>
				<li _src="url(<%=apath%>/resources/images/banner1.jpg)" style="background:center 0 no-repeat;"><a href="#"></a></li>
				<li _src="url(<%=apath%>/resources/images/banner2.jpg)" style="background:center 0 no-repeat;"><a href="#"></a></li>
				<li _src="url(<%=apath%>/resources/images/banner3.jpg)" style="background:center 0 no-repeat;"><a href="#"></a></li>
				
			</ul>
		</div>
		<div class="hd"><ul></ul></div>
		<span class="prev"></span>
		<span class="next"></span>
	</div><!--fullSlide end-->
	  
	  
	<script type="text/javascript">
	$(".fullSlide").hover(function(){
	    $(this).find(".prev,.next").stop(true, true).fadeTo("show", 0.5)
	},
	function(){
	    $(this).find(".prev,.next").fadeOut()
	});
	$(".fullSlide").slide({
	    titCell: ".hd ul",
	    mainCell: ".bd ul",
	    effect: "fold",
	    autoPlay: true,
	    autoPage: true,
	    trigger: "click",
	    startFun: function(i) {
	        var curLi = jQuery(".fullSlide .bd li").eq(i);
	        if ( !! curLi.attr("_src")) {
	            curLi.css("background-image", curLi.attr("_src")).removeAttr("_src")
	        }
	    }
	});
	</script>
	<!-- 轮播结束-->

	<div class="content">
		<div class="title-txt" style="width:90%;max-width:1200px;"><h2>热门产品</h2></div>
		<div class="category-products" style="width:90%;max-width:1200px;">
			<c:forEach items="${hotGoodsList}" var="hotGoods" varStatus="vs">
				<div class="col wow animated fadeInUpBig">
					<a href="<%=apath%>/goodDetail/toGoodDetail?goodsId=${hotGoods.id}">
						<div class="image">
							<img src="${hotGoods.defaultPicture}">
						</div>
						<div class="pro-tit-inf">
							<h3>${hotGoods.goodName}</h3>
							<p id="hotDescription${vs.count}" style="white-space: pre-line"></p>
							<script>
                               $(function () {
                            	   var descriptionBrief = "${hotGoods.description}"; 
                                  // var descriptionBrief = decodeURI('${hotGoods.description}').replace('\n','<br/>').replace(/\n/g,'<br/>');
                                   if (descriptionBrief.length > 59) {
                                       var newDescriptionBrief = descriptionBrief.substring(0, 59);
                                       $("#hotDescription${vs.count}").html(newDescriptionBrief + "……");
                                   } else {
                                       $("#dhotDescription${vs.count}").html(descriptionBrief);
                                   }
                               });
                           </script>
						</div>
						<div class="bt-price">
							<p>￥${hotGoods.price}</p>
						</div>
					</a>
				</div>
			</c:forEach>
			
			
			<div class="clear"></div>
			
	
			
		</div><!--content结束-->
		<div class="title-txt" style="width:90%;max-width:1200px;"><h2>推荐产品</h2></div>
		<div class="row category-products" style="width:90%;max-width:1200px;">
			<c:forEach items="${recommendGoodsList}" var="recommendGoods" varStatus="vs">
				<div class="col wow animated fadeInUpBig" data-wow-duration="2s" data-wow-delay="5s">
					<a href="<%=apath%>/goodDetail/toGoodDetail?goodsId=${recommendGoods.id}">
						<div class="image">
							<img src="${recommendGoods.defaultPicture}">
						</div>
						<div class="pro-tit-inf">
							<h3>${recommendGoods.goodName}</h3>
							<!--  -->
							<p id="description${vs.count}" style="white-space: pre-line"></p>
							<script>
                               $(function () {
                            	   var descriptionBrief = "${recommendGoods.description}"; 
                                   if (descriptionBrief.length > 59) {
                                       var newDescriptionBrief = descriptionBrief.substring(0, 59);
                                      // $("#description${vs.count}").html(newDescriptionBrief.replaceAll("\n\r","<br>").replace("\n","<br>") + "……");
                                       $("#description${vs.count}").html(newDescriptionBrief + "……");
                                   } else {
                                       $("#description${vs.count}").html(descriptionBrief);
                                   }
                               });
                           </script>
							<%-- <p>${recommendGoods.description}</p> --%>
						</div>
						<div class="bt-price">
							<p>￥${recommendGoods.price}</p>
						</div>
					</a>
				</div>
			</c:forEach>
				
				
				<div class="clear"></div>
			</div>		
		
		<%-- <div class="title-txt"><h2>分类</h2></div>
		<div class="ca-ul">
	        <ul class="c-list">
	            <c:forEach items="${firstLevel}" var="IndustryCategory">
				<li><a href="###" class="c-hyperlink">${IndustryCategory.name} </a></li>
			</c:forEach>
	        </ul>
		</div> --%>
		<div class="clear"></div>
	</div><!--content结束-->	
	
	
	
	
	<div class="footer">
		<div class="f-info">
	    <h6>Copyright © 2017-2020 鹏海软件 鲁ICP备10205373号 </h6>
	   </div>
	</div><!--footer结束-->
</div>	
<script>
if (!(/msie [6|7|8|9]/i.test(navigator.userAgent))){
	new WOW().init();
};
</script>
</body>
</html>