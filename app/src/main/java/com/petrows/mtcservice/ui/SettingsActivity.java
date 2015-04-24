package com.petrows.mtcservice.ui;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.petrows.mtcservice.R;
import com.petrows.mtcservice.Settings;
import com.petrows.mtcservice.appcontrol.ControllerBase;
import com.petrows.mtcservice.appcontrol.ControllerList;

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
	protected void onResume() {
		super.onResume();

		// Set app icon (if app selected)
		if (!Settings.get(this).getMediaPlayerApp().isEmpty()) {
			try {
				Drawable playerIcon = getPackageManager().getApplicationIcon(Settings.get(this).getMediaPlayerApp());
				if (null != playerIcon)
				{
					PreferenceScreen playerOption = (PreferenceScreen)getPreferenceManager().findPreference("player_screen");
					playerOption.setIcon(playerIcon);
				}
			} catch (PackageManager.NameNotFoundException e) {
				Log.d(TAG, "Error get package!");
				e.printStackTrace();
			}
		}
	}


	@Override
	protected void onPause() {
		Log.d("CFG", "Settings closed, apply new");
		// Restart service and re-create the settings
		Settings.destroy();
		// Bt restart
		// BtReceiver.get(this).connectBt();
		super.onPause();
	}
}
