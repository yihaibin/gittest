package com.qyer.android.oneday.activity;

import com.qyer.android.oneday.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends BaseActivity{
	
	private static final String TAG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main,TITLE_BAR_STYLE_IBTN);
	}
	
	protected void initTitleView()
	{
		ImageButton ibtn = (ImageButton) getTitleBarLeftBtn();
		ibtn.setImageResource(R.drawable.ic_setting);
		ibtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				startSettingActivity();
			}
		});
		
		Button btn = getTitleBarMidBtn();
		btn.setText(R.string.app_name);
		
		ibtn = (ImageButton) getTitleBarRightBtn();
		ibtn.setImageResource(R.drawable.ic_search);
		ibtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
			}
		});
	}
	
	private void startSettingActivity()
	{
		Intent intent = new Intent();
		intent.setClass(this, SettingActivity.class);
		startActivity(intent);
	}
}
