package com.nowhealth.mobile.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nowhealth.mobile.constants.LudiConstants;
import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.entity.OTPGeneration;
import com.nowhealth.mobile.payUtils.SybConstants;
import com.nowhealth.mobile.payUtils.SybUtil;
import com.nowhealth.mobile.service.PaymentCompleteService;
import com.nowhealth.mobile.utils.SendMailsToCustomer;
import com.nowhealth.mobile.utils.SysConstantsConfig;

/**
 * @Description: 支付完成的controller 
 * @author zhuhaidong
 * @date 2017-7-7 下午4:05:58
 */
@Controller
@RequestMapping("/paymentComplete")
public class PaymentCompleteController {

	private static final Logger logger = Logger
			.getLogger(PaymentCompleteController.class);
	
	@Autowired
	private PaymentCompleteService paymentService;
	
	//支付完成回调接口
	@RequestMapping("payCallback.do")
	public String successPayCallback(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap) throws IOException{
		logger.info("微信支付回调进入！");
		BaseInfor baseinfor= new BaseInfor();
		OTPGeneration Generation=new OTPGeneration();
		request.setCharacterEncoding("gbk");//通知传输的编码为GBK
		response.setCharacterEncoding("gbk");
		TreeMap<String,String> params = getParams(request);//动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容
		String openId=params.get("acct");            //获取用户openId oOLN5w9uiHOpJ7CRzDZj37gwuutw
		//String openId="oOLN5w9uiHOpJ7CRzDZj37gwuutw";    //测试用
		logger.info("当前用户名为：" + openId);
		String phoneno = "";     //手机号
		String orderNo = "";     //订单号
		String querycode ="";    //咨询码
		String emailAdraess =""; //用户邮箱地址
		String orderState ="";
		String dxyReturnCode=""; //丁香园返回状态码
		try {
			boolean isSign = SybUtil.validSign(params, SybConstants.SYB_APPKEY);// 接受到推送通知,首先验签
			System.out.println("验签结果:"+isSign);
			//验签完毕进行业务处理
		} catch (Exception e) {//处理异常
			e.printStackTrace();
		}
		String trxstatus=params.get("trxstatus");//获取交易状态码，判断交易是否成功
		logger.info("当前状态码为 ： "+trxstatus);
		/*String orderno=params.get("chnltrxid");*/		
		BaseInfor base=paymentService.selectById(request.getParameter("cusorderid"));
		if(base !=null){
			orderState = base.getOrderstart();
			logger.info("当前订单状态为：" + orderState);
		}
		if("50".equals(orderState)){
			modelMap.addAttribute("baseinfor", base);
			modelMap.addAttribute("trxstatus", params.get("trxstatus"));
			logger.info("接口已经回调过一次，订单状态已经修改为40，防止多次发送咨询码到用户.......");
		}else{
			if("0000".equals(trxstatus)){
				//支付完成回调响应结果
				response.getOutputStream().write("success".getBytes());
				response.flushBuffer();
				baseinfor = paymentService.processPaymentSuccessData(openId,request, response);
				logger.info("交易成功....交易订单号为"+baseinfor.getOrderno());
				if(baseinfor!=null){
					phoneno  = baseinfor.getPurchaserphoneno();
					orderNo  = baseinfor.getOrderno();    //当前订单号
					querycode= phoneno;   //咨询码与手机号相同
					emailAdraess = baseinfor.getPurchaseremail();
					logger.info("交易成功....订单咨询码为"+querycode);
					try {
						//丁香园签名
						dxyReturnCode = paymentService.getDxySign(baseinfor, request);
						//丁香园签名成功 更新订单状态为50 并发送咨询码
						if("200".equals(dxyReturnCode)){
							logger.info("丁香园签名成功...！returnCode ="+dxyReturnCode + "并更新订单状态为50...");
							//更新订单状态为50
							baseinfor = paymentService.proceDxySuccessData(orderNo, request);
							//发送咨询码
							if(baseinfor !=null && "50".equals(baseinfor.getOrderstart())){
								//发手机
								if(phoneno!=null&&phoneno!="" &&orderNo !=null && orderNo !=""&& querycode!="" && querycode!=null){
									Generation = paymentService.validateCode(phoneno,orderNo,querycode);
								}
								//发邮箱
								if(emailAdraess!=null && emailAdraess !="" &&orderNo !=null && orderNo !=""&& querycode!="" && querycode!=null){
									sendEmailToCustomer(emailAdraess,orderNo,querycode);
								}
							}
						}else{
							logger.info("丁香园签名失败，错误码为："+ dxyReturnCode);
						}
					} catch (Exception e) {
						StringWriter sw = new StringWriter();  
						e.printStackTrace(new PrintWriter(sw, true));  
						String str = sw.toString();
						logger.info("咨询码发送异常"+str);
					}
				}
			}else if("2008".equals(trxstatus)){
				logger.info("交易处理中.......");
			}else {
				logger.info("交易失败.......");
			}
			modelMap.addAttribute("baseinfor", baseinfor);
			modelMap.addAttribute("trxstatus", params.get("trxstatus"));
		}
		return LudiConstants.PAYMENTSUCCESS;	//跳转到支付成功界面	
	}
	
