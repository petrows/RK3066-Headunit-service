package com.petrows.mtcservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class ServiceEventReciever extends BroadcastReceiver {
	final static String TAG = "ServiceEventReciever";
    
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		//int keyCode_ = intent.getIntExtra("keyCode", 0);
		//toast(context, "Action " + intent.getAction() + ", key " + keyCode_);
		
		Log.d(TAG, "Action " + intent.getAction() + ", key " + intent.getIntExtra("keyCode", -1));
		Log.d(TAG, "Service enable " + Settings.get(context).getServiceEnable());
		
		if (Settings.get(context).getServiceEnable() && !ServiceMain.isRunning)
		{
			// Run our service (if needed)
			context.startService(new Intent(context, ServiceMain.class));
		}
		
		// Microntek keys?
		if (intent.getAction().equals(Settings.MTCBroadcastIrkeyUp))
		{
			// Key pressed...
			
			int keyCode = intent.getIntExtra("keyCode", 0);
			Log.d(TAG, "MTCService recieve key "+keyCode);
			
			if (Settings.MTCKeysPrev.contains(keyCode))
			{
				sendKey(context, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
				toast(context, "<<");
			}
			if (Settings.MTCKeysNext.contains(keyCode))
			{
				sendKey(context, KeyEvent.KEYCODE_MEDIA_NEXT);
				toast(context, ">>");
			}				
			if (Settings.MTCKeysPause.contains(keyCode))
			{
				sendKey(context, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
				toast(context, "||");
			}
		}
		
		// Microntek launch app?
		if (intent.getAction().equals(Settings.MTCBroadcastWidget))
		{
			// Install widget?
			int wdgAction  = intent.getIntExtra("myWidget.action", 0);
			String wdgPackage = intent.getStringExtra("myWidget.packageName");
			
			Log.d(TAG, "MTCService recieve widget "+wdgPackage+", "+wdgAction);
			// Install widget?
			if (Settings.MTCWidgetAdd == wdgAction)
			{
				String[] wdgPackageSplit = wdgPackage.split("\\.");
				if (wdgPackageSplit.length > 0)
				{
					String pkgShort = wdgPackageSplit[wdgPackageSplit.length - 1];
					pkgShort = String.valueOf(pkgShort.charAt(0)).toUpperCase() + pkgShort.subSequence(1, pkgShort.length());
					Log.d(TAG, "Started mode: " + pkgShort);						
					toast(context, pkgShort);
				}
				
				killMusic(context);
			}
		}
	}
	
	public void sendKey(Context ctx, int keycode) {
		Log.d(TAG, "Send key " + keycode);
		long eventtime = SystemClock.uptimeMillis();

		Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		KeyEvent downEvent = new KeyEvent(eventtime, eventtime,
				KeyEvent.ACTION_DOWN, keycode, 0);
		downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
		ctx.sendOrderedBroadcast(downIntent, null);
				
		Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		KeyEvent upEvent = new KeyEvent(eventtime, eventtime,
				KeyEvent.ACTION_UP, keycode, 0);
		upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
		ctx.sendOrderedBroadcast(upIntent, null);		
	}
	
	public void killMusic(Context ctx)
	{
		Log.d(TAG, "Killing music");	
		// Stop playback (NORMAL players)
		sendKey(ctx, KeyEvent.KEYCODE_MEDIA_STOP);
	}
	
	public void toast(Context ctx, String msg) {
		if (Settings.get(ctx).getServiceToast()) {
			Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
		}
	}
	
}
