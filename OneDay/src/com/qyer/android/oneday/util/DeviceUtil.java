package com.qyer.android.oneday.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class DeviceUtil {

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
	
	public static boolean hasApp(Context context, String packagename) 
	{
		PackageInfo packageInfo = null;

		try {
			packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		return packageInfo == null ? false:true;
	}
	
    public static boolean isConnectInternet(Context context)
    {
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if (networkInfo != null )
        	return networkInfo.isAvailable();
        
        return false ;
	}	
    
    
}
