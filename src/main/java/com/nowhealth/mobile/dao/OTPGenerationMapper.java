package com.nowhealth.mobile.dao;

import java.util.List;
import java.util.Map;

import com.nowhealth.mobile.entity.OTPGeneration;



public interface OTPGenerationMapper {
    int deleteByPrimaryKey(Integer otpid);

    int insert(OTPGeneration record);

    int insertSelective(OTPGeneration record);

    OTPGeneration selectByPrimaryKey(Integer otpid);

    int updateByPrimaryKeySelective(OTPGeneration record);

    int updateByPrimaryKey(OTPGeneration record);
    
    List<OTPGeneration> listOTPGeneration(Map params);
}