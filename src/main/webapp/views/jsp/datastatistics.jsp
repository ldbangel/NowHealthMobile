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
    <title>我的收入</title>
    <link rel="stylesheet" href="<%=path%>/views/css/datastatistics.css">
    <link rel="stylesheet" href="<%=path%>/views/css/mobiscroll_date.css">
    <script src="<%=path%>/views/js/jquery-1.9.1.js"></script>
</head>
<body>
<div class="content">
    <div class="header">我的收入</div>
    <div class="top">
        <h2>0.00</h2>
        <p>累计收入(元)</p>
        <ul class="bill_box">
            <li><span>0.00</span><p>今日收入(元)</p></li>
            <li><span>0.00</span><p>即将生效(元)</p></li>
        </ul>
    </div>
    <div class="date_box">
        <input type="text" id="date" readonly="readonly" value="2017-07">
    </div>
    <div class="tab_list_box">
        <table class="tab-list">
            <tr>
                <th>奖励来源</th>
                <th>数量</th>
                <th>收入</th>
                <th>保费</th>
            </tr>
            <tr>
                <td class="fir_td">推广产品奖励</td>
                <td>0单</td>
                <td>0.00</td>
                <td>0.00</td>
            </tr>
            <tr>
                <td class="fir_td">代购产品奖励</td>
                <td>0单</td>
                <td>0.00</td>
                <td>0.00</td>
            </tr>
            <tr>
                <td class="fir_td">合伙人奖励</td>
                <td>0人</td>
                <td>0.00</td>
                <td>-</td>
            </tr>
            <tr>
                <td>总计</td>
                <td></td>
                <td>0.00</td>
                <td></td>
            </tr>
        </table>
    </div>
</div>
</body>
<script src="<%=path%>/views/js/mobiscroll_date.js"></script>
<script src="<%=path%>/views/js/mobiscroll.js"></script>
<script>
    $(function () {
        var currYear = (new Date()).getFullYear();
        var opt={};
        opt.date = {preset : 'date'};
        opt.datetime = {preset : 'datetime'};
        opt.time = {preset : 'time'};
        opt.default = {
            theme: 'android-ics light',
            display: 'modal',
            mode: 'scroller',
            dateFormat: 'yyyy-mm',
            lang: 'zh',
            showNow: true,
            nowText: "今天",
            startYear: currYear - 50,
            endYear: currYear + 50
        };
        var t=new Date();
        //var iToDay=t.getDate();
        var iToMon=t.getMonth();
        var iToYear=t.getFullYear();
        //var newDay = new Date(iToYear,iToMon,(iToDay+1));
        var newDay = new Date(iToYear,iToMon);
        var min = { preset : 'date', minDate: new Date(newDay) };
        $("#date").mobiscroll($.extend(min, opt['default']));

    });
    </script>
</html>