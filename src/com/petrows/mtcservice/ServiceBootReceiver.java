package com.petrows.mtcservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceBootReceiver extends BroadcastReceiver {
	final static String TAG = "ServiceBootReceiver";
	public ServiceBootReceiver() {}
	
	@Override
	public void onReceive(Context ctx, Intent arg1) 
	{
		// Check out this
		Log.d(TAG, "Starting service");

        //dsa
        ServiceEventReciever.syn = true;

        //dsa
        if (Settings.get(ctx).getServiceEnable() && !ServiceMain.isRunning)
		    ctx.startService(new Intent(ctx, ServiceMain.class));
	}
}
