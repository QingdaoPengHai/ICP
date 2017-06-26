<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String apath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+apath+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>ICP-store-goodDetail</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=apath%>/resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/css/style.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/css/microsoft/cf-7ecfa4-68ddb2ab.css" rel="stylesheet" type="text/css"/>
<link href="<%=apath%>/resources/css/microsoft/store-chinese-simplified-default.min.css" rel="stylesheet" type="text/css"/>

<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/charisma-app.css">
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/jquery.noty.css">
<link rel="stylesheet" type="text/css" href="<%=apath%>/resources/css/noty_theme_default.css">

<script src ="<%=apath%>/resources/js/jquery-3.1.1.min.js"></script>
<script src ="<%=apath%>/resources/js/jquery.noty.js"></script>
<script src ="<%=apath%>/resources/js/charisma.js"></script>

</head>

<body id="mainContent" class="mainContent">
<!-- 头导航栏 -->
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<!-- end 头导航栏 -->

<div class="page-header pdp-header cli_sku-selector">

<style>
    .m-product-detail-hero .m-product-detail-hero-xpa {
        padding-top: 0;
        padding-left: 12px;
    }

    .m-product-detail-hero .m-product-detail-hero-xpa .hero-xpa{
        margin-left: -5px;
    }

    .m-product-detail-hero .m-product-detail-hero-age-rating + .m-product-detail-hero-xpa {
        padding-top: 20px;
    }

    .m-product-detail-hero .m-product-detail-hero-social + .m-product-detail-hero-xpa {
        padding-top: 20px;
    }

    @media only screen and (min-width: 540px) {
        .m-product-detail-hero .m-product-detail-hero-xpa {
            width: 50%;
            padding-top: 0;
            display: inline-block;
            float: left;
        }
    }

    @media only screen and (min-width: 1084px) {
        .m-product-detail-hero .m-product-detail-hero-xpa {
            width: 100%;
            padding-top: 0;
            padding-left: 0;
            display: block;
            float: none;
        }

        .m-product-detail-hero .m-product-detail-hero-xpa {
            padding-top: 108px;
        }
    }

    @media only screen and (max-width: 1083px) {
        *[dir="rtl"] .m-product-detail-hero .m-product-detail-hero-social.oneui-override+.m-product-detail-hero-age-rating.oneui-override {
            padding-right: 36px;
        }
        .m-product-detail-hero .m-product-detail-hero-social.oneui-override+.m-product-detail-hero-age-rating.oneui-override {
            padding-left: 36px;
        }
    }

    @media only screen and (max-width: 768px) {
        *[dir="rtl"] .m-product-detail-hero .m-product-detail-hero-social.oneui-override+.m-product-detail-hero-age-rating.oneui-override {
            padding-right: 24px;
        }
        .m-product-detail-hero .m-product-detail-hero-social.oneui-override+.m-product-detail-hero-age-rating.oneui-override {
            padding-left: 24px;
        }
    }

    @media only screen and (max-width: 540px) {
        *[dir="rtl"] .m-product-detail-hero .m-product-detail-hero-social.oneui-override+.m-product-detail-hero-age-rating.oneui-override {
            padding-right: 12px;
        }
        .m-product-detail-hero .m-product-detail-hero-social.oneui-override+.m-product-detail-hero-age-rating.oneui-override {
            padding-left: 12px;
        }
    }
    
</style>

