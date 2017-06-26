<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>

<div>
	<ul class="breadcrumb">
		<li><a href="#">首页</a></li>
		<li><a href="#">商品管理</a></li>
		<li><a href="#">商品分类</a></li>
	</ul>
</div>
<div style="display:table-cell;width:100%;height:50px;vertical-align:middle;text-align: center;">
	<button type="button" style="margin-left: 10px;" class="btn btn-primary" onclick="addResources()">新增分类</button>
</div>
<div class="row">
	<form class="form-inline" role="form">
		<div class="form-group has-success col-sm-1">
			
		</div>
	</form>
</div>
<br />
<table id="resourceTable" class="table table-bordered table-striped tablelist">
	<thead>
		<tr>
			<th class="col-lg-3 col-md-3 col-sx-3">编码</th>
			<th class="col-lg-3 col-md-3 col-sx-3">名称</th>
			<th class="col-lg-3 col-md-3 col-sx-3">描述</th>
			<th class="col-lg-3 col-md-3 col-sx-3">操作</th>
		</tr>
	</thead>
	<tbody id="tableBody">
	</tbody>
</table>
<!-- edit 模态框 -->
<div class="modal fade" id="resources-Modal-edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>分类编辑</h3>
			</div>
			<div class="modal-body">
				<form role="form" id="resources-form">
					<input type="hidden" id="parentId" name="parentId" value="">
					<input type="hidden" id="level" name="level" value="">					
					<div class="form-group">
						<label for="parentName">上级分类名称</label> 
						<!-- <input type="text" class="form-control zzy-required" id="parentName" name="parentName" readonly="readonly"> -->
						<select style="padding: 0px 10px;" class="form-control zzy-required" id="parentName" name="parentName"></select>
					</div>
					<div class="form-group">
						<label for="name">分类名称</label> <input type="text" class="form-control zzy-required" id="name" name="name">
					</div>
					<div class="form-group">
						<label for="categoryCode">分类编码</label> <input type="text" class="form-control zzy-required" id="categoryCode" name="categoryCode">
					</div>
					<div class="form-group">
						<label for="description">描述</label> <input type="text" class="form-control zzy-required" id="description" name="description">
					</div>
					<!-- <div class="form-group">
						<label for="sysResourcesUrl">图片</label> 
						<img style="display:inline" src="" width="55" height="55" id="originalImg">
						<input style="display:inline" type="file" class="form-control zzy-required" id="img" name="img">
					</div> -->
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
				<a href="#" class="btn btn-default" data-dismiss="modal">取消</a> <a href="#" class="btn btn-primary" id="confirmDel" data-dismiss="modal">确认</a>
			</div>
		</div>
	</div>
