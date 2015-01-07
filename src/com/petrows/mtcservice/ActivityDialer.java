package com.petrows.mtcservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.microntek.mtcser.BTServiceInf;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class ActivityDialer extends Activity {
	final static String TAG = "ActivityDialer";
	
	BTServiceInf btInterface;
	String phoneNumber = "";
	
	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {

			btInterface = BTServiceInf.Stub.asInterface(service);
			try {
				btInterface.init();
				// Make a call!
				onBtConnected();
			} catch (RemoteException e) {				
				e.printStackTrace();
				Log.d(TAG, "Error " + e.getMessage());
				showError(2);
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			btInterface = null;
		}		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Test who called us...	
		String callRequest = getIntent().getData().getSchemeSpecificPart();
		phoneNumber = callRequest.replaceAll("[^\\+\\d]", "");
		if (phoneNumber.length() == 0)
		{
			// Wrong phone!
			showError(4);
			return;
		}
		
		// Disabled in settings?
		if (!Settings.get(this).getCallerEnable())
		{
			// Do nothing
			Log.d(TAG, "Calling to: " + phoneNumber + " rejected by settings");
			finish();
			return;
		}
		
		Settings.get(this).showToast(String.format(getString(R.string.toast_calling_process), phoneNumber));		
		Log.d(TAG, "Calling to: " + phoneNumber);
				
		try {
			if (!bindService(new Intent("com.microntek.btserver"), this.serviceConnection, 1))
			{
				showError(1);		
				return;
			} else {
				// Will try to call on ServiceConnected!
			}
		} catch (Exception e) {
			Log.e(TAG, "Service exception " + e.getLocalizedMessage());
			showError(1);		
			return;
		}
	}
		
	void onBtConnected()
	{
		// Make a call!
		try {
			btInterface.dialOut(phoneNumber);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showError(3);
		}
		finish();
	}
	
	void showError(int id)
	{
		Log.e(TAG, "Call error " + String.valueOf(id));
		Settings.get(this).showToast(String.format(getString(R.string.toast_calling_error), id));	
		finish();
	}	
}
