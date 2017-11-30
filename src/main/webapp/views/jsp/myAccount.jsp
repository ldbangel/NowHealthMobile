<%@ page language="java" import="java.util.*,com.nowhealth.mobile.utils.*,com.nowhealth.mobile.entity.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String nowDate=DateFormatUtils.getSystemDateByYm();
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserInfor userinfor=null;
if(session.getAttribute("loginUser")!=null){
  userinfor=(UserInfor)session.getAttribute("loginUser");
}
int userId = userinfor!=null?userinfor.getUserid():0;
int agentFlag = userinfor!=null?userinfor.getAgentflag():0;
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
    <title>我的订单</title>
    <link rel="stylesheet" href="<%=path%>/views/css/myAccout.css">
    <link rel="stylesheet" href="<%=path%>/views/css/optiscroll.css">
    <link rel="stylesheet" href="<%=path%>/views/css/mobiscroll_date.css">
    <script type="text/javascript">
     var searchDate = "<%=nowDate%>";
     var agentFlag = "<%=agentFlag%>";
     var userId = "<%=userId%>";
     if(userId==null){
       userId=0;
    }
    if(agentFlag==null){
       agentFlag=0;
    }
    </script>
</head>
<body>
<div class="content">

    <ul class="top-ID">
        <li> <img class="img" src=""></li>
        <li>
            <span class="Name"></span>
            <p class="phone"></p>
        </li>
    </ul>
    <div class="purse_box" style="display:none">
         <a class="gainings"><span id="income"></span><p>我的收入（元）</p></a><a class="quota"><span id="balance"></span><p>余额（元）</p></a>
    </div>
    <input type="hidden" id = "num" name="num" value=""> <!-- 记录当前页数！ -->
    <div class="product_title_box">
        <div>
            <ul class="title_list">
                <li class="Active">待支付(<span class="waitPay">0</span>)<i></i></li>
                <li>已支付(<span class="alreadyPay">13</span>)<i></i></li>
                <li>已关闭(<span class="closed">33</span>)</li>
            </ul>
        </div>
    </div>
    <div id="Order_information"></div>
    <ul id="pager" class="pager"></ul>
    <div class="shade-layer" id="shade-layer">
        <div class="datastatistics-box" style="display:none;" id="datastatistics-box">
            <div class="header">我的收入<i class="close-icon-1"><img src="<%=path%>/views/images/close.png"></i></div>
            <div class="top">
                <span id="income1"></span>
                <p>累计收入(元)</p>
       
            </div>
            <div class="date_box">
                <input type="text" id="date" readonly="readonly" value="" placeholder=""><!-- 获取系统当前时间 -->
            </div>
            <div class="tab_list_box">
             <div id="osl" class="optiscroll column mid-50">
	                <table class="tab-list" id="datastatis-table">
	                    <tr>
	                        <th>用户姓名</th>
	                        <th>购买时间</th>
	                        <th>佣金金额</th>
	                        <!-- <th>佣金状态</th> -->
	                    </tr>
	                </table>
             </div>
             <div class="Loading-box"><i></i>
		            <p class="loading-more show-p" id="datastatis-load">加载更多>></p>
		            <p class="loading-now "><span class="loading-icon"></span>加载中....</p>
		            <p class="none-have">暂无更多信息</p>
              </div>
            </div>
        </div>
        <div class="balancedetail-box" id="balancedetail-box">
            <div class="header">余额明细<i class="close-icon"><img src="<%=path%>/views/images/close.png"></i></div>
            <div class="top">
                <span id="balance1"></span>
                <p>当前余额(元)</p>
                <div class="tixian">
                    <a href="javascript:cashDrawal()">申请提现</a>
                </div>
            </div>
            <div class="detail-content">
                <h3>余额明细</h3>
                <div class="detail-list">
                    <div id="os1" class="optiscroll column mid-50">
                         <table class="tab-list" id="balance-table">
		                    <tr>
		                        <th>提现时间</th>
		                        <th>提现金额</th>
		                        <th>提现状态</th>
		                        <!-- <th>佣金状态</th> -->
		                    </tr>
                         </table>
                    </div>
                </div>
         </div>
         <div class="Loading-box"><i></i>
             <p class="loading-more show-p" id="balance-load">加载更多>></p>
             <p class="loading-now "><span class="loading-icon"></span>加载中....</p>
             <p class="none-have">暂无更多信息</p>
         </div>
        </div>
     
    </div>
