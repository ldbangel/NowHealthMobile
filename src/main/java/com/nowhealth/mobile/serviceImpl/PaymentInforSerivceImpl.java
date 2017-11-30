package com.nowhealth.mobile.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.nowhealth.mobile.dms.LoginManageService;
import com.nowhealth.mobile.dms.PaymentInforDataManageService;
import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.entity.UserInfor;

import com.nowhealth.mobile.payUtils.SybPayService;
import com.nowhealth.mobile.payUtils.SybUtil;
import com.nowhealth.mobile.service.PaymentInforService;
import com.nowhealth.mobile.utils.CreateQRCodeUtils;
import com.nowhealth.mobile.utils.HttpRequestUtility;
import com.nowhealth.mobile.utils.JsonUtility;
import com.nowhealth.mobile.utils.QRCodeUtil;
import com.nowhealth.mobile.utils.StringUtils;
import com.nowhealth.mobile.utils.SysConstantsConfig;

@Service("paymentInforService")
public class PaymentInforSerivceImpl implements PaymentInforService{
	private static final Logger logger = Logger
			.getLogger(PersonInforServiceImpl.class);
	@Resource
	private PaymentInforDataManageService paymentInforDataManageService;
	
	@Resource
	private LoginManageService loginManageService;
	/**
	 * 支付申请
	 */
	public BaseInfor paymentApplication(HttpServletRequest httpRequest) {
		//获取页面提交过来订单号
		String orderno = httpRequest.getParameter("orderNo");
		HttpSession session = httpRequest.getSession();
		BaseInfor baseinfor= new BaseInfor();		
		if (StringUtils.checkStringEmpty(orderno)&& session.getAttribute(orderno + "baseinfor") != null){
			baseinfor = (BaseInfor)session.getAttribute(orderno +"baseinfor");
		}
		if (baseinfor == null) {			
			BaseInfor baseInfor=new BaseInfor();
			baseinfor=baseInfor;
		}
		baseinfor.setPaymentstatus("10");
		logger.info("设置支付状态为10......");
		int result = paymentInforDataManageService.operatePaymentInfo(baseinfor);
		if(result>0){
			session.setAttribute(orderno+"baseinfor", baseinfor);
		}
		return baseinfor;
	}
	
	/**
	 * 生成扫码支付的二维码
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	/*public String parsePayQRcode(HttpServletRequest request) throws Exception{
		CreateQRCodeUtils codeUtil = new CreateQRCodeUtils();//二维码生成工具类
		//获取页面提交过来订单号
		String orderno = request.getParameter("orderNo");
		HttpSession session = request.getSession();
		BaseInfor baseinfor=null;		
		if (StringUtils.checkStringEmpty(orderno)&& session.getAttribute(orderno + "baseinfor") != null){
			baseinfor = (BaseInfor)session.getAttribute(orderno +"baseinfor");
		}else{			
			BaseInfor baseInfor=new BaseInfor();
			baseinfor=baseInfor;
		}
		SybPayService service = new SybPayService();
		//调取通联通第三方支付接口
		//(long)(baseinfor.getTotalamount()*100)
		Map<String, String> map = service.sweepPay(1, baseinfor.getOrderno(), 
				SysConstantsConfig.WECHAT_SWEEP_PAY, "慧英保险", "备注", "",
				"123",SysConstantsConfig.TLT_PAY_CALLBACK_ADDRESS,"");
		//logo图标位置
		String basePath = request.getScheme()+"://"
   			 +request.getServerName()+":"+request.getServerPort()+request.getContextPath()  +"/"; 
   	 	String ccbPath = basePath+"views/images/nowhealth_logo.png";   //获取图片路径(URL)
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		//生成微信支付二维码
		String result = codeUtil.encoderQRCode(map.get("payinfo"), output, "png", 13, ccbPath);
		return result;
	}*/
	public String parsePayQRcode(HttpServletRequest request) throws Exception{
		CreateQRCodeUtils codeUtil = new CreateQRCodeUtils();//二维码生成工具类
		//获取页面提交过来订单号
		String orderno = request.getParameter("orderNo");
		HttpSession session = request.getSession();
		BaseInfor baseinfor=null;		
		if (StringUtils.checkStringEmpty(orderno)&& session.getAttribute(orderno + "baseinfor") != null){
			baseinfor = (BaseInfor)session.getAttribute(orderno +"baseinfor");
		}else{			
			BaseInfor baseInfor=new BaseInfor();
			baseinfor=baseInfor;
		}
		SybPayService service = new SybPayService();
		//调取通联通第三方支付接口
		//(long)(baseinfor.getTotalamount()*100)
		Map<String, String> map = service.sweepPay(1, baseinfor.getOrderno(), 
				SysConstantsConfig.WECHAT_SWEEP_PAY, "慧英保险", "备注", "",
				"123",SysConstantsConfig.TLT_PAY_CALLBACK_ADDRESS,"");
		//logo图标位置
		String basePath = request.getScheme()+"://"
   			 +request.getServerName()+":"+request.getServerPort()+request.getContextPath()  +"/"; 
   	 	String ccbPath = basePath+"views/images/nowhealth_logo.png";   //获取图片路径(URL)
		//生成微信支付二维码
   	    BufferedImage image = QRCodeUtil.createQRCodeWithLogo(map.get("payinfo"), ccbPath);
		//读取文件转换为字节数组
   	    ByteArrayOutputStream output = new ByteArrayOutputStream(); 
        ImageIO.write(image, "png",output );                 //output:字节输出流
        byte[] bytes = output.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();         //将字节数组转为二进制
        String result = encoder.encodeBuffer(bytes).trim();  //二进制字节码
        output.flush();
        output.close(); 
		return result;
	}
	
