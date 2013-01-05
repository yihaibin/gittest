package com.qyer.android.sns.activity;

import com.qyer.android.sns.activity.SinaWeiboService.BaseRequest;
import com.qyer.android.sns.activity.SinaWeiboService.OauthRequest;
import com.qyer.android.sns.activity.SinaWeiboService.ShareRequest;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class WeiboSinaSsoActivity extends Activity{
	
	private SsoHandler  mSsoHandler;
	private BaseRequest mRequest;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		initData();
	}

	private void initData() 
	{
		mRequest = SinaWeiboService.getRequest();
		if(mRequest == null){
			finish();
			return;
		}
		
		Weibo weibo = Weibo.getInstance(mRequest.appKey, mRequest.redirectUrl);
		mSsoHandler = new SsoHandler(this, weibo);
		mSsoHandler.authorize(new WeiboAuthListener() {
			
			@Override
			public void onWeiboException(WeiboException arg0)
			{
			}
			
			@Override
			public void onError(WeiboDialogError arg0)
			{
			}
			
			@Override
			public void onComplete(Bundle arg0) 
			{
			}
			
			@Override
			public void onCancel() 
			{
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		try{
			
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		
		}catch(Exception e){
			
		}
		
		if(data == null){
		
			finishRecycle();
		}else{
			
			boolean saveResult = saveSinaWeiboAccount(data);
			if(saveResult && mRequest instanceof OauthRequest){
				((OauthRequest) mRequest).lisn.onSuccess();
				finishRecycle();
			}else if (saveResult && mRequest instanceof ShareRequest){
				SinaWeiboService.startWeiboEditActivityNewTask(getApplicationContext());
				finish();
			}else{
				finishRecycle();
			}
		}
	}
	
	private boolean saveSinaWeiboAccount(Intent data)
	{
		try{
			
			mRequest.prefs.saveExpiresTime(Long.parseLong(data.getStringExtra(Weibo.KEY_EXPIRES)));
			mRequest.prefs.saveAccessToken(data.getStringExtra(Weibo.KEY_TOKEN));
			mRequest.prefs.saveUid(Long.parseLong(data.getStringExtra("uid")));
			mRequest.prefs.saveNickName("");
			return true;
			
		}catch(Exception e){
			return false;
		}
	}
	
	public void finishRecycle()
	{
		super.finish();
		if(mRequest != null)
			mRequest.recycle();
	}
}
