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
<script  type="text/javascript" src="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script src="<%=apath%>/resources/js/echarts.min.js"></script>
<style>
.dropbtn {
    background-color: #4CAF50;
    color: white;
    padding: 16px;
    font-size: 16px;
    border: none;
    cursor: pointer;
}

.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}

.dropdown-content a:hover {background-color: #f1f1f1}

.dropdown:hover .dropdown-content {
    display: block;
}

.dropdown:hover .dropbtn {
    background-color: #3e8e41;
}
</style>
<body>
<div id="mainContent" class="mainContent">
<!-- 头导航栏 -->
  <div class="head" style="z-index:10;">
		<div class="topnavbar">
		    <div class="toplogo">
			    <a class="headlogo" href="<%=apath%>/login/toUserOrderList"><img src="<%=apath%>/resources/images/logo.jpg" title=""></a>
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
			<div class="menutitle">
		    	<div class="classifmodal">
		    		<div  class="menu-gud">
		    		<h1>存储系统</h1>
		    		</div>
		    		<!--//下拉列表-->
		    	</div>	    				        
		    </div>
    </div><!--head结束-->

<!-- end 头导航栏 -->
	<div class="user-content">	
		<div class="user-con">
			<div class="user-left-menu">			
				<%-- <dl class="side-menutree">
					<dt>订单中心</dt>
					<dd>
						<a href="<%=apath%>/login/toUserOrderList">我的订单</a>
					</dd>
					<dd>
						<a href="<%=apath%>/login/toUserLinker">Linker</a>
					</dd>
					<dd>
						<a href="<%=apath%>/login/toUserDatabase" class="active">数据库</a>
					</dd>
				</dl> --%>	
				<dl class="side-menutree">
					<dt><a href="<%=apath%>/login/toUserOrderList">我的订单</a></dt>
					<dt><a href="<%=apath%>/login/toUserLinker">Linker</a></dt>
					<dt><a href="<%=apath%>/login/toUserDatabase" class="active" style="color: #e4393c;font-weight: bold;">数据库</a></dt>
				</dl>									    	
			</div>
			<div class="user-right">
				<div class="user-content-info">
					 	
					 	
<div id="mainContainer"><div class="ordersContainer" >
	<div style="width:90%;height:100px;">
	<div class="dropdown">
	  <button class="dropbtn">选择数据库</button>
	  <div id="dropdown-content" class="dropdown-content" style="z-index:10;">
	  </div>
	</div>
	</div>		 	
	<div id="echartPain" style="width:90%;height:500px;">
	</div>				 		 
				</div><!--user-content-info结束-->
			</div><!--user-right结束	-->	
			<div class="clear"></div>
		</div><!--user-con结束-->	
	</div><!--user-content结束-->		
	<div class="clear"></div>	
	<div class="footer">
		<div class="f-info">
	    	<h6>Copyright © 2017-2020 鹏海软件 鲁ICP备10205373号 </h6>
	    </div>	
	</div>
</div>	
</div>
</div>
</body>

<script type="text/javascript">


$(function(){
	showDatabases();

});
//查询数据库，显示数据库选择列表
function showDatabases(){
	$.ajax({
		url : "<%=apath%>/dataCount/getDatabaseList",
		type:"POST",
		async : false,
		success : function(json){
			var h = "";
			dataArray= $.parseJSON(json);
			$(dataArray).each(
			  function(i,a) {
				  h += "<a onclick=\"getDatabeseInfo(\'"+ a.databaseName+ "\')\"" +"style=\"cursor: pointer;\"" +">"+ a.databaseName +"</a>";
			  });
			$("#dropdown-content").html("");
			$("#dropdown-content").append(h);
		},
		error: function(data){
			optTip2(data);
		}
	});
} 

//查询单个数据库的信息
function getDatabeseInfo(databaseName){
	$.ajax({
		url : "<%=apath%>/dataCount/getTableDataCount",
		data:{"databaseName":databaseName},
		type:"POST",
		async : false,
		success : function(json){
			if(json!=""){
				dataArray= $.parseJSON(json);
				paintEchat(databaseName,dataArray);
			}
		}
	});
} 
//渲染数据
function paintEchat(databaseName,dataArray){
	var h = dataArray.length*80;
	if(h>500){
		$("#echartPain").height(h);//直接设置元素的高
		//$("#echartPain").css("height", "300px");//通过设置CSS属性来设置元素的高
	}
	var myChart = echarts.init(document.getElementById('echartPain'));
	var dataAxis = [];
	var data = [];
	$(dataArray).each(
	  function(i,a) {
		  dataAxis[i]=a.tableName;
		  data[i]=a.count;
	  });
	var dataShadow = [];

	option = {
	    title: {
	        text: databaseName,
	        /* subtext: 'Feature Sample: Gradient Color, Shadow, Click Zoom' */
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : ''        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    xAxis: {
	    	axisLine: {
	            show: true
	        },
	        axisTick: {
	            show: false
	        },
	        axisLabel: {
	            textStyle: {
	                color: '#999'
	            }
	        },
	        position: 'top',
	    },
	    yAxis: {
	    	data: dataAxis,
	        axisLabel: {
	            inside: true,
	            textStyle: {
	                color: '#573efe'
	            }
	        },
	        axisTick: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        z: 10
	    },
	    /* dataZoom: [
	        {
	            type: 'inside'
	        }
	    ], */
	    series: [
	        { // For shadow
	        	name:'数据量',
	            type: 'bar',
	            itemStyle: {
	                normal: {color: 'rgba(0,0,0,0.05)'}
	            },
	            barGap:'-100%',
	            barCategoryGap:'40%',
	            data: dataShadow,
	            animation: false
	        },
	        {
	            type: 'bar',
	            itemStyle: {
	                normal: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#83bff6'},
	                            {offset: 0.5, color: '#188df0'},
	                            {offset: 1, color: '#188df0'}
	                        ]
	                    )
	                },
	                emphasis: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#2378f7'},
	                            {offset: 0.7, color: '#2378f7'},
	                            {offset: 1, color: '#83bff6'}
	                        ]
	                    )
	                }
	            },
	            data: data
	        }
	    ]
	};
	
	
	myChart.setOption(option);
	
}
//window.onresize = myChart.resize;
/* setTimeout(function (){
    window.onresize = function () {
        myChart.resize();
    }
},200); */
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
</script>
</html>

