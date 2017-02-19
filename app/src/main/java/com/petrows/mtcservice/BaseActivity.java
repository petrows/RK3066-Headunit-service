package com.petrows.mtcservice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class BaseActivity extends Activity {

    private final static String TAG = "BaseActivity";
    private final static int[] themes = {R.style.AppThemeDefault, R.style.AppTheme, R.style.AppThemeDark, R.style.AppThemeBlack};

    private int lastTheme = -1;

    @Override
    protected void onResume() {
        super.onResume();

        try {
            int id = Settings.get(this).getTheme();
            if (id >= 0 && id != lastTheme) {
                recreate();
            }
        } catch (Throwable e) {
            Log.e(TAG, "error on recreate", e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            int id = Settings.get(this).getTheme();
            if (id >= 0) {
                setTheme(themes[id]);
                lastTheme = id;
            }
        } catch (Throwable e) {
            Log.e(TAG, "error on setting theme", e);
        }

    }
}
