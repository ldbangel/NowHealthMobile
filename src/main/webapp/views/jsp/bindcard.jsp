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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="x-rim-auto-match" content="none">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <title>绑定银行卡</title>
</head>
<style>
    html,body{width:100%;min-height:100%;font:1rem Microsoft YaHei,SimHei,SimSun,Arial,Verdana;}body{-webkit-touch-callout:none;-webkit-text-size-adjust:100%;-webkit-tap-highlight-color:rgba(255,255,255,0)}a{text-decoration:none;outline:0}ol,ul{list-style:none}
    *{margin: 0;
        padding: 0;
        border: 0;
    }
    input{outline: none;background:#fff;line-height:30px;font-size:0.9rem;padding-left:15px;}
    .content{max-width:640px;width:100%;height:auto;margin:0 auto;}
    .header{position:relative;height:34px;line-height:34px;text-align:center;font-size:1rem;background-color:#f63c30;color:#fff;position:relative}
    .header i{float: left;
        margin-left: 10px;
        margin-top: 12px;
        left:0px;top:0px;border-top:2px solid #fff;border-left:2px solid #fff;width:8px;height:8px;    -webkit-transform: rotate(-45deg);
        -moz-transform: rotate(-45deg);
        -ms-transform: rotate(-45deg);
        -o-transform: rotate(-45deg);
        transform: rotate(-45deg);
    }
    .Form ul{width: 96%;
        margin: 0 auto;
    }
    .Form li{line-height:40px;font-size:0.9rem;padding-left:15px;border-bottom:1px solid #ddd;}
    .Form span{display: inline-block;position: relative;width: 21%;}
    .Form input{width:71%}
    .Form i{
        position: absolute;
        width: 1px;
        height: 15px;
        border-right: 1px solid #eee;
        top: 12px;
        right: 0;
    }
    .btn-box span{
        display: inline-block;
        width: 73%;
        line-height: 40px;
        color: #fff;
        background: #f74c50;
        font-size: 14px;
        border-radius: 8px;
    }
</style>
<body>
<div class="content">
    <div class="header">银行卡绑定</div>
    <form action="" class="Form">
        <ul>
            <li><span>银行卡号 <i></i></span><input type="text" placeholder="请输入银行卡号"></li>
            <li><span>身份证号 <i></i></span><input type="text" placeholder="请输入身份证号"></li>
            <li><span>&nbsp;手 机 号 <i></i></span><input type="text" placeholder="请输入手机号"></li>
        </ul>
        <div class="btn-box" style="text-align:center;margin-top: 25px;">
            <span>提交</span>
        </div>
    </form>
</div>
</body>
</html>