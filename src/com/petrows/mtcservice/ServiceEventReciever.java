package com.petrows.mtcservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;

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
			
			// Test media keys?
			if (Settings.get(context).getMediaKeysEnable())
			{			
				if (Settings.MTCKeysPrev.contains(keyCode))
				{
					sendKey(context, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
					Settings.get(context).showToast("<<");
				}
				if (Settings.MTCKeysNext.contains(keyCode))
				{
					sendKey(context, KeyEvent.KEYCODE_MEDIA_NEXT);
					Settings.get(context).showToast(">>");
				}				
				if (Settings.MTCKeysPause.contains(keyCode))
				{
					sendKey(context, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
					Settings.get(context).showToast("||");
				}
			} else {
				Log.d(TAG, "Media keys handler is disabled in settings");
			}
		}
		
		// C200 keys?
		if (intent.getAction().equals("cn.com.cs2c.android.vehicle.action.NEXT_KEY"))
		{
			sendKey(context, KeyEvent.KEYCODE_MEDIA_NEXT);
			Settings.get(context).showToast(">>");
		}
		if (intent.getAction().equals("cn.com.cs2c.android.vehicle.action.PREVIEW_KEY"))
		{
			sendKey(context, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
			Settings.get(context).showToast("<<");
		}
		
		if (intent.getAction().equals(Settings.MTCBroadcastACC))
		{
			String accState = intent.getStringExtra("accstate");
			Log.d(TAG, "Acc state: " + accState);
			if ("accoff" == accState)
			{
				// We are powering-off
				
				// Set safe volume?
				if (Settings.get(context).getSafeVolumeEnable())
				{
					Settings.get(context).setVolumeSafe();
				}
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
					Settings.get(context).showToast(pkgShort);
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
		ctx.sendBroadcast(downIntent);
				
		Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		KeyEvent upEvent = new KeyEvent(eventtime, eventtime,
				KeyEvent.ACTION_UP, keycode, 0);
		upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
		ctx.sendBroadcast(upIntent);		
	}
	
	public void killMusic(Context ctx)
	{
		Log.d(TAG, "Killing music");	
		// Stop playback (NORMAL players)
		sendKey(ctx, KeyEvent.KEYCODE_MEDIA_STOP);
	}	
}
