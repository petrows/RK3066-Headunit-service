package com.petrows.mtcservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.petrows.mtcservice.Settings;

public class BootReceiver extends BroadcastReceiver {

	private final static String TAG = "BootReceiver";
	public static boolean syn = false;

	@Override
	public void onReceive(Context context, Intent intent) {
		// Check out this
		Log.d(TAG, "Starting services");

		//dsa
		syn = true;
		Settings.get(context).startMyServices();
	}
}
