package com.qyer.android.sns.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.qyer.android.sns.http.NetStateManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class SnsNetWorkUtil {

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
			SnsIOUtil.closeReader(reader);
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
			SnsIOUtil.closeInStream(input);
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
			SnsIOUtil.closeInStream(input);
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

		HttpClient httpClient = getSinaHttpClient();
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, NetStateManager.getAPN());
		HttpPost post = new HttpPost(url);
		post.setHeader("Charset", HTTP.UTF_8);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		if(params != null && !params.isEmpty())
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		
		HttpResponse response = httpClient.execute(post);
		return EntityUtils.toString(response.getEntity());
	}
	
	public static String httpClientUpload(String url, List<NameValuePair> params, byte[] data) throws ClientProtocolException, IOException
	{
		if (TextUtils.isEmpty(url))
			return "";

		HttpClient httpClient = getSinaHttpClient();
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, NetStateManager.getAPN());
		
		HttpPost post = new HttpPost(url);
		String boundary = getBoundry();
		post.setHeader("Content-Type", "multipart/form-data" + "; boundary=" + boundary);
			
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(getParamsBytes(boundary, params));
		baos.write(getDataPrefix(boundary));
		baos.write(data);
		baos.write(getDataPostFix(boundary));
		post.setEntity(new ByteArrayEntity(baos.toByteArray()));
		SnsIOUtil.closeOutStream(baos);
		
		HttpResponse response = httpClient.execute(post);
		return EntityUtils.toString(response.getEntity());
	}	
	
	public static String httpClientGet(String url, List<NameValuePair> params) throws ClientProtocolException, IOException 
	{
		if (TextUtils.isEmpty(url))
			return "";
		
		if(params != null && params.size()>0){
			url = buildGetParams(url,params);	
		}
		HttpClient httpClient = getSinaHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response = httpClient.execute(get);
		return EntityUtils.toString(response.getEntity());
	}
	
	private static HttpClient getSinaHttpClient()
	{
		HttpClient httpClient = null;
		try{
			
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000*60);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000*60);
			
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			SchemeRegistry register = httpClient.getConnectionManager().getSchemeRegistry();
			register.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			register.register(new Scheme("https", sf, 443));
	
		}catch(Exception e){
			e.printStackTrace();
		}

		return httpClient;
	}
	
	private static class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException,
				KeyManagementException, KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}
		
		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException 
				{
			
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}
		
		@Override
		public Socket createSocket() throws IOException 
		{
			return sslContext.getSocketFactory().createSocket();
		}
	}
	
	private static String buildGetParams(String url, List<NameValuePair> params)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		sb.append("?");
		NameValuePair nvp = null;
		for(int i=0;i<params.size();i++){
			nvp = params.get(i);
			sb.append(URLEncoder.encode(nvp.getName()));
			sb.append("=");
			sb.append(URLEncoder.encode(nvp.getValue()));
			sb.append("&");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	private static byte[] getParamsBytes(String boundary, List<NameValuePair> params)
	{
		StringBuilder sb = new StringBuilder();
		NameValuePair nvp = null;
		for(int i=0; i<params.size();i++){
			nvp = params.get(i);
			sb.append("--");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("content-disposition: form-data; name=\"").append(nvp.getName()).append("\"\r\n\r\n");
			sb.append(nvp.getValue()).append("\r\n");
		}
		return sb.toString().getBytes();
	}
	
	private static byte[] getDataPrefix(String boundary)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("--");
		sb.append(boundary);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data; name=\"pic\"; filename=\"").append("news_image").append("\"\r\n");
		sb.append("Content-Type: ").append("image/png").append("\r\n\r\n");
		
		return sb.toString().getBytes();
	}
	
	private static byte[] getDataPostFix(String boundary)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("--");
		sb.append(boundary);
		sb.append("--");
		
		return sb.toString().getBytes();
	}
	
    private static String getBoundry() 
    {
        StringBuilder sb = new StringBuilder();
        for (int t = 1; t < 12; t++) {
            long time = System.currentTimeMillis() + t;
            if (time % 3 == 0) {
                sb.append((char) time % 9);
            } else if (time % 3 == 1) {
                sb.append((char) (65 + time % 26));
            } else {
                sb.append((char) (97 + time % 26));
            }
        }
        return sb.toString();
    }
}
