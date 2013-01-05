package com.qyer.android.sns.activity;

import com.qyer.android.sns.util.SnsDeviceUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class SinaWeiboService {
	
	public static final String TAG = "SinaWeiboService";
	
	private static BaseRequest mRequest;
	
	private SinaWeiboService()
	{
	}
	
	public static boolean isAuthorized(Context context)
	{
		return new SinaWeiboPrefs(context).isOauth();
	}
	
	public static void writeOffAccount(Context context)
	{
		SinaWeiboPrefs prefs = new SinaWeiboPrefs(context);
		prefs.saveAccessToken("");
		prefs.saveNickName("");
		prefs.saveUid(0);
		prefs.saveExpiresTime(0);
	}
	
	public static void oauth(Context context, String appKey, String redirectUrl, OauthListener lisn)
	{
		OauthRequest oauthRequest = createOauthRequest(context, appKey, redirectUrl, lisn);
		if(SnsDeviceUtil.hasSinaWeiboClient(context)){
			
			startWeiboSinaSSoActivity(context);
		}else{
			//check internet
			if(SnsDeviceUtil.isConnectInternet(context)){
				startOauthActivity(context);
			}else{
				oauthRequest.lisn.onFailed(ErrorCode.ERROR_INTERNET_DISABLE);
				return;
			}
		}
		mRequest = oauthRequest;
	}
	
	public static void share(Context context, String appKey, String redirectUrl, String text, Bitmap bmp, ShareListener lisn)
	{	
		ShareRequest shareReuqest = createShareRequest(context, appKey, redirectUrl, text, bmp, lisn);
		if(shareReuqest.prefs.isOauth()){
		
			//has access_token and no expire
			startWeiboEditActivity(context);
		}else{
			
			if(SnsDeviceUtil.hasSinaWeiboClient(context)){
				startWeiboSinaSSoActivity(context);
			}else{
				//no access_token and token expire ,so check access_token
				if(SnsDeviceUtil.isConnectInternet(context)){
					startOauthActivity(context);
				}else{
					shareReuqest.lisn.onShareFailed(ErrorCode.ERROR_INTERNET_DISABLE);
					return;
				}
			}
		}
		mRequest = shareReuqest;
	}
	
	public static void share(Context context, String appKey, String redirectUrl, String text, ShareListener lisn)
	{
		share(context, appKey, redirectUrl, text, null, lisn);
	}
	
/*
 * protected method , for lib use	
 */
	protected static void startOauthActivity(Context context)
	{
		Intent intent = new Intent();
		intent.setClass(context, WeiboSinaOauthActivity.class);
		context.startActivity(intent);
	}
	
	protected static void startWeiboEditActivity(Context context)
	{
		Intent intent = new Intent();
		intent.setClass(context, WeiboSinaEditActivity.class);
		context.startActivity(intent);
	}
	
	protected static void startWeiboEditActivityNewTask(Context context)
	{
		Intent intent = new Intent();
		intent.setClass(context, WeiboSinaEditActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	protected static void startWeiboSinaSSoActivity(Context context)
	{
		Intent intent = new Intent();
		intent.setClass(context, WeiboSinaSsoActivity.class);
		context.startActivity(intent);
	}
	
	protected static BaseRequest getRequest()
	{
		return mRequest;
	}
	
/*
* private help method*******************************
*/
	private static OauthRequest createOauthRequest(Context context, String appKey, String redirectUrl, OauthListener lisn)
	{
		OauthRequest or = new OauthRequest();
		or.appKey = appKey;
		or.redirectUrl = redirectUrl;
		or.lisn = lisn;
		or.prefs = new SinaWeiboPrefs(context);
		return or;
	}
	
	private static ShareRequest createShareRequest(Context context, String appKey, String redirectUrl, String text, Bitmap bmp,ShareListener lisn)
	{
		ShareRequest sr = new ShareRequest();
		sr.appKey = appKey;
		sr.redirectUrl = redirectUrl;
		sr.text = text;
		sr.bmp = bmp;
		sr.lisn = lisn;
		sr.prefs = new SinaWeiboPrefs(context);
		return sr;
	}
	
/*
 *  interface
 */
	public static interface OauthListener{
		public void onFailed(int errorCode);
		public void onSuccess();
	}
	
	public static interface ShareListener{
		public void onShareSuccess();
		public void onShareFailed(int errorCode);
	}
	
	public static interface ErrorCode{
		public static final int ERROR_NO_SEND = 0;
		public static final int ERROR_INTERNET_DISABLE = 1;
		public static final int ERROR_INTERNET_TIMEOUT = 2;
		public static final int ERROR_WEIBO_FAILED = 3;
		public static final int ERROR_WEIBO_SIMILARITY = 20017;
		public static final int ERROR_WEIBO_REPEAT = 20019;
		public static final int ERROR_WEIBO_OUT_LIMIT = 20016;
		public static final int ERROR_WEIBO_TOKEN_EXPIRED = 21315;
		public static final int ERROR_WEIBO_EXPIRED_TOKEN = 21327;
	}

/*
 *  inner class ***********************************	
*/	
	protected static class BaseRequest{
		 String appKey;
		 String redirectUrl;
		 SinaWeiboPrefs prefs;
		 
		 public void recycle(){}
	 }
	
	protected static class OauthRequest extends BaseRequest{
		
		OauthListener lisn;
		
		OauthRequest(){}
		
		public void recycle()
		{
			SinaWeiboService.mRequest = null;
			System.out.println("recycle OauthRequest");
		}
		
		@Override
		public String toString() 
		{
			return "appKey = "+appKey+", prefs = "+prefs+", lisn = "+lisn;
		}
	}	
	 
	protected static class ShareRequest extends BaseRequest{
		 String text;
		 Bitmap bmp;
		 ShareListener lisn;
		 ShareRequest(){}
		 
		 public void recycle()
		 {
			 if(bmp != null && !bmp.isRecycled())
				 bmp.recycle();
			 
			 SinaWeiboService.mRequest = null;
			 System.out.println("recycle ShareRequest");
		 }
			
		 @Override
		 public String toString() 
		 {
			 return "appKey = "+appKey+", prefs = "+prefs+", lisn = "+lisn+", text="+text+", bmp = "+bmp;
		 }		 
	}
	 
	protected static class SinaWeiboPrefs{
		
		private static final String ACCESS_TOKEN = "access_token";
		private static final String EXPIRES_TIME = "expires_time";
		private static final String NICK_NAME = "nick_name";
		private static final String UID = "uid";
		
		private SharedPreferences mSharedPref;
		
		private SinaWeiboPrefs(Context context)
		{
			mSharedPref = context.getSharedPreferences("sinaweio_pref", Context.MODE_PRIVATE);
		}
		
		public void saveAccessToken(String accessToken)
		{
			mSharedPref.edit().putString(ACCESS_TOKEN, accessToken).commit();
		}
		
		public String getAccessToken()
		{
			return mSharedPref.getString(ACCESS_TOKEN, "");
		}
		
		public void saveExpiresTime(long expiresTime)
		{
			mSharedPref.edit().putLong(EXPIRES_TIME, System.currentTimeMillis()+(expiresTime*1000)).commit();
		}
		
		public long getExpiresTime()
		{
			return mSharedPref.getLong(EXPIRES_TIME, 0);
		}
		
		public void saveUid(long uid)
		{
			mSharedPref.edit().putLong(UID, uid).commit();
		}
		
		public long getUid()
		{
			return mSharedPref.getLong(UID, 0);
		}
		
		public void saveNickName(String nickName)
		{
			mSharedPref.edit().putString(NICK_NAME, nickName).commit();
		}
		
		public String getNickName()
		{
			return mSharedPref.getString(NICK_NAME, "");
		}
		
		public boolean hasAccessToken()
		{
			return getAccessToken().length() != 0;
		}
		
		public boolean isOauth()
		{
			return hasAccessToken() && !isAccessTokenExpire();
		}
		
		public boolean isAccessTokenExpire()
		{
			return System.currentTimeMillis() >= getExpiresTime();
		}		
		
		public void saveOauth2Info(String accessToken, long expiresTime, long uid)
		{
			saveAccessToken(accessToken);
			saveExpiresTime(expiresTime);
			saveUid(uid);
		}		
	}
}
