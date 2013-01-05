package com.qyer.android.sns.http;

public class BaseResponse {

	private boolean isException;
	private boolean isTimeout;

	protected BaseResponse()
	{
	}

	public boolean isException()
	{
		return isException;
	}

	public void setException(boolean isException) 
	{
		this.isException = isException;
	}

	public boolean isTimeout()
	{
		return isTimeout;
	}

	public void setTimeout(boolean isTimeout)
	{
		this.isTimeout = isTimeout;
		if(isTimeout)
			this.isException = true;
	}
}
