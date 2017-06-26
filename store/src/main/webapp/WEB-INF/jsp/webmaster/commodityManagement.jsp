<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>

<div>
	<ul class="breadcrumb">
		<li><a href="#">首页</a></li>
		<li><a href="#">商品管理</a></li>
		<li><a href="#">商品管理</a></li>
	</ul>
</div>
<div style="display:table-cell;width:100%;height:50px;vertical-align:middle;text-align: center;">
	<button type="button" style="margin-left: 10px;" class="btn btn-primary" onclick="addResources()">新增商品</button>
</div> 
<div class="box-inner">
	<table border="0" style="width:100%;">
		<tr style="height:44px;background-image:url('');background-repeat:no-repeat;">
			<td class="goodstd" style="font-size:15px;font-weight:bolder;">序号
			</td>
			<td class="goodstd" style="font-size:15px;font-weight:bolder;">所属分类
			</td>
			<td class="goodstd" style="font-size:15px;font-weight:bolder;">商品名称
			</td>
			<td class="goodstd" style="font-size:15px;font-weight:bolder;">图片
			</td>
			<td class="goodstd" style="font-size:15px;font-weight:bolder;">价格
			</td>
			<td class="goodstd" style="font-size:15px;font-weight:bolder;">操作
			</td>
			
		</tr>
	</table>
<table id="goodList">
</table>

</div>
<!-- 商品详情编辑模态框 -->

<!-- edit 模态框 -->
<div class="modal fade" id="resources-Modal-edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" enctype="multipart/form-data">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>商品编辑</h3>
			</div>
			<div class="modal-body">
				<form role="form" id="resources-form">
					<input type="hidden" id="parentId" name="parentId" value="">
					<input type="hidden" id="id" name="id" value="">					
					<div class="form-group">
						<label for="goodCate">所属分类</label>
						<select style="padding: 0px 10px;" class="form-control zzy-required" id="goodCate" name="goodCate"></select>
					</div>
					<div class="form-group">
						<label for="goodName">商品名称</label> <input type="text" class="form-control zzy-required" id="goodName" name="goodName">
					</div>
					<div class="form-group">
						<label for="description">描述</label> 
						<textarea rows="5" class="form-control zzy-required" id="description" name="description"></textarea>
					</div>
					<div class="form-group">
						<label for="price">价格</label> <input type="text" class="form-control zzy-required" id="price" name="price">
					</div>
					<div class="form-group">
						<label for="goodModel">商品模型</label> <input type="text" class="form-control zzy-required" id="goodModel" name="goodModel">
					</div>
					<div class="form-group">
						<label for="file">图片</label> 
						<img style="display:inline" src="" width="55" height="55" id="oldPicture">
						<input style="display:inline" type="file" class="form-control zzy-required" id="file" name="file">
					</div>
					<!-- <div class="form-group">
						<label for="sysResourcesSeq">排序</label> <input type="text" class="form-control zzy-required" id="sortOrder" name="sortOrder">
					</div> -->
					<button type="button" class="btn btn-default btn-primary" id="submitResources">提交</button>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- del模态框-->
<div class="modal fade" id="resources-Modal-del" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog dialog-width">
		<div class="modal-content" style="margin-top:350px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>删除提示</h3>
			</div>
			<div class="modal-body body-padding">确定删除？</div>
			<div class="modal-footer">
				<a href="#" class="btn btn-default" data-dismiss="modal">取消</a> <a href="#" class="btn btn-primary" id="confirmDel" data-dismiss="modal" onclick="delResource()">确认</a>
			</div>
		</div>
	</div>
