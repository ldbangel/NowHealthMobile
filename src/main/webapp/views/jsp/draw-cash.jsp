<%@ page language="java" import="java.util.*,com.nowhealth.mobile.utils.*,com.nowhealth.mobile.entity.*,java.text.DecimalFormat" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserInfor userinfor=null;
if(session.getAttribute("loginUser")!=null){
  userinfor=(UserInfor)session.getAttribute("loginUser");
}
DecimalFormat df = new DecimalFormat("0.00");
String agreeaMount = userinfor!=null?df.format(Double.parseDouble(userinfor.getDrawalamount())):"0.00";
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
    <title>提现申请</title>
     <link rel="stylesheet" href="<%=path%>/views/css/draw-cash.css">
    <script type="text/javascript">
        var agreeAmount = <%=agreeaMount%>
        if(agreeAmount=="null"){
    	agreeAmount="0.00";
    }
    </script>
</head>
<body>
<div class="content">

    <form class="main">
        <div class="bind-user-box">
            <table>
                <tr>
                    <td>账&nbsp;&nbsp;户&nbsp;&nbsp;名:</td>
                    <td colspan="2"><input id="bankName" style="background-color: lavenderblush" readonly="readonly" type="text"placeholder="请输入账户名" maxlength="4"></td>
                </tr>
                <tr>
                    <td>银行卡号:</td>
                    <td colspan="2"><input id="bankCard" readonly="readonly" type="text"placeholder="请输入银行卡号" maxlength="19"></td>
                </tr>
                <tr>
                    <td>手机号码:</td>
                    <td colspan="2"><input id="phoneNo" type="text"placeholder="请输入银行预留手机号" maxlength="11"></td>
                </tr>
                <tr>
                    <td>提现金额:</td>
                    <td colspan="2"><input id="drawal_amount" class="text8"type="text"placeholder="当前可提现￥<%=agreeaMount%>"></td>
                </tr>
                <tr class="tr2">
                    <td>验&nbsp;&nbsp;证&nbsp;&nbsp;码:</td>
                    <td><input type="text" id="phoneCheckCode" placeholder="请输入验证码" maxlength="6"></td>
                    <td class="send-code"
                    style="float: right;float: right;margin-top: 9px;display: inline-block;padding: 2 1px;height: 29px;line-height: 28px;
					border: 1px solid #FF8300;border-radius: 3px;background: rgba(255, 255, 255, 0.28);color: #FF8300;text-align: center;width: 75px;background-color: rgba(0, 0, 0, 0.01);">
                          <span id="send-code" onclick="getCheckCode();">获取验证码</span>
                          
                    </td>
                </tr>
                <tr>
                    <td>交易密码:</td>
                    <td colspan="2"><input id="cashDealpass" type="text"placeholder="请输入交易密码" maxlength="6"></td>
                </tr>
            </table>

        </div>
        <div class="btn-wrap">
            <span id="drawal_money" onclick="">提 现</span>
        </div>
        <input type="text" id="bankprop" value="" style="display:none"/>
        
    </form>
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
   <!--提现成功确认弹框 -->
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
                           height: 24px;color: #fff ; border: none;" onclick="updatePhone();">是</button>&nbsp;&nbsp;
                   <button class="ensure" id="ensure1" style="cursor: pointer;width: 63px; background: #f63c30 ;border-radius: 3px; display: inline-block;
                           height: 24px;color: #fff ;border: none;" onclick="gotoHome();">否</button>
                </div>  
			</div>
    </div>
</div>
</body>
<script src="<%=path%>/views/js/jquery.1.7.2.min.js"></script>
<script src="<%=path%>/views/js/draw-cash.js"></script>
<script type="text/javascript">
   $('.errortan3').click(function(){
    $('.errorhei1').hide();
   });
   $('#ensure').click(function(){
    $('.errorhei1').hide();
    }) ;
   $('#ensure1').click(function(){
    $('.errorhei2').hide();
   })
</script>
</html>