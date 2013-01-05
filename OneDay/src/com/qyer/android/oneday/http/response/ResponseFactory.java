package com.qyer.android.oneday.http.response;

import com.qyer.android.oneday.http.HttpParams;

public class ResponseFactory {

	
	public static BaseResponse getResponseByUrl(String url)
	{
		BaseResponse resp = null;
		if(HttpParams.URL_LOGIN.equals(url))
			resp = new LoginResponse();
		else if(HttpParams.URL_REGISTER.equals(url))
			resp = new RegisterResponse();
		else if(HttpParams.URL_FEEDBACK.equals(url))
			resp = new QyerResponse();
		else if(HttpParams.URL_GETADVERT.equals(url))
			resp = new AdvertResponse();
		else if(HttpParams.URL_QYER_MORE_APP.equals(url))
			resp = new QyerAppResponse();
		else if(HttpParams.URL_RECOMMEND_APP.equals(url))
			resp = new RecommendAppResponse();
		else if(HttpParams.URL_FORCE_UPDATE.equals(url))
			resp = new ForceUpdateResponse();
		else if(HttpParams.URL_GET_PUSH.equals(url))
			resp = new PushResponse();
		else
			resp = new QyerResponse();
		
		return resp;
	}
}
