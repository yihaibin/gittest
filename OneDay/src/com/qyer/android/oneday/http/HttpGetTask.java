package com.qyer.android.oneday.http;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

public class HttpGetTask extends HttpTask{

	protected HttpGetTask(HttpCallback callback)
	{
		super(new HttpGet(), callback);
	}

	@Override
	protected void setParamToRequest(HttpUriRequest request, String url, List<NameValuePair> params) throws UnsupportedEncodingException 
	{
		HttpGet httpGet = (HttpGet) request;
		httpGet.setURI(URI.create(url));
	}
}