package com.qyer.android.oneday.activity;

import com.qyer.android.oneday.R;
import com.qyer.android.oneday.pref.AppConfigPrefs;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public abstract class BaseActivity extends Activity{

	protected static final int TITLE_BAR_STYLE_BTN = 0;//text btn
	protected static final int TITLE_BAR_STYLE_IBTN = 1;//image btn
	protected static final int TITLE_BAR_STYLE_IBTN_BTN = 2;//image btn
	
	private static String mCurrentToastText = "";
	
	private Handler mToastHandler = new Handler(){
		public void handleMessage(android.os.Message msg) 
		{
			mCurrentToastText = "";
		};
	};
	
	protected void onCreate(android.os.Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
//		MobclickAgent.onError(this);
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
//		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
//		MobclickAgent.onPause(this);
	}	
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
	}
	
	protected void setContentView(int rid, int titleBarStyle)
	{
		switch (titleBarStyle) {
		case TITLE_BAR_STYLE_BTN:
			super.setContentView(R.layout.act_base_btn);
			break;
		case TITLE_BAR_STYLE_IBTN:
			super.setContentView(R.layout.act_base_ibtn);
			break;
		case TITLE_BAR_STYLE_IBTN_BTN:
			super.setContentView(R.layout.act_base_ibtn_btn);
			break;			
		default:
			super.setContentView(R.layout.act_base_btn);
		}
		
		ViewGroup vg = (ViewGroup) findViewById(R.id.content);
		vg.addView(getLayoutInflater().inflate(rid, null));
		callbackInit();
	}
	
	@Override
	public void setContentView(int layoutResID) 
	{
		super.setContentView(layoutResID);
		callbackInit();
	}
	
	private void callbackInit()
	{
		initData();
		initTitleView();
		initContentView();
	}
	
	protected void initData()
	{
	}
	
	protected void initTitleView()
	{
	}
	
	protected void initContentView()
	{
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
		return findViewById(R.id.titleBar);
	}
	
	protected void setTtitleBarVisible(int visibility)
	{
		getTitleBar().setVisibility(visibility);
	}
	
	protected void showToast(int rid)
	{
		showToast(getResources().getString(rid));
	}
	
	protected void showToast(String text)
	{
		if(text == null || text.equals(mCurrentToastText))
			return;
		
		mCurrentToastText = text;
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		mToastHandler.sendEmptyMessageDelayed(0, 2000);
	}
	
	protected AppConfigPrefs getAppConfigPrefs()
	{
		return AppConfigPrefs.getInstance(getApplicationContext());
	}
	
	protected void onUmengEvent(String key)
	{
//		MobclickAgent.onEvent(this, key);
	}
	
	protected void onUmengEvent(String key, String info)
	{
//		MobclickAgent.onEvent(this, key, info);
	}
	
	protected void startUriActivity(String uriStr) 
	{
		try {
			Uri uri = Uri.parse(uriStr);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
