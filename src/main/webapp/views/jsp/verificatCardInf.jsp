<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
    <meta name="keywords" content="晶算师保险,特价保险,互联网保险,少儿保险,儿童保险,意外伤害保险,个人重大疾病保险,健康保险,意外保险,交通意外险">
    <meta name="description" content="晶算师是国内创新型互联网保险服务平台，是特惠保险的先行者，晶算师聚焦于意外险和健康疾病险等纯保障型产品，将保险回归保障本源，并以一种简单而健康的方式呈现给消费者。">
    <title>验证银行卡信息</title>
    <script type="text/javascript">
    function getUrl() {
		return "<%=path%>";
  	}
    </script>
   <link rel="stylesheet" href="<%=path%>/views/css/verificatCardInf.css"> 
</head>
<body>
<div class="content">
	  
    <form class="main">                
        <div class="bind-user-box">
            <table> 
                <tr>
                    <td>银行卡:</td>
                    <td colspan="2"><span style="font-size:15px;margin-left: 20px;" id="bankname"></span></td>
                </tr> 
                 <tr>
                    <td>卡号:</td>
                    <td colspan="2"><span style="font-size:15px;margin-left:37px;" id="banknum"></span></td>
                </tr>
                <tbody><tr class="tip" style=""><td colspan="3">提醒：绑到该持卡人的银行卡</td></tr></tbody>
                <tr>
                    <td>持卡人</td>
                    <td colspan="2"><input type="text"placeholder="持卡人姓名" name="payee" id="payee" maxlength="4" onblur="Trim(this);"></td>
                </tr>
                <tr>
                    <td>身份证</td>
                    <td colspan="2"><input type="text"placeholder="请输入证件号码" name="payeeid" id="payeeid" maxlength="18" onblur="Trim(this);" onkeyup="if(value!=value.replace(/[\W]/g,''))value=value.replace(/[\W]/g,'')"></td>
                </tr>
                <tr class="last">
                    <td>手机号</td>
                    <td colspan="2"><input type="text"placeholder="银行预留的手机号码" name="banknumber" id="userphoneno" maxlength="11" onblur="Trim(this);" onKeyUp="if(value!=value.replace(/[^\d]/g,''))value=value.replace(/[^\d]/g,'')"></td>
                </tr>
            </table>
        </div>
        <div class="btn-wrap">
            <a href="javascript:submitForm()"><span>验证信息</span></a>
        </div>
        <input type="text" id="banknumber" name="banknumber" style="display:none" value="${banknumber}"/>
        <input type="text" id="bankprop" name="bankprop" style="display:none" value="${bankprop}"/>
    </form>
</div>
<!--错误信息提示框  -->
<div id="errorhei" class="errorhei" style="z-index: 100; display:none;position: fixed; width: 100%;height: 100%; background: rgba(0,0,0,0.5);top: 0;">
    <div style="   position: fixed;top: 35%; width:100%;height:auto;text-align:center;">
        <div style="width:85%;margin:0 auto;max-width:300px;background:#fff;color: #333; border-radius:5px;padding: 20px 0px 3px 0px;box-shadow: 0 0 20px 2px #333;">
            <h2 style="font-size: 17px;color: #222;color: #aeaeae;padding-bottom:5px;">提示信息</h2>
            <p style="margin-bottom: 10px;font-size: 12px;padding: 15px 20px;text-align: center; color:#444;" id="Message"></p >
            <div style="border-top:1px solid #ddd;font-size:17px;color:#333;padding: 8px 0px;"><span style="display:block;width:100%;font-size:15px" id="ensure">确定</span></div>
        </div>
    </div>
</div>
<!--页面加载框  -->
<div style="position:fixed;top:0px;left:0px;width:100%;height:100%;z-index: 100;display:none;"id="pop">
<div style="position: fixed;top:35%; width:100%;height:auto;text-align:center;">
     <span style='display:inline-block;height: auto;border-radius: 5px;background: rgba(0,0,0,0.6);color: #fff;font-size: 10px;letter-spacing: 2px; padding: 20px;'>
        <img  src="<%=path%>/views/images/mu_loading.gif" style='width: 36px; margin-bottom: 5px;'>
        <p id="prompt"></p>
	</span>	 
</div>
</div>
</body>
<script src="<%=path%>/views/js/jquery.1.7.2.min.js"></script>
<script src="<%=path %>/views/js/verificatCardInf.js"></script>
<script type="text/javascript">
   	//关闭错误消息提示框
	$("#ensure").click(function(){
		$("#errorhei").hide();
	});
</script>
</html>