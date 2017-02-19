package com.petrows.mtcservice;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;


public class BasePreferenceActivity extends PreferenceActivity {

    private final static String TAG = "BasePreferenceActivity";
    private final static int[] themes = {R.style.AppThemeDefault, R.style.AppTheme, R.style.AppThemeDark, R.style.AppThemeBlack};

    private int lastTheme = -1;

    @Override
    protected void onResume() {

        try {
            int id = Settings.get(this).getTheme();
            if (id >= 0 && id != lastTheme) {
                recreate();
            }
        } catch (Throwable e) {
            Log.e(TAG, "error on recreate", e);
        }

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            int id = Settings.get(this).getTheme();
            if (id >= 0) {
                setTheme(themes[id]);
                lastTheme = id;
            }
        } catch (Throwable e) {
            Log.e(TAG, "error on setting theme", e);
        }

        super.onCreate(savedInstanceState);
    }
}
