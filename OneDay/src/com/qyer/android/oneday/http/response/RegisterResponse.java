package com.qyer.android.oneday.http.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.qyer.android.oneday.http.HttpParams;
import com.qyer.android.oneday.http.domain.User;

public class RegisterResponse extends QyerResponse{
	
	private User user ;
	
	public RegisterResponse()
	{
	}
	
	public RegisterResponse(int status, String info)
	{
		super(status,info);
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public User getUser()
	{
		return user;
	}
	
	@Override
	void onSetDataFromJsonObj(JSONObject jsonObj) throws JSONException 
	{
		super.onSetDataFromJsonObj(jsonObj);
		
		if(isSuccess()){
			jsonObj = jsonObj.getJSONObject(HttpParams.RESP_DATA);
			User user = new User();
			user.setUid(jsonObj.getInt(HttpParams.RESP_UID));
			user.setUserName(jsonObj.getString(HttpParams.REQ_USERNAME));
			user.setPassWord(jsonObj.getString(HttpParams.REQ_EMAIL));
			setUser(user);
		}		
	}
	
	public String toString()
	{
		return "RegisterResponse: isSuccess = "+isSuccess()
				+", status = "+getStatus()
				+", info = "+getInfo()
				+", "+user;
	}
}
