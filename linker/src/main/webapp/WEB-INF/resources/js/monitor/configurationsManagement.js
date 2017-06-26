//页面加载时获取linker配置信息
$(function() {
	getConfigutions();
	$("#configurations-form div input").change(function () {
		var i=0;
		for(i=0;i<$('#configurations-form div input').length;i++){
			var obj = $('#configurations-form div input')[i];
			//alert(obj.value);
			if(obj.value==""){
				$('#submitBtn').attr("disabled","disabled");
				break;
			}
			$('#submitBtn').removeAttr("disabled");
		}
	});
});
//获取配置信息
function getConfigutions(){
	$.ajax({
		url : "/linker/management/getConfigurations",
		data : {},
		type:"POST",
		dataType:"json",
		async : false,
		success : function(json){
			if(json.serverIp!=''){
				$("#serverIpShow").text(json.serverIp);
			}else{
				$("#serverIpShow").text("未配置");
			}
			if(json.databaseIp!=''&&json.databasePort!=''&&json.databaseUsername!=''&&json.databasePassword!=''){
				$("#databaseIpShow").text(json.databaseIp);
			}else{
				$("#databaseIpShow").text("未配置");
			}
			if(json.rabbitMqIp!=''&&json.rabbitMqPort!=''&&json.rabbitMqUsername!=''&&json.rabbitMqPassword!=''){
				$("#rabbitMqIpShow").text(json.rabbitMqIp);
			}else{
				$("#rabbitMqIpShow").text("未配置");
			}
			if(json.rabbitMqIp!=''&&json.rabbitMqPort!=''&&json.rabbitMqUsername!=''&&json.rabbitMqPassword!=''
				&&json.databaseIp!=''&&json.databasePort!=''&&json.databaseUsername!=''&&json.databasePassword!=''
					&&json.serverIp!=''){
				$('#linkerConfigStatus').val('1');
				$('#configurationButton').hide();
			}else{
				$('#linkerConfigStatus').val('0');
				$('#configurationButton').show();
			}
		},error: function(a){
			$("#serverIpShow").text("信息读取出错，请刷新页面！");
			$("#databaseIpShow").text("信息读取出错，请刷新页面！");
			$("#rabbitMqIpShow").text("信息读取出错，请刷新页面！");
		}
	});
}
//显示配置编辑模态框
function goEditConfigurations(){
	$.ajax({
		url : "/linker/management/getConfigurations",
		data : {},
		type:"POST",
		dataType:"json",
		async : false,
		success : function(json){
			if(json.code=='1'){
				$("#serverIp").val(json.serverIp);
				$("#databaseIp").val(json.databaseIp);
				$("#databasePort").val(json.databasePort);
				$("#databaseUsername").val(json.databaseUsername);
				$("#databasePassword").val(json.databasePassword);
				$("#rabbitMqIp").val(json.rabbitMqIp);
				$("#rabbitMqPort").val(json.rabbitMqPort);
				$("#rabbitMqUsername").val(json.rabbitMqUsername);
				$("#rabbitMqPassword").val(json.rabbitMqPassword);
				$("#configurations-edit").modal();
			}else{
				setFormEmpty();
				$("#configurations-edit").modal();
			}
		},error: function(a){
			optTip2("查询失败，请刷新页面后重试！");
		}
	});
}
//提交保存配置信息
function submitConfigurations(){	
		$.ajax({
			url : "/linker/management/saveConfigurations",
			data : $("#configurations-form").serialize(),
			type:"POST",
			dataType:"json",
			async : false,
			success : function(json){
				getConfigutions();
				$("#configurations-edit").modal('hide');
				setFormEmpty();
			},error: function(a){
				
			}
		});
}
//模态框输入格清空
function setFormEmpty(){
	$("#serverIp").val("");
	$("#databaseIp").val("");
	$("#databasePort").val("");
	$("#databaseUsername").val("");
	$("#databasePassword").val("");
	$("#rabbitMqIp").val("");
	$("#rabbitMqPort").val("");
	$("#rabbitMqUsername").val("");
	$("#rabbitMqPassword").val("");
}