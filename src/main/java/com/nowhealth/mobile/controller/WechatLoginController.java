package com.nowhealth.mobile.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Encoder;

import com.nowhealth.mobile.constants.LudiConstants;
import com.nowhealth.mobile.dms.LoginManageService;
import com.nowhealth.mobile.entity.CommssionRatio;
import com.nowhealth.mobile.entity.UserInfor;
import com.nowhealth.mobile.entity.WechatConfig;
import com.nowhealth.mobile.service.LoginService;
import com.nowhealth.mobile.serviceImpl.WechatLoginServiceImpl;
import com.nowhealth.mobile.utils.CreateQRCodeUtils;
import com.nowhealth.mobile.utils.QRCodeUtil;
import com.nowhealth.mobile.utils.StringUtils;
import com.nowhealth.mobile.utils.SysConstantsConfig;


@Controller
@RequestMapping("/WechatLogin")
public class WechatLoginController {
	private static final Logger logger = Logger
			.getLogger(WechatLoginController.class);
	@Resource
	private WechatLoginServiceImpl wechatLoginServiceImpl;
	@Resource
	private LoginService loginService;
	@Resource
	private LoginManageService loginManageService;	

	//微信登录跳转首页
	@RequestMapping(value="/goToFirstScreen")
	public String goToFirstScreen(HttpServletRequest httprequest,HttpServletResponse response,ModelMap modelMap){
		int userType = 3;
		String openId = "";
		String token = "";
		String action = httprequest.getParameter("action");
		UserInfor userinfor = new UserInfor();
		try {
			if("0".equals(SysConstantsConfig.OPENID_TEST)){
				openId ="oOLN5wyORRx7LzjzT_VC1taGsNS0"; //测试openId
			}else if("1".equals(SysConstantsConfig.OPENID_TEST)){
				openId = wechatLoginServiceImpl.getOpenId(httprequest, response);
			}
			token = wechatLoginServiceImpl.getaccessToken();
			if(StringUtils.checkStringEmpty(openId)&&StringUtils.checkStringEmpty(token)){
				//获取微信用户信息(返回UserInfor)
				userinfor = wechatLoginServiceImpl.getUserinfo(httprequest,openId, token);
				userinfor.setUsername(openId);
				userinfor.setUsertype(userType);
				loginService.registUser(userinfor, httprequest);
				loginService.userExists(userinfor, httprequest);   //用户登录
			}
		} catch (Exception e) {
			 StringWriter sw = new StringWriter();  
			 e.printStackTrace(new PrintWriter(sw, true));  
			 String str = sw.toString();
			 logger.error("微信端跳转首页异常"+str);
		}
		modelMap.addAttribute("openId", openId);
		if(StringUtils.checkStringEmpty(action)&&"bindUser".equalsIgnoreCase(action)){
			return LudiConstants.BINGUSER;
		}else if(StringUtils.checkStringEmpty(action)&&"myaccount".equals(action)){
			return LudiConstants.MYACCOUNT;
		}else{
			return LudiConstants.NOWHEALTHHOME;
		}
		
	}
   
