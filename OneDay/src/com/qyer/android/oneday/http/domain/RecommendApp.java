package com.qyer.android.oneday.http.domain;

import java.io.Serializable;

public class RecommendApp implements Serializable{

	private static final long serialVersionUID = 1L;

	private String title = "";
	private String link = "";
	private String thumb = "";
	private String desc = "";
	
	public RecommendApp()
	{
	}
	
	public RecommendApp(String title, String link, String thumb, String dec)
	{
		setTitle(title);
		setLink(link);
		setThumb(thumb);
		setDesc(dec);
	}	
	
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title) 
	{	
		if(title == null)
			title = "";
		
		this.title = title;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link) 
	{
		if(link == null)
			link = "";
		
		this.link = link;
	}

	public String getThumb() 
	{
		return thumb;
	}

	public void setThumb(String thumb)
	{
		if(thumb == null)
			thumb = "";
		
		this.thumb = thumb;
	}

	public String getDesc() 
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		if(desc == null)
			desc = "";
		
		this.desc = desc;
	}
}
