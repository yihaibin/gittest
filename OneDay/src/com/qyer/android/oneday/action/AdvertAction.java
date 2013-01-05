package com.qyer.android.oneday.action;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qyer.android.oneday.http.domain.Advert;
import com.qyer.android.oneday.util.EnvironmentUtil;
import com.qyer.android.oneday.util.IOUtil;
import com.qyer.android.oneday.util.NetWorkUtil;

public class AdvertAction {
	
	private static final String FILE_NAME = "advert";
	private Context mContext;
	
	public AdvertAction(Context context)
	{
		mContext = context;
	}
	
	public void deleteAdvert()
	{
		File advertInfo = new File(mContext.getFilesDir(),FILE_NAME);
		if(advertInfo.exists())
			advertInfo.delete();
		
		File adverImage = new File(EnvironmentUtil.getSdcardPicsDir(),FILE_NAME);
		if(adverImage.exists())
			adverImage.delete();
	}
	
	public void saveAdvert(Advert advert)
	{
		File f = new File(mContext.getFilesDir(),FILE_NAME);
		IOUtil.writeObj(advert, f);
		NetWorkUtil.saveUrlContentAsFile(advert.getImageUrl(), new File(EnvironmentUtil.getSdcardPicsDir(),FILE_NAME));
	}
	
	public Advert getAdvert()
	{
		File f = new File(mContext.getFilesDir(),FILE_NAME);
		if(!f.exists())
			return null;
		
		return (Advert) IOUtil.readObj(f);
	}
	
	public Bitmap getAdvertImage()
	{
		return BitmapFactory.decodeFile(new File(EnvironmentUtil.getSdcardPicsDir(),FILE_NAME).getAbsolutePath());
	}
}
