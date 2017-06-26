<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String apath = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>应用商店-报表分析</title>
<link href="<%=apath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/css/style.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/css/jquery.noty.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/noty_theme_default.css"/>
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/charisma-app.css"/>

<script  type="text/javascript" src="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/bootstrap.js"></script>
<script  type="text/javascript" src="<%=apath%>/resources/js/main.js"></script>
<script type="text/javascript" src="<%=apath%>/resources/js/superslide.2.1.js"></script>
<script src ="<%=apath%>/resources/js/charisma.js"></script>
<script src ="<%=apath%>/resources/js/jquery.noty.js"></script>

<script src ="<%=apath%>/resources/js/monitor/timer.js"></script>
<script src ="<%=apath%>/resources/js/monitor/configurationsManagement.js"></script>
</head>
<body style="background: #242832;min-height: 760px;">
	<div id="mainContent" class="mainContent  bg-color">
	    <div class="char-head-tit"><h2>Linker监控<img src="<%=apath%>/resources/images/char_ico.png"/></h2>
	    	<div class="right-btnlist">
	    		<input id="runningStatus" value="" type="hidden"></input>
	    		<input id="startButton" type="button" class="begin-btn"  value=""  onclick="startMonitor()" title="开始"/>
	    		<input id="pauseButton" type="button" class="stop-btn" value=""  onclick="pauseMonitor()" title="停止"/>
	    		<input id="resetButton" type="button" class="reset-btn" value=""  onclick="clearCache()" title="停止"/>
	    	</div>
	    </div>
		<div class="chart-content">
			
			<div class="c-graph">
				<div class="c-tit">
					监控日志
				</div>
				<div class="c-graph-cont" style="word-wrap: break-word;white-space: pre-line;overflow-y:auto;height:565px">
					<input id="logRefreshTimeSet" type="hidden"></input>
					<div style="margin:5px;" id="logMonitor">
						服务未运行
					</div>
				</div>
			</div>
			
			<div class="c-graph">
				<div class="c-tit">
					配置信息
				</div>
				<div class="c-graph-cont">
					<input id="linkerConfigStatus" type="hidden" value=""></input>
					<div style="margin-top:20px;">
						<div id="serverText"  style="display:inline;margin-left:50px;">服务器地址：</div>
						<div id="serverIpShow"  style="display:inline;margin-left:48px;"></div>
					</div>
					<div style="margin-top:20px;">
						<div id="databaseText"  style="display:inline;margin-left:50px;">数据库地址：</div>
						<div id="databaseIpShow"  style="display:inline;margin-left:48px;"></div>
					</div>
					<div style="margin-top:20px;">
						<div id="rabbitMqText"  style="display:inline;margin-left:50px;">上传服务器地址：</div>
						<div id="rabbitMqIpShow"  style="display:inline;margin-left:20px;"></div>
					</div>
					<div style="margin-top:50px;">
						<button id="configurationButton" type="button" style="margin-left:300px;" onclick="goEditConfigurations()" class="btn btn-default btn-primary">修改</button>
					</div>
				</div>
			</div>
			
			<div class="c-graph">
				<div class="c-tit">
					统计信息
				</div>
				<div class="c-graph-cont">
					<input id="startTimeSet" type="hidden"></input>
					<input id="countRefreshTimeSet" type="hidden"></input>
					<table>
						<tr style="margin-top:20px;height:40px;">
							<td id="startTimeTitle"  style="padding-left:50px;width:150px;" value="">开始时间：</td>
							<td id="startTime"  style="padding-left:30px;width:180px;">服务未运行</td>
						</tr>
						<tr style="margin-top:20px;height:40px;">
							<td id="runTimeTitle" style="padding-left:50px;width:150px;">运行时间：</td>
							<td id="runTime" style="padding-left:30px;width:180px;">服务未运行</td>
						</tr>
						<tr style="margin-top:20px;height:40px;">
							<td id="readDataAcountTitle" style="padding-left:50px;width:150px;">读取数据总量：</td>
							<td id="readDataAcount" style="padding-left:30px;width:180px;">服务未运行</td>
						</tr>
						<tr style="margin-top:20px;height:40px;">
							<td id="upleadDataAcountTitle" style="padding-left:50px;width:150px;">上传数据总量：</td>
							<td id="upleadDataAcount" style="padding-left:30px;width:180px;">服务未运行</td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="c-graph">
				<div class="c-tit">
					表统计信息
				</div>
				<div id="tablesStatics" class="c-graph-cont" style="overflow-y:auto;">
					服务未运行
				</div>
			</div>
			
			<div class="c-graph" >
				<div class="c-tit">
					连接状态
				</div>
				<div class="c-graph-cont" >
					<div id="conectionNormal" class="c-conection-normal">
						linker工作正常
					</div>
					<div id="sqlConectionError" class="c-conection-error">
						数据库连接异常
					</div>
					<div id="rabbitmqConectionError" class="c-conection-error">
						上传服务器连接异常
					</div>
					<div id="serverStop" class="c-conection-stop">
						linker未运行
					</div>
				</div>
			</div>
			
		</div>
		<div class="clear"></div>	
		<div class="char-footer">
			<div class="f-info">
		    	<h6>Copyright © 2017-2020 鹏海软件 鲁ICP备10205373号 </h6>
		    </div>	
		</div><!--footer结束-->
	</div>	

	<div class="modal fade" id="configurations-edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3>配置信息编辑</h3>
				</div>
				<div class="modal-body">
					<form role="form" id="configurations-form">
						<div class="form-group">
							<label for="serverIp" style="display:inline;">存储系统地址</label>
							<input type="text" class="form-control" id="serverIp" name="serverIp" style="display:inline;"/>
						</div>
						<div class="form-group">
							<label for="databaseIp" style="display:inline;">数据库地址</label>
							<input type="text" class="form-control" id="databaseIp" name="databaseIp" style="display:inline;"/>
						</div>
						<div class="form-group">
							<label for="databasePort" style="display:inline;">数据库端口</label>
							<input type="text" class="form-control" id="databasePort" name="databasePort" style="display:inline;"/>
						</div>
						<div class="form-group">
							<label for="databaseUsername" style="display:inline;">数据库登录名</label>
							<input type="text" class="form-control" id="databaseUsername" name="databaseUsername" style="display:inline;"/>
						</div>
						<div class="form-group">
							<label for="databasePassword" style="display:inline;">数据库登陆密码</label>
							<input type="text" class="form-control" id="databasePassword" name="databasePassword" style="display:inline;"/>
						</div>
						<div class="form-group">
							<label for="rabbitMqIp" style="display:inline;">上传服务器(rabbitMQ)地址</label>
							<input type="text" class="form-control" id="rabbitMqIp" name="rabbitMqIp" style="display:inline;"/>
						</div>
						<div class="form-group">
							<label for="rabbitMqPort" style="display:inline;">上传服务器端口</label>
							<input type="text" class="form-control" id="rabbitMqPort" name="rabbitMqPort" style="display:inline;"/>
						</div>
						<div class="form-group">
							<label for="rabbitMqUsername" style="display:inline;">上传服务器登录名</label>
							<input type="text" class="form-control" id="rabbitMqUsername" name="rabbitMqUsername" style="display:inline;"/>
						</div>
						<div class="form-group">
							<label for="rabbitMqPassword" style="display:inline;">上传服务器登录密码</label>
							<input type="text" class="form-control" id="rabbitMqPassword" name="rabbitMqPassword" style="display:inline;"/>
						</div>
						<button type="button" class="btn btn-default btn-primary" id="submitBtn" onclick="submitConfigurations()">提交保存</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>

