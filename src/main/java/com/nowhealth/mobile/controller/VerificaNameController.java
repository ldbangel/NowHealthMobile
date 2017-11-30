package com.nowhealth.mobile.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowhealth.mobile.constants.LudiConstants;
import com.nowhealth.mobile.service.VerificationCadeService;

@Controller
@RequestMapping("/VerificaName")
public class VerificaNameController {
	private static final Logger logger = Logger
			.getLogger(WechatLoginController.class);
	@Resource
	private VerificationCadeService verificationCadeService;
	/**
	 * 实名验证四要素(验证卡号，户名，证件,手机号)
	 * @throws ParseException 
	 */
	@RequestMapping("/verifyCardNumber.do")
	//@ResponseBody
	public void verifyCardNumber(HttpServletRequest httpRequest,HttpServletResponse httpResponse) 
			throws ParseException{
		String result=verificationCadeService.userVeriCarid(httpRequest,httpResponse);
		try {
			httpResponse.setCharacterEncoding("utf-8");
			httpResponse.getWriter().print(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return result;
		
	}
	/**
	 * 测试输入的录入的账户是否是否与所选得银行类型一致
	 */
	@RequestMapping("/checkBankNumber.do")
	@ResponseBody
	public String testBankNumber(HttpServletRequest httpRequest,HttpServletResponse httpResponse){
		String isresult="";
		String banknameresult="";
		String result="";		
		String bankname=httpRequest.getParameter("bankname");
		banknameresult=verificationCadeService.testBank(httpRequest,httpResponse);
		if(banknameresult.length()==4){
			StringBuffer bank = new StringBuffer("中国");
			result=bank.append(banknameresult).toString();
		}
		if(bankname.equals(banknameresult) || result.equals(bankname)){
			isresult="success";
		}else{
			isresult="error";
		}
		return isresult;
		
	}
	
	/**
	 * 将提现密码更新进入数据库
	 */
	@RequestMapping("/addUserPass.do")
	@ResponseBody
	public String insertPass(HttpServletRequest httpRequest,HttpServletResponse httpResponse){
		String result=verificationCadeService.addPassword(httpRequest);
		return result;
		
	}
	
	/**
	 * 添加银行卡
	 */
	@RequestMapping("/addBankcard.do")
	public String addbank(HttpServletRequest httpRequest,HttpServletResponse httpResponse,ModelMap modelmap){
		String bankprop=httpRequest.getParameter("bankprop");
		String banknumber=httpRequest.getParameter("banknumber");
		modelmap.addAttribute("bankprop", bankprop);
		modelmap.addAttribute("banknumber", banknumber);
		return LudiConstants.VERIFICATCARDINF;
	}
	/**
	 * 设置密码
	 */
	@RequestMapping("/setPassword.do")
	public String setpass(HttpServletRequest httpRequest,HttpServletResponse httpResponse,ModelMap modelmap){
		String password=httpRequest.getParameter("payPassword_rsainput");
		modelmap.addAttribute("password", password);
		return LudiConstants.PASSWORDCONFIM;
	}
	
	
	
}
