package com.nowhealth.mobile.dao;

import java.util.List;
import java.util.Map;

import com.nowhealth.mobile.entity.UserCommssionInfor;

public interface UsercommssionInforMapper {
	
    int insert(UserCommssionInfor record);

    int insertSelective(UserCommssionInfor record);

    UserCommssionInfor selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(UserCommssionInfor record);

    int updateByPrimaryKey(UserCommssionInfor record);
    
    public List<UserCommssionInfor> selectByUserId(Map<String,Object> map);
    
    int selectByNowTime(String nowTime);

}