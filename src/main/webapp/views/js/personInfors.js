var agreeflag = false;

/*$(function () {
    var currYear = (new Date()).getFullYear();
    var opt={};
    opt.date = {preset : 'date'};
    opt.datetime = {preset : 'datetime'};
    opt.time = {preset : 'time'};
    opt.default = {
        theme: 'android-ics light',
        display: 'modal',
        mode: 'scroller',
        dateFormat: 'yyyy-mm-dd',
        lang: 'zh',
        showNow: true,
        nowText: "今天",
        startYear: currYear - 50,
        endYear: currYear + 50
    };
    var t=new Date();
    var iToDay=t.getDate(),val_date=new Date().getDate()+1,
    next_Date=new Date(new Date().setDate(val_date)),
    year=next_Date.getFullYear(),
    month=(next_Date.getMonth()<10)?"0"+(next_Date.getMonth()+1):next_Date.getMonth();
    Day=(next_Date.getDate()<10)?"0"+next_Date.getDate():next_Date.getDate();
    if(month>t.getMonth()+1){Day=(next_Date.getDate()<10)?"0"+(next_Date.getDate()+1):(next_Date.getDate()+1);}
    var iToMon=t.getMonth();
    var iToYear=t.getFullYear();
    var newDay = new Date(iToYear,iToMon,(iToDay+1));
    var min = { preset : 'date', minDate: new Date(newDay) };
    $("#date").mobiscroll($.extend(min, opt['default']));
    $("#date").val(year+"-"+month+"-"+Day);
});
*/ 
var t=new Date();
var iToDay=t.getDate();
var val_date=new Date().getDate(),  //当天时间 
next_Date=new Date(new Date().setDate(val_date)),
    year=next_Date.getFullYear(),
    month=(next_Date.getMonth()<10)?"0"+(next_Date.getMonth()+1):next_Date.getMonth();
    Day=(next_Date.getDate()<10)?"0"+next_Date.getDate():next_Date.getDate();
    if(month>t.getMonth()+1){Day=(next_Date.getDate()<10)?"0"+(next_Date.getDate()+1):(next_Date.getDate()+1);}
    $("#date").val(year+"-"+month+"-"+Day);
/**
 * 姓名校验
 */
