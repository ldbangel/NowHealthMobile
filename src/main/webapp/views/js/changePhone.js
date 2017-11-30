$(document).ready(function() {
	$("#phoneno").focus();
	$("#pop").hide();
});

//取绝对路径地址
function getUrl(){
	 var protocol = window.location.protocol;
	 var host = window.location.host;
	 var pathname = window.location.pathname.split('/');
	 var url = protocol+"//"+host+"/"+pathname[1];
	 return url;
}

var checkphoneNoFlag=false;
var checkpswFlag=false;
var checkconfirmFlag=false;
var checkcodeflag=false;
var userExist=false;
var validatecodeFlag=false;
var _interval;


//更改手机号，获取验证码
function getCheckCode(){
	var phoneno=$("#phoneno").val();
	checkphone();
	if(checkphoneNoFlag){
		$.ajax({
			url : getUrl()+"/personController/phoneCheck.do",
			async: false ,
			data: {phoneNo:phoneno},
			success : function(generation){
		   		if(checkphoneNoFlag){
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
	    				        Span.innerHTML='获取';
	    				        clear();
	    				    }
	    				},1000);
	    				function clear(){
	    				   clearInterval(_interval);
	    				}
	    		}
			}
		});
	}
}

//更改手机号码
function changePhoneNo(){
   var username=$("#phoneno").val();
   var phoneCheckCode=$("#phoneCheckCode").val();//获取手机验证码
   checkphone();            //校验手机号
   if(checkphoneNoFlag){
	   checkCode();         //校验验证码
	   if(checkcodeflag){
			$.ajax({
				   url : getUrl()+"/personController/checkPhoneCode.do", //校验手机验证码
		           async: false ,
				   data: {checkCode:phoneCheckCode,phoneNo:username},
				   success : function(result){
					   if(result=="true"){   //手机验证码校验成功
						   var openId=$("#openId").val();//获取openId oOLN5w9uiHOpJ7CRzDZj37gwuutw
						   $.ajax({
				    	        url : getUrl()+"/WechatLogin/changePhone.do",
				    	        async: false ,
				    	        data: {openId:openId,phoneNo:username},
				    	        success : function(result){
				    	    		if("更改用户成功"===result){
				    	    			/*$("#Message").html("用户绑定成功.");
				    	        		$(".errorhei1").show();*/
				    	    			window.location.href = getUrl()+"/views/jsp/nowhealthHome.jsp";
				    	    		}else{
				    	    			$("#Message").html(result);
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
			   });
	  }
}		
}
//点击是留在本页面，修改手机号码
function updatePhone(){
	$('.errorhei2').hide();
	$("#phoneno").val('');
	$("#phoneno").focus();
}
//点击否跳转到首页
function gotoHome(){
	window.location.href = getUrl()+"/views/jsp/nowhealthHome.jsp";
}
//手机号码校验
function checkphone() {
	var phone=$('#phoneno').val();
	if(phone==null||"undefined"==typeof phone||""==phone){
		checkphoneNoFlag=false;
		$('#Message').html("请输入手机号！");
    	$('.errorhei1').show();
    	//Hide();
		$('#phoneno').focus();
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

function remove_red(){
    $('Error').removeClass('red');
}

$('.errortan3').click(function(){
    $('.errorhei1').hide();
});

function load(outtimes){
	$("#pop").show();
	setTimeout(function(){$('#pop').hide()
	},outtimes);
}
