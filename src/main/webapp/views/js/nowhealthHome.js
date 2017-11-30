$(document).ready(function(){
    $(".introduce_table div").eq(1).show();
    $(".Dr-img").css("padding-top",$('.heard-top').height()+"px");
    $(".title li").click(function(){
       var Index=$(this).index(),This=$(this),Div=$(".introduce_table>div").eq(Index);
       This.addClass("Active");
       This.siblings().removeClass("Active");
       Div.show();
       Div.siblings("div").hide();
    });
    
    /**
     * 二维码分享通过userid登录
     */
    /*if(codeId=="1"){//二维码扫描到首页然后登录		
		$.ajax({
			type: "post",
			url : getUrl()+"/WechatLogin/weixinLogin.do",
			async: false,
			data: {userId:userId,codeId:codeId},
			success : function(result){
				if(result!==null&&result!==""){
					var name=result.username;
					alert(name);
					$('#phoneNo').val(name);
					$('#loginQr').hide();
					$('#qrcode').hide();
				}else{	
					$('#headImg').hide();//隐藏头像
					$('#nickname').hide();//隐藏头像
					$('.mycount').hide();//隐藏我的订单
					$('#isagentshare').val(1);
				}
			}
	   });
	}*/
    
    /**
     * 链接分享通过userid登录
     */
    if(shareId!==0 && shareId!==null && shareId!=="" ){//微信端链接分享获取到userId然后登录
		var nowshareID=1;
		$.ajax({
			type:"POST",
			url:getUrl()+"/WechatLogin/weixinLogin.do",      //通过userId获取用户信息进行登录
			async:true,
			data:{shareId:shareId,nowshareID:nowshareID},
			success:function(result){
				if(result!==null&&result!==""){
					
				}else{	
					$('#headImg').hide();//隐藏头像
					$('#nickname').hide();//隐藏头像
					$('.mycount').hide();//隐藏我的订单
					$('#isagentshare').val(shareFrequency);
				}
			}
	   });
	}
})

//首页我的订单按钮
function myaccount(){
	window.location.href=getUrl()+"/views/jsp/myAccount.jsp";
}

/*//获取微信图像及微信名称
$(document).ready(function(){
	var nickname="";
	var headimg="";
	$.ajax({
		url: getUrl()+"/WechatLogin/getUserinfo.do",
		type:'post',
		success : function(result) {
		   if(result!==""&&result!==null){
			  headimg=result.headimgurl;
			  nickname=result.nickname;
			  $('#nickname').html(nickname);
			  $("#imageId").attr("src",headimg);
		   }
		}
	 })
})*/

/**
 * 提交表单
 */
function submitForm(){
    var totalamount = $('.show-price span').html();//获取到总金额
	$("#totalamount").val(totalamount);
	$("#form").submit();
}

//生成二维码
function createQrcode(){
	//查询被分享的次数，通过userid;
	var sharenumber=parseFloat(shareFrequency)+parseFloat(1);
	var isagentshare = $("#isagentshare").val();
	var strUrl="http://m.quicksure.com/NowHealthMobile/";
	/*var strUrl="http://172.16.55.108:8008/NowHealthMobile/";*/
	window.location.href = getUrl()+"/WechatLogin/parseQRCode.do?userId="+userId+"&strUrl="+strUrl+"&sharenumber="+sharenumber+"&isagentshare="+isagentshare;
}
$("#Tobind").click(function(){
	var This_chil=$(this).children("ul"),This_i=$(this).children("i");
	if(This_chil.hasClass('anima_top')){This_chil.removeClass('anima_top');}else{This_chil.addClass('anima_top');}
})
$(".Dr-img").click(function(){
	$(".share-list").removeClass('anima');
	$('.user-seletbox').removeClass('anima_top');
	$(".share").removeClass('act');
})
$(".share").click(function(){
	var This_chil=$(this).children("ul"),This=$(this);
	if(This_chil.hasClass('anima')){This_chil.removeClass('anima');This.removeClass('act')}else{This_chil.addClass('anima');This.addClass('act')}
	
})
$('.share-link').click(function(){
		$(this).parents('.share').removeClass('anima');
		$(".sharetips").show();$(".share").removeClass('act');
	})
$(".sharetips").click(function(){
	$(this).hide();
})

//绑定用户
/*function bindUser(){
	window.location.href = getUrl()+"/views/jsp/userbind.jsp";
}*/
function bindUser(){
	if(userId!==null && userId!==""){
		$.ajax({
			type:"POST",
			url:getUrl()+"/WechatLogin/isBindUser.do", //通过userId获取用户信息进行判断是否绑定过手机号
			async:false,
			data:{userId:userId},
			success:function(result){
				if(result=="1"){
					$('#Message').html("用户已绑定,请勿重复绑定！");
			    	$('.errorhei1').show();
				}else{				
			    	window.location.href = getUrl()+"/views/jsp/userbind.jsp";
				}
			}
	   });
	}
}
//更改手机号
function changePhone(){
	if(userId!==null && userId!==""){
		$.ajax({
			type:"POST",
			url:getUrl()+"/WechatLogin/isBindUser.do", //通过userId获取用户信息进行判断是否绑定过手机号
			async:false,
			data:{userId:userId},
			success:function(result){
				if(result=="1"){
					window.location.href = getUrl()+"/views/jsp/changePhone.jsp";
				}else{				
					$('#Message').html("用户未绑定,请绑定后在进行更改！");
			    	$('.errorhei1').show();
				}
			}
	   });
	}
}


