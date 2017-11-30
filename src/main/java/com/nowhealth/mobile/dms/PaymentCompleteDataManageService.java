package com.nowhealth.mobile.dms;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.nowhealth.mobile.dao.BaseInforMapper;
import com.nowhealth.mobile.dao.QueryinforMapper;
import com.nowhealth.mobile.dao.UserInforMapper;
import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.entity.CommssionRatio;
import com.nowhealth.mobile.entity.QueryInfor;
import com.nowhealth.mobile.entity.UserInfor;
import com.nowhealth.mobile.serviceImpl.PersonInforServiceImpl;

@Repository
public class PaymentCompleteDataManageService {
	private static final Logger logger = Logger
			.getLogger(PersonInforServiceImpl.class);
	@Resource
	private BaseInforMapper baseinforMapper;
	@Resource
	private QueryinforMapper queryinforMapper;
	@Resource
	private UserInforMapper userInforMapper;
	@Resource
	private LoginManageService loginManageService;
	/**
	 * 跳转到成功支付界面，并修改订单状态,咨询码状态，和佣金状态
	 */
	public int updatePaymentAndBaseinfor(String openId,BaseInfor baseinfor,HttpServletRequest request){
		int result=-1;
		String orderNo =baseinfor.getOrderno();
		double totalAmount = baseinfor.getTotalamount(); //获取订单总额
		List<QueryInfor> listqueryCode = new ArrayList<QueryInfor>();
		//HttpSession session = request.getSession();
		QueryInfor queryInfor = new QueryInfor();
		UserInfor userinfor = new UserInfor();
		CommssionRatio comssionRatio = new CommssionRatio();
		userinfor.setUsername(openId);
		//根据openId查询用户信息
		userinfor =userInforMapper.selectByName(userinfor);
		baseinfor.setOrderstart("40");
		baseinfor.setQuerycode(baseinfor.getPurchaserphoneno()); //添加手机号为查询码
		Integer agentCode = 0;
		logger.info("支付成功,设置订单状态为40......");
		logger.info("咨询码为：" +baseinfor.getPurchaserphoneno());
		//支付成功后查询咨询码
		/*listqueryCode=queryinforMapper.listQueryCode();
		if(listqueryCode!=null){
			String baseinfororderno="";   //订单号
		    String querycode="";          //咨询码
		    String querycodestart="";     //咨询码状态
			for(int i=0;i<listqueryCode.size();i++){
				queryInfor = listqueryCode.get(i);
				if(queryInfor!=null){
					baseinfororderno = queryInfor.getBaseinfororderno();
					querycodestart = queryInfor.getQuerycodestart();
					//当订单号为空并且咨询码状态为10时，即该咨询码未被使用
					if( "".equals(baseinfororderno) && "10".equals(querycodestart)){
						queryInfor.setBaseinfororderno(orderNo);
						querycode = queryInfor.getQuerycode();
						//设置咨询码状态为20(10：未使用 ; 20:已使用)
						queryInfor.setQuerycodestart("20");
						baseinfor.setQuerycode(querycode);
						logger.info("支付成功,咨询码状态为：20 ");
						queryinforMapper.updateByPrimaryKeySelective(queryInfor);	
						session.setAttribute("queryInfor", queryInfor);
						break;
					}
				}
			}
		}*/
		logger.info("=====当前订单状态为 ==============="+baseinfor.getOrderstart());
		if(userinfor!=null){
			agentCode = userinfor.getAgentflag();
			logger.info("=====该订单为代理人定单 =====代理人编码为 ："+agentCode);
			//代理人结算佣金
			if(agentCode !=null && agentCode == 1){
				Double comRatio = 0d;
				//获取佣金比例
				comssionRatio = loginManageService.getCommssionRatio(1);
				if(comssionRatio !=null){
					comRatio = comssionRatio.getComratio();
					logger.info("结算佣金的比例为 ：" + comRatio);
				}
				//支付成功结算佣金(--暂定佣金比例为comRatio--)
				Float commission = (float)(Math.round((totalAmount*(comRatio))*100))/100;
				if(commission!=null){
					baseinfor.setCommission(commission);
					//支付完成后，设置佣金状态为10（10：未提取状态）
					baseinfor.setCommissionstart("10");
				 }
				logger.info("支付完成结算佣金为 ：" +commission +" 设置佣金状态为 10");
				result=baseinforMapper.updateBasebyorderNo(baseinfor);
			}else{
				logger.info("更新订单状态为 :"+baseinfor.getOrderstart());
				result=baseinforMapper.updateBasebyorderNo(baseinfor);
			}
		}else{
			result=baseinforMapper.updateBasebyorderNo(baseinfor);
		}
		return result;
	}
	
	/**
	 * 丁香园发送成功更新baseInfor
	 */
	public int updateBasebyfromDxy(BaseInfor baseinfor,HttpServletRequest request){
		int result=-1;
		logger.info("更新订单状态为 :"+baseinfor.getOrderstart());
		result=baseinforMapper.updateBasebyorderNo(baseinfor);
		return result;
	}
	
}
