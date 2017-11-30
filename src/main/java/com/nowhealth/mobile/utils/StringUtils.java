package com.nowhealth.mobile.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

//******************************************************************
/**
* 类名:com.Utils.StringUtils <pre>
* 描述: 字符串处理类
* 	基本思路:
* 	public方法:
* 	特别说明:
* 修改说明: 类的修改说明
* </pre>
*/
//******************************************************************
public class StringUtils {

	public static String DATE_TIME_CONSTANT = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_CONSTANT = "yyyy-MM-dd";
	public static final int YEAR_FIELD=1;
	public static final int MONTH_FIELD=2;
	public static final int WEEK_FIELD=3;
	public static final int DAY_FIELD=5;
    
   public static boolean checkStringEmpty(String str){
	   if(str!=null&&!"".equalsIgnoreCase(str)){
		   return true;
	   }else{
		   return false;
	   }
	   
   }
	public static String trim(String str){
		return null==str?"":str.trim();
	}
	
	public static String trim(Object obj){
		return null==obj?"":obj.toString().trim();
	}
	
	public static String[] split(String str,String expr){
		if("".equalsIgnoreCase(trim(str))){
			return new String[0];
		}
		return str.split(expr);
	}
	
	public static String replace(String str, String expr, String substitute){
		if("".equalsIgnoreCase(trim(str))){
			return "";
		}
		return str.replaceAll(expr, substitute);
	}
	