<script>
	//启动监控
	function startMonitor(){
		if($('#linkerConfigStatus').val()=="1"){
			$.ajax({
				url : "/linker/dataAcquisition/start",
				data : {},
	 			type:"POST",
	 			dataType:"json",
	 			async : false,	
	 			success : function(json){
	 				if(json.result=='1'){
						$('#runningStatus').val("1");
						showButtonStart();
						startTimer();
						setStartTime((new Date().getTime())/1000);
						
						$('#conectionNormal').show();
						$('#serverStop').hide();
	 	 				optTip2(json.message);
	 				}else{
	 	 				optTip2(json.message);
	 				}
	 			},error:function(){
	 				optTip2("启动服务失败，请刷新页面后重试！");
	 			}
			});
		}else{
			optTip2("请先配置连接信息，否则无法启动服务！");
		}
	}
	//暂停监控
	function pauseMonitor(){
		$.ajax({
			url : "/linker/dataAcquisition/pause",
			data : {},
 			type:"POST",
 			dataType:"json",
 			async : false,	
 			success : function(json){
 				if(json.result=='1'){
 					$('#runningStatus').val("0");
 					showButtonPause();
 	 				pauseTimer();
 	 				$('#conectionNormal').hide();
 	 				$('#rabbitmqConectionError').hide();
 	 				$('#sqlConectionError').hide();
 	 				$('#serverStop').show();
 					$("#startTime").text("服务未运行");
 					$("#runTime").text("");
 	 				optTip2(json.message);
 				}else{
 					optTip2(json.message);
 				}
 			},error:function(){
					optTip2("停止服务失败，请刷新页面后重试！");
 			}
		});
	}
	//清理缓存、重置参数、
	function clearCache(){
		$.ajax({
			url : "/linker/dataAcquisition/resetParameter",
			data : {},
 			type:"POST",
 			dataType:"json",
 			async : false,	
 			success : function(json){
 				optTip2(json.message);
 				$('#configurationButton').show();
 				
				$("#readDataAcount").text("");
				$("#upleadDataAcount").text("");
				$("#tablesStatics").text("");
 			},error:function(){
 			}
		});
	}
	//获取静态配置信息：监控刷新时间间隔，服务开始时间
	function getStaticConfigurations() {
		$.ajax({
 			url : "/linker/management/getStaticConfigurations",
 			data : {},
 			type:"POST",
 			dataType:"json",
 			async : false,
 			success : function(json){
				var logRefreshTime = json.logRefreshTime;
				var countRefreshTime = json.countRefreshTime;
				var dataRefreshTime = json.dataRefreshTime;
				var startTime = json.startTime;
				$("#logRefreshTimeSet").val(logRefreshTime);
				$("#countRefreshTimeSet").val(countRefreshTime);
				$("#dataRefreshTimeSet").val(dataRefreshTime);
				$("#startTimeSet").val(startTime);
 			},error: function(a){
 			}
		});
	}
	//开始时间格式由时间戳转换为日期显示
	function setStartTime(time){
		var startTime; 
		if(time==null){
			startTime = $("#startTimeSet").val();
		}else{
			startTime = time;
			$("#startTimeSet").val(startTime);
		}
		var time = new Date(startTime*1000);
		var y = time.getFullYear();
		var m = time.getMonth()+1;
		var d = time.getDate();
		var h = time.getHours();
		var mm = time.getMinutes();
		var s = time.getSeconds();
		var dateString = y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);

		$("#startTime").text(dateString);
	}
	function showButtonStart(){
		$('#startButton').hide();
		$('#pauseButton').show();
		$('#resetButton').hide();
		$('#configurationButton').hide();
	}
	function showButtonPause(){
		$('#startButton').show();
		$('#pauseButton').hide();
		$('#resetButton').show();
	}
	//页面加载时获取初始信息，判断页面显示
	$(function(){
		refreshPageStatus();
		setInterval("refreshPageStatus()",10000);
	});

	function refreshPageStatus(){
		getStaticConfigurations();
		$.ajax({
			url : "/linker/dataAcquisition/getTimerStatus",
			data : {},
 			type:"POST",
 			dataType:"json",
 			async : false,	
 			success : function(json){
 				var runningStatus = json.result;
 				var localRunningStatus = $('#runningStatus').val();
 				if (runningStatus != localRunningStatus){
					console.log("runningStatus===>>>"+runningStatus+"    localRunningStatus===>>>"+localRunningStatus+"   runningStatus changed !!");
 					$('#runningStatus').val(runningStatus);
 	 				if(runningStatus=="1"){
 	 					$('#conectionNormal').show();
 	 					$('#serverStop').hide();
 	 					showButtonStart();
 	 		 			startTimer();
 	 		 			setStartTime();
 	 				}else{
 	 					$('#serverStop').show();
 	 					$('#conectionNormal').hide();
 	 					$('#sqlConectionError').hide();
 	 					$('#rabbitmqConectionError').hide();
 	 					showButtonPause();
 	 					pauseTimer();
 	 					$("#startTime").text("服务未运行");
 	 					$("#runTime").text("服务未运行");
 	 				}
 				}
 			},error:function(){
 			}
		});
	}	
</script>
</html>