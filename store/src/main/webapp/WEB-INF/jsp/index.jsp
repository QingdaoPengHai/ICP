<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>应用商店</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="css/style.css" rel="stylesheet" type="text/css"/>
<script  type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script  type="text/javascript" src="js/bootstrap.js"></script>
<script  type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/superslide.2.1.js"></script>

</head>
<body>
<div id="mainContent" class="mainContent">
	<div class="head">
		<div class="topnavbar">
		    <div class="toplogo">
			    <a class="headlogo" href="#"><img src="images/logo.jpg" title=""></a>
		    </div>
	    	<div class="narmenu">
	    		<div class="searchgroup">	
					<input type="text" placeholder="请在此处搜索" class="form-control searchinpt" id="searchinpt">
					<button  id="search" name="search" aria-label="搜索" class="search-glyph" title="搜索" ><img src="images/searchicon.png"></button>
		    	</div>
		        <a class="shopping-car" href="###">
	                <span class="s-car-img"></span>
	                <span class="shopping-cart-amount">0</span>
           		</a>
           		<a href="###" class="login">登录</a>
           		<div></div>
   		    </div>
   		   
	    </div>  
	    <div class="menutitle">
	    	<div class="classifmodal">
	    		<div  class="menu-gud">
	    			<a href="###" id="btnallclassif" class="btn-classification">全部分类</a>
	    		</div>
	    		<!--//下拉列表-->
	    		<div id="navtreeview" class="navtreeview">	    				
					<ul class="sidebar-menu">
						<li class="treeview">
							<a href="#">
							  <i class="fa"></i>
							  <span>Layout Options</span>
							</a>
							<ul class="treeview-menu" style="display: none;">
							  <li><a href="#"><i class="fa fa-circle-o"></i> Top Navigation</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Boxed</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Fixed</a></li>
							  <li class=""><a href="#"><i class="fa fa-circle-o"></i> Collapsed Sidebar</a></li>
							</ul>
						</li>
						 
						<li class="treeview">
							<a href="#">
							  <i class="fa fa-pie-chart"></i>
							  <span>Charts</span>
							  <i class="fa fa-angle-left pull-right"></i>
							</a>
							<ul class="treeview-menu">
							  <li><a href="#"><i class="fa fa-circle-o"></i> ChartJS</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Morris</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Flot</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Inline charts</a></li>
							</ul>
						</li>
						<li class="treeview">
							<a href="#">
							  <i class="fa fa-laptop"></i>
							  <span>UI Elements</span>
							  <i class="fa fa-angle-left pull-right"></i>
							</a>
							<ul class="treeview-menu">
							  <li><a href="#"><i class="fa fa-circle-o"></i> General</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Icons</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Buttons</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Sliders</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Timeline</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Modals</a></li>
							</ul>
						</li>
						<li class="treeview">
							<a href="#">
							  <i class="fa fa-edit"></i> <span>Forms</span>
							  <i class="fa fa-angle-left pull-right"></i>
							</a>
							<ul class="treeview-menu">
							  <li><a href="#"><i class="fa fa-circle-o"></i> General Elements</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Advanced Elements</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Editors</a></li>
							</ul>
						</li>
						<li class="treeview">
							<a href="#">
							  <i class="fa fa-table"></i> <span>Tables</span>
							  <i class="fa fa-angle-left pull-right"></i>
							</a>
							<ul class="treeview-menu">
							  <li><a href="#"><i class="fa fa-circle-o"></i> Simple tables</a></li>
							  <li><a href="#"><i class="fa fa-circle-o"></i> Data tables</a></li>
							</ul>
						</li>
					</ul>
										
				</div>	
	    	</div>	    				        
	    </div>

    </div><!--head结束-->
	<!-- 轮播开始-->
	<div class="fullSlide">
		<div class="bd">
			<ul>
				<li _src="url(images/banner1.jpg)" style="background:#E2025E center 0 no-repeat;"><a href="#"></a></li>
				<li _src="url(images/banner2.jpg)" style="background:#DED5A1 center 0 no-repeat;"><a href="#"></a></li>
				<li _src="url(images/banner3.jpg)" style="background:#B8CED1 center 0 no-repeat;"><a href="#"></a></li>
				
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
		<div class="title-txt"><h2>热门产品</h2></div>
		<div class="category-products">
			<div class="col">
				<a href="###">
					<div class="image">
						<img src="images/product_img/pro_img01.jpg">
					</div>
					<div class="pro-tit-inf">
						<h3>零件描述标题</h3>
						<p>简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字</p>
					</div>
					<div class="bt-price">
						<p>了解详情</p>
					</div>
				</a>
			</div>
			<div class="col">
				<a href="###">
					<div class="image">
						<img src="images/product_img/pro_img01.jpg">
					</div>
					<div class="pro-tit-inf">
						<h3>零件描述标题</h3>
						<p>简述零件外观材质等等一些文字简述零件外观材质等等一些文字一些文字简述零件外观材质简述零件外观材质等等一些文字简述零件外观材质等等一些文字</p>
					</div>
					<div class="bt-price">
						<p>了解详情</p>
					</div>
				</a>
			</div>
			<div class="col">
				<a href="###">
					<div class="image">
						<img src="images/product_img/pro_img01.jpg">
					</div>
					<div class="pro-tit-inf">
						<h3>零件描述标题</h3>
						<p>简述零件外观材质等等一等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字</p>
					</div>
					<div class="bt-price">
						<p>了解详情</p>
					</div>
				</a>
			</div>
			<div class="col">
				<a href="###">
					<div class="image">
						<img src="images/product_img/pro_img01.jpg">
					</div>
					<div class="pro-tit-inf">
						<h3>零件描述标题</h3>
						<p>简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字</p>
					</div>
					<div class="bt-price">
						<p>了解详情</p>
					</div>
				</a>
			</div>
			<div class="clear"></div>
			
	
			
		</div><!--content结束-->
		<div class="title-txt"><h2>热门产品</h2></div>
		<div class="category-products">
				<div class="col">
					<a href="###">
						<div class="image">
							<img src="images/product_img/pro_img01.jpg">
						</div>
						<div class="pro-tit-inf">
							<h3>零件描述标题</h3>
							<p>简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字</p>
						</div>
						<div class="bt-price">
							<p>了解详情</p>
						</div>
					</a>
				</div>
				<div class="col">
					<a href="###">
						<div class="image">
							<img src="images/product_img/pro_img01.jpg">
						</div>
						<div class="pro-tit-inf">
							<h3>零件描述标题</h3>
							<p>简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字</p>
						</div>
						<div class="bt-price">
							<p>了解详情</p>
						</div>
					</a>
				</div>
				<div class="col">
					<a href="###">
						<div class="image">
							<img src="images/product_img/pro_img01.jpg">
						</div>
						<div class="pro-tit-inf">
							<h3>零件描述标题</h3>
							<p>简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字</p>
						</div>
						<div class="bt-price">
							<p>了解详情</p>
						</div>
					</a>
				</div>
				<div class="col">
					<a href="###">
						<div class="image">
							<img src="images/product_img/pro_img01.jpg">
						</div>
						<div class="pro-tit-inf">
							<h3>零件描述标题</h3>
							<p>简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字简述零件外观材质等等一些文字</p>
						</div>
						<div class="bt-price">
							<p>了解详情</p>
						</div>
					</a>
				</div>	
				<div class="clear"></div>
			</div>		
		
		<div class="title-txt"><h2>分类</h2></div>
		<div class="ca-ul">
	        <ul class="c-list">
	            <li><a href="###" class="c-hyperlink">制衣配件 </a></li>
	            <li><a href="###" class="c-hyperlink">工厂零部件 </a></li>
	            <li><a href="###" class="c-hyperlink">机器润滑油 </a></li>
	            <li><a href="###" class="c-hyperlink">冰箱配件 </a></li>
	            <li><a href="###" class="c-hyperlink">机器零部件 </a></li>
	            <li><a href="###" class="c-hyperlink">制衣配件 </a></li>
	            <li><a href="###" class="c-hyperlink">工厂零部件 </a></li>
	            <li><a href="###" class="c-hyperlink">机器润滑油 </a></li>
	            <li><a href="###" class="c-hyperlink">冰箱配件 </a></li>
	            <li><a href="###" class="c-hyperlink">机器零部件 </a></li>
	            <li><a href="###" class="c-hyperlink">制衣配件 </a></li>
	            <li><a href="###" class="c-hyperlink">工厂零部件 </a></li>
	            <li><a href="###" class="c-hyperlink">机器润滑油 </a></li>
	            <li><a href="###" class="c-hyperlink">冰箱配件 </a></li>
	            <li><a href="###" class="c-hyperlink">机器零部件 </a></li>
	            <li><a href="###" class="c-hyperlink">制衣配件 </a></li>
	            <li><a href="###" class="c-hyperlink">工厂零部件 </a></li>
	            <li><a href="###" class="c-hyperlink">机器润滑油 </a></li>
	            <li><a href="###" class="c-hyperlink">冰箱配件 </a></li>
	            <li><a href="###" class="c-hyperlink">机器零部件 </a></li>
	        </ul>
		</div>
		<div class="clear"></div>
	</div><!--content结束-->		
	<div class="footer">
		<div class="f-info">
    <h6>Copyright © 2017-2020 鹏海软件 鲁ICP备10205373号 </h6>
  </div>
	</div><!--footer结束-->
</div>	

</body>
</html>