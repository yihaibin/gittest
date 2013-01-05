package com.qyer.android.oneday.util;

import java.io.File;

import android.os.Environment;

public class EnvironmentUtil {
	
	public static final String APP_HOME_DIR = "qyer/template";
	public static final String APP_PICS_DIR = "pics/";
	public static final String APP_ADVERT_DIR = "advert/";
	public static final String APP_MORE_APP_DIR = "mp/";
	
	public static boolean sdcardIsEnable()
	{
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	public static File getSdcardDir()
	{
		return Environment.getExternalStorageDirectory();
	}
	
	public static File getSdcardAppHome()
	{
		File homeDir = new File(getSdcardDir(),APP_HOME_DIR);
		if(!homeDir.exists())
			homeDir.mkdirs();
		
		return homeDir;
	}
	
	public static File getSdcardPicsDir()
	{
		File picsDir = new File(getSdcardAppHome(),APP_PICS_DIR);
		if(!picsDir.exists())
			picsDir.mkdir();
		
		return picsDir;
	}
	
	public static File getSdcardAdvertDir()
	{
		File advertDir = new File(getSdcardAppHome(),APP_ADVERT_DIR);
		if(!advertDir.exists())
			advertDir.mkdir();
		
		return advertDir;
	}
}
