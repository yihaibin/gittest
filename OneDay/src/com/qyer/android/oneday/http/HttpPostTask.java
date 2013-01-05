package com.qyer.android.oneday.http;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HTTP;

import com.qyer.android.oneday.util.CollectionUtil;

public class HttpPostTask extends HttpTask{

	protected HttpPostTask(HttpCallback callback)
	{
		super(new HttpPost(), callback);
	}

	@Override
	protected void setParamToRequest(HttpUriRequest request, String url, List<NameValuePair> params)throws UnsupportedEncodingException
	{
		HttpPost httpPost = (HttpPost) request;
		httpPost.setURI(URI.create(url));
		httpPost.setHeader("Charset", HTTP.UTF_8);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		if(!CollectionUtil.isEmpty(params))
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
	}
}