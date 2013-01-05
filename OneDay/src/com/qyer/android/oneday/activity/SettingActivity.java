package com.qyer.android.oneday.activity;

import java.util.List;

import com.qyer.android.oneday.R;
import com.qyer.android.oneday.action.QyerAppAction;
import com.qyer.android.oneday.action.RecommendAppAction;
import com.qyer.android.oneday.adapter.QyerAppAdapter;
import com.qyer.android.oneday.adapter.RecommendAppAdapter;
import com.qyer.android.oneday.context.Constant;
import com.qyer.android.oneday.http.HttpCallback;
import com.qyer.android.oneday.http.HttpRequest;
import com.qyer.android.oneday.http.domain.QyerApp;
import com.qyer.android.oneday.http.domain.RecommendApp;
import com.qyer.android.oneday.http.response.BaseResponse;
import com.qyer.android.oneday.http.response.QyerAppResponse;
import com.qyer.android.oneday.http.response.RecommendAppResponse;
import com.qyer.android.oneday.util.AppInfoUtil;
import com.qyer.android.oneday.util.DeviceUtil;
import com.qyer.android.oneday.util.UmengEvent;
import com.qyer.android.oneday.view.NoCacheGridView;
import com.qyer.android.oneday.view.NoCacheListView;
import com.qyer.android.sns.activity.SinaWeiboService;
import com.qyer.android.sns.activity.SinaWeiboService.ErrorCode;
import com.qyer.android.sns.activity.SinaWeiboService.OauthListener;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingActivity extends BaseActivity implements Constant{
	
	public static final String TAG = "SettingActivity";
	
	private QyerAppAdapter mQyerAppAdapter;
	private RecommendAppAdapter mRecoAdapter;
	private QyerAppAction mQyerAppAction;
	private RecommendAppAction mRecoAction;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_setting,TITLE_BAR_STYLE_IBTN);
		
		loadAppsFromServer();
	}
	
	@Override
	protected void onStart() 
	{
		super.onStart();
		freshSinaWeiboBind(SinaWeiboService.isAuthorized(this));
	}
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		mQyerAppAdapter.release();
		mRecoAdapter.release();
	}
	
	protected void initData() 
	{
		mQyerAppAction = new QyerAppAction(this);
		List<QyerApp> qyerApps = mQyerAppAction.getQyerApps();
		mQyerAppAdapter = new QyerAppAdapter(this, qyerApps);
		
		mRecoAction = new RecommendAppAction(this);
		List<RecommendApp> recoApps = mRecoAction.getReommendApps();
		mRecoAdapter = new RecommendAppAdapter(this, recoApps);
	}

	protected void initTitleView() 
	{
		ImageButton ibtn = (ImageButton) getTitleBarLeftBtn();
		ibtn.setImageResource(R.drawable.ic_back);
		ibtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		
		Button btn = getTitleBarMidBtn();
		btn.setText(R.string.setting);
		
		getTitleBarRightBtn().setVisibility(View.GONE);
	}
	
	protected void initContentView()
	{
		Button btnBindSinaWeibo = (Button) findViewById(R.id.btnBindSinaWeibo);
		btnBindSinaWeibo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				handleSinaWeiboBind();
			}
		});
		
		TextView tvVersionName = (TextView) findViewById(R.id.tvVersionName);
		tvVersionName.setText(getResources().getString(R.string.versionName, AppInfoUtil.getVersionName(this)));
		
		LinearLayout llbtn = (LinearLayout) findViewById(R.id.llbtnOpinionWe);
		llbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				startResolverActivity();
			}
		});
		
		llbtn = (LinearLayout) findViewById(R.id.llBtnAppShare);
		llbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				startAppShareActivity();
			}
		});		
		
		llbtn = (LinearLayout) findViewById(R.id.llBtnFeedBack);
		llbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				startFeedBackActivity();
			}
		});		
		
		NoCacheGridView gv = (NoCacheGridView) findViewById(R.id.gvQyerApp);
		gv.setAdapter(mQyerAppAdapter);
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				handleQyerAppItemClick(position);
			}
		});
		
		NoCacheListView lv = (NoCacheListView) findViewById(R.id.lvRecommendApp);
		lv.setAdapter(mRecoAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				handleOnRecommendAppItemClick(position);
			}
		});
	}
	
	private void handleSinaWeiboBind()
	{
		boolean isBinded = SinaWeiboService.isAuthorized(this);
		if(isBinded){
			showUnbindDialog();
		}else{
			//bind sina weibo
			SinaWeiboService.oauth(this, APP_KEY_WEIBO_SINA, APP_REDIRECTURL_WEIBO_SINA, new OauthListener() {
				@Override
				public void onSuccess()
				{
					showToast(R.string.toast_bindSina_succeed);
					freshSinaWeiboBind(true);
				}
				@Override
				public void onFailed(int errorCode)
				{
					if(errorCode == ErrorCode.ERROR_INTERNET_DISABLE)
						showToast(R.string.toast_noInternet);
					else
						showToast(R.string.toast_bindSina_failed);
				}
			});
		}
	}
	
	private void freshSinaWeiboBind(boolean isBinded)
	{
		Button btnBindSinaWeibo = (Button) findViewById(R.id.btnBindSinaWeibo);
		if(isBinded){
			btnBindSinaWeibo.setText(R.string.weibo_sina_unbind);
		}else{
			btnBindSinaWeibo.setText(R.string.weibo_sina_binded);
		}
	}	

	private void showUnbindDialog()
	{
		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCanceledOnTouchOutside(true);
		ad.setMessage(getResources().getString(R.string.unbind_SinaWeiboAccount));
		
		ad.setButton(-1, getResources().getString(R.string.confirm), new android.app.AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				SinaWeiboService.writeOffAccount(SettingActivity.this);
				freshSinaWeiboBind(false);
			}
		});
		
		ad.setButton(-2, getResources().getString(R.string.cancel), new android.app.AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});
		ad.show();
	}
	
	private void handleQyerAppItemClick(int position) 
	{
		QyerApp qa = mQyerAppAdapter.getItem(position);

		if (DeviceUtil.hasApp(this, qa.getPackageName())) {
			startQyerApp(qa.getPackageName());
		} else {
			startUriActivity(qa.getAppstoreUrl());
		}
		onUmengEvent(UmengEvent.QYER_APP_CLICK, qa.getSubName());
	}
	
	protected void handleOnRecommendAppItemClick(int position) 
	{
		RecommendApp ra = mRecoAdapter.getItem(position);
		startUriActivity(ra.getLink());
		onUmengEvent(UmengEvent.RECOMMEND_APP_CLICK, ra.getTitle());
	}	

	private void startQyerApp(String packageName)
	{
		try{
			Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
			startActivity(intent);
		}catch(Exception e){}
	}	
	
	private void startResolverActivity()
	{
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setData(Uri.parse("market://details?id="+getPackageName()));
		intent.setComponent(new ComponentName("android", "com.android.internal.app.ResolverActivity"));
		startActivity(intent);
	}	
	
	private void startAppShareActivity()
	{
		Intent intent = new Intent();
		intent.setClass(this, ShareAppActivity.class);
		startActivity(intent);
	}	
	
	private void startFeedBackActivity()
	{
		Intent intent = new Intent();
		intent.setClass(this, FeedBackActivity.class);
		startActivity(intent);
	}	
	
	private void showLoadAppProgress(int divId)
	{
		if(divId == R.id.llQyerAppDiv)
			findViewById(R.id.pbQyerApp).setVisibility(View.VISIBLE);
		else
			findViewById(R.id.pbRecommendApp).setVisibility(View.VISIBLE);
	}
	
	private void hideLoadAppProgress(int divId)
	{
		if(divId == R.id.llQyerAppDiv)
			findViewById(R.id.pbQyerApp).setVisibility(View.GONE);
		else
			findViewById(R.id.pbRecommendApp).setVisibility(View.GONE);
	}
	
	private void hideAppView(int divId)
	{
		if(divId == R.id.llQyerAppDiv)
			findViewById(R.id.llQyerAppDiv).setVisibility(View.GONE);
		else
			findViewById(R.id.llRecommendAppDiv).setVisibility(View.GONE);
	}
	
	private void loadAppsFromServer() 
	{
		if(DeviceUtil.isConnectInternet(this)){

			if(mQyerAppAdapter.getCount() == 0){
				showLoadAppProgress(R.id.llQyerAppDiv);
			}
			startQyerAppTask();
			
			if(mRecoAdapter.getCount() == 0){
				showLoadAppProgress(R.id.llRecommendAppDiv);	
			}
			startRecommendTask();
		}else{
			
			if(mQyerAppAdapter.getCount()==0)
				hideAppView(R.id.llQyerAppDiv);
			
			if(mRecoAdapter.getCount()==0)
				hideAppView(R.id.llRecommendAppDiv);
			
		}
	}	
	
	private void startQyerAppTask()
	{
		HttpRequest.getQyerMoreApp(new HttpCallback() {
			@Override
			public void doInBackground(BaseResponse responser) 
			{
				if(!responser.isConnException())
					mQyerAppAction.saveQyerApps(((QyerAppResponse)(responser)).getApps());
			}
			@Override
			public void onPost(BaseResponse responser) 
			{
				if(isFinishing())
					return;
				
				QyerAppResponse qar = (QyerAppResponse) responser;
				if(qar.isConnException()){
					if(mQyerAppAdapter.getCount() == 0){
						hideLoadAppProgress(R.id.llQyerAppDiv);
						hideAppView(R.id.llQyerAppDiv);
					}
				}else{
					mQyerAppAdapter.setData(qar.getApps());
					mQyerAppAdapter.notifyDataSetChanged();
					hideLoadAppProgress(R.id.llQyerAppDiv);
					if(mQyerAppAdapter.getCount() == 0)
						hideAppView(R.id.llQyerAppDiv);
				}
			}
		});
	}
	
	private void startRecommendTask()
	{
		HttpRequest.getRecommendApp(new HttpCallback() {
			@Override
			public void doInBackground(BaseResponse responser) 
			{
				if(!responser.isConnException())
					mRecoAction.saveRecommendApps(((RecommendAppResponse)responser).getApps());
			}
			@Override
			public void onPost(BaseResponse responser) 
			{
				if(isFinishing())
					return;
				
				if(responser.isConnException()){
					if(mRecoAdapter.getCount() == 0){
						hideLoadAppProgress(R.id.llRecommendAppDiv);
						hideAppView(R.id.llRecommendAppDiv);
					}
				}else{
					RecommendAppResponse rar = (RecommendAppResponse) responser;
					mRecoAdapter.setData(rar.getApps());
					mRecoAdapter.notifyDataSetChanged();
					hideLoadAppProgress(R.id.llRecommendAppDiv);
					if(mRecoAdapter.getCount() == 0)
						hideAppView(R.id.llRecommendAppDiv);
				}
			}
		});
	}
}
