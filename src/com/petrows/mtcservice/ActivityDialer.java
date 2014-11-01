package com.petrows.mtcservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ActivityDialer extends Activity {
	final static String TAG = "ActivityDialer";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String phoneNumber = getIntent().getData().getSchemeSpecificPart();
		String phoneNumberClear = phoneNumber.replaceAll("[^\\+\\d]", "");
		Settings.get(this).showToast(String.format(getString(R.string.toast_calling), phoneNumber));
		
		Log.d(TAG, "Calling to: " + phoneNumberClear);
		finish();
	}
}
