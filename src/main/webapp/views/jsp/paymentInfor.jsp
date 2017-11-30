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
    <meta name="keywords" content="晶算师保险,特价保险,互联网保险,少儿保险,儿童保险,意外伤害保险,个人重大疾病保险,健康保险,意外保险,交通意外险">
    <meta name="description" content="晶算师是国内创新型互联网保险服务平台，是特惠保险的先行者，晶算师聚焦于意外险和健康疾病险等纯保障型产品，将保险回归保障本源，并以一种简单而健康的方式呈现给消费者。">
    <title>支付</title>
    <script src="<%=path%>/views/js/jquery-1.9.1.js"></script>
    <link rel="stylesheet" href="<%=path%>/views/css/paymentInfor.css">
    <link rel="stylesheet" href="<%=path%>/views/css/css.css">
</head>
<body>

    <div class="div640">
        <div class="div600 index7">
            <%-- <div class="ul_bei">
                <img src="<%=path%>/views/images/zhifu.png">
                <ul class="ul_a">

                    <li><a href="javascript:void(0);">基本信息</a></li>
                    <li><a href="javascript:void(0);">报价信息</a></li>
                    <li><a href="javascript:void(0);">确认信息</a></li>
                    <li class="on"><a href="javascript:void(0);">支付</a></li>

                </ul>
                <div class="clear"></div>
            </div> --%>
            
            <div class="content9 boder1">
                <form action="<%=path%>/paymentInfor/paymentApplication.do" method="post" name="form"  id="form" enctype="application/x-www-form-urlencoded">
                    <p class="con9_p1"><font>您的订单已成功提交！</font></p><p style="font-size: 15px;padding: 0 2%;">为保证您的订单能按时起效，请尽快完成支付。</p>
                    <input style="display:none" id="orderNo" name="orderNo" value="${baseinfor.orderno}" readonly="readonly"/>
                    <table class="con10_table">
                        <tr class="tr1"><td class="td1">内容</td><td class="td2">生效日期</td><td class="td3" style="color: #333333;">金额</td></tr>
                        <c:if test="${!empty baseinfor.totalamount}"><tr><td class="td1">Dr.康</td><td class="td2" style="font-size: 13px;">${baseinfor.effectivedate}</td><td class="td3">&yen;${baseinfor.totalamount}</td></tr></c:if>
                    </table>
                    <table class="con9_table con9_table2">
                        <tr><td>合计：&yen;${baseinfor.totalamount}</td></tr>
                    </table>
                    <div class="con9_div" style="display:none">
                        <p class="p1">输入推荐优惠劵码</p>
                        <input class="text3" type="text" disabled="disabled"/>
                        <input class="button1" type="button" value="提交" disabled="disabled"/>
                        <div class="clear"></div>
                    </div>
                    <p class="con3_p7 "><a href="javascript:submitFrom()">立即支付</a></p>
                    <!--  <p class="con3_p7-daifu "><a href="javascript:daifu()">代付</a></p> -->
                </form>
               
            </div>
        </div>
        <div class="error_Box" style="display:none">
            <div class="middle1">
                <p  style="color:red;font-size: 1rem;color: red;text-align: center;margin-bottom: 5%;margin-top:5px;">温馨提示</p >
                <div class="m-wrapper " id="m-wrapper">

                    <div class="message" id="Message" > </div>
                </div>
                <button  class='close_btn btn' >关闭</button>

            </div>

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
</div>
</body>
<script>
    window.onload=function(){
        heigh();
        width_h();
        img_width();
    }

    $(document).ready(function(e) {
        $(window).resize(function(e) {
            heigh();
            img_width();
        });
    });


    /*gundong*/
    function width_h(){

        $('.con4_tang6_ul2').width(ulWidth);
        $('.con4_tang6_ul2 li').width(ulWidth);
    };




    function heigh(){
        var hh=$('.ul_bei').height();
        $('.ul_a li').css('line-height',hh+'px');
        $('.ul_a,.ul_a li').css('height',hh+'px');

    }


    function width_h(){
        $('.texta').focus(function(){

            $(this).next('.tang8_p1').hide();
        });
        $('.texta').blur(function(){
            if($(this).val().length==0)
            {
                $(this).next('.tang8_p1').show();
            }

        });

    }

    function img_width(){
        var hr=$(window).height();
        var hrr=$('.bei').height();
        if(hr>hrr)
        {
            $('.bei').height(hr);
        }

    };
</script>
<script type="text/javascript">
function getUrl() {
		return "<%=path%>";
  	}
function submitFrom() {	
	$("#prompt").html("正在支付,请稍后...");
	load(20000);
	$("#form").submit();

}
//延迟时间
function load(outtimes){
	$("#pop").show();
	setTimeout(function(){$('#pop').hide()
	},outtimes);
}
</script>
</html>