//发送咨询码到用户手机
$(document).ready(function(){
      var queryCode = $(".qureycode span").html();
      var phoneNo = $("#phoneNo").val();
	  $.ajax({
		url: getUrl()+"/paymentComplete/SendAdvisoryCode.do?queryCode="+queryCode+"&phoneNo="+phoneNo,
		type:'post',
		success : function(data) {
		   
		}
	 })
}); 
//发送咨询码到用户邮箱
$(document).ready(function(){
	    var emailAdraess = $("#emailAdraess").val();
	    $.ajax({
		  url: getUrl()+"/paymentComplete/sendEmail.do?emailAdraess="+emailAdraess,
		  type:'post',
		  success : function(data) {
		   
		}
	 })
});