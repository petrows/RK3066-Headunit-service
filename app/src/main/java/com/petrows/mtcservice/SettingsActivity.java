package com.petrows.mtcservice;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.ListPreference;
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

		IconListPreference zApps = (IconListPreference) getPreferenceManager().findPreference("test.list");
		// zApps.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
		//CharSequence[] zAppsTitle = {"a", "b", "z"};
		//zApps.setEntryValues(zAppsTitle);
		//zApps.setEntries(zAppsTitle);
		zApps.setTitle("Asshole");


		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> ides = new ArrayList<String>();
		// ArrayList<Drawable> icons;
		ControllerList ctr = new ControllerList(this);
		//CharSequence[] titlesText = new CharSequence[ctr.getListDisplay().size()];
		for (int z=0; z<ctr.getListDisplay().size(); z++)
		{
			ControllerBase item = ctr.getListDisplay().get(z);
			titles.add(item.getName());
			//ides.add(item.getId());
			//titlesText[z] = "App: " + z;
			Log.d(TAG, titles.get(z).toString());
		}


		//zApps.setEntryValues(titlesText);
		//zApps.setEntries(titlesText);
		zApps.setEntryValues(titles.toArray(new CharSequence[titles.size()]));
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
