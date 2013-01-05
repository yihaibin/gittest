package com.qyer.android.oneday.http.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.qyer.android.oneday.http.HttpParams;

public class ForceUpdateResponse extends QyerResponse{

	private boolean over;
	private String warningVersion = "";
	private String warningUrl = "";
	private String warningContent = "";
	
	public ForceUpdateResponse()
	{
	}
	
	public ForceUpdateResponse(boolean over, String warningVersion, String warningUrl, String conent)
	{
		setOver(over);
		setWarningVersion(warningVersion);
		setWarningUrl(warningUrl);
		setWarningContent(conent);
	}
	
	public boolean isOver() 
	{
		return over;
	}

	public void setOver(boolean over) 
	{
		this.over = over;
	}

	public String getWarningVersion() 
	{
		return warningVersion;
	}

	public void setWarningVersion(String warningVersion) 
	{
		if(warningVersion == null)
			warningVersion = "";
		
		this.warningVersion = warningVersion;
	}

	public String getWarningUrl()
	{
		return warningUrl;
	}

	public void setWarningUrl(String warningUrl)
	{
		if(warningUrl == null)
			warningUrl = "";
		
		this.warningUrl = warningUrl;
	}

	public String getWarningContent()
	{
		return warningContent;
	}

	public void setWarningContent(String content)
	{
		if(content == null)
			content = "";
		
		this.warningContent = content;
	}
	
	@Override
	void onSetDataFromJsonObj(JSONObject jsonObj) throws JSONException 
	{
		super.onSetDataFromJsonObj(jsonObj);
		
		if(isSuccess()){
			jsonObj = jsonObj.getJSONObject(HttpParams.RESP_DATA);
			setOver(jsonObj.getBoolean(HttpParams.RESP_OVER));
			setWarningVersion(jsonObj.getString(HttpParams.RESP_WVERSION));
			setWarningUrl(jsonObj.getString(HttpParams.RESP_WURL));
			setWarningContent(jsonObj.getString(HttpParams.RESP_WCONTENT));
		}		
	}
}
