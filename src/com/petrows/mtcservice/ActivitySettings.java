package com.petrows.mtcservice;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ActivitySettings extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref);	
	}
	
	@Override
	protected void onPause() {
		// Restart service and re-create the settings
		Settings.destroy();		
		super.onPause();
	}
}
