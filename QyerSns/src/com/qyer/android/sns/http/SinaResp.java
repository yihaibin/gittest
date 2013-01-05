package com.qyer.android.sns.http;

public class SinaResp extends SnsResponse{

	private int    errorCode;
	private String errorInfo;
	
	public SinaResp()
	{
	}
	
	public SinaResp(int errorCode, String errorInfo)
	{
		setErrorCode(errorCode);
		setErrorInfo(errorInfo);
	}

	public int getErrorCode() 
	{
		return errorCode;
	}

	public void setErrorCode(int errorCode) 
	{
		this.errorCode = errorCode;
	}

	public String getErrorInfo() 
	{
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) 
	{
		if(errorInfo == null)
			errorInfo = "";
		
		this.errorInfo = errorInfo;
	}
	
	public boolean isError()
	{
		return errorCode != 0;
	}
}
