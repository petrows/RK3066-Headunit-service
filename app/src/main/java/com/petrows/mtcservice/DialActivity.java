package com.petrows.mtcservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.microntek.mtcser.BTServiceInf;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class DialActivity extends Activity {

    private final static String TAG = "DialActivity";

    private BTServiceInf btInterface;
    private String phoneNumber = "";

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            btInterface = BTServiceInf.Stub.asInterface(service);
            try {
                Settings.get( null ).showToast( "Before btInterface.init()" );
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

    private void onBtConnected()
    {
        Settings.get( null ).showToast( "onBtConnected" );
        // Make a call!
        try {
            btInterface.dialOut(phoneNumber);
            Settings.get( null ).showToast( "Done?!" );
            finish();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            showError(3);
        }
    }

    private void showError( int id )
    {
        Log.e(TAG, "Call error " + String.valueOf(id));
        Settings.get(null).showToast(String.format(getString(R.string.toast_calling_error), id));
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            Log.d(TAG, "Calling to: " + phoneNumber + " disabled");
            finish();
            return;
        }

        Settings.get(this).showToast(String.format(getString(R.string.toast_calling_process), phoneNumber));
        Log.d(TAG, "Calling to: " + phoneNumber);

        try {
            if (!bindService(new Intent("com.microntek.btserver"), serviceConnection, Context.BIND_AUTO_CREATE ))
                showError(1);
            //Dial happens here!
        } catch (Exception e) {
            Log.e(TAG, "Service exception " + e.getLocalizedMessage());
            showError(1);
        }
    }
}