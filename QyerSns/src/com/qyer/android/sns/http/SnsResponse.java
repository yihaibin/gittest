package com.qyer.android.sns.http;

import org.json.JSONObject;

public class SnsResponse {

	private boolean isException;
	private boolean isTimeout;
	private boolean parseJsonSuccess;

	protected SnsResponse()
	{
	}

	public boolean isException()
	{
		return isException;
	}

	public void setException(boolean isException) 
	{
		this.isException = isException;
	}

	public boolean isTimeout()
	{
		return isTimeout;
	}

	public void setTimeout(boolean isTimeout)
	{
		this.isTimeout = isTimeout;
		if(isTimeout)
			this.isException = true;
	}
	
	public boolean isParseJsonSuccess()
	{
		return parseJsonSuccess;
	}
	
	protected void setParseJsonSuccess(boolean success)
	{
		parseJsonSuccess = success;
	}
	
	public void setDataFromJson(String jsonText)
	{
		try {
			
			onSetDataFromJsonObj(new JSONObject(jsonText));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onSetDataFromJsonObj(JSONObject jsonObj)throws Exception
	{
	}
}
