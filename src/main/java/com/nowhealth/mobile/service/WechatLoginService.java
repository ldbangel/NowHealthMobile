package com.nowhealth.mobile.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WechatLoginService {
	public String getOpenId(HttpServletRequest httprequest,HttpServletResponse response);
	public String bindUser(HttpServletRequest httprequest,HttpServletResponse response);
	public Map<String,Object> getUserinfo(String openid,String access_token);
	public String userEverRegist(String username);
}
