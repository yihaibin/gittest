package com.qyer.android.sns.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class SnsDeviceUtil {

	public static String getIMEI(Context context) 
	{
		String imei = "";
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager != null) {
			imei = telephonyManager.getDeviceId();
			if (TextUtils.isEmpty(imei)) {
				imei = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
			}
		}
		return imei;
	}
	
	
    public static boolean isConnectInternet(Context context)
    {
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if (networkInfo != null )
        	return networkInfo.isAvailable();
        
        return false ;
	}	
    
    public static boolean hasApp(Context context , String packagename)
    {
        try {
        	
        	context.getPackageManager().getPackageInfo(packagename, 0);
        	return true;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean hasSinaWeiboClient(Context context)
    {
        try {
        	
        	PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.sina.weibo", 0);
        	if(packageInfo == null)
        		return false;
        	
            int highBit = packageInfo.versionName.charAt(0);
            return highBit > 50 ? true : false;//50 = 2

        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
