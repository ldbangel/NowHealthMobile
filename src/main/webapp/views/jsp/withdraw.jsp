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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="x-rim-auto-match" content="none">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="keywords" content="晶算师保险,特价保险,互联网保险,少儿保险,儿童保险,意外伤害保险,个人重大疾病保险,健康保险,意外保险,交通意外险">
    <meta name="description" content="晶算师是国内创新型互联网保险服务平台，是特惠保险的先行者，晶算师聚焦于意外险和健康疾病险等纯保障型产品，将保险回归保障本源，并以一种简单而健康的方式呈现给消费者。">
    <title>申请提现</title>
    <link rel="stylesheet" href="<%=path%>/views/css/withdraw.css">
</head>
<body>
<div class="content">
    <div class="header">申请提现</div>
    <div class="main">
        <div class="top_content">
            <ul class="draw_box">
                <li><strong>&yen;</strong><span>0.00</span><p>当前余额</p></li>
                <li><strong>&yen;</strong><span>0.00</span><p>可提现额</p></li>
            </ul>
            <p class="textcenter">* 当前余额中只有生效保单才可提现，</br>1号提现，20号到账</p>
            <p class="top-tip-text">* 请确保提现信息填写正确，一旦提交后续提现将以此信息为准，不可更改。</p>
        </div>
    </div>
    <div class="top_line"></div>
    <ul class="form-content">
            <li><span class="line-hg">真实姓名</span><input type="text" placeholder="请输入您的真实姓名"></li>
            <li><span class="line-hg">手 机 号</span><input type="text" placeholder="请输入您的手机号"></li>
            <li><span class="line-hg">身份证号</span><input type="text" placeholder="请输入您的身份证号"></li>
            <li><span class="line-hg">开 户 行</span>
                <select name="bank" >
                    <option value="中国工商银行">中国工商银行</option>
                    <option value="中国农业银行">中国农业银行</option>
                    <option value="中国银行">中国银行</option>
                    <option value="中国建设银行">中国建设银行</option>
                    <option value="交通银行">交通银行</option>
                    <option value="中信银行">中信银行</option>
                    <option value="中国光大银行">中国光大银行</option>
                    <option value="华夏银行">华夏银行</option>
                    <option value="中国民生银行">中国民生银行</option>
                    <option value="广发银行">广发银行</option>
                    <option value="平安银行">平安银行</option>
                    <option value="招商银行">招商银行</option>
                    <option value="上海浦东发展银行">上海浦东发展银行</option>
                </select>
            </li>
            <li><span class="title-special">开户行所在地</span></li>
            <li><span class="line-hg">分　　行</span><input type="text" placeholder="如果不清楚请打银行客服电话咨询"></li>
            <li><span class="line-hg">支　　行</span><input type="text" placeholder="如果不清楚请打银行客服电话咨询"></li>
            <li><span class="line-hg">银行卡号</span><input type="text" placeholder="请输入您的银行卡号"></li>
    </ul>
    <div class="instruction"><i></i>信息填错导致的提现损失跟晶算师无关</div>
    <button class="apply_btn">申请提现</button>
</div>
</body>
</html>