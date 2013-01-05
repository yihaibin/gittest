package com.qyer.android.oneday.http.domain;

import java.io.Serializable;

public class QyerApp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String title = "";
	private String thumbUrl = "";
	private String appstoreUrl = "";
	private String appVerstion = "";
	private int osType;
	private String relation = "";
	private String packageName = "";
	private String subName = "";
	
	
	public QyerApp()
	{
	}
	
	public QyerApp(String subName, String packageName, String thumbUrl, String appStoreUrl)
	{
		setSubName(subName);
		setPackageName(packageName);
		setThumbUrl(thumbUrl);
		setAppstoreUrl(appStoreUrl);
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
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

	public String getThumbUrl() 
	{
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) 
	{
		if(thumbUrl == null)
			thumbUrl = "";
		
		this.thumbUrl = thumbUrl;
	}

	public String getAppstoreUrl()
	{
		return appstoreUrl;
	}

	public void setAppstoreUrl(String appstoreUrl) 
	{
		if(appstoreUrl == null)
			appstoreUrl = "";
		
		this.appstoreUrl = appstoreUrl;
	}

	public String getAppVerstion() 
	{
		return appVerstion;
	}

	public void setAppVerstion(String appVerstion)
	{
		if(appVerstion == null)
			appVerstion  = "";
		
		this.appVerstion = appVerstion;
	}

	public int getOsType()
	{
		return osType;
	}

	public void setOsType(int osType)
	{
		this.osType = osType;
	}

	public String getRelation()
	{
		return relation;
	}

	public void setRelation(String relation)
	{
		if(relation == null)
			relation = "";
		
		this.relation = relation;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		if(packageName == null)
			packageName = "";
		
		this.packageName = packageName;
	}

	public String getSubName() 
	{
		return subName;
	}

	public void setSubName(String subName)
	{
		if(subName == null)
			subName = "";
		
		this.subName = subName;
	}
	
	
}
