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
    <title>更改账号</title>
    <style>
        html,body{width:100%;min-height:100%;font:1rem Microsoft YaHei,SimHei,SimSun,Arial,Verdana;}body{-webkit-touch-callout:none;-webkit-text-size-adjust:100%;-webkit-tap-highlight-color:rgba(255,255,255,0)}a{text-decoration:none;outline:0}ol,ul{list-style:none}
        *{margin: 0;
            padding: 0;
            border: 0;
        }
        input{outline: none;background:#fff;}
        input[type="text"],input[type="radio"]{-webkit-appearance: none;outline:none;}
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
        .main{width:100%;}
        .Dr-img img{width:100%;height:auto;}
        table, table td {
            border-collapse: collapse;
        }
        table {
            border-spacing: 0px;
            width: 100%;
        }

        .send-code span{
			     font-size: 0.8rem;
			    color: #f63c30;
			    width: 100%;
			    text-decoration: none;
			    text-align: center;
			    line-height: 29px;
			    float: right;
			    border: 1px solid #FF8300;
			    border-radius: 5px;
        }
        .btn-wrap{text-align:center;margin-top:20px;}
        .btn-wrap span{font-size: 1rem;display:inline-block;    width: 100%;line-height:2.3rem;text-align:center;height:2.3rem;background:#f74c50;color:#fff;border:none;}

        .select_box span{margin:0 15px 0 5px;}

        table{    width: 94%;
            margin: 15px 3%;
        }
        tr{line-height:45px;}
        td:first-child{min-width:50px;max-width: 51px;}
        td{border-bottom: 1px solid #ddd;}
        td input{padding-left:10px;width:85%;line-height:35px;font-size:1rem;}
        .btn-wrap{position: fixed;max-width: 640px;width: 100%;bottom:0px;}
        .Color{color:#fff;background:orange;pointer-events: none;}
    </style>
<script src="<%=path%>/views/js/jquery-1.9.1.js"></script>
<script src="<%=path%>/views/js/changePhone.js"></script>
</head>
<body>
<div class="content">
   
    <form class="main">
        <!-- <div class="Dr-img">
             <img src="images/js_img_binding_7689b6c.png" >
         </div>-->
        <input style="display:none" type="text" id="openId" name="openId"  value="${openId}"> 
        <div class="Dr-form">
            <table>
                <tr class="tr1">
                    <td>手机号</td>
                    <td colspan="2"><input type="text" id="phoneno" placeholder="请输入手机号码" maxlength="11" onKeyUp="if(value!=value.replace(/[^\d]/g,''))value=value.replace(/[^\d]/g,'')"></td>
                    <div class="regist_phonenum_warn" style="display: none"><span class="registPhonenum_text" style="color: red"></span></div>
                </tr>
                <tr class="tr2">
                    <td>验证码</td>
                    <td><input type="text" id="phoneCheckCode" name="mobile_code" placeholder="请输入验证码" maxlength="6"></td>
                    <td style="text-align:center;min-width:72px;" class="send-code"><span onclick="getCheckCode();">获取验证码</span></td>
           
                </tr>
            </table>
        </div>
        <div class="btn-wrap">
            <span onclick="changePhoneNo();">更改手机号</span>
        </div>
    </form>
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
<div style="position:fixed;top:0px;left:0px;width:100%;height:100%;z-index: 100;display:none;"id="pop">
	<div style="position: fixed;top:35%; width:100%;height:auto;text-align:center;">
	     <span style='display:inline-block;height: auto;border-radius: 5px;background: rgba(0,0,0,0.6);color: #fff;font-size: 10px;letter-spacing: 2px; padding: 20px;'>
	        <img  src="<%=path%>/views/images/mu_loading.gif" style='width: 36px; margin-bottom: 5px;'>
	        <p id="prompt"></p>
		</span>
	</div>
</div>
</body>
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