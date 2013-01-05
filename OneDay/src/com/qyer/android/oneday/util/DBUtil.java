package com.qyer.android.oneday.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.qyer.android.oneday.db.DBFiled;

import android.content.Context;

public class DBUtil {

	public static boolean dbIsExist(Context context)
	{
		File f = context.getDatabasePath(DBFiled.DB_NAME);
		return f.exists();
	}
	
	public static void copyDataBase(Context context)
	{
		InputStream input = null;
		OutputStream output = null;
		try{
			File f = context.getDatabasePath(DBFiled.DB_NAME);
			f.getParentFile().mkdirs();
			input = context.getAssets().open(DBFiled.DB_NAME);
			output = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int len = -1;
			while((len = input.read(buffer)) != -1){
				output.write(buffer, 0,len);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			IOUtil.closeInStream(input);
			IOUtil.closeOutStream(output);
		}
	}
}
