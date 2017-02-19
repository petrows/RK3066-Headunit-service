package com.petrows.mtcservice;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MediaAppActivity extends BasePreferenceActivity {

    private final String TAG = "MediaAppActivity";
    private PreferenceCategory mApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefapps);
        if (mApps == null) {
            mApps = (PreferenceCategory) getPreferenceManager().findPreference("call_button_applist");
            PackageManager mPackageManager = getPackageManager();
            List<ApplicationInfo> pkgs = mPackageManager.getInstalledApplications(PackageManager.GET_META_DATA);
            List<AppPreference> prefs = new ArrayList<AppPreference>();

            String callAppPkg = Settings.get(this).getMediaPlayerApp();
            for (ApplicationInfo pkg : pkgs) {
                AppPreference pref = new AppPreference(this);
                pref.setTitle(mPackageManager.getApplicationLabel(pkg));
                Drawable icon = pkg.loadIcon(mPackageManager);
                pref.setPkgName(pkg.packageName);
                boolean isChecked = pkg.packageName.equals(callAppPkg);
                pref.setChecked(isChecked);
                pref.setIcon(icon);
                pref.setOnPreferenceChangeListener(new PrefChangeListener(mApps, pref));
                prefs.add(pref);
            }
            Collections.sort(prefs, new ApplicationComparator());
            for (AppPreference pref : prefs) {
                mApps.addPreference(pref);
            }
        }
    }

    class AppPreference extends CheckBoxPreference {
        private String mPkgName;

        public AppPreference(Context context) {
            super(context);
        }

        public String getPkgName() {
            return mPkgName;
        }

        public void setPkgName(String mPkgName) {
            this.mPkgName = mPkgName;
        }

    }

    class PrefChangeListener implements Preference.OnPreferenceChangeListener {

        private PreferenceCategory mApps;
        private AppPreference mPref;

        PrefChangeListener(PreferenceCategory apps, AppPreference pref) {
            mApps = apps;
            mPref = pref;
        }

        @Override

        public boolean onPreferenceChange(Preference preference, Object o) {
            boolean checked = (Boolean) o;
            if (checked) {
                Settings.get(this.mPref.getContext()).setCfgString("player.app", mPref.getPkgName());
                for (int i = 0; i < mApps.getPreferenceCount(); i++) {
                    AppPreference pref = (AppPreference) mApps.getPreference(i);
                    if (!pref.getPkgName().equals(mPref.getPkgName())) {
                        pref.setChecked(false);
                    }
                }
            }
            return checked;
        }
    }

    class ApplicationComparator implements Comparator<AppPreference> {
        @Override
        public int compare(AppPreference a, AppPreference b) {
            return a.getTitle().toString().compareTo(b.getTitle().toString());
        }
    }
}