	/**
	 * 发送验咨询码到用户邮箱
	 * @param email
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void sendEmailToCustomer(String emailAdraess,String orderNo,String querycode) throws Exception{
		SendMailsToCustomer sendMail = new SendMailsToCustomer();
		// 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
		String myEmailAccount = SysConstantsConfig.LUDI_EMAIL_ADDRESS;
	    // 发件人第三方授权码
	    String myEmailPassword =SysConstantsConfig.LUDI_EMAIL_PASSWORD;  
	    // 发件人邮箱的 SMTP(即简单邮件传输协议) 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般格式为: smtp.xxx.com ;网易163邮箱的 SMTP 服务器地址为: smtp.163.com 
	    String myEmailSMTPHost =SysConstantsConfig.EMAIL_SMTP_HOST;
	    // 收件人邮箱(替换为自己知道的有效邮箱)页面获取的 emailAdraess
	    // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.port","587");
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        props.setProperty("mail.smtp.starttls.enable", "true");
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

        // 3. 创建一封邮件
        MimeMessage message = sendMail.createMimeMessage(session, myEmailAccount,orderNo, emailAdraess,querycode);

        // 也可以保持到本地查看,message.writeTo(file_out_put_stream);
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器
        // 这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        logger.info("邮件发送成功，请耐心等待回复");
        // 7. 关闭连接
        transport.close();
	}
	
	/**
	 * 发送咨询码到用户手机
	 * @param phoneno
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping("SendAdvisoryCode.do")
	@ResponseBody
	public OTPGeneration SendAdvisoryCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OTPGeneration Generation=new OTPGeneration();
		String phoneno = request.getParameter("phoneNo");
		try {
			Generation= paymentService.validateCode(phoneno,request);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.info("咨询码发送异常"+str);
		}
		return Generation;
	}*/
	
	
	/**
	 * 发送验咨询码到用户邮箱
	 * @param email
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping("sendEmail.do")
	@ResponseBody
	public void sendEmailToCustomer(String emailAdraess,HttpServletRequest request,HttpServletResponse response) throws Exception{
		SendMailsToCustomer sendMail = new SendMailsToCustomer();
		// 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
		String myEmailAccount = SysConstantsConfig.LUDI_EMAIL_ADDRESS;
	    // 发件人第三方授权码
	    String myEmailPassword =SysConstantsConfig.LUDI_EMAIL_PASSWORD;  
	    // 发件人邮箱的 SMTP(即简单邮件传输协议) 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般格式为: smtp.xxx.com ;网易163邮箱的 SMTP 服务器地址为: smtp.163.com 
	    String myEmailSMTPHost =SysConstantsConfig.EMAIL_SMTP_HOST;
	    // 收件人邮箱(替换为自己知道的有效邮箱)页面获取的 emailAdraess
	    String receiveMailAccount = request.getParameter("emailAdraess");
	    // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.port","587");
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        props.setProperty("mail.smtp.starttls.enable", "true");
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

        // 3. 创建一封邮件
        MimeMessage message = sendMail.createMimeMessage(session, myEmailAccount, receiveMailAccount);

        // 也可以保持到本地查看,message.writeTo(file_out_put_stream);
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器
        // 这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        logger.info("邮件发送成功，请耐心等待回复");
        // 7. 关闭连接
        transport.close();
	}*/
	
