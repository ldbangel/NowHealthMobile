package com.nowhealth.mobile.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.entity.PageBean;
import com.nowhealth.mobile.entity.UserInfor;

public interface MyAccountService {
	public Map<String,Object> getMyAccountInitData(HttpServletRequest request);
	
	public PageBean<BaseInfor> getCurrentOrders(HttpServletRequest request);
	
	public BaseInfor selectBaseInfor(HttpServletRequest request,String orderno);
	
	public Map<String,Object> getOrdersAllDetails(HttpServletRequest request,ModelMap modelMap);
	
	public Map<String,Object> getOrdersDetails(HttpServletRequest request);
	
	public Map<String,Object> getOrdersDetailsByDate(HttpServletRequest request);
	
	public String cashDrawal(HttpServletRequest request);
	
	public String validateUser(HttpServletRequest request);
	
	public String drawalAmount(HttpServletRequest request);
	
	public UserInfor selectUserInfor(HttpServletRequest request);
	
	public int cancelOrder(HttpServletRequest request);

}