	/**
	 * 微信登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/weixinLogin.do")
	@ResponseBody
	public String weixinLogin( HttpServletRequest request,HttpServletResponse response){		
		int userType = 3;
	    String shareId=request.getParameter("shareId");
		String nowshareID=request.getParameter("nowshareID");
		UserInfor userinfor = new UserInfor();
		String result="";
		String openId = "";		
		try {
			if("1".equals(nowshareID)){   //链接分享后获取userID,然后登录
				userinfor = loginService.selectUserById(Integer.parseInt(shareId));
			    openId = userinfor.getUsername(); //获取用户openId
			    logger.info("用户openId : " + openId);
			}else{
				openId = wechatLoginServiceImpl.getOpenId(request, response);
				//  openId ="oOLN5wyORRx7LzjzT_VC1taGsNS0"; //测试openId	
			}			
		   if(StringUtils.checkStringEmpty(openId)){
				 userinfor.setUsername(openId);
				 userinfor.setUsertype(userType);
				 loginService.registUser(userinfor, request);
				 result = loginService.userExists(userinfor, request);   //用户登录
		    }
		} catch (Exception e) {
			 StringWriter sw = new StringWriter();  
			 e.printStackTrace(new PrintWriter(sw, true));  
			 String str = sw.toString();
			 logger.error("微信端跳转首页异常"+str);
		}
		return result;
	}
	
	/* *//**
     * 生成分享二维码   
     * @throws Exception 
     *//*
    @RequestMapping("parseQRCode.do")
    //@ResponseBody
    public String parseQRCode(ModelMap modelMap,HttpServletRequest httprequest) throws Exception{
    	 String result="";
    	 CreateQRCodeUtils codeUtil = new CreateQRCodeUtils();//二维码生成工具类
    	 String userId=httprequest.getParameter("userId");
    	 String strUrl =httprequest.getParameter("strUrl");
    	 String sharenumber =httprequest.getParameter("sharenumber");
    	 String isagentshare = httprequest.getParameter("isagentshare"); 
    	 //获取web项目全路径
    	 String basePath = httprequest.getScheme()+"://"
    			 +httprequest.getServerName()+":"+httprequest.getServerPort()+httprequest.getContextPath()  +"/"; 
    	 String ccbPath = basePath+"views/images/now007.png";   //获取图片路径(URL)
    	 logger.info("userid = "+userId);
    	  *//** 再此调用扫描二维码的方法获取Id   *//* 	 
    	 String codeId="1";//二维码登录ID,用于判断是否是生成二维码时候再次登录   
    	 String data="";
    	 //生成二维码的链接（车险首页）
    	 data=strUrl+"?userId="+userId+"&codeId="+codeId+"&sharenumber="+sharenumber;
    	 //读取文件转换为字节数组
    	 ByteArrayOutputStream output = new ByteArrayOutputStream(); 
    	 //生成二维码（data:二维码内容，output:字节输出流，png:图片类型 ，8:图片尺寸大小, ccbPath:logo图片所在路径URL）
    	 result = codeUtil.encoderQRCode(data, output, "png", 13, ccbPath);  
    	 modelMap.addAttribute("result", result);
    	 if(isagentshare!=null && isagentshare!=""){  
    		 //分享二维码订单再次分享隐藏返回首页按钮
    		 modelMap.addAttribute("isShare","1");
    	 }
	     return LudiConstants.QRCODE;
    }*/
	 /**
     * 生成分享二维码   
     * @throws Exception 
     */
    @RequestMapping("parseQRCode.do")
    //@ResponseBody
    public String parseQRCode(ModelMap modelMap,HttpServletRequest httprequest) throws Exception{
    	 String result="";
    	 CreateQRCodeUtils codeUtil = new CreateQRCodeUtils();//二维码生成工具类
    	 String userId=httprequest.getParameter("userId");
    	 String strUrl =httprequest.getParameter("strUrl");
    	 String sharenumber =httprequest.getParameter("sharenumber");
    	 String isagentshare = httprequest.getParameter("isagentshare"); 
    	 //获取web项目全路径
    	 String basePath = httprequest.getScheme()+"://"
    			 +httprequest.getServerName()+":"+httprequest.getServerPort()+httprequest.getContextPath()  +"/"; 
    	 String ccbPath = basePath+"views/images/nowhealth_logo.png";   //获取图片路径(URL)
    	 logger.info("userid = "+userId);
    	  /** 再此调用扫描二维码的方法获取Id   */ 	 
    	 String codeId="1";//二维码登录ID,用于判断是否是生成二维码时候再次登录   
    	 String data="";
    	 //生成二维码的链接（车险首页）
    	 data=strUrl+"?userId="+userId+"&codeId="+codeId+"&sharenumber="+sharenumber;
    	 //生成二维码（data:二维码内容， ccbPath:logo图片所在路径URL）
    	 BufferedImage image = QRCodeUtil.createQRCodeWithLogo(data, ccbPath);
    	 //读取文件转换为字节数组
    	 ByteArrayOutputStream output = new ByteArrayOutputStream(); 
         ImageIO.write(image, "png",output );  //output:字节输出流
         byte[] bytes = output.toByteArray();
         BASE64Encoder encoder = new BASE64Encoder();  //将字节数组转为二进制
         result = encoder.encodeBuffer(bytes).trim();  //二进制字节码
         output.flush();
         output.close(); 
    	 modelMap.addAttribute("result", result);
    	 if(isagentshare!=null && isagentshare!=""){  
    		 //分享二维码订单再次分享隐藏返回首页按钮
    		 modelMap.addAttribute("isShare","1");
    	 }
	     return LudiConstants.QRCODE;
    }

