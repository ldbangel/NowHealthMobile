package com.nowhealth.mobile.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.entity.OTPGeneration;


public interface PaymentCompleteService {
    //发送咨询码
	public OTPGeneration validateCode(String phoneNo,String orderNo,String querycode);
	
	public BaseInfor processPaymentSuccessData(String openId,HttpServletRequest request,HttpServletResponse response);
	
	public BaseInfor selectById(String orderno);
	
	public boolean versignature(HttpServletRequest request,HttpServletResponse response);
	
	public BaseInfor procePaymentSuccessData(HttpServletRequest request,HttpServletResponse response);

	public String getDxySign(BaseInfor baseInfor,HttpServletRequest request);
	
	public BaseInfor proceDxySuccessData(String orderNo,HttpServletRequest request);
}
