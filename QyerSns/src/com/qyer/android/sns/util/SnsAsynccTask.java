package com.qyer.android.sns.util;

import android.os.Handler;
import android.os.Message;

public abstract class SnsAsynccTask<Params, Result>{
	
	public static final String TAG = "AsynccTask";
	
	public  static final int STATE_PENDING = 0;
	public  static final int STATE_RUNNING = 1;
	public  static final int STATE_FINISHED = 2;
	
	private static final int MSG_POST_START = 0;
	private static final int MSG_POST_CANCELLED = 1;
	private static final int MSG_POST_RESULT = 2; 
	
	private Thread mThread;
	private int mState = STATE_PENDING;
	private boolean mCancelled;
	
	private Handler mHandler = new Handler(){
		
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) 
		{
			switch(msg.what){
			case MSG_POST_START:
				start((Params[])msg.obj);
				break;
			case MSG_POST_CANCELLED:
				cancel();
				break;
			case MSG_POST_RESULT:
				finish((Result)msg.obj);
				break;
			}
		};
	};
	
	public SnsAsynccTask()
	{
	}
	
	protected void onPreExecute() 
	{
	}
	
	protected abstract Result doInBackground(Params... params);
	
//	protected void onProgressUpdate(Void... values) 
//	{
//	}
	
	protected void onCancelled() 
	{
	}
	
	protected void onPostExecute(Result result) 
	{
	}
	
	public final void execute(Params... params)
	{
		if(mState != STATE_PENDING)
			return;
		
		mState = STATE_RUNNING;
		onPreExecute();
		//start work
		Message msg = mHandler.obtainMessage(MSG_POST_START, params);
		mHandler.sendMessage(msg);
	}
	
	public final void cancel(boolean mayInterruptIfRunning)
	{
		if(mCancelled)
			return;
		
		mHandler.sendEmptyMessage(MSG_POST_CANCELLED);
		if(mayInterruptIfRunning && mThread != null){
			mThread.interrupt();
		}
		mCancelled = true;
	}
	
	public boolean isCancelled()
	{
		return mCancelled;
	}
	
	public int getStatus()
	{
		return mState;
	}
	
	private void start(Params[] params)
	{
		if(!mCancelled){
			mThread = new WorkThread(params);
			mThread.start();
		}
	}
	
	protected void cancel() 
	{
		onCancelled();
		mState = STATE_FINISHED;
	}
	
	private void finish(Result result)
	{
		if(mState == STATE_FINISHED)
			return;
		
		if(isCancelled()){
			onCancelled();
		}else{
			onPostExecute(result);
		}
		mState = STATE_FINISHED;
	}
	
	private class WorkThread extends Thread{
		
		private Params[] params;
		
		private WorkThread(Params[] params)
		{
			this.params = params;
		}
		
		@Override
		public void run() 
		{
			Result result = doInBackground(params);
			Message msg = mHandler.obtainMessage(MSG_POST_RESULT, result);
			mHandler.sendMessage(msg);
		}
		
	}
}
