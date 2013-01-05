package com.qyer.android.oneday.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.qyer.android.oneday.R;
import com.qyer.android.oneday.db.DBFiled;
import com.qyer.android.oneday.receiver.PushMessageReceiver;
import com.qyer.android.oneday.util.IOUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class LauncherActivity extends BaseActivity {
	
	public static final String TAG = "LauncherActivity";
	private static final int WAIT_MILLIS = 2000;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
    	if(!isTaskRoot()){//if resume app by notification
    		finish();
    		return;
    	}
    	
    	setContentView(R.layout.act_launcher);
    	initApp();
    	initPush();
    }
    
    protected void initContentView()
    {
    	ImageView iv = (ImageView) findViewById(R.id.ivAppCover);
    	Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.cover);
    	iv.setImageBitmap(bmp);
	}

	private void initApp() 
	{
		if (getAppConfigPrefs().isInitAeecess()) {
			mHandler.sendEmptyMessageDelayed(0, WAIT_MILLIS);
			return;
		}
		
		long start = System.currentTimeMillis();
		if (copyDataBase()) {
			getAppConfigPrefs().saveInitAccess();
			mHandler.sendEmptyMessageDelayed(0,WAIT_MILLIS - (System.currentTimeMillis() - start));
		} else {
			showToast(R.string.toast_initFailed);
			finish();
		}
	}
	
    private void initPush() 
    {
		if(getAppConfigPrefs().isAcceptPushMessage()){
			PushMessageReceiver.cancel(getApplicationContext());
			PushMessageReceiver.start(getApplicationContext(), 0);
		}
	}	
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) 
		{
			startMainActivity();
			finish();
		};
	};
    
	private boolean copyDataBase()
	{
		InputStream input = null;
		OutputStream output = null;
		boolean flag = true;
		try{
			File f = getDatabasePath(DBFiled.DB_NAME);
			f.getParentFile().mkdirs();
			input = getAssets().open(DBFiled.DB_NAME);
			output = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int len = -1;
			while((len = input.read(buffer)) != -1){
				output.write(buffer, 0,len);
			}
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}finally{
			IOUtil.closeInStream(input);
			IOUtil.closeOutStream(output);
		}
		return flag;
	}    
	
	private void startMainActivity()
	{
    	Intent intent = new Intent();
    	intent.setClass(this, MainActivity.class);
    	startActivity(intent);    	
    	overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		releaseAppCover();
	}

	private void releaseAppCover() 
	{
		ImageView iv = (ImageView) findViewById(R.id.ivAppCover);
		if(iv == null)
			return;
		
		BitmapDrawable bd = (BitmapDrawable)iv.getDrawable();
		if(bd == null)
			return;
		
		Bitmap bmp = bd.getBitmap();
		if(bmp != null && !bmp.isRecycled())
			bmp.recycle();
	}
}