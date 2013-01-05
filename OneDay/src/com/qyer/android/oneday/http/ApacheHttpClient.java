package com.qyer.android.oneday.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class ApacheHttpClient {
	
	private static final int DEFAULT_MAX_CONNECTIONS = 5;  
	private static final int DEFAULT_MAX_ROUTES = 5;  
	private static final int DEFAULT_SOCKET_TIMEOUT = 20 * 1000;  
	@SuppressWarnings("unused")
	private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
	
	private static ApacheHttpClient mInstance;
	
	private HttpClient mHttpClient;
	
	private ApacheHttpClient()
	{

		mHttpClient = getThreadSafeHttpClient();
	}
	
	private HttpClient getThreadSafeHttpClient()
	{
		HttpParams httpParams = new BasicHttpParams();
		
		//set conn mgr attribute
		ConnManagerParams.setTimeout(httpParams, 0);  
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(DEFAULT_MAX_ROUTES));  
		ConnManagerParams.setMaxTotalConnections(httpParams, DEFAULT_MAX_CONNECTIONS); 
		
		//set protocol
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);  
		HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);  
		//HttpProtocolParams.setUserAgent(httpParams, "Android client");
		
		//set http conn attribute
		HttpConnectionParams.setSoTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT);  
		HttpConnectionParams.setConnectionTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT); 
		//HttpConnectionParams.setTcpNoDelay(httpParams, true);  
		//HttpConnectionParams.setSocketBufferSize(httpParams, DEFAULT_SOCKET_BUFFER_SIZE); 
		
		//set ShcemeRegister
		SchemeRegistry schemeRegistry = new SchemeRegistry();  
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));  
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		
		ClientConnectionManager manager = new ThreadSafeClientConnManager(httpParams, schemeRegistry); 
		return new DefaultHttpClient(manager, httpParams);
	}
	
	public static ApacheHttpClient getInstance()
	{
		if(mInstance == null)
			mInstance = new ApacheHttpClient();
		
		return mInstance;
	}
	
	public HttpResponse execute(HttpUriRequest request) throws ClientProtocolException, IOException
	{
		return mHttpClient.execute(request);
	}
	
	public String executeText(HttpUriRequest request) throws ClientProtocolException, IOException
	{
		HttpEntity respEntity = mHttpClient.execute(request).getEntity();
		String text = EntityUtils.toString(respEntity);
		respEntity.consumeContent();
		return text;
	}
	
	public byte[] executeByteArray(HttpUriRequest request) throws ClientProtocolException, IOException
	{
		HttpEntity respEntity = mHttpClient.execute(request).getEntity();
		byte[] byteArray = EntityUtils.toByteArray(respEntity);
		respEntity.consumeContent();
		return byteArray;
	}
}
