package com.qyer.android.sns.util;

import android.util.Log;

public class SnsLogManager {

	private static final String TAG = "WeiboTraces";
	private static final boolean TURN_ON = false;
	
	public static void printV(String tag, String log)
	{
		if(TURN_ON)
			Log.v(TAG, tag+" "+log);
	}
	
	public static void printD(String tag, String log)
	{
		if(TURN_ON)
			Log.d(TAG, tag+" "+log);
	}
	
	public static void printDT(String tag, String log)
	{
		if(TURN_ON)
			Log.d(tag, log);
	}
	
	public static void printI(String tag, String log)
	{
		if(TURN_ON)
			Log.i(TAG, tag+" "+log);
	}
	
	public static void printW(String tag, String log)
	{
		if(TURN_ON)
			Log.w(TAG, tag+" "+log);
	}
	
	public static void printE(String tag, String log)
	{
		if(TURN_ON)
			Log.e(TAG, tag+" "+log);
	}
}
