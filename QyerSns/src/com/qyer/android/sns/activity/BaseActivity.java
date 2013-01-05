package com.qyer.android.sns.activity;

import com.qyer.android.sns.R;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public abstract class BaseActivity extends Activity{

	private ViewGroup mTitleView, mContentView;
	private String mCurrentToastText = "";
	
	private Handler mToastHandler = new Handler(){
		public void handleMessage(android.os.Message msg) 
		{
			mCurrentToastText = "";
		};
	};
	
	protected void onCreate(android.os.Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}
	
	protected void setTitleBackground(int rid)
	{
		if(mTitleView == null)
			return;
		
		mTitleView.setBackgroundResource(rid);
	}
	
	protected void setTitleBackgroundColor(int color)
	{
		if(mTitleView == null)
			return;
		
		mTitleView.setBackgroundColor(color);
	}
	
	protected void setTitleBarBackground(int rid)
	{
		mTitleView.setBackgroundResource(rid);
	}
	
	@Override
	public void setContentView(int layoutResID) 
	{
		super.setContentView(R.layout.act_base_ibtn_btn);
		mTitleView = (ViewGroup)findViewById(R.id.titleBar);
		mContentView = (ViewGroup) findViewById(R.id.content);
		mContentView.addView(getLayoutInflater().inflate(layoutResID, null));		
	}
	
	protected Button getTitleBarMidBtn()
	{
		return (Button) findViewById(R.id.btnMid);
	}
	
	protected View getTitleBarLeftBtn()
	{
		return findViewById(R.id.btnLeft);
	}
	
	protected View getTitleBarRightBtn()
	{
		return findViewById(R.id.btnRight);
	}
	
	protected View getTitleBar()
	{
		return mTitleView;
	}
	
	protected View getTitleBarSplitView()
	{
		return (View) findViewById(R.id.vSplit);
	}
	
	protected void setTtitleBarVisible(int visibility)
	{
		mTitleView.setVisibility(visibility);
	}
    
	protected void showToast(String text)
	{
		if(!mCurrentToastText.equals(text)){
			mCurrentToastText = text;
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
			mToastHandler.sendEmptyMessageDelayed(0, 2000);
		}
	}
	
	protected void showToast(int rid)
	{
		showToast(getResources().getString(rid));
	}
}
