package com.nowhealth.mobile.dao;

import com.nowhealth.mobile.entity.WechatBind;

public interface WechatBindMapper {
    int deleteByPrimaryKey(Integer binduserid);

    int insert(WechatBind record);

    int insertSelective(WechatBind record);

    WechatBind selectByPrimaryKey(Integer binduserid);
    
    WechatBind selectByOpenId(String openId);

    int updateByPrimaryKeySelective(WechatBind record);

    int updateByPrimaryKey(WechatBind record);
}