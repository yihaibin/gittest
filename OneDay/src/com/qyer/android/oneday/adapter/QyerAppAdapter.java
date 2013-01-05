package com.qyer.android.oneday.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qyer.android.oneday.R;
import com.qyer.android.oneday.http.domain.QyerApp;
import com.qyer.android.oneday.util.AsyncImageLoader;
import com.qyer.android.oneday.util.EnvironmentUtil;
import com.qyer.android.oneday.util.AsyncImageLoader.ImageCallback;

public class QyerAppAdapter extends CustomBaseAdapter<QyerApp>{

	private AsyncImageLoader mAsyncImageLoader;
	
	public QyerAppAdapter(Context context) 
	{
		super(context);
		initImageLoader();
	}
	
	public QyerAppAdapter(Context context, List<QyerApp> data) 
	{
		super(context, data);
		initImageLoader();
	}
	
	private void initImageLoader()
	{
		mAsyncImageLoader = new AsyncImageLoader();
		mAsyncImageLoader.setImageSaveDir(EnvironmentUtil.getSdcardPicsDir().getAbsolutePath());
	}

	@Override
	protected View createConvertView(int position, ViewGroup parent) 
	{
		View convertView = getLayoutInflater().inflate(R.layout.item_qyer_app, null);
		ViewHolder vh = new ViewHolder();
		vh.ivAppIcon = (ImageView) convertView.findViewById(R.id.ibtnAppIcon);
		vh.tvAppName = (TextView) convertView.findViewById(R.id.tvAppName);
		convertView.setTag(vh);
		return convertView;
	}

	@Override
	protected void freshConvertView(int position, View convertView, ViewGroup parent) 
	{
		QyerApp qa = getItem(position);
		ViewHolder vh = (ViewHolder) convertView.getTag();
		vh.tvAppName.setText(qa.getSubName());
		vh.ivAppIcon.setTag(qa.getThumbUrl());
		final ImageView iv = vh.ivAppIcon;
		Drawable d = mAsyncImageLoader.asyncImageLoad(qa.getThumbUrl(), new ImageCallback() {
			@Override
			public void onImageLoaded(String imagePath, Drawable drawable) 
			{
				if(imagePath.equals(iv.getTag()) && drawable != null)
					iv.setImageDrawable(drawable);
			}
		});
		
		if(d != null){
			vh.ivAppIcon.setImageDrawable(d);
		}else{
			vh.ivAppIcon.setImageResource(R.drawable.ic_app);
		}
	}
	
	@Override
	public void release() 
	{
		if (mAsyncImageLoader != null)
			mAsyncImageLoader.release();
	}
	
	private class ViewHolder {
		ImageView ivAppIcon;
		TextView tvAppName;
	}
}
