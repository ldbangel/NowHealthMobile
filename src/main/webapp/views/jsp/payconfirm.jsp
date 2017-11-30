<%@ page language="java" import="java.util.*,com.nowhealth.mobile.utils.*,com.nowhealth.mobile.entity.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String nowDate=DateFormatUtils.getSystemDateByYYYYMMDD();
UserInfor userinfor=null;
if(session.getAttribute("loginUser")!=null){
  userinfor=(UserInfor)session.getAttribute("loginUser");
}
String bankpayUserid = userinfor!=null?userinfor.getBankPayUserId():"";
String merchantId=SysConstantsConfig.TLTMERCHANT_ID; //  通联银行支付商户号
String payUrl=SysConstantsConfig.TLTPAY_URL;//通联支付url地 址
String receiveUrl=SysConstantsConfig.TLTRECEIVE_URL;//通联支付后台回调地 址
String pickupUrl=SysConstantsConfig.TLTPICKUP_URL;//通联支付页面回调地 址
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="x-rim-auto-match" content="none">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <title>订单支付</title>
    <link rel="stylesheet" href="<%=path%>/views/css/payconfirm.css">
    <script src="<%=path%>/views/js/jquery.1.7.2.min.js"></script>
    <script type="text/javascript">
      function getUrl() {
		return "<%=path%>";
  	  }
  	  /*商户订单获取时间  */
  	  var nowDate = "<%=nowDate%>";
  	  var bankpayUserid="<%=bankpayUserid%>";
    </script>
    
</head>
<body>
<form >
	<input type="text" style="display:none" name="orderNo" id="orderNo" value="${baseinfor.orderno}"/>
	<div class="content">
	    <!-- <div class="header">
	          <img src="/NowHealthMobile/views/images/card-pay.png" style="width:27px;vertical-align:middle;margin-right: 15px;"><span>银行卡支付</span>
	    </div> -->
	    <div class="bankcard-box"><!-- <p>您暂时未添加银行卡...</p> -->
		    <div class="bc-list">
				     <ul class="pay-way" title="&nbsp;银行卡&nbsp;" data-id="3" style="border:none;">
			            <li style="text-align:center;"><img src="/NowHealthMobile/views/images/card-pay.png" style="width:27px;vertical-align:middle;margin-right: 15px;"></li>
			            <li style="line-height: 52px;"><span>银行卡支付</span></li>
			            <li style="text-align: right;position: relative;"><span class="checkbox "><input type="checkbox"></span></li>
			        </ul>
			      <!-- <ul class="sg-box">
			        <li class="ls-top">
			          <i><img src="/NowHealthMobile/views/images/zhongguo-bc.png"></i>
			          <div><h4>中国银行</h4><p>储蓄卡</p></div>
			        </li>
			        <li class="ls-btm"><span>**** **** **** 3037</span></li>
			      </ul> -->
	         </div>
	    </div>
	  <!--   <div class="add-bc"><span><i class="row"></i><i class="col"></i></span>其他银行卡 <b></b></div> -->
	    <div class="other-pay-way">
	        <div><img src="/NowHealthMobile/views/images/bc-payway.png" style="width:21px;vertical-align:middle;margin-right: 15px;">其它支付方式</div>
	        <ul class="pay-way select" title="&nbsp;微信支付&nbsp;" data-id="1">
	            <li style="text-align:center;"><img src="<%=path%>/views/images/wx.png" style="width:49px;"></li>
	            <li onclick="wechatPay()"><div class="wx-pay"><h4>微信支付</h4><span style="font-size: 12px;color:#666;">微信安全支付</span></div></li>
	            <li style="text-align: right;position: relative;"><span class="checkbox check-icon"><input type="checkbox" checked="checked"></span></li>
	        </ul>
	        <ul class="pay-way" title="&nbsp;好友代付&nbsp;" data-id="2">
	            <li style="text-align:center;"><img src="<%=path%>/views/images/dai-fu.png" style="width:49px;"></li>
	            <li style="line-height: 55px;">微信扫一扫代付</li>
	            <li style="text-align: right;position: relative;"><span class="checkbox "><input type="checkbox"></span></li>
	        </ul>
	    </div>
	    <div class="pay-btn">
	        使用<span class="selet-pwy" data-id="1"> 微信支付 </span><span id="totalamount">${baseinfor.totalamount}</span>元
	    </div>
	</div>
	<input type="text" style="display:none" value="${baseinfor.isagentshare}" id="isagentshare"/>
