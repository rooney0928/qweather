package com.lyc.qweather.util;

import android.util.Log;

/**
 * Log工具类
 */
public class LogUtil {
	private static boolean isDebug = true;
	
	public static void d(String tag, String msg){
		if(isDebug){
			Log.d(tag, msg);
		}
	}
	public static void e(String tag, String msg){
		if(isDebug){
			Log.e(tag, msg);
		}
	}

	/**
	 * 将对象转成json打印
	 * @param tag
	 * @param msg
     */
	public static void eJson(String tag, Object msg){
		if(isDebug){
//			Log.e(tag, JsonUtil.objectToJson(msg));
		}
	}
	
	public static void d(Object object, String msg){
		if(isDebug){
			Log.d(object.getClass().getSimpleName(), msg);
		}
	}

	public static void e(Object object, String msg){
		if(isDebug){
			Log.e(object.getClass().getSimpleName(), msg);
		}
	}

	/**
	 * 将对象转成json打印
	 * @param
	 * @param msg
	 */
	public static void eJson(Object object, Object msg){
		if(isDebug){
//			Log.e(object.getClass().getSimpleName(), JsonUtil.objectToJson(msg));
		}
	}
}
