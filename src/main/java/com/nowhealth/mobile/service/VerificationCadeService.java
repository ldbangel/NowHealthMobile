package com.nowhealth.mobile.service;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VerificationCadeService {
	//验证卡号
	public String userVeriCarid(HttpServletRequest httprequest,HttpServletResponse httprespon) throws ParseException;
	//验证银行卡账号是否为 所选银行卡
	public String testBank(HttpServletRequest httprequest,HttpServletResponse httprespon);
	//实时代付
	public String commissionCashWithdraw(HttpServletRequest request);
	//添加银行提现密码
	public String addPassword(HttpServletRequest request);
	//通联通交易结果查询,传入参数为交易批次号
	public String getTransactionResult(HttpServletRequest request,String seqsn);
	
}
