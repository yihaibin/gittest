package com.qyer.android.sns.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;

import com.qyer.android.sns.util.SnsLogManager;
import com.qyer.android.sns.util.SnsNetWorkUtil;

public class WeiboRequest implements HttpParams{

	public static final String TAG = "WeiboRequest";
	
	public static SinaRespUpdate sendSinaWeibo(String accessToken, String text)
	{
		SinaRespUpdate sur = new SinaRespUpdate();
		
		try {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(SINA_REQ_PARAM_ACCESS_TOKEN,accessToken));
			params.add(new BasicNameValuePair(SINA_REQ_PARAM_STATUS,text));
			
			String resp = SnsNetWorkUtil.httpClientPost(SINA_URL_UPDATE_WEIBO, params);
			sur.setDataFromJson(resp);
			SnsLogManager.printD(TAG, "sendSinaWeibo resp = "+resp);
			
		} catch(ConnectTimeoutException e){
			e.printStackTrace();
			sur.setTimeout(true);
		} catch(Exception e){
			e.printStackTrace();
			sur.setException(true);
		}
		
		return sur;
	}
	
	public static SinaRespUpdate sendSinaWeibo(String accessToken, String text, byte[] pic)
	{
		SinaRespUpdate sur = new SinaRespUpdate();
		
		try {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(SINA_REQ_PARAM_ACCESS_TOKEN,accessToken));
			params.add(new BasicNameValuePair(SINA_REQ_PARAM_STATUS,text));
			params.add(new BasicNameValuePair(SINA_REQ_PARAM_PIC,String.valueOf(System.currentTimeMillis())));
			
			String resp = SnsNetWorkUtil.httpClientUpload(SINA_URL_UPLOAD_WEIBO, params,pic);
			sur.setDataFromJson(resp);
			SnsLogManager.printD(TAG, "sendSinaWeibo pic resp = "+resp);
			
		} catch(ConnectTimeoutException e){
			e.printStackTrace();
			sur.setTimeout(true);
		} catch(Exception e){
			e.printStackTrace();
			sur.setException(true);
		}
		
		return sur;		
	}

	public static SinaRespUserInfo getUserInfo(String accessToken, String uid)
	{
		SinaRespUserInfo suir = new SinaRespUserInfo();
		
		try{
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(SINA_REQ_PARAM_ACCESS_TOKEN,accessToken));
			params.add(new BasicNameValuePair(SINA_REQ_PARAM_UID,uid));
			
			String resp = SnsNetWorkUtil.httpClientGet(SINA_URL_USER_INFO, params);	
			suir.setDataFromJson(resp);
			SnsLogManager.printD(TAG, "getUserInfo resp = "+resp);
			
		}catch(ConnectTimeoutException e){
			e.printStackTrace();
			suir.setTimeout(true);
		}catch(Exception e){
			e.printStackTrace();
			suir.setException(true);
		}
		
		return suir;
	}
}
