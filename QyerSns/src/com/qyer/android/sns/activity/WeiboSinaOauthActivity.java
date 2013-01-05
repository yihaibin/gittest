package com.qyer.android.sns.activity;

import com.qyer.android.sns.R;
import com.qyer.android.sns.activity.SinaWeiboService.BaseRequest;
import com.qyer.android.sns.activity.SinaWeiboService.OauthRequest;
import com.qyer.android.sns.http.HttpParams;
import com.qyer.android.sns.http.SinaRespOauth;
import com.qyer.android.sns.util.SnsLogManager;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WeiboSinaOauthActivity extends Activity implements HttpParams{
    
	public static final String TAG = "OauthActivity";
	
	private WebView mWvOauth;
	private Dialog mDialog;
	private BaseRequest mRequest;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_oauth);
        
        if(!initData(savedInstanceState)){
        	finish();
        	return;
        }
        
        initContentView();
        showLoadingDialog();
    }
	
	private boolean initData(Bundle savedInstanceState)
	{
		mRequest = SinaWeiboService.getRequest();
		if(savedInstanceState != null || mRequest == null)
			return false;
		else 
			return true;
	}
	
	private void initContentView() 
	{
		mWvOauth = (WebView) findViewById(R.id.wvOauthPage);
		mWvOauth.getSettings().setJavaScriptEnabled(true);
		CookieSyncManager.createInstance(getApplicationContext());  
		CookieManager.getInstance().removeAllCookie();  
		mWvOauth.clearCache(true); 

		mWvOauth.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) 
			{
				SnsLogManager.printD(TAG, "onPageStarted url = "+url);
				if(url.startsWith(mRequest.redirectUrl))
				{
					SinaRespOauth soi = new SinaRespOauth();//SinaResponseParser.parseSinaOauth2Info(url);
					soi.setDataFromJson(url);
					if(soi.isParseJsonSuccess()){
						SnsLogManager.printD(TAG, "soi.getExpiresIn() = "+soi.getExpiresIn());
						mRequest.prefs.saveOauth2Info(soi.getAccessToken(), 
											 		 soi.getExpiresIn(), 
											 		 soi.getUid());
						
						mWvOauth.stopLoading();
						if(mRequest instanceof OauthRequest){
							((OauthRequest)mRequest).lisn.onSuccess();
							finish();
						}else{
							SinaWeiboService.startWeiboEditActivity(WeiboSinaOauthActivity.this);
							finishNoRecycle();
						}
					}else{
						//oauth interface expires in or user click webView cancel
//						finish();
					}
				}
			}
			
			@Override
			public void onPageFinished(WebView view, String url) 
			{
				if(url.startsWith(SINA_URL_OAUTH2)){
					dismissLoadingDialog();
				}
			}
		});		
		mWvOauth.loadUrl(String.format(HttpParams.SINA_URL_OAUTH2_CHECK, mRequest.appKey,mRequest.redirectUrl));
	}
	
	private void showLoadingDialog() 
	{
		mDialog = createProgressDialog();
		mDialog.show();
	}
	
	private void dismissLoadingDialog()
	{
		if(mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
	}
	
	private Dialog createProgressDialog()
	{
		ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage(getResources().getString(R.string.loading_oauth_page));
		pd.setCanceledOnTouchOutside(false);
		pd.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) 
			{
				finish();
			}
		});
		return pd;
	}
	
	public void finishNoRecycle()
	{
		super.finish();
	}
	
	@Override
	public void finish() 
	{
		super.finish();
		
		if(mRequest != null){
			mRequest.recycle();
		}
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		if(!isFinishing())
			return;
		
		dismissLoadingDialog();
		
		if(mWvOauth != null)
			mWvOauth.stopLoading();	
	}
}