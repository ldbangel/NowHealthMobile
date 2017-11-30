package com.nowhealth.mobile.utils;

import java.lang.reflect.Field;

public class ObjectUtil {
	/**
	 * 赋值给同类对象
	 * 非空或者非""才赋值
	 * @param origin
	 * @param destination
	 */
	public static <T> void mergeObject(T origin, T destination) {
        if (origin == null || destination == null)
            return;
        if (!origin.getClass().equals(destination.getClass()))
            return;
 
        Field[] fields = origin.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object value = fields[i].get(origin);
                //判断不为空并且不为""时才赋值
                if (value != null && !"".equals(value)) {
                    fields[i].set(destination, value);
                }
                fields[i].setAccessible(false);
            } catch (Exception e) {
            }
        }
    }
}
