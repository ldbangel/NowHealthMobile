package com.nowhealth.mobile.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowhealth.mobile.dao.OTPGenerationMapper;
import com.nowhealth.mobile.dms.PERDataManageService;
import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.entity.OTPGeneration;
import com.nowhealth.mobile.entity.UserInfor;
import com.nowhealth.mobile.service.OTPValidationService;
import com.nowhealth.mobile.service.PaymentCompleteService;
import com.nowhealth.mobile.service.PersonInforService;

@Service("personInforService")
public class PersonInforServiceImpl implements PersonInforService{
	
	private static final Logger logger = Logger
			.getLogger(PersonInforServiceImpl.class);
	@Resource 
	private PERDataManageService dataManageService;
	@Resource
	private OTPGenerationMapper otpGenerationMapper;
	@Autowired
	private OTPValidationService ValidationService;
	@Autowired
	private PaymentCompleteService paymentService;
	/**
	 *提交首页，进入人员信息页面 
	 */
	public BaseInfor gotoPersonInfor(BaseInfor baseinfor,
			HttpServletRequest httprequest) {
        HttpSession session= httprequest.getSession();
        //绑定用户Id至baseinfor表
        UserInfor userInfor=null;
        String isagentshare = httprequest.getParameter("isagentshare");
        if(session.getAttribute("loginUser")!=null){
        	userInfor=(UserInfor)session.getAttribute("loginUser");
        	int userId = userInfor.getUserid();
        	baseinfor.setUserinforid(userId);
        }
        if(isagentshare !=null && isagentshare!=""){
        	baseinfor.setIsagentshare(Integer.parseInt(isagentshare));
		}else{
			baseinfor.setIsagentshare(Integer.parseInt("0"));
		}
		BaseInfor baseInfor=dataManageService.savefirstScreenData(baseinfor);
		//将首页中录入的信息存放入session中		
		session.setAttribute(baseinfor.getOrderno()+"baseinfor", baseinfor);
		return baseInfor;
	}
	/**
	 * 发送验证码到用户手机
	 */
	public OTPGeneration validateCode(String phoneNo, HttpServletRequest request) {
		OTPGeneration otpGeneration=new OTPGeneration();
		otpGeneration=ValidationService.getInfocode(phoneNo, otpGeneration);
		return otpGeneration;
	}
	/**
	 *校验用户录入的验证码是否正确，并修改验证码状态
	 */
	public String phoneCodeExist(String phoneNo, String checkCode,
			HttpServletRequest request) {
		Boolean exist=false;
		Map params=new HashMap();
		params.put("phoneno", phoneNo);
		params.put("dateTime", new Date());
		//查询该用户所有未过期的并且有效的验证码
		List<OTPGeneration> listOTP=otpGenerationMapper.listOTPGeneration(params);
		for(OTPGeneration otp:listOTP){
			if(checkCode.equals(otp.getValidationno())){
				exist=true;
				otp.setStatus("20");//验证成功,设置验证码状态为已用
				otpGenerationMapper.updateByPrimaryKey(otp);
			}
		}
		return exist.toString();
	}
	
