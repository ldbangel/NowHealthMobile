package com.nowhealth.mobile.service;

import javax.servlet.http.HttpServletRequest;

import com.nowhealth.mobile.entity.UserInfor;



public interface LoginService {
	
	public String userExists(UserInfor userinfor,HttpServletRequest request) throws Exception;
	
	public String registUser(UserInfor userinfor,HttpServletRequest request);
	
	public UserInfor selectUserById(int id);
}
