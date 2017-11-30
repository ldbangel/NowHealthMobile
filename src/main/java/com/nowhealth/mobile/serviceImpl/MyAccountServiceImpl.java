package com.nowhealth.mobile.serviceImpl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.github.pagehelper.PageHelper;
import com.nowhealth.mobile.dao.BaseInforMapper;
import com.nowhealth.mobile.dao.MyAccountMapper;
import com.nowhealth.mobile.dao.UserInforMapper;
import com.nowhealth.mobile.dao.UsercommssionInforMapper;
import com.nowhealth.mobile.dms.LoginManageService;
import com.nowhealth.mobile.dms.MyAccountManageService;
import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.entity.PageBean;
import com.nowhealth.mobile.entity.UserCommssionInfor;
import com.nowhealth.mobile.entity.UserInfor;
import com.nowhealth.mobile.service.LoginService;
import com.nowhealth.mobile.service.MyAccountService;
import com.nowhealth.mobile.service.OTPValidationService;
import com.nowhealth.mobile.service.PersonInforService;
import com.nowhealth.mobile.service.VerificationCadeService;
import com.nowhealth.mobile.utils.DateFormatUtils;
import com.nowhealth.mobile.utils.DesUtil;

@Service("myAccountService")
public class MyAccountServiceImpl implements MyAccountService {
	private static final Logger logger = Logger
			.getLogger(MyAccountServiceImpl.class);
	@Resource
	private MyAccountMapper myAccountMapper;
	@Resource
	private BaseInforMapper baseinforMapper;
	@Resource
	private UserInforMapper userInforMapper;
	@Resource
	private LoginService loginService;
	@Resource
	private LoginManageService loginManageService;
	@Resource
	private UsercommssionInforMapper usercominforMapper;
	@Resource
	private VerificationCadeService verificationCadeService;
	@Resource
	private MyAccountManageService myAccountManageService;
	@Resource
	private PersonInforService personInforService;

	public Map<String, Object> getMyAccountInitData(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfor userinfor = (UserInfor) session.getAttribute("loginUser");
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> map1 = new HashMap<String, Object>();
		map1.put("userid", userinfor.getUserid());
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		PageHelper.startPage(pageNum, pageSize);
		List<BaseInfor> list1 = myAccountMapper.getOrders1(map1);
		PageHelper.startPage(pageNum, pageSize);
		List<BaseInfor> list2 = myAccountMapper.getOrders2(map1);
		PageHelper.startPage(pageNum, pageSize);
		List<BaseInfor> list3 = myAccountMapper.getOrders3(map1);
		PageBean<BaseInfor> page1 = new PageBean<BaseInfor>(list1);
		PageBean<BaseInfor> page2 = new PageBean<BaseInfor>(list2);
		PageBean<BaseInfor> page3 = new PageBean<BaseInfor>(list3);
		map.put("page1", page1);
		map.put("page2", page2);
		map.put("page3", page3);
		map.put("headportrait", userinfor.getHeadportrait());
		map.put("nickname", userinfor.getNickname());
		return map;
	}

	@Override
	public PageBean<BaseInfor> getCurrentOrders(
			HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		UserInfor userinfor = (UserInfor) session.getAttribute("loginUser");
		map.put("userid", userinfor.getUserid());
		String index = request.getParameter("flag");
		List<BaseInfor> list = null;
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		PageHelper.startPage(pageNum, pageSize);
		if("0".equals(index)){
			list = myAccountMapper.getOrders1(map);
		}else if("1".equals(index)){
			list = myAccountMapper.getOrders2(map);
		}else if("2".equals(index)){
			list = myAccountMapper.getOrders3(map);
		}
		return new PageBean<BaseInfor>(list);
	}

	@Override
	public BaseInfor selectBaseInfor(HttpServletRequest request,String orderno) {
		BaseInfor baseinfor = baseinforMapper.selectByOrderno(orderno);
		HttpSession session = request.getSession();
		session.setAttribute(baseinfor.getOrderno()+"baseinfor", baseinfor);
		return baseinfor;
	}

