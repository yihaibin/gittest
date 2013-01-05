package com.qyer.android.oneday.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class NoCacheListView extends ListView{
	
	
	public NoCacheListView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
