<%@ page language="java" import="java.util.*,com.nowhealth.mobile.utils.*,com.nowhealth.mobile.entity.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
   <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible"; content="IE=edge">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <meta name="full-screen" content="yes">
    <meta name="x5-fullscreen" content="true">
    <meta name="browsermode" content="application">
    <meta name="x5-page-mode" content="app">
    <link rel="stylesheet" type="text/css" href="<%=path%>/views/css/css.css">
    <title>关注Dr.康</title>

  </head> 
 <body style="font-size:100%; background-image:url(views/images/now2017.jpg);background-size:100% 100%;">
 <div class="top">
	     <%--  <div class="div600">
		      <table style='width:100%;background: rgba(255,255,255,0.5);'>
		         <tr>
		           <td class='top_tr1'><img src="<%=path%>/views/images/logo_nowHealth.png" style='max-width:100%;'></td>
		         </tr>
		      </table>
	     </div> --%>
	   </div>
  <div style="padding-top: 55%;">
    <%-- <div style="margin-left:28%;margin-bottom:2%">长按此处二维码进行分享<img style="margin-left:0px;width:40px;" src="<%=path%>/views/images/gt002.gif"></div> --%>
	<center><img style="width:50%;height:27%;margin-bottom:10%"id="qrcode" src="<%=path%>/views/images/nowHealQR.jpg"/></center>
	<div style="text-align: center;">
	      <a style="text-decoration:none;display: inline-block;width: 50%;color: #fff;background: #f63c30;border-radius: 5px;font-size: 13px;line-height: 35px;" href="<%=path%>"><b>返回首页</b></a>
	</div>
</div>
</body>
<script src="<%=path%>/views/js/jquery-1.9.1.min.js"></script>
</html>
