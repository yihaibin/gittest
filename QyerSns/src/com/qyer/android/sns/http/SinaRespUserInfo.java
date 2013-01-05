package com.qyer.android.sns.http;

import org.json.JSONObject;

public class SinaRespUserInfo extends SinaResp{

	private String nickName;
	
	public SinaRespUserInfo()
	{
	}
	
	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		if(nickName == null)
			nickName = "";
		
		this.nickName = nickName;
	}
	
	@Override
	public void onSetDataFromJsonObj(JSONObject jsonObj) throws Exception 
	{
		setNickName(jsonObj.getString(HttpParams.SINA_RESP_PARAM_USER_NICKNAME));
		setParseJsonSuccess(true);
	}
}
