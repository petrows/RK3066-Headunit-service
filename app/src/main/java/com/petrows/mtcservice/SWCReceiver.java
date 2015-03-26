package com.petrows.mtcservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;

import com.petrows.mtcservice.com.petrows.mtcservice.appcontrol.ControllerList;

public class SWCReceiver extends BroadcastReceiver {

	private final static String TAG = "SWCReceiver";
	ControllerList appController = null;



	//dsa
	public static boolean syn = false;
	private boolean ord = false;

	public SWCReceiver() {
		appController = new ControllerList();
	}

	private void fireIT(Context ctx, Intent it, boolean frrp) {
		if (ord)
			ctx.sendOrderedBroadcast(it, null);
		else {
			if (frrp) it.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
			ctx.sendBroadcast(it);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		//int keyCode_ = intent.getIntExtra("keyCode", 0);
		//toast(context, "Action " + intent.getAction() + ", key " + keyCode_);

		Log.d(TAG, "Action " + intent.getAction() + ", key " + intent.getIntExtra("keyCode", -1));

		// Microntek keys?
		if (intent.getAction().equals(Settings.MTCBroadcastIrkeyUp)) {
			// Key pressed...

			int keyCode = intent.getIntExtra("keyCode", 0);
			Log.d(TAG, "MTCService recieve key " + keyCode);

			// Test media keys?
			if (Settings.get(context).getMediaKeysEnable()) {
				if (Settings.MTCKeysPrev.contains(keyCode)) {
					Settings.get(context).showToast("<<");
					sendKey(context, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
				}
				if (Settings.MTCKeysNext.contains(keyCode)) {
					Settings.get(context).showToast(">>");
					sendKey(context, KeyEvent.KEYCODE_MEDIA_NEXT);
				}
				if (Settings.MTCKeysPause.contains(keyCode)) {
					Settings.get(context).showToast("||");
					sendKey(context, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);

				}
			} else {
				Log.d(TAG, "Media keys handler is disabled in settings");
			}
		} else if (intent.getAction().equals(Settings.MTCBroadcastACC)) {
			String accState = intent.getStringExtra("accstate");
			Log.d(TAG, "Acc state: " + accState);
			if ("accoff" == accState) {
				// We are powering-off

				// Set safe volume?
				if (Settings.get(context).getSafeVolumeEnable()) {
					Settings.get(context).setVolumeSafe();
				}
			}
		} else if (intent.getAction().equals(Settings.MTCBroadcastWidget)) // Microntek launch app?
		{
			// Install widget?
			int wdgAction = intent.getIntExtra("myWidget.action", 0);
			String wdgPackage = intent.getStringExtra("myWidget.packageName");

			Log.d(TAG, "MTCService recieve widget " + wdgPackage + ", " + wdgAction);
			// Install widget?
			if (Settings.MTCWidgetAdd == wdgAction) {
				String[] wdgPackageSplit = wdgPackage.split("\\.");
				if (wdgPackageSplit.length > 0) {
					String pkgShort = wdgPackageSplit[wdgPackageSplit.length - 1];
					pkgShort = String.valueOf(pkgShort.charAt(0)).toUpperCase() + pkgShort.subSequence(1, pkgShort.length());
					Log.d(TAG, "Started mode: " + pkgShort);
					Settings.get(context).showToast(pkgShort);
				}

				killMusic(context);
			}
		} else if (intent.getAction().equals(Settings.C200ActionNext)) {
			sendKeyNow(context, KeyEvent.KEYCODE_MEDIA_NEXT);
			Settings.get(context).showToast(">>");
		} else if (intent.getAction().equals(Settings.C200ActionPrev)) {
			sendKeyNow(context, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
			Settings.get(context).showToast("<<");
		} else if (intent.getAction().equals(Settings.C200ActionPlayPause)) {
			sendKeyNow(context, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
			Settings.get(context).showToast("||");
		}
	}

	public void sendKey(Context ctx, int keycode) {
		Log.d(TAG, "Send key " + keycode);
		long eventtime = SystemClock.uptimeMillis();

		//dsa
		if (false == ord) if (syn) ord = true;

		Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		KeyEvent downEvent = new KeyEvent(eventtime, eventtime,
				KeyEvent.ACTION_DOWN, keycode, 0);
		downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);

		//dsa
		fireIT(ctx, downIntent, true);

		Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		KeyEvent upEvent = new KeyEvent(eventtime, eventtime,
				KeyEvent.ACTION_UP, keycode, 0);
		upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);

		//dsa
		fireIT(ctx, upIntent, false);
		if (!ord) {
			Settings.get(ctx).mySleep(400);
			Intent blankIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
			KeyEvent blankEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_UNKNOWN);
			blankIntent.putExtra(Intent.EXTRA_KEY_EVENT, blankEvent);
			fireIT(ctx, blankIntent, true);
		}
	}

	public void sendKeyNow(Context ctx, int keycode) {
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

	private void killMusic(Context ctx) {
		Log.d(TAG, "Killing music");
		// Stop playback (NORMAL players)
		sendKey(ctx, KeyEvent.KEYCODE_MEDIA_STOP);
	}
}
