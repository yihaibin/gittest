package com.qyer.android.oneday.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppInfoUtil {

	
	public static String getVersionCode(Context context)
	{
		String versionCode = "";
		try {
			
			PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			versionCode = packInfo.versionCode+"";
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		return versionCode;
	}
	
	public static String getVersionName(Context context)
	{
		String versionName = "";
		try{
			
			PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			versionName = packInfo.versionName;
			
		}catch(Exception e){
		}
		
		return versionName;
	}
}
