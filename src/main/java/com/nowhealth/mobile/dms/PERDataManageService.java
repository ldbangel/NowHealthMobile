package com.nowhealth.mobile.dms;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.nowhealth.mobile.dao.BaseInforMapper;
import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.utils.DateFormatUtils;
import com.nowhealth.mobile.utils.StringUtils;


@Repository
public class PERDataManageService {
	private static final Logger logger = Logger
			.getLogger(PERDataManageService.class);
	@Resource
	private BaseInforMapper baseinforMapper;
	/**
	 * 存储首页信息并且生成订单号
	 */
	public BaseInfor savefirstScreenData(BaseInfor baseinfor){
		logger.info("生成订单号开始");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int baseinforresult = 0;
		String orderNo = "";
		String dataTime = DateFormatUtils.getSystemDate();
		resultMap.put("orderNamePre", "LW");
		resultMap.put("number", 8);
		resultMap.put("orderNo", "@orderNo");
		orderNo = baseinforMapper.getOrderNo(resultMap)+StringUtils.getValidateNo(true,4);// 生成订单号	
			logger.info("生成订单号 " + orderNo);
			if (StringUtils.checkStringEmpty(orderNo)) {
				logger.info(" Starts save baseinfor information first  ");
				if (baseinfor != null) {
					baseinfor.setOrderno(orderNo);
				}
				baseinfor.setOrderstart("10");//设置订单状态
				baseinforresult = baseinforMapper.insertSelective(baseinfor);
				logger.info("首页信息存进数据库  " + baseinforresult);
				logger.info(" 订单号存入数据库结束 ");
			}	
			
		return baseinfor;
		
	}
	
	/**
	 * 根据订单查询baseinfor
	 */
	public BaseInfor selelctByorder(String orderno){
		 BaseInfor baseinfor=baseinforMapper.selectByOrderno(orderno);
		return baseinfor;		
	}
	
	/**
	 * 更新baseinfor表，存储人员信息
	 */
	public int updatebasein(BaseInfor baseinfor){
		logger.info("人员信息页面数据更新开始...");
		int result=0;
		if(baseinfor!=null){
			result=baseinforMapper.updateByPrimaryKeySelective(baseinfor);	
		}		
		return result;
		
	}
	
}
