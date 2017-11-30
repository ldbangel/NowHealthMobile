<%@ page language="java" import="java.util.*,com.nowhealth.mobile.utils.*,com.nowhealth.mobile.entity.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String userphoneno=request.getParameter("userphoneno")==null?"":request.getParameter("userphoneno"); // 获取bankname
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
    <title>手机短信验证码</title>
    <script type="text/javascript">
    var userphoneno ="<%=userphoneno%>";
    if(userphoneno=="null"){
        userphoneno="";  
    }
    function getUrl() {
		return "<%=path%>";
  	}
    </script>
    <script src="<%=path%>/views/js/jquery.1.7.2.min.js"></script>
   <%--  <script src="<%=path%>/views/js/addBankCard.js"></script> --%>
   <link rel="stylesheet" href="<%=path%>/views/css/sendMessage.css"> 
</head>
<body>
<div class="content">
	  
    <form class="main">                
        <div style="text-align:center;line-height: 45px;"><span style="font-size: 10px;">请输入手机</span></span><span style="font-size:15px;" id="bankname"></span><span style="font-size: 10px;">收到的短信验证码</span></div>   
         <ul class="smscode_list">
                <li><span style="font-size:1rem;margin-left: 8px;">验证码</span></li>
                <li><input type="text" placeholder="请输入验证吗" id="validationNo" style="height:33px" maxlength="6"></li>
                <li><p onclick="getCheckCode();">重新获取</p></li>
         </ul>
        <div class="btn-wrap">
            <a href="javascript:submitForm()"><span>下一步</span></a>
        </div>
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
</body>
<script src="<%=path %>/views/js/sendMessage.js"></script>
<script type="text/javascript">
   	//关闭错误消息提示框
	$("#ensure").click(function(){
		$("#errorhei").hide();
	});
</script>
</html>