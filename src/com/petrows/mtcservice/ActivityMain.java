package com.petrows.mtcservice;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ActivityMain extends Activity {
	final static String TAG = "MTCService.ActivityMain";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.activity_main);
		
		TextView txtVersion = (TextView)findViewById(R.id.txtVersion);
		String version = "?";
		try {
			PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version = pInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtVersion.setText("Version: " + version);

		if (Settings.get(this).getServiceEnable() && !ServiceMain.isRunning)
		{
			Log.d(TAG, "Starting service!");
			startService(new Intent(this, ServiceMain.class));
		}
		
		super.onCreate(savedInstanceState);
	}
}
