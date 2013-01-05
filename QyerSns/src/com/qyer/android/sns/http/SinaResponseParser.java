package com.qyer.android.sns.http;

import org.json.JSONObject;


public class SinaResponseParser implements HttpParams{

	public static SinaRespOauth parseSinaOauth2Info(String resp)
	{
		SinaRespOauth soi = null;
		
		try{
			
			soi = new SinaRespOauth();
			String[] respArray = resp.split("[#,=,&]");
			soi.setAccessToken(respArray[2]);
			soi.setExpiresIn(Long.parseLong(respArray[6]));
			soi.setUid(Long.parseLong(respArray[8]));
		
		}catch(Exception e){
			e.printStackTrace();
			soi = null;
		}
		
		return soi;
	}
	
	public static SinaRespUpdate parseSinaUpdateResponse(String resp)
	{
		SinaRespUpdate sur = new SinaRespUpdate();
		try{
			
			JSONObject jsonObj = new JSONObject(resp);
			if(!jsonObj.isNull(HttpParams.SINA_RESP_PARAM_CREATED_AT)){
				
				sur.setCreateAt(jsonObj.getString(HttpParams.SINA_RESP_PARAM_CREATED_AT));	
			
			}else if (!jsonObj.isNull(HttpParams.SINA_RESP_PARAM_ERROR_CODE)){
				
				sur.setErrorCode(jsonObj.getInt(HttpParams.SINA_RESP_PARAM_ERROR_CODE));
				sur.setErrorInfo(jsonObj.getString(HttpParams.SINA_RESP_PARAM_ERROR_INFO));
		
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sur;
	}
	
	public static SinaRespUserInfo parseSinaUserInfoResponse(String resp)
	{
		SinaRespUserInfo srui = new SinaRespUserInfo();
		
		try{
			
			JSONObject jsonObj = new JSONObject(resp);
			srui.setNickName(jsonObj.getString(HttpParams.SINA_RESP_PARAM_USER_NICKNAME));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return srui;
	}
}
