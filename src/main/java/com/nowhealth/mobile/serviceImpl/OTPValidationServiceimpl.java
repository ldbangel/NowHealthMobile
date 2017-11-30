package com.nowhealth.mobile.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nowhealth.mobile.common.ProcessData;
import com.nowhealth.mobile.constants.LudiConstants;
import com.nowhealth.mobile.dms.OTPdataManageService;
import com.nowhealth.mobile.entity.OTPGeneration;
import com.nowhealth.mobile.service.OTPValidationService;
import com.nowhealth.mobile.utils.DateFormatUtils;
import com.nowhealth.mobile.utils.EncryptUtil;
import com.nowhealth.mobile.utils.StringUtils;
import com.nowhealth.mobile.utils.SysConstantsConfig;
/**
 * 短信验证码
 * @author 
 */
@Service
public class OTPValidationServiceimpl implements OTPValidationService {
	private static final Logger logger = Logger.getLogger(OTPValidationServiceimpl.class);
	@Resource
	private OTPdataManageService otpdataManageService;
	@Override
	/**
	 * 获取咨询码
	 */
	public OTPGeneration templateSMS(String phoneNo,String orderNo,String querycode ,OTPGeneration otpGeneration) {
		logger.info("发送短信咨询码开始手机号： "+phoneNo);
		String result = "";
		DefaultHttpClient httpclient=new DefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			//logger.info("健康险咨询码为： "+param);
			//先将验证码存入数据库
			otpGeneration.setPhoneno(phoneNo);
			otpGeneration.setValidationno(querycode);
			otpGeneration.setExprietimes(getExprieTime());
			otpGeneration.setStatus("10");//设置验证码的状态为未使用状态
			String timestamp = DateFormatUtils.getSystemDateByYYYYMMDD();// 时间戳
			String url=geturl(timestamp, encryptUtil);
			String body=getAdviceBody(phoneNo,orderNo, querycode);
			HttpResponse response=post("application/xml",SysConstantsConfig.OTP_ACCOUNTSID.trim(), SysConstantsConfig.OTP_AUTHTOKEN, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}			
			EntityUtils.consume(entity);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.error("发送短信验证码异常，异常消息为： "+str);
		} finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		logger.info("发送短信验证码结束，返回结果为： "+result);
		return otpGeneration;
	}	
	
	/**
	 *获取短信验证码 
	 */
	public OTPGeneration getInfocode(String phoneNo, OTPGeneration otpGeneration) {
		
		logger.info("发送短信验证码开始手机号： "+phoneNo);
		String result = "";
		DefaultHttpClient httpclient=new DefaultHttpClient();
		String param="";
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			param=getValidateNo(true,6);
			logger.info("手机短信验证码为： "+param);
			//先将验证码存入数据库
			otpGeneration.setPhoneno(phoneNo);
			otpGeneration.setValidationno(param);
			otpGeneration.setExprietimes(getExprieTime());
			otpGeneration.setStatus("10");//设置验证码的状态为未使用状态
			otpGeneration=otpdataManageService.saveOTPinfor(otpGeneration);
			String timestamp = DateFormatUtils.getSystemDateByYYYYMMDD();// 时间戳
			String url=geturl(timestamp, encryptUtil);
			String body=getNowHealthBody(phoneNo, param);
			HttpResponse response=post("application/xml",SysConstantsConfig.OTP_ACCOUNTSID, SysConstantsConfig.OTP_AUTHTOKEN, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}			
			EntityUtils.consume(entity);
		} catch (Exception e) {
			//删除已插入的验证码
			otpdataManageService.deleteOTPinfor(otpGeneration);
			StringWriter sw = new StringWriter();  
			 e.printStackTrace(new PrintWriter(sw, true));  
			 String str = sw.toString();
			logger.error("发送短信验证码异常，异常消息为： "+str);
		} finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		if(StringUtils.checkStringEmpty(result)){
			String responseCode=ProcessData.getTemplateSMSReturnCode(result);
			if(StringUtils.checkStringEmpty(responseCode)&&!LudiConstants.TEMPLATE_SMS_SUCCESS_CODE.equalsIgnoreCase(responseCode)){//发送验证码失败
				//删除已插入的验证码
				otpdataManageService.deleteOTPinfor(otpGeneration);
			}			
		}
		logger.info("发送短信验证码结束，返回结果为： "+result);
		return otpGeneration;
	}
	
	/**
	 * 发送提现通知短信to lenny
	 */
	public OTPGeneration getDrawalMoneyBody(Map<String,String> map, OTPGeneration otpGeneration) {
		String phoneNo = map.get("phoneNo");
		String amount  = map.get("amount");
		String userName= map.get("userName");
		logger.info("发送提现通知短信开始手机号： "+phoneNo);
		String result = "";
		DefaultHttpClient httpclient=new DefaultHttpClient();
		try {
			//MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 构造请求URL内容
			otpGeneration.setPhoneno(phoneNo);
			otpGeneration.setExprietimes(getExprieTime());
			otpGeneration.setStatus("10");//设置验证码的状态为未使用状态
			//otpGeneration=otpdataManageService.saveOTPinfor(otpGeneration);			
			String timestamp = DateFormatUtils.getSystemDateByYYYYMMDD();// 时间戳
			String url=geturl(timestamp, encryptUtil);
			String body= getDrawalMoneyBody(phoneNo,userName,amount,DateFormatUtils.getSystemDate());//发送短信的参数（多变量）
			HttpResponse response=post("application/xml",SysConstantsConfig.OTP_ACCOUNTSID, SysConstantsConfig.OTP_AUTHTOKEN, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}			
			EntityUtils.consume(entity);
		} catch (Exception e) {
			 StringWriter sw = new StringWriter();  
			 e.printStackTrace(new PrintWriter(sw, true));  
			 String str = sw.toString();
			logger.error("发送短信验证码异常，异常消息为： "+str);
		} finally{
			// 关闭连接
		    httpclient.getConnectionManager().shutdown();
		}
		logger.info("发送短信验证码结束，返回结果为： "+result);
		return otpGeneration;
	}
	
	/**
	 * 获取请求url
	 */
	public String geturl(String time,EncryptUtil encryptUtil) throws ParseException{
		String signature =getSignature(encryptUtil,time);
		String url = new StringBuffer(SysConstantsConfig.OTP_VALIDATION_URL.trim()).append("/").append(SysConstantsConfig.OTP_SOFTVERSION.trim())
				.append("/Accounts/").append(SysConstantsConfig.OTP_ACCOUNTSID.trim())
				.append("/Messages/templateSMS")
				.append(".xml?sig=").append(signature).toString();
		return url;
		
	}
	/**
	 * 获取请求短信验证码模板body
	 */
	public String getBody(String phone,String parm){
		String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><templateSMS>")
        .append("<appId>").append(SysConstantsConfig.OTP_APPID.trim()).append("</appId>")
        .append("<templateId>").append(SysConstantsConfig.SMS_TEMPLATE_ID.trim()).append("</templateId>")
        .append("<to>").append(phone).append("</to>")
        .append("<param>").append(parm).append("</param>")
        .append("</templateSMS>")).toString();
		logger.info("组装报文的Boy： "+body);
		return body;
		
	}
	/**
	 * 获取请求通知类短信模板body
	 */
	public String getAdviceBody(String phone,String orderNo,String parm){
		String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><templateSMS>")
        .append("<appId>").append(SysConstantsConfig.OTP_ADVICE_APPID.trim()).append("</appId>")
        .append("<templateId>").append(SysConstantsConfig.SMS_ADVICE_TEMPLATE_ID.trim()).append("</templateId>")
        .append("<to>").append(phone).append("</to>")
        .append("<param>").append(orderNo +","+parm).append("</param>")
        .append("</templateSMS>")).toString();
		logger.info("组装报文的Boy： "+body);
		return body;
		
	}
	
	/**
	 * 获取[Dr.康]请求验证短信模板body
	 */
	public String getNowHealthBody(String phone,String parm){
		String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><templateSMS>")
        .append("<appId>").append(SysConstantsConfig.OTP_NOWHEALTH_APPID.trim()).append("</appId>")
        .append("<templateId>").append(SysConstantsConfig.SMS_NOWHEALTH_TEMPLATE_ID.trim()).append("</templateId>")
        .append("<to>").append(phone).append("</to>")
        .append("<param>").append(parm).append("</param>")
        .append("</templateSMS>")).toString();
		logger.info("组装报文的Boy： "+body);
		return body;
		
	}
	
	/**
	 * 获取[Dr.康]提现通知模板body
	 */
	public String getDrawalMoneyBody(String phone,String userName,String amount,String date){
		String body = (new StringBuilder("<?xml version='1.0' encoding='utf-8'?><templateSMS>")
        .append("<appId>").append(SysConstantsConfig.OTP_DRAWALMONEY_APPID.trim()).append("</appId>")
        .append("<templateId>").append(SysConstantsConfig.SMS_DRAWALMONEY_TEMPLATE_ID.trim()).append("</templateId>")
        .append("<to>").append(phone).append("</to>")
        .append("<param>").append(userName+","+date+","+amount).append("</param>")
        .append("</templateSMS>")).toString();
		logger.info("组装报文的Boy： "+body);
		return body;
		
	}
	
	
	/**
	 * 获取Signature
	 * @return
	 */
	public String getSignature(EncryptUtil encryptUtil,String timestamp) {
		String signature = "";		
		try {			
			String sig = SysConstantsConfig.OTP_ACCOUNTSID
					+ SysConstantsConfig.OTP_AUTHTOKEN + timestamp;
			signature = encryptUtil.md5Digest(sig);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.error("发送短信验证码异常，异常消息为： "+str);
		}
		return signature;
	}

	public HttpResponse post(String cType, String accountSid, String authToken,
			String timestamp, String url, DefaultHttpClient httpclient,
			EncryptUtil encryptUtil, String body) throws Exception {
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Accept", cType);
		httppost.setHeader("Content-Type", cType + ";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = encryptUtil.base64Encoder(src);
		httppost.setHeader("Authorization", auth);
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody
				.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
		requestBody.setContentLength(body.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		// 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		return response;
	}

	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 * @return
	 */
	public static String getValidateNo(boolean numberFlag, int length){
      String retStr = "";
      String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
      int len = strTable.length();
      boolean bDone = true;
      do {
    	  retStr = "";
    	  int count = 0;
    	  for (int i = 0; i < length; i++) {
    		  double dblR = Math.random() * len;
    		  int intR = (int) Math.floor(dblR);
    		  char c = strTable.charAt(intR);
    		  if (('0' <= c) && (c <= '9')) {
    			  count++;
    		  }
    		  retStr += strTable.charAt(intR);
    	  }
    	  if (count >= 2) {
    		  bDone = false;
    	  }
      } while (bDone);
      return retStr;
	}
	
	/**
	 * 获取验证码的有效时间
	 * 手机验证码的有效时间为20分钟
	 * @return
	 */
 public String getExprieTime(){;
	 Calendar calendar=Calendar.getInstance();//创建实例 默认是当前时刻
	 calendar.setTime(new Date());
	 calendar.add(Calendar.MINUTE, 20);
	 String exprieTime= DateFormatUtils.getSystemDate(calendar.getTime());
	 return exprieTime;
	 
 }

	
  
}