	/**
	 * 获取佣金总的明细(已支付订单)
	 */
	@SuppressWarnings("unused")
	@Override
	public Map<String, Object> getOrdersAllDetails(HttpServletRequest request,ModelMap modelMap) {
		HttpSession session = request.getSession();
		UserInfor userinfor = (UserInfor) session.getAttribute("loginUser");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userid", userinfor.getUserid());
		Double commission = 0d;          //单笔佣金
		Double totalCommission = 0d;     //总佣金
		Double balance = 0d;             //佣金余额（即可提现余额）
		Double drawalBalance = 0d;       //已提现金额
		Double amount = 0d;              //单笔已提现金额
		String comstate ="";             //佣金状态
		DecimalFormat df = new DecimalFormat("0.00");
		List<BaseInfor> list = myAccountMapper.getOrders2(map);
		for(int i =0; i < list.size();i++){
			commission = list.get(i).getCommission();
			if(commission!=null){
				totalCommission = commission + totalCommission;  //我的收入总额
			}
		}
		userinfor.setTotaldraAmount(String.valueOf(totalCommission));
		//我的收入不为空(再查有没有提现)
		if(totalCommission!=null){
			UserInfor usinfor = new UserInfor();
			usinfor = userInforMapper.selectByPrimaryKey(userinfor.getUserid());
			if(userinfor.getWithdrawal()!=null){
			  drawalBalance = Double.parseDouble(usinfor.getWithdrawal());   //可提现余额
			}
			if(drawalBalance!=null){
				balance = totalCommission - drawalBalance;   //佣金余额(可提现金额)
			}else{
				balance = totalCommission;
			}
			userinfor.setDrawalamount(df.format(balance));
			loginManageService.updateuserinfor(userinfor);
		}else{
			userinfor.setDrawalamount(df.format(totalCommission));
			loginManageService.updateuserinfor(userinfor);
		}
		map.put("list", list);
		map.put("totalCommission", totalCommission);
		map.put("balance", balance);
		return map;
	}
	
	/**
	 * 页面展示佣金明细
	 */
	@Override
	public Map<String, Object> getOrdersDetails(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfor userinfor = (UserInfor) session.getAttribute("loginUser");
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> map1 = new HashMap<String, Object>();
		String searchDate = request.getParameter("searchDate");
		map1.put("userid", userinfor.getUserid());
		map1.put("searchDate", searchDate);
		//map1.put("searchDate", "2017-07-01"); //测试======================
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		//根据时间查询订单明细
		PageHelper.startPage(pageNum, pageSize);
		List<BaseInfor> list1 = myAccountMapper.getOrdersByDate(map1);
		//根据userId查出的所有订单
		PageHelper.startPage(pageNum, pageSize);
		List<UserCommssionInfor> list = usercominforMapper.selectByUserId(map1);
		PageBean<BaseInfor> page = new PageBean<BaseInfor>(list1);
		PageBean<UserCommssionInfor> page1 = new PageBean<UserCommssionInfor>(list);
		map.put("page", page);   //收入明细
		map.put("page1", page1); //余额明细（已提现明细）
		return map;
	}

	/**
	 * 申请提现
	 */
	@Override
	public String cashDrawal(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfor userinfor = new UserInfor();
		String userId = request.getParameter("userId");
		String result="";
		String userBankCard = "";
		String bankPhoneNo = ""; //银行预留手机号
		logger.info("当前申请提现用户的Id: " + userId);
		userinfor=loginService.selectUserById(Integer.parseInt(userId)); //获取用户信息
		if(userinfor != null){
			userBankCard = userinfor.getBanknumber();
			bankPhoneNo = userinfor.getBankphoneno();
			if(userBankCard !=null && !"".equals(userBankCard)){
				logger.info("用户银行卡已绑定.预留手机号码为 ： "+ bankPhoneNo);
				result ="1";    //1为已绑定
			}else{
				logger.info("用户银行卡未绑定");
				result ="";     //""为未绑定
			}
		}
		return result;
	}

