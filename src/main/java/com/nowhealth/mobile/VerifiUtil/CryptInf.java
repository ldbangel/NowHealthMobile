package com.nowhealth.mobile.VerifiUtil;

public interface CryptInf{   
	//验证签名
	public boolean VerifyMsg(String TobeVerified, String PlainText, String CertFile) throws Exception;
	//签名
	public boolean SignMsg(String TobeSigned, String KeyFile, String PassWord) throws Exception;
	//获取签名结果
	public String getLastSignMsg();
}
