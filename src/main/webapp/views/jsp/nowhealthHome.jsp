<%@ page language="java" import="java.util.*,com.nowhealth.mobile.utils.*,com.nowhealth.mobile.entity.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserInfor userinfor=null;
if(session.getAttribute("loginUser")!=null){
  userinfor=(UserInfor)session.getAttribute("loginUser");
}
String headImg = userinfor!=null?userinfor.getHeadportrait():"";
String nickName = userinfor!=null?userinfor.getNickname():"";
String shareId=request.getParameter("userId")==null?"":request.getParameter("userId"); //微信链接分享获取到userId
int userId = userinfor!=null?userinfor.getUserid():0;
String codeId=request.getParameter("codeId")==null?"":request.getParameter("codeId"); //二维码扫描后跳转到首页
String shareFrequency=request.getParameter("sharenumber")==null?"":request.getParameter("sharenumber"); //获取分享次数
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
    <meta name="keywords" content="dr.康">
    <meta name="description" content="dr.康">
    <title>Dr.康</title>
    <link rel="stylesheet" href="<%=path%>/views/css/nowhealthHome.css">
    <script type="text/javascript">
    var nickName="<%=nickName%>";
    var headImg ="<%=headImg%>";
    var shareId="<%=shareId %>";
    var userId ="<%=userId%>";
    var codeId ="<%=codeId%>";
    var shareFrequency ="<%=shareFrequency%>";
    <%-- var passwordQr ="<%=passwordQr%>"; --%>
    function getUrl() {
		return "<%=path%>";
  	}
    if(nickName=="null"){
    	nickName="";
    }
    if(headImg=="null"){
        headImg="";   
    }
    if(shareId==null||shareId==""){
    	shareId=0;
    }
    if(userId=="null"){
        userId="";
    }
    if(codeId=="null"){
        codeId="";  
    }
    if(shareFrequency==null||shareFrequency==""){
    	shareFrequency=0;
    }
</script>
</head>
<body>
<div class="content">
         <table class="heard-top">
           <tr>
             <td class="logo"><img src="<%=path%>/views/images/DXY-LOGO.jpg"></td>       
             <td id="Tobind">
	             <img style="width:30px ;height:30px;border-radius:50%;vertical-align: middle;" id="headImg" src="<%=headImg %>">
	             <span id="nickname"  vaule=""><%=nickName %></span>
	             <ul class='user-seletbox'>
	               <li><a href="javascript:bindUser()">绑定用户</a></li>
	               <li><a href="javascript:changePhone()">更改用户</a></li>
	             </ul>
             </td>
             <td class="mycount"><span onclick="javascript:myaccount();"><img src="<%=path%>/views/images/myc.png" >我的订单</span></td>
           </tr>
         </table>
        <%-- <div class="imageId" style="position: absolute;width: 200px;z-index: 100;right: 0;">
              <p style="display:inline-block;" id="Tobind"><img id="imageId" width="20" hight="30" src="<%=headImg %>">
              <span id="nickname"  vaule=""><%=nickName %></span></p>
              <span style="position:absolute;right:5px;top: 0;" onclick="javascript:myaccount();">我的订单</span>
        </div> --%>
        <div class="Dr-img">
            <img src="<%=path%>/views/images/1-20170719160722.jpg">
        </div>
        <ul class="footer">
            <li class="show-price">价格: &yen;<span>72.00</span></li>
            <li class="share" style="cursor:pointer;"><img src="<%=path%>/views/images/share_b38a0ee.png" alt="">分享客户 
                 <ul class="share-list">
                 <li class="share-link" style="cursor:pointer;">分享链接</li>
                 <li> <a href="javascript:createQrcode()">分享二维码</a></li>
                 </ul>
            </li>
           <li class="pay-bt" style="width:220px"> 
                <a href="javascript:submitForm()"class='td_qrcode' id='qrcode'>立即购买</a>
           </li>
        </ul>
       
</div>
 <div class="sharetips">
           <div class="shares">点击这里分享给好友</div>
 </div>
<form action="<%=path%>/personController/goTopersonInfo.do" method="POST" name="form"  id="form" enctype="application/x-www-form-urlencoded">
	<input style="display:none" type="text" id="totalamount" name="totalamount" value="${baseInfor.totalamount}"/>  
	<input type="hidden" id="isagentshare" name="isagentshare" value=""/>
</form>
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
</body>
<script src="<%=path%>/views/js/jquery.1.7.2.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="<%=path%>/views/js/nowhealthHome.js"></script>
<script src="<%=path%>/views/js/wx_linkshare.js"></script>
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