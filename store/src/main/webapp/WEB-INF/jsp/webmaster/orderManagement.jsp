<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>
<link href="<%=apath%>/resources/css/orderList.css" rel="stylesheet" type="text/css"/>
<div>
	<ul class="breadcrumb">
		<li><a href="#">首页</a></li>
		<li><a href="#">订单管理</a></li>
		<li><a href="#">订单管理</a></li>
	</ul>
</div>
<div class="ordersContainer">
<div class="ordersPagination"><div class="filters"></div>
<div class="pages"><span class="page_index">共${fn:length(ordersAndGoods)}个订单</span>
<!-- <a href="javascript:;" class="btn unactive">上一页</a><a href="javascript:;" class="btn unactive">下一页</a> -->
</div></div>					 	
<!-- 订单列表 -->
<div class="ordersList">

<c:forEach items="${ordersAndGoods}" var="order">
<!-- 订单1 -->
<div class="ordersBox"><div class="order"><div class="title">
<div class="time"  style="width: 31.5993266%;"><div class="inner">
<p>下单时间：${order.buyTime}</p><p>订单编号：${order.orderCode}</p></div></div>
<div class="status" style="width: 29.68013468%;"><div class="inner"><p>
<!-- <span>订单状态：</span> -->

</p></div></div>
<div class="payInfo"><div class="inner"><!-- <p class="payMethod"><span>支付方式：</span><span>支付宝</span></p> -->
<p>订单金额：￥${order.totalPrice}</p></div></div>
<div class="link"><div class="inner"><p><!-- <a href="/my-account/order/1661573256">订单详情</a> --></p></div></div></div>
<div class="con">
<div class="paymentBar">
<c:if test="${order.status=='0'} and ${order.isDelete=='0'}">
	<%-- <a class="cancelOrder" style="z-index:3;" href="<%=apath%>/order/cancelOrder?id=${order.id}">取消订单</a> --%>
	<!-- <a class="pay" style="z-index:3;" href="/alipay/requestController/1661573256">付款</a> -->
</c:if>

</div>

<div class="products">
			<c:forEach items="${order.goodList}" var="good">
	    		<div class="product"><div class="mainCon">
	    		<div class="productInfos">
					<div class="img"><a href="<%=apath%>/goodDetail/toGoodDetail?goodsId=${good.goodId}">
					<img style="width:80%" src="${good.goodUrl}">
					 </a></div>
					 <div class="otherInfo">
					   <div class="desc" style="width:47%"><p class="name"><span> </span>
						 <a href="<%=apath%>/goodDetail/toGoodDetail?goodsId=${good.goodId}">${good.goodName}</a></p>
						 </div>
						 <div class="price"  style="width:33%"><p>价格：￥${good.goodPrice}</p><p>数量：${good.goodNumber}</p>
						</div>
						<div class="desc"  style="width:15%">
						 <p class="name">
							 <c:choose>  
							   <c:when test="${good.status=='0'}">
							   	 <input id='${good.orderGoodsFileId}' 
							   	 class="btn btn-block btn-primary" 
							   	 type="button" 
							   	 style="width:100px" 
							   	 onclick="showEditModal(
								   	 '${order.id}',
								   	 '${order.userId}',
								   	 '${good.goodId}',
								   	 '${good.goodsModel}',
								   	 '${good.orderGoodsFileId}',
								   	 '0')" 
							   	 value="创建服务包">
							   </c:when> 
							   <c:when test="${good.status=='1'}">
							   	 <input id='${good.orderGoodsFileId}' 
							   	 class="btn btn-block btn-primary" 
							   	 type="button" 
							   	 style="width:100px" 
							   	 onclick="showEditModal(
								   	 '${order.id}',
								   	 '${order.userId}',
								   	 '${good.goodId}',
								   	 '${good.goodsModel}',
								   	 '${good.orderGoodsFileId}',
								   	 '1')" 
							   	 value="编辑服务包">
							   </c:when>  
							   <c:when test="${good.status=='2'}">
							   	 <input id='${good.orderGoodsFileId}' 
							   	 class="btn btn-block btn-primary" 
							   	 type="button" 
							   	 style="width:100px" 
							   	 onclick="showEditModal(
								   	 '${order.id}',
								   	 '${order.userId}',
								   	 '${good.goodId}',
								   	 '${good.goodsModel}',
								   	 '${good.orderGoodsFileId}',
								   	 '1')" 
							   	 value="编辑服务包">
							   </c:when>     
							   <c:otherwise>  
								 <em>异常</em>
							   </c:otherwise>  
							</c:choose>  
						 </p>
							<!-- 已生成 已下载 -->
						</div>
					</div>
					
				</div>
				</div>
				</div>
			</c:forEach>   
			
</div>
</div></div></div>
<!-- end 订单1 -->
</c:forEach>  



