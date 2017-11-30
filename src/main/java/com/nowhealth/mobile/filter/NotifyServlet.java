package com.nowhealth.mobile.filter;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.nowhealth.mobile.payUtils.SybConstants;
import com.nowhealth.mobile.payUtils.SybUtil;

public class NotifyServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(NotifyServlet.class);
	private static final long serialVersionUID = 1L;
       
    public NotifyServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("get method,no deal");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("接收到通知!");
		request.setCharacterEncoding("gbk");//通知传输的编码为GBK
		response.setCharacterEncoding("gbk");
		TreeMap<String,String> params = getParams(request);//动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容
		try {
			boolean isSign = SybUtil.validSign(params, SybConstants.SYB_APPKEY);// 接受到推送通知,首先验签
			logger.info("验签结果:"+isSign);
			//验签完毕进行业务处理
		} catch (Exception e) {//处理异常
			e.printStackTrace();
		}
		finally{//收到通知,返回success
			response.getOutputStream().write("success".getBytes());
			response.flushBuffer();
		}
	}
	
	/**
	 * 动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容由于收银宝加字段而引起的签名异常
	 * @param request
	 * @return
	 */
	private TreeMap<String, String> getParams(HttpServletRequest request){
		TreeMap<String, String> map = new TreeMap<String, String>();
		Map reqMap = request.getParameterMap();
		for(Object key:reqMap.keySet()){
			String value = ((String[])reqMap.get(key))[0];
			System.out.println(key+";"+value);
			map.put(key.toString(),value);
		}
		return map;
	}

}
