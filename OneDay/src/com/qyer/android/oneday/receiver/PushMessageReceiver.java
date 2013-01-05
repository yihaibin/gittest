package com.qyer.android.oneday.receiver;

import java.util.List;

import com.qyer.android.oneday.R;
import com.qyer.android.oneday.activity.LauncherActivity;
import com.qyer.android.oneday.http.HttpCallback;
import com.qyer.android.oneday.http.HttpRequest;
import com.qyer.android.oneday.http.response.BaseResponse;
import com.qyer.android.oneday.http.response.PushResponse;
import com.qyer.android.oneday.http.response.PushResponse.PushEntity;
import com.qyer.android.oneday.util.AppInfoUtil;
import com.qyer.android.oneday.util.CollectionUtil;
import com.qyer.android.oneday.util.DeviceUtil;
import com.qyer.android.oneday.util.LogManager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class PushMessageReceiver extends BroadcastReceiver{
	
	public static final String TAG = "PushMessageReceiver";
	public static final int DELAY_MILLIS_DEF = 60*30*1000;//unit:second
	public static final int APP_NOTIFICATION_ID = 1;
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		if(DeviceUtil.isConnectInternet(context)){
			getPushMessage(context);
		}else{
			start(context, DELAY_MILLIS_DEF);
			LogManager.printD(TAG, "getPushMessage no ConnectInternet");
		}
	}
	
	private void getPushMessage(final Context context)
	{
		String imei = DeviceUtil.getIMEI(context);
		String versionName = AppInfoUtil.getVersionName(context);
		HttpRequest.getPushMessage(imei, versionName, new HttpCallback() {
			@Override
			public void onPost(BaseResponse resp) 
			{
				int delayMillis = DELAY_MILLIS_DEF;
				if(resp instanceof PushResponse && 
				   !resp.isConnException()){
					
					PushResponse pr = (PushResponse) resp;
					sendNotification(context, pr);
					delayMillis = pr.getSpace()*1000;
				}
				start(context, delayMillis);
			}
		});
	}
	
	private void sendNotification(Context context, PushResponse pr)
	{
		List<PushEntity> entites = pr.getPushEntities();
		if(CollectionUtil.isEmpty(entites))
			return;
		
		PushEntity pe = entites.get(0);
		if(pe == null)
			return;
		
		NotificationManager notifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notify = new Notification();
		notify.icon =  R.drawable.ic_launcher;
		notify.when = System.currentTimeMillis();
		notify.number = pe.getChart();
		
		notify.defaults = Notification.DEFAULT_ALL;
		notify.flags = Notification.FLAG_AUTO_CANCEL;
		
	    Intent intent = new Intent();
	    intent.setClass(context, LauncherActivity.class);
    	PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
    	notify.setLatestEventInfo(context, context.getResources().getString(R.string.app_name), pe.getContent(), pi);
    	notifyMgr.notify(APP_NOTIFICATION_ID, notify);	
	}
	
	public static void start(Context context, int delayMillis)
	{
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, PushMessageReceiver.class);
		PendingIntent pendIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + delayMillis, pendIntent);
	}
	
	public static void cancel(Context context)
	{
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, PushMessageReceiver.class);
		PendingIntent pendIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.cancel(pendIntent);
	}
}
