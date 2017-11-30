package com.nowhealth.mobile.dao;

import java.util.Map;

import com.nowhealth.mobile.entity.UserInfor;

public interface UserInforMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(UserInfor record);

    int insertSelective(UserInfor record);

    UserInfor selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(UserInfor record);

    int updateByPrimaryKey(UserInfor record);

	UserInfor selectByName(UserInfor userinfor);
	
    UserInfor selectByUser(Map<String,Object> map);
    
    UserInfor selectByUser2(Map<String,Object> map);
}
