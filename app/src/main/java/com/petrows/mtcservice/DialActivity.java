package com.petrows.mtcservice;

import android.os.Bundle;
import android.util.Log;

public class DialActivity extends BaseActivity {

    private final static String TAG = "DialActivity";

    private String phoneNumber = "";

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

        ServiceBtReciever.get(this).call(phoneNumber);

        finish();
    }
}
