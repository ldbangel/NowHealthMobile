package com.nowhealth.mobile.dao;

import com.nowhealth.mobile.entity.BankInfor;

public interface BankInforMapper {
    int deleteByPrimaryKey(Integer bankid);

    int insert(BankInfor record);

    int insertSelective(BankInfor record);

    BankInfor selectByPrimaryKey(Integer bankid);

    int updateByPrimaryKeySelective(BankInfor record);

    int updateByPrimaryKey(BankInfor record);
}