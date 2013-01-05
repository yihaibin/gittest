package com.qyer.android.oneday.http.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.qyer.android.oneday.http.HttpParams;

public class QyerResponse extends JsonableResponse implements HttpParams{
	
	public static final int STATUS_SUCCESS = 1;
	
	//login failed
	private int status;
	private String info = "";
	
	public QyerResponse()
	{
	}
	
	public QyerResponse(int status, String info)
	{
		setStatus(status);
		setInfo(info);
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}

	public void setInfo(String info)
	{
		if(info == null)
			info = "";
		
		this.info = info;
	}
	
	public int getStatus()
	{
		return status;
	}

	public String getInfo() 
	{
		return info;
	}
	
	@Override
	void onSetDataFromJsonObj(JSONObject jsonObj) throws JSONException
	{
		setStatus(jsonObj.getInt(HttpParams.RESP_STATUS));
		setInfo(jsonObj.getString(HttpParams.RESP_INFO));
	}

	public boolean isSuccess() 
	{
		return this.status == STATUS_SUCCESS;
	}
}