	/**
	 * 方法getUTF8StringFromGBKString的详细说明  <br><pre>
	 * 字符集转换，从GBK转为UTF－8 <br>
	 * @param gbkStr
	 * @return
	 */
	public static String getUTF8StringFromGBKString(String gbkStr) {   
        try {   
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");   
        } catch (UnsupportedEncodingException e) {   
            throw new InternalError();   
        }   
    }   
       
    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {   
        int n = gbkStr.length();   
        byte[] utfBytes = new byte[3 * n];   
        int k = 0;   
        for (int i = 0; i < n; i++) {   
            int m = gbkStr.charAt(i);   
            if (m < 128 && m >= 0) {   
                utfBytes[k++] = (byte) m;   
                continue;   
            }   
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));   
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));   
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));   
        }   
        if (k < utfBytes.length) {   
            byte[] tmp = new byte[k];   
            System.arraycopy(utfBytes, 0, tmp, 0, k);   
            return tmp;   
        }   
        return utfBytes;   
    }
	
  /**
   * 压缩字符串<br>
   * @param 参数类型 参数名 说明
   * @return String 说明
   * @throws 异常类型 说明  
   * @param str
   * @return
   * @throws IOException
   */
  public static String compress(String str) throws IOException {    
      System.out.println("压缩之前的字符串大小："+str.length());    
      if (str == null || str.length() == 0) {    
          return str;    
      }    
      ByteArrayOutputStream out = new ByteArrayOutputStream();    
      GZIPOutputStream gzip = new GZIPOutputStream(out);    
      gzip.write(str.getBytes());    
      gzip.close();    
      return out.toString("ISO-8859-1");    
  }    
  
  /**
   * 解压 <br>
   * 创建时间：Mar 12, 2013 5:47:01 PM </pre>
   * @param 参数类型 参数名 说明
   * @return String 说明
   * @throws 异常类型 说明  
   * @param str
   * @return
   * @throws IOException
   */
  public static String uncompress(String str) throws IOException {    
      System.out.println("压缩之后的字符串大小："+str.length());    
      if (str == null || str.length() == 0) {    
          return str;    
      }    
      ByteArrayOutputStream out = new ByteArrayOutputStream();    
      ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));    
      GZIPInputStream gunzip = new GZIPInputStream(in);    
      byte[] buffer = new byte[256];    
      int n;    
      while ((n = gunzip.read(buffer)) >= 0) {    
          out.write(buffer, 0, n);    
      }    
      return out.toString();    
  }  
  
  /**
   * 将sourceStr以splitElementFlag分割为若干个元素，再将每个元素以splitKeyAndValueFlag分割为一对key/value，
   * 再将每一对key/value封装到Map中<br>
   * @param sourceStr 需分割的字符串
   * @param splitKeyAndValueFlag Map中key与value的分隔符
   * @param splitElementFlag Map中两个元素之间的分隔符
   * @return Map<String,Object> 返回封装完成的map
   * @throws Exception 
   */
  public static Map<String, Object> parseStringToMap(String sourceStr,String splitKeyAndValueFlag,String splitElementFlag) throws Exception{
		Map<String, Object> map = null;
		if(sourceStr==null || "".equalsIgnoreCase(sourceStr.trim())){
			throw new Exception("传递的sourceStr参数字符串为NULL或空");
		}
		if(splitKeyAndValueFlag==null || "".equalsIgnoreCase(splitKeyAndValueFlag.trim())){
			throw new Exception("传递的splitKeyAndValueFlag(Map中key与value的分隔符)参数字符串为NULL或空");
		}
		if(splitElementFlag==null || "".equalsIgnoreCase(splitElementFlag.trim())){
			throw new Exception("传递的splitElementFlag(Map中两个元素之间的分隔符)参数字符串为NULL或空");
		}
		
		String[] sArr = sourceStr.split(splitElementFlag);
		if(sArr!=null && sArr.length>0){
			map = new HashMap<String, Object>();
			for(int i=0;i<sArr.length;i++){
				String temp = sArr[i];
				if(temp!=null && !"".equalsIgnoreCase(temp.trim())){
					String[] t = temp.split(splitKeyAndValueFlag);
					map.put(t[0], (t.length==1 || t[1]==null)?"":t[1]);
				}
			}
		}
		return map;
	}
  
  /**
   * 将sourceStr以splitElementFlag分割为若干个元素，再将每个元素以splitKeyAndValueFlag分割为一对key/value，
   * 再将每一对key/value封装到Map中<br>
   * @param sourceStr 需分割的字符串
   * @param splitKeyAndValueFlag Map中key与value的分隔符
   * @param splitElementFlag Map中两个元素之间的分隔符
   * @return Map<String,Object> 返回封装完成的map
   * @throws Exception 
   */
  public static Map<String, String> parseStringToMap1(String sourceStr,String splitKeyAndValueFlag,String splitElementFlag) throws Exception{
		Map<String, String> map = null;
		//如果
		if(sourceStr==null || "".equalsIgnoreCase(sourceStr.trim())){
			throw new Exception("传递的sourceStr参数字符串为NULL或空");
		}
		if(splitKeyAndValueFlag==null || "".equalsIgnoreCase(splitKeyAndValueFlag.trim())){
			throw new Exception("传递的splitKeyAndValueFlag(Map中key与value的分隔符)参数字符串为NULL或空");
		}
		if(splitElementFlag==null || "".equalsIgnoreCase(splitElementFlag.trim())){
			throw new Exception("传递的splitElementFlag(Map中两个元素之间的分隔符)参数字符串为NULL或空");
		}
		
		String[] sArr = sourceStr.split(splitElementFlag);
		if(sArr!=null && sArr.length>0){
			map = new HashMap<String, String>();
			for(int i=0;i<sArr.length;i++){
				String temp = sArr[i];
				if(temp!=null && !"".equalsIgnoreCase(temp.trim())){
					String[] t = temp.split(splitKeyAndValueFlag);
					map.put(t[0], (t.length==1 || t[1]==null)?"":t[1]);
				}
			}
		}
		return map;
	}
  
  /**
	 * 方法firstCharToUpperCase的简要说明 <br>
	 * 将字符串首字母变为大写
	 * <pre> 
	 * </pre>
	 * @param paramString
	 * @return
	 */
	public static String firstCharToUpperCase(String paramString){
		return paramString.substring(0, 1).toUpperCase()+paramString.substring(1, paramString.length());
	}
	
	/**
	 * 方法operationDate的简要说明 <br>
	 * 日期运算，将指定的（有符号的）时间量添加到给定的日历字段中
	 * <pre>
	 * </pre>
	 * @param dateStr　日期字符串
	 * @param field　日历字段（即年月日）；1:年字段;2：月字段;3:周;5:日字段
	 * @param amount　时间量，可以有符号，如果：-2
	 * @return
	 * @throws Exception
	 */
	public static String operationDate(String dateStr,int field,int amount,String formatString) throws Exception{
		SimpleDateFormat sf  =new SimpleDateFormat(formatString);
		GregorianCalendar gc=new GregorianCalendar(); 
		gc.setTime(strToDate(dateStr));
		gc.add(field, amount);
		return sf.format(gc.getTime());
	}
	
	/**
	 * 方法operationDate的简要说明 <br>
	 * 日期运算，将指定的（有符号的）时间量添加到给定的日历字段中
	 * <pre>
	 * </pre>
	 * @param dateStr　日期字符串
	 * @param field　日历字段（即年月日）；1:年字段;2：月字段;3:周;5:日字段
	 * @param amount　时间量，可以有符号，如果：-2
	 * @return
	 * @throws Exception
	 */
	public static String operationDate(Date date,int field,int amount,String formatString) throws Exception{
		if("".equalsIgnoreCase(trim(formatString))){
			formatString = DATE_TIME_CONSTANT;
		}
		SimpleDateFormat sf  =new SimpleDateFormat(formatString);
		GregorianCalendar gc=new GregorianCalendar(); 
		gc.setTime(date);
		gc.add(field, amount);
		return sf.format(gc.getTime());
	}
	
	/**
	 * 方法dateToStr的简要说明 <br>
	 * 日期转为String，默认格式yyyy-MM-dd HH:mm:ss
	 * <pre>
	 * </pre>
	 * @param paramDate
	 * @return
	 */
	public static String dateToStr(Date paramDate) {
		if (paramDate == null){
			return "";
		}
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				DATE_TIME_CONSTANT);
		return localSimpleDateFormat.format(paramDate);
	}

	/**
	 * 方法dateToStr的简要说明 <br>
	 * 日期转为String，格式需指定
	 * <pre>
	 * </pre>
	 * @param paramDate
	 * @param paramString 格式字符串
	 * @return
	 */
	public static String dateToStr(Date paramDate, String paramString) {
		if (paramDate == null)
			return "";
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				paramString);
		return localSimpleDateFormat.format(paramDate);
	}
	
	public static boolean isInt(String paramString) {
		if (paramString == null)
			return false;
		Pattern localPattern = Pattern.compile("[-+]{0,1}[0-9]+");
		return localPattern.matcher(paramString).matches();
	}

	public static int strToInt(String paramString) {
		if (!isInt(paramString))
			return 0;
		return Integer.parseInt(paramString);
	}
	
	/**  
    * @param args  
    */  
    public static void main(String[] args) throws Exception {   
    	System.out.println(checkDate(strToDate("20141111000000")));
    }
	
	public static String getDefautFormat(String paramString) throws Exception {
		String temp = "yyyyMMddHHmmss";
		return temp.substring(0, paramString.length());
	}
	
	public static Date strToDate(String paramString) throws Exception {
		paramString = paramString.trim().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
		if(paramString.indexOf(".")>0){
			paramString = paramString.substring(0, paramString.indexOf("."));
		}
		String str = getDefautFormat(paramString);
		return strToDate(paramString, str);
	}
	public static Date strToDate(String paramString1, String paramString2)
			throws Exception {
		try {
			if ("".equalsIgnoreCase(trim(paramString2))){
				paramString2 = "yyyy-MM-dd";
			}
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2.trim());
			return localSimpleDateFormat.parse(paramString1.trim());
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		throw new Exception("根据" + paramString2 + "模式转换字符" + paramString1+ "异常，请检查！");
	}
	
	/*********************************** 身份证验证开始 ****************************************/       
    /**   
     * 身份证号码验证    
     * 1、号码的结构   
     * 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，   
     * 八位数字出生日期码，三位数字顺序码和一位数字校验码。   
     * 2、地址码(前六位数）    
     * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。    
     * 3、出生日期码（第七位至十四位）   
     * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。    
     * 4、顺序码（第十五位至十七位）   
     * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，   
     * 顺序码的奇数分配给男性，偶数分配给女性。    
     * 5、校验码（第十八位数）   
     * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0,  , 16 ，先对前17位数字的权求和   
     * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4   
     * 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0   
     * X 9 8 7 6 5 4 3 2   
     */    
    
    /**   
     * 功能：身份证的有效验证   
     * @param IDStr 身份证号   
     * @return 有效：返回"" 无效：返回String信息   
     * @throws ParseException   
     */    
    @SuppressWarnings("unchecked")     
    public static String IDCardValidate(String IDStr) throws ParseException {  
    	IDStr = trim(IDStr);
        String errorInfo = "";// 记录错误信息     
       String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",     
                "3", "2" };     
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",     
                "9", "10", "5", "8", "4", "2" };     
        String Ai = "";     
        // ================ 号码的长度 15位或18位 ================     
       if (IDStr.length() != 15 && IDStr.length() != 18) {     
            errorInfo = "身份证号码长度应该为15位或18位。";     
            return errorInfo;     
        }     
        // =======================(end)========================     
   
        // ================ 数字 除最后以为都为数字 ================     
       if (IDStr.length() == 18) {     
            Ai = IDStr.substring(0, 17);     
        } else if (IDStr.length() == 15) {     
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);     
        }     
        if (isNumeric(Ai) == false) {     
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";     
            return errorInfo;     
        }     
        // =======================(end)========================     
   
        // ================ 出生年月是否有效 ================     
       String strYear = Ai.substring(6, 10);// 年份     
       String strMonth = Ai.substring(10, 12);// 月份     
       String strDay = Ai.substring(12, 14);// 月份     
       if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {     
            errorInfo = "身份证生日无效。";     
            return errorInfo;     
        }     
        GregorianCalendar gc = new GregorianCalendar();     
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");     
        if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150    
                || (gc.getTime().getTime() - s.parse(     
                        strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {     
            errorInfo = "身份证生日不在有效范围。";     
            return errorInfo;     
        }     
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {     
            errorInfo = "身份证月份无效";     
            return errorInfo;     
        }     
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {     
            errorInfo = "身份证日期无效";     
            return errorInfo;     
        }     
        // =====================(end)=====================     
   
        // ================ 地区码时候有效 ================     
       Hashtable h = GetAreaCode();     
        if (h.get(Ai.substring(0, 2)) == null) {     
            errorInfo = "身份证地区编码错误。";     
            return errorInfo;     
        }     
        // ==============================================     
   
        // ================ 判断最后一位的值 ================     
       int TotalmulAiWi = 0;     
        for (int i = 0; i < 17; i++) {     
            TotalmulAiWi = TotalmulAiWi     
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))     
                    * Integer.parseInt(Wi[i]);     
        }     
        int modValue = TotalmulAiWi % 11;     
        String strVerifyCode = ValCodeArr[modValue];     
        Ai = Ai + strVerifyCode;     
    
        if (IDStr.length() == 18) {     
            if (Ai.equals(IDStr) == false) {     
                errorInfo = "身份证无效，不是合法的身份证号码";     
                return errorInfo;     
            }     
        } else {     
            return "";     
        }     
        // =====================(end)=====================     
       return "";     
    }     
    
    /**   
     * 功能：设置地区编码   
     * @return Hashtable 对象   
     */    
    @SuppressWarnings("unchecked")     
    private static Hashtable GetAreaCode() {     
        Hashtable hashtable = new Hashtable();     
        hashtable.put("11", "北京");     
        hashtable.put("12", "天津");     
        hashtable.put("13", "河北");     
        hashtable.put("14", "山西");     
        hashtable.put("15", "内蒙古");     
        hashtable.put("21", "辽宁");     
        hashtable.put("22", "吉林");     
        hashtable.put("23", "黑龙江");     
        hashtable.put("31", "上海");     
        hashtable.put("32", "江苏");     
        hashtable.put("33", "浙江");     
        hashtable.put("34", "安徽");     
        hashtable.put("35", "福建");     
        hashtable.put("36", "江西");     
        hashtable.put("37", "山东");     
        hashtable.put("41", "河南");     
        hashtable.put("42", "湖北");     
        hashtable.put("43", "湖南");     
        hashtable.put("44", "广东");     
        hashtable.put("45", "广西");     
        hashtable.put("46", "海南");     
        hashtable.put("50", "重庆");     
        hashtable.put("51", "四川");     
        hashtable.put("52", "贵州");     
        hashtable.put("53", "云南");     
        hashtable.put("54", "西藏");     
        hashtable.put("61", "陕西");     
        hashtable.put("62", "甘肃");     
        hashtable.put("63", "青海");     
        hashtable.put("64", "宁夏");     
        hashtable.put("65", "新疆");     
        hashtable.put("71", "台湾");     
        hashtable.put("81", "香港");     
        hashtable.put("82", "澳门");     
        hashtable.put("91", "国外");     
        return hashtable;     
    }     
    
    /**   
     * 功能：判断字符串是否为数字   
     * @param str   
     * @return  
     */    
    private static boolean isNumeric(String str) {     
        Pattern pattern = Pattern.compile("[0-9]*");     
        Matcher isNum = pattern.matcher(str);     
        if (isNum.matches()) {     
            return true;     
        } else {     
            return false;     
        }     
    }     
    
    /**   
     * 功能：判断字符串是否为日期格式   
     * @param str   
     * @return   
     */    
    public static boolean isDate(String strDate) {     
        Pattern pattern = Pattern     
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");     
        Matcher m = pattern.matcher(strDate);     
        if (m.matches()) {     
            return true;     
        } else {     
            return false;     
        }     
    }         
