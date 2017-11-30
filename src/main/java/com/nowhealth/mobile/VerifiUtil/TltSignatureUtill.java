package com.nowhealth.mobile.VerifiUtil;

import java.io.File;
import java.security.Provider;


public class TltSignatureUtill {
	
private static Provider prvd=null;
	
	/*
	 *获取签名
	 */
	public static String signMsg(String strData, String pathPfx, String pass,boolean restrict) throws Exception{
		return signMsg(strData, pathPfx, pass, prvd, restrict);
	}
	
	//获取签名后的报文
	private static String signMsg(String strData, String pathPfx, String pass,Provider prv,boolean restrict) throws Exception{
		final String IDD_STR="<SIGNED_MSG></SIGNED_MSG>";
		String strMsg = strData.replaceAll(IDD_STR, "");
		//数字签名
		String signedMsg = signPlain(strMsg, pathPfx, pass,prv,restrict);
		System.out.print("数字签名结果为=============="+signedMsg+"===================");
		//组装签名后的报文
		String strRnt = strData.replaceAll(IDD_STR, "<SIGNED_MSG>" + signedMsg + "</SIGNED_MSG>");
		return strRnt;
	}
	
	//数字签名
	private static String signPlain(String strData, String pathPfx, String pass,Provider prv,boolean restrict) throws Exception{
		PaymentSign.initProvider();
		CryptInf crypt;
		//实现层传入过来的restrict=false;
		if(restrict) crypt=new CryptUnderRestrict("GBK");
		else crypt=new CryptNoRestrict("GBK",prv);
		String strRnt = "";
		//签名方法
		if (crypt.SignMsg(strData, pathPfx, pass)){
			String signedMsg = crypt.getLastSignMsg();
			strRnt = signedMsg;
		}
		else{
			throw new Exception("签名失败");
		}
		return strRnt;		
	}
	
	
	/**
	 * 验证签名
	 */
	/**
	 * 通过响应报文验证签名
	 */
	public static boolean verifySign(String strXML, String cerFile,boolean restrict,boolean isfront) throws Exception
	{
		return verifySign(strXML, cerFile, prvd, restrict,isfront);
	}
	//验证签名
	private static boolean verifySign(String strXML, String cerFile,Provider prv,boolean restrict,boolean isfront) throws Exception
	{
		if(!isfront){
			PaymentSign.initProvider();
			// 签名
			CryptInf crypt;
			//此时传入过来的restrict=false;
			if(restrict) crypt=new CryptUnderRestrict("GBK");
			else crypt=new CryptNoRestrict("GBK",prv);
			//
			File file = new File(cerFile);
			if (!file.exists()) throw new Exception("文件"+cerFile+"不存在");
			int iStart = strXML.indexOf("<SIGNED_MSG>");
			if(iStart==-1) throw new Exception("XML报文中不存在<SIGNED_MSG>");
			int end = strXML.indexOf("</SIGNED_MSG>");
			if(end==-1) throw new Exception("XML报文中不存在</SIGNED_MSG>");
			//获取返回的signature
			String signedMsg = strXML.substring(iStart + 12, end);
			String strMsg = strXML.substring(0, iStart) + strXML.substring(end + 13);
			System.out.println(strMsg);
			System.out.println(strMsg.length());
			System.out.println(signedMsg.toLowerCase());
			return crypt.VerifyMsg(signedMsg.toLowerCase(), strMsg,cerFile);
		}else{
			return true;
		}
		
	}
}
