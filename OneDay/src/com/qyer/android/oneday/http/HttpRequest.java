package com.qyer.android.oneday.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.qyer.android.oneday.http.domain.User;
import com.qyer.android.oneday.util.MD5Util;

public class HttpRequest {
	
	public static HttpTask login(User user, HttpCallback callback)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_ID, HttpParams.VAL_CLIENT_ID));
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_SECRET, HttpParams.VAL_CLIENT_SECRET));
		params.add(new BasicNameValuePair(HttpParams.REQ_GRANT_TYPE, HttpParams.VAL_GRANT_TYPE));
		params.add(new BasicNameValuePair(HttpParams.REQ_USERNAME, user.getUserName()));
		params.add(new BasicNameValuePair(HttpParams.REQ_PASSWORD, user.getPassWord()));
		params.add(new BasicNameValuePair(HttpParams.REQ_ACCOUNT_S, MD5Util.getAccountS(user.getUserName(), user.getPassWord())));
		
		return executePostTask(HttpParams.URL_LOGIN, params, callback);
	}
	
	public static HttpTask register(User user, HttpCallback callback)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_ID,HttpParams.VAL_CLIENT_ID));
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_SECRET,HttpParams.VAL_CLIENT_SECRET));
		params.add(new BasicNameValuePair(HttpParams.REQ_USERNAME, user.getUserName()));
		params.add(new BasicNameValuePair(HttpParams.REQ_PASSWORD, user.getPassWord()));
		params.add(new BasicNameValuePair(HttpParams.REQ_EMAIL, user.getEmail()));
		params.add(new BasicNameValuePair(HttpParams.REQ_ACCOUNT_S,MD5Util.getAccountS(user.getUserName(), user.getPassWord())));
		
		return executePostTask(HttpParams.URL_REGISTER, params, callback);
	}
	
	public static HttpTask feedBack(String text, String imei, HttpCallback callback) 
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_ID, HttpParams.VAL_CLIENT_ID));
		params.add(new BasicNameValuePair(HttpParams.REQ_DEVICE_ID, imei));
		params.add(new BasicNameValuePair(HttpParams.REQ_CONTENT, text));
		params.add(new BasicNameValuePair(HttpParams.REQ_MODIFIED, System.currentTimeMillis() + ""));
		
		return executePostTask(HttpParams.URL_FEEDBACK, params, callback);
	}
	
	public static HttpTask getAppAdvertInfo(HttpCallback callback) 
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_ID, HttpParams.VAL_CLIENT_ID));
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_SECRET, HttpParams.VAL_CLIENT_SECRET));
		params.add(new BasicNameValuePair(HttpParams.REQ_APP_VERSION, HttpParams.VAL_APP_VERSION_ALL));
			
		return executePostTask(HttpParams.URL_GETADVERT, params, callback);
	}	
	
	public static HttpTask getQyerMoreApp(HttpCallback callback) 
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_ID, HttpParams.VAL_CLIENT_ID));
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_SECRET, HttpParams.VAL_CLIENT_SECRET));
			
		return executePostTask(HttpParams.URL_QYER_MORE_APP, params, callback);	
	}	
	
	public static HttpTask getRecommendApp(HttpCallback callback)
	{
			
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_ID, HttpParams.VAL_CLIENT_ID));
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_SECRET, HttpParams.VAL_CLIENT_SECRET));
		
		return executePostTask(HttpParams.URL_RECOMMEND_APP, params, callback);	
	}	
	
	public static HttpTask getForceUpdateInfo(String versionName, HttpCallback callback)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_ID, HttpParams.VAL_CLIENT_ID));
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_SECRET, HttpParams.VAL_CLIENT_SECRET));
		params.add(new BasicNameValuePair(HttpParams.REQ_VERSION, versionName));
		
		return executePostTask(HttpParams.URL_FORCE_UPDATE, params, callback);	
	}	
	
	public static HttpTask getPushMessage(String imei, String versionName, HttpCallback callback)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_ID, HttpParams.VAL_CLIENT_ID));
		params.add(new BasicNameValuePair(HttpParams.REQ_CLIENT_SECRET, HttpParams.VAL_CLIENT_SECRET));
		params.add(new BasicNameValuePair(HttpParams.REQ_DEVICE_TYPE, HttpParams.VAL_DEVICE_TYPE_ANDROID));
		params.add(new BasicNameValuePair(HttpParams.REQ_DEVICE_NUMBER, imei));
		params.add(new BasicNameValuePair(HttpParams.REQ_APP_VERSION, versionName));

		return executePostTask(HttpParams.URL_GET_PUSH, params, callback);
	}	
	
	private static HttpTask executePostTask(String url, List<NameValuePair> params, HttpCallback callback)
	{
		HttpTask hrt = new HttpPostTask(callback);
		hrt.execute(url, params);
		return hrt;
	}
	
	@SuppressWarnings("unused")
	private static HttpTask executeGetTask(String url, List<NameValuePair> params, HttpCallback callback)
	{
		HttpTask hrt = new HttpGetTask(callback);
		hrt.execute(url, params);
		return hrt;
	}
}
