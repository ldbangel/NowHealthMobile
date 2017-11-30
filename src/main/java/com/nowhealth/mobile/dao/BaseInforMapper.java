package com.nowhealth.mobile.dao;

import java.util.List;
import java.util.Map;

import com.nowhealth.mobile.entity.BaseInfor;

public interface BaseInforMapper {
    int deleteByPrimaryKey(Integer orderid);

    int insert(BaseInfor record);

    int insertSelective(BaseInfor record);

    BaseInfor selectByPrimaryKey(Integer orderid);

    int updateByPrimaryKeySelective(BaseInfor record);

    int updateByPrimaryKey(BaseInfor record);
    
    String getOrderNo(Map<String, Object> map);
    
    BaseInfor selectByOrderno(String orderno);
    
    List<BaseInfor> selectByPurchaserPhoneNo(String purchaserPhoneNo);
    
    int updateBasebyorderNo(BaseInfor base);
    
    int cancelOrderByorderNo(String orderno);
    
    int autoBatchCancelOrder();
}