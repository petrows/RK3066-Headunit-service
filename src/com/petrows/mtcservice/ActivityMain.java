package com.petrows.mtcservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ActivityMain extends Activity {
	final static String TAG = "MTCService.ActivityMain";
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (Settings.get().getServiceEnable())
		{
			Log.d(TAG, "Starting service!");
			startService(new Intent(this, ServiceMain.class));
		}
		
		super.onCreate(savedInstanceState);
	}
}
