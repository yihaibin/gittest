package com.qyer.android.oneday.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

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

}
