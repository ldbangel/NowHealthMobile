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
    <script src="<%=path%>/views/js/jquery.1.7.2.min.js"></script>
    <link rel="stylesheet" href="<%=path%>/views/css/setPassword.css">
</head>
<style>

</style>
<body>
<div class="content">
    <p>请设置密码，用于提现验证</p>
    <form id="form" action="<%=path%>/VerificaName/setPassword.do" method="post">
	    <div class="psw-box">
	        <ul>
	            <li class="Li"><span class="icon">.</span></li>
	            <li class="Li"><span class="icon">.</span></li>
	            <li class="Li"><span class="icon">.</span></li>
	            <li class="Li"><span class="icon">.</span></li>
	            <li class="Li"><span class="icon">.</span></li>
	            <li class="Li"><span class="icon">.</span></li>
	        </ul>
	        <input minlength="6" maxlength="6" tabindex="1" id="PSW" name="payPassword_rsainput" class="ui-input i-text" oncontextmenu="return false" onpaste="return false" oncopy="return false" oncut="return false" autocomplete="off" value="" type="number"  autofocus="autofocus"/>
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
</body>
<script>
$(document).ready(function(){
var icon=$(".icon");
 $(".num").click(function(event){
 event.preventDefault();
   var num=$(this).text(),
   psw=$("#PSW"),
   val= psw.val(),
   leg=val.length;
   if(leg>=6){
        $('#form').submit();
    }else{ 
        if(leg>=5){
            psw.val(val+num);
	        icon.eq(leg).addClass("opy");
	        $('#form').submit();
             }else{
		        psw.val(val+num);
		        icon.eq(leg).addClass("opy");
           }
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
</html>