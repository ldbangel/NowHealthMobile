package com.nowhealth.mobile.serviceImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.DigestException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowhealth.mobile.dao.BaseInforMapper;
import com.nowhealth.mobile.dms.LoginManageService;
import com.nowhealth.mobile.dms.PERDataManageService;
import com.nowhealth.mobile.dms.PaymentCompleteDataManageService;
import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.entity.OTPGeneration;
import com.nowhealth.mobile.entity.UserInfor;
import com.nowhealth.mobile.payUtils.SybUtil;
import com.nowhealth.mobile.service.OTPValidationService;
import com.nowhealth.mobile.service.PaymentCompleteService;
import com.nowhealth.mobile.utils.DateFormatUtils;
import com.nowhealth.mobile.utils.HttpRequestUtility;
import com.nowhealth.mobile.utils.JsonUtility;
import com.nowhealth.mobile.utils.RandomValidateCode;
import com.nowhealth.mobile.utils.SHA1utils;
import com.nowhealth.mobile.utils.SysConstantsConfig;

@Service
public class PaymentCompleteServiceimpl implements PaymentCompleteService {
	
	private static final Logger logger = Logger.
			getLogger(PaymentCompleteServiceimpl.class);
	@Autowired
	private OTPValidationService ValidationService;
	@Autowired
	private PaymentCompleteDataManageService paymentComManageService;
	@Resource 
	private PERDataManageService dataManageService;	
	@Resource
	private BaseInforMapper baseinforMapper;
	@Resource
	private LoginManageService loginManageService;

	//发送短信
	public OTPGeneration validateCode(String phoneNo,String orderNo, String querycode) {
		OTPGeneration otpGeneration=new OTPGeneration();
		ValidationService.templateSMS(phoneNo,orderNo, querycode,otpGeneration);
		return otpGeneration;
	}
	
	/**
	 * 跳转到支付成功界面，修改订单状态和支付状态
	 */
	@Override
	public BaseInfor processPaymentSuccessData(String openId,HttpServletRequest httpRequest,
			HttpServletResponse response) {
		//获取交易完成返回回来订单号
		String orderno=httpRequest.getParameter("cusorderid");
		//String orderno = "LW201708160000056004";   //测试数据
		logger.info("交易完成返回的订单号："+orderno);
		BaseInfor baseinfor=new BaseInfor();
		logger.info("查询订单开始");
		baseinfor=baseinforMapper.selectByOrderno(orderno);
		logger.info("查询订单结束");
		baseinfor.setPaymentstatus("20");
		baseinfor.setOrderno(orderno);
		logger.info("设置支付状态为20......");
		int result=paymentComManageService.updatePaymentAndBaseinfor(openId,baseinfor,httpRequest);
		if(result>0){
			baseinfor=dataManageService.selelctByorder(orderno);
			/*HttpSession session=httpRequest.getSession();
			session.setAttribute(baseinfor.getOrderno()+"baseinfor", baseinfor);*/
		}
		return baseinfor;
	}

	//根據訂單查詢
	public BaseInfor selectById(String orderno) {
		return baseinforMapper.selectByOrderno(orderno);
	}

	//银行卡支付验证签名
	public boolean versignature(HttpServletRequest request,
			HttpServletResponse response) {
		//接收Server返回的支付结果
		String merchantId=request.getParameter("merchantId");//与提交订单时候的商户号保持一致
		String version=request.getParameter("version");//v1.0
		String language=request.getParameter("language");//网页显示语言种类，1为中文
		String signType=request.getParameter("signType");//签名，与提交订单时候的签名保持一致
		String payType=request.getParameter("payType");//与提交订单时候的支付方式一致
		//String issuerId=request.getParameter("issuerId");//发卡方机构代码（固定为空
		String paymentOrderId=request.getParameter("paymentOrderId");//通联订单号
		String orderNo=request.getParameter("orderNo");//与提交订单时候的商户订单号保持一致
		String orderDatetime=request.getParameter("orderDatetime");//与提交订单时候商户订单提交时间保持一致
		String orderAmount=request.getParameter("orderAmount");//商户订单金额
		String payDatetime=request.getParameter("payDatetime");//支付完成时间
		String payAmount=request.getParameter("payAmount");//订单的实际支付金
		String ext1=request.getParameter("ext1");
		/*String ext2=request.getParameter("ext2");*/
		String payResult=request.getParameter("payResult");//处理结果，1.代表支付成功（仅在成功时候通知商户）
		//String errorCode=request.getParameter("errorCode");//固定为空
		String returnDatetime=request.getParameter("returnDatetime");//系统返回支付结果时间
		//获取到返回的签名信息
		String signMsg=request.getParameter("signMsg");
		String signa="";
		boolean isfalg=false;
		if(!"".equals(signType) && signType.equals("0")){//采用md5验证签名
			String param = "merchantId="+merchantId+"&version="+version+"&language="
					+language+"&signType="+signType+"&payType="+payType+"&paymentOrderId="+paymentOrderId+"&orderNo="+orderNo+"&orderDatetime="+orderDatetime+"&orderAmount="+orderAmount
					+"&payDatetime="+payDatetime+"&payAmount="+payAmount+"&ext1="+ext1+"&payResult="+payResult+"&returnDatetime="+returnDatetime;
			String dataString = param+"&key="+"1234567890";
			logger.info("签名原串为："+dataString);
			signa=SybUtil.Md5Encode(dataString.toString());//记得是md5编码的加签
			//如果验证签名成功，并且支付状态为1，（代表支付成功，那么进行后台业务逻辑处理）
			if(signa.equals(signMsg) && "1".equals(payResult)){
				logger.info("快捷支付成功，并且签名成功");
				isfalg=true;
			}else{
				if(signa.equals(signMsg)){
					logger.info("签名验证成功，支付失败！");
				}else if( "1".equals(payResult)){
					logger.info("支付成功，签名验证失败！");
				}else {
					logger.info("支付和验证签名失败！");
				}
				
			}
		}
		
		return isfalg;
	}
	