	@Override
	public String validateUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfor userinfor = (UserInfor) session.getAttribute("loginUser");
		int userId = userinfor.getUserid();
		String phoneNo = request.getParameter("phoneNo");
		String result="";
		String bankPhoneNo = ""; //银行预留手机号
		logger.info("当前提现用户的Id: " + userId);
		userinfor=loginService.selectUserById(userId); //获取用户信息
		if(userinfor !=null){
		    bankPhoneNo = userinfor.getBankphoneno();
		    if(bankPhoneNo!=null && bankPhoneNo.equals(phoneNo)){
		    	logger.info("用户银行卡预留手机号码为 ： "+ bankPhoneNo);
		    	result ="1";
		    }else{
		    	logger.info("手机号与银行预留手机号不一致！");
		    	result ="";
		    }
		}
		return result;
	}
    
	/**
	 * 佣金提现
	 */
	@SuppressWarnings("unused")
	@Override
	public String drawalAmount(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfor userinfor = (UserInfor) session.getAttribute("loginUser");
		Double amount = Double.valueOf(request.getParameter("amount"))/100; //前台传过来是以分为单位的，这里转成元为单位
		String jyPassword = request.getParameter("jyPassword");
		String phoneNo = request.getParameter("phoneNo");
		String phoneCheckCode = request.getParameter("phoneCheckCode");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userid", userinfor.getUserid());
		String  jypassword ="";   //交易密码
		String  totalDraAmount="";//佣金总额
		String nowDate="";        //系统当前时间
		int record = 0;           //提现记录数
		String result ="";
		int userId = userinfor.getUserid();
		logger.info("当前提现用户的Id: " + userId);
		userinfor=loginService.selectUserById(userId);    //获取用户信息
		if(userinfor !=null){
		     jypassword = userinfor.getCashDealpass();    //提现交易密码
		     totalDraAmount=userinfor.getTotaldraAmount();//佣金总额
		     try {
				if(jypassword !=null && jypassword !="" && DesUtil.decrypt(jypassword).equals(jyPassword)){
				  logger.info("====提现密码为输入正确！=====");
			      //校验验证码
				  //TODD	
				  result =  checkPhoneCode(request,phoneNo,phoneCheckCode);
				 if(result != null && "true".equals(result)){
					logger.info("====提现验证码校验正确！=====");
				    nowDate = DateFormatUtils.getSystemDateByYmd();//获取系统当前时间
				    //nowDate ="2017-08-17";                       //测试时间
				    record = usercominforMapper.selectByNowTime(nowDate);
				    logger.info("当前提现记录数为 ：" +record);
				  if(record == 0){
			        if(amount >= 0.01 && amount<=200){     //测试 10 - 100
					 logger.info("当前提现金额为 ： "+ amount );
					 result = verificationCadeService.commissionCashWithdraw(request);
					 //提现成功更新userInfor
					 if(result!=null && result!="" && "1".equals(result)){
						 Double draAmount = 0d;   //单笔提现金额
						 Double drawalBalance=0d; //已提现总额
						 Double balance =0d;
						 //获取已提现金额列表
						 List<UserCommssionInfor> list = usercominforMapper.selectByUserId(map);
						 for(int i =0; i<list.size(); i++){
						    draAmount  = list.get(i).getAmount();  
							if(draAmount !=null){
								drawalBalance = draAmount + drawalBalance;   //已提现金额
							}
						 }
						 logger.info("当前已提现总额为 ： "+ drawalBalance );
						 userinfor.setWithdrawal(String.valueOf(drawalBalance));
						 //结算佣金余额
						 if(drawalBalance!=null){
								balance = Double.parseDouble(totalDraAmount) - drawalBalance;   //(可提现金额)
						 }else{
								balance = Double.parseDouble(totalDraAmount);
						 }
						 logger.info("当前可提现余额为 ： "+ balance );
					     userinfor.setDrawalamount(String.valueOf(balance));
						 //更新已提现金额至userInfor表
						 loginManageService.updateuserinfor(userinfor);
						 }
					  }else{
						 logger.info("当前提现金额为 不满足提现规则。 " );
						 result ="ruleErr";
					  }
				  }else{
					   logger.info("提现次数超限");
					   result ="3";
				  }
				 }else{
					   logger.info("提现验证码校验错误");
					   result ="codeErr";
				 }
				}else{
					   logger.info("====提现密码为输入错误！=====");
					   result ="passErr"; 
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		session.setAttribute("loginUser", userinfor);
		return result;
	}

	
	/**
	 * 根据时间查询佣金明细
	 */
	@Override
	public Map<String, Object> getOrdersDetailsByDate(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserInfor userinfor = (UserInfor) session.getAttribute("loginUser");
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> map1 = new HashMap<String, Object>();
		String orderSerchDate = request.getParameter("orderSerchDate");
		map1.put("userid", userinfor.getUserid());
		map1.put("serchDate", orderSerchDate);
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		PageHelper.startPage(pageNum, pageSize);
		//根据userId查出所有订单
		List<BaseInfor> list = myAccountMapper.getOrdersByDate(map1);
		PageBean<BaseInfor> page = new PageBean<BaseInfor>(list);
		map.put("page", page);
		return map;
	}

	//查询用户的账户信息
	public UserInfor selectUserInfor(HttpServletRequest request) {
		HttpSession session=request.getSession();
		UserInfor user=null;
		user=(UserInfor) session.getAttribute("loginUser");
		int userid=user.getUserid();
		logger.info("查询用户账户的userId为"+userid);
		user=userInforMapper.selectByPrimaryKey(userid);
		//在这里测试解密，将账号与账户名按照需求展示		
		String banknumber="";
		String payee="";
			
		try {
			if(user.getBanknumber()!=null && !"".equals(user.getBanknumber())){
				banknumber=user.getBanknumber();//银行账户
			}
			if(user.getPayee()!=null && !"".equals(user.getPayee())){
				payee=user.getPayee();//账户名
			}	
			banknumber=DesUtil.decrypt(banknumber);
			payee=DesUtil.decrypt(payee);
			String banknumberTwo="";
			String payeeTwo="";
			banknumberTwo=banknumber.substring(banknumber.length()-4);//截取解密账户后四位
			payeeTwo=payee.substring(payee.length()-1);//截取解密后账户名的后一位
			StringBuffer bank = new StringBuffer(); 
			StringBuffer accountName = new StringBuffer();  
			for (int i = 0; i < banknumber.length()-4; i++) {
				bank=bank.append("*");						
			}
			for (int i = 0; i < payee.length()-1; i++) {
				accountName=accountName.append("*");						
			}
			bank=bank.append(banknumberTwo);
			accountName=accountName.append(payeeTwo);
			user.setBanknumber(bank.toString());
			user.setPayee(accountName.toString());
			System.out.println("展示给用户的银行账户和账户名分别为"+bank.toString()+"===="+accountName.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return user;
	}

	//取消订单
	public int cancelOrder(HttpServletRequest request) {
		String orderNo = request.getParameter("orderno");
		int result = myAccountManageService.cancelOrder(orderNo);
		return result;
	}
	
	//定时任务--撤销当天未支付的订单(每天凌晨1点自动撤销)
	public void autoCancelOrders(){
		Date d = new Date();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String dateNowStr = sdf.format(d); 
		logger.info("开始执行定时撤单任务！当前时间为："+dateNowStr);
		myAccountManageService.autoCancelOrders();
	}
	
	//提现验证码校验
	public String checkPhoneCode(HttpServletRequest request,String phoneno,String checkCode){
		String codeExist="";
		String phoneNo = request.getParameter("phoneNo");
		try {
			codeExist=personInforService.phoneCodeExist(phoneNo,checkCode,request);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.info("用户校验手机验证码异常"+str);
		}
		return codeExist;
	}
}

