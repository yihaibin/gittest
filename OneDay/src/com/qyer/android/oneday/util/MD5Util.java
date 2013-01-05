package com.qyer.android.oneday.util;

import java.security.MessageDigest;

import android.text.TextUtils;

public class MD5Util {

	
	public static String md5(String s) 
	{
		if(TextUtils.isEmpty(s))
			return null;
		
		  char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd','e', 'f'};
		  try {
		    byte[] strTemp = s.getBytes();
		    MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		    mdTemp.update(strTemp);
		    byte[] md = mdTemp.digest();
		    int j = md.length;
		    char str[] = new char[j * 2];
		    int k = 0;
		    for (int i = 0; i < j; i++) {
		      byte byte0 = md[i];
		      str[k++] = hexDigits[byte0 >>> 4 & 0xf];
		      str[k++] = hexDigits[byte0 & 0xf];
		    }
		    return new String(str);
		  }
		  catch (Exception e) {
		    return null;
		  }
		}

	public static String getAccountS(String username, String pwd)
	{
		if(TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd))
			return null;
		
		String temp = username.substring(0, 2) + pwd.substring(0, 2) + 
					  username.substring(username.length()-2, username.length())+ pwd.substring(pwd.length()-2, pwd.length());
//		LogA.d("getAccountS :  " + temp);
//		LogA.d("getAccountS MD5 :  " + md5(md5(temp).toLowerCase()).toLowerCase());
		
		return md5(md5(temp).toLowerCase()).toLowerCase();
	}
}