</form>
<form action="<%=payUrl%>" method="post" id="form">
	<div style="display:none">
		<!-- 通联移动支付支付所需要的参数 -->
		<!-- 字符集 -->                
		<input type="text" name="inputCharset" value="1" id="inputCharset"/>
		<!-- 取货地址， 页面通知地址 首页--> 
		<input type="text" name="pickupUrl" value="<%=pickupUrl%>" id="pickupUrl"/>
		<!-- 商户系统通知地址:回填后台通知地址  -->
		<input type="text" name="receiveUrl" value="<%=receiveUrl%>" id="receiveUrl"/>
		<!-- 版本号 -->				
		<input type="text" name="version" value="v1.0" id="version"/>
		<!--语言 1.代表utf-8  -->      
		<input type="text" name="language" value="1" id="language"/>
		<!-- 签名类型 -->				
		<input type="text" name="signType" value="0" id="signType"/>
		<!--商户号  -->                
		<input type="text" name="merchantId" value="<%=merchantId%>" id="merchantId"/>
		<!--收款人姓名  -->				
		<input type="text" name="payerName" value=""/>
		<!-- 收款人联系email -->	
		<input type="text" name="payerEmail" value=""/>
		<!-- 收款人电话 -->
		<input type="text" name="payerTelephone" value=""/>
		<!--收款人证件号码  -->
		<input type="text" name="payerIDCard" value=""/>
		<!--合作伙伴的商户号 -->
		<input type="text" name="pid" value=""/>
		<!--  商户系统的订单号-->
		<input type="text" name="orderNo"  id="orderNo" value="${baseinfor.orderno}"/>
		<!-- 订单金额单位为分 -->
		<input type="text" name="orderAmount" id="orderAmount" value=""/>
		<!-- 订单金额币种类型  默认为人民币;0,156表示人民币;344表示港币;840表示美元-->
		<input type="text" name="orderCurrency" value="0" id="orderCurrency"/>
		<!-- 商户的订单提交时间:  -->
		<input type="text" name="orderDatetime" id="orderDatetime" value=""/>
		<!-- 订单的过期时间 -->
		<input type="text" name="orderExpireDatetime" value=""/>
		<!-- 商品名称 -->
		<input type="text" name="productName" value=""/>
		<!-- 商品单价 -->
		<input type="text" name="productPrice" value=""/>
		<!--商品数量  -->
		<input type="text" name="productNum" value=""/>
		<!--商品标识 -->
		<input type="text" name="productId" value=""/>
		<!-- 商品描述 -->
		<input type="text" name="productDesc" value=""/>
		<!--扩展字段二  -->
		<input type="text" name="ext1" value="" id="bankPayUserId"/>
		<!--扩展字段二  -->
		<input type="text" name="ext2" value=""/>
		<!--业务扩展字段  -->
		<input type="text" name="extTL" value=""/>
		<!-- 支付方式 -->
		<input type="text" name="payType" value="0" id="payType"/>
		<!-- 发卡方代码 -->
		<input type="text" name="issuerId" value="" />
		<!--付款人支付卡号  -->
		<input type="text" name="pan" value=""/>
		<!-- 贸易类型 -->
		<input type="text" name="tradeNature" value=""/>
		<!--用于计算signMsg的key值  --><!--所有非空参数与密钥key组合  -->
		<!-- <input type="text" name="key" id="key" value="1234567890"/>
		海关扩展字段 
		<input type="text" name="customsExt" id="customsExt" value=""/> -->
		<!-- 签名 -->
		<input type="text" name="signMsg" value="" id="signMsg"/>
	</div>
</form>
</body>
<script>
    $(".pay-way").click(function(){
        var This=$(this),pwy=$(".selet-pwy"),t_data=This.attr("data-id");
        pwy.html(This.attr('title'));
        pwy.attr("data-id",t_data)
      if(This.hasClass("select")){
                  return true;
          }else{
            var select_elm=$(".select");
            select_elm.find(".checkbox").removeClass('check-icon');
            select_elm.find("input").removeAttr("checked");
            select_elm.removeClass("select");
            This.addClass("select");
            This.find(".checkbox").addClass('check-icon');
            This.find("input").attr("checked","checked");
       }
    })
</script>
<script src="<%=path%>/views/js/payconfirm.js"></script>
</html>