package com.nowhealth.mobile.serviceImpl;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowhealth.mobile.dms.LoginManageService;
import com.nowhealth.mobile.entity.CommssionRatio;
import com.nowhealth.mobile.entity.UserInfor;
import com.nowhealth.mobile.entity.WechatBind;
import com.nowhealth.mobile.entity.WechatConfig;
import com.nowhealth.mobile.service.WechatLoginService;
import com.nowhealth.mobile.utils.HttpRequestUtility;
import com.nowhealth.mobile.utils.JsonUtility;
import com.nowhealth.mobile.utils.SHA1;
import com.nowhealth.mobile.utils.StringUtils;
import com.nowhealth.mobile.utils.SysConstantsConfig;


@Service
public class WechatLoginServiceImpl implements WechatLoginService {
	private static final Logger logger = Logger.getLogger(WechatLoginService.class);
	@Resource
	private LoginManageService loginManageService;
	/**
	 * 获取微信用户的OpenId 孙小东
	 */
	@Override
	public String getOpenId(HttpServletRequest httprequest,
			HttpServletResponse response) {
		logger.info("开始获取用户openId");
		String code = httprequest.getParameter("code");
		String openId="";
		if(StringUtils.checkStringEmpty(code)){
			String url = SysConstantsConfig.WECHAT_URL + "sns/oauth2/access_token";
			HttpRequestUtility httpRequestUtility = new HttpRequestUtility();
			List<NameValuePair> params = new ArrayList<NameValuePair>();			
			params.add(new BasicNameValuePair("appid",
					SysConstantsConfig.WECHAT_APPID));
			params.add(new BasicNameValuePair("secret",
					SysConstantsConfig.WECHAT_APPSECRET));
			params.add(new BasicNameValuePair("code",
					code));
			params.add(new BasicNameValuePair("grant_type",
					"authorization_code"));			
			String result = httpRequestUtility.getResponsebyGet(url, params);
			if (StringUtils.checkStringEmpty(result)) {
				String errorCode = JsonUtility.getVauleFromJson("errcode", result);
				String errorMessage = JsonUtility.getVauleFromJson("errmsg", result);
				if (StringUtils.checkStringEmpty(errorCode)
						&& !StringUtils.checkStringEmpty(errorMessage)) {
					logger.info("开始获取用户openId失败，返回错误码为 ：" + errorCode
							+ " 错误消息为 ：" + errorMessage);
				} else {
					openId = JsonUtility.getVauleFromJson("openid",
							result);
					logger.info("开始获取用户openId成功，openId  ：" + openId);
				}
			}
		}
		return openId;
	}
	
