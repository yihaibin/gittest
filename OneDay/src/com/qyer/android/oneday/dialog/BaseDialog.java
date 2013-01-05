package com.qyer.android.oneday.dialog;

import com.qyer.android.oneday.R;

import android.app.Dialog;
import android.content.Context;

public class BaseDialog extends Dialog{

	protected BaseDialog(Context context) 
	{
		super(context, R.style.base_dialog);
	}
	
	protected BaseDialog(Context context, int style)
	{
		super(context,style);
	}
}