	/**
	 * 微信扫码支付因为不能长按识别支付，所以改成调用微信公众号支付，扫码或者长按调用微信公众号支付
	 * 生成二维码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String sweepPayQRcode(HttpServletRequest request){
		//获取页面提交过来订单号
		String orderno = request.getParameter("orderNo");
		//生成微信支付二维码
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		HttpSession session = request.getSession();
		UserInfor userinfor = (UserInfor) session.getAttribute("loginUser");
		String openid = userinfor.getUsername();
		String basePath = request.getScheme()+"://"+request.getServerName()
				+":"+request.getServerPort()+request.getContextPath()  +"/";  //获取web项目全路径
		String result = CreateQRCodeUtils.encoderQRCode(basePath+"views/jsp/wechatSweep.jsp?orderno="
				+orderno+"&openid="+openid,output, "png", 10);
		return result;
	}

	/**
	 * 微信公众号支付
	 */
	public Map<String, String> wechatPay(HttpServletRequest request) {
		//获取页面提交过来订单号
		String orderno = request.getParameter("orderNo");
		HttpSession session = request.getSession();
		BaseInfor baseinfor=null;		
		if (StringUtils.checkStringEmpty(orderno)&& session.getAttribute(orderno + "baseinfor") != null){
			baseinfor = (BaseInfor)session.getAttribute(orderno +"baseinfor");
		}else{			
			BaseInfor baseInfor=new BaseInfor();
			baseinfor=baseInfor;
		}
		SybPayService service = new SybPayService();
		Map<String, String> map = null;
		UserInfor user = (UserInfor) session.getAttribute("loginUser");
		String openid = user.getUsername();
		try {
			map = service.sweepPay(1, baseinfor.getOrderno(), SysConstantsConfig.WECHAT_JS_PAY,
					"慧英保险", "备注", openid,"123",SysConstantsConfig.TLT_PAY_CALLBACK_ADDRESS,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("返回的map为："+map);
		return map;
	}
	
	/**
	 * 长按或者扫描二维码调用微信公众号支付
	 * @param request
	 * @return
	 */
	public Map<String, String> wechatPayToSweep(HttpServletRequest request) {
		//获取页面提交过来订单号
		String orderno = request.getParameter("orderNo");
		String openid = request.getParameter("openid");
		SybPayService service = new SybPayService();
		Map<String, String> map = null;
		try {
			map = service.sweepPay(1, orderno, SysConstantsConfig.WECHAT_JS_PAY,
					"慧英保险", "备注", openid,"123",SysConstantsConfig.TLT_PAY_CALLBACK_ADDRESS,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("返回的map为："+map);
		return map;
	}

	/**
	 * 选择银行卡支付生成签名
	 */
	public String generateSignature(HttpServletRequest request,
			HttpServletResponse response) {
		/*logger.info("通联快捷支付key"+SysConstantsConfig.TLTPAY_KEY+"=====商户号为："+SysConstantsConfig.TLTMERCHANT_ID+
				"登录url为："+SysConstantsConfig.TLTLOGNIN_URL+"支付url为："+SysConstantsConfig.TLTPAY_URL);*/
		String inputCharset=request.getParameter("inputCharset");
		String pickupUrl=request.getParameter("pickupUrl");//跳转到首页地址
		String receiveUrl=request.getParameter("receiveUrl");//支付成功之后回调到后台的地址
		String version=request.getParameter("version");
		String language=request.getParameter("language");
		String signType=request.getParameter("signType");
		//String merchantId=request.getParameter("merchantId");
		String orderNo=request.getParameter("orderNo");
		String orderAmount=request.getParameter("orderAmount");
		String orderCurrency=request.getParameter("orderCurrency");
		String orderDatetime=request.getParameter("orderDatetime");
		String bankPayUserId=request.getParameter("bankPayUserId");//通联返回的userid
		String payType=request.getParameter("payType");//0
		//String key=request.getParameter("key");//1234567890
		String signa="";
		String param = "inputCharset="+inputCharset+"&pickupUrl="+pickupUrl+"&receiveUrl="+receiveUrl+"&version="+version+"&language="
				+language+"&signType="+signType+"&merchantId="+SysConstantsConfig.TLTMERCHANT_ID+"&orderNo="+orderNo+"&orderAmount="+orderAmount+"&orderCurrency="+orderCurrency+"&orderDatetime="+orderDatetime+"&ext1="+bankPayUserId+"&payType="+payType;
		String dataString = param+"&key="+SysConstantsConfig.TLTPAY_KEY;
		logger.info("签名原串为："+dataString);
		signa=SybUtil.Md5Encode(dataString.toString());//记得是md5编码的加签
		logger.info("签名为："+signa+"=====订单为:"+orderNo+"=======时间为："+orderDatetime+"========金额为："+orderAmount);
		return signa;
	}

	/**
	 * 银行卡支付前，向通联注册用户，并且获取返回的userid
	 */
	public String getTltUserid(HttpServletRequest request,
			HttpServletResponse httResponse) {
		HttpSession session=request.getSession();
		UserInfor user=(UserInfor) session.getAttribute("loginUser");
		String orderno=request.getParameter("orderNo");
		String isagentshare=request.getParameter("isagentshare");
		String binduser="";
		if(Integer.parseInt(isagentshare)>0){//代理人分享录单
			binduser=orderno.substring(orderno.length()-4);
		}else{
			binduser=String.valueOf(user.getUserid());
		}
		if(user!=null){
			logger.info("获取用户的userid为"+user.getUserid());
		}
		String signatue="";//签名结果
		//获取签名原串：
		String param = "&signType="+"0"+"&merchantId="+SysConstantsConfig.TLTMERCHANT_ID+"&partnerUserId="+binduser+"&key="+SysConstantsConfig.TLTPAY_KEY+"&";
		logger.info("签名原串为"+param);
		signatue=SybUtil.Md5Encode(param.toString());
		String requestxml="signType="+"0"+"&merchantId="+SysConstantsConfig.TLTMERCHANT_ID+"&partnerUserId="+binduser+"&signMsg="+signatue;
		logger.info("请求参数为"+requestxml);
		String responseJson = HttpRequestUtility.sendGET(SysConstantsConfig.TLTLOGNIN_URL, requestxml);
		String returnUserid="";
		int reslut=-1;
		if(!"".equals(responseJson)){
			returnUserid= JsonUtility.getVauleFromJson("userId", responseJson);
			logger.info("通联支付注册返回的userid为"+returnUserid);
			if(!"".equals(returnUserid)){
				if(Integer.parseInt(isagentshare)==0){
					user.setBankPayUserId(returnUserid);
					reslut=loginManageService.updateuserinfor(user);
					if(reslut>0){
						logger.info("通联支付返回userid更新成功");
					}
				}
			}
		}
		return returnUserid;
	}

}
