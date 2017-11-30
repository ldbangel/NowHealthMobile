package com.nowhealth.mobile.dms;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.nowhealth.mobile.dao.BaseInforMapper;
import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.serviceImpl.PersonInforServiceImpl;

@Repository
public class PaymentInforDataManageService {
	private static final Logger logger = Logger
			.getLogger(PersonInforServiceImpl.class);
	@Resource
	private BaseInforMapper baseinforMapper;
	/**
	 * 调用第三方支付后，修改订单状态，和支付状态
	 */
	public int operatePaymentInfo (BaseInfor baseinfo){
		int result=-1;
		baseinfo.setOrderstart("30");
		logger.info("支付成功,设置订单状态为30......");
		result=baseinforMapper.updateByPrimaryKeySelective(baseinfo);
		return result;		
	}
}
