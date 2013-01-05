package com.qyer.android.oneday.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class NetWorkUtil {

	
	public static boolean saveUrlContentAsFile(String url, File f)
	{
		boolean flag = true;
		FileOutputStream fos = null;
		InputStream is = null;
		try{
			
			URL urlObj = new URL(url);
			fos = new FileOutputStream(f);
			is = (InputStream) urlObj.getContent();
			byte[] buffer = new byte[1024];
			int len = -1;
			while((len = is.read(buffer)) != -1){
				fos.write(buffer, 0, len);
			}
			
		}catch(Exception e){
			flag = false;
		}finally{
			IOUtil.closeInStream(is);
			IOUtil.closeOutStream(fos);
		}
		return flag;
	}
	
	public static String loadTextFromUrl(String url) 
	{
		if (url == null)
			return null;

		BufferedReader reader = null;
		StringBuilder sb  = new StringBuilder(100);
		try {
			
			URL urlObj = new URL(url);
			reader = new BufferedReader(new InputStreamReader((InputStream) urlObj.getContent()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.closeReader(reader);
		}

		return sb.toString();
	}
	
	public static Bitmap loadBitmapFromUrl(String url)
	{
		if(url == null)
			return null;
		
		Bitmap bmp = null;
		InputStream input = null;
		try {
			
			URL urlObj = new URL(url);
		    input = (InputStream) urlObj.getContent();
			bmp = BitmapFactory.decodeStream(input);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.closeInStream(input);
		}
		
		return bmp;
	}
	
	public static Drawable loadDrawableFromUrl(String url)
	{
		if(url == null)
			return null;
		
		Drawable drawable = null;
		InputStream input = null;
		
		try {
			
			URL urlObj = new URL(url);
			input = (InputStream) urlObj.getContent();
			drawable = Drawable.createFromStream(input, "");
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.closeInStream(input);
		}
		
		return drawable;
	}
	
	public static InputStream getInputStreamFromUrl(String url)
	{
		InputStream input = null;
		try {
			
			URL urlObj = new URL(url);
			input = (InputStream) urlObj.getContent();
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return input;
	}
	
	public static String httpClientPost(String url, List<NameValuePair> params) throws ClientProtocolException, IOException
	{
		if (TextUtils.isEmpty(url))
			return "";

		HttpClient httpClient = getDefaultHttpClient();
		
		HttpPost post = new HttpPost(url);
		post.setHeader("Charset", HTTP.UTF_8);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		if(params != null && !params.isEmpty())
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		
		HttpResponse response = httpClient.execute(post);
		return EntityUtils.toString(response.getEntity());
	}
	
	public static String httpClientGet(String url, List<NameValuePair> params) throws ClientProtocolException, IOException
	{
		if (TextUtils.isEmpty(url))
			return "";
		
		HttpClient httpClient = getDefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response = httpClient.execute(get);
		return EntityUtils.toString(response.getEntity());
	}
	
	private static HttpClient getDefaultHttpClient()
	{
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000*30);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000*30);
		return httpClient;
	}
}
