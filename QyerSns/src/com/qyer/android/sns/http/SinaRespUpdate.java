package com.qyer.android.sns.http;

import org.json.JSONObject;

public class SinaRespUpdate extends SinaResp{

	private String createAt = "";
	
	public SinaRespUpdate() 
	{
	}
	
	public String getCreateAt() 
	{
		return createAt;
	}

	public void setCreateAt(String createAt) 
	{
		if(createAt == null)
			createAt = "";
		
		this.createAt = createAt;
	}
	
	public boolean isSuccess()
	{
		return createAt.length() != 0;
	}
	
	@Override
	public void onSetDataFromJsonObj(JSONObject jsonObj) throws Exception 
	{
		if(!jsonObj.isNull(HttpParams.SINA_RESP_PARAM_CREATED_AT)){
			
			setCreateAt(jsonObj.getString(HttpParams.SINA_RESP_PARAM_CREATED_AT));	
		
		}else if (!jsonObj.isNull(HttpParams.SINA_RESP_PARAM_ERROR_CODE)){
			
			setErrorCode(jsonObj.getInt(HttpParams.SINA_RESP_PARAM_ERROR_CODE));
			setErrorInfo(jsonObj.getString(HttpParams.SINA_RESP_PARAM_ERROR_INFO));
		}
		setParseJsonSuccess(true);
	}
}
