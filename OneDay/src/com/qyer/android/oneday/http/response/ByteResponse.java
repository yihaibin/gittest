package com.qyer.android.oneday.http.response;

public class ByteResponse extends BaseResponse{

	private byte[] bytes;
	
	public ByteResponse() 
	{
	}
	
	public ByteResponse(byte[] bytes)
	{
		setBytes(bytes);
	}
	
	public void setBytes(byte[] bytes)
	{
		this.bytes = bytes;
	}
	
	public byte[] getBytes()
	{
		return this.bytes;
	}
}
