package com.nowhealth.mobile.common;

import java.util.List;
import java.util.Map;

import com.nowhealth.mobile.constants.LudiConstants;
import com.nowhealth.mobile.utils.XmlUtilsByDom4j;



public class ProcessData {
	/**
	 * 获取发送短信的验证码返回状态
	 */
	public static String getTemplateSMSReturnCode(String xml) {
		String resultCode = "";
		List<Map<String, String>> resultList = XmlUtilsByDom4j
				.specifiedXml2list(xml, "resp", "UTF-8");
		if (resultList != null && resultList.size() > 0) {
			for (String s : resultList.get(0).keySet()) {
				if (LudiConstants.TEMPLATE_SMS_RESPONSE_CODE
						.equalsIgnoreCase(s)) {
					resultCode = resultList.get(0).get(s);
				}
			}
		}
		return resultCode;
	}
}
