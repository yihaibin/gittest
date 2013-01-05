package com.qyer.android.oneday.http.response;

public abstract class BaseResponse{

	private boolean isTimeout;
	private boolean isConnException;
	
	protected BaseResponse()
	{
	}
	
	public void setTimeout(boolean timeout)
	{
		this.isTimeout = timeout;
		if(timeout)
			setConnException(true);
	}
	
	public boolean isTimeout()
	{
		return this.isTimeout;
	}
	
	public void setConnException(boolean exception)
	{
		this.isConnException = exception;
	}
	
	public boolean isConnException()
	{
		return this.isConnException;
	}
}
