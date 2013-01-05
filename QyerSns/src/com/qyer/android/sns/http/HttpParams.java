package com.qyer.android.sns.http;

public interface HttpParams {

	public static final String SINA_CLIENT_ID = "client_id";
	public static final String SINA_RESPONSE_TYPE = "response_type";
	public static final String SINA_REDIRECT_URI = "redirect_uri";
	public static final String SINA_DISPLAY = "display";
	
	public static final String SINA_VAL_RES_TYPE_TOKEN = "token";
	public static final String SINA_VAL_DISPLAY_MOBILE = "mobile";
	
	public static final String SINA_URL_OAUTH2 = "https://open.weibo.cn/oauth2/authorize";
	public static final String SINA_URL_OAUTH2_CHECK = SINA_URL_OAUTH2+"?"+SINA_CLIENT_ID+"=%s&"+SINA_RESPONSE_TYPE+"="+SINA_VAL_RES_TYPE_TOKEN+"&"+SINA_REDIRECT_URI+"=%s&"+SINA_DISPLAY+"="+SINA_VAL_DISPLAY_MOBILE;
	public static final String SINA_URL_UPDATE_WEIBO = "https://api.weibo.com/2/statuses/update.json";
	public static final String SINA_URL_UPLOAD_WEIBO = "https://upload.api.weibo.com/2/statuses/upload.json";
	public static final String SINA_URL_USER_INFO = "https://api.weibo.com/2/users/show.json";
	
	public static final String SINA_REQ_PARAM_STATUS = "status";
	public static final String SINA_REQ_PARAM_ACCESS_TOKEN = "access_token";
	public static final String SINA_REQ_PARAM_UID = "uid";
	public static final String SINA_REQ_PARAM_PIC = "pic";
	
	public static final String SINA_RESP_PARAM_ERROR_CODE = "error_code";
	public static final String SINA_RESP_PARAM_ERROR_INFO = "error";
	public static final String SINA_RESP_PARAM_USER_NICKNAME = "screen_name";
	public static final String SINA_RESP_PARAM_CREATED_AT = "created_at";
	
}
