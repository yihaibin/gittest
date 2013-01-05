package com.qyer.android.oneday.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {

	public static Bitmap loadBitmapFromFile(String imagePath)
	{
		InputStream input = null;
		Bitmap bmp = null;
		try{
			
			input = new FileInputStream(imagePath);
			bmp = BitmapFactory.decodeStream(input);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			IOUtil.closeInStream(input);
		}
		
		return bmp;
	}
	
	public static Bitmap loadBitmapFromFile(File imageFile)
	{
		InputStream input = null;
		Bitmap bmp = null;
		try{
			if(!imageFile.exists())
				return null;
			
			input = new FileInputStream(imageFile);
			bmp = BitmapFactory.decodeStream(input);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			IOUtil.closeInStream(input);
		}
		
		return bmp;
	}	
	
	public static Bitmap loadBitmapFromUrl(String url)
	{
		if(TextUtil.isEmpty(url))
			return null;
		
		Bitmap bmp = null;
		InputStream input = null;
		
		try {
			
			URL urlObj = new URL(url);
			input = (InputStream) urlObj.getContent();
			bmp = BitmapFactory.decodeStream(input);
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.closeInStream(input);
		}
		
		return bmp;
	}
	
	public static boolean storeBitmap(Bitmap bitmap, String fileName, int quality)
	{
		if(bitmap == null || fileName == null)
			return false;
		
		return storeBitmap(bitmap, new File(fileName), quality);
	}
	
	public static boolean storeBitmap(Bitmap bitmap, File saveFile, int quality)
	{
		if(bitmap == null || saveFile == null)
			return false;
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(saveFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, quality, fos);
			return true;
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtil.closeOutStream(fos);
		}
		return false;
	}
}