	/**
	 *进入支付页面，存放人员信息页面的数据
	 */
	public BaseInfor savePersoninfo(BaseInfor baseinfor, UserInfor user,HttpServletRequest request) {
		String purchasename="";//投保人姓名
		String cardType="";   //投保人证件类型
		String passportID=""; //投保人护照号
		String purchaserID="";//投保人身份证号
		String cardId="";   //投保人证件号
		String gender ="";  //购买人性别
		String age ="";     //年龄
		Double high =0d;    //身高
		Double weight =0d;  //体重
		String isallergy =""; //是否有过敏史
		String isfamilyillness =""; //是否有家族病史
		String purchaseremail="";//投保人邮箱
		String purchaserphoneno="";//投保人手机号
		String effectiveDate="";//生效日期
		purchasename=baseinfor.getPurchasername();
		cardType=baseinfor.getCardStyle();
		effectiveDate=baseinfor.getEffectivedate();
		purchaserphoneno=baseinfor.getPurchaserphoneno();
		double totalamount=baseinfor.getTotalamount();
		purchaseremail=baseinfor.getPurchaseremail();
		/*==================新增字段======================*/
		//cardId =baseinfor.getCardID();   //护照号、性别、年龄 （证件类型必须选择护照）
		passportID = baseinfor.getPassportID();
		purchaserID = baseinfor.getPurchaserID();
		gender = baseinfor.getPurchaserSex();
		age  = baseinfor.getPurchaserAge();
		high = baseinfor.getPurchaserHigh();
		weight = baseinfor.getPurchaserweight();
		isallergy = baseinfor.getIsAllergy();
		isfamilyillness = baseinfor.getIsfamilyillness();
		/*===============================================*/
		BaseInfor base=null;
		int result=0;
		HttpSession session=request.getSession();
		String orderno=request.getParameter("orderno");
		if(!"".equals(orderno)&&null!=orderno){
			base=dataManageService.selelctByorder(orderno);
			//修改订单状态为20
			base.setOrderstart("20");
			base.setUpdatetime(null);
			base.setCreatetime(null);
			logger.info("修改订单状态为20.......");
			base.setTotalamount(totalamount);
			if(!"".equals(purchasename)&&purchasename!=null){
				base.setPurchasername(purchasename);
			}
			if(!"".equals(cardType)&&cardType!=null){
				base.setCardStyle(cardType);
			}
			if(!"".equals(effectiveDate)&&effectiveDate!=null){
				base.setEffectivedate(effectiveDate);
			}
			if(!"".equals(purchaserphoneno)&&purchaserphoneno!=null){
				base.setPurchaserphoneno(purchaserphoneno);
			}
			if(!"".equals(isallergy)&& isallergy!=null){
				base.setIsAllergy(isallergy);
			}
			if(!"".equals(isfamilyillness) && isfamilyillness!=null){
				base.setIsfamilyillness(isfamilyillness);
			}
			/*====================================================*/
			if(!"".equals(passportID)&& passportID!=null){
				base.setCardID(passportID);
			}
			//身份证号
			if(!"".equals(purchaserID)&& purchaserID!=null){
				base.setCardID(purchaserID);
				Calendar cal = Calendar.getInstance();
		        String year = purchaserID.substring(6, 10);
		        int iCurrYear = cal.get(Calendar.YEAR);
		        int iAge = iCurrYear - Integer.valueOf(year);
		        base.setPurchaserAge(String.valueOf(iAge));
			    //投保人性别（当前同车主）
		        String inssCardNum = purchaserID.substring(16, 17);
		        String inssGender="";
		        if (Integer.parseInt(inssCardNum) % 2 != 0) {
		            inssGender = "1";//男
		        } else {
		            inssGender = "0";//女
		        }
		        base.setPurchaserSex(inssGender);
			}
			
			if(!"".equals(gender)&& gender!=null){
				base.setPurchaserSex(gender);
			}
			if(!"".equals(age)&& age!=null){
				base.setPurchaserAge(age);
			}
			if(!"".equals(high)&& high!=null){
				base.setPurchaserHigh(high);
			}
			if(!"".equals(weight)&& weight!=null){
				base.setPurchaserweight(weight);
			}
			/*=====================================================*/
			if(!"".equals(purchaseremail)&& purchaseremail!=null){
				base.setPurchaseremail(purchaseremail);
			}
			//更新人员信息数据
			result=dataManageService.updatebasein(base);	
		}	
		if(result>0){
			//丁香园签名测试用例
			/*String dxyReturnCode = paymentService.getDxySign(base, request);
			if("200".equals(dxyReturnCode)){
				logger.info("丁香园签名成功！");
				baseinfor = paymentService.proceDxySuccessData(base.getOrderno(), request);
			}else{
				logger.info("丁香园签名失败，错误码为："+ dxyReturnCode);
			}*/
			logger.info("人员信息页面数据更新成功 结果为"+result+"对应订单为"+base.getOrderno());
			session.setAttribute(base.getOrderno()+"baseinfor", base);
		}
		return base;
		
	}

}
