package com.qyer.android.oneday.http.response;

public class TextResponse extends BaseResponse{

	private String text = "";
	
	public TextResponse()
	{
	}
	
	public TextResponse(String text)
	{
		setText(text);
	}
	
	public void setText(String text) 
	{	
		if(text == null)
			text = "";
		
		this.text = text;
	}

	public String getText() 
	{
		return text;
	}
}
