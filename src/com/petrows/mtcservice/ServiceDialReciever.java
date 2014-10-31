package com.petrows.mtcservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceDialReciever extends BroadcastReceiver {
	final static String TAG = "ServiceDialReciever";

	@Override
	public void onReceive(Context context, Intent intent) {
		String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
		Log.d(TAG, "New call: " + intent.getAction() + ", " + phoneNumber);
	}
}
