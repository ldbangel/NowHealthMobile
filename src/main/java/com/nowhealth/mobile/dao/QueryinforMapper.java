package com.nowhealth.mobile.dao;

import java.util.List;

import com.nowhealth.mobile.entity.QueryInfor;

public interface QueryinforMapper {

    int deleteByPrimaryKey(Integer querycodeid);

    int insert(QueryInfor record);

    int insertSelective(QueryInfor record);

    QueryInfor selectByPrimaryKey(Integer querycodeid);

    int updateByPrimaryKeySelective(QueryInfor record);

    int updateByPrimaryKey(QueryInfor record);
    
    List<QueryInfor> listQueryCode();
}