/*********************************** 身份证验证结束 ***************************************
 * @throws Exception */   

    /**
     * 通过身份证号计算年龄（周岁）
     */
    public static int getAgeByIdCard(String idCard) throws Exception{
    	idCard = trim(idCard);
        int leh = idCard.length();
        if (leh == 18) {
            //int se = Integer.valueOf(idCard.substring(leh - 1)) % 2;//se=0,男；se＝1，女
            String dates = idCard.substring(6, 10) + "-" + idCard.substring(10, 12) + "-" + idCard.substring(12, 14);
            String s1 = dateToStr(new Date(), "yyyy-MM-dd");
            Date today = strToDate(s1,null);
            Date birthday = strToDate(dates,null);
            return (int)((today.getTime()-birthday.getTime())/1000/60/60/24/365);
        }else {
            String dates = "19" + idCard.substring(6, 8) + "-" + idCard.substring(8, 10) + "-" + idCard.substring(10, 12);
            String s1 = dateToStr(new Date(), "yyyy-MM-dd");
            Date today = strToDate(s1,null);
            Date birthday = strToDate(dates,null);
            return (int)((today.getTime()-birthday.getTime())/1000/60/60/24/365);
        }
        
    }
    
    
    /**
     * 方法containsEnglishPunctuation的简要说明 <br>
     * 判断字符串中是否含有英文标点,默认校验的标点集合为：[?;'<>]
     * <pre>
     * </pre>
     * @param str
     * @param reg 正则表达式字符串
     * @return
     */
    public static boolean containsEnglishPunctuation(String str,String reg){
    	if("".equalsIgnoreCase(trim(str))){
    		return false;
    	}
    	if("".equalsIgnoreCase(trim(reg))){
    		reg = "[?;'<>]";
    	}
    	Pattern pattern=Pattern.compile(reg);//增加对应的标点
    	Matcher matcher = pattern.matcher(trim(str));
    	return matcher.find();
    }
    
    
    public static String listToString(List<String> list, char separator) {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < list.size(); i++) {
    		sb.append(list.get(i)).append(separator);
    	}
    	return sb.toString().substring(0,sb.toString().length()-1);
    }
    
    /**校验起保止期不应晚于当前日期的下一天
	 * 孙晓东
	 * @param bTInsrncBgnTm
	 * @return
	 */
	public static boolean checkDate(Date bTInsrncBgnTm) {
		try {
		Date newDate=new Date();//获取当前日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(newDate);
		// 从下一天0点开始算
		calendar.add(Calendar.DATE, 1);
		Date nextDate=calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		bTInsrncBgnTm=sdf.parse(sdf.format(bTInsrncBgnTm));
		nextDate =sdf.parse(sdf.format(nextDate));
		if(bTInsrncBgnTm.before(nextDate)){
				return false;
			}
			else{
				return true;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 创建指定数量的随机字符串
	 * 
	 * @param numberFlag
	 *            是否是数字
	 * @param length
	 * @return
	 */
	public static String getValidateNo(boolean numberFlag, int length){
      String retStr = "";
      String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
      int len = strTable.length();
      boolean bDone = true;
    do {
         retStr = "";
        int count = 0;
        for (int i = 0; i < length; i++) {
        double dblR = Math.random() * len;
       int intR = (int) Math.floor(dblR);
       char c = strTable.charAt(intR);
        if (('0' <= c) && (c <= '9')) {
        count++;
         }
      retStr += strTable.charAt(intR);
      }
    if (count >= 2) {
    bDone = false;
     }
    } while (bDone);
     return retStr;
	}
	
	/**
	 * md5
	 * @param b
	 * @return
	 */
	public static String MD5(byte[] b) {
        try {
        	MessageDigest md = MessageDigest.getInstance("MD5");
        	 md.reset();
             md.update(b);
             byte[] hash = md.digest();
             StringBuffer outStrBuf = new StringBuffer(32);
             for (int i = 0; i < hash.length; i++) {
                 int v = hash[i] & 0xFF;
                 if (v < 16) {
                 	outStrBuf.append('0');
                 }
                 outStrBuf.append(Integer.toString(v, 16).toLowerCase());
             }
             return outStrBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new String(b);
        }
    }
	
}
