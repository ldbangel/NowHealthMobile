<%@ page language="java" import="java.util.*,com.nowhealth.mobile.utils.*,com.nowhealth.mobile.entity.*" pageEncoding="UTF-8"%>
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
    <title>设置密码</title>
    <script type="text/javascript">
	  	function getUrl() {
		return "<%=path%>";
		}
  	</script>
    <script src="<%=path%>/views/js/jquery.1.7.2.min.js"></script>
    <link rel="stylesheet" href="<%=path%>/views/css/setPassword.css">
</head>
<style>
</style>
<body>
<div class="content">
    <p>请再次收入密码</p>
    <form action="">
    <div class="psw-box">
        <ul>
            <li class="Li"><span class="icon">.</span></li>
            <li class="Li"><span class="icon">.</span></li>
            <li class="Li"><span class="icon">.</span></li>
            <li class="Li"><span class="icon">.</span></li>
            <li class="Li"><span class="icon">.</span></li>
            <li class="Li"><span class="icon">.</span></li>
        </ul>
        <input type="text" style="display:none" value="0" id="show"/>
        <input type="text" id="password" style="display:none" value="${password}"/>
        <input minlength="6" maxlength="6" tabindex="1" id="PSW" name="payPassword_rsainput" class="ui-input i-text" oncontextmenu="return false" onpaste="return false" oncopy="return false" oncut="return false" autocomplete="off" value="" type="number">
    </div>
    </form>
     <div class='keybord-box'>
        <div id='Off'><span></span></div>
        <ul>
		     <li><span class="num">1</span><span class="num">2</span><span class="num">3</span></li>
		    <li><span class="num">4</span><span class="num">5</span><span class="num">6</span></li>
		    <li><span class="num">7</span><span class="num">8</span><span class="num">9</span></li>
		    <li><span class="NULL">&nbsp;</span><span class="num">0</span><span id="delete"><b>&nbsp;</b></span></li>
         </ul>
    </div>
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
<script>
//初始化获取焦点
$(document).ready(function(){
var icon=$(".icon");

 $(".num").click(function(event){
 event.preventDefault();
   var num=$(this).text(),
   psw=$("#PSW"),
   val= psw.val(),
   leg=val.length; 
        if(leg>=5){
             var password=$('#password').val();
             psw.val(val+num);
	         icon.eq(leg).addClass("opy");
				        if((val+num)==password){
								$.ajax({
									url : getUrl()+"/VerificaName/addUserPass.do",
									async: false ,
									data: {passwordConfim:password},
									success : function(result){
										if(result=="success"){
											$('#show').val("1");
											$('#Message').html("银行卡绑定成功！！");
									    	$('.errorhei').show();
										}else{
											$('#Message').html("密码更新进入数据失败！");
									    	$('.errorhei').show();
										}
									}
								});
						 }else{
								$('#Message').html("两次录入的密码不一致,请重新输入！！");
				    			$('.errorhei').show();
						      }
             }else{
		        psw.val(val+num);
		        icon.eq(leg).addClass("opy");
           }
       
 })
 
 
 $("#delete").click(function(){
  var val= $("#PSW").val(),
  leg=parseInt((val.length)-1),
  Val=val.substring(0,leg),
  span=$(".opy").length,
  sleg=parseInt(span-1);
   $("#PSW").val(Val);
   $(".opy").eq(sleg).removeClass("opy");
 })
})
$("#Off").click(function(){
$(".keybord-box").addClass("anima");
})
$(".psw-box").click(function(){
$(".keybord-box").removeClass("anima");
})

</script>
<script type="text/javascript">
   	//关闭错误消息提示框
	$("#ensure").click(function(){
		$("#errorhei").hide();
		if($("#show").val()==1){
		   window.location.href = getUrl()+"/views/jsp/draw-cash.jsp";
   		}
   		/* var icon=$(".icon");
		$(".active").removeClass("active");
		for(var i=0;i<=5;i++){
		    icon[i].removeClass("opy");
		} */
	});
</script>
</html>