function validateName(val){
	var reg = /^[\u4e00-\u9fa5]{2,4}$/i; 
	if(val==''||val==null||val==undefined){
		$("#Message").html("姓名不能为空");
		$("#errorhei").show();
	   	return false;
	}else if(!reg.test(val)){
		$("#Message").html("姓名输入有误");
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
 * 年龄、身高、体重
 */
function checkAge(value){
	if(value==null||"undefined"==value||""==value){
		$("#Message").html("年龄不能为空");
		$("#errorhei").show();
   		return false;
	}else{
		return true;
	}
}

function checkHigh(value){
	if(value==null||"undefined"==value||""==value){
		$("#Message").html("身高不能为空");
		$("#errorhei").show();
   		return false;
	}else{
		return true;
	}
}

function checkWeight(value){
	if(value==null||"undefined"==value||""==value){
		$("#Message").html("体重不能为空");
		$("#errorhei").show();
   		return false;
	}else{
		return true;
	}
}
/**
 * 常用邮箱校验
 */
function checkEmail(value){	
	if(value==null||"undefined"==value||""==value){
			$("#Message").html("邮箱不能为空");
			$("#errorhei").show();
	   		return false;
	}else if (!(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value))) {
			$("#Message").html("邮箱有误，请检查");
			$("#errorhei").show();
	   		return false;		
	} else{
			return true;
	}	
}

/**
 * 护照号校验
 */
function checkPassport(value){
	var reg =new RegExp("(^([PSE]{1}\\d{7}|[GS]{1}\\d{8})$)");//E字打头的后面不知道要跟几位
	if(value==null||"undefined"==value||""==value){
		$("#Message").html("护照号不能为空");
		$("#errorhei").show();
   		return false;
	}else if(!reg.test(value.toUpperCase())){
		$("#Message").html("护照号输入有误");
		$("#errorhei").show();
   		return false;
	}else{
		return true;
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

/**
 * 获取验证码
 */
function getCheckCode(){
	var purchaserPhoneNo=$('#purchaserphoneno').val();
	if(checkPhone(purchaserPhoneNo)){
		$.ajax({
			url : getUrl()+"/personController/selectByPurchaserPhoneNo.do",
			async: false ,
			data: {phoneNo:purchaserPhoneNo},
			success : function(result){
			  if(result=="1"){
				var num=60;
			    var Span=document.getElementsByClassName('smscode_list ')[0].getElementsByTagName('p')[0];
			    Span.setAttribute('class','Color ');
			    _interval=setInterval( function time(){
			        Span.innerHTML=' ';
			        num--;
			        Span.innerHTML=num+'s';
			        if(num<=0){
			            Span.removeAttribute("class");
			            Span.innerHTML='获取验证码';
			            clear();
			        }
			    },1000);
				function clear(){
					clearInterval(_interval);
				}
				$.ajax({
					url : getUrl()+"/personController/phoneCheck.do",
					async: false ,
					data: {phoneNo:purchaserPhoneNo},
					success : function(generation){
					}
				});
		 }else{
			 $('#Message').html("该手机号已有订单，请勿重复购买！");
		     $('.errorhei').show();
		 }
		}
	 });
	}
}

/**
 * 通过后台校验验证码录入是否正确
 */
var codeFlag=false;
function checkPhoneCode(validationNo,purchaserPhoneNo){
	$.ajax({
		url : getUrl()+"/personController/checkPhoneCode.do",
		async: false ,
		data: {checkCode:validationNo,phoneNo:purchaserPhoneNo},
		success : function(result){
			if(result=="true"){
				codeFlag=true;
			}
		},
		error: function(res) {
			$('#Message').html("验证码校验失败！");
	    	$('.errorhei').show();
        }		
	});
}

//初始化获取支付金额
$(document).ready(function(){
    var payMoney=$('#pay_money').val();
    $('.pay_box span').html(payMoney);
})

/**
 * 提交人员信息页面，进入支付界面
 */
function submitForm(){
	//增加校验
	var purname=$('#purchasername').val();  //姓名
	var ID = $("#ID").val();                //获取证件类型
	var purid   = $('#purchaserid').val();  //身份证号
	var passport= $("#passport").val();     //护照号
	var gender  = $('input:radio[name="purchaserSex"]:checked').val();//性别
	var age     = $("#age").val();          //年龄
	var high    = $("#high").val();         //身高
	var weight  = $("#weight").val();       //体重
	var isAllergy =$('input:radio[name="isAllergy"]:checked').val();//有过敏症
	var isfamilyillness = $('input:radio[name="isfamilyillness"]:checked').val();//有家族病史
	var puremail=$('#purchaseremail').val();//email
	var purphoneno=$('#purchaserphoneno').val();//手机号
	var validano=$('#validationNo').val();  //验证码
	if(!validateName(purname)){
		return false;
	}
	//判断证件类型
	if("身份证"==ID){
		if(!checkId(purid)){
			return false;
		}
	}else if("护照"==ID){
		if(!checkPassport(passport)){
			return false;
		}
		if(!checkAge(age)){
			return false;
		}
	}
	if(!checkHigh(high)){
		return false;
	}
	if(!checkWeight(weight)){
		return false;
	}
	if(!checkEmail(puremail)){
		return false;
	}
	if(!checkPhone(purphoneno)){
		return false;
	}
	if(!checkCode(validano)){
		return false;
	}
	checkPhoneCode(validano,purphoneno);
	if(!codeFlag){
		$('#Message').html("验证码输入有误！");
    	$('.errorhei').show();
    	return false;
	}
	getAgree();
	if(agreeflag){
		$("#prompt").html("订单确认中，请稍等...");
		load(6000);
		$('#form').submit();
	}
}
//关闭错误消息提示框
$("#ensure").click(function(){
		$("#errorhei").hide();
});

//加载延迟时间
function load(outtimes){
		$("#pop").show();
		setTimeout(function(){$('#pop').hide()
		},outtimes);
}

//小写转大写
function setUpperCase(obj){ 
    var a=obj.value;
    var b=a.toLocaleUpperCase();
    obj.value=b;
}

//去除字符串中的空格
function Trim(obj){
	var str = obj.value;
    var a = str.replace(/<\/?[^>]*>/gim,"");//去掉所有的html标记
    var b = a.replace(/(^\s+)|(\s+$)/g,"");//去掉前后空格
    var c = b.replace(/\s/g,"");//去除文章中间空格
    obj.value=c;
}

$(".check-icon input").click(function(){
	var This_parent=$(this).parent(),This=$(this);
	if(This.attr("checked")=="checked"){
		This.removeAttr("checked");
		 This_parent.removeClass("select-on");
	}else{
		 This_parent.addClass("select-on");
		 This.attr("checked","checked")
	}
})

  $(".close-icon").click(function(){  
    	var box=$("#agreement-box");
    	box.removeClass("anima");
        $("#shade-layer").hide();
    });
$(".Dr-artinfo").click(function(e){
	 $("#shade-layer").show();
	 var box=$("#agreement-box");
 	box.addClass("anima");
});
var os1 = new Optiscroll(document.getElementById('os1'), { preventParentScroll: true });

//选择条款
function getAgree(){
	if($("#agree").attr("checked") =="checked"){
		agreeflag = true;
	}else{
		agreeflag = false;
		$('#Message').html("请确认服务协议！");
    	$('.errorhei').show()
	}
}


$("#ID").change(function(){
	var This=$(this),val=This.val();
	$("#ID_ipt").val(val);
	if(This.val()=="护照"){
		$(".passport-box").show();
		$(".person-ID").hide();
	}else if(This.val()=="身份证"){
		$(".passport-box").hide();
		$(".person-ID").show();
	}
})


/*$(".ID-box").click(function(){
	$(".select-layer-box").show();
	$(".sel-ID-list").addClass("anima");
})
$(".yes").click(function(){
	$(".sel-ID-list").removeClass("anima");
	$(".select-layer-box").hide();
})*/
/*$("#sel-ID-list li").click(function(){
	var This=$(this),val=This.text(),Index=This.index();
	This.addClass("FFF");
	This.siblings().removeClass("FFF");
	$("#ID_ipt").val(val);
	if(Index==0){
		$(".passport-box").hide();
		$(".person-ID").show();
	}else if(Index==1){
		$(".passport-box").show();
		$(".person-ID").hide();
	}
})*/

//支付完成回调 测试方法
/*function completehuidiao(){
	var payinfo ={"appId":"wxda18d758438de540","timeStamp":"1502246129","signType":"MD5","package":"prepay_id=wx20170809103529569747d8bf0985864350","nonceStr":"24701","paySign":"5B2E2E02B928CFE2FC2FEBC50F89DC7D"}
	$.ajax({
		url : getUrl()+"/paymentComplete/payCallback.do",
		async: false ,
		data: {trxid:"111746070000007361", sign:"E96C669BA7200630BE5F19C3F42ECA5C", retcode:"SUCCESS", trxstatus:"0000", reqsn:"LW201708090000078199", appid:"00011842", payinfo:payinfo, cusorderid:"LW201708090000078199",cusid:"356584059609216"},
		success : function(result){
			if(result=="true"){
				
			}
		},
		error: function(res) {
			$('#Message').html("验证码校验失败！");
	    	$('.errorhei').show();
        }		
	});
}*/








