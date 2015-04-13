package com.petrows.mtcservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SafeVolumeReciever extends BroadcastReceiver {
	
	private final static String TAG = "SafeVolumeReciever";
	public static boolean syn = false;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// Check out this
		Log.d(TAG, "Safe volume recieved");
		
		if (Settings.get(context).getBool("svol.enable"))
		{
			Settings.get(context).showToast(context.getString(R.string.toast_safe_volume));
			Settings.get(context).setVolume(getInt("svol.level"));
		}
	}
}

