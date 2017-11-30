//初始化获取参数并赋值到页面
$(document).ready(function(){
	var bankprop=$('#bankprop').val();
	var banknumber=$('#banknumber').val();
	var oSpan = document.getElementById('bankname');
    var oImg = document.createElement('img');   //创建一个img标签
    if(bankprop=="0105"){
    	oImg.src = '/NowHealthMobile/views/images/ccb.png';//建行
    }else if(bankprop=="0102"){
    	oImg.src = '/NowHealthMobile/views/images/icbc.png';//工商银行
    }else if(bankprop=="0103"){
    	oImg.src = '/NowHealthMobile/views/images/abc.png';//农业银行
    }else if(bankprop=="0104"){
    	oImg.src = '/NowHealthMobile/views/images/bofc.png';//中国银行
    }else if(bankprop=="0403"){
    	oImg.src = '/NowHealthMobile/views/images/pspc.png'; //中国邮政存储银行
    }else if(bankprop=="0301"){
    	oImg.src = '/NowHealthMobile/views/images/boc.png';  //交通银行
    }else if(bankprop=="0302"){
    	oImg.src = '/NowHealthMobile/views/images/zxyh.png';  //中信银行
    }else if(bankprop=="0303"){
    	oImg.src = '/NowHealthMobile/views/images/cebb.png';  //中国光大银行
    }else if(bankprop=="0305"){
    	oImg.src = '/NowHealthMobile/views/images/ms-bc.png'; //中国民生银行
    }else if(bankprop=="0308"){
    	oImg.src = '/NowHealthMobile/views/images/zs-bc.png'; //招商银行
    }else if(bankprop=="0309"){
    	oImg.src = '/NowHealthMobile/views/images/xy-bc.png'; //兴业银行
    }else if(bankprop=="0304"){
    	oImg.src = '/NowHealthMobile/views/images/hx-bc.png'; //华夏银行
    }else if(bankprop=="04105840"){
    	oImg.src = '/NowHealthMobile/views/images/pab_pc.png'; //平安银行
    }    
    oSpan.appendChild(oImg);//将img标签添加到span标签里面。
	/*$("#bankname").html(bankname);*/
	$("#banknum").html(banknumber);
	
});
/**
 * 点击验证四要素调用后台
 */
function submitForm(){
	var payee=$('#payee').val();//账户名
	var payeeid=$('#payeeid').val();//身份证
	var userphoneno=$('#userphoneno').val();//手机号码
	var bankprop=$('#bankprop').val();//银行代码属性
	var banknumber=$('#banknumber').val();//账号
	if(!validateName(payee)){
		return false;
	}
	if(!checkId(payeeid)){
		return false;
	}	
	if(!checkPhone(userphoneno)){
		return false;
	}
	$("#prompt").html("银行卡绑定中，请稍等...");
	load(6000);
	$.ajax({
		type:"POST",
		url:getUrl()+"/VerificaName/verifyCardNumber.do", 
		async:false,
		data:{payeeid:payeeid,banknumber:banknumber,
			payee:payee,userphoneno:userphoneno,bankprop:bankprop},
		/*contentType: "application/x-www-form-urlencoded; charset=utf-8",*/
		success:function(result){
			if(result=="success"){
				//如果验证成功,跳转到发送手机验证码界面
				window.location.href = getUrl()+"/views/jsp/sendMessage.jsp?userphoneno="+userphoneno;
			}else{
				$("#Message").html(result);
				$("#errorhei").show();
				$('#pop').hide();
			}			
		}
   });
}
/**
 * 账户名校验
 */
function validateName(val){
	var reg = /^[\u4e00-\u9fa5]{2,4}$/i; 
	if(val==''||val==null||val==undefined){
		$("#Message").html("账户名不能为空");
		$("#errorhei").show();
	   	return false;
	}else if(!reg.test(val)){
		$("#Message").html("账户名输入有误");
		$("#errorhei").show();
   		return false;
	}else{
		return true;
	}
}
/**
 * 身份证号校验
 */
function checkId(value){
 	if(value==null||"undefined"==value||""==value){
 			$("#Message").html("身份证不能为空");
 			$("#errorhei").show();
 	   		return false;
 	}else if(value.length!==15&&value.length!==18){		
 			$("#Message").html("身份证长度不符,请检查！");
 			$("#errorhei").show();
 	   		return false;
 	}else{
 	var code=value;
 	 var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
 	    var tip = "";
 	    var pass= true;
 	    if(!code || !/^\d{6}(19|20)?\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
 	        tip = "The format of Id no is wrong!";
 	        pass = false;
 	    }

 	    else if(!city[code.substr(0,2)]){
 	        tip = "Area code is wrong ";
 	        pass = false;
 	    }
 	    else{
 	        // 18位身份证需要验证最后一位校验位
 	        if(code.length == 18){
 	            code = code.split('');
 	            // ∑(ai×Wi)(mod 11)
 	            // 加权因子
 	            var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
 	            // 校验位
 	            var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
 	            var sum = 0;
 	            var ai = 0;
 	            var wi = 0;
 	            for (var i = 0; i < 17; i++)
 	            {
 	                ai = code[i];
 	                wi = factor[i];
 	                sum += ai * wi;
 	            }
 	            var last = parity[sum % 11];
 	            if(parity[sum % 11] != code[17]){
 	                tip = "The last character is wrong!";
 	                pass =false;
 	            }
 	        }
 	    }
 	    if(!pass){
 				$("#Message").html("身份证输入有误，请检查");
 				$("#errorhei").show();
 		   		return false;				
 	    }else{
 	    	return true;
 	    }
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


//去除字符串中的空格
function Trim(obj){
	var str = obj.value;
    var a = str.replace(/<\/?[^>]*>/gim,"");//去掉所有的html标记
    var b = a.replace(/(^\s+)|(\s+$)/g,"");//去掉前后空格
    var c = b.replace(/\s/g,"");//去除文章中间空格
    obj.value=c;
}

//加载延迟时间
function load(outtimes){
		$("#pop").show();
		setTimeout(function(){$('#pop').hide()
		},outtimes);
}
