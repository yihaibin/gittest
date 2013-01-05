
package com.qyer.android.oneday.http;

import com.qyer.android.oneday.http.response.BaseResponse;

public class HttpCallback {

	public void onPre(){}
	public void doInBackground(BaseResponse resp){}
	public void onPost(BaseResponse resp){}
	public void onCancelled(){}
	
}
