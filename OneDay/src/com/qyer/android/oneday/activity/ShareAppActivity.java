package com.qyer.android.oneday.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

import com.qyer.android.oneday.R;
import com.qyer.android.oneday.context.Constant;
import com.qyer.android.oneday.util.DeviceUtil;
import com.qyer.android.oneday.util.EnvironmentUtil;
import com.qyer.android.oneday.util.IOUtil;
import com.qyer.android.sns.activity.SinaWeiboService;
import com.qyer.android.sns.activity.SinaWeiboService.ErrorCode;
import com.qyer.android.sns.activity.SinaWeiboService.ShareListener;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;

public class ShareAppActivity extends BaseActivity implements OnClickListener,Constant{

	public static final String TAG = "ShareActivity";
						
	private static final String WX_PACKAGE_NAME = "com.tencent.mm";
	private static final int THUMB_SIZE = 150;
	
	private LinearLayout mLlWeibo;
	private LinearLayout mLlWeixin;
	private LinearLayout mLlWeixinFriend;
	private LinearLayout mLlMail;
	private LinearLayout mLlSms;
	
	private IWXAPI wxapi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.act_share_app);
	}
	
	protected void initData() 
	{
		//reg wx
		wxapi = WXAPIFactory.createWXAPI(this, APP_KEY_WEIXIN, true);
		wxapi.registerApp(APP_KEY_WEIXIN);
	}
	
	protected void initContentView() 
	{
		mLlWeibo = (LinearLayout)findViewById(R.id.llSina);
		mLlWeixin = (LinearLayout)findViewById(R.id.llWeixin);
		mLlWeixinFriend = (LinearLayout)findViewById(R.id.llWeixinFriend);
		mLlMail = (LinearLayout)findViewById(R.id.llEmail);
		mLlSms = (LinearLayout)findViewById(R.id.llSms);
		
		mLlWeibo.setOnClickListener(this);
		mLlWeixin.setOnClickListener(this);
		mLlWeixinFriend.setOnClickListener(this);
		mLlMail.setOnClickListener(this);
		mLlSms.setOnClickListener(this);
	}
	
	public void onClick(View view) 
	{
		switch (view.getId()) {
			case R.id.llSina:
				shareToSinaWeibo();
				return;
			case R.id.llWeixin:
				startWeixinActivity();
				break;
			case R.id.llWeixinFriend:
				startWeixinFriendActivity();
				break;
			case R.id.llEmail:
				startEmailActivity();
				break;
			case R.id.llSms:
				startSmsActivity();
				break;
		}
		finish();
	}
	
	private void shareToSinaWeibo() 
	{
		String shareText = getResources().getString(R.string.share_AppDesc,SHARE_DOWNLOAD_URL);
		Bitmap appCover = BitmapFactory.decodeResource(getResources(),R.drawable.cover);
		SinaWeiboService.share(this, APP_KEY_WEIBO_SINA, APP_REDIRECTURL_WEIBO_SINA, shareText, appCover, new ShareListener() {
					@Override
					public void onShareSuccess() {
						showToast(R.string.toast_share_success);
					}

					@Override
					public void onShareFailed(int errorCode) {
						switch (errorCode) {
						case ErrorCode.ERROR_INTERNET_DISABLE:
							showToast(R.string.toast_noInternet);
							break;
						case ErrorCode.ERROR_INTERNET_TIMEOUT:
							showToast(R.string.toast_internet_timeout);
							break;							
						case ErrorCode.ERROR_WEIBO_REPEAT:
							showToast(R.string.toast_weibo_repeat);
							break;
						case ErrorCode.ERROR_WEIBO_SIMILARITY:
							showToast(R.string.toast_weibo_similarty);
							break;
						case ErrorCode.ERROR_WEIBO_OUT_LIMIT:
							showToast(R.string.toast_weibo_out_limit);
							break;
						case ErrorCode.ERROR_WEIBO_TOKEN_EXPIRED:
						case ErrorCode.ERROR_WEIBO_EXPIRED_TOKEN:
							showToast(R.string.toast_weibo_token_expired);
							break;
						default:
							showToast(R.string.toast_share_failed);
							break;
						}
					}
				});
	}
	
	private void startWeixinActivity() 
	{
		if(!DeviceUtil.hasApp(this, WX_PACKAGE_NAME)){
			showToast(R.string.toast_no_weixin);
			return;
		}
		
		sendWeiXinMsg(SendMessageToWX.Req.WXSceneSession, R.drawable.ic_launcher108);
	}	
	
	private void startWeixinFriendActivity() 
	{
		if(!DeviceUtil.hasApp(this, WX_PACKAGE_NAME)){
			showToast(R.string.toast_no_weixin);
			return;
		}
		
		if (wxapi.getWXAppSupportAPI() < 0x21020001) {
			showToast(R.string.toast_no_weixin4_2);
			return;
		}

		sendWeiXinMsg(SendMessageToWX.Req.WXSceneTimeline,R.drawable.ic_launcher108);
	}		

	private void sendWeiXinMsg(int scene, int drawable)
	{
		Resources resource = getResources();
		WXMediaMessage msg = null;
		String text = resource.getString(R.string.share_AppDesc,SHARE_DOWNLOAD_URL);
		
		if(scene == SendMessageToWX.Req.WXSceneSession){
			//freand
			WXWebpageObject webpage = new WXWebpageObject();
			webpage.webpageUrl = SHARE_DOWNLOAD_URL;
			
			msg = new WXMediaMessage(webpage);
			msg.title = resource.getString(R.string.app_name);
			msg.description = text;
			
			Bitmap bmp = BitmapFactory.decodeResource(resource, drawable);
			Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
			if(bmp != null && !thumbBmp.isRecycled())
				bmp.recycle();
			
			msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
			
		}else{
			//freand quan
			WXTextObject textObj = new WXTextObject();
			textObj.text = text;
			
			msg = new WXMediaMessage(textObj);
			msg.description = text;
		}
			
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = scene;
		
		wxapi.sendReq(req);
	}
	
	private void startEmailActivity() 
	{
		try{
			
			String subject = getResources().getString(R.string.share_email_subject,
													  getResources().getString(R.string.app_name));
			String body = getResources().getString(R.string.share_AppDesc,SHARE_DOWNLOAD_URL);
			File imgFile = getAppCoverFile();
			
			Intent intent = new Intent(android.content.Intent.ACTION_SEND);
			intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
			intent.putExtra(android.content.Intent.EXTRA_TEXT, body);
			if(imgFile == null)
				intent.setType("plain/text");
			else{
				Uri uri = Uri.fromFile(imgFile);
				intent.putExtra(Intent.EXTRA_STREAM, uri);
				intent.setType("application/octet-stream");
			}

			startActivity(intent);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private File getAppCoverFile()
	{
		File f = new File(EnvironmentUtil.getSdcardPicsDir(),"cover_app.jpg");
		if(f.exists())
			return f;
		
		InputStream input = null;
		OutputStream outPut = null;
		try{
			
			input = getResources().openRawResource(R.drawable.cover);
			outPut = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int len = -1;
			while((len = input.read(buffer))!=-1){
				outPut.write(buffer, 0, len);
			}
			
		}catch(Exception e){
			f = null;
			e.printStackTrace();
		}finally{
			IOUtil.closeInStream(input);
			IOUtil.closeOutStream(outPut);
		}
		return f;
	}
	
	private void startSmsActivity()
	{
		try{
			
			Intent it = new Intent(Intent.ACTION_VIEW);
			it.putExtra("sms_body",getResources().getString(R.string.share_AppDesc,SHARE_DOWNLOAD_URL));
			it.setType("vnd.android-dir/mms-sms");
			startActivity(it);
			
		}catch(Exception e){
			
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		if(event.getAction() == MotionEvent.ACTION_UP)
			finish();
		
		return super.onTouchEvent(event);
	}
}
