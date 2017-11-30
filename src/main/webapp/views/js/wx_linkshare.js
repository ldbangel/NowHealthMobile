//取绝对路径地址
function getPath(){
	 var protocol = window.location.protocol;
	 var host = window.location.host;
	 var pathname = window.location.pathname.split('/');
	 var url = protocol+"//"+host+"/"+pathname[1];
	 return url;
}

$(document).ready(function(){
	var ua = window.navigator.userAgent.toLowerCase(); //识别微信浏览器
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
    	iswechatBrowser=true;
    }else{
    	iswechatBrowser=false;
    }
    if(iswechatBrowser){ //如果是微信浏览器就去配置微信JS接口
    	var currenturl = location.href.split('#')[0];
    	var sharedTitle = "【Dr.康】";
    	var imageUrl= getPath()+"/views/images/linkShared_image.png";
    	var sharenumber=parseFloat(shareFrequency)+parseFloat(1);
		$.ajax({  
			type: "POST",  
			url: getPath()+"/WechatLogin/getWechatJSAccess.do",  
			data:{currenturl:currenturl,sharenumber:sharenumber},//将对象序列化成JSON字符串  
			//data:currenturl,
			dataType:"json",  
			//contentType:'application/json;charset=utf-8', //设置请求头信息  		
			success: function(data){ 
				var sharedLink = data.sharedLink;
				wx.config({
				    debug: false, 
				    appId: data.appid, // 必填，公众号的唯一标识
				    timestamp:data.timestamp, // 必填，生成签名的时间戳
				    nonceStr: data.nonceStr,   // 必填，生成签名的随机串
				    signature: data.signature.toLowerCase(), // 必填，签名，见附录1
				    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表
				});
				wx.ready(function(){ //通过ready接口处理成功验证,需要把相关接口放在ready函数中调用来确保正确执行
					//实现JS分享功能
					wx.onMenuShareTimeline({//1.获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
		                title: sharedTitle, // 分享标题
		                link: sharedLink, // 分享链接的
		                imgUrl: imageUrl, // 分享图标
		                success: function () { 
		                    // 用户确认分享后执行的回调函数
		                },
		                cancel: function () { 
		                    // 用户取消分享后执行的回调函数
		                }
		            });
		            wx.onMenuShareAppMessage({//2.获取“分享给朋友”按钮点击状态及自定义分享内容接口
		                title: sharedTitle, // 分享标题
		                desc: "口袋里的家庭医生", // 分享描述
		                link: sharedLink, // 分享链接
		                imgUrl: imageUrl, // 分享图标
		                type: '', // 分享类型,music、video或link，不填默认为link
		                dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		                success: function () { 
		                    // 用户确认分享后执行的回调函数
		                },
		                cancel: function () { 
		                    // 用户取消分享后执行的回调函数
		                }
		            });
				});
		    },  
		    error: function(res){
		    	 $("#pop").hide();
		     	 $("#Message").html("微信登录失败");
		     	 $('.errorhei').show();
		     	 $('.beijing').hide();
		    }  
		});	
    }
});


