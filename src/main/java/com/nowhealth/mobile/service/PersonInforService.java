package com.nowhealth.mobile.service;

import javax.servlet.http.HttpServletRequest;

import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.entity.OTPGeneration;
import com.nowhealth.mobile.entity.UserInfor;


public interface PersonInforService {

	//提交首页，进入人员信息页面
	BaseInfor gotoPersonInfor(BaseInfor baseinfor,HttpServletRequest httprequest);
	//发送验证码到用户手机
	public OTPGeneration validateCode(String phoneNo,HttpServletRequest request);
	//校验用户录入的验证码
	public String phoneCodeExist(String phoneNo,String checkCode,HttpServletRequest request);
	//进入支付页面，存入人员信息页面数据
	public BaseInfor savePersoninfo(BaseInfor baseinfor,UserInfor user,HttpServletRequest request);
	
	
}
