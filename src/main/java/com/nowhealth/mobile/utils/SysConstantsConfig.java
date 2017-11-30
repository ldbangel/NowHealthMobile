package com.nowhealth.mobile.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SysConstantsConfig {
	private static Logger logger = Logger.getLogger(SysConstantsConfig.class);
	/**
	 * 系统中各种模板、文件路径等配置
	 */
	public static Properties Pro_Sys_FilePath_Config = new Properties();
	// ---------------------LUDI 通用配置------------------
	public static String Velocity_Template_ForQuickApp_path;//velocity 读取模板的路径
	public static String JY_CONNECT_USERNAME; // 精友服务用户名
	public static String JY_CONNECT_PASSWORD; // 精友服务密码
    public static String SINOSAFE_EXTENTERPCODE;//ludi渠道号
    public static String SINOSAFE_CONNECT_USERNAME;//华安服务用户名
    public static String SINOSAFE_CONNECT_PASSWOED;//华安服务密码
    public static String SINOSAFE_RETURN_URL;//华安支付系统成功之后返回我们系统的地址
    public static String OTP_VALIDATION_URL;//OTP短信验证请求地址
    public static String OTP_APPID;//
    public static String OTP_ADVICE_APPID;//通知类短信模板APPID
    public static String OTP_NOWHEALTH_APPID;//时康短信模板AppID
    public static String OTP_DRAWALMONEY_APPID;//提现通知短信模板APPID
    public static String OTP_ACCOUNTSID;//
    public static String OTP_AUTHTOKEN;//
    public static String OTP_SOFTVERSION;
    public static String SMS_TEMPLATE_ID;
    public static String SMS_ADVICE_TEMPLATE_ID;//通知类短信模板ID
    public static String SMS_NOWHEALTH_TEMPLATE_ID;//时康短信模板ID
    public static String SMS_DRAWALMONEY_TEMPLATE_ID;//提现通知短信模板ID
    public static String WECHAT_URL;
    public static String WECHAT_APPID;
    public static String WECHAT_APPSECRET;
    public static String OCR_URL_ADDRESS;
    public static String OCR_PATH;
    public static String OCR_APPCODE;
    public static String SINOSAFE_NOTCART_USERNAME;//非车用户名
    public static String SINOSAFE_NOTCART_PASSWOED;//非车密码
    public static String SINOSAFE_SYNCHRONIZATION_URL;//非车同步地址
    public static String SINOSAFE_ASYNCHRONOUS_URL;//非车异步地址
    public static String SINOSAFE_FEICHE_CODE;//非车渠道代码
    public static String LUDI_EMAIL_ADDRESS;//邮箱地址
    public static String LUDI_EMAIL_PASSWORD;//邮箱地址
    public static String EMAIL_SMTP_HOST;//邮箱服务器地址
    public static String WECHAT_SWEEP_PAY;//通联通支付微信扫码支付代码
    public static String WECHAT_JS_PAY; //微信公众号支付
    //通联通配置参数
    public static String TLT_PAY_CALLBACK_ADDRESS;//通联通支付完成回调地址
    public static String TLT_VER_URL;
    public static String PFX_PATH;
    public static String TLT_CER_PATH;
    public static String TLT_VER_NAME;
    public static String TLT_VER_PASSWORD;
    public static String BUSINESS_CODE;
    public static String MERCHANT_ID;
    public static String PFX_PASSWORD;
    public static String VERIFY_MERCHANT_ID; //四要素验证商户号
    public static String VERIFY_KEY; //四要素验证交易秘钥
    public static String VERIFY_URL; //四要素验证地址
    //通联移动支付配置参数
    public static String TLTPAY_KEY;
    public static String TLTLOGNIN_URL;
    public static String TLTPAY_URL;
    public static String TLTMERCHANT_ID;
    public static String TLTRECEIVE_URL;
    public static String TLTPICKUP_URL;
    //丁香园配置参数
    public static String DXY_APPID;//接口调用方身份标识
    public static String DXY_APPSIGNKEY; //签名用密钥
    public static String DXY_URL;  //丁香园url
    
    public static String OPENID_TEST; //测试环境与生产环境openID切换
    
	static {
		loadConfigFiles();
	}

	/**
	 * 方法reloadConfigs的详细说明 <br>
	 * 
	 * <pre>
	 * 重新加载配置信息 <br>
	 * 编写者：孙小东
	 * 创建时间：2014-9-29 上午9:58:15
	 * </pre>
	 */
	public static void reloadConfigs() {
		loadConfigFiles();
	}

	private static void loadConfigFiles() {
		Class clazz = SysConstantsConfig.class;
		String fileName = "";
		try {
			fileName = "/ludiconfigurationinfor.properties";
			Pro_Sys_FilePath_Config.load(clazz.getResourceAsStream(fileName));
		} catch (IOException e) {
			logger.error("加载配置文件失败。文件名是：" + fileName, e);
		}
		initConfig();
	}

	private static void initConfig() {
		JY_CONNECT_USERNAME = Pro_Sys_FilePath_Config.getProperty("JY_CONNECT_USERNAME");
		JY_CONNECT_PASSWORD=Pro_Sys_FilePath_Config.getProperty("JY_CONNECT_PASSWORD");
		SINOSAFE_EXTENTERPCODE=Pro_Sys_FilePath_Config.getProperty("SINOSAFE_EXTENTERPCODE");
		SINOSAFE_CONNECT_USERNAME=Pro_Sys_FilePath_Config.getProperty("SINOSAFE_CONNECT_USERNAME");
		SINOSAFE_CONNECT_PASSWOED=Pro_Sys_FilePath_Config.getProperty("SINOSAFE_CONNECT_PASSWOED");
		Velocity_Template_ForQuickApp_path=Pro_Sys_FilePath_Config.getProperty("Velocity_Template_ForQuickApp_path");
		SINOSAFE_RETURN_URL=Pro_Sys_FilePath_Config.getProperty("SINOSAFE_RETURN_URL");
		OTP_VALIDATION_URL=Pro_Sys_FilePath_Config.getProperty("OTP_VALIDATION_URL");
		OTP_APPID=Pro_Sys_FilePath_Config.getProperty("OTP_APPID");
		OTP_ADVICE_APPID=Pro_Sys_FilePath_Config.getProperty("OTP_ADVICE_APPID");
		OTP_NOWHEALTH_APPID=Pro_Sys_FilePath_Config.getProperty("OTP_NOWHEALTH_APPID");
		OTP_DRAWALMONEY_APPID=Pro_Sys_FilePath_Config.getProperty("OTP_DRAWALMONEY_APPID");
		OTP_ACCOUNTSID=Pro_Sys_FilePath_Config.getProperty("OTP_ACCOUNTSID");
		OTP_AUTHTOKEN=Pro_Sys_FilePath_Config.getProperty("OTP_AUTHTOKEN");
		OTP_SOFTVERSION=Pro_Sys_FilePath_Config.getProperty("OTP_SOFTVERSION");
		SMS_TEMPLATE_ID=Pro_Sys_FilePath_Config.getProperty("SMS_TEMPLATE_ID");
		SMS_ADVICE_TEMPLATE_ID=Pro_Sys_FilePath_Config.getProperty("SMS_ADVICE_TEMPLATE_ID");
		SMS_NOWHEALTH_TEMPLATE_ID=Pro_Sys_FilePath_Config.getProperty("SMS_NOWHEALTH_TEMPLATE_ID");
		SMS_DRAWALMONEY_TEMPLATE_ID=Pro_Sys_FilePath_Config.getProperty("SMS_DRAWALMONEY_TEMPLATE_ID");
		WECHAT_URL=Pro_Sys_FilePath_Config.getProperty("WECHAT_URL");
		WECHAT_APPID=Pro_Sys_FilePath_Config.getProperty("WECHAT_APPID");
		WECHAT_APPSECRET=Pro_Sys_FilePath_Config.getProperty("WECHAT_APPSECRET");
		OCR_URL_ADDRESS=Pro_Sys_FilePath_Config.getProperty("OCR_URL_ADDRESS");
		OCR_PATH=Pro_Sys_FilePath_Config.getProperty("OCR_PATH");
		OCR_APPCODE=Pro_Sys_FilePath_Config.getProperty("OCR_APPCODE");
		SINOSAFE_NOTCART_USERNAME=Pro_Sys_FilePath_Config.getProperty("SINOSAFE_NOTCART_USERNAME");
		SINOSAFE_NOTCART_PASSWOED=Pro_Sys_FilePath_Config.getProperty("SINOSAFE_NOTCART_PASSWOED");
		SINOSAFE_SYNCHRONIZATION_URL=Pro_Sys_FilePath_Config.getProperty("SINOSAFE_SYNCHRONIZATION_URL");
		SINOSAFE_ASYNCHRONOUS_URL=Pro_Sys_FilePath_Config.getProperty("SINOSAFE_ASYNCHRONOUS_URL");
		SINOSAFE_FEICHE_CODE=Pro_Sys_FilePath_Config.getProperty("SINOSAFE_FEICHE_CODE");
		LUDI_EMAIL_ADDRESS=Pro_Sys_FilePath_Config.getProperty("LUDI_EMAIL_ADDRESS");
		LUDI_EMAIL_PASSWORD=Pro_Sys_FilePath_Config.getProperty("LUDI_EMAIL_PASSWORD");
		EMAIL_SMTP_HOST=Pro_Sys_FilePath_Config.getProperty("EMAIL_SMTP_HOST");
		WECHAT_SWEEP_PAY=Pro_Sys_FilePath_Config.getProperty("WECHAT_SWEEP_PAY");
		WECHAT_JS_PAY=Pro_Sys_FilePath_Config.getProperty("WECHAT_JS_PAY");
		TLT_PAY_CALLBACK_ADDRESS=Pro_Sys_FilePath_Config.getProperty("TLT_PAY_CALLBACK_ADDRESS");
		TLT_VER_URL=Pro_Sys_FilePath_Config.getProperty("TLT_VER_URL");
		PFX_PATH=Pro_Sys_FilePath_Config.getProperty("PFX_PATH");
		TLT_CER_PATH=Pro_Sys_FilePath_Config.getProperty("TLT_CER_PATH");
		TLT_VER_NAME=Pro_Sys_FilePath_Config.getProperty("TLT_VER_NAME");
		TLT_VER_PASSWORD=Pro_Sys_FilePath_Config.getProperty("TLT_VER_PASSWORD");
		BUSINESS_CODE=Pro_Sys_FilePath_Config.getProperty("BUSINESS_CODE");
		MERCHANT_ID=Pro_Sys_FilePath_Config.getProperty("MERCHANT_ID");
		PFX_PASSWORD=Pro_Sys_FilePath_Config.getProperty("PFX_PASSWORD");
		OPENID_TEST=Pro_Sys_FilePath_Config.getProperty("OPENID_TEST");
		VERIFY_MERCHANT_ID=Pro_Sys_FilePath_Config.getProperty("VERIFY_MERCHANT_ID");
		VERIFY_KEY=Pro_Sys_FilePath_Config.getProperty("VERIFY_KEY");
		VERIFY_URL=Pro_Sys_FilePath_Config.getProperty("VERIFY_URL");
		TLTPAY_KEY=Pro_Sys_FilePath_Config.getProperty("TLTPAY_KEY");
		TLTLOGNIN_URL=Pro_Sys_FilePath_Config.getProperty("TLTLOGNIN_URL");
		TLTPAY_URL=Pro_Sys_FilePath_Config.getProperty("TLTPAY_URL");
		TLTMERCHANT_ID=Pro_Sys_FilePath_Config.getProperty("TLTMERCHANT_ID");
		TLTRECEIVE_URL=Pro_Sys_FilePath_Config.getProperty("TLTRECEIVE_URL");
		TLTPICKUP_URL=Pro_Sys_FilePath_Config.getProperty("TLTPICKUP_URL");
		DXY_APPID=Pro_Sys_FilePath_Config.getProperty("DXY_APPID");
		DXY_APPSIGNKEY=Pro_Sys_FilePath_Config.getProperty("DXY_APPSIGNKEY");
		DXY_URL=Pro_Sys_FilePath_Config.getProperty("DXY_URL");
	}
}
