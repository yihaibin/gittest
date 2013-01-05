package com.qyer.android.oneday.http;

public interface HttpParams {
	
	//request params value
	public static final String VAL_CLIENT_ID = "abcd123";
	public static final String VAL_CLIENT_SECRET = "a88637d05a706c33278a";
	public static final String VAL_GRANT_TYPE = "password";
	public static final String VAL_APP_VERSION_ALL = "all";
	public static final String VAL_DEVICE_TYPE_ANDROID = "3";
	
	//url
	public static final String URL_BASE = "http://open.qyer.com/";
	public static final String URL_LOGIN = URL_BASE+"access_token";
	public static final String URL_REGISTER = URL_BASE+"user/register";
	public static final String URL_FEEDBACK = URL_BASE+"app_feedback/add"; 
	public static final String URL_GETADVERT = URL_BASE+"app/get_ad";//?client_id="+VAL_CLIENT_ID+"&client_secret="+VAL_CLIENT_SECRET+"&app_version=all";
	public static final String URL_QYER_MORE_APP = URL_BASE+"app/relations";
	public static final String URL_RECOMMEND_APP = URL_BASE+"app/out_relations";
	public static final String URL_FORCE_UPDATE = URL_BASE+"app/verify_version";
	public static final String URL_GET_PUSH = URL_BASE+"app/get_push"; 
	
	//request params
	public static final String REQ_CLIENT_ID = "client_id";
	public static final String REQ_CLIENT_SECRET = "client_secret";//CS_PARAM_CLIENT_SECRET
	public static final String REQ_GRANT_TYPE = "grant_type";
	public static final String REQ_USERNAME = "username";
	public static final String REQ_PASSWORD = "password";
	public static final String REQ_EMAIL = "email";
	public static final String REQ_ACCOUNT_S = "account_s";
	
	public static final String REQ_APP_NAME = "app_name";
	public static final String REQ_APP_VERSION = "app_version";
	public static final String REQ_DEVICE_ID = "device_id";
	public static final String REQ_DEVICE_NUMBER = "device_number";
	public static final String REQ_DEVICE_TYPE = "device_type";
	public static final String REQ_VERSION = "version";
	public static final String REQ_CONTENT = "content";
	public static final String REQ_MODIFIED = "modified";
	
	
	//response params
	public static final String RESP_STATUS = "status";
	public static final String RESP_INFO = "info";
	public static final String RESP_DATA = "data";
	
	//login response
	public static final String RESP_UID = "uid";
	public static final String RESP_ACCESS_TOKEN = "access_token";
	public static final String RESP_EXPIRES_IN = "expires_in";
	public static final String RESP_SCOPE = "scope";
	
	public static final String RESP_ID = "id";
	public static final String RESP_AD_TITLE = "ad_title";
	public static final String RESP_TAG = "tag";
	public static final String RESP_CREATED = "created";
	public static final String RESP_ORDER_NUMBER = "order_number";
	public static final String RESP_AD_DATA = "ad_data";
	public static final String RESP_AD_OPEN_MODE = "ad_open_mode";
	public static final String RESP_AD_LINK = "ad_link";
	public static final String RESP_AD_IMG = "ad_img";
	public static final String RESP_START_TIME = "start_time";
	public static final String RESP_END_TIME = "end_time";
	
	public static final String RESP_THUMB = "thumb";
	public static final String RESP_APPSTORE_URL = "appstore_url";
	public static final String RESP_SUBNAME = "sub_name";
	public static final String RESP_PACKAGE = "package";
	
	public static final String RESP_TITLE = "title";
	public static final String RESP_LINK = "link";
	public static final String RESP_DESC = "description";	
	
	//verifyVersion
	public static final String RESP_OVER = "over";
	public static final String RESP_WVERSION = "warning_version";
	public static final String RESP_WURL = "warning_url";
	public static final String RESP_WCONTENT = "warning_content";
	
	//get push
	public static final String RESP_SPACE = "space";
	public static final String RESP_LIST = "list";
	public static final String RESP_ENTITY_ID = "id";
	public static final String RESP_APP_NAME = "app_name";
	public static final String RESP_CONTENT= "content";
	public static final String RESP_CHART = "chart";
	public static final String RESP_DATETIME = "datetime";
	
	
}
