package com.qyer.android.oneday.http.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qyer.android.oneday.http.HttpParams;
import com.qyer.android.oneday.http.domain.Advert;

public class AdvertResponse extends QyerResponse{

	private List<Advert> data;
	
	public AdvertResponse()
	{
	}
	
	public AdvertResponse(int status, String info)
	{
		super(status, info);
	}
	
	public List<Advert> getData() 
	{
		return data;
	}

	public void setData(List<Advert> adverts) 
	{
		this.data = adverts;
	}
	
	@Override
	void onSetDataFromJsonObj(JSONObject jsonObj) throws JSONException 
	{
		super.onSetDataFromJsonObj(jsonObj);
		
		if(isSuccess()){
			JSONArray imgInfos = jsonObj.getJSONArray(HttpParams.RESP_DATA);
			List<Advert> adverts = new ArrayList<Advert>(5);
			JSONObject imgObj = null;
			Advert advert = null;
			for(int i=0;i<imgInfos.length();i++){
				imgObj = imgInfos.getJSONObject(i);
				advert = new Advert(imgObj.getInt(RESP_ID),
									imgObj.getString(REQ_APP_NAME), 
									imgObj.getString(RESP_AD_TITLE), 
									imgObj.getString(RESP_TAG), 
									imgObj.getLong(RESP_CREATED), 
									imgObj.getInt(RESP_ORDER_NUMBER),
									imgObj.getLong(RESP_START_TIME),
									imgObj.getLong(RESP_END_TIME));
				
				imgObj = imgObj.getJSONObject(RESP_AD_DATA);
				advert.setOpenMode(imgObj.getInt(RESP_AD_OPEN_MODE));
				advert.setLinkUrl(imgObj.getString(RESP_AD_LINK));
				advert.setImageUrl(imgObj.getString(RESP_AD_IMG));
				adverts.add(advert);
			}
			setData(adverts);
		}		
	}
}
