package com.qyer.android.oneday.pref;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfigPrefs {
	
	private static final String USER_NAME = "userName";
	private static final String IS_ACCPET_PUSH_MSG = "acceptPushMsg";
	private static final String APP_INITED = "inited";
	private static final String CLOSE_ADVERT_MILLIS = "closeAdvertMillis";
	private static final String SHARE_NAME = "config";
	
	private static AppConfigPrefs mAppConfig;
	private SharedPreferences mSharePrefs;
	
	private AppConfigPrefs(Context context)
	{
		mSharePrefs = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
	}
	
	public static AppConfigPrefs getInstance(Context context)
	{
		if(mAppConfig == null)
			mAppConfig = new AppConfigPrefs(context);
		
		return mAppConfig;
	}
	
	public void saveUserName(String userName)
	{
		mSharePrefs.edit().putString(USER_NAME, userName).commit();
	}
	
	public String getUserName()
	{
		return mSharePrefs.getString(USER_NAME, "");
	}
	
	public void saveAcceptPushMessage(boolean isAccept)
	{
		mSharePrefs.edit().putBoolean(IS_ACCPET_PUSH_MSG, isAccept).commit();
	}
	
	public boolean isAcceptPushMessage()
	{
		return mSharePrefs.getBoolean(IS_ACCPET_PUSH_MSG, true);
	}
	
	public void saveInitAccess()
	{
		mSharePrefs.edit().putBoolean(APP_INITED, true).commit();
	}
	
	public boolean isInitAeecess()
	{
		return mSharePrefs.getBoolean(APP_INITED, false);
	}
	
	public void saveCloseAdvertMillis(long millis)
	{
		mSharePrefs.edit().putLong(CLOSE_ADVERT_MILLIS, millis).commit();
	}
	
	public long getCloseAdvertMillis()
	{
		return mSharePrefs.getLong(CLOSE_ADVERT_MILLIS, 0);
	}
}
