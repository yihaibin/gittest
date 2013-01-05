package com.qyer.android.oneday.http.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qyer.android.oneday.http.HttpParams;

public class PushResponse extends QyerResponse{

	private int space;
	private List<PushEntity> pushEntities;
	
	public PushResponse()
	{
	}
	
	public PushResponse(int space, List<PushEntity> pushEntities)
	{
		setSpace(space);
		setPushEntities(pushEntities);
	}
	
	public int getSpace() 
	{
		return space;
	}

	public void setSpace(int space)
	{
		this.space = space;
	}

	public List<PushEntity> getPushEntities() 
	{
		return pushEntities;
	}

	public void setPushEntities(List<PushEntity> pushEntities) 
	{
		this.pushEntities = pushEntities;
	}

	@Override
	void onSetDataFromJsonObj(JSONObject jsonObj) throws JSONException 
	{
		super.onSetDataFromJsonObj(jsonObj);

		if(isSuccess()){
			jsonObj = jsonObj.getJSONObject(HttpParams.RESP_DATA);
			setSpace(jsonObj.getInt(HttpParams.RESP_SPACE));
			
			JSONArray pushDatas = jsonObj.getJSONArray(HttpParams.RESP_LIST);
			List<PushEntity> pushEntities = new ArrayList<PushEntity>(5);
			JSONObject pushDataObj = null;
			PushEntity pushEntity = null;
			for(int i=0; i<pushDatas.length(); i++){
				pushDataObj = pushDatas.getJSONObject(i);
				pushEntity = new PushEntity(pushDataObj.getInt(RESP_ID), 
								  	 		pushDataObj.getString(RESP_APP_NAME),
								  	 		pushDataObj.getString(RESP_CONTENT),
								  	 		pushDataObj.getInt(RESP_CHART),
								  	 		pushDataObj.getString(RESP_DATETIME));
				pushEntities.add(pushEntity);
			}
			setPushEntities(pushEntities);
		}		
	}
	
	public static final class PushEntity{
		private int id;
		private String appName = "";
		private String content = "";
		private int chart;
		private String dateTime = "";
		
		public PushEntity()
		{
		}
		
		public PushEntity(int id, String appName, String content, int chart, String dateTime)
		{
			setId(id);
			setAppName(appName);
			setContent(content);
			setChart(chart);
			setDateTime(dateTime);
		}

		public int getId() 
		{
			return id;
		}

		public void setId(int id) 
		{
			this.id = id;
		}

		public String getAppName()
		{
			return appName;
		}

		public void setAppName(String appName)
		{
			if(appName == null)
				appName = "";
			
			this.appName = appName;
		}

		public String getContent()
		{
			return content;
		}

		public void setContent(String content) 
		{
			if(content == null)
				content = "";
			
			this.content = content;
		}

		public int getChart() 
		{
			return chart;
		}

		public void setChart(int chart) 
		{
			this.chart = chart;
		}

		public String getDateTime()
		{
			return dateTime;
		}

		public void setDateTime(String dateTime) 
		{
			if(dateTime == null)
				dateTime = "";
			
			this.dateTime = dateTime;
		}
	}
}
