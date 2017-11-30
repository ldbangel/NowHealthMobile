var checkphoneNoFlag=false;
var checkcodeflag=false;
var checkAmountflag=false;
var checkRatioflag=false;
var _interval;

//取绝对路径地址
function getPath(){
	 var protocol = window.location.protocol;
	 var host = window.location.host;
	 var pathname = window.location.pathname.split('/');
	 var url = protocol+"//"+host+"/"+pathname[1];
	 return url;
} 
//初始化获取用户信息
$(document).ready(function(){
	$.getJSON(getPath()+"/WechatLogin/getCommssionRatio.do",function(data){
		var commssionRatio =data.comratio;
   	    $("#commRatio").val(commssionRatio);
	});
});

//确定更改
var isChange=0;
$("#updateRatio").click(function(){
	var comRatio= $("#newCommRatio").val();
	var phoneNo = $("#phoneNo").val(); //手机号
	var phoneCheckCode = $("#phoneCheckCode").val(); //验证码
	checkratio()              //佣金比例校验
	if(checkRatioflag){
	  checkphone();           //手机号校验
	  if(checkphoneNoFlag){
		 checkCode();         //验证码校验
		if(checkcodeflag){
		  $.ajax({
			   url : getPath()+"/personController/checkPhoneCode.do", //校验手机验证码
	           async: false ,
			   data: {checkCode:phoneCheckCode,phoneNo:phoneNo},
			   success : function(result){
				   if(result=="true"){   //手机验证码校验成功
					   $.ajax({
			    	        url : getPath()+"/WechatLogin/changeCommssionRatio.do",
			    	        async: false ,
			    	        data: {comRatio:comRatio,phoneNo:phoneNo},
			    	        success : function(result){
			    	    		if("1"===result){
			    	    			isChange=1;
			    	    			$('#Message3').html("佣金比例更改成功,请核实！");
			    					$('.errorhei3').show();
			    	    		}else{
			    	    			$("#Message").html("佣金比例更改存在异常，请稍后再试！");
			    	    			$(".errorhei1").show();
			    	    		}
			    	        }
			    	   		});
				   }else{
					   $("#Message").html("验证码输入有误！");
	            	   $(".errorhei1").show();
	            	   $("#phoneCheckCode").focus();
				   }
			   }
		    }) 
	        }
	     }
	     checkphoneNoFlag=false;
		 $('#phoneno').focus();
     } 
})

$('#ensure3').click(function(){
    $('.errorhei3').hide();
	if(isChange==1){
	   window.location.href = getPath()+"/views/jsp/nowhealthHome.jsp";
	  }
});
$('#Close').click(function(){
	$('.errorhei3').hide();
	  isChange=0;
	  window.location.href = getPath()+"/views/jsp/nowhealthHome.jsp";
});

//点击验证码时验证手机号是否管理员手机号
function getCheckCode(){
	checkratio()
	if(checkRatioflag){
	//手机号规则校验
	var phoneNo = $("#phoneNo").val();
	checkphone();
	if(checkphoneNoFlag){
		$.ajax({
			url : getPath()+"/WechatLogin/isAdministrator.do",
			async: false ,
			data: {phoneNo:phoneNo},
			success : function(result){
				if(result=="true"){
				  	$.ajax({
						url : getPath()+"/personController/phoneCheck.do",
						async: false ,
						data: {phoneNo:phoneNo},
						success : function(generation){
				    				var num=60;
				    				var Span=document.getElementsByClassName('tr2')[0].getElementsByTagName('span')[0];
				    				Span.setAttribute('class','Color ');
				    				console.log(num);
				    					_interval=setInterval( function time(){
				    					Span.innerHTML=' ';
				    				    num--;
				    				    Span.innerHTML=num+'s';
				    				    if(num<=0){
				    				        Span.removeAttribute("class");
				    				        Span.innerHTML='重新获取';
				    				        clear();
				    				    }
				    				},1000);
				    				function clear(){
				    				   clearInterval(_interval);
				    				}
							}
						});
				}else{
					 $("#Message").html("您的权限不够,请勿随意修改！");
	            	 $(".errorhei1").show();
				}
	    }
	});
   }
}
}

//佣金比例校验
function checkratio(){
	var comRatio= $("#newCommRatio").val();
	if(comRatio==null || "undefined"==typeof comRatio || ""==comRatio){
		checkRatioflag = false;
		$('#Message').html("请输入佣金比例！");
    	$('.errorhei1').show();
		$('#newCommRatio').focus();
	}else{
		checkRatioflag = true;
	}
}

//手机号码校验
function checkphone() {
	var phone=$('#phoneNo').val();
	if(phone==null||"undefined"==typeof phone||""==phone){
		checkphoneNoFlag=false;
		$('#Message').html("请输入手机号！");
    	$('.errorhei1').show();
    	//Hide();
		$('#phoneNo').focus();
	}else{
		if (!(/^1[34578]\d{9}$/.test(phone))) {
			checkphoneNoFlag=false;
			$('#Message').html("手机号输入有误！");
	    	$('.errorhei1').show();
	    	//Hide();
			$('#phoneno').focus();
		}else{
			checkphoneNoFlag=true;
		}
	}
}

//验证码校验不能为空
function checkCode() {
	var phoneCheckCode=$("#phoneCheckCode").val();//获取手机验证码
	var reg=/^\d{6}$/;
	if(phoneCheckCode ==null ||"undefined"==typeof phoneCheckCode||""==phoneCheckCode){
		checkcodeflag=false;
		$('#Message').html("请输入验证码！");
    	$('.errorhei1').show();
		$('#phoneCheckCode').focus();
	}else{
		if(!reg.test(phoneCheckCode)){
			checkcodeflag=false;
			$('#Message').html("验证码输入有误！");
	    	$('.errorhei1').show();
	    	//Hide();
	    	$("#phoneCheckCode").focus();
		}else{
			checkcodeflag=true;
	    }
	}
}