	/**
	 * 支付完成后,跳转到支付成功界面
	 */
	/*@RequestMapping("/goPaymentSucessPage.do")
	public String goPaymentSucessPage(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		
		BaseInfor baseinfor=paymentService.processPaymentSuccessData(request, response);
		modelMap.addAttribute("baseinfor", baseinfor);
		return LudiConstants.PAYMENTSUCCESS;	//跳转到支付成功界面	
	}*/
	
	/**
	 * 动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容由于收银宝加字段而引起的签名异常
	 * @param request
	 * @return
	 */
	private TreeMap<String, String> getParams(HttpServletRequest request){
		TreeMap<String, String> map = new TreeMap<String, String>();
		Map reqMap = request.getParameterMap();
		for(Object key:reqMap.keySet()){
			String value = ((String[])reqMap.get(key))[0];
			System.out.println(key+";"+value);
			map.put(key.toString(),value);
		}
		return map;
	}
	
	/**
	 * 银行卡支付回调地址
	 */
	@RequestMapping("bankpayCallback.do")
	public String bankPaycallback(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
	    logger.info("通联快捷支付完成,结果回调开始.........................");
		//获取回调后的签名结果
		boolean isfalg=paymentService.versignature(request, response);
		//String orderNo=request.getParameter("orderNo");//与提交订单时候的商户订单号保持一致
		String phoneno = "";     //手机号
		String orderNo = "";     //订单号
		String querycode ="";    //咨询码
		String emailAdraess =""; //用户邮箱地址
		String orderState ="";
		String dxyReturnCode=""; //丁香园返回状态码
		if(isfalg){
			BaseInfor baseinfor= new BaseInfor();
			OTPGeneration Generation=new OTPGeneration();
			BaseInfor base=paymentService.selectById(request.getParameter("orderNo"));
			if(base !=null){
				orderState = base.getOrderstart();
			}
			/*if("40".equals(orderState)){
				modelMap.addAttribute("baseinfor", base);
				modelMap.addAttribute("trxstatus", "快捷支付成功");
				logger.info("接口已经回调过一次，订单状态已经修改为40，防止多次发送咨询码到用户.......");
			}else{*/
				//支付完成回调响应结果
				baseinfor = paymentService.procePaymentSuccessData(request, response);
				logger.info("交易成功....交易订单号为"+baseinfor.getOrderno());
				if(baseinfor!=null){
					phoneno  = baseinfor.getPurchaserphoneno();
					orderNo  = baseinfor.getOrderno();
					querycode= phoneno;   //咨询码与手机号相同
					emailAdraess = baseinfor.getPurchaseremail();
					logger.info("交易成功....订单咨询码为"+querycode);
					//发送咨询码
					try {
						//丁香园签名
						dxyReturnCode = paymentService.getDxySign(baseinfor, request);
						//丁香园签名成功 更新订单状态为50 并发送咨询码
						if("200".equals(dxyReturnCode)){
							logger.info("丁香园签名成功！");
							//去更改订单状态（50）
							baseinfor = paymentService.proceDxySuccessData(orderNo, request);
							//发送咨询码
							if(baseinfor !=null && "50".equals(baseinfor.getOrderstart())){
								//发手机
								if(phoneno!=null&&phoneno!="" &&orderNo !=null && orderNo !=""&& querycode!="" && querycode!=null){
									Generation = paymentService.validateCode(phoneno,orderNo,querycode);
								}
								//发邮箱
								if(emailAdraess!=null && emailAdraess !="" &&orderNo !=null && orderNo !=""&& querycode!="" && querycode!=null){
									sendEmailToCustomer(emailAdraess,orderNo,querycode);
								}
							}
						}else{
							logger.info("丁香园签名失败，错误码为："+ dxyReturnCode);
						}
					} catch (Exception e) {
						StringWriter sw = new StringWriter();  
						e.printStackTrace(new PrintWriter(sw, true));  
						String str = sw.toString();
						logger.info("咨询码发送异常"+str);
					}
				}
			modelMap.addAttribute("baseinfor", baseinfor);
			modelMap.addAttribute("trxstatus", "快捷支付成功");
			/*}*/
		}
		
		return LudiConstants.PAYMENTSUCCESS;	//跳转到支付成功界面	
	
	}
	
}
