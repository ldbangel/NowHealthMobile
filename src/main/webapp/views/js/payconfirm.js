var appid,timestamp,noncestr,pack,paysign;
var orderno = $("#orderNo").val();
//微信好友代付
$(".pay-btn").click(function(e){
	var paytype=$('.selet-pwy').attr("data-id")
	 e.preventDefault();
	if(paytype==2){
	   window.location.href = getUrl()+"/paymentInfor/sharedFriendPay.do?orderNo="+orderno;
	}else if(paytype==1){
		wechatPay();
	}else if(paytype==3){
		//首先判断是否有获取到通联返回的userid
		var bankPayUserId=$('#bankPayUserId').val();
		if(bankPayUserId=="" || bankPayUserId==null){
			gettltUserid();//快捷支付，用户注册,获取返回的userid
			var bankPayUserId="<USER>"+Tluserid+"</USER>";
			$('#bankPayUserId').val(bankPayUserId);
		}
		//调用通联移动支付 1.首先生成签名，然后再提交表单
		generateSignature();
		var amount=$('#totalamount').html()
		var amount1=parseInt(amount)*parseInt(100);
		//将支付设定为1分
		$('#orderAmount').val("1");
		$('#signMsg').val(siganuture);
		$('#orderDatetime').val(nowDate);
		$('#form').submit();
		
	}
})
//初始化判断支付所需的userid是否为空
var isagentshare;
$(document).ready(function(){
	isagentshare=$('#isagentshare').val();
	if(bankpayUserid!="" && "null" !== bankpayUserid && isagentshare==0){
		var bankPayUserId="<USER>"+bankpayUserid+"</USER>";
		$('#bankPayUserId').val(bankPayUserId);
	}else{
		$('#bankPayUserId').val('');
	}
	
});
//微信公众号支付
function wechatPay(){
	$.ajax({
		url:getUrl()+"/paymentInfor/wechatPay.do",
		type:"post",
		data:{orderNo:orderno},
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
}
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
/*
 * 银行支付生成签名
 */
var siganuture;
function generateSignature(){
	var inputCharset=$('#inputCharset').val();//字符集
	var pickupUrl=$('#pickupUrl').val();//页面通知地址
	var receiveUrl=$('#receiveUrl').val();//后台回调通知地址
	var version=$('#version').val();//版本号
	var language=$('#language').val();//语言
	var signType=$('#signType').val();//签名类型
	//var merchantId=$('#merchantId').val();//商户号
	var orderNo=$('#orderNo').val();//订单号
	/*var orderAmount=$('#orderAmount').val();//订单金额*/
	var amount=$('#totalamount').html()
	var orderAmount=parseInt(amount)*parseInt(100);
	var orderCurrency=$('#orderCurrency').val();//订单金额货币类型
	var orderDatetime=$('#orderDatetime').val();//商户的订单提交时间
	var bankPayUserId=$('#bankPayUserId').val();//通联返回的userid
	var payType=$('#payType').val();//支付方式
	//var key=$('#key').val();//key
	$.ajax({
		type:"POST",
		url:getUrl()+"/paymentInfor/generateSignature.do", 
		async:false,
		data:{inputCharset:inputCharset,pickupUrl:pickupUrl,receiveUrl:receiveUrl,
			version:version,language:language,signType:signType,orderNo:orderNo,orderAmount:"1",orderCurrency:orderCurrency,orderDatetime:nowDate,
			bankPayUserId:bankPayUserId,payType:payType},
		success:function(result){
			siganuture=result;
		}
   });
}


//注册用户，获取返回的userid
var Tluserid;
function gettltUserid(){
	var orderNo=$('#orderNo').val();//订单号
	$.ajax({
		type:"POST",
		url:getUrl()+"/paymentInfor/getTLuserid.do", 
		async:false,
		data:{isagentshare:isagentshare,orderNo:orderNo},
		success:function(result){
			Tluserid=result;
		}
   });
}