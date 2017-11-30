package com.nowhealth.mobile.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowhealth.mobile.constants.LudiConstants;
import com.nowhealth.mobile.dao.BaseInforMapper;
import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.entity.OTPGeneration;
import com.nowhealth.mobile.entity.UserInfor;
import com.nowhealth.mobile.service.PersonInforService;
import com.nowhealth.mobile.utils.DateFormatUtils;



@Controller
@RequestMapping("personController")
public class PersonInforController {
	private static final Logger logger = Logger.getLogger(PersonInforController.class);
	
	@Resource
	private PersonInforService personInforService;
	@Resource
	private BaseInforMapper baseInforMapper;
	/**
	 *提交首页，进入人员信息页面
	 */
	@RequestMapping("/goTopersonInfo.do")
	public String goTopersonInfo(BaseInfor baseInfor,ModelMap modelMap,HttpServletRequest httprequest){
		BaseInfor baseinfor = personInforService.gotoPersonInfor(baseInfor, httprequest);
		modelMap.addAttribute("baseinfor", baseinfor);
		return LudiConstants.PERSONINFORS;
	}
	/**
	 * 判断，购买获取验证码的时候一个手机号码只能有一个订单为已支付
	 */
	@RequestMapping("/selectByPurchaserPhoneNo.do")
	@ResponseBody
	public String selectByPurchaserPhoneNo(HttpServletRequest request){
		String phoneNo= request.getParameter("phoneNo");
		String result="";        
		String effectivedate=""; //订单生效时间
		int returnDays = 0;      //年限
		String orderNo="";       //订单号
		try {
		 String nowDate = DateFormatUtils.getSystemDateByYmd();  //当前时间
		 List<BaseInfor> baseInfor = baseInforMapper.selectByPurchaserPhoneNo(phoneNo);
		 //判断该手机号1年内只能有一单已支付订单(人员信息页面填写的手机号)
		 if(baseInfor.size()>0 ){
		   if(baseInfor.size()==1){
				for(BaseInfor base : baseInfor){
					effectivedate = base.getEffectivedate();
					orderNo = base.getOrderno();
				}
				returnDays = DateFormatUtils.compareDate(effectivedate,nowDate,2);
				if(returnDays < 1){  //当购买订单小于1年不可重复购买
					logger.info("已有订单,订单号为"+orderNo+"请勿重复购买！"); 
				    result="0";
				}else{
					logger.info("已有订单，但过有效期可以继续购买！"); 
				    result="1";
				}
		   }else{
			   logger.info("已有订单,请勿重复购买！"); 
			   result="0";
		   }
		 }else{
			 logger.info(" 订单首次购买！");
			 result ="1";  //1成功 0失败
		 }
		} catch (ParseException e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.info("订单验证失败！"+str);
		}
		return result;
	}
	
	/**
	 *发送验证码到用户手机
	 */
	@RequestMapping("/phoneCheck.do")
	@ResponseBody
	public OTPGeneration phoneCheck(String phoneno,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String phoneNo= request.getParameter("phoneNo");
		OTPGeneration Generation=new OTPGeneration();
		try {
		   Generation=personInforService.validateCode(phoneNo,request);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.info("用户注册发送验证码异常"+str);
		}
		return Generation;
	}
	
	/**
	 * 校验手机验证码
	 */
	@RequestMapping("checkPhoneCode.do")
	@ResponseBody
	public String checkPhoneCode(HttpServletRequest request,String phoneno,String checkCode){
		String codeExist="";
		String phoneNo = request.getParameter("phoneNo");
		try {
			codeExist=personInforService.phoneCodeExist(phoneNo,checkCode,request);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.info("用户校验手机验证码异常"+str);
		}
		return codeExist;
	}
	
	/**
	 * 提交人员信息，进入支付页面
	 */
	@RequestMapping("/goTopayinfor.do")
	public String gotoPayinfor(BaseInfor baseinfor,UserInfor userInfor,ModelMap modelmap,HttpServletRequest httprRequest){
		BaseInfor baseinfo=personInforService.savePersoninfo(baseinfor, userInfor,httprRequest);
		modelmap.addAttribute("baseinfor", baseinfo);
		return LudiConstants.PAYMENTINFOR;
		
	}
}
