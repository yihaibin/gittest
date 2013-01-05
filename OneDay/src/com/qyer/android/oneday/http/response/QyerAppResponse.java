package com.qyer.android.oneday.http.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qyer.android.oneday.http.HttpParams;
import com.qyer.android.oneday.http.domain.QyerApp;

public class QyerAppResponse extends QyerResponse{

	private List<QyerApp> apps = null;
	
	public QyerAppResponse()
	{
	}
	
	public QyerAppResponse(List<QyerApp> apps)
	{
		this.apps = apps;
	}

	public List<QyerApp> getApps() 
	{
		return apps;
	}

	public void setApps(List<QyerApp> apps) 
	{
		this.apps = apps;
	}
	
	@Override
	void onSetDataFromJsonObj(JSONObject jsonObj) throws JSONException
	{
		super.onSetDataFromJsonObj(jsonObj);
		
		if(isSuccess()){
			JSONArray appData = jsonObj.getJSONArray(HttpParams.RESP_DATA);
			List<QyerApp> apps = new ArrayList<QyerApp>(5);
			JSONObject appObj = null;
			QyerApp app = null;
			for(int i=0; i<appData.length(); i++){
				appObj = appData.getJSONObject(i);
				app = new QyerApp(appObj.getString(RESP_SUBNAME),
						  		  appObj.getString(RESP_PACKAGE),
						  		  appObj.getString(RESP_THUMB), 
								  appObj.getString(RESP_APPSTORE_URL));
				
				apps.add(app);
			}
			setApps(apps);
		}		
	}
}