	/**
	 * 注册判断用户是否注册过
	 * @param username
	 * @return
	 */
	@RequestMapping("/userEverRegist.do")
	@ResponseBody
	public String userEverRegist(String username,HttpServletRequest request,HttpServletResponse response){
		//1表示用户已存在
		String result=wechatLoginServiceImpl.userEverRegist(username);
		return result;
	}
	
	/**
	 * 绑定微信账户
	 * @param httprequest
	 * @param response
	 * @param modelMap
	 */
	@RequestMapping("/bindUser.do")
	public  void bindUserinfor(HttpServletRequest httprequest,HttpServletResponse response,ModelMap modelMap){		
		String result = wechatLoginServiceImpl.bindUser(httprequest,response);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(result);
		} catch (IOException e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.error("微信端绑定用户信息异常"+str);
		}
	}
	
	/**
	 * 注册判断用户是否绑定过
	 * @param username
	 * @return
	 */
	@RequestMapping("/isBindUser.do")
	@ResponseBody
	public String isBindUser(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getParameter("userId");
		String result=wechatLoginServiceImpl.isBindUser(userId);
		return result;
	}
	
	/**
	 * 更改绑定用户手机号
	 * @param httprequest
	 * @param response
	 * @param modelMap
	 */
	@RequestMapping("/changePhone.do")
	public  void changePhoneNo(HttpServletRequest httprequest,HttpServletResponse response,ModelMap modelMap){		
		String result = wechatLoginServiceImpl.changePhone(httprequest,response);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(result);
		} catch (IOException e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.error("微信端绑定用户信息异常"+str);
		}
	}
	
	/**
	 * 校验手机号是否管理员手机号
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("/isAdministrator.do")
	@ResponseBody
	public String isAdministrator(HttpServletRequest request,HttpServletResponse reponse){
		String result = "";
		int comRatioId = 1;  //佣金比例Id
		String adminPhoneNo = request.getParameter("phoneNo");
		CommssionRatio commssionRatio = new CommssionRatio();
		//获取管理员手机号
		commssionRatio = loginManageService.getCommssionRatio(comRatioId);
		if(commssionRatio!=null){
			if(commssionRatio.getUserphone().equals(adminPhoneNo)){
				logger.info("管理员手机号码为 ： " + commssionRatio.getUserphone());
				result="true";
			}else{
				logger.info("当前手机号 ：" +adminPhoneNo+" 非管理员用户！");
				result="false";
			}
		}
		return result;
	}
	
	/**
	 * 获取佣金比例
	 * @param httprequest
	 * @param response
	 * @param modelMap
	 */
	@RequestMapping("/getCommssionRatio.do")
	@ResponseBody
	public CommssionRatio getCommssionRatio(HttpServletRequest request,HttpServletResponse reponse){
		CommssionRatio commssionRatio = new CommssionRatio();
		int comRatioId = 1;  //佣金比例Id
		//获取佣金比例信息
		commssionRatio = loginManageService.getCommssionRatio(comRatioId);
		logger.info("获取佣金比例为： " + commssionRatio.getComratio());
		return commssionRatio;
	}
	
	/**
	 * 更改佣金比例
	 * @param httprequest
	 * @param response
	 * @param modelMap
	 */
	@RequestMapping("/changeCommssionRatio.do")
	public  void changeCommssionRatio(HttpServletRequest httprequest,HttpServletResponse response,ModelMap modelMap){		
		String result = wechatLoginServiceImpl.changeCommssionRatio(httprequest);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(result);
		} catch (IOException e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.error("更改佣金比例信息异常"+str);
		}
	}
	
	
	/*	
	@RequestMapping("/goToMyAccount.do")
	public String goToMyAccount(HttpServletRequest httprequest,HttpServletResponse response,ModelMap modelMap){
		int userType = 3;
		UserInfor userinfor = new UserInfor();
		String openId = "";
		try {
	    openId = wechatLoginServiceImpl.getOpenId(httprequest, response);
			if (StringUtils.checkStringEmpty(openId)) {
				userinfor.setUsername(openId);
				userinfor.setUsertype(userType);
				loginService.registUser(openId, "", userType, httprequest);
				loginService.userExists(userinfor, httprequest);
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.error("微信端跳转我的订单异常"+str);
		}
		return LudiConstants.MYACCOUNT;
	}
	

	
	
	
	@RequestMapping("/getVehicleinforByOCR.do")
	public void  saveImageToDisk(@RequestBody String mediaId,HttpServletRequest httprequest,HttpServletResponse response,ModelMap modelMap){
		HttpSession session=httprequest.getSession();
		JSONObject jsonObject=null;
		String token="";
		if(session!=null&&session.getAttribute("token")!=null){
			token=(String) session.getAttribute("token");
			session.setAttribute("token", token);
		}else{
			token=wechatLoginServiceImpl.getaccessToken();
		}
		try {
			InputStream is=wechatLoginServiceImpl.getInputStream(token,mediaId);
			String result= wechatLoginServiceImpl.getVehicleinforByOCR(is);	
			jsonObject = JSONObject.fromObject(result);
			response.setContentType("text/html;chartset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(jsonObject);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.error("微信端下载图片异常"+str);
		}
	}*/
	
	/**
	 * 微信端链接分享
	 */
	@RequestMapping("/getWechatJSAccess.do")
	public @ResponseBody WechatConfig getWechatJSAccess(
			HttpServletRequest httprequest,HttpServletResponse response,ModelMap modelMap){
		String url=httprequest.getParameter("currenturl");
		String sharenumber=httprequest.getParameter("sharenumber");
		HttpSession session = httprequest.getSession();
		WechatConfig wechatConfig = null;
		UserInfor userinfor = new UserInfor();
		UserInfor user = new UserInfor();
		String ticket="";
		String token="";
		if(session!=null&&session.getAttribute("token")!=null){
			token=(String) session.getAttribute("token");
			session.setAttribute("token", token);
		}else{
			token=wechatLoginServiceImpl.getaccessToken();
		}
		if(session!=null&&session.getAttribute("ticket")!=null){
			ticket=(String) session.getAttribute("ticket");
		}else{
			if(StringUtils.checkStringEmpty(token)){
				ticket=wechatLoginServiceImpl.getjsapi_ticket(token);
				session.setAttribute("ticket", ticket);		
			}
		}
		if(StringUtils.checkStringEmpty(ticket)){
			 wechatConfig = wechatLoginServiceImpl.getSingnature(ticket,url);
			 if(session!=null && session.getAttribute("loginUser")!=null){
				 userinfor = (UserInfor) session.getAttribute("loginUser");
			 }
			 try {
				 user = loginManageService.selectByName(userinfor);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
			 if(user!=null && user.getUserid()!=null){
				 wechatConfig.setSharedLink("http://m.quicksure.com/NowHealthMobile/?userId="+user.getUserid()+"&sharenumber="+sharenumber);
				// wechatConfig.setSharedLink("http://172.16.55.108:8088/NowHealthMobile/?userId="+user.getUserid());
			}
		}
		return wechatConfig;	
	}
}