</div>
<!-- end 订单列表 -->					 	
					 	
					 		 
				</div><!--user-content-info结束-->
<!-- 编辑模态框  by xc-->
<div class="modal fade" id="xml-Modal-edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>编辑服务包</h3>
			</div>
			<div class="modal-body">
				<div id="xmlContentForm">
					<input type="hidden" id="orderId"/>
					<input type="hidden" id="userId"/>
					<input type="hidden" id="goodsId"/>
					<input type="hidden" id="goodsModel"/>
					<input type="hidden" id="orderGoodsFileId"/>
					<input type="hidden" id="isEdit"/>
					<div class="form-group">
						<label for="version">版本号</label>
						<input type="tel" class="form-control" id="version" name="version" 
						onkeyup="this.value=this.value.replace(/\D/g,'')" />
					</div>
					<div class="form-group">
						<label for="xmlContent">服务包内容</label>
						<textarea class="form-control input-lg" id="xmlContent" name="xmlContent"  style="height:400px;"></textarea>
					</div>
					<button type="button" class="btn btn-default btn-primary" id="submitBtn" onclick="submitXMLContent(0)">保存</button>
					<button type="button" class="btn btn-default btn-primary" id="closeBtn" data-dismiss="modal">放弃</button>
				</div>
			</div>
		</div>
	</div>
</div>				
<script type="text/javascript">
	//显示编辑服务包模态框
	function showEditModal(orderId,userId,goodsId,goodsModel,orderGoodsFileId,isEdit){
		//初始化隐藏参数
		$("#orderId").val(orderId);
		$("#userId").val(userId);
		$("#goodsId").val(goodsId);
		$("#goodsModel").val(goodsModel);
		$("#orderGoodsFileId").val(orderGoodsFileId);
		$("#isEdit").val(isEdit);
		//清空显示的值
		$("#version").val("");
		$("#xmlContent").val("");
		if("1"==isEdit){
			//若为编辑则查出原有数据
			$.ajax({
	 			url : "<%=apath%>/order/getUserOrderXmlDetailByOrderIdAndGoodsId",
	 			data : {"orderId":orderId,"goodsId":goodsId},
	 			type:"POST",
	 			dataType:"json",
	 			async : false,
	 			success : function(json){
	 				if(json.result=='1'){
	 					$("#version").val(json.version);
	 					$("#xmlContent").val(json.xmlContent);
	 				}else{
	 					optTip2(json.message);
	 				}
	 			},error: function(a){
	 				optTip2("查询失败，请刷新页面后重试！");
	 			}
	 		});
		}
		$("#xml-Modal-edit").modal();
	}
	
	function submitXMLContent(){
		
		var orderId = $("#orderId").val();
		var userId = $("#userId").val();
		var goodsId = $("#goodsId").val();
		var goodsModel = $("#goodsModel").val();
		var isEdit = $("#isEdit").val();
		var version = $("#version").val();
		var xmlContent = $("#xmlContent").val();
		$.ajax({
 			url : "<%=apath%>/order/editOrderGoodsXml",
 			data : {
 					"orderId":orderId,
 					"userId":userId,
 					"goodsId":goodsId,
 					"goodsModel":goodsModel,
 					"isEdit":isEdit,
 					"version":version,
 					"xmlContent":xmlContent
 					},
 			type:"POST",
 			dataType:"json",
 			async : false,
 			success : function(json){
 				if(json.result=='1'){
 					var orderId = $("#orderId").val();
 					var userId = $("#userId").val();
 					var goodsId = $("#goodsId").val();
 					var goodsModel = $("#goodsModel").val();
 					var orderGoodsFileId = $("#orderGoodsFileId").val();
 					var isEdit = $("#isEdit").val();
 					$("#xml-Modal-edit").modal("hide");
 					//清空值
 					$("#orderId").val("");
 					$("#userId").val("");
 					$("#goodsId").val("");
 					$("#goodsModel").val("");
 					$("#isEdit").val("");
 					$("#version").val("");
 					$("#xmlContent").val("");
 					$("#orderGoodsFileId").val("");
 					optTip2(json.message);
 					unDisabledBtn("submitBtn");
 					if('0'==isEdit){
 						//改变按钮的显示值
 						$("#"+orderGoodsFileId).val("编辑服务包");
 						//改变按钮的onclick方法
 						$("#"+orderGoodsFileId).attr('onclick',
 								'showEditModal(\''+orderId+'\',\''+userId+'\',\''+goodsId+'\',\''+goodsModel+'\',\''+orderGoodsFileId+'\',\''+1+'\')');
 					}
 				}else{
 					optTip2(json.message);
 				}
 			},error: function(a){
 				optTip2("提交失败，请刷新页面后重试！");
 			}
 		});
	}
</script>