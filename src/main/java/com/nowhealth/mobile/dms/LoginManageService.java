package com.nowhealth.mobile.dms;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nowhealth.mobile.dao.CommssionRatioMapper;
import com.nowhealth.mobile.dao.UserInforMapper;
import com.nowhealth.mobile.dao.UsercommssionInforMapper;
import com.nowhealth.mobile.dao.WechatBindMapper;
import com.nowhealth.mobile.entity.CommssionRatio;
import com.nowhealth.mobile.entity.UserCommssionInfor;
import com.nowhealth.mobile.entity.UserInfor;
import com.nowhealth.mobile.entity.WechatBind;
import com.nowhealth.mobile.utils.DesUtil;
import com.nowhealth.mobile.utils.StringUtils;


@Repository
public class LoginManageService {
	private HttpSession session;
	private static final Logger logger = Logger.getLogger(LoginManageService.class);
	@Resource
	private  UserInforMapper userinforMapper;
	@Resource
	private  WechatBindMapper wechatBindMapper;
	@Resource
	private UsercommssionInforMapper userCommssionInforMapper;
	@Resource 
	private CommssionRatioMapper commssionRatioMapper;

	@Transactional
	public UserInfor selectByName(UserInfor userinfor) throws Exception{
		if(userinfor.getPassword()!=null&&!userinfor.getPassword().equals("")){
			userinfor.setPassword(DesUtil.encrypt(userinfor.getPassword()));// 加密密码
		}
		UserInfor uinfo = userinforMapper.selectByName(userinfor);
		return uinfo;
	}

	@Transactional
	public String registUser(UserInfor userinfor){
		//用户名和密码唯一,注册须判断
		String msg = "";
		try {
//			userinfor.setPassword(DesUtil.encrypt(userinfor.getPassword()));
			int result = userinforMapper.insertSelective(userinfor);
			if (result != 1) {
				msg = "1";//"用户注册失败";
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();  
			  e.printStackTrace(new PrintWriter(sw, true));  
			  String str = sw.toString();
			logger.info("用户登录添加异常: "+str);
		}
		return msg;
		
	}
	
	@Transactional
	public String userFindPassword(UserInfor userinfor,HttpServletRequest request){
		session = request.getSession();
		UserInfor uinfo = userinforMapper.selectByName(userinfor);
		String msg = "";
		if (uinfo != null) {
			try {
				session.setAttribute("loginUser", uinfo);
				String password = DesUtil.decrypt(uinfo.getPassword());//解密password
				// 调用短信接口将密码通过短信方式发送给用户
			} catch (Exception e) {
				StringWriter sw = new StringWriter();  
				  e.printStackTrace(new PrintWriter(sw, true));  
				  String str = sw.toString();
				logger.info("用户找回密码异常: "+str);
			}
		} else {
			msg = "手机与用户名不匹配";
		}
		return msg;
	}
	
	/**
	 * 更新用户数据
	 * 孙小东
	 */
	public int updateUserInfor(UserInfor userinfor){
		return userinforMapper.updateByPrimaryKey(userinfor);
	}
	
	/**
	 * 更新佣金比例数据
	 * 朱海东
	 */
	public int updateByPrimaryKeySelective(CommssionRatio comratio){
		return commssionRatioMapper.updateByPrimaryKeySelective(comratio);
	}
	/**
	 * 查询佣金比例数据
	 */
	public CommssionRatio getCommssionRatio(Integer comratioid){
		return commssionRatioMapper.selectByPrimaryKey(comratioid);
	}
	
	/**
	 * 绑定手机号码
	 * 李长立
	 */
	public int updateuserinfor(UserInfor userinfor){
		return userinforMapper.updateByPrimaryKeySelective(userinfor);
	}

	/**
	 * 微信账号绑定手机号，校验该微信账号是否已经存在
	 * 孙小东
	 * @param openId
	 * @return
	 */
	public boolean existFlag(String openId){
		boolean flag=false;
		if(StringUtils.checkStringEmpty(openId)){
		WechatBind  wechatBind=wechatBindMapper.selectByOpenId(openId);
		if(wechatBind==null){
			flag=true;
		}
		}
		return flag;		
	}
	
	/**
	 * 微信账号绑定手机号
	 * 孙小东
	 * @param openId
	 * @return
	 */
	public int saveBindData( WechatBind wechatBind){	
		return wechatBindMapper.insert(wechatBind);		
	}
	
	/**
	 * 佣金提现插入数据
	 * @param commissionInfor
	 * @return
	 */
	public int insertUserCommissionInfor(UserCommssionInfor commissionInfor){
		return userCommssionInforMapper.insertSelective(commissionInfor);
	}
	
	/**
	 * 查询用户
	 */
	public UserInfor selectByID(int userid){
		UserInfor user=userinforMapper.selectByPrimaryKey(userid);
		return user;
	}
	
	/**
	 * 查詢四要素當天驗證次數
	 */
	public UserInfor selectBytime(Map<String,Object> map){
		UserInfor user=userinforMapper.selectByUser(map);
		return user;
	}
	
	/**
	 * 查詢四要素不是當天驗證次數
	 */
	public UserInfor selectBytime2(Map<String,Object> map){
		UserInfor user=userinforMapper.selectByUser2(map);
		return user;
	}
}