	/**
	 * 跳转到支付成功界面，修改订单状态和支付状态
	 */
	@Override
	public BaseInfor procePaymentSuccessData(HttpServletRequest httpRequest,
			HttpServletResponse response) {
		//获取交易完成返回回来订单号
		String orderno=httpRequest.getParameter("orderNo");
		//String orderno = "LW201708160000056004";   //测试数据
		logger.info("交易完成返回的订单号："+orderno);
		//HttpSession session=httpRequest.getSession();
		BaseInfor baseinfor=new BaseInfor();		
		baseinfor=baseinforMapper.selectByOrderno(orderno);
		baseinfor.setPaymentstatus("20");
		baseinfor.setOrderno(orderno);
		logger.info("设置支付状态为20......");
		UserInfor user=loginManageService.selectByID(baseinfor.getUserinforid());
		String openId=user.getUsername();
		int result=paymentComManageService.updatePaymentAndBaseinfor(openId,baseinfor,httpRequest);
		if(result>0){
			baseinfor=dataManageService.selelctByorder(orderno);
			//session.setAttribute(baseinfor.getOrderno()+"baseinfor", baseinfor);
		}
		return baseinfor;
		
	}
	
    /**
     * 丁香园签名
     */
	@Override
	public String getDxySign(BaseInfor baseInfor, HttpServletRequest request) {
		logger.info("丁香园签名开始.....");
		String returnCode="";  //返回码
		RandomValidateCode random = new RandomValidateCode();
		//获取签名参数
		String name = baseInfor.getPurchasername();          //购买人姓名
		int sex = Integer.parseInt(baseInfor.getPurchaserSex());    //性别
		int age = Integer.parseInt(baseInfor.getPurchaserAge());    //年龄
		String id_number = baseInfor.getCardID();            //身份证号
		String cellphone= baseInfor.getPurchaserphoneno();   //手机号
		int weight=(int)baseInfor.getPurchaserweight();      //体重
		int height=(int)baseInfor.getPurchaserHigh();        //身高
		String email= baseInfor.getPurchaseremail();         //email
		String allergic_history = baseInfor.getIsAllergy().equals("0")?"否":"是";  //过敏史
		String family_medical_history = baseInfor.getIsfamilyillness().equals("0")?"否":"是"; //家族病史
		int timestamp = (int)System.currentTimeMillis();     //时间戳
		String nonce = random.get16RandomString();           //获取16位随机字符串
		String sign="";  //签名结果
		Map<String,Object> maps = new HashMap<String,Object>();
		//签名参数
		maps.put("appId", SysConstantsConfig.DXY_APPID);
		maps.put("appSignKey", SysConstantsConfig.DXY_APPSIGNKEY);
		maps.put("nonce", nonce);
		maps.put("timestamp", timestamp);
		maps.put("name", name);
		maps.put("sex", sex);
		maps.put("age", age);
		maps.put("id_number", id_number);
		maps.put("cellphone", cellphone);
		maps.put("weight", weight);
		maps.put("height", height);
		maps.put("email", email);
		maps.put("allergic_history", allergic_history);
		maps.put("family_medical_history", family_medical_history);
		try {
			sign = SHA1utils.SHA1(maps);   //签名结果
			logger.info("签名为 ："+sign);
		} catch (DigestException e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.info("获取丁香园签名异常"+str);
		}
		//请求参数
		String requestxml="sign="+sign+"&appId="+SysConstantsConfig.DXY_APPID+"&timestamp="+timestamp
				+"&nonce="+nonce+"&name="+name+"&sex="+sex+"&age="+age+"&id_number="+id_number
				+"&cellphone="+cellphone+"&weight="+weight+"&height="+height+"&email="+email
				+"&allergic_history="+allergic_history+"&family_medical_history="+family_medical_history;
		logger.info("请求参数为"+requestxml);
		String responseJson = HttpRequestUtility.sendPost(SysConstantsConfig.DXY_URL, requestxml);
		if(!"".equals(responseJson)){
		     returnCode= JsonUtility.getVauleFromJson("returnCode", responseJson);
		     logger.info("请求返回的状态码 :"+returnCode);
		}
		
		return returnCode;
	}

	/**
	 * 发送丁香园签名成功后，更新订单状态为50
	 */
	@Override
	public BaseInfor proceDxySuccessData(String orderNo,HttpServletRequest request) {
		logger.info("交易订单号为： "+orderNo);
		//HttpSession session=request.getSession();
		int updateResult=-1;
		BaseInfor baseinfor=null;		
		baseinfor=baseinforMapper.selectByOrderno(orderNo);
		baseinfor.setOrderstart("50");
		logger.info("设置订单状态为：50 .....");
		updateResult = paymentComManageService.updateBasebyfromDxy(baseinfor, request);
		if(updateResult >0){
			baseinfor=dataManageService.selelctByorder(orderNo);
			//session.setAttribute(baseinfor.getOrderno()+"baseinfor", baseinfor);
		}
		return baseinfor;
	}
}
