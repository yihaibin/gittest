package com.qyer.android.oneday.action;

import java.io.File;
import java.util.List;

import com.qyer.android.oneday.http.domain.QyerApp;
import com.qyer.android.oneday.util.IOUtil;

import android.content.Context;

public class QyerAppAction {
	
	private static final String FILE_NAME = "QyerApps";

	private Context mContext;
	
	public QyerAppAction(Context context)
	{
		mContext = context;
	}
	
	public void saveQyerApps(List<QyerApp> apps)
	{
		File f = new File(mContext.getFilesDir(),FILE_NAME);
		IOUtil.writeObj(apps, f);
	}
	
	@SuppressWarnings("unchecked")
	public List<QyerApp> getQyerApps()
	{
		File f = new File(mContext.getFilesDir(),FILE_NAME);
		if(!f.exists())
			return null;
		
		return (List<QyerApp>)IOUtil.readObj(f);
	}
}
