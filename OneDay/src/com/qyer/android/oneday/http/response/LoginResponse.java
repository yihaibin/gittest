package com.qyer.android.oneday.http.response;


import org.json.JSONException;
import org.json.JSONObject;

import com.qyer.android.oneday.http.HttpParams;

import android.text.TextUtils;

public class LoginResponse extends QyerResponse{
	
	//login success
	private int uid;
	private String userName = "";
	private String accessToken = "";
	private long expiresIn;
	private String scope = "";
	
	public LoginResponse()
	{
	}
	
	public LoginResponse(int status, String info)
	{
		super(status, info);
	}
	
	public LoginResponse(int uid, String accessToken, long expiresIn, String scope)
	{
		setUid(uid);
		setAccessToken(accessToken);
		setExpiresIn(expiresIn);
		setScope(scope);
	}
	
	public void setUserName(String userName)
	{
		if(userName == null)
			userName = "";
		
		this.userName = userName;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
	public void setAccessToken(String accessToken)
	{
		if(accessToken == null)
			accessToken = "";
		
		this.accessToken = accessToken;
	}

	public void setExpiresIn(long expiresIn) 
	{
		this.expiresIn = expiresIn;
	}

	public void setScope(String scope) 
	{
		if(scope == null)
			scope = "";
		
		this.scope = scope;
	}

	public void setUid(int uid)
	{
		this.uid = uid;
	}

	public int getUid()
	{
		return uid;
	}

	public String getAccessToken() 
	{
		return accessToken;
	}

	public long getExpiresIn()
	{
		return expiresIn;
	}

	public String getScope() 
	{
		return scope;
	}
	
	public boolean isSuccess()
	{
		return !TextUtils.isEmpty(accessToken);
	}

	@Override
	void onSetDataFromJsonObj(JSONObject jsonObj) throws JSONException 
	{
		if(jsonObj.isNull(HttpParams.RESP_ACCESS_TOKEN)){
			super.onSetDataFromJsonObj(jsonObj);
		}else{
			setUid(jsonObj.getInt(HttpParams.RESP_UID));
			setAccessToken(jsonObj.getString(HttpParams.RESP_ACCESS_TOKEN));
			setExpiresIn(jsonObj.getLong(HttpParams.RESP_EXPIRES_IN));
			setScope(jsonObj.getString(HttpParams.RESP_SCOPE));
		}
	}
	
	public String toString()
	{
		return "LoginResponse: isSuccess = "+isSuccess()
			   +", uid = "+uid
			   +", access_token = "+accessToken 
			   +", expires_in = "+expiresIn
			   +", scope = "+scope
			   +", status = "+getStatus()
			   +", info = "+getInfo();
	}
}