</div>
<!-- icon模态框-->
<div class="modal fade" id="resourcesicon-Modal-del" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>图标</h3>
			</div>
			<div class="modal-body">
				<div class="row bs-icons">
					<div class="col-md-3">
						<ul class="the-icons">
							<li data-name="glyphicon-glass"><i class="glyphicon glyphicon-glass"></i></li>
							<li data-name="glyphicon-music"><i class="glyphicon glyphicon-music"></i></li>
							<li data-name="glyphicon-search"><i class="glyphicon glyphicon-search"></i></li>
							<li data-name="glyphicon-envelope"><i class="glyphicon glyphicon-envelope"></i></li>
							<li data-name="glyphicon-heart"><i class="glyphicon glyphicon-heart"></i></li>
							<li data-name="glyphicon-star"><i class="glyphicon glyphicon-star"></i></li>
							<li data-name="glyphicon-star-empty"><i class="glyphicon glyphicon-star-empty"></i></li>
							<li data-name="glyphicon-user"><i class="glyphicon glyphicon-user"></i></li>
							<li data-name="glyphicon-film"><i class="glyphicon glyphicon-film"></i></li>
							<li data-name="glyphicon-th-large"><i class="glyphicon glyphicon-th-large"></i></li>
							<li data-name="glyphicon-th"><i class="glyphicon glyphicon-th"></i></li>
							<li data-name="glyphicon-th-list"><i class="glyphicon glyphicon-th-list"></i></li>
							<li data-name="glyphicon-ok"><i class="glyphicon glyphicon-ok"></i></li>
							<li data-name="glyphicon-remove"><i class="glyphicon glyphicon-remove"></i></li>
							<li data-name="glyphicon-zoom-in"><i class="glyphicon glyphicon-zoom-in"></i></li>
							<li data-name="glyphicon-zoom-out"><i class="glyphicon glyphicon-zoom-out"></i></li>
							<li data-name="glyphicon-off"><i class="glyphicon glyphicon-off"></i></li>
							<li data-name="glyphicon-signal"><i class="glyphicon glyphicon-signal"></i></li>
							<li data-name="glyphicon-cog"><i class="glyphicon glyphicon-cog"></i></li>
							<li data-name="glyphicon-trash"><i class="glyphicon glyphicon-trash"></i></li>
							<li data-name="glyphicon-home"><i class="glyphicon glyphicon-home"></i></li>
							<li data-name="glyphicon-file"><i class="glyphicon glyphicon-file"></i></li>
							<li data-name="glyphicon-time"><i class="glyphicon glyphicon-time"></i></li>
							<li data-name="glyphicon-road"><i class="glyphicon glyphicon-road"></i></li>
							<li data-name="glyphicon-download-alt"><i class="glyphicon glyphicon-download-alt"></i></li>
							<li data-name="glyphicon-download"><i class="glyphicon glyphicon-download"></i></li>
							<li data-name="glyphicon-upload"><i class="glyphicon glyphicon-upload"></i></li>
							<li data-name="glyphicon-inbox"><i class="glyphicon glyphicon-inbox"></i></li>
							<li data-name="glyphicon-play-circle"><i class="glyphicon glyphicon-play-circle"></i></li>
							<li data-name="glyphicon-repeat"><i class="glyphicon glyphicon-repeat"></i></li>
							<li data-name="glyphicon-refresh"><i class="glyphicon glyphicon-refresh"></i></li>
							<li data-name="glyphicon-list-alt"><i class="glyphicon glyphicon-list-alt"></i></li>
							<li data-name="glyphicon-lock"><i class="glyphicon glyphicon-lock"></i></li>
							<li data-name="glyphicon-flag"><i class="glyphicon glyphicon-flag"></i></li>
							<li data-name="glyphicon-headphones"><i class="glyphicon glyphicon-headphones"></i></li>
						</ul>
					</div>
					<div class="col-md-3">
						<ul class="the-icons">
							<li data-name="glyphicon-volume-off"><i class="glyphicon glyphicon-volume-off"></i></li>
							<li data-name="glyphicon-volume-down"><i class="glyphicon glyphicon-volume-down"></i></li>
							<li data-name="glyphicon-volume-up"><i class="glyphicon glyphicon-volume-up"></i></li>
							<li data-name="glyphicon-qrcode"><i class="glyphicon glyphicon-qrcode"></i></li>
							<li data-name="glyphicon-barcode"><i class="glyphicon glyphicon-barcode"></i></li>
							<li data-name="glyphicon-tag"><i class="glyphicon glyphicon-tag"></i></li>
							<li data-name="glyphicon-tags"><i class="glyphicon glyphicon-tags"></i></li>
							<li data-name="glyphicon-book"><i class="glyphicon glyphicon-book"></i></li>
							<li data-name="glyphicon-bookmark"><i class="glyphicon glyphicon-bookmark"></i></li>
							<li data-name="glyphicon-print"><i class="glyphicon glyphicon-print"></i></li>
							<li data-name="glyphicon-camera"><i class="glyphicon glyphicon-camera"></i></li>
							<li data-name="glyphicon-font"><i class="glyphicon glyphicon-font"></i></li>
							<li data-name="glyphicon-bold"><i class="glyphicon glyphicon-bold"></i></li>
							<li data-name="glyphicon-italic"><i class="glyphicon glyphicon-italic"></i></li>
							<li data-name="glyphicon-text-height"><i class="glyphicon glyphicon-text-height"></i></li>
							<li data-name="glyphicon-text-width"><i class="glyphicon glyphicon-text-width"></i></li>
							<li data-name="glyphicon-align-left"><i class="glyphicon glyphicon-align-left"></i></li>
							<li data-name="glyphicon-align-center"><i class="glyphicon glyphicon-align-center"></i></li>
							<li data-name="glyphicon-align-right"><i class="glyphicon glyphicon-align-right"></i></li>
							<li data-name="glyphicon-align-justify"><i class="glyphicon glyphicon-align-justify"></i></li>
							<li data-name="glyphicon-list"><i class="glyphicon glyphicon-list"></i></li>
							<li data-name="glyphicon-indent-left"><i class="glyphicon glyphicon-indent-left"></i></li>
							<li data-name="glyphicon-indent-right"><i class="glyphicon glyphicon-indent-right"></i></li>
							<li data-name="glyphicon-facetime-video"><i class="glyphicon glyphicon-facetime-video"></i></li>
							<li data-name="glyphicon-picture"><i class="glyphicon glyphicon-picture"></i></li>
							<li data-name="glyphicon-pencil"><i class="glyphicon glyphicon-pencil"></i></li>
							<li data-name="glyphicon-map-marker"><i class="glyphicon glyphicon-map-marker"></i></li>
							<li data-name="glyphicon-adjust"><i class="glyphicon glyphicon-adjust"></i></li>
							<li data-name="glyphicon-tint"><i class="glyphicon glyphicon-tint"></i></li>
							<li data-name="glyphicon-edit"><i class="glyphicon glyphicon-edit"></i></li>
							<li data-name="glyphicon-share"><i class="glyphicon glyphicon-share"></i></li>
							<li data-name="glyphicon-check"><i class="glyphicon glyphicon-check"></i></li>
							<li data-name="glyphicon-move"><i class="glyphicon glyphicon-move"></i></li>
							<li data-name="glyphicon-step-backward"><i class="glyphicon glyphicon-step-backward"></i></li>
							<li data-name="glyphicon-fast-backward"><i class="glyphicon glyphicon-fast-backward"></i></li>
						</ul>
					</div>
					<div class="col-md-3">
						<ul class="the-icons">
							<li data-name="glyphicon-backward"><i class="glyphicon glyphicon-backward"></i></li>
							<li data-name="glyphicon-play"><i class="glyphicon glyphicon-play"></i></li>
							<li data-name="glyphicon-pause"><i class="glyphicon glyphicon-pause"></i></li>
							<li data-name="glyphicon-stop"><i class="glyphicon glyphicon-stop"></i></li>
							<li data-name="glyphicon-forward"><i class="glyphicon glyphicon-forward"></i></li>
							<li data-name="glyphicon-fast-forward"><i class="glyphicon glyphicon-fast-forward"></i></li>
							<li data-name="glyphicon-step-forward"><i class="glyphicon glyphicon-step-forward"></i></li>
							<li data-name="glyphicon-eject"><i class="glyphicon glyphicon-eject"></i></li>
							<li data-name="glyphicon-chevron-left"><i class="glyphicon glyphicon-chevron-left"></i></li>
							<li data-name="glyphicon-chevron-right"><i class="glyphicon glyphicon-chevron-right"></i></li>
							<li data-name="glyphicon-plus-sign"><i class="glyphicon glyphicon-plus-sign"></i></li>
							<li data-name="glyphicon-minus-sign"><i class="glyphicon glyphicon-minus-sign"></i></li>
							<li data-name="glyphicon-remove-sign"><i class="glyphicon glyphicon-remove-sign"></i></li>
							<li data-name="glyphicon-ok-sign"><i class="glyphicon glyphicon-ok-sign"></i></li>
							<li data-name="glyphicon-question-sign"><i class="glyphicon glyphicon-question-sign"></i></li>
							<li data-name="glyphicon-info-sign"><i class="glyphicon glyphicon-info-sign"></i></li>
							<li data-name="glyphicon-screenshot"><i class="glyphicon glyphicon-screenshot"></i></li>
							<li data-name="glyphicon-remove-circle"><i class="glyphicon glyphicon-remove-circle"></i></li>
							<li data-name="glyphicon-ok-circle"><i class="glyphicon glyphicon-ok-circle"></i></li>
							<li data-name="glyphicon-ban-circle"><i class="glyphicon glyphicon-ban-circle"></i></li>
							<li data-name="glyphicon-arrow-left"><i class="glyphicon glyphicon-arrow-left"></i></li>
							<li data-name="glyphicon-arrow-right"><i class="glyphicon glyphicon-arrow-right"></i></li>
							<li data-name="glyphicon-arrow-up"><i class="glyphicon glyphicon-arrow-up"></i></li>
							<li data-name="glyphicon-arrow-down"><i class="glyphicon glyphicon-arrow-down"></i></li>
							<li data-name="glyphicon-share-alt"><i class="glyphicon glyphicon-share-alt"></i></li>
							<li data-name="glyphicon-resize-full"><i class="glyphicon glyphicon-resize-full"></i></li>
							<li data-name="glyphicon-resize-small"><i class="glyphicon glyphicon-resize-small"></i></li>
							<li data-name="glyphicon-plus"><i class="glyphicon glyphicon-plus"></i></li>
							<li data-name="glyphicon-minus"><i class="glyphicon glyphicon-minus"></i></li>
							<li data-name="glyphicon-asterisk"><i class="glyphicon glyphicon-asterisk"></i></li>
							<li data-name="glyphicon-exclamation-sign"><i class="glyphicon glyphicon-exclamation-sign"></i></li>
							<li data-name="glyphicon-gift"><i class="glyphicon glyphicon-gift"></i></li>
							<li data-name="glyphicon-leaf"><i class="glyphicon glyphicon-leaf"></i></li>
							<li data-name="glyphicon-fire"><i class="glyphicon glyphicon-fire"></i></li>
							<li data-name="glyphicon-eye-open"><i class="glyphicon glyphicon-eye-open"></i></li>
						</ul>
					</div>
					<div class="col-md-3">
						<ul class="the-icons">
							<li data-name="glyphicon-eye-close"><i class="glyphicon glyphicon-eye-close"></i></li>
							<li data-name="glyphicon-warning-sign"><i class="glyphicon glyphicon-warning-sign"></i></li>
							<li data-name="glyphicon-plane"><i class="glyphicon glyphicon-plane"></i></li>
							<li data-name="glyphicon-calendar"><i class="glyphicon glyphicon-calendar"></i></li>
							<li data-name="glyphicon-random"><i class="glyphicon glyphicon-random"></i></li>
							<li data-name="glyphicon-comment"><i class="glyphicon glyphicon-comment"></i></li>
							<li data-name="glyphicon-magnet"><i class="glyphicon glyphicon-magnet"></i></li>
							<li data-name="glyphicon-chevron-up"><i class="glyphicon glyphicon-chevron-up"></i></li>
							<li data-name="glyphicon-chevron-down"><i class="glyphicon glyphicon-chevron-down"></i></li>
							<li data-name="glyphicon-retweet"><i class="glyphicon glyphicon-retweet"></i></li>
							<li data-name="glyphicon-shopping-cart"><i class="glyphicon glyphicon-shopping-cart"></i></li>
							<li data-name="glyphicon-folder-close"><i class="glyphicon glyphicon-folder-close"></i></li>
							<li data-name="glyphicon-folder-open"><i class="glyphicon glyphicon-folder-open"></i></li>
							<li data-name="glyphicon-resize-vertical"><i class="glyphicon glyphicon-resize-vertical"></i></li>
							<li data-name="glyphicon-resize-horizontal"><i class="glyphicon glyphicon-resize-horizontal"></i></li>
							<li data-name="glyphicon-hdd"><i class="glyphicon glyphicon-hdd"></i></li>
							<li data-name="glyphicon-bullhorn"><i class="glyphicon glyphicon-bullhorn"></i></li>
							<li data-name="glyphicon-bell"><i class="glyphicon glyphicon-bell"></i></li>
							<li data-name="glyphicon-certificate"><i class="glyphicon glyphicon-certificate"></i></li>
							<li data-name="glyphicon-thumbs-up"><i class="glyphicon glyphicon-thumbs-up"></i></li>
							<li data-name="glyphicon-thumbs-down"><i class="glyphicon glyphicon-thumbs-down"></i></li>
							<li data-name="glyphicon-hand-right"><i class="glyphicon glyphicon-hand-right"></i></li>
							<li data-name="glyphicon-hand-left"><i class="glyphicon glyphicon-hand-left"></i></li>
							<li data-name="glyphicon-hand-up"><i class="glyphicon glyphicon-hand-up"></i></li>
							<li data-name="glyphicon-hand-down"><i class="glyphicon glyphicon-hand-down"></i></li>
							<li data-name="glyphicon-circle-arrow-right"><i class="glyphicon glyphicon-circle-arrow-right"></i></li>
							<li data-name="glyphicon-circle-arrow-left"><i class="a icon-circle-arrow-left s s"></i></li>
							<li data-name="glyphicon-circle-arrow-up"><i class="glyphicon glyphicon-circle-arrow-up"></i></li>
							<li data-name="glyphicon-circle-arrow-down"><i class="glyphicon glyphicon-circle-arrow-down"></i></li>
							<li data-name="glyphicon-globe"><i class="glyphicon glyphicon-globe"></i></li>
							<li data-name="glyphicon-wrench"><i class="glyphicon glyphicon-wrench"></i></li>
							<li data-name="glyphicon-tasks"><i class="glyphicon glyphicon-tasks"></i></li>
							<li data-name="glyphicon-filter"><i class="glyphicon glyphicon-filter"></i></li>
							<li data-name="glyphicon-briefcase"><i class="glyphicon glyphicon-briefcase"></i></li>
							<li data-name="glyphicon-fullscreen"><i class="glyphicon glyphicon-fullscreen"></i></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- <div class="modal-footer">
				<a href="#" class="btn btn-default" data-dismiss="modal">取消</a>
				<a href="#" class="btn btn-primary" id="confirmDel" data-dismiss="modal">确认</a>
			</div> -->
		</div>
	</div>
