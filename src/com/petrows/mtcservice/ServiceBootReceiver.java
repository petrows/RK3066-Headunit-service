package com.petrows.mtcservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class ServiceBootReceiver extends BroadcastReceiver {
	final static String TAG = "MTCService.ServiceBootReceiver";
	public ServiceBootReceiver() {}
	
	@Override
	public void onReceive(Context ctx, Intent arg1) 
	{
		// Check out this
		Log.d(TAG, "Starting service");
		
		if (Settings.get(ctx).getServiceEnable())
		{
			ctx.startService(new Intent(ctx, ServiceMain.class));
		}
	}
}
