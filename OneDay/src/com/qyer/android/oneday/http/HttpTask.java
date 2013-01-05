package com.qyer.android.oneday.http;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;

import android.os.AsyncTask;

import com.qyer.android.oneday.http.response.BaseResponse;
import com.qyer.android.oneday.http.response.ByteResponse;
import com.qyer.android.oneday.http.response.JsonableResponse;
import com.qyer.android.oneday.http.response.ResponseFactory;
import com.qyer.android.oneday.http.response.TextResponse;
import com.qyer.android.oneday.util.LogManager;

public abstract class HttpTask extends AsyncTask<Object, Void, BaseResponse>{

	public static final String TAG = "HttpRequestTask";

	private HttpCallback callback;
	private HttpUriRequest request;

	protected HttpTask(HttpUriRequest request, HttpCallback callback)
	{
		this.request = request;
		this.callback = callback;
	}

	@Override
	protected void onPreExecute() 
	{
		if (callback != null)
			callback.onPre();
	}

	/**
	 * params[0]: url
	 * params[1]: List<NameValuePair>
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected BaseResponse doInBackground(Object... params) 
	{
		LogManager.printD(TAG, "http request url = "+params[0]);
		BaseResponse resp = ResponseFactory.getResponseByUrl((String) params[0]);
		try {

			setParamToRequest(request, (String)params[0], (List<NameValuePair>)params[1]);
			if(resp instanceof JsonableResponse){
				
				String respText = ApacheHttpClient.getInstance().executeText(request);
				((JsonableResponse)resp).setDataFromJson(respText);
				LogManager.printD(TAG, "return text = "+respText);
			
			}else if(resp instanceof TextResponse){
				
				String respText = ApacheHttpClient.getInstance().executeText(request);
				((TextResponse)resp).setText(respText);
				LogManager.printD(TAG, "return text = "+respText);
			
			}else if(resp instanceof ByteResponse){
				
				byte[] bytes = ApacheHttpClient.getInstance().executeByteArray(request);
				((ByteResponse)resp).setBytes(bytes);
			}
			
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			resp.setTimeout(true);
		} catch (Exception e){
			e.printStackTrace();
			resp.setConnException(true);
		}finally{
			abort(false);
		}
		if(callback != null)
			callback.doInBackground(resp);
		
		return resp;
	}
	
	protected abstract void setParamToRequest(HttpUriRequest request, String url, List<NameValuePair> params) throws UnsupportedEncodingException;

	@Override
	protected final void onPostExecute(BaseResponse result)
	{
		if (callback != null)
			callback.onPost(result);
	}
	
	@Override
	protected final void onCancelled() 
	{
		if (callback != null)
			callback.onCancelled();
	}
	
	public final void abort()
	{
		abort(true);
	}

	private void abort(boolean isCancel) 
	{
		try{
			
			if (request != null) 
				request.abort();
			
		}catch (Exception e) {
		}
		
		if(isCancel)
			cancel(true);
	}
}