var pagenums;  //记录当前页数
 
   //点击我的收入弹出明细列表
   $(".gainings").click(function(e){
	    pagenums =1;
	    var totalCommission = $("#income").html();
	    var num = $('#datastatis-table tr').length-1; //当前页面记录数
        e.preventDefault();
        $("#income1").html(totalCommission);  //展示佣金总额
        $("#shade-layer").show();
        var box=$("#datastatistics-box");
        box.show();
        box.addClass("anima");
        //pageTotal1 为初始化获取到的明细总记录数
        if(num >= pageTotal){
        	 $(".loading-more").hide();
        	$('.none-have').show();
        }else if(num<10){
        	$(".loading-more").hide();
        	$('.none-have').show();
        }
        $("#num").val(pagenums);
    });
 
    //点击余额 弹出明细列表
    $(".quota").click(function(e){
    	pagenums = 1;
    	var balance =$("#balance").html();
    	var num = $('#balance-table tr').length-1; //当前页面记录数
        e.preventDefault();
        $("#shade-layer").show();
        $("#balance1").html(balance);  //展示佣金余额
        var box=$("#balancedetail-box");
        box.show();
        box.addClass("anima");
        //pageTota 为初始化获取到的明细总记录数
        if(num >= pageTotal1){
        	$(".loading-more").hide();
        	$('.none-have').show();
        }else if(num < 10){
        	$(".loading-more").hide();
        	$('.none-have').show();
        }else{
        	$('.none-have').hide();
        	$('.loading-more').show();
        }
        $("#num").val(pagenums);
    });
     
    //余额明细点击加载更多
    $("#balance-load").click(function(){
    	var pageNum = $("#num").val();
    	var nowPageNum =parseInt(pageNum)+1; //获取当前页数
        var This=$(this),
        num=$('#balance-table tr').length-1; //当前页面记录数
        This.hide();
        This.siblings('.loading-now').show();
        //pageNum 为初始化获取明细的总页数   ;pageSize1 为初始化获取明细每页10条的数据
        $.getJSON(getPath()+"/myAccount/OrderSearch.do",{pageNum:nowPageNum,pageSize:pageSize1},function(data){
        	var orderDetails = data.page1;
        	This.siblings('.loading-now').hide();
            This.show();
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
             $("#num").val(nowPageNum);//设置当前页码
             num1=$('#balance-table tr').length-1; //页面展示的记录数  总的记录数:pageTotal
             if(num1 >= pageTotal1){
            	 $(".loading-more").hide();
             	$('.none-have').show();
             }else{
            	$(".loading-more").show();
             }
        })
    });
    
    //我的收入明细加载更多   
    $("#datastatis-load").click(function(){
    	var searchDate = $("#date").val();
    	var pageNum = $("#num").val();
    	var nowPageNum = parseInt(pageNum)+1; //获取当前页数
        var This=$(this),
        num=$('#datastatis-table tr').length-1;
        This.hide();
        This.siblings('.loading-now').show(); 
        //pageNum1 为初始化获取明细的总页数   ;pageSize1 为初始化获取明细每页10条的数据
        $.getJSON(getPath()+"/myAccount/OrderSearch.do",{pageNum:nowPageNum,pageSize:pageSize1,searchDate:searchDate},function(data){
            var orderDetails = data.page;
            var totalSize = orderDetails.total;
        	This.siblings('.loading-now').hide();
            for (var i = 0; i < data.page.list.length; i++) {
            	 var commission = orderDetails.list[i].commission;
            	 var createTime1 = new Date(orderDetails.list[i].createtime);
    			 var cerateTime =formatDate(createTime1);  //创建时间
            	 if(commission == null || commission ==""){
    				 commission ="--";
    			 }
            	$("#datastatis-table").append(' <tr><td class="fir_td">'+orderDetails.list[i].purchasername+'</td><td class="fir_td">'+cerateTime+'</td><td class="fir_td">'+commission+'</td></tr>')
             }
            $("#num").val(nowPageNum);
            num1=$('#datastatis-table tr').length-1;
            if(num1 >= totalSize){   //页面展示的记录数  总的记录数:pageTotal1
            	$(".loading-more").hide();
            	$('.none-have').show();
             }else{
            	$(".loading-more").show();
             }
        })
    });
    //申请提现按钮
    var isBd=0;
    function cashDrawal(){
    	$.ajax({
			type:"POST",
			url :getPath()+"/myAccount/cashDrawal.do?userId="+userId,
			async: false,
			success : function(result){
				if(result !==null&& result!==""){
						window.location.href = getPath()+"/views/jsp/draw-cash.jsp";
				}else{	
					$('#Message3').html("银行卡未绑定,是否绑定银行卡！");
					$('.errorhei3').show();
					isBd=1;
				}
			}
	   });
    }
    $('#ensure3').click(function(){
		$('.errorhei3').hide();
		if(isBd==1){
			window.location.href = getPath()+"/views/jsp/addBankCard.jsp";
		}
	});
    $('#Close').click(function(){
		$('.errorhei3').hide();
		isBd=0;
	});
  
   /* //确定跳转银行卡绑定页面
    function isbind(){
    	 window.location.href = getPath()+"/views/jsp/verifyCardno.jsp";
    }
    function nobind(){
    	$(".errorhei2").hide();
    }*/
    
    
    //我的收入根据时间查询
    $("#date").change(function(){ 
    	pagenums =1;
    	var list_tr=$("#datastatis-table tr"),tr_length=$("#datastatis-table tr").length;
    	for(var i=1;i<tr_length;i++){
    		list_tr[i].remove();
    	}
    	var searchDate = $("#date").val();
        var This=$(this),
        num=$('#datastatis-table tr').length-1;
        This.siblings('.loading-now').show(); 
        //pageNum1 为初始化获取明细的总页数   ;pageSize1 为初始化获取明细每页10条的数据
        $.getJSON(getPath()+"/myAccount/OrderSearch.do",{pageNum:pagenums,pageSize:pageSize1,searchDate:searchDate},function(data){
            var orderDetails = data.page;
            var totalPageNm = orderDetails.pages; //总页数
            var totalSize   = orderDetails.total; //总记录数
            if(orderDetails.list.length > 0 ){
            	This.siblings('.loading-now').hide();
                for (var i = 0; i < data.page.list.length; i++) {
                	 var commission = orderDetails.list[i].commission;
                	 var createTime1 = new Date(orderDetails.list[i].createtime);
        			 var cerateTime =formatDate(createTime1);  //创建时间
                	 if(commission == null || commission ==""){
        				 commission ="--";
        			 }
                	$("#datastatis-table").append(' <tr><td class="fir_td">'+orderDetails.list[i].purchasername+'</td><td class="fir_td">'+cerateTime+'</td><td class="fir_td">'+commission+'</td></tr>')
                 }
                   $("#num").val(pagenums);
                    num1=$('#datastatis-table tr').length-1;
                 if(num1 >= totalSize){   //页面展示的记录数  总的记录数:pageTotal1
                	 $(".loading-more").hide();
                	 $('.none-have').show();
                 }else if(totalPageNm<=1 && (num1-1)<10){
                 	$(".loading-more").hide();
                	$('.none-have').show();
                }else{
                	$(".loading-more").show();
                 }	
            }else{
            	$(".loading-more").hide();
           	    $('.none-have').show();
            }
        })
    });
    
    // 关闭明细
    $(".close-icon").click(function(){  
    	var box=$("#balancedetail-box");
    	box.removeClass("anima");
    	box.hide();
        $("#shade-layer").hide();
    });
    $(".close-icon-1").click(function(){
        var box=$("#datastatistics-box");
        box.removeClass("anima");
        box.hide();
        $("#shade-layer").hide();
       
    });
    
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
    var os1 = new Optiscroll(document.getElementById('os1'), { preventParentScroll: true });
    var osl = new Optiscroll(document.getElementById('osl'), { preventParentScroll: true });