package com.petrows.mtcservice;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.util.Log;

import com.petrows.mtcservice.appcontrol.ControllerBase;
import com.petrows.mtcservice.appcontrol.ControllerList;
import com.petrows.mtcservice.view.IconListPreference;

import java.util.ArrayList;

public class SettingsActivity extends PreferenceActivity {
	private final static String TAG = "SettingsActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref);

		MultiSelectListPreference zApps = (MultiSelectListPreference) getPreferenceManager().findPreference("keys.apps");
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> ides = new ArrayList<String>();
		ArrayList<ControllerBase> apps = ControllerList.get(this).getListDisplay();
		for (int z=0; z<apps.size(); z++)
		{
			ControllerBase item = apps.get(z);
			titles.add(item.getName());
			ides.add(item.getId());
			Log.d(TAG, titles.get(z).toString());
		}
		zApps.setEntryValues(ides.toArray(new CharSequence[ides.size()]));
		zApps.setEntries(titles.toArray(new CharSequence[titles.size()]));
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
