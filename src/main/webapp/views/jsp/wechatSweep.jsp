<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String orderNo = request.getParameter("orderno")==null?"":request.getParameter("orderno");
String openid = request.getParameter("openid")==null?"":request.getParameter("openid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>慧英保险</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
   
  </body>
<script src="<%=path%>/views/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript">
var appid,timestamp,noncestr,pack,paysign;
var orderno = "<%=orderNo%>";
var openid = "<%=openid%>";
//取绝对路径地址
function getPath(){
	 var protocol = window.location.protocol;
	 var host = window.location.host;
	 var pathname = window.location.pathname.split('/');
	 var url = protocol+"//"+host+"/"+pathname[1];
	 return url;
}

//微信公众号支付
$(document).ready(function(){
	$.ajax({
		url:getPath()+"/paymentInfor/wechatPayToSweep.do",
		type:"post",
		data:{orderNo:orderno,openid:openid},
		success:function(data){
			var dataObj = eval("("+data.payinfo+")"); //解析回传的json字符串为对象数组
			appid=dataObj.appId;
			timestamp=dataObj.timeStamp;
			noncestr=dataObj.nonceStr;
			pack=dataObj.package;
			paysign=dataObj.paySign;
			callPay();
		},
		complete:function(){
			console.log("appid"+appid+"         pack:"+pack);
		}
	});
});
function onBridgeReady(){
   WeixinJSBridge.invoke(
       'getBrandWCPayRequest', {
           "appId":appid,     			//公众号名称，由商户传入     
           "timeStamp":timestamp,       //时间戳，自1970年以来的秒数     
           "nonceStr":noncestr, 		//随机串     
           "package":pack,     
           "signType":"MD5",         	//微信签名方式     
           "paySign":paysign 			//微信签名 
       },
       function(res){     
           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
        	   window.location.href="http://m.quicksure.com/NowHealthMobile/WechatLogin/goToFirstScreen.do";
           }else{
        	   alert("支付异常，请重新支付！");
           }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
       }
   ); 
}
function callPay(){
	if (typeof WeixinJSBridge == "undefined"){
		if( document.addEventListener ){
			document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
		}else if (document.attachEvent){
			document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
			document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
		}
	}else{
		onBridgeReady();
	}
}
</script>
</html>
