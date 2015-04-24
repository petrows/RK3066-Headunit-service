package com.petrows.mtcservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.petrows.mtcservice.HeadUnit;
import com.petrows.mtcservice.R;
import com.petrows.mtcservice.Settings;

public class SafeVolumeReceiver extends BroadcastReceiver {
	
	private final static String TAG = "SafeVolumeReciever";

	@Override
	public void onReceive(Context context, Intent intent) {
		// Check out this
		Log.d(TAG, "Safe volume recieved");
		
		/*if (Settings.get(context).getBool("svol.enable"))
		{
			Settings.get(context).showToast(context.getString(R.string.toast_safe_volume));
			HeadUnit.get(context).setVolume(Settings.get(context).getInt("svol.level"));
		}*/
	}
}

