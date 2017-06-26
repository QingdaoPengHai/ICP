<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String fpath = request.getContextPath();
/* String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/"; */
%>

<!-- foot --> 
<%--  <div class="title-txt"><h2>分类</h2></div>
		<div class="ca-ul">
	        <ul class="c-list">
	        <c:forEach items="${firstLevel}" var="IndustryCategory">
				<li><a href="###" class="c-hyperlink">"${IndustryCategory.name}" </a></li>
			</c:forEach>
	        </ul>
		</div>
		<div class="clear"></div>    --%>
	<div class="footer">
		<div class="f-info">
	    <h6>Copyright © 2017-2020 鹏海软件 鲁ICP备10205373号 </h6>
	   </div>
	</div><!--footer结束-->
<script type="text/javascript">
	$(function(){
		$('input').bind('keyup', function () {
			this.value=this.value.replace(/[<|>]/g,'');
			});
	});
</script>
	