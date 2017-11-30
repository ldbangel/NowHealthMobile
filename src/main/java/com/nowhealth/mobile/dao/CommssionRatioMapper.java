package com.nowhealth.mobile.dao;

import com.nowhealth.mobile.entity.CommssionRatio;

public interface CommssionRatioMapper {

    int insert(CommssionRatio record);

    int insertSelective(CommssionRatio record);

    CommssionRatio selectByPrimaryKey(Integer comratioid);

    int updateByPrimaryKeySelective(CommssionRatio record);

    int updateByPrimaryKey(CommssionRatio record);
}