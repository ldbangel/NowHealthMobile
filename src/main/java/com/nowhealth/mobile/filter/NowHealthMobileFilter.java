package com.nowhealth.mobile.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.nowhealth.mobile.entity.UserInfor;

public class NowHealthMobileFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String path = req.getContextPath();
		String url = req.getRequestURI();
		//控制只能用微信浏览器打开
		/*boolean isWechat = detectWeixin(req);
		if(!isWechat){
			resp.sendRedirect(path+"/views/jsp/wxerror.jsp");
		}*/
		UserInfor user = (UserInfor) session.getAttribute("loginUser");
		String conString = req.getHeader("REFERER");// 获取父url
		if (url.endsWith(".jsp") || url.endsWith(".do")) {
			if ("".equals(conString) || null == conString) {
				String servletPath = req.getServletPath();
				if(servletPath.indexOf("/goToFirstScreen.do")>=0
						|| servletPath.indexOf("nowhealthHome.jsp")>=0
						|| servletPath.indexOf("myAccount.jsp")>=0
						|| servletPath.indexOf("getWechatJSAccess.do")>=0
						|| servletPath.indexOf("/paymentComplete/payCallback.do")>=0
						|| servletPath.indexOf("/paymentComplete/bankpayCallback.do")>=0
						|| servletPath.indexOf("/wechatSweep.jsp")>=0
						|| servletPath.indexOf("/wxerror.jsp")>=0){
					chain.doFilter(request, response);
				}else{
					resp.sendRedirect(path+"/views/jsp/nowhealthHome.jsp");
				}
			}else{
				if(user==null){
					if(url.indexOf("/paymentInfor/wechatPayToSweep.do")>=0
							||url.indexOf("/WechatLogin/weixinLogin.do")>=0
							){
						chain.doFilter(request, response);
					}else{
						resp.sendRedirect(path+"/views/jsp/wxerror.jsp");
					}
				}else{
					chain.doFilter(request, response);
				}
			}
		}else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	
	//判断微信浏览器
	private boolean detectWeixin(HttpServletRequest request){
		Enumeration<String> names = request.getHeaderNames();
		while(names.hasMoreElements()){
			String name=names.nextElement();
			if(name.equalsIgnoreCase("user-agent")||name.equalsIgnoreCase("useragent")){
				String value=request.getHeader(name);
				if(StringUtils.isNotBlank(value)&&value.contains("MicroMessenger")){
					return true;
				}
			}
		}
		return false;
	}
}
