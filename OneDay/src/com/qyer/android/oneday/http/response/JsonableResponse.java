package com.qyer.android.oneday.http.response;

import org.json.JSONObject;

public abstract class JsonableResponse extends BaseResponse{
	
	public void setDataFromJson(String jsonText)
	{
		try {
			
			onSetDataFromJsonObj(new JSONObject(jsonText));
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	abstract void onSetDataFromJsonObj(JSONObject jsonObj)throws Exception;

}
