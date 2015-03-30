package com.petrows.mtcservice;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.util.Log;

import com.petrows.mtcservice.view.IconListPreference;

public class SettingsActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref);


		IconListPreference zApps = (IconListPreference) getPreferenceManager().findPreference("test.list");
		// zApps.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		CharSequence[] zAppsTitle = {"a", "b", "z"};
		zApps.setEntryValues(zAppsTitle);
		zApps.setEntries(zAppsTitle);
		zApps.setTitle("Asshole");
	}

	@Override
	protected void onPause() {
		Log.d("CFG", "Settings closed, apply new");
		// Restart service and re-create the settings
		Settings.destroy();
		// Bt restart
		ServiceBtReciever.get(this).connectBt();
		super.onPause();
	}
}
