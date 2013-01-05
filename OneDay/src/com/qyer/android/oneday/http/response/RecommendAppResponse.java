package com.qyer.android.oneday.http.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qyer.android.oneday.http.HttpParams;
import com.qyer.android.oneday.http.domain.RecommendApp;

public class RecommendAppResponse extends QyerResponse{

	private List<RecommendApp> apps ;
	
	public RecommendAppResponse()
	{
	}
	
	public RecommendAppResponse(int status, String info, List<RecommendApp> apps)
	{
		super(status, info);
		this.apps = apps;
	}

	public List<RecommendApp> getApps() 
	{
		return apps;
	}

	public void setApps(List<RecommendApp> apps)
	{
		this.apps = apps;
	}
	
	@Override
	void onSetDataFromJsonObj(JSONObject jsonObj) throws JSONException
	{
		super.onSetDataFromJsonObj(jsonObj);
		
		if(isSuccess()){
			JSONArray appData = jsonObj.getJSONArray(HttpParams.RESP_DATA);
			List<RecommendApp> apps = new ArrayList<RecommendApp>(5);
			JSONObject appObj = null;
			RecommendApp app = null;
			for(int i=0; i<appData.length(); i++){
				appObj = appData.getJSONObject(i);
				app = new RecommendApp(appObj.getString(RESP_TITLE), 
								  	   appObj.getString(RESP_LINK),
								  	   appObj.getString(RESP_THUMB),
								  	   appObj.getString(RESP_DESC));
				apps.add(app);
			}
			setApps(apps);
		}		
	}
}
