/**
 * 点击下一步，
 */
var isfalsg=false;
var bankname;
function submitForm(){
	var banknumber=$('#banknumber').val();//银行卡号
	var bankprop=$('#bc-ID').val();//银行属性
	if(!checkIdCard(banknumber)){
		return false;
	}
	testYinHangnumber(bankprop, banknumber);
	if(!isfalsg){//判断银行账户和所选银行类型是否一致
		return false;
	}
	$('#bankprop').val(bankprop);
	$('#form').submit();
	/*//跳转到验证银行卡信息页面(传，银行卡属性，银行卡账号，和银行名称)
	window.location.href = getUrl()+"/views/jsp/verificatCardInf.jsp?bankname="+bankname+"&bankprop="+bankprop+"&banknumber="+banknumber;*/
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
	
	if(bankprop=="0102"){
		bankname="中国工商银行"//工商银行
	}else if(bankprop=="0103"){
		bankname="农业银行"
	}else if(bankprop=="0104"){
		bankname="中国银行"
	}else if(bankprop=="0105"){
		bankname="中国建设银行"//中国建设银行
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
