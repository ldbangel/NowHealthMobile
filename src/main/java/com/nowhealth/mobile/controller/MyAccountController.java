package com.nowhealth.mobile.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowhealth.mobile.constants.LudiConstants;
import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.entity.PageBean;
import com.nowhealth.mobile.entity.UserInfor;
import com.nowhealth.mobile.service.MyAccountService;
import com.nowhealth.mobile.service.VerificationCadeService;

@Controller
@RequestMapping("myAccount")
public class MyAccountController {
	private static final Logger logger = Logger.getLogger(MyAccountController.class);
	@Resource
	private MyAccountService myAccountService;
	@Resource
	private VerificationCadeService verificationCadeService;
	
	/**
	 * MyAccount初始化
	 * @param request
	 * @return
	 */
	@RequestMapping("myAccountInit.do")
	@ResponseBody
	public Map<String,Object> initMyAccount(HttpServletRequest request){
		logger.info("My Account init start!");
		Map<String,Object> map = myAccountService.getMyAccountInitData(request);
		return map;
	}
	
	//分页查询
	@RequestMapping("getPageOrders.do")
	@ResponseBody
	public PageBean<BaseInfor> selectCurrentPageOrders(HttpServletRequest request){
		logger.info("My Account get current page data start!");
		PageBean<BaseInfor> page = myAccountService.getCurrentOrders(request);
		return page;
	}
	
	//我的订单里面没支付的订单----继续支付
	@RequestMapping("continuePay.do")
	public String continuePay(HttpServletRequest request,ModelMap modelMap){
		String orderNo = request.getParameter("orderno");
		BaseInfor baseinfor = myAccountService.selectBaseInfor(request,orderNo);
		modelMap.addAttribute("baseinfor", baseinfor);
		return LudiConstants.PAYMENT;
	}
	
	//未支付的订单----取消订单
	@RequestMapping("cancelOrder.do")
	@ResponseBody
	public String cancelOrder(HttpServletRequest request){
		String result;
		int num = myAccountService.cancelOrder(request);
		if(num>=1){
			result = "success";
		}else{
			result = "fail";
		}
		return result;
	}
	
	//订单佣金总明细查询
	@RequestMapping("AllOrderSearch.do")
	@ResponseBody
	public Map<String,Object> selectOrdersDetails(HttpServletRequest request, ModelMap modelMap){
		logger.info("My Account get orderDetails Alldata start!");
		Map<String,Object> map = myAccountService.getOrdersAllDetails(request,modelMap);
		return map;
	}
	
	//订单佣金页面明细查询
	@RequestMapping("OrderSearch.do")
	@ResponseBody
	public Map<String,Object> selectOrdersDetails1(HttpServletRequest request){
		logger.info("My Account get orderDetails data start!");
		Map<String,Object> map = myAccountService.getOrdersDetails(request);
		return map;
	}
	
	//提现页面跳转
	@RequestMapping("cashDrawal.do")
	@ResponseBody
	public String cashDrawal(HttpServletRequest request,ModelMap modelMap){
		String ressult =  myAccountService.cashDrawal(request);
		return ressult;
	}
	
    //验证银卡信息
	@RequestMapping("validateUser.do")
	@ResponseBody
	public String validateUser(HttpServletRequest request,ModelMap modelMap){
		String ressult =  myAccountService.validateUser(request);
		return ressult;
	}
	
	//佣金提现
	@RequestMapping("drawal_amount.do")
	@ResponseBody
	public String drawal_amount(HttpServletRequest request,ModelMap modelMap,
			HttpServletResponse httrResponse) throws UnsupportedEncodingException{
		String result =  myAccountService.drawalAmount(request);
		return result;
	}
	
	//根据选定时间查询佣金明细
	@RequestMapping("OrderSearchByDate.do")
	@ResponseBody
	public Map<String,Object> selectOrdersDetailsByDate(HttpServletRequest request){
		logger.info("My Account get orderDetails data by Date start!");
		Map<String,Object> map = myAccountService.getOrdersDetailsByDate(request);
		return map;
	}
	
	//查询用户的账户信息
	@RequestMapping("getUserInfor.do")
	@ResponseBody
	public UserInfor selectUserInfor(HttpServletRequest request){
		logger.info("My Account get orderDetails data by Date start!");
		UserInfor user = myAccountService.selectUserInfor(request);
		return user;
	}
}
