package com.qyer.android.oneday.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CustomBaseAdapter<T> extends BaseAdapter{
	
	private List<T> mData;
	private LayoutInflater mLayoutInflater;
	
	protected CustomBaseAdapter(Context context)
	{
		mLayoutInflater = LayoutInflater.from(context);
	}
	
	protected CustomBaseAdapter(Context context, List<T> data)
	{
		this(context);
		mData = data;
	}
	
	@Override
	public int getCount() 
	{
		if(mData == null)
			return 0;
		
		return mData.size();
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	
	@Override
	public T getItem(int position) 
	{
		if (mData == null)
			return null;
	
		T t = null;
		try {
			t = mData.get(position);
		} catch (Exception e) {
		}
		
		return t;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if(convertView == null)
			convertView = createConvertView(position, parent);
		
		freshConvertView(position, convertView, parent);
		return convertView;
	}
	
	protected abstract View createConvertView(int position, ViewGroup parent);
	
	protected abstract void freshConvertView(int position, View convertView, ViewGroup parent); 
	
	public void setData(List<T> data)
	{
		mData = data;
	}
	
	public List<T> getData()
	{
		return mData;
	}
	
	public void remove(T t)
	{
		if(mData == null)
			return;
		
		mData.remove(t);
	}
	
	public void clear()
	{
		if(mData == null)
			return;
		
		mData.clear();
	}
	
	public void release()
	{
	}
	
	protected LayoutInflater getLayoutInflater()
	{
		return mLayoutInflater;
	}
}
