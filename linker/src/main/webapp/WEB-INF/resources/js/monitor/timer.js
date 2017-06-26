//运行时间刷新定时器
var clockTimer;
//日志监控定时器
var logTimer;
//统计信息监控定时器
var countTimer;
//统计信息监控定时器
var conectionTimer;

//运行定时器
function startTimer(){
	logMonitor();
	dateMinus();
	getTablesAcount();
	//定时器运行时间间隔
	var logRefreshTimeSet = $("#logRefreshTimeSet").val();
	var countRefreshTimeSet = $("#countRefreshTimeSet").val();
	//运行时间刷新定时器
	clockTimer = setInterval("dateMinus()", 1000);
	//日志监控定时器
	logTimer = setInterval("logMonitor()",logRefreshTimeSet);
	//统计信息监控定时器
	countTimer = setInterval("getTablesAcount()",countRefreshTimeSet);
	//统计信息监控定时器
	conectionTimer = setInterval("getConectionStatus()",countRefreshTimeSet);

}
//停止定时器
function pauseTimer(){
	clearInterval(clockTimer);
	clearInterval(logTimer);
	clearInterval(countTimer);
	clearInterval(conectionTimer);
}

//获取服务器连接状态
function getConectionStatus(){
	$.ajax({
		url : "/linker/monitor/getConectionStatus",
		data : {},
		type:"POST",
		dataType:"json",
		async : false,	
		success : function(json){
			if(json.sqlConectionStatus == 0){
				$('#sqlConectionError').show();
				$('#conectionNormal').hide();
			}else if(json.rabbitMqConectionStatus == 0){
				$('#rabbitmqConectionError').show();
				$('#conectionNormal').hide();
			}else{
				$('#conectionNormal').show();
				$('#rabbitmqConectionError').hide();
				$('#sqlConectionError').hide();
			}
		}
	});
}

//获取表统计信息
function getTablesAcount(){
	$.ajax({
		url : "/linker/monitor/getTablesAcount",
		data : {},
			type:"POST",
			dataType:"json",
			async : false,	
			success : function(json){
				var h = "";
				var totalSelectNumber = parseInt(0);
				var totalSaveNumber = parseInt(0);
				if(json.code=='1'){
					h += '<table><tr style="margin-top:20px;height:40px;">';
					h += '<td style="width:150px;padding-left:50px;">表名</td>';
					h += '<td style="width:180px;padding-left:30px;">总上传量</td></tr>';
					$(json.tableData).each(
						function(i, table) {
							h += '<tr style="margin-top:20px;height:40px;">';
							h += '<td style="width:150px;padding-left:50px;">' + table.tableName + '</td>';
							h += '<td style="width:180px;padding-left:30px;">' + table.SaveCountNumber + '</td></tr>';
							//转为int值
							var tableSelectCount = parseInt(table.SelectCountNumber);
							var tableSaveCount = parseInt(table.SaveCountNumber);
							
							totalSelectNumber += tableSelectCount;
							totalSaveNumber += tableSaveCount;
				    });
					h += '</table>';
				}else{
					//h += '<tr><td valign="top" colspan="15" class="dataTables_empty">抱歉， 没有找到</td></tr>';
					h += '<div style="margin-top:20px;"><div id="startTimeTitle"  style="display:inline;margin-left:50px;" value="">抱歉，没有数据</div></div>';
				}
				$("#tablesStatics").html(h);
				$("#readDataAcount").text(totalSelectNumber+"条");
				$("#upleadDataAcount").text(totalSaveNumber+"条");
				
			},error:function(){
			}
	});
}
//获取日志
function logMonitor(){
	$.ajax({
		url : "/linker/monitor/getRecentLog",
		data : {},
			type:"POST",
			dataType:"json",
			async : false,	
			success : function(json){
				if(json.code=='1'){
					$("#logMonitor").text(json.logInfo);
				}else{
					
				}
			},error:function(){
			}
	});
}
//获取运行时间差、按秒刷新
function dateMinus(){
	var currentTime = new Date().getTime();
	var startTime = $("#startTimeSet").val();
	var dateMinus = parseInt(currentTime/1000 - startTime);
	var day = parseInt(dateMinus/(24*60*60));//计算整数天数
	var afterDay = dateMinus - day*24*60*60;//取得算出天数后剩余的秒数
	var hour = parseInt(afterDay/(60*60));//计算整数小时数
	var afterHour = afterDay - hour*60*60;//取得算出小时数后剩余的秒数
	var min = parseInt(afterHour/60);//计算整数分
	var afterMin = afterHour - min*60;//取得算出分后剩余的秒数
	
	var dateMinus = day+"天"+add0(hour)+"时"+add0(min)+"分"+add0(afterMin)+"秒";
	$("#runTime").text(dateMinus);
}
//只有一位数的时间前+0
function add0(m){return m<10?'0'+m:m }