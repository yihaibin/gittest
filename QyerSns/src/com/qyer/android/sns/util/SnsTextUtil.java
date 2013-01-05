package com.qyer.android.sns.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SnsTextUtil {

    public static boolean isEmpty(CharSequence str)
    {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
    
	public static boolean checkMailFormat(String email) 
	{
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(email);
		return m.find();
	}
	
	public static int calculateWeiboLength(CharSequence c)
	{ 
        double len = 0; 
        for (int i = 0; i < c.length(); i++) { 
                int temp = (int)c.charAt(i); 
                if (temp > 0 && temp < 127) { 
                        len += 0.5; 
                }else{ 
                        len ++; 
                } 
        } 
     return (int)Math.round(len); 
 }
}