	/**
	 * 动态生成微信API access_token 调用微信提供的接口必须要这个属性 孙小东
	 */
	public String getaccessToken() {
		logger.info("开始动态生成access_token");
		String accessToken = "";
		String errorCode = "";
		String errorMessage = "";
		String url = SysConstantsConfig.WECHAT_URL + "cgi-bin/token";
		HttpRequestUtility httpRequestUtility = new HttpRequestUtility();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("grant_type", "client_credential"));
		params.add(new BasicNameValuePair("appid",
				SysConstantsConfig.WECHAT_APPID));
		params.add(new BasicNameValuePair("secret",
				SysConstantsConfig.WECHAT_APPSECRET));
		String response = httpRequestUtility.getResponsebyGet(url, params);
		if (StringUtils.checkStringEmpty(response)) {
			errorCode = JsonUtility.getVauleFromJson("errcode", response);
			errorMessage = JsonUtility.getVauleFromJson("errmsg", response);
			if (StringUtils.checkStringEmpty(errorCode)
					&& !StringUtils.checkStringEmpty(errorMessage)) {
				logger.info("开始动态生成access_token失败，返回错误码为 ：" + errorCode
						+ " 错误消息为 ：" + errorMessage);
			} else {
				accessToken = JsonUtility.getVauleFromJson("access_token",
						response);
				logger.info("开始动态生成access_token成功，accessToken  ：" + accessToken);
			}
		}
		return accessToken;
	}
	
	/**
	 * 获取微信用户的基本信息 zhd
	 * @param  request
	 * @param  openid
	 * @param  access_token
	 * @return UserInfor
	 */
	 
	public UserInfor getUserinfo(HttpServletRequest request, String openid,String access_token){
		logger.info("开始获取用户基本信息--头像--昵称等");
		String errorCode = "";
		String errorMessage = "";
		UserInfor userInfor = new UserInfor();
		String url = SysConstantsConfig.WECHAT_URL + "cgi-bin/user/info";
		HttpRequestUtility httpRequestUtility = new HttpRequestUtility();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("access_token", access_token));
		params.add(new BasicNameValuePair("openid", openid));
		params.add(new BasicNameValuePair("lang", "zh_CN"));
		String response = httpRequestUtility.getResponsebyGet(url, params);
		if (StringUtils.checkStringEmpty(response)) {
			errorCode = JsonUtility.getVauleFromJson("errcode", response);
			errorMessage = JsonUtility.getVauleFromJson("errmsg", response);
			if (StringUtils.checkStringEmpty(errorCode)
					&& !StringUtils.checkStringEmpty(errorMessage)) {
				logger.info("获取用户信息失败，返回错误码为 ：" + errorCode
						+ " 错误消息为 ：" + errorMessage);
			} else {
				String headimgurl = JsonUtility.getVauleFromJson("headimgurl",response);
				String nickname = JsonUtility.getVauleFromJson("nickname",response);
				String sex = JsonUtility.getVauleFromJson("sex",response);
				userInfor.setHeadportrait(headimgurl);
				userInfor.setNickname(nickname);
			}
		}
		return userInfor;
	}
	
	/**
	 * 获取微信用户的基本信息   刘东波
	 */
	public Map<String,Object> getUserinfor(HttpServletRequest request, String openid,String access_token){
		Map<String,Object> map = new HashMap<String, Object>();
		logger.info("开始获取用户基本信息--头像--昵称等");
		String errorCode = "";
		String errorMessage = "";
		String url = SysConstantsConfig.WECHAT_URL + "cgi-bin/user/info";
		HttpRequestUtility httpRequestUtility = new HttpRequestUtility();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("access_token", access_token));
		params.add(new BasicNameValuePair("openid", openid));
		params.add(new BasicNameValuePair("lang", "zh_CN"));
		String response = httpRequestUtility.getResponsebyGet(url, params);
		if (StringUtils.checkStringEmpty(response)) {
			errorCode = JsonUtility.getVauleFromJson("errcode", response);
			errorMessage = JsonUtility.getVauleFromJson("errmsg", response);
			if (StringUtils.checkStringEmpty(errorCode)
					&& !StringUtils.checkStringEmpty(errorMessage)) {
				logger.info("获取用户信息失败，返回错误码为 ：" + errorCode
						+ " 错误消息为 ：" + errorMessage);
			} else {
				String headimgurl = JsonUtility.getVauleFromJson("headimgurl",response);
				String nickname = JsonUtility.getVauleFromJson("nickname",response);
				String sex = JsonUtility.getVauleFromJson("sex",response);
				map.put("headimgurl", headimgurl);
				map.put("nickname", nickname);
				map.put("sex", sex);
			}
		}
		return map;
	}
	
	
	
   /**
    * 微信openId和手机号进行绑定
    */
	
   /* public @ResponseBody String bindUser(HttpServletRequest httprequest,
			HttpServletResponse response) {
		HttpSession session=httprequest.getSession();
		UserInfor loginUser=(UserInfor) session.getAttribute("loginUser");
		String openId=httprequest.getParameter("openId");
		String phoneNo=httprequest.getParameter("phoneNo");		
		logger.info("开始绑定微信用户的手机号，微信openId： "+openId+" 用户手机号为："+phoneNo);
		String resultString="绑定失败";
		WechatBind wechatBind=new  WechatBind();
		UserInfor user=new UserInfor();
		user.setUsername(phoneNo);
		try {
			logger.info("开始查询用户数据");
			UserInfor uu=loginManageService.selectByName(user);
			if(uu!=null){
				logger.info("校验该微信账户是否已经绑定过手机号");
				boolean flag=loginManageService.existFlag(openId);
				if(flag){
                    logger.info("开始绑定过手机号");
					wechatBind.setOpenid(openId);
					wechatBind.setPhoneno(phoneNo);
					wechatBind.setPhoneuserid(uu.getUserid());
					wechatBind.setWechatuserid(loginUser.getUserid());
					int result=loginManageService.saveBindData(wechatBind);
					if(result==1){
						logger.info("绑定成功");
						resultString="绑定成功";
						logger.info("开始更新用户信息");
						if(uu.getAgentFlag()==1){
							loginUser.setAgentFlag(1);
						}
						loginUser.setPhoneno(phoneNo);
						loginUser.setUpdatetimes(DateFormatUtils.getSystemDate());
						logger.info("开始更新用户信息");
						int updateresult=loginManageService.updateUserInfor(loginUser);
						logger.info("结束更新用户信息,更新结果为： "+updateresult);
						session.setAttribute("loginUser", loginUser);
					}else{
						logger.info("绑定失败");
						resultString="绑定失败";
					}
				}else{
					logger.info("该账户已经绑定了手机号，不能够重复绑定");
					resultString="该账户已经绑定了手机号，不能够重复绑定";
				}
			}else{
				logger.info("绑定用户不存在");
				resultString="绑定用户不存在，请检查用户名是否录入正确";
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			e.printStackTrace(new PrintWriter(sw, true));  
			String str = sw.toString();
			logger.error("绑定用户异常"+str);
		}
		return resultString;
	}	*/
	
	/**
	 * 动态生成微信API jsapi_ticket 调用微信提供的接口必须要这个属性 孙小东
	 */

	public String getjsapi_ticket(String accessToken) {
		logger.info("开始动态生成jsapi_ticket");
		String ticket = "";
		String errorCode = "";
		String errorMessage = "";
		String url = SysConstantsConfig.WECHAT_URL + "cgi-bin/ticket/getticket";
		HttpRequestUtility httpRequestUtility = new HttpRequestUtility();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("access_token", accessToken));
		params.add(new BasicNameValuePair("type",
				"jsapi"));		
		String response = httpRequestUtility.getResponsebyGet(url, params);
		if (StringUtils.checkStringEmpty(response)) {
			errorCode = JsonUtility.getVauleFromJson("errcode", response);
			errorMessage = JsonUtility.getVauleFromJson("errmsg", response);
			if (StringUtils.checkStringEmpty(errorCode)	&&!"0".equalsIgnoreCase(errorCode)) {
				logger.info("开始动态生成jsapi_ticket失败，返回错误码为 ：" + errorCode
						+ " 错误消息为 ：" + errorMessage);
			} else {
				ticket = JsonUtility.getVauleFromJson("ticket",
						response);
				logger.info("开始动态生成jsapi_ticket成功，jsapi_ticket  ：" + ticket);
			}
		}
		return ticket;
	}
	
	/**
	 * 获取签名
	 * @param ticket
	 * @return
	 */
 public WechatConfig getSingnature(String ticket,String url){
	 SHA1 sha1=new SHA1();
	 WechatConfig wechatConfig=new WechatConfig();
	String signature="";
	String timestamp = System.currentTimeMillis()/1000+"";
	String noncestr=getRandomString(16);
	signature="jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
	signature=sha1.getDigestOfString(signature.getBytes());
	wechatConfig.setAppid(SysConstantsConfig.WECHAT_APPID);
	wechatConfig.setNonceStr(noncestr);
	wechatConfig.setSignature(signature);
	wechatConfig.setTimestamp(timestamp);
	return wechatConfig;
 }
 
 /**
  * 生成随机字符串
  * @param length
  * @return
  */
 public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }  
 
 public  InputStream getInputStream(String accessToken,String mediaId) {
	 logger.info("开始从微信服务器下载图片,accessToken:  "+accessToken+" mediaId: "+mediaId);
     InputStream is = null; 
     String url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token="  + accessToken + "&media_id=" + mediaId;
     logger.info("url为："+url);
     try {
         URL urlGet = new URL(url);
         HttpURLConnection http = (HttpURLConnection) urlGet  .openConnection();
         http.setRequestMethod("GET"); // 必须是get方式请求
         http.setRequestProperty("Content-Type",
                 "application/x-www-form-urlencoded");
         http.setDoOutput(true);
         http.setDoInput(true);
         System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
         System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
         http.connect();
         is = http.getInputStream();
     } catch (Exception e) {
    	 StringWriter sw = new StringWriter();  
		 e.printStackTrace(new PrintWriter(sw, true));  
		 String str = sw.toString();
    	 logger.error("从微信服务器下载图片异常,异常消息为："+str);
     }
     logger.info("从微信服务器下载图片完成,结果为："+is.toString());
     return is;
 }

	public static void main(String[] args) {
		WechatLoginServiceImpl wechatLoginServiceImpl=new WechatLoginServiceImpl();
	//	wechatLoginServiceImpl.getVehicleinforByOCR(wechatLoginServiceImpl.getInputStream(null, null));
	}

	/**
	 * 微信openId和手机号进行绑定
	 */
	public @ResponseBody String bindUser(HttpServletRequest httprequest,
			HttpServletResponse response) {
		HttpSession session=httprequest.getSession();
		UserInfor loginUser=(UserInfor) session.getAttribute("loginUser");
		//String openId=httprequest.getParameter("openId");
		String openId = loginUser.getUsername();
		String phoneNo=httprequest.getParameter("phoneNo");
		String agentflag=httprequest.getParameter("agentflag");	
		int isAgent = Integer.parseInt(agentflag);
		logger.info("开始绑定微信用户的手机号，微信openId： "+openId+" 用户手机号为："+phoneNo);
		String resultString="绑定失败";
		WechatBind wechatBind=new  WechatBind();
		UserInfor user=new UserInfor();
		user.setUserphoneno(phoneNo);
		int updateresult=0;
		try {
			logger.info("开始查询用户是否绑定过手机数据");
			UserInfor uu=loginManageService.selectByName(user);
			if(uu!=null){
				logger.info("该账户已经绑定了手机号"+uu.getUserphoneno()+",不能够重复绑定");
				resultString="该账户已经绑定了手机号"+uu.getUserphoneno()+"不能够重复绑定";
			}else{
				logger.info("开始绑定手机号");
				loginUser.setAgentflag(isAgent);
				loginUser.setUserphoneno(phoneNo);
				updateresult=loginManageService.updateuserinfor(loginUser);
				logger.info("结束绑定手机号,绑定结果为： "+updateresult);
			}
			if(updateresult==1){
				logger.info("绑定成功");
				resultString="绑定成功";
			}
			
			/*if(uu!=null){
				logger.info("校验该微信账户是否已经绑定过手机号");
				boolean flag=loginManageService.existFlag(openId);
				if(flag){
                    logger.info("开始绑定过手机号");
					wechatBind.setOpenid(openId);
					wechatBind.setPhoneno(phoneNo);
					wechatBind.setPhoneuserid(uu.getUserid());
					wechatBind.setWechatuserid(loginUser.getUserid());
					int result=loginManageService.saveBindData(wechatBind);
					if(result==1){
						logger.info("绑定成功");
						resultString="绑定成功";
						logger.info("开始更新用户信息");
						if(uu.getAgentflag()==1){
							loginUser.setAgentflag(1);
						}
						loginUser.setUserphoneno(phoneNo);
						logger.info("开始更新用户信息");
						int updateresult=loginManageService.updateUserInfor(loginUser);
						logger.info("结束更新用户信息,更新结果为： "+updateresult);
						session.setAttribute("loginUser", loginUser);
					}else{
						logger.info("绑定失败");
						resultString="绑定失败";
					}
				}else{
					logger.info("该账户已经绑定了手机号，不能够重复绑定");
					resultString="该账户已经绑定了手机号，不能够重复绑定";
				}
			}else{
				logger.info("绑定用户不存在");
				resultString="绑定用户不存在，请检查用户名是否录入正确";
			}*/
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			 e.printStackTrace(new PrintWriter(sw, true));  
			 String str = sw.toString();
			 logger.error("绑定用户异常"+str);
		}
		return resultString;
	}	

	/**
	 * 更改绑定用户手机号
	 * @param httprequest
	 * @param response
	 * @return
	 */
	public String changePhone(HttpServletRequest httprequest,HttpServletResponse response){
		HttpSession session = httprequest.getSession();
		UserInfor loginUser = (UserInfor) session.getAttribute("loginUser");
		String resultMsg="";
		String phoneNo = httprequest.getParameter("phoneNo");  //获取需要更改的手机号
		String openId = loginUser.getUsername();
		int updateresult=0;
		logger.info("开始更改已绑定用户的手机号，微信openId： "+openId+" 用户手机号为："+phoneNo);
		UserInfor user=new UserInfor();
		user.setUserphoneno(phoneNo);
		try {
			logger.info("开始查询用户手机号是否重复绑定！");
			UserInfor uu=loginManageService.selectByName(user);
			if(uu !=null && phoneNo.equals(uu.getUserphoneno())){
				 logger.info("更改用户失败,该手机号重复绑定！");
				 resultMsg="用户已绑定,是否更改手机号码！";
			}else{
				loginUser.setUserphoneno(phoneNo);
				updateresult=loginManageService.updateuserinfor(loginUser);
				logger.info("结束更新用户手机号,更新结果为： "+updateresult);
			}
		    if(updateresult==1){
			    logger.info("更改用户成功");
			    resultMsg="更改用户成功";
		    }
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			 e.printStackTrace(new PrintWriter(sw, true));  
			 String str = sw.toString();
			 logger.error("更新用户异常"+str);
			}
		return resultMsg;
	}
	
	/**
	 * 更改佣金比例
	 * @param httprequest
	 * @param response
	 * @return result (成功/失败)
	 */
	public String  changeCommssionRatio(HttpServletRequest request){
		HttpSession session = request.getSession();
		UserInfor loginUser = (UserInfor) session.getAttribute("loginUser");
		CommssionRatio commssionRatio = new CommssionRatio();
		String comRatio = request.getParameter("comRatio");        //佣金比例
		String userPhoneNo    = request.getParameter("phoneNo");   //操作人手机号
		String userNickName   = "星宇涵歌";//测试昵称
		if(loginUser!=null){
		 //String userNickName   = loginUser.getNickname();   //操作人微信昵称
		}
		String result = "";
		int updateResult= -1;
		if(comRatio!=null && comRatio !="" ){
			commssionRatio.setComratio(Double.parseDouble(comRatio));
		}
		if(userPhoneNo!=null && userPhoneNo !=""){
			commssionRatio.setUserphone(userPhoneNo);
		}
		if(userNickName!=null && userNickName !=""){
			commssionRatio.setUsernickname(userNickName);
		}
		updateResult = loginManageService.updateByPrimaryKeySelective(commssionRatio);
		if(updateResult>0){
			logger.info("更新佣金比例成功！");
			result="1";
		}else{
			logger.info("更新佣金比例失败！");
			result="0";
		}
		return result;
	}
	
	@Override
	public Map<String, Object> getUserinfo(String openid, String access_token) {
		// TODO Auto-generated method stub
		return null;
	}
    
	/**
	 * 判断用户手机号是否绑定过微信
	 * @param userId
	 * @return
	 */
	public String isBindUser(String userId){
		String msg="";
		try {
			UserInfor user=new UserInfor();
			user.setUserid(Integer.parseInt(userId));
			UserInfor uu=loginManageService.selectByName(user);
			if(uu!=null && (uu.getUserphoneno()!=null && uu.getUserphoneno().length()>0)){
				msg="1";
			}else{
				msg="0";
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			 e.printStackTrace(new PrintWriter(sw, true));  
			 String str = sw.toString();
			logger.info("用户查询异常: "+str);
		}
		return msg;
	}
	
	//判断手机号是否注册过
	public String userEverRegist(String username) {
		String msg="";
		try {
			UserInfor user=new UserInfor();
			user.setUserphoneno(username);
			UserInfor uu=loginManageService.selectByName(user);
			if(uu!=null){
				msg="1";
			}else{
				msg="0";
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			 e.printStackTrace(new PrintWriter(sw, true));  
			 String str = sw.toString();
			logger.info("用户查询异常: "+str);
		}
		return msg;
	}

}
