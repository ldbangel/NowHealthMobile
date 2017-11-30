//初始化获取参数并赋值到页面,自动调用后台获取短信验证码
$(document).ready(function(){
	var userphoneFirst=userphoneno.substring(0,3);
	var userphoneEnd=userphoneno.substring(7);
	var showphone=userphoneFirst+"****"+userphoneEnd;
	/*alert(showphone);*/
	$("#bankname").html(showphone);
	if(userphoneno!==""){
		var num=60;
	    var Span=document.getElementsByClassName('smscode_list ')[0].getElementsByTagName('p')[0];
	    Span.setAttribute('class','Color ');
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

		$.ajax({
			url : getUrl()+"/personController/phoneCheck.do",
			async: false ,
			data: {phoneNo:userphoneno},
			success : function(generation){
			}
		});
	}
});
/**
 * 点击重新获取验证码
 */
function getCheckCode(){
	if(userphoneno!==""){
		var num=60;
	    var Span=document.getElementsByClassName('smscode_list ')[0].getElementsByTagName('p')[0];
	    Span.setAttribute('class','Color ');
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
		$.ajax({
			url : getUrl()+"/personController/phoneCheck.do",
			async: false ,
			data: {phoneNo:userphoneno},
			success : function(generation){
			}
		});
	}
}

/**
 * 通过后台校验验证码录入是否正确
 */
function submitForm(){	
	var validationNo=$('#validationNo').val();
	if(checkCode(validationNo)){
		$.ajax({
			url : getUrl()+"/personController/checkPhoneCode.do",
			async: false ,
			data: {checkCode:validationNo,phoneNo:userphoneno},
			success : function(result){
				/*alert(result);*/
				if(result=="true"){
					//如果验证成功,跳转到设置提现密码页面
					window.location.href = getUrl()+"/views/jsp/setPassword.jsp";
				}else{
					$('#Message').html("验证码输入有误！");
			    	$('.errorhei').show();
				}
			},
			error: function(res) {
				$('#Message').html("验证码校验失败！");
		    	$('.errorhei').show();
	        }		
		});
	}	
}

/**
 * 手机号码校验
 */
function checkPhone(value) {
	if(value==null||"undefined"==value||""==value){
		$("#Message").html("手机号码不能为空");
		$("#errorhei").show();
   		return false;
	}else if (!(/^1[34578]\d{9}$/.test(value))) {
		$('#Message').html("手机号输入有误");
		$("#errorhei").show();		
		return false;
	} else {
		return true;
	}
}

/**
 * 验证码校验
 */
function checkCode(codee){
	var reg=/^\d{6}$/;
	if(codee==null||"undefined"==typeof codee||""==codee){
		$('#Message').html("请输入验证码！");
    	$('.errorhei').show();
    	return false;		
	}else{
		if(!reg.test(codee)){
			checkcodeflag=false;
			$('#Message').html("验证码格式有误！");
	    	$('.errorhei').show();
	    	return false;
		}else{
			return true;
		}
	}
}

//去除字符串中的空格
function Trim(obj){
	var str = obj.value;
    var a = str.replace(/<\/?[^>]*>/gim,"");//去掉所有的html标记
    var b = a.replace(/(^\s+)|(\s+$)/g,"");//去掉前后空格
    var c = b.replace(/\s/g,"");//去除文章中间空格
    obj.value=c;
}




