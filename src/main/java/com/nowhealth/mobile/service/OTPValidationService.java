package com.nowhealth.mobile.service;


import java.util.Map;

import com.nowhealth.mobile.entity.OTPGeneration;


public interface OTPValidationService {
 public OTPGeneration templateSMS(String phoneNo,String orderNo,String querycode,OTPGeneration otpGeneration);
 //获取短信验证码
 public OTPGeneration getInfocode(String phoneNo,OTPGeneration otpGeneration);
 
 //发送提现通知短信
 public OTPGeneration getDrawalMoneyBody(Map<String,String> map,OTPGeneration otpGeneration);
}
