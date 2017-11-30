package com.nowhealth.mobile.serviceImpl;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nowhealth.mobile.dao.UserInforMapper;
import com.nowhealth.mobile.dms.LoginManageService;
import com.nowhealth.mobile.entity.UserInfor;
import com.nowhealth.mobile.service.LoginService;


@Service("loginService")
public class LoginServiceImpl implements LoginService {
	@Resource
	private  UserInforMapper userinforMapper;
	@Resource
	private LoginManageService loginManageService;
	
	private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);

	//用户登录
	public String userExists(UserInfor userinfor, HttpServletRequest request) throws Exception{
		HttpSession session=request.getSession();
		String msg="";
		Integer type=userinfor.getUsertype();//1：动态密码登录；2：会员密码登录
		if(type!=3){
			userinfor.setUsertype(null);//用户登录统一设置type为1
		}
		UserInfor user = userinforMapper.selectByName(userinfor);
		if(type==2||type==3){
			if(user==null){
				msg="3";     //"用户名不存在或密码输入错误";
			}else{
				logger.info("帐号密码登录或者微信登录查询用户名为：" + user.getUsername()+"------usertype为："+user.getUsertype());
				session.setAttribute("loginUser", user);
			}
		}		
		return msg;
	}

    //重设用户
	public String registUser(UserInfor userinfor,HttpServletRequest request) {
		String agentCode = request.getParameter("agentCode")==null?"0":request.getParameter("agentCode");
		int isAgent = Integer.parseInt(agentCode);
		//用户名和密码唯一,注册须判断
		String msg = "";
		String username= userinfor.getUsername();   //用户名称openId
		Integer usertype= userinfor.getUsertype();  //用户类型
		String nickname = userinfor.getNickname();   //微信名称
		String headportrait  = userinfor.getHeadportrait(); //微信图片
		int updateResult = -1;
			try {
				UserInfor user=new UserInfor();
				user.setUsername(username);
				UserInfor uu=loginManageService.selectByName(user);
				if(uu!=null){
					int userId = uu.getUserid();
					if(!headportrait.equals(uu.getHeadportrait())){
						logger.info("====更新微信图片信息至userinfor====");
						userinfor.setUserid(userId);
						updateResult = loginManageService.updateuserinfor(userinfor);
					}
					msg="2"; //用户已存在
				}else{
					UserInfor newUser=new UserInfor();
					newUser.setUsername(username);
					newUser.setNickname(nickname);
					newUser.setHeadportrait(headportrait);
					newUser.setAgentflag(isAgent);
					newUser.setUsertype(usertype);
					msg=loginManageService.registUser(newUser);  //用户不存在，创建用户
				}
			} catch (Exception e) {
				StringWriter sw = new StringWriter();  
				 e.printStackTrace(new PrintWriter(sw, true));  
				 String str = sw.toString();
				logger.info("用户注册异常: "+str);
			}
		return msg;
	}

	/**
	 * 根据userId获取userInfor
	 */
	public UserInfor selectUserById(int userid) {
		UserInfor userinfor=null;
		 userinfor=userinforMapper.selectByPrimaryKey(userid);
		 String password="";
		  password=userinfor.getPassword();
		 try {
			/*password=DesUtil.decrypt(password);//解密*/
			userinfor.setPassword(password);
		 } catch (Exception e) {
			e.printStackTrace();
		 }		
		return userinfor;	
	}

}
