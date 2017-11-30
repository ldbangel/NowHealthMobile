var checkphoneNoFlag=false;
var checkcodeflag=false;
var checkAmountflag=false;
var checkjyPassword=false;
var _interval;

//取绝对路径地址
function getPath(){
	 var protocol = window.location.protocol;
	 var host = window.location.host;
	 var pathname = window.location.pathname.split('/');
	 var url = protocol+"//"+host+"/"+pathname[1];
	 return url;
} 
//初始化获取用户绑定的账号信息
$(document).ready(function(){
	 $.ajax({
	     type: "POST",
	     url: getPath() + "/myAccount/getUserInfor.do",
	    /* data: JSON.stringify(fcBaseinfor),*/
	     //将对象序列化成JSON字符串  
	     dataType: "json",
	     async: true,
	     contentType: 'application/json;charset=utf-8',
	     success: function(data) {
	    	 var banknumber=data.banknumber;
	    	 var bankName=data.payee;
	    	 var amount=data.drawalamount;
	    	 var bankprop=data.bankprop;
	    	 $('#bankCard').val(banknumber);
	    	 $('#bankName').val(bankName);
	    	 // $('#drawal_amount').val(amount);
	    	 $("#drawalAmount").val(amount);
	    	 $("#bankprop").val(bankprop);
	     }
     })
});
//校验交易密码
function jypsw(){
	var cashDealpass = $("#cashDealpass").val(); //获取输入的交易密码
	var reg=/^\d{6}$/;
	if(cashDealpass ==null || "undefined"==typeof cashDealpass ||""==cashDealpass){
		checkjyPassword=false;
		$('#Message').html("请输入交易密码！");
    	$('.errorhei1').show();
		$('#drawal_amount').focus();
	}else{
		if(!reg.test(cashDealpass)){
			checkjyPassword=false;
			$('#Message').html("交易密码输入有误！");
	    	$('.errorhei1').show();
	    	$("#cashDealpass").focus();
		}else{
			checkjyPassword=true;
		}
	}
}
//确定提现
var isBd=0;
$("#drawal_money").click(function(){
	var phoneNo = $("#phoneNo").val(); //手机号
	var phoneCheckCode = $("#phoneCheckCode").val(); //验证码
	checkphone();           //手机号校验
	if(checkphoneNoFlag){
		checkAmount();      //提现金额校验
	  if(checkAmountflag){
		   checkCode();     //验证码校验
		if(checkcodeflag){
			jypsw();        //交易密码校验
		 if(checkjyPassword){
		   var amount  =$("#drawal_amount").val();
		   var jyPassword =$("#cashDealpass").val();
		   var bankprop=$("#bankprop").val();
		   $("#pop").ajaxStart(function(){
			   $("#prompt").html("提现确认中，请稍等...");
			   load(6000);
		    });
		   $.ajax({
    	        url : getPath()+"/myAccount/drawal_amount.do",
    	        async: true ,
    	        data: {phoneNo:phoneNo,phoneCheckCode:phoneCheckCode,amount:amount*100,jyPassword:jyPassword,bankprop:bankprop},
    	        success : function(result){
    	    		if("1"===result){
    	    			$('#Message3').html("提现成功,请核实！");
    					$('.errorhei3').show();
    					isBd=1;
    	    		}else if("codeErr"===result){
    	    			$("#Message").html("验证码输入有误！");
    	    			$(".errorhei1").show();
    	            }else if("3"===result){
    	    			$("#Message").html("当天提现限1次,请勿重复提现！");
    	    			$(".errorhei1").show();
    	    		}else if("passErr"===result){
    	    			$("#Message").html("密码输入错误,请重新输入");
    	    			$(".errorhei1").show();
    	    			//window.location.href = getPath()+"/views/jsp/myAccount.jsp";
    	    		}else if("ruleErr"===result){
    	    			$("#Message").html("当前提现金额不满足提现规则,请确认！");
    	    			$(".errorhei1").show();
    	    		}
    	        }
    	   		});	
		   $("#pop").ajaxStop(function(){
			   $('#pop').hide();
		    });
		 }
		 checkphoneNoFlag=false;
 		 $('#phoneno').focus();
	   }
	}
	}
	
})

//加载延迟时间
function load(outtimes){
		$("#pop").show();
		setTimeout(function(){$('#pop').hide()
		},outtimes);
}

 $('#ensure3').click(function(){
		$('.errorhei3').hide();
		if(isBd==1){
			window.location.href = getPath()+"/views/jsp/myAccount.jsp";
		}
	});
    $('#Close').click(function(){
		$('.errorhei3').hide();
		isBd=0;
		window.location.href = getPath()+"/views/jsp/myAccount.jsp";
	});

//点击验证码时验证手机号是否银行预留手机号
function getCheckCode(){
	//手机号规则校验
	var phoneNo = $("#phoneNo").val();
	checkphone();
	if(checkphoneNoFlag){
		checkAmount();
		if(checkAmountflag){
		$.ajax({
			type:"POST",
			url :getPath()+"/myAccount/validateUser.do", //后台验证银行及银行预留手机号
			async: false,
			data: {phoneNo:phoneNo},
			success : function(result){
				if(result!==null&& result!==""){
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
	               $("#Message").html("手机号与银行预留手机号不符！")
	               $(".errorhei1").show();
				}
			}
	   });
	}
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

//提现金额验证
function checkAmount(){
	var draAmount = $("#drawal_amount").val();   //获取输入提现金额
	if(draAmount ==null || "undefined"==typeof draAmount ||""==draAmount){
		checkAmountflag=false;
		$('#Message').html("请输入提现金额！");
    	$('.errorhei1').show();
		$('#drawal_amount').focus();
	}else{
		if(draAmount > agreeAmount){
			checkAmountflag=false;
			$('#Message').html("提现金额不得超出可提现金额！" + agreeAmount);
	    	$('.errorhei1').show();
			$('#drawal_amount').focus();
	    }else if(draAmount < 0){
	    	checkAmountflag=false;
	    	$('#Message').html("请输入正确的提现金额！");
	    	$('.errorhei1').show();
			$('#drawal_amount').focus();
	    }else{
	    	checkAmountflag = true;
	    }
	}
}



