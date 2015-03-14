package com.petrows.mtcservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	private final static String TAG = "BootReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// Check out this
		Log.d(TAG, "Starting services");

		//dsa
		SWCReceiver.syn = true;
		Settings.get(context).startMyServices();
	}
}
