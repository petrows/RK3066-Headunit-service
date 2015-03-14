package com.petrows.mtcservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class DialActivity extends Activity {

	private final static String TAG = "DialActivity";

	private android.microntek.mtcser_v1.BTServiceInf btInterfaceV1;
	private android.microntek.mtcser_v2.BTServiceInf btInterfaceV2;

	private String phoneNumber = "";

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {

			btInterfaceV1 = android.microntek.mtcser_v1.BTServiceInf.Stub.asInterface(service);
			btInterfaceV2 = android.microntek.mtcser_v2.BTServiceInf.Stub.asInterface(service);

			// Check the FW version for call

			try {
				Settings.get(null).showToast("Before btInterface.init()");
				btInterfaceV2.init();
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
			btInterfaceV1 = null;
			btInterfaceV2 = null;
		}
	};

	private void onBtConnected() {
		Settings.get(null).showToast("onBtConnected");
		// Make a call!
		try {
			btInterfaceV2.dialOut(phoneNumber);
			Settings.get(null).showToast("Done?!");
			finish();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showError(3);
		}
	}

	private void showError(int id) {
		Log.e(TAG, "Call error " + String.valueOf(id));
		Settings.get(null).showToast(String.format(getString(R.string.toast_calling_error), id));
		finish();
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String callRequest = getIntent().getData().getSchemeSpecificPart();
		phoneNumber = callRequest.replaceAll("[^\\+\\d]", "");
		if (phoneNumber.length() == 0) {
			// Wrong phone!
			showError(4);
			return;
		}

		// Disabled in settings?
		if (!Settings.get(this).getCallerEnable()) {
			// Do nothing
			Log.d(TAG, "Calling to: " + phoneNumber + " disabled");
			finish();
			return;
		}

		Settings.get(this).showToast(String.format(getString(R.string.toast_calling_process), phoneNumber));
		Log.d(TAG, "Calling to: " + phoneNumber);

        /*

        try {
            if (!bindService(new Intent("com.microntek.btserver"), serviceConnection, Context.BIND_AUTO_CREATE ))
                showError(1);
            //Dial happens here!
        } catch (Exception e) {
            Log.e(TAG, "Service exception " + e.getLocalizedMessage());
            showError(1);
        }*/

		ServiceBtReciever.get(this).call(phoneNumber);
	}
}
