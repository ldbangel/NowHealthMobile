var orderCount1,orderCount2,orderCount3,initData,initData2;
var pageSize = 5; //myaccount分页每页信息数量
var pageTotal,pageTotal2;
var orderDetails,orderDetails1;
var pageSize1 = 10;

$(document).ready(function(){
	init();
	initCommissionDatils();
	initOrderDetails();
});

//MyAccount初始化
function init(){
	$.getJSON(getPath()+"/myAccount/myAccountInit.do?pageNum=1&pageSize="+pageSize,function(data){
		$('.img').attr('src',data.headportrait);
		$('.phone').html(data.nickname);
		initData = data;
		orderCount1 = data.page1.total;
		orderCount2 = data.page2.total;
		orderCount3 = data.page3.total;
		$('.waitPay').html(orderCount1);
		$('.alreadyPay').html(orderCount2);
		$('.closed').html(orderCount3);
		tabShowRule();
	});
}

$(document).ready(function(){
	if(agentFlag!=0 && agentFlag!==undefined){
		$(".purse_box").show();
	}
});

//初始化展示我的收入/余额数据
function initCommissionDatils(){
	var totalCommission;
	var balance;
	$.ajax({
		type:"POST",
		url:getPath()+"/myAccount/AllOrderSearch.do",      //通过userId获取佣金明细
		async:false,
		success:function(result){
			if(result!=null && result!=""){
				totalCommission = (result.totalCommission).toFixed(2);
				balance = (result.balance).toFixed(2);
				if(totalCommission!=null && totalCommission!=""){
					$("#income").html(totalCommission);
				}
				if(balance!=null && balance!=""){
					$("#balance").html(balance);
				}
			}
		}
   });
}

//页面初始化加载10条数据
function initOrderDetails(){
	$.getJSON(getPath()+"/myAccount/OrderSearch.do?pageNum=1&pageSize="+pageSize1+"&searchDate="+searchDate,function(data){
		initData2 = data;
		/*余额明细初始化所加载数据*/
		//pageNum1 = data.page1.pages;      //获取总页数
		pageTotal1 = data.page1.total;     //总记录数
		orderDetails = data.page1;
		/*我的收入明细初始化所加载数据*/
		//pageNum = data.page.pages;      //获取总页数
		pageTotal = data.page.total;      //总记录数
		orderDetails1 = data.page;
		commsionShow()
	});
 }

//初始化佣金明细展示规则
function commsionShow(){
	if(orderDetails !=null && orderDetails1 !=null){
		//余额明细
		for (var i = 0; i < orderDetails.list.length; i++) {
			 var amount = orderDetails.list[i].amount;
			 var drawalstate = orderDetails.list[i].drawalstate;
			 var darwaltime = orderDetails.list[i].darwaltime.substr(0,19);  //创建时间
			 if(darwaltime == null || darwaltime ==""){
				 darwaltime ="--";
			 }
			 if(drawalstate!=null&& drawalstate!=""&&drawalstate=="1"){
				 drawalstate ="已提现"
			 }
		    $("#balance-table").append(' <tr><td class="fir_td">'+darwaltime+'</td><td class="fir_td">'+amount+'</td><td class="com">'+drawalstate+'</td></tr>')
         }
		//我的收入明细
		for (var i = 0; i < orderDetails1.list.length; i++) {
			 var commission = orderDetails1.list[i].commission;
			 var commissionState = orderDetails1.list[i].commissionstart;
			 var createTime1 = new Date(orderDetails1.list[i].createtime);
			 var cerateTime =formatDate(createTime1);  //创建时间
			 if(commission == null || commission ==""){
				 commission ="--";
			 }
		     $("#datastatis-table").append(' <tr><td class="fir_td">'+orderDetails1.list[i].purchasername+'</td><td class="fir_td">'+cerateTime+'</td><td class="com">'+commission+'</td></tr>')
        }
	}
}

//我的订单tab展示规则
function tabShowRule(){
	var This = $('.title_list>.Active'),A=$('.title_list li');
	var index = This.index();
	var num = This.children().eq(0).text();
	if(num==0){
		if($('.waitPay').text()!=0 && index!=0){
			A.eq(0).click();
		}else if($('.alreadyPay').text()!=0 && index!=1){
			A.eq(1).click();
		}else if($('.closed').text()!=0 && index!=2){
			A.eq(2).click();
		}else if($('.waitPay').text()==0&&$('.alreadyPay').text()==0
				&&$('.closed').text()==0){
			A.eq(0).click();
		}
	}else{
		if(index==0){
			A.eq(0).click();
		}else if(index==1){
			A.eq(1).click();
		}else if(index==2){
			A.eq(2).click();
		}
	}
}

