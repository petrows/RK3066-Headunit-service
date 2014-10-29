package com.petrows.mtcservice;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class ActivityDialer extends Activity {
	final static String TAG = "ActivityDialer";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		Log.d(TAG, "New call: " + getIntent().getAction() + ", " + getIntent().getDataString());
		Uri intent_uri = intent.getData();
		
		Log.d(TAG, "Get URI: " + intent_uri);
	}
}
