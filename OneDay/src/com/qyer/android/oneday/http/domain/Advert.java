package com.qyer.android.oneday.http.domain;

import java.io.Serializable;

public class Advert implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int ad_id;
	private String appName;
	private String title;
	private String tag;
	private int openMode;
	private String linkUrl;
	private String imageUrl;
	private long created;
	private int order;	
	private long startTime;
	private long endTime;
	
	public Advert()
	{
	}
	
	public Advert(int ad_id, String appName, String title, String tag, long created, int order, long startTime, long endTime)
	{
		this.ad_id = ad_id;
		setAppName(appName);
		setTitle(title);
		setTag(tag);
		this.created = created;
		this.order = order;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Advert(int id, int ad_id, String appName, String title, String tag, long created, int order, long startTime, long endTime)
	{
		this(ad_id,appName,title,tag,created,order,startTime,endTime);
		this.id = id;
	}	

	public int getADId() 
	{
		return ad_id;
	}

	public void setADId(int adId) 
	{
		this.ad_id = adId;
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

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag) 
	{
		if(tag == null)
			tag = "";
		
		this.tag = tag;
	}

	public int getOpenMode() 
	{
		return openMode;
	}

	public void setOpenMode(int openMode) 
	{
		this.openMode = openMode;
	}

	public String getLinkUrl() 
	{
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) 
	{
		if(linkUrl == null)
			linkUrl = "";
		
		this.linkUrl = linkUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		if(imageUrl == null)
			imageUrl = "";
		
		this.imageUrl = imageUrl;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created)
	{
		this.created = created;
	}

	public int getOrder()
	{
		return order;
	}

	public void setOrder(int order) 
	{
		this.order = order;
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

	public long getStartTime() 
	{
		return startTime;
	}

	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
	}

	public long getEndTime() 
	{
		return endTime;
	}

	public void setEndTime(long endTime)
	{
		this.endTime = endTime;
	}	
	
	public String getImageUrlHashCodeStr()
	{
		return imageUrl.hashCode()+"";
	}
}
