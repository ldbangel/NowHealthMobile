package com.nowhealth.mobile.dms;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.nowhealth.mobile.dao.BaseInforMapper;

@Repository
public class MyAccountManageService {
	private static final Logger logger = Logger.getLogger(MyAccountManageService.class);
	
	@Resource
	private BaseInforMapper baseInforMapper;
	
	public int cancelOrder(String orderNo){
		int result = baseInforMapper.cancelOrderByorderNo(orderNo);
		return result;
	}
	
	public void autoCancelOrders(){
		int result = baseInforMapper.autoBatchCancelOrder();
		logger.info("定时任务批量撤单数量："+result);
	}
}
