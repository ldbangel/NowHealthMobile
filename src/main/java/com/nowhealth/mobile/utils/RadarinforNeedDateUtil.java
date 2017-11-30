package com.nowhealth.mobile.utils;

public class RadarinforNeedDateUtil {
    //将日期类型字符串转换成(yyyy/MM/dd 'T' HH:mm:ss +HH:mm)
	public static String toStandard(String str){
	        String[] dateTime = str.split("\\s");
	        String date = dateTime[0];
	        String time = dateTime.length>1?dateTime[1]:"";
	        return toStandardDate(date) +" "+ toStandardTime(time);
	    }
	    static String toStandardDate(String date){
	        String ymd[] = date.split("-");
	        String year = ymd[0];
	        String month = ymd.length>1?fill(ymd[1]):"01";
	        String day = ymd.length>2?fill(ymd[2]):"01";
	        return year +"/"+month+"/"+day+" T";
	    }
	    static String toStandardTime(String time){
	        String[] hms = time.split(":");
	        String hh = hms.length>0?fill(hms[0]):"00";
	        String mm = hms.length>1?fill(hms[1]):"00";
	        String ss = hms.length>2?fill(hms[2]):"00";
	        return hh+":"+mm+":"+ss+" +"+hh+":"+mm;
	    }
	    
	    static String fill(String str) {
	        if(str.length() == 2){
	            return str;
	        }else if(str.length() == 1){
	            return "0"+str;
	        }else if(str.length() == 0){
	            return "00";
	        }
	        throw new IllegalArgumentException("参数不合法!");
	    }
}
