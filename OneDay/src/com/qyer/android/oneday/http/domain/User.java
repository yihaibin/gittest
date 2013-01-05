package com.qyer.android.oneday.http.domain;

public class User {

	private int uid;
	private String userName = "";
	private String email = "";
	private String passWord = "";
	
	public User()
	{
	}
	
	public User(String userName)
	{
		setUserName(userName);
	}
	
	public User(String userName, String email, String passWord)
	{
		setUserName(userName);
		setEmail(email);
		setPassWord(passWord);
	}
	
	public User(int uid, String userName, String email, String passWord)
	{
		setUid(uid);
		setUserName(userName);
		setEmail(email);
		setPassWord(passWord);
	}	
	
	public User(String userName, String passWord)
	{
		setUserName(userName);
		setPassWord(passWord);
	}
	
	public void setUid(int uid)
	{
		this.uid = uid;
	}
	
	public int getUid()
	{
		return this.uid;
	}
	
	public void setUserName(String userName)
	{
		if(userName == null)
			userName = "";
		
		this.userName = userName;
	}

	public String getUserName()
	{
		return userName;
	}
	
	public void setEmail(String email)
	{
		if(email == null)
			email = "";
		
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}
	
	public void setPassWord(String passWord)
	{
		if(passWord == null)
			email = "";
		
		this.passWord = passWord;
	}

	public String getPassWord() 
	{
		return passWord;
	}

	public String toString()
	{
		return "User: email = "+email+", userName = "+userName+", pwd = "+passWord.length();
	}
}
