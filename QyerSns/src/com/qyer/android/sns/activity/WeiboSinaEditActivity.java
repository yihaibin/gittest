package com.qyer.android.sns.activity;

import java.io.ByteArrayOutputStream;

import com.qyer.android.sns.R;
import com.qyer.android.sns.activity.SinaWeiboService.ErrorCode;
import com.qyer.android.sns.activity.SinaWeiboService.ShareRequest;
import com.qyer.android.sns.activity.SinaWeiboService.SinaWeiboPrefs;
import com.qyer.android.sns.http.SinaRespUpdate;
import com.qyer.android.sns.http.SinaRespUserInfo;
import com.qyer.android.sns.http.WeiboRequest;
import com.qyer.android.sns.util.SnsAsynccTask;
import com.qyer.android.sns.util.SnsDeviceUtil;
import com.qyer.android.sns.util.SnsTextUtil;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WeiboSinaEditActivity extends BaseActivity implements ErrorCode{

	private static final int WORD_MAX_COUNT = 140;
	
	private EditText mEtWeiboContent;
	private TextView mTvWordCount, mTvAccount;
	private ImageView mIvPic;
	private boolean mIsSend;
	private ShareRequest mShareReuqest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_weibo_edit);
		
		if(!initData(savedInstanceState)){
			finish();
			return;
		}
		
		initTitleView();
		initContentView();
	}
	
	private boolean initData(Bundle savedInstanceState) 
	{
		mShareReuqest = (ShareRequest) SinaWeiboService.getRequest();
		if(mShareReuqest == null || savedInstanceState != null)
			return false;
		else
			return true;
	}

	private void initTitleView() 
	{
		getTitleBarLeftBtn().setVisibility(View.INVISIBLE);
		
		getTitleBarMidBtn().setText(R.string.share_to_sina_weibo);
		
		Button btnSend = (Button) getTitleBarRightBtn();
		btnSend.setText(R.string.weibo_send);
		btnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				sendSinaWeibo();
			}
		});
	}
	
	private void initContentView() 
	{
		mIvPic = (ImageView) findViewById(R.id.ivPic);
		if(mShareReuqest.bmp != null)
			mIvPic.setImageBitmap(mShareReuqest.bmp);
		
		mTvWordCount = (TextView) findViewById(R.id.tvWordCount);
		mTvWordCount.setText(String.valueOf(WORD_MAX_COUNT));
		mEtWeiboContent = (EditText) findViewById(R.id.etWeiboContent);
		mEtWeiboContent.requestFocus();
		mEtWeiboContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(WORD_MAX_COUNT){
			
					public CharSequence filter(CharSequence source, int start,
							int end, Spanned dest, int dstart, int dend) {
						
						int destLen = SnsTextUtil.calculateWeiboLength(dest);
						int sourceLen = SnsTextUtil.calculateWeiboLength(source);
						if (destLen + sourceLen > WORD_MAX_COUNT) 
							return "";
						
						return source;
					}
		
		}});
		mEtWeiboContent.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) 
			{
			}
			
			@Override
			public void afterTextChanged(Editable s) 
			{
				mTvWordCount.setText((WORD_MAX_COUNT - SnsTextUtil.calculateWeiboLength(s))+"");
			}
		});		
		mEtWeiboContent.setText(mShareReuqest.text);
		mEtWeiboContent.setSelection(mEtWeiboContent.length());
		
		mTvAccount = (TextView) findViewById(R.id.tvAccount);
		String nickName = mShareReuqest.prefs.getNickName();
		if(TextUtils.isEmpty(nickName)){
			new GetUserInfoTask(mShareReuqest.prefs).execute();
		}else{
			mTvAccount.setText(nickName);
		}
	}
	
	private void sendSinaWeibo()
	{
		if(!SnsDeviceUtil.isConnectInternet(this)){
			mShareReuqest.lisn.onShareFailed(ERROR_INTERNET_DISABLE);
			return;
		}
		new SendWeiboTask().execute(mShareReuqest.prefs.getAccessToken(),
									mEtWeiboContent.getText().toString(),
									mShareReuqest.bmp);
		finish();
	}
	
	private class SendWeiboTask extends SnsAsynccTask<Object, SinaRespUpdate>{
		
		@Override
		protected void onPreExecute() 
		{
			mIsSend = true;
		}
		
		@Override
		protected SinaRespUpdate doInBackground(Object... params) 
		{
			String accessToken = (String) params[0];
			String text = (String) params[1];
			Bitmap bmp = (Bitmap) params[2];
			SinaRespUpdate sur;
			if(bmp == null){
				 sur = WeiboRequest.sendSinaWeibo(accessToken, text);
			}else{
				 ByteArrayOutputStream baos = new ByteArrayOutputStream();
				 bmp.compress(CompressFormat.PNG, 100, baos);
				 sur = WeiboRequest.sendSinaWeibo(accessToken, text, baos.toByteArray());
			}
			return sur;
		}
		
		@Override
		protected void onPostExecute(SinaRespUpdate result) 
		{
			if(result.isException()){
				if(result.isTimeout())
					mShareReuqest.lisn.onShareFailed(ERROR_INTERNET_TIMEOUT);
				else
					mShareReuqest.lisn.onShareFailed(ERROR_WEIBO_FAILED);
			}else{
				
				if(result.isSuccess()){
					mShareReuqest.lisn.onShareSuccess();
				}else{
					if(result.isError()){
						mShareReuqest.lisn.onShareFailed(result.getErrorCode());
					}else{
						mShareReuqest.lisn.onShareFailed(ERROR_WEIBO_FAILED);
					}
				}
			}
			mShareReuqest.recycle();
			mShareReuqest = null;
		}
	}
	
	private class GetUserInfoTask extends SnsAsynccTask<Object, SinaRespUserInfo>{
		
		private SinaWeiboPrefs mPrefs;
		
		GetUserInfoTask(SinaWeiboPrefs prefs)
		{
			mPrefs = prefs;
		}
		
		@Override
		protected void onPreExecute() 
		{
		}
		
		@Override
		protected SinaRespUserInfo doInBackground(Object... params) 
		{
			SinaRespUserInfo srui = WeiboRequest.getUserInfo(mPrefs.getAccessToken(), 
															 String.valueOf(mPrefs.getUid()));
			return srui;
		}
		
		@Override
		protected void onPostExecute(SinaRespUserInfo result) 
		{
			String nickName = result.getNickName();
			if(TextUtils.isEmpty(nickName))
				return;
			
			mPrefs.saveNickName(nickName);
			mPrefs = null;
			if(isFinishing())
				return;
			
			mTvAccount.setText(nickName);
		}
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		if(keyCode == KeyEvent.KEYCODE_BACK){
			showExitTipDialog();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	private void showExitTipDialog()
	{
		android.app.AlertDialog ad = new android.app.AlertDialog.Builder(this).create();
		ad.setMessage(getResources().getString(R.string.giveup_edit));
		ad.setCanceledOnTouchOutside(false);
		ad.setButton(-1, getResources().getString(R.string.confirm), new android.app.AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				finish();
			}
		});
		ad.setButton(-2, getResources().getString(R.string.cancel), new android.app.AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});		
		ad.show();		
	}
	
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		
		if(mShareReuqest != null && !mIsSend){
			mShareReuqest.recycle();
			mShareReuqest = null;
		}
	}
}
