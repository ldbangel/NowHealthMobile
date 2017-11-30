package com.nowhealth.mobile.serviceImpl;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowhealth.mobile.VerifiUtil.CallTlt;
import com.nowhealth.mobile.VerifiUtil.TltSignatureUtill;
import com.nowhealth.mobile.VerifiUtil.VerificBankNumber;
import com.nowhealth.mobile.constants.LudiConstants;
import com.nowhealth.mobile.dms.LoginManageService;
import com.nowhealth.mobile.entity.OTPGeneration;
import com.nowhealth.mobile.entity.UserCommssionInfor;
import com.nowhealth.mobile.entity.UserInfor;
import com.nowhealth.mobile.service.LoginService;
import com.nowhealth.mobile.service.OTPValidationService;
import com.nowhealth.mobile.service.VerificationCadeService;
import com.nowhealth.mobile.utils.DateFormatUtils;
import com.nowhealth.mobile.utils.DesUtil;
import com.nowhealth.mobile.utils.HttpRequestUtility;
import com.nowhealth.mobile.utils.JsonUtility;
import com.nowhealth.mobile.utils.StringUtils;
import com.nowhealth.mobile.utils.SysConstantsConfig;

@Service("verificationCadeService")
public class VerificationCadeServiceImpl implements VerificationCadeService{
	private static final Logger logger = Logger.getLogger(OTPValidationServiceimpl.class);
	@Resource
	private LoginManageService loginManageService;
	@Resource
	private OTPValidationService ValidationService;
	@Resource
	private LoginService loginService;
	