</div>
<script>
var editFlag = 0;
$(function() {
	//加载本服务站商品分类
	searchGoods();
});
function searchGoods(){
	$.ajax({
		url : "<%=apath%>/admin/manage/queryGoods",
		/* data : $("#resources-form").serialize(), */
		data :{/* goodName:"商品1" */},
		type : "GET",
		success : function(data) {
			paintTable(data);
		},
		error : function(data) {
			optTip(data);
		}
	});
}
function paintTable(data){
	result = data.result;
	var h="";
	$("#goodList").html(h); 
	 for(i=0;i<data.length;i++){
		 if(i%2==1)
			{
			 h += '<tr class="trf9f9f9">';
			}else{
				 h += '<tr class="trf">';
			}
		 h += '<td class="goodstd">' + (i+1) + '</td>';
		 h += '<td class="goodstd">' + result[i].goodCateName + '</td>';
		 h += '<td class="goodstd">' + result[i].goodName + '</td>';
		 h += '<td class="goodstd"><img style=\"height: 50px;\" src=\"'+ result[i].defaultPicture + '\"></td>';
		 
		 /* if (!result[i].description || typeof(result[i].description)=="undefined" || result[i].description=="null" || result[i].description==null){
				h += '<td class="goodstd"></td>';
			}else{
				h += '<td class="goodstd">' + result[i].description + '</td>';
			} */
		 h += '<td class="goodstd">' + result[i].price + '</td>';
		 h += '<td class="goodstd">' + "<a href=\"javascript:;\" onclick=\"editGoodDetail('"+result[i].id+"','"+result[i].goodCate+"','"+result[i].goodName+"','"+result[i].description+"','"+result[i].price+"','"+result[i].goodModel+"','"+result[i].defaultPicture+"')\">编辑</a> &nbsp;&nbsp;" 
		 		+ "<a href=\"javascript:;\" onclick=\"delGood('"+result[i].id+"')\">删除</a>" + '</td>';
		 h += '</tr>';
	 }
	 $("#goodList").html(h);
}
//新增商品，新增模态框显示
function addResources() {
	$.ajax({
		url : "<%=apath%>/level/queryFourLevel",
		async : false,
		type : "GET",
		success : function(data) {
			$("#resources-form")[0].reset();
			var HtmlSelect = paintHtmlSelect(data);
			$("#goodCate").html("");
			$("#goodCate").append(HtmlSelect);
			editFlag=0;
			$("#id").val('');
			$("#resources-Modal-edit").modal();
		},
		error : function() {
		}
	});
}
//修改商品--修改模态框显示
function editGoodDetail(id,goodCate,goodName,description,price,goodModel,defaultPicture) {
	$.ajax({
		url : "<%=apath%>/level/queryFourLevel",
		async : false,
		success : function(data) {
			var HtmlSelect = paintHtmlSelect(data);
			$("#goodCate").html("");
			$("#goodCate").append(HtmlSelect);
			editFlag=1;
		//	$("#resources-Modal-edit").data("edit", '1'); 
		//	$.post("<%=apath%>/goodDetail/getGoodsDetailInfo?format=json", {
		//		goodsId : id
		//	}, function(commodity) {
		//		commodity=commodity.goodDetail;
		//		var goodsInfo = commodity.goods;
		//		var goodsPictures = commodity.goodsPictureList;
				$("#resources-form")[0].reset();
				$("#id").val(id);
				$("#goodCate").val(goodCate);
				$("#goodName").val(goodName);
				$("#description").val(description);
				$("#price").val(price);
				$("#goodModel").val(goodModel);
				$("#oldPicture").attr('src',defaultPicture);
				
				$("#resources-Modal-edit").modal();
		//	});
			
		}
	});
}
$("#submitResources").click(
	function() {
		//新增
		if (id == '') {
			$("#id").val("-1");
		}
		var name = $.trim($("#goodName").val());
		if (name == '') {
			optTip2("请输入商品名称!");
			return;
		}
		var price = $.trim($("#price").val());
		if (price == '') {
			optTip2("请输入商品价格!");
			return;
		}
		var categoryCode = $.trim($("#goodModel").val());
		if (categoryCode == '') {
			optTip2("请输入商品模型!");
			return;
		}
		var form = new FormData(document.getElementById("resources-form"));
		form.append('oldPicture',$("#oldPicture")[0].src); 
		if(editFlag==1){
			$.ajax({
				url : "<%=apath%>/admin/manage/editGood?format=json",
				//data : $("#resources-form").serialize(),
				data : form,
				type : "POST",
				processData : false,
				contentType : false,
				success : function(jsonData) {
					unDisabledBtn("submitResources");
					if ('false' == jsonData.success) {
						optTip2("修改失败");
					} else {
						optTip("修改成功");
						location.reload();
						$("#resources-Modal-edit").modal('hide');
					}
				},
				error : function(data) {
					optTip(false);
					unDisabledBtn("submitResources");
				}
			});
		}else{
			$.ajax({
				url : "<%=apath%>/admin/manage/addGood?format=json",
				data : form,
				type : "POST",
				processData : false,
				contentType : false,
				success : function(jsonData) {
					unDisabledBtn("submitResources");
					if ('false' == jsonData.success) {
						optTip2("新增失败");
					} else {
						optTip("新增成功");
						location.reload();
						$("#resources-Modal-edit").modal('hide');
					}
				},
				error : function(data) {
					optTip(false);
					unDisabledBtn("submitResources");
				}
			});
		}
});	
function paintHtmlSelect(data) {
	data = data.result;
	var h = "";
	$(data).each(
	  function(i,a) {
		h += "<option value=\""+ a.id +"\">"+ a.name +"</option>";  
		$(a.children).each(
		  function(i,b) {
			  h += "<option value=\""+ b.id +"\">&nbsp;&nbsp;"+ b.name +"</option>";  
			  $(b.children).each(
				  function(i,c) {
					  h += "<option value=\""+ c.id +"\">&nbsp;&nbsp;&nbsp;&nbsp;"+ c.name +"</option>";  
					  $(c.children).each(
						  function(i,d) {
							  h += "<option value=\""+ d.childrenId +"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ d.childrenName +"</option>";  
					  	  });
			  	  });
	  	  });
	});
	return h;
}
//删除商品模态框
function delGood(id) {
	$("#resources-Modal-del").data("id", id);
	$("#resources-Modal-del").modal();
}
function delResource() {
	$.ajax({
		url : "<%=apath%>/admin/manage/deleteGood?format=json",
		data : {
			"id" : $("#resources-Modal-del").data("id")
		},
		async : true,
		dataType : "json",
		type : "POST",
		success : function(jsonData) {
			if (jsonData.success) {
				$("#commodity-Modal-del").modal('hide');
				unDisabledBtn("confirmDel");
				searchGoods();
			} else {
				optTip(false);
				unDisabledBtn("confirmDel");
			}
		},
		error : function() {
			optTip(false);
		}
	});
}
</script>
<style>
.trf9f9f9{
	 background-color:#f9f9f9; 
	height:25px;font-size:13px;width:16%;text-align:center;
}
.trf{
	height:25px;font-size:13px;width:16%;text-align:center;
}
.goodstd{
min-height:20px;font-size:13px;width:16%;text-align:center;
}
</style>