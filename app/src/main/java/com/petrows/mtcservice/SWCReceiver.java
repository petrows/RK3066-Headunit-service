package com.petrows.mtcservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;

import com.petrows.mtcservice.appcontrol.ControllerList;

public class SWCReceiver extends BroadcastReceiver {

	private final static String TAG = "SWCReceiver";

	public SWCReceiver() {}

	@Override
	public void onReceive(Context context, Intent intent) {
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
					ControllerList.get(context).sendKeyPrev();
				}
				if (Settings.MTCKeysNext.contains(keyCode)) {
					Settings.get(context).showToast(">>");
					ControllerList.get(context).sendKeyNext();
				}
				if (Settings.MTCKeysPause.contains(keyCode)) {
					Settings.get(context).showToast("||");
					ControllerList.get(context).sendKeyPlayPause();
				}
                if (Settings.MTCKeysPhone.contains(keyCode)) {
	                if (Settings.get(context).getMediaPlayerPhonerun()) {
		                killMusic(context);
		                Settings.get(context).startMediaPlayer();
	                }
                }
				if (Settings.MTCKeysVolumeUp.contains(keyCode)){
					int vol = Settings.get(context).getVolume();
					int volNew = vol + 1;
					Settings.get(context).setVolumeUSB(volNew);
				}
				if (Settings.MTCKeysVolumeDown.contains(keyCode)){
					int vol = Settings.get(context).getVolume();
					int volNew = vol - 1;
					Settings.get(context).setVolumeUSB(volNew);
				}
				if (Settings.MTCKeysPrevInCat.contains(keyCode)) {
					Settings.get(context).showToast("<< in cat");
					ControllerList.get(context).sendKeyPrevInCat();
				}
				if (Settings.MTCKeysNextInCat.contains(keyCode)) {
					Settings.get(context).showToast(">> in cat");
					ControllerList.get(context).sendKeyNextInCat();
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
		} else if (intent.getAction().equals(Settings.MTCBroadcastWidget)) { // Microntek launch app?
			// Install widget?
			int wdgAction = intent.getIntExtra("myWidget.action", 0);
			String wdgPackage = intent.getStringExtra("myWidget.packageName");

			Log.d(TAG, "MTCService receive widget " + wdgPackage + ", " + wdgAction);
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
		}
	}

	private void killMusic(Context ctx) {
		Log.d(TAG, "Killing music");
		// Stop playback (NORMAL players)
		ControllerList.get(ctx).sendKeyStop();
	}
}