</div>
<!-- 普通弹框 -->
  <div class="errorhei1" style="display:none">
	<div style="position: fixed;top: 35%; width:100%;height:auto;text-align:center;">
			<div style="width:85%;margin:0 auto;background:#fff;color: #333; border-radius:5px;padding: 20px 0px 3px 0px;box-shadow: 0 0 20px 2px #333;">
				<h2 style='font-size: 17px;color: #222;'>提示信息</h2>
				<p style='margin-bottom: 10px;font-size: 12px;padding: 15px 20px;text-align: center; color:#444;'id="Message"></p>
                <div style='border-top:1px solid #ddd;font-size:17px;color:#333;padding: 8px 0px;'><span style='display:block;cursor: pointer;width:100%;font-size:15px' id="ensure">确定</span></div>  
			</div>
    </div>
  </div>
  <!-- 是否绑定银行卡弹框 -->
  <div id="errorhei" class="errorhei3" style="z-index: 100; display:none;position: fixed; width: 100%;height: 100%; background: rgba(0,0,0,0.5);top: 0;">
    <div style="   position: fixed;top: 35%; width:100%;height:auto;text-align:center;">
        <div style="width:85%;margin:0 auto;max-width:300px;background:#fff;color: #333; border-radius:5px;padding: 20px 0px 3px 0px;box-shadow: 0 0 20px 2px #333;">
            <h2 style="font-size: 17px;color: #222;color: #aeaeae;padding-bottom:5px;">提示信息</h2>
            <p style="margin-bottom: 10px;font-size: 12px;padding: 15px 20px;text-align: center; color:#444;" id="Message3"></p >
            <div style="border-top:1px solid #ddd;font-size:17px;color:#333;padding: 8px 0px;">
            <span style="display:block;width:50%;cursor: pointer;flex:1;font-size:15px"><span id="Close" style="padding-left:30Px;padding-right: 30px;">取消</span></span>
            <span style="display:block;width:140%;cursor: pointer;flex:1;font-size:15px;margin-top:-18px;" ><span id="ensure3" style="padding-left:30Px;padding-right: 30px;">确定</span></span>
            </div>
        </div>
    </div>
  </div>
  <!-- 选择是否注册弹框 -->
  <div class="errorhei2" style="display:none">
	<div style="position: fixed;top: 35%; width:100%;height:auto;text-align:center;">
			<div style="width:85%;margin:0 auto;background:#fff;color: #333; border-radius:5px;padding: 20px 0px 3px 0px;box-shadow: 0 0 20px 2px #333;">
				<h2 style='font-size: 17px;color: #222;'>提示信息</h2>
				<p style='margin-bottom: 10px;font-size: 12px;padding: 15px 20px;text-align: center; color:#444;'id="Message1"></p>
				<div style='font-size:17px;color:#333;padding: 12px 0px;text-align:center;'>
				   <button class="ensure" id="ensure2" style="cursor: pointer;width: 63px; background: #f63c30 ;border-radius: 3px; display: inline-block;
                           height: 24px;color: #fff ; border: none;" onclick="isbind();">是</button>&nbsp;&nbsp;
                   <button class="ensure" id="ensure1" style="cursor: pointer;width: 63px; background: #f63c30 ;border-radius: 3px; display: inline-block;
                           height: 24px;color: #fff ;border: none;" onclick="nobind();">否</button>
                </div>  
			</div>
    </div>
</div>
</body>
<script type="text/javascript">
//取绝对路径地址
function getPath(){
	 var protocol = window.location.protocol;
	 var host = window.location.host;
	 var pathname = window.location.pathname.split('/');
	 var url = protocol+"//"+host+"/"+pathname[1];
	 return url;
}
</script>
<script src="<%=path %>/views/js/jquery.1.7.2.min.js"></script>
<script src="<%=path %>/views/js/myAccount.js"></script>
<script src="<%=path %>/views/js/optiscroll.js"></script>
<script src="<%=path%>/views/js/mobiscroll_date.js"></script>
<script src="<%=path%>/views/js/mobiscroll.js"></script>
<script src="<%=path%>/views/js/orderDetails.js"></script>
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
            startYear: currYear - 10,
            endYear: currYear + 50
        };
        var t=new Date();
        var iToDay=t.getDate();
        var now= t.toString("yyyy-MM-dd");
        var iToMon=t.getMonth();
        var iToYear=t.getFullYear();
        var newDay = new Date(iToYear,iToMon,iToDay);
         var max = { preset : 'date', maxDate: new Date(now) };
        $("#date").mobiscroll($.extend(max, opt['default']));

    });
    $(document).ready(function(){
        var t=new Date();
        var iToDay=t.getDate();
        var now= t.toString("yyyy-MM-dd");
        var iToMon=t.getMonth()+1;
        var iToYear=t.getFullYear();
        $("#date").attr("placeholder",iToYear+"-"+iToMon);
        // $("#date").attr("placeholder",iToYear+"-"+iToMon+"-"+iToDay);
         
    })
    </script>
</html>