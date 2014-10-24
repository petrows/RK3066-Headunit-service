package com.petrows.mtcservice;

import java.util.Arrays;
import java.util.List;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class ServiceMain extends Service {

	final static String TAG = "MTCService.ServiceMain";
	final static String MTCBroadcastIrkeyUp    = "com.microntek.irkeyUp";
	final static String MTCBroadcastIrkeyDown  = "com.microntek.irkeyDown";

	List<Integer> MTCKeysPrev  = Arrays.asList(58, 22);
	List<Integer> MTCKeysNext  = Arrays.asList(59, 24);
	List<Integer> MTCKeysPause = Arrays.asList(3);
	
	private final String MTCBroadcastWidget = "com.android.MTClauncher.action.INSTALL_WIDGETS";
	private final int    MTCWidgetAdd    = 10520;
	private final int    MTCWidgetRemove = 10521;

	public BroadcastReceiver brKeys;
	public BroadcastReceiver brWidget;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "MTCService onCreate");
				
		IntentFilter intFilt;

		brKeys = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				int keyCode = intent.getIntExtra("keyCode", 0);
				Log.d(TAG, "MTCService recieve key "+keyCode);
				
				if (MTCKeysPrev.contains(keyCode))
				{
					sendKey(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
					toast("<<");
				}
				if (MTCKeysNext.contains(keyCode))
				{
					sendKey(KeyEvent.KEYCODE_MEDIA_NEXT);
					toast(">>");
				}				
				if (MTCKeysPause.contains(keyCode))
				{
					sendKey(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
					toast("||");
				}				
			}
		};
		
		intFilt = new IntentFilter();
		intFilt.addAction(MTCBroadcastIrkeyDown);
		registerReceiver(brKeys, intFilt);
		
		brWidget = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				int wdgAction  = intent.getIntExtra("myWidget.action", 0);
				String wdgPackage = intent.getStringExtra("myWidget.packageName");
				Log.d(TAG, "MTCService recieve widget "+wdgPackage+", "+wdgAction);
				// Install widget?
				if (MTCWidgetAdd == wdgAction)
				{
					String[] wdgPackageSplit = wdgPackage.split("\\.");
					if (wdgPackageSplit.length > 0)
					{
						String pkgShort = wdgPackageSplit[wdgPackageSplit.length - 1];
						pkgShort = String.valueOf(pkgShort.charAt(0)).toUpperCase() + pkgShort.subSequence(1, pkgShort.length());
						Log.d(TAG, "Started mode: " + pkgShort);						
						toast(pkgShort);
					}
					
					killMusic();
				}
			}
		};
		
		intFilt = new IntentFilter();
		intFilt.addAction(MTCBroadcastWidget);
		registerReceiver(brWidget, intFilt);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(brKeys);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		Log.d(TAG, "MTCService onStartCommand");

		Notification note = new NotificationCompat.Builder(this)
				.setContentTitle("MTC system service")
				.setContentText("Service running")
				.setSmallIcon(R.drawable.ic_launcher).build();
		startForeground(1337, note);

		return START_STICKY;
	}
	
	public void killMusic()
	{
		Log.d(TAG, "Killing music");	
		// Stop playback (NORMAL players)
		sendKey(KeyEvent.KEYCODE_MEDIA_STOP);
		// Stop all music players
		
		//ActivityManager activityManager = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
		//activityManager.killBackgroundProcesses("com.vkontakte.android.AudioPlayerService");
	}

	public void sendKey(int keycode) {
		Log.d(TAG, "Send key " + keycode);
		long eventtime = SystemClock.uptimeMillis();

		Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		KeyEvent downEvent = new KeyEvent(eventtime, eventtime,
				KeyEvent.ACTION_DOWN, keycode, 0);
		downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
		sendOrderedBroadcast(downIntent, null);
				
		Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		KeyEvent upEvent = new KeyEvent(eventtime, eventtime,
				KeyEvent.ACTION_UP, keycode, 0);
		upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
		sendOrderedBroadcast(upIntent, null);		
	}

	public void toast(String msg) {
		if (Settings.get().toast) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}
}
