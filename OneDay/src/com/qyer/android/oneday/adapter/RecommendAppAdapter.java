package com.qyer.android.oneday.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qyer.android.oneday.R;
import com.qyer.android.oneday.http.domain.RecommendApp;
import com.qyer.android.oneday.util.AsyncImageLoader;
import com.qyer.android.oneday.util.EnvironmentUtil;
import com.qyer.android.oneday.util.AsyncImageLoader.ImageCallback;

public class RecommendAppAdapter extends CustomBaseAdapter<RecommendApp>{

	private AsyncImageLoader mAsyncImageLoader;
	
	public RecommendAppAdapter(Context context) 
	{
		super(context);
		initImageLoader();
	}
	
	public RecommendAppAdapter(Context context, List<RecommendApp> data) 
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
		View convertView = getLayoutInflater().inflate(R.layout.item_recommend_app, null);
		ViewHolder vh = new ViewHolder();
		vh.ivAppIcon = (ImageView) convertView.findViewById(R.id.ivAppIcon);
		vh.tvAppTitle = (TextView) convertView.findViewById(R.id.tvAppTitle);
		vh.tvAppDesc = (TextView) convertView.findViewById(R.id.tvAppDesc);
		convertView.setTag(vh);
		return convertView;
	}

	@Override
	protected void freshConvertView(int position, View convertView, ViewGroup parent) 
	{
		RecommendApp ra = getItem(position);
		ViewHolder vh = (ViewHolder) convertView.getTag();
		vh.tvAppTitle.setText(ra.getTitle());
		vh.tvAppDesc.setText(ra.getDesc());
		vh.ivAppIcon.setTag(ra.getThumb());
		
		final ImageView iv = vh.ivAppIcon;
		Drawable d = mAsyncImageLoader.asyncImageLoad(ra.getThumb(), new ImageCallback() {
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
		if(mAsyncImageLoader != null)
			mAsyncImageLoader.release();
	}
	
	private class ViewHolder {
		ImageView ivAppIcon;
		TextView tvAppTitle,tvAppDesc;
	}
}