</div>


<script>
$(function() {
	//加载本服务站商品分类
	searchAllGoodsClass();
});
function searchAllGoodsClass(){
	$.ajax({
		url : "<%=apath%>/level/queryFourLevel",
		async : false,
		success : function(d) {
			var data = d.result;
			var h = "";
			if (data == null || data == "") {
				h += '<tr><td valign="top" colspan="15" class="dataTables_empty">抱歉， 没有找到</td></tr>';
				$("#tableBody").html(h);
				$("#tableBody").treeTable();
			} else {
				$(data).each(
								function(i, e) {
									h += '<tr id="' + e.id + '" pId="0">';
									h += '<td>' + e.categoryCode + '</td>';
									h += '<td>' + e.name + '</td>';
									if (!e.description || typeof(e.description)=="undefined" || e.description=="null" || e.description==null){
										h += '<td></td>';
									}else{
										h += '<td>' + e.description + '</td>';
									}
									h += '<td><a href="javascript:;" onclick=addResource(\"' + e.id + '\",2,\"' + e.name + '\")>新增分类</a></td>';
									h += '</tr>';	
									$(e.children).each(
										function(i, c) {
											h += '<tr id="' + c.id + '" pId="' + e.id + '">';
											h += '<td>' + c.categoryCode + '</td>';
											h += '<td>' + c.name + '</td>';
											if (!c.description || typeof(c.description)=="undefined" || c.description=="null" || c.description==null){
												h += '<td></td>';
											}else{
												h += '<td>' + c.description + '</td>';
											}
											h += '<td><a href="javascript:;" onclick=addResource(\"' + c.id + '\",3,\"' + c.name + '\")>新增分类</a></td>';
											h += '</tr>';		
											$(c.children).each(
													function(i, d) {
														h += '<tr id="' + d.id + '" pId="' + c.id + '">';
														h += '<td>' + d.categoryCode + '</td>';
														h += '<td>' + d.name + '</td>';
														if (!d.description || typeof(d.description)=="undefined" || d.description=="null" || d.description==null){
															h += '<td></td>';
														}else{
															h += '<td>' + d.description + '</td>';
														}
														h += '<td><a href="javascript:;" onclick=addResource(\"' + d.id + '\",4,\"' + d.name + '\")>新增分类</a></td>';
														h += '</tr>';
														$(d.children).each(
																function(i, de) {
																	h += '<tr id="' + de.childrenId + '" pId="' + d.id + '">';
																	h += '<td>' + de.categoryCode + '</td>';
																	h += '<td>' + de.childrenName + '</td>';
																	if (!de.description || typeof(de.description)=="undefined" || de.description=="null" || de.description==null){
																		h += '<td></td>';
																	}else{
																		h += '<td>' + de.description + '</td>';
																	}
																	h += '<td></td>';
																	h += '</tr>';
														    });
											    });
								    });
									
								});
				$("#tableBody").html(h);
				$("#tableBody").treeTable();
				/* $("#tableBody").treeTable({expandable:true});  */
			}
		}/* ,
		error(data){
			alert(data);
		} */
	});
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
			$("#parentName").html("");
			$("#parentName").append("<option value=\"0\">--根目录--</option>");
			$("#parentName").append(HtmlSelect);
			$("#parentId").val("0");
			$("#level").val("1");
			$("#resources-Modal-edit").modal();
		},
		error : function() {
		}
	});
}
//子分类下新增商品
function addResource(id,level,parentName) {
	$.ajax({
		url : "<%=apath%>/level/queryFourLevel",
		async : false,
		type : "GET",
		success : function(data) {
			$("#resources-form")[0].reset();
			var HtmlSelect = paintHtmlSelect(data);
			$("#parentName").html("");
			$("#parentName").append(HtmlSelect);
			$("#parentId").val(id);
			$("#level").val(level);
			$("#parentName").val(id);
			$("#resources-Modal-edit").modal();
		},
		error : function() {
		}
	});
}
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
$("#submitResources").click(
		function() {
			var parentId = $.trim($("#parentId").val());
			//新增
			if (parentId == '') {
				$("#ccid").val("0");
			}
			var name = $.trim($("#name").val());
			if (name == '') {
				optTip2("请输入分类名称!");
				return;
			}
			var categoryCode = $.trim($("#categoryCode").val());
			if (categoryCode == '') {
				optTip2("请输入分类编码!");
				return;
			}
			$.ajax({
				url : "<%=apath%>/admin/addCategory?format=json",
				data : $("#resources-form").serialize(),
				type : "POST",
				success : function(jsonData) {
					unDisabledBtn("submitResources");
					if ('false' == jsonData.success) {
						optTip2("新增失败");
					} else {
						optTip(jsonData);
						location.reload();
						$("#resources-Modal-edit").modal('hide');
					}
				},
				error : function() {
					optTip(false);
					unDisabledBtn("submitResources");
				}
			});
		});
</script>