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
    <title>绑定账号</title>
     <script type="text/javascript">
    function getUrl() {
		return "<%=path%>";
  	}
    </script>
    <link rel="stylesheet" href="<%=path%>/views/css/optiscroll.css">
    <link rel="stylesheet" href="<%=path%>/views/css/verifyCardno.css">
</head>
<body>
<div class="content">

    <form class="main">
        <div class="bind-user-box">
            <table>
                <tr>
                    <td>身&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证</td>
                    <td colspan="2"><input type="text"placeholder="请输入证件号" name="payeeid" id="payeeid" maxlength="18"></td>
                </tr>
                <tr>
                    <td colspan="3" id="bc-box"><p>请选择银行卡&gt;&gt;</p>
                    <img src="" id="img-bc" class="img-bc">
                    <input type="hidden" value="" id="bc-ID">
                </td>
                </tr>
                <tr>
                    <td>银&nbsp;&nbsp;行&nbsp;&nbsp;卡&nbsp;&nbsp;号</td>
                    <td colspan="2"><input type="text"placeholder="请输入卡号" name="banknumber" id="banknumber" maxlength="19" onblur="Trim(this);"></td>
                </tr>
                <tr>
                    <td>户 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</td>
                    <td colspan="2"><input type="text" placeholder="请输入户名" name="payee" id="payee" maxlength="4" onblur="Trim(this);"></td>
                </tr>
                <tr>
                    <td>手&nbsp;&nbsp;机&nbsp;&nbsp;号&nbsp;&nbsp;码</pre></td>
                    <td colspan="2"><input type="text"placeholder="请输入银行预留手机号" name="userphoneno" id="userphoneno" maxlength="11" onblur="Trim(this);"></td>
                </tr>
                <tr>
                    <td>提&nbsp;&nbsp;现&nbsp;&nbsp;密&nbsp;&nbsp;码</td>
                    <td colspan="2"><input type="text"placeholder="请输入密码" name="cashDealpass" id="cashDealpass" onblur="Trim(this);"></td>
                </tr>
                <tr >
                    <td>确&nbsp;&nbsp;认&nbsp;&nbsp;密&nbsp;&nbsp;码</td>
                    <td colspan="2"><input type="text"placeholder="请再次输入密码" name="checkpassword" id="checkpassword" onblur="Trim(this);"></td>
                </tr>
            </table>

        </div>
        <div class="btn-wrap">
            <a href="javascript:submitForm()"><span>用户绑定</span></a>
        </div>
    </form>
    <div class="shade-layer" id="shade-layer">
		    <div class="con4_tang5 " id="vehicleModel">
					<p class="con4_tan_p1" style="font-style:16px;">请选择银行 <b style="display:inline-block;border-bottom:1px solid #ddd;position:absolute;bottom:0px;width:100%;left:0px;"></b></p>
					<div class="m-wrapper is-enabled has-vtrack" id="m-wrapper"><div class="optiscroll-content" >
						<ul class="tang5_ul">
							<li data-t="0102" class="single-bc" title="中国工商银行">
								<span class="Select"></span><img src="/NowHealthMobile/views/images/icbc.png">
							</li>
						    <li data-t="0103" class="single-bc" title="中国农业银行">
						        <img src="/NowHealthMobile/views/images/abc.png">
						        <span class="Select"></span>
						    </li>
					    </ul>
					    <ul>
						    <li data-t="0104" class="single-bc" title="中国银行 ">
						    <span class="Select"></span><img src="/NowHealthMobile/views/images/bofc.png">
						    </li>
						    <li data-t="0105" class="single-bc" title="中国建设银行">
						    <img src="/NowHealthMobile/views/images/ccb.png">
						    <span class="Select"></span>
						    </li>
						</ul>
						<ul>
						    <li data-t="0403" class="single-bc" title="中国邮政存储银行">
						    <span class="Select"></span><img src="/NowHealthMobile/views/images/pspc.png">
						    </li>
						    <li data-t="0301" class="single-bc" title="交通银行 ">
						    <img src="/NowHealthMobile/views/images/boc.png">
						    <span class="Select"></span>
						    </li>
						</ul>
						<ul>
						    <li data-t="0302" class="single-bc" title="中信银行 ">
						   <span class="Select"></span> <img src="/NowHealthMobile/views/images/zxyh.png">
						    </li>
						    <li data-t="0303" class="single-bc" title="中国光大银行">
						    <img src="/NowHealthMobile/views/images/cebb.png">
						    <span class="Select"></span>
						    </li>
						</ul>
						<ul>
						    <li data-t="0305" class="single-bc" title="中国民生银行">
						    <span class="Select"></span><img src="/NowHealthMobile/views/images/ms-bc.png">
						    </li>
						    <li data-t="0308" class="single-bc" title="招商银行 ">
						    <img src="/NowHealthMobile/views/images/zs-bc.png"><span class="Select"></span>
						    </li>
						 </ul>
						 <ul>
						     <li data-t="04105840" class="single-bc" title="平安银行 ">
						      <span class="Select"></span><img src="/NowHealthMobile/views/images/pab_pc.png">
						    </li>
						     <li data-t="0309" class="single-bc" title="兴业银行 ">
						    <img src="/NowHealthMobile/views/images/xy-bc.png">
						    <span class="Select"></span>
						    </li>
						  </ul>
						  <ul style=" margin-bottom: 42px;">
						     <li data-t="0304" class="single-bc" title="华夏银行 ">
						    <span class="Select"></span><img src="/NowHealthMobile/views/images/hx-bc.png" style="width:32%;">
						    </li>
						  </ul>
					</div><div class="optiscroll-v"><b class="optiscroll-vtrack" style="height: 40.3756%; transform: translate(0%, 147.674%);"></b></div><div class="optiscroll-h"><b class="optiscroll-htrack"></b></div></div>
					<div class="con3_p7"><a href="javascript:void(0);" class="que">确 定</a><p></p>
		        </div>
		     </div>
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
<script src="<%=path%>/views/js/jquery.1.7.2.min.js"></script>
<script src="<%=path %>/views/js/optiscroll.js"></script>
<script src="<%=path%>/views/js/verifyCadeno.js"></script>
<script type="text/javascript">
   	//关闭错误消息提示框
	$("#ensure").click(function(){
		$("#errorhei").hide();
	});
	var wr = new Optiscroll(document.getElementById('m-wrapper'), { forceScrollbars: true });
	$(".single-bc").click(function(){
        $("#bc-box").children("p").css("display","none");
         var This_chil=$(this).children("on"),
         span=$(this).children("span"),
         src=$(this).children("img").attr("src"),
         val=$(this).attr("data-t");
         $("#bc-ID").val(val);
         if(This_chil.length==0){
         $("#img-bc").attr("src",src);
         $("#img-bc").show();
          $(".ON").removeClass("ON");
          span.addClass("ON");
         }
    })
    $("#bc-box").click(function(){
         $("#shade-layer").show();
    })
    $(".que").click(function(){
      $("#shade-layer").hide();
    })
</script>
</html>