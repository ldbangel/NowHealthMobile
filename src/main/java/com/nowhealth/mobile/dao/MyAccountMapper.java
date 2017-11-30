package com.nowhealth.mobile.dao;

import java.util.List;
import java.util.Map;

import com.nowhealth.mobile.entity.BaseInfor;

public interface MyAccountMapper {
	public List<BaseInfor> getOrders1(Map<String,Object> map);
	
	public List<BaseInfor> getOrders2(Map<String,Object> map);
	
	public List<BaseInfor> getOrders3(Map<String,Object> map);
	
	public List<BaseInfor> getOrdersByDate(Map<String,Object> map);
}
