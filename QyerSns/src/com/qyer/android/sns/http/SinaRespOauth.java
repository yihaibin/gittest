package com.qyer.android.sns.http;

public class SinaRespOauth extends SnsResponse{

	private String accessToken = "";
	private long expiresIn;
	private long uid;
	
	public SinaRespOauth()
	{
	}
	
	public SinaRespOauth(String accessToken, long expiresIn, long uid)
	{
		setAccessToken(accessToken);
		setExpiresIn(expiresIn);
		setUid(uid);
	}

	public String getAccessToken() 
	{
		return accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		if(accessToken == null)
			accessToken = "";
		
		this.accessToken = accessToken;
	}

	public long getExpiresIn()
	{
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn)
	{
		this.expiresIn = expiresIn;
	}

	public long getUid() 
	{
		return uid;
	}

	public void setUid(long uid) 
	{
		this.uid = uid;
	}
	
	@Override
	public void setDataFromJson(String jsonText)
	{
		try{
			
			String[] respArray = jsonText.split("[#,=,&]");
			setAccessToken(respArray[2]);
			setExpiresIn(Long.parseLong(respArray[6]));
			setUid(Long.parseLong(respArray[8]));
			setParseJsonSuccess(true);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
