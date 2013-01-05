package com.qyer.android.oneday.activity;

import com.qyer.android.oneday.R;
import com.qyer.android.oneday.http.HttpCallback;
import com.qyer.android.oneday.http.HttpRequest;
import com.qyer.android.oneday.http.HttpTask;
import com.qyer.android.oneday.http.response.BaseResponse;
import com.qyer.android.oneday.http.response.QyerResponse;
import com.qyer.android.oneday.util.AppInfoUtil;
import com.qyer.android.oneday.util.DeviceUtil;
import com.qyer.android.oneday.util.LogManager;
import com.qyer.android.oneday.util.TextUtil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class FeedBackActivity extends BaseActivity{

	public static final String TAG = "IdeaFeedBackActivity";
	
	private static final int WORD_MAX_COUNT = 300;
	
	private EditText mEtShareContent;
	private TextView mTvCount;
	private HttpTask mFeedBackTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_feedback, TITLE_BAR_STYLE_IBTN_BTN);
	}

	protected void initTitleView() 
	{
		ImageButton ibtn = (ImageButton) getTitleBarLeftBtn();
		ibtn.setImageResource(R.drawable.ic_back);
		ibtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		
		Button btn = getTitleBarMidBtn();
		btn.setText(R.string.feedBack);
		
		btn = (Button) getTitleBarRightBtn();
		btn.setText(R.string.send);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				handleSendBtnClick();
			}
		});
	}

	protected void initContentView() 
	{
		mEtShareContent = (EditText) findViewById(R.id.etFeedBackCotent);
		InputFilter[] filters = new InputFilter[]{new InputFilter.LengthFilter(WORD_MAX_COUNT)};
		mEtShareContent.setFilters(filters);
		
		mTvCount = (TextView) findViewById(R.id.tvCount);
		mTvCount.setText(String.valueOf(WORD_MAX_COUNT));
		mEtShareContent.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) 
			{
				mTvCount.setText(String.valueOf(WORD_MAX_COUNT - s.length()));
			}
		});
	}
	
	protected void handleSendBtnClick() 
	{
		if(mFeedBackTask != null && mFeedBackTask.getStatus() == AsyncTask.Status.RUNNING)
			return;
		
		if(!DeviceUtil.isConnectInternet(this)){
			showToast(R.string.toast_noInternet);
			return;
		}
		
		//check content
		String content = mEtShareContent.getText().toString();
		if(content.length() == 0){
			showToast(R.string.toast_pleaseInputFull);
			return;
		}
		
		String imei = DeviceUtil.getIMEI(this);
		String sendContent = wrappFeedBack(content);
		
		mFeedBackTask = HttpRequest.feedBack(sendContent, imei, new HttpCallback() {
			private Dialog dialog;
			@Override
			public void onPre() 
			{
				LogManager.printD(TAG, "IdeaFeedBackTask onPre");
				dialog = showProgressDialog();
			}
			
			@Override
			public void onPost(BaseResponse resp) 
			{
				if(!dialog.isShowing())
					return;
				
				QyerResponse qr = (QyerResponse) resp;
				if(qr.isSuccess()){
					showToast(R.string.toast_feedback_success);
					dialog.dismiss();
					finish();
				}else{
					showToast(R.string.toast_feedback_failed);
					dialog.dismiss();
				}
			}
			
			private Dialog showProgressDialog()
			{
				ProgressDialog pd = new ProgressDialog(FeedBackActivity.this);
				pd.setMessage(getResources().getString(R.string.sending));
				pd.setCanceledOnTouchOutside(false);
				pd.show();
				return pd;
			}
		});
	}
	
	private String wrappFeedBack(String content)
	{
		String deviceName = android.os.Build.DEVICE;
		String sysVersion = android.os.Build.VERSION.RELEASE;
		String appVersionName = AppInfoUtil.getVersionName(this);
		String userName = getAppConfigPrefs().getUserName();
		if(TextUtil.isEmpty(userName))
			userName = getResources().getString(R.string.visitor);
		
		String text = getResources().getString(R.string.feedBack_content,deviceName,sysVersion,appVersionName,userName,content);
		LogManager.printD(TAG, text);
		return text;
	}
}