<section class="m-product-detail-hero m-digital-good-hero srv_appItemDetailsHeader" role="main">
    <div data-grid="container stack-3">
        <div data-grid="col-12" class="m-product-detail-hero-product-placement oneui-override">
            <picture class="srv_appHeaderBoxArt c-image" style="max-width:358px;">
                <img class="c-image cli_image srv_screenshot srv_microdata" src="${goodDetail.goods.defaultPicture}" style="background-color: #a42323">
            </picture>
            <div class="context-product-placement-data oneui-override srv_appHeaderTitleArea">
                <h1 id="page-title" class="c-heading-2 srv_title">${goodDetail.goods.goodName}</h1>
                <dl>
                    <dd class="context-product-details">
                        <!-- <div>浙江核新同花顺网络信息股份有限公司</div> -->
                        <p id="product-description" class="m-product-detail-description max-height-override oneui-override srv_description" style="white-space: pre-line">
                        	</p>
							<script>
                               $(function () {
                            	   $("#product-description").html("${goodDetail.goods.description}");
                               });
                           </script>
                        	
                    </dd>
                    <dt class="x-screen-reader">价格</dt>
                    <dd class="">
                        <div class="price-info">
						<style>
						div.price-text {overflow: hidden;font-weight: normal;}
						.srv_countdown, .cli_upsell-option {font-size: 15px;font-weight: 400;line-height: 20px;}
						</style>
						<div class="c-price">
						
						        <div class="price-text srv_price">
						            <div class="ea-vault-message hidden x-hidden">
						                <div>The Vault 提供</div>
						                <div>或</div>
						            </div>
						       <!--      <s class="srv_saleprice" aria-label="全价为 ¥124.50">¥124.50</s> -->
						            <span>&nbsp;</span>
						            <div class="price-disclaimer">
						                <span>￥${goodDetail.goods.price}</span>
						                
						            </div>
						            <span>&nbsp;</span>
						            <span></span>
						        </div>
						
						    <div class="srv_microdata" itemprop="offers" itemscope="" itemtype="http://schema.org/Offer">
						        <meta itemprop="price" content="12.5">
						        <meta itemprop="priceCurrency" content="CNY">
						    </div>
						</div>
 			</div>
                    </dd>

    <section id="srv_purchaseCTA" class="btn-group-justified">
        <div class="srv_addCartContainer">
            <div class="cli_purchaseOnThreshold">
             <a onclick="addShoppingCart('${goodDetail.goods.id}')" class="srv_purchaseButton get-link-adjustment c-call-to-action c-glyph" style="cursor: pointer;">
               		加入购物车
            </a>
            <script>
			$(document).ready(function(){
			  $(".cli_purchaseOnThreshold a").hover(function(){
			    $(".cli_purchaseOnThreshold a").css("opacity","0.7");
			    $(".cli_purchaseOnThreshold a").css("color","#FFF");
			    },function(){
			    $(".cli_purchaseOnThreshold a").css("opacity","1.0");
			    $(".cli_purchaseOnThreshold a").css("color","#FFF");
			  });
			});
			</script>
            </div>
        </div>
        
		<div class="c-caption-1">
		        <div>
		<span>
		可能需要某些硬件。有关详细信息，请参阅 <a class="c-hyperlink" href="#system-requirements" >系统要求</a>。    
		</span>        </div>
		        <!-- <div>
		你可以在 Xbox One 主机上进行购买。(Microsoft.com 购买在你所在的地区不可用。)        </div> -->
		</div>

    </section>
                </dl>
            </div>
        </div>
    </div>
</section>
</div>
 
<!-- foot.jsp -->
<%@ include file="/WEB-INF/jsp/foot.jsp"%>
<!-- end foot.jsp -->

</body>
<script type="text/javascript">
$(document).ready(function(){
	var h1= $(window).height(); 
	h1=h1-220;
	$("div.page-header").css({		minHeight: h1	});
	window.onresize = function () {
		$("div.page-header").css({ minHeight:$(window).height()-220 });
	}
});
  function addShoppingCart(goodsId){
  $.ajax({
	url : "<%=apath%>/shoppingCart/addShoppingCart",
		data : {"goodsId":goodsId},
		type:"POST",
		dataType:"json",
		async : false,
		success : function(json){
			if(json.result=='1'){
				optTip2("加入购物车成功！");
			}else if(json.result=='2'){
				optTip2("购物车中已存在！");
			}else{
				optTip2("加入购物车失败，请刷新页面后重试！");
			}
			getShoppingCartCount();
		},error: function(a){
			optTip2("加入购物车失败，请刷新页面后重试！！");
		}
	});
}
  </script>
     
 </html>