//点击tab事件
$('.title_list>li').click(function(e){
	$('#pager').html('');
	var This = $(this);
    e.preventDefault();
    This.addClass('Active').siblings().removeClass('Active');
    var index = This.index();
    var orderList;
    if(index==0){
    	orderList = initData.page1.list;
    }else if(index==1){
    	orderList = initData.page2.list;
    }else if(index==2){
    	orderList = initData.page3.list;
    }
    $('#tabFlag').val(index);
    showTab(orderList,index,1);
});

//展示当前页的数据
function showTab(orderList,flag,thatPage){
    $('#pager').html('');
    $('#Order_information').html("");
    var Last_Page;
    if(flag==0){
    	Last_Page = initData.page1.pages;
    	for(var i=0;i<orderList.length;i++){
    		var status="待支付";
       	    addPolicyInfo(orderList[i],status);
    	}
    }else if(flag==1){
    	Last_Page = initData.page2.pages;
    	for(var i=0;i<orderList.length;i++){
    		var status="已支付";
       	    addPolicyInfo(orderList[i],status);
    	}
    }else if(flag==2){
    	Last_Page = initData.page3.pages;
    	for(var i=0;i<orderList.length;i++){
    		var status="已撤销";
    	    addPolicyInfo(orderList[i],status);
    	}
    }
    var That_Page = thatPage;
    Pager(That_Page,Last_Page);
}

//时间戳转换日期
function formatDate(now) { 
	var year= now.getFullYear();
	var month=now.getMonth()+1; 
	month = month < 10 ? ('0' + month) : month;
	var date=now.getDate(); 
	date = date < 10 ? ('0' + date) : date; 
	var hour=now.getHours(); 
	hour = hour < 10 ? ('0' + hour) : hour; 
	var minute=now.getMinutes(); 
	minute = minute < 10 ? ('0' + minute) : minute; 
	var second=now.getSeconds();
	second = second < 10 ? ('0' + second) : second; 
	return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
} 


//我的订单详情动态加载
function addPolicyInfo(baseinfor,orderstate){
	var orderno="'"+baseinfor.orderno+"'";
	var createTime1 = new Date(baseinfor.createtime);
	var updateTime1 = new Date(baseinfor.updatetime);
	var createTime =formatDate(createTime1);
	var updateTime = formatDate(updateTime1);
	var order_info = $('#Order_information');
	var sp_div = $('<div class="single_product"/>');
	sp_div.append($('<ul class="Top_mation"/>').append('<li class="left">订单编号: <span class="indent_number">'+baseinfor.orderno+'</span></li class="right"><li class="right">状态: <span class="mode">'+orderstate+'</span></li>'));
	var mm_div = $('<div class="Middle_mation"/>');
	mm_div.append('<img src="'+getPath()+'/views/images/420--300.jpg" class="car_img"/>');
	var sp_ul = $('<ul class="Car_mation"/>').append('<li class="main_search">用户姓名: <span class="Car_number">'+baseinfor.purchasername+'</span></li>');
	if(baseinfor.updatetime != null && baseinfor.updatetime != ""){
		sp_ul.append($('<li style="color:#333;">更新时间:<span class="begin_date">'+updateTime+'</span></li>'));
	}else{
		sp_ul.append($('<li style="color:#333;">创建时间:<span class="begin_date">'+createTime.slice(0,19)+'</span></li>'));
	}
	sp_ul.append($('<li style="color:#333;">用户手机号:<span class="end_date">'+baseinfor.purchaserphoneno+'</span></li>'));
	if(baseinfor.querycode != null && baseinfor.querycode!="" && baseinfor.querycode!="null"){
		sp_ul.append($('<li style="color:#333;">查询码:<span class="end_date">'+baseinfor.querycode+'</span></li>'));
	}else{
		sp_ul.append($('<li style="color:#333;">查询码:<span class="end_date">暂无</span></li>'));
	}
	
	mm_div.append(sp_ul).append('<!--<img src="'+getPath()+'/views/images/BM.png" style="vertical-align:middle;"/>-->');
	sp_div.append(mm_div);
	var p = $('<p/>');
	if((baseinfor.paymentstatus==10 && baseinfor.orderstart==30)|| baseinfor.orderstart==20 ){
		p.append('<a href="#" onclick="continuePay('+orderno+');">继续支付</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cancelOrder('+orderno+');">取消订单</a>&nbsp;&nbsp;&nbsp;&nbsp;');
	}else if(baseinfor.paymentstatus==20 && (baseinfor.orderstart==40 || baseinfor.orderstart==50)){
		p.append('<a href="'+getPath()+'/views/jsp/nowhealQR.jsp">时康关注</a>&nbsp;&nbsp;&nbsp;&nbsp;')
	}
	p.append('<span style="color:#333;font-size:11px;">总金额</span>: <span class="money">'+baseinfor.totalamount+'</span>');
	sp_div.append(p);
	order_info.append(sp_div);
}

