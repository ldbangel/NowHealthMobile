<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>绑定账号</title>
    <link rel="stylesheet" href="<%=path%>/views/css/wxlogin.css">
</head>
<body>
<div class="content">
    <div class="header">绑定账号 </div>
    <div class="main">
        <div class="Dr-img">
            <img src="<%=path%>/views/images/js_img_binding_7689b6c.png" >
        </div>
        <div class="Dr-form">
            <ul class="dr-list">
                <li>手机号</li>
                <li>
                    <input type="tel" name="mobile" class="inp" placeholder="用于接收保单信息" maxlength="11">
                </li>
            </ul>
            <ul class="dr-list">
                <li>验证码</li>
                <li>
                    <input type="tel" name="smscode"  placeholder="请输入验证码" style="width:128px;position: absolute;left: 0px;height: 2rem;">
                    <span class="send-code">获取验证码</span>
                </li>
                <li class="select_box"><input type="radio" name="agent"><span>是</span><input type="radio" name="agent" checked="checked"><span>否</span>为代理人</li>
            </ul>
        </div>
        <div class="btn-wrap">
            <button>确认</button>
        </div>
    </div>
</div>
</body>
</html>