/**
 * 点击绑定，验证卡号
 */
var isfalsg=false;
function submitForm(){
	var payeeid=$('#payeeid').val();//身份证
	var banknumber=$('#banknumber').val();//银行卡号
	var payee=$('#payee').val();//账户名
	var userphoneno=$('#userphoneno').val();//手机号码
	var cashDealpass=$('#cashDealpass').val();//提现密码
	var checkpassword=$('#checkpassword').val();//密码确认
	var bankprop=$('#bc-ID').val();//银行属性
	if(!checkId(payeeid)){
		return false;
	}
	if(!checkIdCard(banknumber)){
		return false;
	}
	testYinHangnumber(bankprop, banknumber);
	if(!isfalsg){//判断银行账户和所选银行类型是否一致
		return false;
	}
	if(!validateName(payee)){
		return false;
	}
	if(!checkPhone(userphoneno)){
		return false;
	}
	if(!checkPass(cashDealpass)){
		return false;
	}
	if(checkpassword!==cashDealpass){
		$("#Message").html("两次输入的密码不一致,请确认！");
		$("#errorhei").show();
	   	return false;
	}
	$.ajax({
		type:"POST",
		url:getUrl()+"/VerificaName/verifyCardNumber.do", 
		async:false,
		data:{payeeid:payeeid,banknumber:banknumber,
			payee:payee,userphoneno:userphoneno,cashDealpass:cashDealpass,bankprop:bankprop},
		success:function(result){
			if(result=="success"){
				window.location.href = getUrl()+"/views/jsp/draw-cash.jsp";
			}else{
				$("#Message").html(result);
				$("#errorhei").show();
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
/**
 * 密码校验
 */
//密码正则，4到16位
function checkPass(value) {
	if(value==null||"undefined"==value||""==value){
		$("#Message").html("密码不能为空");
		$("#errorhei").show();
   		return false;
	}else if (!(/^[a-zA-Z0-9_-]{4,16}$/.test(value))) {
		$('#Message').html("密码输入有误");
		$("#errorhei").show();		
		return false;
	} else {
		return true;
	}
}
/**
 * 银行卡账户校验
 * 银行卡号一般15位或19位，第一位不为0
 */
function checkIdCard(value) {
	if(value==null||"undefined"==value||""==value){
		$("#Message").html("银行账号不能为空");
		$("#errorhei").show();
   		return false;
	}
	if(value.length < 16 || value.length > 19) {
	     $("#Message").html("银行卡号长度必须在16到19之间");
	     $("#errorhei").show();
	     return false;
	   }
	 var num = /^\d*$/; //全数字
	 if(!num.test(value)) {
	     $("#Message").html("银行卡号必须全为数字");
	     $("#errorhei").show();
	     return false;
	 }
	 //开头2位
	 var strBin = "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
	 if(strBin.indexOf(value.substring(0, 2)) == -1) {
	     $("#Message").html("银行卡号开头2位不符合规范");
	     $("#errorhei").show();
	     return false;
	 }
	 return true;
}

/**
 * 测试银行卡账户与所选择银行是否一致
 */
function testYinHangnumber(bankprop,banknumber){
	/**
	 * 中国工商银行，农业银行，中国银行，中国建设银行，邮政储蓄银行，交通银行，中信银行，光大银行，民生银行，招商银行，平安银行，兴业银行，华夏银行
	 */
	var bankname;
	if(bankprop=="0102"){
		bankname="中国工商银行"
	}else if(bankprop=="0103"){
		bankname="农业银行"
	}else if(bankprop=="0104"){
		bankname="中国银行"
	}else if(bankprop=="0105"){
		bankname="建设银行"
	}else if(bankprop=="0403"){
		bankname="邮政储蓄银行"
	}else if(bankprop=="0301"){
		bankname="交通银行"
	}else if(bankprop=="0302"){
		bankname="中信银行"
	}else if(bankprop=="0303"){
		bankname="光大银行"
	}else if(bankprop=="0305"){
		bankname="民生银行"
	}else if(bankprop=="0308"){
		bankname="招商银行"
	}else if(bankprop=="04105840"){
		bankname="平安银行"
	}else if(bankprop=="0309"){
		bankname="兴业银行"
	}else if(bankprop=="0304"){
		bankname="华夏银行"
	}
	if(bankprop==null || bankprop==undefined || bankprop==""){
		$('#Message').html("请点击选择银行卡");
		$("#errorhei").show();	
		return false;
	}
	$.ajax({
		type:"POST",
		url:getUrl()+"/VerificaName/checkBankNumber.do", 
		async:false,
		data:{banknumber:banknumber,bankname:bankname},
		success:function(result){			
			if(result=="success"){
				isfalsg=true;
			}else{
				$('#Message').html("账户所属银行和所选银行不一致");
				$("#errorhei").show();	
				isfalsg=false;
			}			
		}
   });	
}

//去除字符串中的空格
function Trim(obj){
	var str = obj.value;
    var a = str.replace(/<\/?[^>]*>/gim,"");//去掉所有的html标记
    var b = a.replace(/(^\s+)|(\s+$)/g,"");//去掉前后空格
    var c = b.replace(/\s/g,"");//去除文章中间空格
    obj.value=c;
}