//分页栏
function Pager(That_Page,Last_Page){
    var pager=$('#pager');
    pager.html("");
    if((That_Page-1)>0){
        pager.append("<li class='prev home'><span>首页</span></li><li class='prev'><span>上一页</span></li><li>"+parseInt(That_Page-1)+"</li>");
    }else{
        pager.append("<li class='none'><span>首页</span></li>");
        pager.append(" <li class='none'><span>上一页</span></li>");
    }
    pager.append("<li class='act'>"+That_Page+"</li>");//当前页
    if((That_Page+1)<=Last_Page){
    	pager.append("<li>"+ parseFloat(That_Page+1) +"</li><li class='next'><span>下一页</span></li><li class='next end' data-last='"+Last_Page+"' ><span>尾页</span></li>");
    }else{
        pager.append(" <li class='none'><span>下一页</span></li>");
        pager.append(" <li class='none'><span>尾页</span></li>");
    }
    pager.append(" <li class='All_page' disabled='disabled'><span>共"+Last_Page+"页</span></li>");
}

//点击页码换页
$('#pager').on('click','#pager>li',function(event){
    var This=$(this),fewPage,flag=$('.title_list>.Active').index();
    if(This.hasClass('act')||This.hasClass('All_page')||This.hasClass('none')){
    	return true;
    }else if(This.children().length!=0){
        if(This.hasClass('next')){//判断 此页 的  右边还是左边
            if(This.hasClass('end')){
            	fewPage=parseFloat(This.attr('data-last'))
            }else{
            	fewPage=parseFloat(parseInt(This.siblings('.act').text()) + 1);
            }
        }else if(This.hasClass('prev')){
            if (This.hasClass('home')) {
            	fewPage = 1
            } else {
            	fewPage = parseFloat(parseInt(This.siblings('.act').text()) - 1);
            }
        }
        getdata(fewPage,flag);$(document).scrollTop(0);
    }else{
        /*fewPage=parseFloat(This.text());*/
    	return true;
    }
});

//点击分页页码，请求后台返回对应页码的数据
function getdata(thatPage,flag){
   $.getJSON(getPath()+'/myAccount/getPageOrders.do?pageNum='+thatPage+'&flag='+flag+'&pageSize='+pageSize, function (data) {
	   if(flag=="0"){
		   orderCount1=data.total;
	   }else if(flag=="1"){
		   orderCount2=data.total;
	   }else if(flag=="2"){
		   orderCount3=data.total;
	   }
       var orderList = data.list;
		
       $(".waitPay").html(orderCount1);
       $(".alreadyPay").html(orderCount2);
       $(".closed").html(orderCount3);
       
       showTab(orderList,flag,thatPage);
   });
}

//继续支付
function continuePay(orderno){
	window.location.href = getPath()+"/myAccount/continuePay.do?orderno="+orderno;
}

//取消订单
function cancelOrder(orderno){
	if(window.confirm('你确定要取消订单?')){
		$.ajax({
			url:getPath()+"/myAccount/cancelOrder.do",
			type:"post",
			data:{orderno:orderno},
			success:function(result){
				if(result=="success"){
					$('#Message').html("取消成功！");
					$('.errorhei1').show();
					$('#ensure').click(function(){
						$('.errorhei1').hide();
						init();  //取消订单之后刷新页面
					});
				}
			}
		});
		
	}
}
