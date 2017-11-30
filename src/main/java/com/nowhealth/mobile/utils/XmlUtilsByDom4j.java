package com.nowhealth.mobile.utils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtilsByDom4j {
public final static String Encoding="UTF-8";
	
	public static List<Map<String,String>> specifiedXml2list(String xml,String spefNode,String encoding){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		Map<String,String> map = null;
		Document document = null;
		List<Element> list = null;
		List<Element> elemlilst = null;
		try {
			document = parseDocFromXml(xml,encoding);
			list = document.selectNodes("//" + spefNode);
			for(Element elem : list){
				elemlilst = elem.elements();
				map = new HashMap<String, String>();
				for(Element subelem : elemlilst){
					map.put(subelem.getName(), subelem.getText());
				}
				result.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 将xml格式字符串，根据指定编码转为org.w3c.dom.Document对像,如果传入编码为null或"",则默认编码为UTF-8
	 * 创建时间：Mar 12, 2013 7:31:19 PM </pre>
	 * @param paramString xml格式字符串
	 * @param encoding  编码
	 * @throws 异常类型 说明  
	 * @return
	 * @throws DocumentException 
	 * @throws UnsupportedEncodingException 
	 */
	public static Document parseDocFromXml(String paramString,String encoding) throws Exception{
		if("".equalsIgnoreCase(FileUitls.trim(encoding))){
			encoding = Encoding;
		}
		Document document = null;
		SAXReader saxReader = null;
		try {
			saxReader = new SAXReader();
			document = saxReader.read(new ByteArrayInputStream(paramString.getBytes(encoding)));
		} catch (Exception e) {
			throw e;
		}
		return document;
	}
}
