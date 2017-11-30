package com.nowhealth.mobile.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nowhealth.mobile.entity.BaseInfor;

public interface PaymentInforService {
	//选择银行支付接口
	public BaseInfor paymentApplication(HttpServletRequest httpRequest);
	
	public String parsePayQRcode(HttpServletRequest request) throws Exception;
	
	//微信公众号支付
	public Map<String,String> wechatPay(HttpServletRequest request);
	
	//代付生成二维码
	public String sweepPayQRcode(HttpServletRequest request);
	
	//扫码支付实际调用微信公众号支付实现
	public Map<String,String> wechatPayToSweep(HttpServletRequest request);
	
	//银行卡支付生成签名
	public String generateSignature(HttpServletRequest request,HttpServletResponse response);
	
	//向通联祖册用户获取userid
	
	public String getTltUserid(HttpServletRequest request,HttpServletResponse httResponse);
	
}