	/**
	 *	四要素验证
	 * @throws ParseException 
	 */
	public String userVeriCarid(HttpServletRequest httpRequest,
			HttpServletResponse httprespon) throws ParseException{
		HttpSession session=httpRequest.getSession();
		UserInfor userinfor = (UserInfor) session.getAttribute("loginUser");
		logger.info("验证卡号证件开始,用户的id为...."+userinfor.getUserid());
		
		String payeeid=httpRequest.getParameter("payeeid");//身份证号
		String banknumber=httpRequest.getParameter("banknumber");//银行卡号
		String username = httpRequest.getParameter("payee");//账户名
		String userphoneno=httpRequest.getParameter("userphoneno");//手机号
		String cashDealpass=httpRequest.getParameter("cashDealpass");//提现密码
		String bankprop=httpRequest.getParameter("bankprop");//银行代码属性
		String result="";
		String errorMag=""; 
		int updateresult=-1;
		UserInfor user = new UserInfor();
		String timestamp = DateFormatUtils.getSystemDateByYYYYMMDD();
		//添加校验四要素次数
		int verificNumber=0;
		int verifictotalNumber=0;
		String verificTime="";
		String StringNowDate="";
		StringNowDate=DateFormatUtils.getSystemDate();//獲取當前時間的字符串類型
		/*Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		Date time=cal.getTime();
		String yestady=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time);
		String yestaday=yestady.substring(0, 10);//獲取昨天的日期*/
		String nowdate=StringNowDate.substring(0, 10);//獲取當前日期
		Map<String,Object> map=new HashMap<String,Object>();
		/*Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("verifictime", yestaday);
		map1.put("userid", userinfor.getUserid());*/
		map.put("verifictime", nowdate);
		map.put("userid" ,userinfor.getUserid());
		
		UserInfor user1=loginManageService.selectBytime(map);//查詢出今天四要素驗證次數
		UserInfor user2=loginManageService.selectBytime2(map);//查詢出昨天四要素驗證次數
		boolean isfalg=false;
		if(user1==null && user2==null){//如果昨天和今天都為null;
			verificNumber=1;
			verifictotalNumber=1;
			verificTime=StringNowDate;
			isfalg=true;
		}else if(user2!=null){//如果查詢出昨天不為空
			verifictotalNumber=user2.getVarificTotalNumber()+1;
			verificNumber=1;
			verificTime=StringNowDate;
			isfalg=true;
		}else if(user1!=null && user1.getVerificNumber()<5){
			verifictotalNumber=user1.getVarificTotalNumber()+1;
			verificNumber=1+user1.getVerificNumber();
			verificTime=StringNowDate;
			isfalg=true;
		}else if(user1!=null && user1.getVerificNumber()>=5){//數據不更新，單日驗證已經超個上限
			isfalg=false;
			result="对不起，单日验证次数不能超过五次!!";
		}
		
		if(isfalg){
	        //获取请求报文（此时SIGNED_MSG为空）
			String param = "acctname="+username+"&acctno="+banknumber+"&certno="+payeeid+"&merchantno="
					+SysConstantsConfig.VERIFY_MERCHANT_ID+"&phone="+userphoneno+"&requestno="+timestamp+"&verifytype=0040";
			String dataString = param+"&key="+SysConstantsConfig.VERIFY_KEY;
			logger.info("签名前字符串为："+dataString);
			//MD5签名
			String signeture = null;
			try {
				signeture = StringUtils.MD5(dataString.getBytes("utf-8")).toUpperCase();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			logger.info("签名后的字符串： " + signeture);
			String reqString = param+"&sign="+signeture;
			logger.info("请求字符串："+reqString);
			String responseJson = HttpRequestUtility.sendPost(SysConstantsConfig.VERIFY_URL, reqString);
			String returnCode = JsonUtility.getVauleFromJson("retcode", responseJson);
			errorMag = JsonUtility.getVauleFromJson("retmsg", responseJson);		
			if(returnCode.equals(LudiConstants.TLT_VER_RET_CODE_STATE)){    
				user.setUserid(userinfor.getUserid());//先给定指定值，后面丛session登录对象中获取
				try {
					user.setBanknumber(DesUtil.encrypt(banknumber));
					user.setPayeeid(payeeid);//收款人身份证
					user.setPayee(DesUtil.encrypt(username));//账户名
					user.setBankphoneno(userphoneno);//银行预留手机号
					//user.setCashDealpass(DesUtil.encrypt(cashDealpass));//提现密码
					user.setBankprop(bankprop);//银行代码属性
					user.setVerificNumber(verificNumber);
					user.setVerificTime(verificTime);
					user.setVarificTotalNumber(verifictotalNumber);
				} catch (Exception e) {
					e.printStackTrace();
				}//银行卡号
				updateresult=loginManageService.updateuserinfor(user);
				try {
					userinfor =loginManageService.selectByName(user);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(updateresult>0){						
					result="success";
				}else{
					result="数据更新失败";
				}
			}else{
				try {
					user.setUserid(userinfor.getUserid());
					user.setVerificNumber(verificNumber);
					user.setVerificTime(verificTime);
					user.setVarificTotalNumber(verifictotalNumber);
					updateresult=loginManageService.updateuserinfor(user);
					userinfor =loginManageService.selectByName(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result = errorMag;
			}
			
			if(result.equals("success")){
				session.setAttribute("loginUser", userinfor);
			}
			if("".equals(result)){
				result=errorMag;
			}
		}
		return result;
	}
	
	/**
	 * 佣金提现，调TLT的代付接口
	 * @param request
	 * @param response
	 * @return
	 */
	public String commissionCashWithdraw(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfor userinfor=(UserInfor) session.getAttribute("loginUser");
		logger.info("佣金提现开始,用户的id为...."+userinfor.getUserid());
		
		String banknumber = null;
		String username = null;
		String userphoneno = null;
		try {
			banknumber = DesUtil.decrypt(userinfor.getBanknumber());
			username = DesUtil.decrypt(userinfor.getPayee()); //账户名
			userphoneno = userinfor.getBankphoneno(); //手机号
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}//银行卡号
		String amount = request.getParameter("amount"); //金额
		String bankprop=request.getParameter("bankprop");//银行代码属性
		String signeture="";
		String requestxml="";
		String result="";
		try {
			String timestamp = DateFormatUtils.getSystemDateByYYYYMMDD();//时间戳			
			//获取请求报文（此时SIGNED_MSG为空）
			requestxml = getDaifuBody(timestamp,signeture,banknumber,username,amount,userphoneno,bankprop);
			//从请求报文中获取交易批次号
			int reqStart = requestxml.lastIndexOf("<REQ_SN>");
			int reqEnd = requestxml.lastIndexOf("</REQ_SN>");
			String REQ_SN = requestxml.substring(reqStart + 8, reqEnd);
			//生成含有（SIGNED_MSG的请求报文）
			requestxml = signMsg(requestxml);
			logger.info("组装签名后的报文为 "+requestxml);
			//通联通账户实名签证，获取响应报文
			CallTlt callTlt = new CallTlt();
			String responsexml = callTlt.send(SysConstantsConfig.TLT_VER_URL, requestxml);
			logger.info("返回的响应报文结果为"+responsexml);
			//解析响应报文，
			//Map<String,String> map=ProcessData.getTltReturnCode(responsexml);
			//获取API验签原串，和返回的状态码
			//String returnSign=map.get("signature");
			//String returnCode=map.get("returnCode");
			//截取返回响应报文,判断提交是否成功
			int iStart = responsexml.lastIndexOf("<RET_CODE>");
			int end = responsexml.lastIndexOf("</RET_CODE>");
			int errStart = responsexml.lastIndexOf("<ERR_MSG>");
			int errend = responsexml.lastIndexOf("</ERR_MSG>");	
			//获取返回的状态值，信息值
			String returnCode = responsexml.substring(iStart + 10, end);
			String errorMag = responsexml.substring(errStart + 9, errend);
			//使用请求报文验证签名
			boolean isfalag = verifyMsg(responsexml,SysConstantsConfig.TLT_CER_PATH,false);
			//如果验证签名通过，将用户账号信息更新进入数据库,(需要加密)
			int updateresult=-1;
			UserCommssionInfor commissionInfor = new UserCommssionInfor();
			if(isfalag){
				if(returnCode.equals(LudiConstants.TLT_VER_RET_CODE_STATE)){
					commissionInfor.setUserid(userinfor.getUserid());
					commissionInfor.setAmount(Double.parseDouble(amount)/100);
					commissionInfor.setBankcard(banknumber);
					commissionInfor.setDrawalstate("1");
					commissionInfor.setPhoneno(userinfor.getBankphoneno());
					commissionInfor.setTransacNumber(REQ_SN);
					Map<String,String> draMap = new HashMap<String, String>();
					OTPGeneration otpGeneration=new OTPGeneration();
					String userName ="";    
					updateresult = loginManageService.insertUserCommissionInfor(commissionInfor);
					int userId = userinfor.getUserid();
					logger.info("当前提现用户的Id: " + userId);
					/*userinfor=loginService.selectUserById(userId); //获取用户信息
					if(userinfor!=null){
						userName = userinfor.getNickname(); //提款人
					}*/
					//提现成功发送提现通知至lenny
					if(updateresult>0){
						 result="1";
						 draMap.put("phoneNo", "15013727214"); 
						 draMap.put("userName", String.valueOf(userId));   
						 draMap.put("amount",String.valueOf(Double.parseDouble(amount)/100) );
						 ValidationService.getDrawalMoneyBody(draMap, otpGeneration);  //发送提现通知短信to Lenny
					}else{
						result="2";
					}
				}else{
					//如果不成功，也将数据添加进入数据库，将提现状态set为0;
					commissionInfor.setUserid(userinfor.getUserid());
					commissionInfor.setAmount(Double.parseDouble(amount)/100);
					commissionInfor.setBankcard(banknumber);
					commissionInfor.setDrawalstate("0");
					commissionInfor.setPhoneno(userinfor.getBankphoneno());
					commissionInfor.setTransacNumber(REQ_SN);
					updateresult = loginManageService.insertUserCommissionInfor(commissionInfor);
					result=errorMag;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取请求验证卡号模板body
	 */
	public String getVerifyBody(String phone,String username,String time,String payeeid,String banknumber,String sineture,String bankprop){
		String body=(new StringBuilder("<?xml version='1.0' encoding='GBK'?><AIPG>")
			.append("<INFO>")
				.append("<TRX_CODE>").append("211004").append("</TRX_CODE>")//交易代码
				.append("<VERSION>").append("03").append("</VERSION>")//版本
				.append("<DATA_TYPE>").append("2").append("</DATA_TYPE>")//数据格式(2代表xml
				.append("<USER_NAME>").append(SysConstantsConfig.TLT_VER_NAME).append("</USER_NAME>")//用户名
				.append("<USER_PASS>").append(SysConstantsConfig.TLT_VER_PASSWORD).append("</USER_PASS>")//密码
				.append("<REQ_SN>").append("LYZ_"+time).append("</REQ_SN>")//交易批次号//LYZ_20170804_095822_230
				.append("<SIGNED_MSG>").append(sineture).append("</SIGNED_MSG>")//数据签名
				.append("<LEVEL>").append("5").append("</LEVEL>")//处理级别N(1)0-9  0优先级最低，默认为5
			.append("</INFO>")
			.append("<VALIDR>")
				.append("<MERCHANT_ID>").append(SysConstantsConfig.MERCHANT_ID).append("</MERCHANT_ID>")//商户代码
				.append("<SUBMIT_TIME>").append(time).append("</SUBMIT_TIME>")//提交时间
				.append("<BANK_CODE>").append(bankprop).append("</BANK_CODE>")//银行代码
				.append("<ACCOUNT_NO>").append(banknumber).append("</ACCOUNT_NO>")//账号
				.append("<ACCOUNT_NAME>").append(username).append("</ACCOUNT_NAME>")//账户名
				.append("<ACCOUNT_PROP>").append("0").append("</ACCOUNT_PROP>")//账号属性
				.append("<ID_TYPE>").append("0").append("</ID_TYPE>")//开户证件类型
				.append("<ID>").append(payeeid).append("</ID>")//证件号
				.append("<TEL>").append(phone).append("</TEL>")//手机号
			.append("</VALIDR>")
		.append("</AIPG>")).toString();
		logger.info("组装报文的Boy： "+body);
		return body;	
	}
	
	/**
	 * 获取实时代付的发送报文xml字符串
	 * @return
	 */
	public String getDaifuBody(String time,String signeture,
			String banknumber,String username,String amount,String phoneNo,String bankprop){
		String body=(new StringBuilder("<?xml version='1.0' encoding='GBK'?><AIPG>")
			.append("<INFO>")
				.append("<TRX_CODE>").append("100014").append("</TRX_CODE>")//交易代码
				.append("<VERSION>").append("03").append("</VERSION>")//版本
				.append("<DATA_TYPE>").append("2").append("</DATA_TYPE>")//数据格式(2代表xml
				.append("<USER_NAME>").append(SysConstantsConfig.TLT_VER_NAME).append("</USER_NAME>")//用户名
				.append("<USER_PASS>").append(SysConstantsConfig.TLT_VER_PASSWORD).append("</USER_PASS>")//密码
				.append("<REQ_SN>").append("LYZ_"+time).append("</REQ_SN>")//交易批次号//LYZ_20170804_095822_230
				.append("<SIGNED_MSG>").append(signeture).append("</SIGNED_MSG>")//数据签名
				.append("<LEVEL>").append("5").append("</LEVEL>")//处理级别N(1)0-9  0优先级最低，默认为5
			.append("</INFO>")
			.append("<TRANS>")
				.append("<BUSINESS_CODE>").append(SysConstantsConfig.BUSINESS_CODE).append("</BUSINESS_CODE>") //业务代码
				.append("<MERCHANT_ID>").append(SysConstantsConfig.MERCHANT_ID).append("</MERCHANT_ID>")//商户代码
				.append("<SUBMIT_TIME>").append(time).append("</SUBMIT_TIME>")//提交时间
				.append("<BANK_CODE>").append(bankprop).append("</BANK_CODE>")//银行代码
				.append("<ACCOUNT_NO>").append(banknumber).append("</ACCOUNT_NO>")//账号
				.append("<ACCOUNT_NAME>").append(username).append("</ACCOUNT_NAME>")//账户名
				.append("<ACCOUNT_PROP>").append("0").append("</ACCOUNT_PROP>")//账号属性
				.append("<AMOUNT>").append(amount).append("</AMOUNT>") //金额
				.append("<CURRENCY>").append("CNY").append("</CURRENCY>") //货币类型
				.append("<TEL>").append(phoneNo).append("</TEL>")//手机号
			.append("</TRANS>")
		.append("</AIPG>")).toString();
		logger.info("组装报文的Boy： "+body);
		return body;	
	}
	 
	
	/**
	 * 获取查询的交易结果
	 * 传入上次交易的（交易批次号）
	 */
	public String getTransactionResult(HttpServletRequest request,String seqsn) {
		String signeture="";
		String requestxml="";
		String responsexml="";
		try {
			String timestamp = DateFormatUtils.getSystemDateByYYYYMMDD();
			//获取请求报文（此时SIGNED_MSG为空）
			requestxml = getTransacReusltBody(timestamp,signeture,seqsn);
			//生成含有（SIGNED_MSG的请求报文）
			requestxml = signMsg(requestxml);
			logger.info("组装签名后的报文为 "+requestxml);
			//通联通账户实名签证，获取响应报文
			CallTlt callTlt = new CallTlt();
			responsexml = callTlt.send(SysConstantsConfig.TLT_VER_URL, requestxml);
			logger.info("查询交易结果的响应报文为"+responsexml);
			int errStart = responsexml.lastIndexOf("<ERR_MSG>");
			int errend = responsexml.lastIndexOf("</ERR_MSG>");	
			String errorMag = responsexml.substring(errStart + 9, errend);
			logger.info("执行的返回结果为"+errorMag);
			//使用请求报文验证签名
			boolean isfalag = verifyMsg(responsexml,SysConstantsConfig.TLT_CER_PATH,false);
			logger.info("响应报文签名结果： "+isfalag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//时间戳	
		return responsexml;
	}
	/**
	 * 获取查询交易结果body
	 */
	private String getTransacReusltBody(String timestamp, String signeture,
			String seqsn) {
		String body=(new StringBuilder("<?xml version='1.0' encoding='GBK'?><AIPG>")
		.append("<INFO>")
			.append("<TRX_CODE>").append("200004").append("</TRX_CODE>")//交易代码
			.append("<VERSION>").append("03").append("</VERSION>")//版本
			.append("<DATA_TYPE>").append("2").append("</DATA_TYPE>")//数据格式(2代表xml
			.append("<LEVEL>").append("5").append("</LEVEL>")//处理级别N(1)0-9  0优先级最低，默认为5
			.append("<MERCHANT_ID>").append(SysConstantsConfig.MERCHANT_ID).append("</MERCHANT_ID>")//商户代码
			.append("<USER_NAME>").append(SysConstantsConfig.TLT_VER_NAME).append("</USER_NAME>")//用户名
			.append("<USER_PASS>").append(SysConstantsConfig.TLT_VER_PASSWORD).append("</USER_PASS>")//密码
			.append("<REQ_SN>").append("LYZ_"+timestamp).append("</REQ_SN>")//交易批次号//LYZ_20170804_095822_230
			.append("<SIGNED_MSG>").append(signeture).append("</SIGNED_MSG>")//数据签名			
		.append("</INFO>")
		.append("<QTRANSREQ>")
			.append("<QUERY_SN>").append(seqsn).append("</QUERY_SN>") //要查询的交易流水,也就是原请求交易中的REQ_SN的值
			.append("<MERCHANT_ID>").append(SysConstantsConfig.MERCHANT_ID).append("</MERCHANT_ID>")//商户代码
			.append("<STATUS>").append(2).append("</STATUS>")//交易状态条件, 0成功,1失败, 2全部,3退票,4代付失败退款,5代付退票退款,6委托扣款7提现
			.append("<TYPE>").append(1).append("</TYPE>")//0.按完成日期1.按提交日期，默认为1
			.append("<START_DAY>").append("").append("</START_DAY>")//若不填QUERY_SN则必填
			.append("<END_DAY>").append("").append("</END_DAY>")//填了开始时间必填
		.append("</QTRANSREQ>")
		.append("</AIPG>")).toString();
		logger.info("组装报文的Boy： "+body);
		return body;
	}

	/**
	 * 获取签名后的请求报文
	 */
	public String signMsg(String xml) throws Exception{
		String returnxml=TltSignatureUtill.signMsg(xml, SysConstantsConfig.PFX_PATH, SysConstantsConfig.PFX_PASSWORD, false);
		return returnxml;
	}
		
	/**
	 * 用返回的响应报文验证签名是否正确
	 */
	public boolean verifyMsg(String msg,String cer,boolean isFront) throws Exception{
		boolean flag = TltSignatureUtill.verifySign(msg, cer, false,isFront);
		System.out.println("验签结果["+flag+"]") ;
		return flag;
	}

	/**
	 * 测试用户录入的银行卡账号是否符合所选择的银行
	 */
	public String testBank(HttpServletRequest httprequest,
			HttpServletResponse httprespon) {
		String banknumber="";
		String bankname="";
		banknumber=httprequest.getParameter("banknumber");//获取银行账号
		VerificBankNumber vBankNumber=new VerificBankNumber();
		/*bankname=vBankNumber.testNumber(banknumber);*/
	    bankname=vBankNumber.testNumber(banknumber);
		bankname=bankname.substring(0, bankname.indexOf("·"));
		logger.info("该银行卡账户所属银行为"+bankname);
		return bankname;
	}

	/**
	 *将提现密码更新进入数据库
	 */
	public String addPassword(HttpServletRequest request) {
		HttpSession session=request.getSession();
		UserInfor userinfor = (UserInfor) session.getAttribute("loginUser");
		String passwordConfim=request.getParameter("passwordConfim");
		logger.info("更新提现密码为...."+passwordConfim);
		int result=-1;
		String isflag="error";
		if(userinfor!=null){
			try {
				userinfor.setCashDealpass(DesUtil.encrypt(passwordConfim));
				result=loginManageService.updateuserinfor(userinfor);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(result>0){
				isflag="success";
			}
		}
		return isflag;
	}

	public static void main(String[] args) throws ParseException {
		String sign = null;
		String timestamp = DateFormatUtils.getSystemDateByYYYYMMDD();//时间戳	
		String param = "acctname=liudongbo&acctno=6212264100012745687&certno=150104198606120323&merchantno=600027&phone=13800138000&requestno="+timestamp+"&verifytype=0040";
		//MD5签名
		logger.info("签名前字符串为："+param+"&key=75578ada7b404b61abb4b69efb12b1fd");
		sign = StringUtils.MD5((param+"&key=75578ada7b404b61abb4b69efb12b1fd").getBytes()).toUpperCase();
		logger.info("签名后的数据串： "+param);
		logger.info("请求前字符串："+param+"&sign="+sign);
		String url = "http://119.29.103.158:9090/CRM/validategateway_validateIdentity.action";
		String responseString = HttpRequestUtility.sendPost(url, param+"&sign="+sign);
		System.out.println("请求结果为："+responseString);
	}
	
}
