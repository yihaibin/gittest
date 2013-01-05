package com.qyer.android.oneday.action;

import java.io.File;
import java.util.List;

import com.qyer.android.oneday.http.domain.RecommendApp;
import com.qyer.android.oneday.util.IOUtil;

import android.content.Context;

public class RecommendAppAction {

	private static final String FILE_NAME = "RecommendApp";
	
	private Context mContext;
	
	public RecommendAppAction(Context context)
	{
		mContext = context;
	}
	
	public void saveRecommendApps(List<RecommendApp> apps)
	{
		File f = new File(mContext.getFilesDir(),FILE_NAME);
		IOUtil.writeObj(apps, f);
	}
	
	@SuppressWarnings("unchecked")
	public List<RecommendApp> getReommendApps()
	{
		File f = new File(mContext.getFilesDir(),FILE_NAME);
		if(!f.exists())
			return null;
		
		return (List<RecommendApp>)IOUtil.readObj(f);
	}
}
